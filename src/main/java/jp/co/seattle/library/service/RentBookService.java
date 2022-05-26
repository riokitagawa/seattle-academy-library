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
	 * 貸出テーブルから情報取得
	 *
	 * @param bookInfo 書籍情報
	 */
	public String selectRentBookInfo(int bookId) {
		String sql = "select book_id from rentbooks where book_id =" + bookId;

		try {
			String selectedRentInfo = jdbcTemplate.queryForObject(sql, String.class);
			return selectedRentInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 貸出テーブルから貸出日取得
	 *
	 * @param bookInfo 書籍情報
	 */
	public String selectRentdateInfo(int bookId) {
		String sql = "select rent_date from rentbooks where book_id =" + bookId;

		try {
			String selectedRentdateInfo = jdbcTemplate.queryForObject(sql, String.class);
			return selectedRentdateInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 貸出テーブルの貸出日付に日付を入れる、返却日の日付を消す つまり（情報更新
	 *
	 * @param bookInfo 書籍情報
	 */
	public void updateRentdateInfo(int bookId) {
		String sql = "update rentbooks set rent_date = now(), return_date = null where book_id =" + bookId;
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 貸出テーブルからタイトル、借りた日付、返却した日付を取得
	 *
	 * @param bookInfo 書籍情報
	 */
	public List<BookRentInfo> rentHistroy() {

		List<BookRentInfo> rentHistoryList = jdbcTemplate.query(
				"select book_id, title, rent_date, return_date from rentbooks inner join books on rentbooks.book_id = books.id",
				new RentBookRowMapper());
		return rentHistoryList;
	}
	
	/**
	 * 貸出してある書籍を貸出テーブルから削除する　→　返却したら貸出テーブルの返却日カラムのレコードに返却した日付入れる
	 *
	 * @param bookInfo 書籍情報
	 */
	public void returnBook(int bookId) {

		String sql = "update rentbooks set rent_date = null, return_date = now() where book_id =" + bookId;
		
		jdbcTemplate.update(sql);
	}
}
