package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Reader;
import fun.juhua.library_springboot.service.AdminService;
import fun.juhua.library_springboot.service.BorrowService;
import fun.juhua.library_springboot.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Resource
    AdminService adminServiceImpl;
    @Resource
    ReaderService readerServiceImpl;
    @Resource
    BorrowService borrowServiceImpl;

    @RequestMapping("/welcome")
    public String toWelcome() {
        return "welcome";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/Login")
    public String login(String id, String password, HttpSession session, Model model) {
        String checkAdmin = id.substring(0, 1);
        boolean state = false;
        if (checkAdmin.equals("m")) {
            state = adminServiceImpl.login(id, password);
            if (state) {
                session.setAttribute("role", "admin");
                session.setAttribute("user", adminServiceImpl.findAdmin(id));
                model.addAttribute("user", adminServiceImpl.findAdmin(id));
                return "redirect:/admin/";
            }
        } else if (checkAdmin.equals("r")) {
            state = readerServiceImpl.login(id, password);
            if (state) {
                session.setAttribute("role", "reader");
                boolean allow = borrowServiceImpl.check(id) == 1 ? true : false;
                session.setAttribute("allow", allow);
                Reader reader = readerServiceImpl.findReader(id);
                System.out.println("IndexController -> login(49)reader: " + reader);
                session.setAttribute("user", reader);
                model.addAttribute("user", readerServiceImpl.findReader(id));
                return "redirect:/reader/";
            }
        }
        model.addAttribute("errorMsg", "用户名或密码错误");
        return "redirect:/toLogin";
    }

    @RequestMapping("/Logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/toIndex";
    }

    @RequestMapping("/toIndex")
    public String toIndex(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        //System.out.println("IndexController -> toIndex(63)role: " + role);
        Object user = session.getAttribute("user");
        //System.out.println("IndexController -> toIndex(70)user: " + user);
        //System.out.println(user == null);
        if (role != null && user != null && role.equals("admin")) {
            model.addAttribute("user", session.getAttribute("user"));
            return "redirect:/admin/";
        } else if (role != null && user != null && role.equals("reader")) {
            model.addAttribute("user", session.getAttribute("user"));
            return "redirect:/reader/";
        }
        return "redirect:/toLogin";
    }
}
