package com.gxuwz.subject.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team_member")
public class TeamMemberModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 团队成员编号
     */
    @TableId(value = "member_no", type = IdType.AUTO)
    private Integer memberNo;

    /**
     * 团队编号
     */
    private String teamNo;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 年级
     */
    private String grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 手机号码
     */
    private String mobileNumber;


}
