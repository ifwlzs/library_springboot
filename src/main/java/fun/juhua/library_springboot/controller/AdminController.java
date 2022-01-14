package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Admin;
import fun.juhua.library_springboot.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
// @RestController渲染成json格式返回给前台
public class AdminController {
    @Resource
    AdminService adminServiceImpl;

    @RequestMapping("/toAdminList")
    public String toAdminList(String name, Model model) {
        List<Admin> adminList = adminServiceImpl.getAdminList(name);
        model.addAttribute("adminList", adminList);
        return "admin/adminList";
    }

    @RequestMapping("/DeleteAdmin")
    public String deleteAdmin(String id) {
        int raw = adminServiceImpl.deleteAdminById(id);
        return "redirect:/admin/";
    }


    @RequestMapping("/")
    public String toIndex(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "admin/index";
    }

    @RequestMapping("/toAddReader")
    public String toAddReader() {
        return "redirect:/toRegister";
    }

    @RequestMapping("/toEditAdmin")
    public String toEditAdmin(String id, HttpSession session, Model model) {
        Admin admin = null;
        if (id == "" || id == null || id.equals("")) {
            admin = (Admin) session.getAttribute("user");
        } else {
            admin = adminServiceImpl.findAdmin(id);
        }
        model.addAttribute("editUser", admin);
        return "admin/editAdmin";
    }

    @RequestMapping("/UpdateAdmin")
    @ResponseBody
    public String updateAdmin(String id, String name, String password, String oldPassword, String gender, String telephone, String email) {
        Admin admin = new Admin(id, name, password == "" ? oldPassword : password, gender, telephone, email);
        int state = adminServiceImpl.updateAdmin(admin);
        String msg = "更新失败，请重试";
        if (state == 1) {
            msg = "更新成功!";
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/toAddAdmin")
    public String toAddAdmin() {
        return "admin/addAdmin";
    }

    @RequestMapping("/RegisterAdmin")
    @ResponseBody
    public String registerReader(Admin admin) {
        int state = 0;
        String msg = "用户名已存在！";
        if (!adminServiceImpl.haveAdmin(admin.getId())) {
            state = adminServiceImpl.addAdmin(admin);
            if (state == 1) {
                msg = "注册成功！";//注册成功！
            } else {
                msg = "请重试！";//请重试！
            }
        }
        //System.out.println("ReaderController -> registerReader(49): " + msg);
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

}