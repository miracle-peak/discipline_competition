package com.gxuwz.subject.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 11:27
 *
 * @Version V1.0
 **/
public class JWTUtil {
    // 秘钥
    private static final String SECRET = "tale_jwt";


    /**
     * 生成jwt
     * @param id
     * @param userName
     * @param uType
     * @return
     */
    public static String createToken(String id, String userName, String uType) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24 * 6); // 设置过期时间
        Date expireTime = calendar.getTime(); // 过期时间

        Map<String, Object> info = new HashMap<>();

        info.put("id", id);
        info.put("userName", userName);
        info.put("uType", uType);

        String jwt = Jwts.builder().setClaims(info)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        return jwt;
    }


    /**
     * 解密jwt
     * @param jwt
     * @return
     */
    public static Claims parseJwt(String jwt){

        Claims claims = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
