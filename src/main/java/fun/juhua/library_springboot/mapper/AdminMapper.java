package fun.juhua.library_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.juhua.library_springboot.entity.Admin;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

//在对应的Mapper.上面继承基本的类BaseMapper

//@Repository 代表持久层
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
//    //增加管理员
//
//    //删除管理员
//
//    //通过id删除管理员
//
//    //更新管理员信息
//
    //查询管理员
//
    //通过id查询管理员
//
    //通过用户名查询管理员

//    //根据账密查询

}
