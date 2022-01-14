package fun.juhua.library_springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("fun.juhua.library_springboot.mapper")
public class LibrarySpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibrarySpringbootApplication.class, args);
    }

}
