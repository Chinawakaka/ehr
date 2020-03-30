package com.linln.admin.rewardSystem.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.admin.rewardSystem.domain.RewardSystem;
import com.linln.admin.rewardSystem.repository.RewardSystemRepository;
import com.linln.admin.rewardSystem.service.RewardSystemService;
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
public class RewardSystemServiceImpl implements RewardSystemService {

    @Autowired
    private RewardSystemRepository rewardSystemRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public RewardSystem getById(Long id) {
        return rewardSystemRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<RewardSystem> getPageList(Example<RewardSystem> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return rewardSystemRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param rewardSystem 实体对象
     */
    @Override
    public RewardSystem save(RewardSystem rewardSystem) {
        return rewardSystemRepository.save(rewardSystem);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return rewardSystemRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}