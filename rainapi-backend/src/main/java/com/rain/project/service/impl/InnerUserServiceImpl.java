package com.rain.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rain.project.common.ErrorCode;
import com.rain.project.exception.BusinessException;
import com.rain.project.mapper.UserMapper;
import com.rainapi.common.model.entity.User;
import com.rainapi.common.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @PROJECT_NAME : rainapi-backend
 * @PACKAGE_NAME : com.rain.project.service.impl
 * @NAME : InnerUserServiceImpl
 * @SITE :
 * @DATE : 2024/11/15
 * @TIME : 23:42
 * @DAY_NAME_FULL : 星期五
 * @AUTHOR : rainlane
 * @Description :
 * @Solution :
 **/
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据公钥获取内部用户信息
     *
     * @param accessKey 公钥
     * @return 用户信息，找不到返回null
     */
    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("accessKey", accessKey);

        return userMapper.selectOne(QueryWrapper);
    }
}
