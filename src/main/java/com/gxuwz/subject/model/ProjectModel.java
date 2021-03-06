package com.gxuwz.subject.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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
@TableName("sys_project")
public class ProjectModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;

    private String name;

    private String organization;

    private String format;

    private String teacherId;

//    @Size(min = 11, max = 11)
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String mail;

    private String startDate;

    private String endDate;

    private String major;

    private String sponsor;

    private String contractor;

    private String projectDate;

    private String objective;

    private String invitation;

    private ProjectApplyModel projectApply;


}
