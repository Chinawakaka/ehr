package com.linln.admin.reward.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.admin.reward.domain.Reward;
import com.linln.admin.reward.repository.RewardRepository;
import com.linln.admin.reward.service.RewardService;
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
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Reward getById(Long id) {
        return rewardRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Reward> getPageList(Example<Reward> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return rewardRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param reward 实体对象
     */
    @Override
    public Reward save(Reward reward) {
        return rewardRepository.save(reward);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return rewardRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}