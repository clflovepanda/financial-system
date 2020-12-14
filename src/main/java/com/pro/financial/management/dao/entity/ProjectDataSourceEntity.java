package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project_data_source")
public class ProjectDataSourceEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String projectId;

    private String dataSourceId;

    private Date ctime;
}