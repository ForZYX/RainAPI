package com.rain.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rain.project.common.ErrorCode;
import com.rain.project.exception.BusinessException;
import com.rain.project.mapper.InterfaceInfoMapper;
import com.rainapi.common.model.entity.InterfaceInfo;
import com.rainapi.common.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @PROJECT_NAME : rainapi-backend
 * @PACKAGE_NAME : com.rain.project.service.impl
 * @NAME : InnerInterfaceInfoServiceImpl
 * @SITE :
 * @DATE : 2024/11/15
 * @TIME : 23:35
 * @DAY_NAME_FULL : 星期五
 * @AUTHOR : rainlane
 * @Description :
 * @Solution :
 **/
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);

        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
