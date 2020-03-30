package com.linln.admin.reward.service;

import com.linln.common.enums.StatusEnum;
import com.linln.admin.reward.domain.Reward;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liangjunhao
 * @date 2020/03/29
 */
public interface RewardService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Reward> getPageList(Example<Reward> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Reward getById(Long id);

    /**
     * 保存数据
     * @param reward 实体对象
     */
    Reward save(Reward reward);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}