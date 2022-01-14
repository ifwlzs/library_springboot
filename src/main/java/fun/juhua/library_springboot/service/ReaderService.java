package fun.juhua.library_springboot.service;

import fun.juhua.library_springboot.entity.Reader;

import java.util.List;
import java.util.Map;

public interface ReaderService {
    List<Reader> getReaderList(String name, String id);

    boolean login(String id, String password);

    Reader findReader(String id);

    boolean haveReader(String id);

    int addReader(Reader reader);

    int updateReader(Reader reader);

    int deleteAdminById(String id);
}
