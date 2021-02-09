package com.xiami;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­11­18 21:29
 */
public class JWTUtil {
    /**
     * 设置过期时间24小时
     */
    private static final long EXPIRE_TIME = 1000*60*60*24;
    ///**
    // * 设置密钥
    // */
    //private static final String SECRET = "shiro+jwt";

    /**
     * 根据用户名创建一个token
     * @return 返回的token字符串
     */
    //public static String createToken(String username){
    public static String createToken(Map<String, String> claims,String secret){
        try {
            //将当前时间的毫秒数和设置的过期时间相加生成一个新的时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            //由密钥创建一个指定的算法
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTCreator.Builder builder = JWT.create()
                    //附带过期时间
                    .withExpiresAt(date);
            //附带的信息
            claims.forEach(builder::withClaim);
            //使用指定的算法进行标记
            return builder.sign(algorithm);
            //return JWT.create()
            //        //附带username信息
            //        //.withClaim("username",username)
            //        //附带过期时间
            //        .withExpiresAt(date)
            //        //使用指定的算法进行标记
            //        .sign(algorithm);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token是否正确
     * @param token 前端传过来的token
     * @param name 用户名
     * @return 返回boolean
     */
    public static boolean verify(String token,String name,String secret){
        try {
            //获取算法
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //生成JWTVerifier
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("name",name)
                    .build();
            //验证token
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 从token中获得username，无需secret
     * @param token token
     * @return username
     */
    public static String getUsername(String token){
        try {
            System.out.println("token:"+token);
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getClaim("name").asString();
            return username;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
