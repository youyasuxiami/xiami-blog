package com.xiami.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiami.entity.User;
import com.xiami.exception.UserTokenException;
import com.xiami.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­11­21 11:16
 */
@Configuration
public class AuthUtils {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthConfig authConfig;

    public BaseJwtInfo getBaseJwtInfo(String token) throws Exception {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String name = jwt.getClaim("name").asString();
            User user = userService.getUserByName(name);
            BaseJwtInfo baseJwtInfo = new BaseJwtInfo();
            baseJwtInfo.setName(user.getName());
            baseJwtInfo.setUserId(user.getId());
            return baseJwtInfo;
        } catch (TokenExpiredException ex) {
            throw new UserTokenException("User token expired!");
        } catch (AlgorithmMismatchException ex) {
            throw new UserTokenException("AlgorithmMismatch!");
        } catch (InvalidClaimException ex) {
            throw new UserTokenException("InvalidClaim");
        } catch (JWTDecodeException ex) {
            throw new UserTokenException("JWTDecode Exception");
        } catch (IllegalArgumentException ex) {
            throw new UserTokenException("User token is null or empty!");
        }
    }

    public User getUser(String token) throws Exception {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String name = jwt.getClaim("name").asString();
            User user = userService.getUserByName(name);
            return user;
        } catch (TokenExpiredException ex) {
            throw new UserTokenException("User token expired!");
        } catch (AlgorithmMismatchException ex) {
            throw new UserTokenException("AlgorithmMismatch!");
        } catch (InvalidClaimException ex) {
            throw new UserTokenException("InvalidClaim");
        } catch (JWTDecodeException ex) {
            throw new UserTokenException("JWTDecode Exception");
        } catch (IllegalArgumentException ex) {
            throw new UserTokenException("User token is null or empty!");
        }
    }
}
