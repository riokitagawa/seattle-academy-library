package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class BookEditInfo {

    private int bookId;

    private String title;

    private String author;

    private String publisher;
    
    private String publishDate;
    
    private String ISBN;
    
    private String explain;
    
    private String thumbnail;

    private String thumbnailUrl;

    private String thumbnailName;

    public BookEditInfo() {

    }

    public BookEditInfo(int bookId, String title, String author, String publisher,
    		String publishDate, String isbn, String explain, String thumbnail, String thumbnail_url, String thumbnail_name) {
    	
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.ISBN = isbn;
        this.explain = explain;
        this.thumbnail = thumbnail;
        this.thumbnailUrl = thumbnail_url;
        this.thumbnailName = thumbnail_name;
    }

}