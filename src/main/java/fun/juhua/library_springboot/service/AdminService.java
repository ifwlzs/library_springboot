package fun.juhua.library_springboot.service;

import fun.juhua.library_springboot.entity.Admin;

import java.util.List;

public interface AdminService {
    /**
     * 查询admin列表，如果有名，根据名模糊查询，否则返回全部admin用户
     *
     * @param name
     * @return List<Admin>
     */
    List<Admin> getAdminList(String name);

    int deleteAdminById(String id);

    boolean login(String id, String password);

    Admin findAdmin(String id);

    int updateAdmin(Admin admin);

    int addAdmin(Admin admin);

    boolean haveAdmin(String id);
}
