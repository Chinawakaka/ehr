package com.linln.train.admin.train.service;

import com.linln.common.enums.StatusEnum;
import com.linln.train.admin.train.domain.Train;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liangjunhao
 * @date 2020/03/31
 */
public interface TrainService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Train> getPageList(Example<Train> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Train getById(Long id);

    /**
     * 保存数据
     * @param train 实体对象
     */
    Train save(Train train);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}