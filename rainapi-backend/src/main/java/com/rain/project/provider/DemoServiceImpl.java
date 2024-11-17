package com.rain.project.provider;

import com.rainapi.common.demo.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @PROJECT_NAME : rainapi-backend
 * @PACKAGE_NAME : com.rain.project.provider
 * @NAME : DemoServiceImpl
 * @SITE :
 * @DATE : 2024/11/17
 * @TIME : 21:41
 * @DAY_NAME_FULL : 星期日
 * @AUTHOR : rainlane
 * @Description :
 * @Solution :
 **/
@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name;
    }

    @Override
    public String sayHello2(String name) {
        return "rain";
    }
}
