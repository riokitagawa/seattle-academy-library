package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.service.RentBookService;

/*
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class RentHistoryController {
	final static Logger logger = LoggerFactory.getLogger(RentHistoryController.class);
	@Autowired
	private RentBookService rentBookService;

	/**
	 * 書籍を借りる
	 *
	 * @param locale ロケール情報
	 * @param bookId 書籍I
	 * @param model  モデル
	 * @return 遷移先画面
	 */
	@RequestMapping(value = "/rentHistory", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String rentHistory(Locale locale, Model model) {
		logger.info("Welcome rentHistory! The client locale is {}.", locale);

		model.addAttribute("rentHistoryList", rentBookService.rentHistroy());

		return "rentHistory";

	}
}
