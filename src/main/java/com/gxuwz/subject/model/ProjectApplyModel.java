package com.gxuwz.subject.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.List;

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
@TableName("project_apply")
public class ProjectApplyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    private Integer budgetId;

    private String opinion;

    private String status;

    @TableLogic
    private int deleteFlag;

    @TableField(exist = false)
    private ProjectModel project;

    @TableField(exist = false)
    private BudgetModel budget;

}
