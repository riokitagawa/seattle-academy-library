package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class BookRentInfo {

	private int bookId;

	private int rentbookId;
	
	
	public BookRentInfo() {

	}

	public BookRentInfo(int bookId, int rentbook_id) {

		this.bookId = bookId;
		this.rentbookId = rentbook_id;
	}

}