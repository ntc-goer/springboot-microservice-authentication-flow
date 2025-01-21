package ntcgoer.sharingmodule.configuration;

import feign.codec.ErrorDecoder;
import ntcgoer.sharingmodule.http.FeignSafeExecutor;
import ntcgoer.sharingmodule.util.DateUtil;
import ntcgoer.sharingmodule.util.HashUtil;
import ntcgoer.sharingmodule.util.RedisUtil;
import ntcgoer.sharingmodule.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class SharingConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DateUtil dateUtil() {
        return new DateUtil();
    }

    @Bean
    public HashUtil hashUtil() {
        return new HashUtil();
    }

    @Bean
    public StringUtil stringUtil() {
        return new StringUtil();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public FeignSafeExecutor httpUtil() {
        return new FeignSafeExecutor();
    }

    @Bean
    @Lazy
    public RedisUtil redisUtil(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisUtil(redisTemplate);
    }
}
