package com.ysx.common.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "ysx.auth")
@Component
@Data
public class JwtAuthProperties {

    private String secretKey;

    private String tokenHeader;

    private Long ttlMillis;
}
