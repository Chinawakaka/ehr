package com.linln.admin.train.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.admin.train.domain.Train;
import com.linln.admin.train.repository.TrainRepository;
import com.linln.admin.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liangjunhao
 * @date 2020/03/31
 */
@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainRepository trainRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Train getById(Long id) {
        return trainRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Train> getPageList(Example<Train> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return trainRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param train 实体对象
     */
    @Override
    public Train save(Train train) {
        return trainRepository.save(train);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return trainRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}