package com.linln.admin.rewardSystem.service;

import com.linln.common.enums.StatusEnum;
import com.linln.admin.rewardSystem.domain.RewardSystem;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liangjunhao
 * @date 2020/03/29
 */
public interface RewardSystemService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<RewardSystem> getPageList(Example<RewardSystem> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    RewardSystem getById(Long id);

    /**
     * 保存数据
     * @param rewardSystem 实体对象
     */
    RewardSystem save(RewardSystem rewardSystem);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}