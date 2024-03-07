package com.group.libraryapp.controller.book;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import com.group.libraryapp.service.book.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookController {
	private final BookService bookService;

	/**
	 * 책 생성
	 * @param request
	 */
	@PostMapping("/book")
	public void saveBook(@RequestBody BookCreateRequest request) {
		bookService.saveBook(request);
	}

	/**
	 * 책 대출
	 * @param request
	 */
	@PostMapping("/book/loan")
	public void loanBook(@RequestBody BookLoanRequest request) {
		bookService.loanBook(request);
	}

	@PutMapping("/book/return")
	public void returnBook(@RequestBody BookReturnRequest request) {
		bookService.returnBook(request);
	}
}
