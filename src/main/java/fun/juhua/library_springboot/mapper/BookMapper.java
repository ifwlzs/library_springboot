package fun.juhua.library_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.juhua.library_springboot.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMapper extends BaseMapper<Book> {
}
