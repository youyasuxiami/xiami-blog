package com.xiami.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * Description：加密工具类
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­07­20 15:03
 */
public class AccountSecurityUtils {
    private static final Logger log = LoggerFactory.getLogger(AccountSecurityUtils.class);

    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsD1gI70BxYujhNw8NpaVKRXkcRofoeUbN9Dj5m3i3h9XAIS6LkjI01L4ieRpTHnMEzoXUY8a2/svDf//xuHuDJlZBNtCXK4DPx5x4zHdUWDjFGpWlMQzhsqQlfs0tkN5gP095g27L0ki/NrRuBpgxP1q2dHKpL37sBF8XNRpedwIDAQAB";

    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKwPWAjvQHFi6OE3Dw2lpUpFeRxGh+h5Rs30OPmbeLeH1cAhLouSMjTUviJ5GlMecwTOhdRjxrb+y8N///G4e4MmVkE20JcrgM/HnHjMd1RYOMUalaUxDOGypCV+zS2Q3mA/T3mDbsvSSL82tG4GmDE/WrZ0cqkvfuwEXxc1Gl53AgMBAAECgYEAogyhgWi0bRYW92Z/yv6julvMQRE8l3sBcJ//uTbwbwqECrw1tkYu+wsTOCyO2pHnCjPoX6zJTziSeMJpMCPsToCm3+EO38Ki/12RSJ51DgSl7C4R8leoFMZAmExQO4L1rVae2dJS80dvfpLeNiquJw4y7xjXIBUYbQfu+mNKHoECQQDic2F3UMm05M1d/POQHqsuMohoDhwVM0N8/SdbA4fpYFFOahMWdmuFclYbkgTwfYBdGMH/UHLsCGWVv8z48jKHAkEAwoMID93DzTsZWqxAoyGDg7GGdNsMPvYErRxwcOVNpdQXv8F7IAXOslLTvJF542dplCCNzLgyupwxMPlMLuZAkQJANpcoHPJt3dz2oTzUnp62F6n49lTIclfsYhpJPYipYBpnH2c0+MpNe1sn5Peblzo6ErdgNSN4wOv5SVN2n2ELywJAGjyiccFwD9bQ7LIfZeG3Y6QmhsylMjjtGIylfhTwDFY3fd4TRZaC8vrJJL5aupnQW/KoLd0KurEm0XxPEmRsgQJARKNghvpoce39bD/BbC2AojY5Ea6afLzW+GllxHGNGkgGu+x3c4PIUDAt8f60021WoENtonEQpEjzbIU5XpefJA==";


    /**
     * 加密数据和秘钥的编码方式
     */
    public static final String UTF_8 = "UTF-8";

    public static final String RSA_ALGORITHM_NO_PADDING = "RSA";

    public static String decrypt(String password) {
        try {
            String getPass = decryptRSADefault(PRIVATE_KEY, password);
            String longtime = StringUtils.substringAfterLast(getPass, ",");
            //if ((System.currentTimeMillis() - Long.valueOf(longtime) > 30 * 60 * 1000)) {
            //    //抛出自定义异常
            //    throw new MYException("密码超时");
            //}
            return StringUtils.substringBeforeLast(getPass, ",");
        } catch (Exception e) {
            //if (e instanceof MYException) {
            //    throw new MYException(e.getMessage());
            //}
            e.printStackTrace();
            log.error("password is :" + password + " 密码解密异常：" + e.getMessage());
        }
        return null;
    }

    public static String decryptRSADefault(String privateKeyStr, String data) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NO_PADDING);
        byte[] privateKeyArray = privateKeyStr.getBytes();
        byte[] dataArray = data.getBytes();
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyArray));
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NO_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(dataArray)), UTF_8);
    }
}

