package com.rain.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;


    private static final long serialVersionUID = 1L;
}