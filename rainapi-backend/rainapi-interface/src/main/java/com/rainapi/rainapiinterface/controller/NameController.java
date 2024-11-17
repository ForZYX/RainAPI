package com.rainapi.rainapiinterface.controller;

import com.rainapi.rainapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(String name) {
        return "GET 你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是" + name;
    }

    /**
     * API鉴权
     *      防重放：时间戳验证，不能跨度太大；以及记录随机数，短时间内发来的随机数不能重复
     *      防泄密：不直接传递密钥，传递参数+密钥后加密的秘文数据
     * 已放置到网关统一处理✌️
     */
    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {

        String result = "POST 用户名字是" + user.getUsername();
        return result;
    }

}
