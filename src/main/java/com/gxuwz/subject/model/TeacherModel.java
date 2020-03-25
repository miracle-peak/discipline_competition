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
@TableName("sys_teacher")
public class TeacherModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师工号
     */
    @TableId(value = "teacher_id", type = IdType.ID_WORKER)
    private String teacherId;

    /**
     * 账号
     */
    private String accountNum;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 二级学院编号
     */
    private Integer collegeNo;


}
