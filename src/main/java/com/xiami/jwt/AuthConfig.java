package com.xiami.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­11­21 11:17
 */
@Component
public class AuthConfig {
    @Value("${auth.token-header}")
    private String tokenHeader;

    private byte[] pubKeyByte;

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public byte[] getPubKeyByte() {
        return pubKeyByte;
    }

    public void setPubKeyByte(byte[] pubKeyByte) {
        this.pubKeyByte = pubKeyByte;
    }
}
