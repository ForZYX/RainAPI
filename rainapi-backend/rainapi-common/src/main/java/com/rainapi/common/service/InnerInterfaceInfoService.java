package com.rainapi.common.service;

import com.rainapi.common.model.entity.InterfaceInfo;

/**
* @author zyx
*/
public interface InnerInterfaceInfoService {

    /**
     * 查询模拟接口是否存在（请求路径，请求方法、请求参数）
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
