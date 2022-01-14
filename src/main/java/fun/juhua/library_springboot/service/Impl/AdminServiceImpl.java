package fun.juhua.library_springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.juhua.library_springboot.entity.Admin;
import fun.juhua.library_springboot.mapper.AdminMapper;
import fun.juhua.library_springboot.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;

    /**
     * 查询admin列表，如果有名，根据名模糊查询，否则返回全部admin用户
     *
     * @param name
     * @return List<Admin>
     */
    @Override
    public List<Admin> getAdminList(String name) {
        //System.out.println("AdminService -> getAdminList(19): " + name);
        List<Admin> adminList = null;
        if (name == null || name == "") {
            adminList = adminMapper.selectList(null);
        } else {
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            adminList = adminMapper.selectList(wrapper.like("name", name));
        }
        return adminList;
    }

    @Override
    public int deleteAdminById(String id) {
        return adminMapper.deleteById(id);
    }

    @Override
    public boolean login(String id, String password) {
        Admin admin = findAdmin(id);
        if (admin == null || !admin.getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    @Override
    public Admin findAdmin(String id) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        return adminMapper.selectOne(wrapper.eq("id", id));
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.updateById(admin);
    }

    @Override
    public int addAdmin(Admin admin) {
        return adminMapper.insert(admin);
    }

    @Override
    public boolean haveAdmin(String id) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        return adminMapper.selectOne(wrapper.eq("id", id)) != null;
    }
}
