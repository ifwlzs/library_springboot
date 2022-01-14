package fun.juhua.library_springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.juhua.library_springboot.entity.Reader;
import fun.juhua.library_springboot.mapper.ReaderMapper;
import fun.juhua.library_springboot.service.ReaderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Resource
    ReaderMapper readerMapper;

    @Override
    public List<Reader> getReaderList(String name, String id) {
        List<Reader> readerList = null;
        QueryWrapper<Reader> wrapper = new QueryWrapper<>();
        if ((name == null || name == "") && (id == null || id == "")) {
            readerList = readerMapper.selectList(null);
        } else if ((id == null || id == "")) {
            readerList = readerMapper.selectList(wrapper.like("name", name));
        } else {
            readerList = readerMapper.selectList(wrapper.eq("id", id));
        }
        return readerList;
    }

    @Override
    public boolean login(String id, String password) {
        Reader reader = findReader(id);
        if (reader == null || !reader.getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    @Override
    public Reader findReader(String id) {
        QueryWrapper<Reader> wrapper = new QueryWrapper<>();
        return readerMapper.selectOne(wrapper.eq("id", id));
    }

    @Override
    public boolean haveReader(String id) {
        QueryWrapper<Reader> wrapper = new QueryWrapper<>();
        return readerMapper.selectOne(wrapper.eq("id", id)) != null;
    }

    @Override
    public int addReader(Reader reader) {
        return readerMapper.insert(reader);
    }

    @Override
    public int updateReader(Reader reader) {
        return readerMapper.updateById(reader);
    }

    @Override
    public int deleteAdminById(String id) {
        return readerMapper.deleteById(id);
    }
}
