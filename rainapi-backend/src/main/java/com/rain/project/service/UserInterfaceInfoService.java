package com.rain.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainapi.common.model.entity.UserInterfaceInfo;

/**
* @author zyx
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-10-21 23:23:14
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceId, long userId);
}
