package com.rainapi.common.service;

/**
* @author zyx
*/
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口
     * @param interfaceId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceId, long userId);
}
