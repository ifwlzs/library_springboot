package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Book;
import fun.juhua.library_springboot.entity.Borrow;
import fun.juhua.library_springboot.entity.Reader;
import fun.juhua.library_springboot.service.BookService;
import fun.juhua.library_springboot.service.BorrowService;
import fun.juhua.library_springboot.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class BorrowController {
    @Resource
    BorrowService borrowServiceImpl;
    @Resource
    BookService bookService;
    @Resource
    ReaderService readerServiceImpl;

    @RequestMapping("/admin/toBorrowList")
    public String toBorrowList(String bookID, String readerID, String isNull, Model model) {
        List<Borrow> borrowList = borrowServiceImpl.getBorrowList(bookID, readerID, isNull);
        model.addAttribute("borrowList", borrowList);
        return "admin/borrowList";
    }

    @RequestMapping("/reader/toReaderBorrow")
    public String toReaderBorrow(HttpSession httpSession, String isNull, Model model) {
        Reader reader = (Reader) httpSession.getAttribute("user");
        List<Borrow> borrowList = borrowServiceImpl.getBorrowList(null, reader.getId(), isNull);
        model.addAttribute("borrowList", borrowList);
        model.addAttribute("user", reader);
        return "reader/borrowList";
    }

    @RequestMapping("/admin/toReturnBook")
    @ResponseBody
    public String toReturnBook(String readerID, String bookID, String borrowTime) {
        borrowTime = borrowTime.toString().replace("T", " ").replace(".000+08:00", "");
        Borrow borrow = new Borrow(readerID, bookID, borrowTime, new Date());
        int state = bookService.returnBook(bookID);
        String msg = "归还失败，请重试！";
        if (state == 1) {
            state = borrowServiceImpl.updateBorrow(borrow);
            msg = "归还成功！";
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/admin/toAddBorrow")
    public String toAddBorrow(Model model) {
        List<Reader> readerList = readerServiceImpl.getReaderList(null, null);
        List<Book> bookList = bookService.getBookList(null, null);
        model.addAttribute("readerList", readerList);
        model.addAttribute("bookList", bookList);
        return "admin/addBorrow";
    }

    @RequestMapping("/admin/AddBorrow")
    @ResponseBody
    public String addBorrow(String readerID, String bookID, String borrowTime) {
        int state = 0;
        String msg = "馆内暂时无货,请等待。";
        //判断借出数是否小于书本总数
        if (bookService.check(bookID) == 1) {
            state = borrowServiceImpl.check(readerID);
            Reader reader = readerServiceImpl.findReader(readerID);
            if (reader != null) {
                if (state == 1) {
                    state = borrowServiceImpl.addBorrow(readerID, bookID, borrowTime);
                    msg = "借阅成功！";
                } else {
                    msg = "达到最大借书数量！请尽快归还！";
                }
            } else {
                state = 0;
                msg = "查无此用户！";
            }
        }
        //System.out.println("ReaderController -> registerReader(49): " + msg);
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }
}
