package com.group.libraryapp.service.book;

import org.springframework.stereotype.Service;

import com.group.libraryapp.repository.book.BookMemoryRepository;

@Service
public class BookService {
	private final BookMemoryRepository bookMemoryRepository;

	public BookService(BookMemoryRepository bookMemoryRepository) {
		this.bookMemoryRepository = bookMemoryRepository;
	}

	public void saveBook() {
		bookMemoryRepository.saveBook();
	}
}
