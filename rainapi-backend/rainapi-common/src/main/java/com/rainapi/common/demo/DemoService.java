package com.rainapi.common.demo;

import java.util.concurrent.CompletableFuture;

/**
 * @PROJECT_NAME : rainapi-common
 * @PACKAGE_NAME : com.rainapi.common.demo
 * @NAME : DemoService
 * @DATE : 2024/11/17
 * @TIME : 23:26
 * @DAY_NAME_FULL : 星期日
 * @AUTHOR : rainlane
 **/
public interface DemoService {

    String sayHello(String name);

    String sayHello2(String name);

    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }

}
