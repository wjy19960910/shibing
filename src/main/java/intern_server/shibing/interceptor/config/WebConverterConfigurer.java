package intern_server.shibing.interceptor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import intern_server.shibing.config.JacksonConfig;
import intern_server.shibing.interceptor.AuthenticationInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Configuration
public class WebConverterConfigurer implements WebMvcConfigurer {


    @Bean
    @Primary
    public static ObjectMapper jacksonObjectMapper() {
        return JacksonConfig.jacksonObjectMapper();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {


        // 注册时间反序列化转换器，时间戳字符串转换为LocalDateTime类型
        registry.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return StringUtils.isBlank(source) ? null : new Timestamp(Long.valueOf(source)).toLocalDateTime().toLocalDate();
            }
        });

        // 注册时间反序列化转换器，时间戳字符串转换为LocalDateTime类型
        registry.addConverter(new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return StringUtils.isBlank(source) ? null : new Timestamp(Long.valueOf(source)).toLocalDateTime();
            }
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor( AuthorityInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public AuthenticationInterceptor AuthorityInterceptor() {
        return new AuthenticationInterceptor();
    }
}