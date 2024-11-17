package com.rainapi.rainapigateway;

import com.rainapi.common.model.entity.InterfaceInfo;
import com.rainapi.common.model.entity.User;
import com.rainapi.common.service.InnerInterfaceInfoService;
import com.rainapi.common.service.InnerUserInterfaceInfoService;
import com.rainapi.common.service.InnerUserService;
import com.rainapi.rainapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @PROJECT_NAME : rainapi-gateway
 * @PACKAGE_NAME : com.rainapi.rainapigateway
 * @NAME : CustomGlobalFilter
 * @SITE :
 * @DATE : 2024/10/24
 * @TIME : 00:33
 * @DAY_NAME_FULL : 星期四
 * @AUTHOR : rainlane
 * @Description :
 * @Solution :
 **/
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    // RPC
    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    // test
    private static final String INTERFACE_HOST = "http://127.0.0.1:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        /**
//         * 1. 用户请求发送到API网关
//         * 2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        System.out.println("请求唯一标识："+request.getId());
        String path = INTERFACE_HOST + request.getPath().value();
        System.out.println("请求路径：" + path);
        String method = request.getMethod().toString();
        System.out.println("请求方法：" + method);
        System.out.println("请求参数：" + request.getQueryParams());
//        System.out.println("请求头："+request.getHeaders());
//        System.out.println("请求cookirs："+request.getCookies());
        System.out.println("请求来源地址：" + request.getRemoteAddress());
        String hostString = request.getLocalAddress().getHostString();
        System.out.println("请求来源地址：" + hostString);
//         * 3. 黑白日志
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(hostString)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
//         * 4. 用户鉴权
        HttpHeaders headers = request.getHeaders();
        // 从请求头中获取参数
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");

        // 去数据库中查是否已分配给用户
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            log.error("getInvokeUser error", e.getMessage());
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }
//        if (!accessKey.equals("8f7e6af6b50ae4c110d202a7803c147b")){
//            return handleNoAuth(response);
//        }

        // 直接校验如果随机数大于1万，则抛出异常，并提示"无权限"
        if (Long.parseLong(nonce) > 10000) {
            return handleNoAuth(response);
        }

        // 时间和当前时间不能超过5分钟
        // System.currentTimeMillis()返回当前时间的毫秒数，除以1000后得到当前时间的秒数。
        Long currentTime = System.currentTimeMillis() / 1000;
        // 定义一个常量FIVE_MINUTES,表示五分钟的时间间隔(乘以60,将分钟转换为秒,得到五分钟的时间间隔)。
        final Long FIVE_MINUTES = 60 * 5L;
        // 判断当前时间与传入的时间戳是否相差五分钟或以上
        // Long.parseLong(timestamp)将传入的时间戳转换成长整型
        // 然后计算当前时间与传入时间戳之间的差值(以秒为单位),如果差值大于等于五分钟,则返回true,否则返回false
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            // 如果时间戳与当前时间相差五分钟或以上，调用handleNoAuth(response)方法进行处理
            return handleNoAuth(response);
        }

        //concat parameters
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 生成随机数(生成一个包含100个随机数字的字符串)
        hashMap.put("nonce", nonce);
        // 请求体内容
        hashMap.put("body", body);
        // 当前时间戳
        // System.currentTimeMillis()返回当前时间的毫秒数。通过除以1000，可以将毫秒数转换为秒数，以得到当前时间戳的秒级表示
        // String.valueOf()方法用于将数值转换为字符串。在这里，将计算得到的时间戳（以秒为单位）转换为字符串
        hashMap.put("timestamp", timestamp);

        // 从数据库中查出 secretKey
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.genSign(hashMap, secretKey);
        // 如果生成的签名不一致，则抛出异常，并提示"无权限"
        if (sign == null || !sign.equals(serverSign)) {
            return handleNoAuth(response);
        }

//         * 5. 请求的模拟接口是否存在？
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }
        // check if get interface info
        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }

        // todo: 检查用户是否还有调用次数？


//         * 6. 请求转发，调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
//         */
    }


    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理来说,现在没有调用,是拿不到响应码的,对这个保持怀疑 沉思.jpg)
            if(statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(装饰，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理

                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体(这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            // 往返回值里拼接，写数据
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        //         * 8. 调用成功，接口调用次数 + 1
                                        // 调用次数 + 1
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        }catch (Exception e){
                                            log.error("invokeCount error", e);
                                        }

                                        // 读取响应体的内容并转换为字节数组
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        //rspArgs.add(requestUrl);
                                        String data = new String(content, StandardCharsets.UTF_8);//data
                                        sb2.append(data);

                                        //         * 7. 响应日志
                                        log.info("响应： " + originalResponse.getStatusCode());
                                        log.info("响应结果： " + data);

        //                                log.info(sb2.toString(), rspArgs.toArray());//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                        // 将处理后的内容重新包装成DataBuffer并返回
                                        return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        }catch (Exception e){
            // 处理异常情况，记录错误日志
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    /**
     * 该方法定义了多个中每个过滤器的执行顺序
     * @return order
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
