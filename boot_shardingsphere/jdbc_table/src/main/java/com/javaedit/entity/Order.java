package com.javaedit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("tb_order")
public class Order implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 购买数量
     */
    private Integer number;
    /**
     * 创建时间
     */
    private Date createTime;
}
