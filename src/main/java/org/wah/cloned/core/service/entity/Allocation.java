package org.wah.cloned.core.service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Data
@NoArgsConstructor
public class Allocation extends Entity implements Createable, Updateable{

    //客服
    private Service service;
    //概率
    private Double probability;
    //默认概率
    private Double defaultProbability;
    //步长
    private Double step;
    private Date createTime;
    private Date updateTime;
}
