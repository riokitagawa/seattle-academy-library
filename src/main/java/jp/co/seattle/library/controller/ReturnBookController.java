package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookRentInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBookService;

/**
 * 返却コントローラー
 */
@Controller // APIの入り口
public class ReturnBookController {
	final static Logger logger = LoggerFactory.getLogger(ReturnBookController.class);

	@Autowired
	private RentBookService rentBookService;
	@Autowired
	private BooksService booksService;

	/**
	 * 対象書籍を返却≒削除する
	 *
	 * @param locale ロケール情報
	 * @param bookId 書籍ID
	 * @param model  モデル情報
	 * @return 遷移先画面名
	 */
	@Transactional
	@RequestMapping(value = "/returnBook", method = RequestMethod.POST)
	public String returnBook(Locale locale, @RequestParam("bookId") Integer bookId, Model model) {
		logger.info("Welcome returnBook! The client locale is {}.", locale);

		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));

		BookRentInfo selectedReturntInfo = rentBookService.selectRentBookInfo(bookId);

		if (selectedReturntInfo == null) {
			model.addAttribute("errorMessage", "貸出しされていません。");

		} else {
			// 貸出テーブルから削除
			rentBookService.returnBook(bookId);
		}

		String bookDetailsInfo = booksService.getStatusBookInfo(bookId);

		model.addAttribute("statusMessage", bookDetailsInfo);

		return "details";
	}

}
