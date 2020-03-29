package com.linln.admin.wage.service;

import com.linln.admin.wage.domain.Wage;
import com.linln.common.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liangjunhao
 * @date 2020/03/28
 */
public interface WageService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Wage> getPageList(Example<Wage> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Wage getById(Long id);

    /**
     * 保存数据
     * @param wage 实体对象
     */
    Wage save(Wage wage);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}