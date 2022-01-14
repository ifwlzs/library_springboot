package fun.juhua.library_springboot.service;

import fun.juhua.library_springboot.entity.Borrow;

import java.util.List;

public interface BorrowService {

    List<Borrow> getBorrowList(String bookID, String readerID, String isNull);

    int updateBorrow(Borrow borrow);

    int check(String readerID);

    int addBorrow(String readerID, String bookID, String borrowTime);
}
