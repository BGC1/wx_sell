package com.example.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@Table(name="product_info") //按照了去掉下划线首字母大写的规则 就可以不用指定
public class ProductInfo {

    @Id
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常 1下架. */
    private Integer productStatus;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;


}
