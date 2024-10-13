package com.rainapi.rainapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.Map;

/**
 * 签名工具类
 **/
public class SignUtils {
    /**
     * 生成签名
     * @param hashMap 加密的参数
     * @param secretKey 密钥
     * @return 签名字符串
     */
    public static String genSign(Map<String, String> hashMap, String secretKey) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = hashMap.get("body") + "." + secretKey;
        return digester.digestHex(content);
    }
}
