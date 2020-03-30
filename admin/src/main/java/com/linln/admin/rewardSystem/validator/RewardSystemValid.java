package com.linln.admin.rewardSystem.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author liangjunhao
 * @date 2020/03/29
 */
@Data
public class RewardSystemValid implements Serializable {
    @NotEmpty(message = "制度名称不能为空")
    private String systemName;
    @NotEmpty(message = "制度原因不能为空")
    private String systemReason;
}