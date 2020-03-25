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
@TableName("sys_budget")
public class BudgetModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "budget_id", type = IdType.AUTO)
    private Integer budgetId;

    private Integer enroll;

    private Integer travel;

    private Integer guide;

    private Integer bonus;

    private Integer consume;

    private Integer other;

    private Integer sum;

    private Integer train;


}
