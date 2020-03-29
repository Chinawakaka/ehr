package com.linln.admin.system.config.flowable;

import com.linln.admin.system.config.bean.ActGroupEntityManage;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 20014915
 * @create: 2019-06-13
 * @description: 重写idm的组验证，接到自身系统的角色管理中
 **/
@Configuration
public class FlowableIdemConfig  {
//    @Bean
//    public GroupEntityManager groupEntityManager(IdmEngineConfiguration idmEngineConfiguration, SysService sysService){
//        ActGroupEntityManage actGroupEntityManage = new ActGroupEntityManage(idmEngineConfiguration, idmEngineConfiguration.getGroupDataManager());
//
//        //填充用户角色关系service和角色service
//        actGroupEntityManage.setSysService(sysService);
//        idmEngineConfiguration.setGroupEntityManager(actGroupEntityManage);
//        return actGroupEntityManage;
//    }
}
