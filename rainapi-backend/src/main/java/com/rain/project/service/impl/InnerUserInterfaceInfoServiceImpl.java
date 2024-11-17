package com.rain.project.service.impl;

import com.rain.project.service.UserInterfaceInfoService;
import com.rainapi.common.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @PROJECT_NAME : rainapi-backend
 * @PACKAGE_NAME : com.rain.project.service.impl
 * @NAME : InnerUserInterfaceInfoServiceImpl
 * @SITE :
 * @DATE : 2024/11/15
 * @TIME : 23:38
 * @DAY_NAME_FULL : 星期五
 * @AUTHOR : rainlane
 * @Description :
 * @Solution :
 **/
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceId, long userId) {
        // 调用service的方法
        return userInterfaceInfoService.invokeCount(interfaceId, userId);
    }
}
