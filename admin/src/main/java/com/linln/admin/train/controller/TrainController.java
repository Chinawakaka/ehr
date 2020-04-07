package com.linln.admin.train.controller;

import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.admin.train.domain.Train;
import com.linln.admin.train.service.TrainService;
import com.linln.admin.train.validator.TrainValid;
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
 * @date 2020/03/31
 */
@Controller
@RequestMapping("/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("train:index")
    public String index(Model model, Train train) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("trainName", match -> match.contains())
                .withMatcher("hostName", match -> match.contains());

        // 获取数据列表
        Example<Train> example = Example.of(train, matcher);
        Page<Train> list = trainService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/train/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("train:add")
    public String toAdd() {
        return "/train/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("train:edit")
    public String toEdit(@PathVariable("id") Train train, Model model) {
        model.addAttribute("train", train);
        return "/train/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"train:add", "train:edit"})
    @ResponseBody
    public ResultVo save(@Validated TrainValid valid, Train train) {
        // 复制保留无需修改的数据
        if (train.getId() != null) {
            Train beTrain = trainService.getById(train.getId());
            EntityBeanUtil.copyProperties(beTrain, train);
        }

        // 保存数据
        trainService.save(train);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("train:detail")
    public String toDetail(@PathVariable("id") Train train, Model model) {
        model.addAttribute("train",train);
        return "/train/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("train:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (trainService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}