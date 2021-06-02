import com.xiami.utils.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­31 12:47
 */
public class TestDemo {

    //@Autowired
    //@Qualifier("redisTemplate")
    //private RedisTemplate redisTemplate;
    public  final RedisTemplate redisTemplate;

    @Autowired
    public RedisUtil redisUtil;

    public TestDemo(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //String result = "";
        //String str = "APP_IDabcTIMESTAMP1610092358139TRANS_ID20160412150606100335423B2732427";
        //
        //MessageDigest md5 = MessageDigest.getInstance("MD5");
        //md5.update((str).getBytes("UTF-8"));
        //byte b[] = md5.digest();
        //
        //int i;
        //StringBuffer buf = new StringBuffer("");
        //
        //for(int offset=0; offset<b.length; offset++){
        //    i = b[offset];
        //    if(i<0){
        //        i+=256;
        //    }
        //    if(i<16){
        //        buf.append("0");
        //    }
        //    buf.append(Integer.toHexString(i));
        //}
        //
        //result = buf.toString();
        //System.out.println("result = " + result);
        //String token = getToken();
        //System.out.println(token);

    }

    public static String getToken()  {

        //生成token
        Date dd = new Date();
        String TIMESTAMP = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS")).format(dd);
        String TIMESTAMP1 = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(dd);
        StringBuilder sbid = new StringBuilder();
        sbid.append(TIMESTAMP1);
        for (int i = 0; i < 6; i ++) {
            sbid.append(new Random().nextInt(10));
        }
        String TRANS_ID = sbid.toString();

        //md5加密
        //String tokenStr = (new StringBuilder("APP_ID")).append(APP_ID).append("TIMESTAMP").append(TIMESTAMP).append("TRANS_ID").append(TRANS_ID).append(APP_SECRET).toString();
        String tokenStr ="APP_IDabcTIMESTAMP1610092358139TRANS_ID20160412150606100335423B2732427";
        String token = "";
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte result[] = digest.digest(tokenStr.getBytes());
            StringBuilder sb = new StringBuilder();
            byte abyte0[];
            int j = (abyte0 = result).length;
            //二进制转换字符串
            for (int i = 0; i < j; i ++) {
                byte b = abyte0[i];
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append((new StringBuilder("0")).append(hex).toString());
                }else {
                    sb.append(hex);
                }
                token = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("加密算法异常:"+e.getMessage());
            e.printStackTrace();
        }
        System.out.println("token:"+token);

        return token;
    }
}
