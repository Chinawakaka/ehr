package com.linln.admin.attence.controller;

import com.linln.admin.attence.domain.Attence;
import com.linln.admin.attence.service.AttenceService;
import com.linln.admin.attence.validator.AttenceValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
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
@RequestMapping("/attence/attence")
public class AttenceController {

    @Autowired
    private AttenceService attenceService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("attence:attence:index")
    public String index(Model model, Attence attence) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<Attence> example = Example.of(attence, matcher);
        Page<Attence> list = attenceService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/attence/attence/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("attence:attence:add")
    public String toAdd() {
        return "/attence/attence/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("attence:attence:edit")
    public String toEdit(@PathVariable("id") Attence attence, Model model) {
        model.addAttribute("attence", attence);
        return "/attence/attence/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"attence:attence:add", "attence:attence:edit"})
    @ResponseBody
    public ResultVo save(@Validated AttenceValid valid, Attence attence) {
        // 复制保留无需修改的数据
        if (attence.getId() != null) {
            Attence beAttence = attenceService.getById(attence.getId());
            EntityBeanUtil.copyProperties(beAttence, attence);
        }

        // 保存数据
        attenceService.save(attence);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("attence:attence:detail")
    public String toDetail(@PathVariable("id") Attence attence, Model model) {
        model.addAttribute("attence",attence);
        return "/attence/attence/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("attence:attence:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (attenceService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}