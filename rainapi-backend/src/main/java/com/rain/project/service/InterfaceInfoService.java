package com.rain.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainapi.common.model.entity.InterfaceInfo;

/**
* @author zyx
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-10-03 21:40:28
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo post, boolean add);
}
