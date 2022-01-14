package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Book;
import fun.juhua.library_springboot.entity.Reader;
import fun.juhua.library_springboot.service.BookService;
import fun.juhua.library_springboot.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReaderController {
    @Resource
    ReaderService readerServiceImpl;
    @Resource
    BookService bookService;

    @RequestMapping("/reader")
    public String toIndex(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "reader/index";
    }

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/RegisterReader")
    @ResponseBody
    public String registerReader(Reader reader) {
        int state = 0;
        String msg = "用户名已存在！";
        if (!readerServiceImpl.haveReader(reader.getId())) {
            state = readerServiceImpl.addReader(reader);
            if (state == 1) {
                msg = "注册成功！";//注册成功！
            } else {
                msg = "请重试！";//请重试！
            }
        }
        //System.out.println("ReaderController -> registerReader(49): " + msg);
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/admin/toReaderList")
    public String toReaderList(String name, String id, Model model) {
        List<Reader> readerList = readerServiceImpl.getReaderList(name, id);
        model.addAttribute("readerList", readerList);
        return "admin/readerList";
    }

    @RequestMapping("/reader/toBookshelf")
    public String toBookshelf(String bookName, String bookID, HttpSession session, Model model) {
        List<Book> bookList = bookService.getBookList(bookName, bookID);
        model.addAttribute("bookList", bookList);
        Boolean allow = (Boolean) session.getAttribute("allow");
        model.addAttribute("allow", allow);
        return "reader/bookList";
    }

    @RequestMapping("/UpdateReader")
    @ResponseBody
    public String updateReader(String id, String name, String password, String oldPassword, String gender, String telephone, String email) {
        Reader reader = new Reader(id, name, password == "" ? oldPassword : password, gender, telephone, email);
        int state = readerServiceImpl.updateReader(reader);
        String msg = "请重试";
        if (state == 1) {
            msg = "更新成功!";
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/toEditReader")
    public String toEditReader(HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("user");
        model.addAttribute("user", reader);
        return "reader/edit";
    }

    @RequestMapping("/admin/toEditReader")
    public String toEditReader(String id, Model model) {
        Reader reader = readerServiceImpl.findReader(id);
        model.addAttribute("user", reader);
        return "admin/editReader";
    }

    @RequestMapping("/admin/DeleteReader")
    public String deleteReader(String id) {
        int raw = readerServiceImpl.deleteAdminById(id);
        return "redirect:/admin/";
    }

}
