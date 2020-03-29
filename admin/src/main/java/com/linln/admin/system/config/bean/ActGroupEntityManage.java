package com.linln.admin.system.config.bean;

import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityManagerImpl;
import org.flowable.idm.engine.impl.persistence.entity.data.GroupDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 20014915
 * @create: 2019-06-13
 * @description: 重写flowable组接口，避开flowble的idm组验证
 **/
public class ActGroupEntityManage  {
//    extends GroupEntityManagerImpl
//    private static Logger logger = LoggerFactory.getLogger(ActGroupEntityManage.class);
//    private SysService sysService;
//
//    public void setSysService(SysService sysService) {
//        this.sysService = sysService;
//    }
//
//    public ActGroupEntityManage(IdmEngineConfiguration idmEngineConfiguration, GroupDataManager groupDataManager) {
//        super(idmEngineConfiguration, groupDataManager);
//    }
//
//    @Override
//    public List<Group> findGroupByQueryCriteria(GroupQueryImpl groupQuery) {
//
//        //TODO 接入系统自身的角色关系
//        User user = new User();
//        Set<Role> roles = user.getRoles();
//
//        userAndRole.setUserId(groupQuery.getUserId());
//        RtnMessage<PageInfo<UserAndRole>> pageInfoRtnMessage = sysService.loadAllUserAndRole(userAndRole);
//        PageInfo<UserAndRole> data = pageInfoRtnMessage.getData();
//
//        List<Group> groups = new ArrayList<>();
//        //将自身的role对象转换为flowable的group对象
//        for (UserAndRole relation : data.getList()) {
//            Role role = sysServiceClient.queryRole(relation.getRoleId()).getData();
//            if(role == null){
//                logger.error("findGroupByQueryCriteria找不到角色：{}", JSON.toJSONString(relation));
//                continue;
//            }
//            Group group = new GroupEntityImpl();
//            group.setId(role.getEname());
//            group.setName(role.getName());
//            group.setType(role.getRoleType());
//            groups.add(group);
//        }
//        return groups;
//    }

}
