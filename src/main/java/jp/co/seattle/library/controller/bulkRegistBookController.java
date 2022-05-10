package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

/*
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class bulkRegistBookController {
	final static Logger logger = LoggerFactory.getLogger(bulkRegistBookController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * 一括登録画面に遷移
	 * 
	 * @return 遷移先画面
	 */
	@RequestMapping(value = "/bulkRegist", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String bulkRegist(Model model) {
		return "bulkRegistBook";
	}

	/**
	 * 書籍情報を一括登録する
	 * 
	 * @param locale ロケール情報
	 * @param file   サムネイルファイル
	 * @param model  モデル
	 * @return 遷移先画面
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得

	public String uploadFile(Locale locale, @RequestParam("upload_file") MultipartFile uploadFile, Model model) {

		List<String> bulkErrorList = new ArrayList<String>();

		// バリデいけた時に入れるリスト
		List<BookDetailsInfo> bulkBookList = new ArrayList<BookDetailsInfo>();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(uploadFile.getInputStream(), StandardCharsets.UTF_8))) {

			if (!br.ready()) {
				bulkErrorList.add("csvに書籍情報がありません。");
			}
			String line;
			int lineCount = 0;
			while ((line = br.readLine()) != null) {
				String[] uploadElement = line.split(",", -1);
				System.out.println(uploadElement);

				BookDetailsInfo bookInfo = new BookDetailsInfo();

				lineCount++;

				if (uploadElement[0].equals("") || uploadElement[1].equals("") || uploadElement[2].equals("")
						|| uploadElement[3].equals("")
						|| !uploadElement[3].matches("^[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$")
						|| !uploadElement[4].equals("") && !uploadElement[4].matches("^[0-9]{10}|[0-9]{13}+$/")) {
					bulkErrorList.add(lineCount + "行目の書籍でエラーが起きました。");
				} else

					bookInfo.setTitle(uploadElement[0]);
				bookInfo.setAuthor(uploadElement[1]);
				bookInfo.setPublisher(uploadElement[2]);
				bookInfo.setPublishDate(uploadElement[3]);
				bookInfo.setISBN(uploadElement[4]);
				bulkBookList.add(bookInfo);
			}

			if (bulkErrorList.size() > 0) {
				model.addAttribute("bulkErrorList", bulkErrorList);
				return "bulkRegistBook";
			} else {
				booksService.bulkRegistBook(bulkBookList);
			}

		} catch (IOException e) {
			bulkErrorList.add("ファイルが読み込めません");
			model.addAttribute("bulkErrorList", bulkErrorList);
			return "redirect:/bulkRegist";
		}

		// home画面に遷移する
		return "redirect:/home";
	}
}
