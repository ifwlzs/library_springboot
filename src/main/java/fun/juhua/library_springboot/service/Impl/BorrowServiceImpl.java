package fun.juhua.library_springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.juhua.library_springboot.entity.Borrow;
import fun.juhua.library_springboot.mapper.BorrowMapper;
import fun.juhua.library_springboot.service.BorrowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Resource
    BorrowMapper borrowMapper;

    @Override
    public List<Borrow> getBorrowList(String bookID, String readerID, String isNull) {
        List<Borrow> borrowList = null;
        QueryWrapper<Borrow> wrapper = new QueryWrapper<>();
        //当bookID和readerID都为空，说明显示全部
        if ((bookID == null || bookID == "") && (readerID == null || readerID == "")) {
            //如果isNull为T，查询未归还的书籍
            if ("T".equals(isNull)) {
                borrowList = borrowMapper.selectList(wrapper.isNull("returnTime"));
            } else {
                borrowList = borrowMapper.selectList(null);
            }
        } else if (bookID == null || bookID == "") {
            //readerID不为空，查询某人所有借阅记录
            //如果isNull为T，查询此人未归还的书籍
            if ("T".equals(isNull)) {
                borrowList = borrowMapper.selectList(wrapper.eq("readerID", readerID).isNull("returnTime"));
            } else {
                borrowList = borrowMapper.selectList(wrapper.eq("readerID", readerID));
            }
        } else {
            //bookID不为空，查询某人所有借阅记录
            //如果isNull为T，查询此人未归还的书籍
            if ("T".equals(isNull)) {
                borrowList = borrowMapper.selectList(wrapper.eq("bookID", bookID).isNull("returnTime"));
            } else {
                borrowList = borrowMapper.selectList(wrapper.eq("bookID", bookID));
            }
        }
        return borrowList;
    }

    @Override
    public int updateBorrow(Borrow borrow) {
        QueryWrapper<Borrow> wrapper = new QueryWrapper();
        wrapper.eq("readerID", borrow.getReaderID())
                .eq("bookID", borrow.getBookID())
                .eq("borrowTime", borrow.getBorrowTime());
        return borrowMapper.update(borrow, wrapper);
    }

    /**
     * 判断用户是否超过8本未还，未超过返回1
     *
     * @param readerID
     * @return
     */
    @Override
    public int check(String readerID) {
        QueryWrapper<Borrow> wrapper = new QueryWrapper();
        wrapper.eq("readerID", readerID).isNull("returnTime");
        Long raw = borrowMapper.selectCount(wrapper);
        if (raw < 8) {
            return 1;
        }
        return 0;
    }

    @Override
    public int addBorrow(String readerID, String bookID, String borrowTime) {
        Borrow borrow = new Borrow(readerID, bookID, borrowTime);
        return borrowMapper.insert(borrow);
    }
}
