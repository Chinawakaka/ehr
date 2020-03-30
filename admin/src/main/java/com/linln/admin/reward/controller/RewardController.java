package com.linln.admin.reward.controller;

import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.admin.reward.domain.Reward;
import com.linln.admin.reward.service.RewardService;
import com.linln.admin.reward.validator.RewardValid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liangjunhao
 * @date 2020/03/29
 */
@Controller
@RequestMapping("/attence/reward")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("attence:reward:index")
    public String index(Model model, Reward reward) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("rewardUserName", match -> match.contains())
                .withMatcher("rewardName", match -> match.contains());

        // 获取数据列表
        Example<Reward> example = Example.of(reward, matcher);
        Page<Reward> list = rewardService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/attence/reward/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("attence:reward:add")
    public String toAdd() {
        return "/attence/reward/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("attence:reward:edit")
    public String toEdit(@PathVariable("id") Reward reward, Model model) {
        model.addAttribute("reward", reward);
        return "/attence/reward/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"attence:reward:add", "attence:reward:edit"})
    @ResponseBody
    public ResultVo save(@Validated RewardValid valid, Reward reward) {
        // 复制保留无需修改的数据
        if (reward.getId() != null) {
            Reward beReward = rewardService.getById(reward.getId());
            EntityBeanUtil.copyProperties(beReward, reward);
        }

        // 保存数据
        rewardService.save(reward);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("attence:reward:detail")
    public String toDetail(@PathVariable("id") Reward reward, Model model) {
        model.addAttribute("reward",reward);
        return "/attence/reward/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("attence:reward:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (rewardService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}