package com.xiami.web;

import com.xiami.base.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Author：郑锦
 * Date：2020­06­17 23:17
 * Description：文件上传
 */
@RestController
@RequestMapping("/file")
public class FileController {

    public final static String EXCEL_PATH_PREFIX = "static/upload/excels";

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        //如果文件为空，返回状态码
        if (file.isEmpty()) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "上传的文件为空，请重新上传");
        }

        //文件名称
        String fileName = file.getOriginalFilename();

        // 项目路径
        String fileDirPath = new String("src/main/resources/" + EXCEL_PATH_PREFIX);
        //根据项目路径 构造文件
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }

        //磁盘路径
        System.out.println(fileDir.getAbsolutePath());

        try {
            // 构建真实的文件路径：磁盘路径\文件全名.文件类型
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + fileName);
            System.out.println(newFile.getAbsolutePath());
            // 上传图片到 -》 “绝对路径/磁盘路径”
            file.transferTo(newFile);
            return new ResponseResult(ResponseResult.CodeStatus.OK, "上传文件成功");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "上传文件发生异常");
        }
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/download")
    public ResponseResult downloadFile(String fileName, HttpServletRequest request,HttpServletResponse response) throws Exception {
        String filePath = null;                       //linux下使用/opt/nfdw/
        //fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        if (fileName.equals("")) {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "文件名不能为空");
        }
        //项目路径
        String fileDirPath = new String("src/main/resources/" + EXCEL_PATH_PREFIX);
        //根据项目路径 构造文件
        File fileDir = new File(fileDirPath);
        // 构建真实的文件路径
        File newFile = new File(fileDir.getAbsolutePath() + File.separator + fileName);
        if (newFile == null) {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "文件不存在");
        }
        if (newFile.exists()) {
            // response.setContentType("application/force-download");// 设置强制下载不打开
            response.setContentType("multipart/form-data");
            fileName = filenameEncoding(fileName,request);
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(newFile);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return new ResponseResult(ResponseResult.CodeStatus.OK, "文件下载成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult(ResponseResult.CodeStatus.FAIL, "文件下载异常");
            }
        }
        return new ResponseResult(ResponseResult.CodeStatus.FAIL, "文件不存在2");
    }
    public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
        String agent = request.getHeader("User-Agent"); //获取浏览器
        if (agent.contains("Firefox")) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8"))
                    + "?=";
        } else if(agent.contains("MSIE")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
