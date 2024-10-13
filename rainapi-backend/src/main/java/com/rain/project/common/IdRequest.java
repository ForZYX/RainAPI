package com.rain.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求Id
 *
 * @author rain
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}