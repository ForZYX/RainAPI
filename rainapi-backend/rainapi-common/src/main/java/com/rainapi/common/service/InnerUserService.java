package com.rainapi.common.service;

import com.rainapi.common.model.entity.User;

/**
 * 用户服务
 *
 * @author rain
 */
public interface InnerUserService {

    /**
     * 查询是否分配给用户密钥
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
