package com.linln.admin.reward.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author liangjunhao
 * @date 2020/03/29
 */
@Data
public class RewardValid implements Serializable {
    @NotEmpty(message = "奖惩人不能为空")
    private String rewardUserName;
    @NotEmpty(message = "奖惩名称不能为空")
    private String rewardName;
    @NotEmpty(message = "奖惩原因不能为空")
    private String rewardReason;
}