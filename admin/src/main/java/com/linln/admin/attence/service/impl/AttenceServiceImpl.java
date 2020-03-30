package com.linln.admin.attence.service.impl;

import com.linln.admin.attence.domain.Attence;
import com.linln.admin.attence.repository.AttenceRepository;
import com.linln.admin.attence.service.AttenceService;
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
 * @date 2020/03/29
 */
@Service
public class AttenceServiceImpl implements AttenceService {

    @Autowired
    private AttenceRepository attenceRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Attence getById(Long id) {
        return attenceRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Attence> getPageList(Example<Attence> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return attenceRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param attence 实体对象
     */
    @Override
    public Attence save(Attence attence) {
        return attenceRepository.save(attence);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return attenceRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}