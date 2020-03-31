package com.linln.admin.wage.service.impl;

import com.linln.admin.wage.domain.Wage;
import com.linln.admin.wage.repository.WageRepository;
import com.linln.admin.wage.service.WageService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liangjunhao
 * @date 2020/03/28
 */
@Service
public class WageServiceImpl implements WageService {

    @Autowired
    private WageRepository wageRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Wage getById(Long id) {
        return wageRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Wage> getPageList(Example<Wage> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return wageRepository.findAll(example, page);
    }

    @Override
    public List<Wage> getList() {
        return wageRepository.findAll();
    }

    /**
     * 保存数据
     * @param wage 实体对象
     */
    @Override
    public Wage save(Wage wage) {
        return wageRepository.save(wage);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return wageRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}