package com.xiami.config;

import cn.hutool.core.util.StrUtil;
import com.xiami.utils.YamlUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author entfrm
 * @date 2019/6/12
 * @description 全局配置类
 */
@Slf4j
public class GlobalConfig {

    private static String NAME = "application.yml";

    /**
     * 当前对象实例
     */
    private static GlobalConfig globalConfig = null;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();

    private GlobalConfig() {
    }

    /**
     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
     */

    public static synchronized GlobalConfig getInstance() {
        if (globalConfig == null) {
            synchronized (GlobalConfig.class) {
                if (globalConfig == null)
                    globalConfig = new GlobalConfig();
            }
        }
        return globalConfig;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            Map<?, ?> yamlMap = null;
            try {
                yamlMap = YamlUtil.loadYaml(NAME);
                value = String.valueOf(YamlUtil.getProperty(yamlMap, key));
                map.put(key, value != null ? value : StrUtil.EMPTY);
            } catch (FileNotFoundException e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    /**
     * 获取系统语言
     */
    public static String getLang() {
        return StrUtil.nullToDefault(getConfig("entfrm.lang"), "zh_CN");
    }

    /**
     * 获取项目名称
     */
    public static String getName() {
        return StrUtil.nullToDefault(getConfig("entfrm.name"), "entfrm开发平台");
    }

    /**
     * 获取项目版本
     */
    public static String getVersion() {
        return StrUtil.nullToDefault(getConfig("entfrm.version"), "2.0.0");
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile() {
        return getConfig("entfrm.profile");
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressSwitch() {
        return Boolean.valueOf(getConfig("entfrm.addressSwitch"));
    }

    /**
     * 获取redis开关
     */
    public static Boolean isRedisSwitch() {
        return Boolean.valueOf(getConfig("entfrm.redisSwitch"));
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getConfig("entfrm.profile") + "avatar/";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getConfig("entfrm.profile") + "download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getConfig("entfrm.profile") + "upload/";
    }

}
