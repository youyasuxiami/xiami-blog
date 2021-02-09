package com.xiami.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiami
 * @date 2019/6/12
 * @description 根据IP地址获取城市
 */
@Slf4j
public class AddressUtil {
    /**
     * 根据ip获取地址
     *
     * @param ip
     * @return
     */
    public static String getAddress(String ip) {
        String url = "http://ip.taobao.com/outGetIpInfo";
        Map hashMap = new HashMap();
        hashMap.put("ip", ip);
        hashMap.put("accessKey", "alibaba-inc");
        String str = HttpUtil.post(url, hashMap);
        System.out.println(str);
        if (!StrUtil.hasBlank(str)) {
            JSONObject jsonObject = JSONUtil.parseObj(str);
            JSONObject data = (JSONObject) jsonObject.get("data");
            System.out.println("1");
            return data.getStr("region") + " " + data.getStr("city");
        }
        return null;
    }


    public static void main(String[] args) {
        String ip = "117.136.31.82";
        String result = getAddress(ip);
        System.out.println(result);
        if (StrUtil.hasBlank("")) {
            System.out.println(1);
        }
    }
}
