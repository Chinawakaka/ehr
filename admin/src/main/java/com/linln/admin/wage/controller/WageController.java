package com.linln.admin.wage.controller;

import com.linln.admin.wage.domain.Wage;
import com.linln.admin.wage.repository.WageRepository;
import com.linln.admin.wage.service.WageService;
import com.linln.admin.wage.validator.WageValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.SpringContextUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.excel.ExcelUtil;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.sys.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liangjunhao
 * @date 2020/03/28
 */
@Controller
@RequestMapping("/wage/wage")
public class WageController {

    @Autowired
    private WageService wageService;
    @Autowired
    private UserService userService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("wage:wage:index")
    public String index(Model model, Wage wage) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("userName", match -> match.contains());

        // 获取数据列表
        Example<Wage> example = Example.of(wage, matcher);
        Page<Wage> list = wageService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/wage/wage/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("wage:wage:add")
    public String toAdd(Model model) {
        return "/wage/wage/add";
    }

    @GetMapping("/findUserList")
    @ResponseBody
    public List<User> findUserList(){
        List<User> list = userService.getList();
        return list;
    }

    /**
     * 导出用户数据
     */
    @GetMapping("/export")
    @RequiresPermissions("system:user:export")
    @ResponseBody
    public void exportExcel() {
        WageRepository wageRepository = SpringContextUtil.getBean(WageRepository.class);
        ExcelUtil.exportExcel(Wage.class, wageRepository.findAll());
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("wage:wage:edit")
    public String toEdit(@PathVariable("id") Wage wage, Model model) {
        model.addAttribute("wage", wage);
        return "/wage/wage/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"wage:wage:add", "wage:wage:edit"})
    @ResponseBody
    public ResultVo save(@Validated WageValid valid, Wage wage) {
        // 复制保留无需修改的数据
        if (wage.getId() != null) {
            Wage beWage = wageService.getById(wage.getId());
            EntityBeanUtil.copyProperties(beWage, wage);
        }
        //计算总计金额
        BigDecimal total = wage.getWageBase().add(wage.getRiceWage()).add(wage.getHouseWage()).add(wage.getPerfectWage())
                .add(wage.getAddedWage()).subtract(wage.getRevenueWage()).subtract(wage.getPenaltyWage());
        wage.setTotalWage(total);

        // 保存数据
        wageService.save(wage);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("wage:wage:detail")
    public String toDetail(@PathVariable("id") Wage wage, Model model) {
        model.addAttribute("wage",wage);
        return "/wage/wage/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("wage:wage:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (wageService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}