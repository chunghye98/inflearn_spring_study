package com.group.libraryapp.service.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
	private final BookRepository bookRepository;
	private final UserLoanHistoryRepository userLoanHistoryRepository;
	private final UserRepository userRepository;

	@Transactional
	public void saveBook(BookCreateRequest request) {
		bookRepository.save(new Book(request.getName()));
	}

	@Transactional
	public void loanBook(BookLoanRequest request) {
		// 1. 책 정보를 가져온다.
		Book book = bookRepository.findByName(request.getBookName())
			.orElseThrow(IllegalArgumentException::new);

		// 2. 대출 기록 정보를 확인해서 대출중인지 확인한다.
		// 3. 만약에 대출중이면 예외를 발생한다.
		if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
			throw new IllegalArgumentException("이미 대출된 책입니다.");
		}

		// 4. 유저 정보를 가져온다.
		User user = userRepository.findByName(request.getUserName())
			.orElseThrow(IllegalArgumentException::new);
		// 5. 유저 정보와 책 정보를 기반으로 대출 기록을 저장
		user.loanBook(book.getName());
	}

	@Transactional
	public void returnBook(BookReturnRequest request) {
		// 1. 유저 정보를 가져온다.
		User user = userRepository.findByName(request.getUserName())
			.orElseThrow(IllegalArgumentException::new);

		// 2. 유저의 대출 기록을 가져온다.
		// 3. 대출 기록을 true로 바꿔준다.
		user.returnBook(request.getBookName());
	}
}
