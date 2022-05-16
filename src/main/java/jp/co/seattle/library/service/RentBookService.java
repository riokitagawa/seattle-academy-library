package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookRentInfo;
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

		String sql = "INSERT INTO rentbooks (book_id) VALUES (" + bookId + ")";

		jdbcTemplate.update(sql);
	}
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
	 * 貸出してある書籍を貸出テーブルから削除する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void returnBook(int bookId) {

		String sql = "delete from rentbooks where book_id =" + bookId;

		jdbcTemplate.update(sql);
	}
}
