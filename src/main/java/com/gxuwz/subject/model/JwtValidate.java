package com.gxuwz.subject.model;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * @author: 蔡奇峰
 * date: 2020/4/24 16:09
 **/
@Data
public class JwtValidate {

    private int errCode;

    private boolean success;

    private Claims claims;


}
