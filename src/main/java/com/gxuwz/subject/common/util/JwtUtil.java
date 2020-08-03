package com.gxuwz.subject.common.util;

import com.gxuwz.subject.common.constant.StatusCode;
import com.gxuwz.subject.model.JwtValidate;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author: 蔡奇峰
 * date: 2020/3/25 11:27
 * @Version V1.0
 **/
@Slf4j
public class JwtUtil {

    /**
     * jwt秘钥
     */
    private static final String SECRET = "tale_jwt";


    /**
     * 生成jwt
     *
     * @param id
     * @param userName
     * @param uType
     * @param expireTime
     * @return
     */
    public static String createToken(String id, String userName, String uType, Date expireTime) {
        Map<String, Object> info = new HashMap<>();

        info.put("id", id);
        info.put("userName", userName);
        info.put("uType", uType);

        SecretKey key = getSecret();

        String jwt = Jwts.builder()
                .setClaims(info)
                .setIssuer(userName)
                .setSubject("tale")
                .setIssuedAt(new Date())
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, key)
//                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        return jwt;
    }

    /**
     * 验证jwt
     *
     * @param jwt
     * @return
     */
    public static JwtValidate validateJwt(String jwt){

        JwtValidate validate = new JwtValidate();

        Claims claims = null;

        try {
            claims = parseJwt(jwt);
            validate.setSuccess(true);
            validate.setClaims(claims);
        }catch (ExpiredJwtException e) {

            validate.setSuccess(false);
            validate.setErrCode(StatusCode.JWT_EXPIRE);
            log.error("jwt过期:" + e.getMessage());

        }catch (Exception e){
            log.error("jwt exec--->" + e.getMessage());
            validate.setSuccess(false);
            validate.setErrCode(StatusCode.JWT_EXCEPTION);
        }

        return validate;
    }


    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     */
    public static Claims parseJwt(String jwt){
        SecretKey key = getSecret();

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }


    /**
     * 获取加密秘钥
     *
     * @return
     */
    public static SecretKey getSecret(){
//        String encodeKey = MD5Util.saltEncryption(SECRET);

        SecretKey key = new SecretKeySpec(SECRET.getBytes(), 0 , SECRET.length(), "AES");

        return key;
    }
}
