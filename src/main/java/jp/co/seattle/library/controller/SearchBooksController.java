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

import jp.co.seattle.library.service.BooksService;

/**
 * アカウント作成コントローラー
 */
@Controller // APIの入り口
public class SearchBooksController {
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * 書籍名を検索
	 *
	 * @param search 検索ワード
	 * @param model
	 * @return ホーム画面に遷移
	 */
	@Transactional
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String SearchBooks(Locale locale, @RequestParam("search") String search,
			@RequestParam("searchway") String searchway, Model model) {

		logger.info("Welcome SearchBooks! The client locale is {}.", locale);

		// 完全一致の方が選択されていたら書籍名が検索ワードと完全一致する情報を取得する
		if (searchway.equals("0")) {
			model.addAttribute("bookList", booksService.searchBooksPerfectly(search));
			return "home";

		} else {
		// 部分一致による書籍情報の取得　
			model.addAttribute("bookList", booksService.searchBooks(search));

			return "home";
		}
	}
}