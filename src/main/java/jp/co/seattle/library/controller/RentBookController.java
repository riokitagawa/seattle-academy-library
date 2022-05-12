package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookRentInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBookService;

/*
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class RentBookController {
	final static Logger logger = LoggerFactory.getLogger(RentBookController.class);

	@Autowired
	private RentBookService rentBookService;
	@Autowired
	private BooksService booksService;

	/**
	 * 書籍を借りる
	 *
	 * @param locale ロケール情報
	 * @param bookId 書籍I
	 * @param model  モデル
	 * @return 遷移先画面
	 */
	@RequestMapping(value = "/rentBook", method = RequestMethod.POST) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String rentBook(Locale locale, @RequestParam("bookId") Integer bookId, Model model) {
		logger.info("Welcome rentBook! The client locale is {}.", locale);

		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));

		BookRentInfo selectedRentInfo = rentBookService.selectRentBookInfo(bookId);

		if (selectedRentInfo != null) {
			model.addAttribute("errorMessage", "貸出済みです");

			
		} else {

			// 貸出テーブルに登録
			rentBookService.rentBook(bookId);
			// 書籍の情報保持
		}
			return "details";
		
	}
}
