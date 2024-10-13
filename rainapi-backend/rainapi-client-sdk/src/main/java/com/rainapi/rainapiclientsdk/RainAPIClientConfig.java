package com.rainapi.rainapiclientsdk;

import com.rainapi.rainapiclientsdk.client.RainClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @PROJECT_NAME : rainapi-client-sdk
 * @PACKAGE_NAME : com.rainapi.rainapiclientsdk
 * @NAME : RainAPIClientConfig
 * @SITE :
 * @DATE : 2024/10/12
 * @TIME : 21:21
 * @DAY_NAME_FULL : 星期六
 * @AUTHOR : rainlane
 * @Description :
 * @Solution :
 **/
// 通过该注解，将该类标记为一个配置类，使得Spring可以识别这个配置类
@Configuration
// 通过该注解，可以读取配置文件application.yml后将读到的配置设置到我们这里的属性中
@ConfigurationProperties("rainapi.client")
@Data
// 通过该注解使得Spring能够自动自动扫描组件并注册相应的Bean
@ComponentScan
public class RainAPIClientConfig {
    private String accessKey;

    private String secretKey;

    @Bean
    public RainClient getRainClient() {
        return new RainClient(accessKey, secretKey);
    }
}
