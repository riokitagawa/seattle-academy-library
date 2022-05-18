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
	public String SearchBooks(Locale locale, @RequestParam("search") String search, Model model) {

		logger.info("Welcome SearchBooks! The client locale is {}.", locale);

		model.addAttribute("bookList", booksService.searchBooks(search));

		return "home";

	}
}