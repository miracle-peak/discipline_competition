package com.gxuwz.subject.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author: 蔡奇峰
 * date: 2020/5/1 14:04
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("login_log")
public class LoginLogModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String userName;

    private String ip;

    private String loginTime;

    /**
     * 0 登录成功 1登录失败
     */
    private String status;

    private String os;

    private String browser;

    private String msg;
}
