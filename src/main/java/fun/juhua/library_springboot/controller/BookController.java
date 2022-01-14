package fun.juhua.library_springboot.controller;

import fun.juhua.library_springboot.entity.Book;
import fun.juhua.library_springboot.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    BookService bookServiceImpl;

    @RequestMapping("/admin/toBookList")
    public String toBookList(String bookName, String bookID, Model model) {
        List<Book> bookList = bookServiceImpl.getBookList(bookName, bookID);
        model.addAttribute("bookList", bookList);
        return "admin/bookList";
    }

    @RequestMapping("/admin/toEditBook")
    public String toEditBook(String id, Model model) {
        //System.out.println(id);
        Book book = bookServiceImpl.findById(id);
        model.addAttribute("editBook", book);
        return "admin/editBook";
    }

    @RequestMapping("/admin/UpdateBook")
    @ResponseBody
    public String updateBook(String bookID, String bookName, String bookAuthor, String bookPublisher, String publishTime, String bookPrice, String bookSum, String bookLend, String tag, String isbn) {
        Book book = new Book(bookID, bookName, bookAuthor, bookPublisher, publishTime, bookPrice, bookSum, bookLend, tag, isbn);
        int state = bookServiceImpl.updateBook(book);
        String msg = "更新失败，请重试";
        if (state == 1) {
            msg = "更新成功!";
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }

    @RequestMapping("/admin/DeleteBook")
    public String deleteBook(String id) {
        int raw = bookServiceImpl.deleteBook(id);
        //System.out.println("BookController -> deleteBook(49): " + raw);
        return "redirect:/admin/";
    }

    @RequestMapping("/admin/toAddBook")
    public String toAddBook() {
        return "admin/addBook";
    }

    @RequestMapping("/admin/AddBook")
    @ResponseBody
    public String addBook(String bookID, String bookName, String bookAuthor, String bookPublisher, String publishTime, String bookPrice, String bookSum, String tag, String isbn) {
        Book book = new Book(bookID, bookName, bookAuthor, bookPublisher, publishTime, bookPrice, bookSum, "0", tag, isbn);
        int state = bookServiceImpl.addBook(book);
        String msg = "添加失败，请重试";
        if (state == 1) {
            msg = "添加成功!";
        }
        return "{\"state\":" + state + ",\"msg\":\"" + msg + "\"}";
    }
}
