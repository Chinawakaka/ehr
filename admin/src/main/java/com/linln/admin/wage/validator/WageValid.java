package com.linln.admin.wage.validator;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author liangjunhao
 * @date 2020/03/28
 */
@Data
public class WageValid implements Serializable {
    @NotEmpty(message = "用户名称不能为空")
    private String userName;
    @NotNull(message = "基本薪金不能为空")
    private BigDecimal wageBase;
    @NotNull(message = "饭补不能为空")
    private BigDecimal riceWage;
    @NotNull(message = "房补不能为空")
    private BigDecimal houseWage;
    @NotNull(message = "全勤奖不能为空")
    private BigDecimal perfectWage;
    @NotNull(message = "赋税不能为空")
    private BigDecimal revenueWage;
    @NotNull(message = "额外补助不能为空")
    private BigDecimal addedWage;
    @NotNull(message = "罚款不能为空")
    private BigDecimal penaltyWage;
}