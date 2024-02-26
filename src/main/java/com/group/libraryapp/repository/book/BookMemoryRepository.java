package com.group.libraryapp.repository.book;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookMemoryRepository implements BookRepository{

	private final JdbcTemplate jdbcTemplate;

	public BookMemoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void saveBook() {

	}
}
