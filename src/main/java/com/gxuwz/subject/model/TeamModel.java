package com.gxuwz.subject.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("sys_team")
public class TeamModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "team_no", type = IdType.ID_WORKER)
    private String teamNo;

    private String teamName;

    private String title;

    private String projectId;

    private Date enrollTime;

    private String teacherId;

    @TableLogic
    private Integer delete_flag;

    private List<TeamMemberModel> teamMember;

}
