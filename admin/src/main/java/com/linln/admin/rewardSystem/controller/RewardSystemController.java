package com.linln.admin.rewardSystem.controller;

import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.admin.rewardSystem.domain.RewardSystem;
import com.linln.admin.rewardSystem.service.RewardSystemService;
import com.linln.admin.rewardSystem.validator.RewardSystemValid;
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
@RequestMapping("/rewardSystem/rewardSystem")
public class RewardSystemController {

    @Autowired
    private RewardSystemService rewardSystemService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("rewardSystem:rewardSystem:index")
    public String index(Model model, RewardSystem rewardSystem) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("systemName", match -> match.contains());

        // 获取数据列表
        Example<RewardSystem> example = Example.of(rewardSystem, matcher);
        Page<RewardSystem> list = rewardSystemService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/rewardSystem/rewardSystem/index";
    }

    /**
     * 获取所有奖惩制度列表
     * @return
     */
    @GetMapping("/findRewardSystemList")
    @ResponseBody
    public List<RewardSystem> findRewardSystemList(){

        return rewardSystemService.findRewardSystemList();
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("rewardSystem:rewardSystem:add")
    public String toAdd() {
        return "/rewardSystem/rewardSystem/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("rewardSystem:rewardSystem:edit")
    public String toEdit(@PathVariable("id") RewardSystem rewardSystem, Model model) {
        model.addAttribute("rewardSystem", rewardSystem);
        return "/rewardSystem/rewardSystem/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"rewardSystem:rewardSystem:add", "rewardSystem:rewardSystem:edit"})
    @ResponseBody
    public ResultVo save(@Validated RewardSystemValid valid, RewardSystem rewardSystem) {
        // 复制保留无需修改的数据
        if (rewardSystem.getId() != null) {
            RewardSystem beRewardSystem = rewardSystemService.getById(rewardSystem.getId());
            EntityBeanUtil.copyProperties(beRewardSystem, rewardSystem);
        }

        // 保存数据
        rewardSystemService.save(rewardSystem);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("rewardSystem:rewardSystem:detail")
    public String toDetail(@PathVariable("id") RewardSystem rewardSystem, Model model) {
        model.addAttribute("rewardSystem",rewardSystem);
        return "/rewardSystem/rewardSystem/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("rewardSystem:rewardSystem:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (rewardSystemService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}