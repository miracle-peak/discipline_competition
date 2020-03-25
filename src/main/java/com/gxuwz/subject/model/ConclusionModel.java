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
@TableName("sys_conclusion")
public class ConclusionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "conclusion_id", type = IdType.AUTO)
    private Integer conclusionId;

    private Integer prizeId;

    private Integer capitalId;

    private String teamNo;

    private String status;

    private String file;

    private String opinion;


}
