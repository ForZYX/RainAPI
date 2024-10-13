package com.rainapi.rainapiinterface.controller;

import com.rainapi.rainapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.rainapi.rainapiclientsdk.utils.SignUtils.genSign;


@RestController
@RequestMapping("name")
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
     */
    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        //todo: access database


        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");

        //todo: should check database right
        if (!accessKey.equals("rain")) {
            throw new RuntimeException("No right to access!");
        }

        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("No right to access!");
        }

        //todo: check timestamp

        //todo: check sign
        //concat parameters
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 注意：不能直接发送密钥
        // hashMap.put("secretKey", secretKey);
        // 生成随机数(生成一个包含100个随机数字的字符串)
        hashMap.put("nonce", nonce);
        // 请求体内容
        hashMap.put("body", body);
        // 当前时间戳
        // System.currentTimeMillis()返回当前时间的毫秒数。通过除以1000，可以将毫秒数转换为秒数，以得到当前时间戳的秒级表示
        // String.valueOf()方法用于将数值转换为字符串。在这里，将计算得到的时间戳（以秒为单位）转换为字符串
        hashMap.put("timestamp", timestamp);

        //todo: access database to get secretKey
        String sign_assure = genSign(hashMap, "12345678");

        if (!sign.equals(sign_assure)) {
            throw new RuntimeException("No right to access!");
        }

        return "POST 用户名字是" + user.getUsername();
    }

}
