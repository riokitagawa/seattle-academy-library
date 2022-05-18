package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"select id,title, author, publisher, publish_date, thumbnail_url, thumbnail_name from books order by title asc",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/**
	 * 書籍IDに紐づくステータス情報を書籍テーブル、貸出テーブルを結合したテーブルから取得する
	 *
	 * @param bookId 書籍ID
	 * @return ステータス情報
	 */
	public String getStatusBookInfo(int bookId) {

		// JSPに渡すデータを設定する

		String sql = "select case when book_id =" + bookId
				+ "then '貸し出し中' else '貸し出し可' end from rentbooks right join books on rentbooks.book_id = books.id where id ="
				+ bookId;

		String bookDetailsInfo = jdbcTemplate.queryForObject(sql, String.class);

		return bookDetailsInfo;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {

		// JSPに渡すデータを設定する
		String sql = "SELECT * FROM books where id =" + bookId;

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

		return bookDetailsInfo;
	}

	/**
	 * 書籍情報を取得する
	 * 
	 * @return 書籍詳細情報
	 */

	public BookDetailsInfo getBookInfo() {

		// JSPに渡すデータを設定する
		String sql = "select * from books where id = (select MAX(id) from books)";

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

		return bookDetailsInfo;
	}

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void registBook(BookDetailsInfo bookInfo) {

		String sql = "INSERT INTO books (title, author,publisher,publish_date,isbn,explain, thumbnail_name,thumbnail_url,reg_date,upd_date) VALUES ('"
				+ bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','" + bookInfo.getISBN() + "','" + bookInfo.getExplain() + "','"
				+ bookInfo.getThumbnailName() + "','" + bookInfo.getThumbnailUrl() + "'," + "now()," + "now())";

		jdbcTemplate.update(sql);
	}

	/**
	 * 一括で書籍を登録する
	 * 
	 * @param bulkBookList
	 */
	public void bulkRegistBook(List<BookDetailsInfo> bulkBookList) {
		for (BookDetailsInfo bookInfo : bulkBookList) {

			String sql = "INSERT INTO books (title,author,publisher,publish_date,isbn,explain,thumbnail_url,reg_date,upd_date) VALUES ('"
					+ bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
					+ bookInfo.getPublishDate() + "','" + bookInfo.getISBN() + "','" + bookInfo.getExplain() + "','"
					+ bookInfo.getThumbnailUrl() + "'," + "now()," + "now())";

			jdbcTemplate.update(sql);
			System.out.println(sql);
		}
	}

	/**
	 * 書籍を更新する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void updateBook(BookDetailsInfo bookInfo) {
		String sql = "update books set (title, author,publisher,publish_date,isbn,explain, thumbnail_name,thumbnail_url,upd_date) = ('"
				+ bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','" + bookInfo.getISBN() + "','" + bookInfo.getExplain() + "','"
				+ bookInfo.getThumbnailName() + "','" + bookInfo.getThumbnailUrl() + "'," + "now())where id ="
				+ bookInfo.getBookId();

		jdbcTemplate.update(sql);
	}

	/**
	 * 書籍を削除する
	 *
	 * @param bookId 書籍ID
	 */
	public void deleteBook(int bookId) {

		String sql = "delete from books where id =" + bookId;

		jdbcTemplate.update(sql);
	}
	
	/**
	 * 書籍を検索する
	 * 検索ワードと一致する書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */	
	public List<BookInfo> searchBooks(String search) {

		List<BookInfo> searchBooksList = jdbcTemplate.query(
				"select id, title, author, publisher, publish_date, thumbnail_url, thumbnail_name from books where title like '%" + search + "%'",
				new BookInfoRowMapper());

		return searchBooksList;
	}	
}
