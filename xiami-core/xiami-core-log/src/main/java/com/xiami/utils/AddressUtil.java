package com.xiami.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiami.config.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author xiami
 * @date 2019/6/12
 * @description 根据IP地址获取城市
 */
@Slf4j
public class AddressUtil {
    /**
     * 根据ip获取地址
     * @param ip
     * @return
     */
    public static String getAddress(String ip) {
        String url = "http://ip.ws.126.net/ipquery?ip=" + ip;
        String str = HttpUtil.get(url);
        if(!StrUtil.hasBlank(str)){
            String substring = str.substring(str.indexOf("{"), str.indexOf("}")+1);
            System.out.println(substring);
            JSONObject jsonObject = JSONUtil.parseObj(substring);
            String province = jsonObject.getStr("province");
            String city = jsonObject.getStr("city");
            return province + " " + city;
        }
        return null;
    }



    public static void main(String[] args) {
        String ip = "121.32.78.251";
        String result = getAddress(ip);
        System.out.println(result);
        if(StrUtil.hasBlank("")){
            System.out.println(1);
        }
    }
}
