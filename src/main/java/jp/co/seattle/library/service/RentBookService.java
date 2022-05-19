package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

//import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.dto.BookRentInfo;
//import jp.co.seattle.library.rowMapper.BookInfoRowMapper;
import jp.co.seattle.library.rowMapper.RentBookRowMapper;

/**
 * 書籍サービス
 * 
 * rentbooksテーブルに関する処理を実装する
 */
@Service
public class RentBookService {
	final static Logger logger = LoggerFactory.getLogger(RentBookService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 貸出す書籍を貸出テーブルに登録する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void rentBook(int bookId) {

		String sql = "INSERT INTO rentbooks (book_id,rent_date) VALUES (" + bookId + ", now())";

		jdbcTemplate.update(sql);
	}
	/**
	 * rentbboksテーブルにタイトルと日付カラムを付け加えようとした。 消そう。↓
	 * 貸出す書籍を貸出テーブルに登録する
	 *
	 * @param bookInfo 書籍情報
	 */
//	public void rentBook(int bookId) {
//
//		String sql = "INSERT INTO rentbooks (book_id,title,rent_date,return_date) VALUES (" 
//				+ bookId + bookrentInfo.getTitle() + "'," + "now()," + "now())";
//
//		jdbcTemplate.update(sql);
//	}

	/**
	 * 貸出テーブルから情報取得
	 *
	 * @param bookInfo 書籍情報
	 */
	public BookRentInfo selectRentBookInfo(int bookId) {
		String sql = "select * from rentbooks where book_id =" + bookId;

		try {
			BookRentInfo selectedRentInfo = jdbcTemplate.queryForObject(sql, new RentBookRowMapper());
			return selectedRentInfo;
		} catch (Exception e) {
			return null;
		}
	}

	
	
	/**
	 * 貸出テーブルからタイトル、借りた日付、返却した日付を取得
	 *
	 * @param bookInfo 書籍情報
	 */
	public List<BookRentInfo> rentHistroy() {

		List<BookRentInfo> rentHistoryList = jdbcTemplate.query(
				"select title, rent_date, return_date from rentbooks inner join books on rentbooks.book_id = books.id",
				new RentBookRowMapper());
		return rentHistoryList;
	}
	//下のこのやつじゃなくてList↑　を使うんじゃに？　消そう。↓
//	public BookRentInfo rentHistroy() {
//		String sql = "select title, rent_date, return_date from rentbooks inner join books on rentbooks.book_id = books.id";
//
//		try {
//			BookRentInfo selectedRentInfo = jdbcTemplate.queryForObject(sql, new RentBookRowMapper());
//			return selectedRentInfo;
//		} catch (Exception e) {
//			return null;
//		}
//
//	}
	
	
	
	

	/**
	 * 貸出してある書籍を貸出テーブルから削除する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void returnBook(int bookId) {

		String sql = "delete from rentbooks where book_id =" + bookId;
		String sql2 = "INSERT INTO rentbooks (book_id,return_date) VALUES (" + bookId + "now())";

		jdbcTemplate.update(sql, sql2);
	}
}
