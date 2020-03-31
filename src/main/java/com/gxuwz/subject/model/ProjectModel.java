package com.gxuwz.subject.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
@TableName("sys_project")
public class ProjectModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;

    private String name;

    private String organization;

    private String format;

    private String teacherId;

    private String phone;

    private String mail;

    private Date startDate;

    private Date endDate;

    private String major;

    private String sponsor;

    private String contractor;

    private Date projectDate;

    private String objective;

    private String invitation;

    private ProjectApplyModel projectApply;


}
