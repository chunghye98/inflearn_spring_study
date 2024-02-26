package com.group.libraryapp.controller.book;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.service.book.BookService;

@RestController
public class BookController {
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping("/book")
	public void saveBook() {
		bookService.saveBook();
	}
}
