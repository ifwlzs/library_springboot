package fun.juhua.library_springboot.config;

import fun.juhua.library_springboot.interceptor.ReaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ReaderWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReaderInterceptor())
                .addPathPatterns("/admin/**");
    }
}
