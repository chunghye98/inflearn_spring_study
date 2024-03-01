package com.group.libraryapp.domain.user.loanhistory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.group.libraryapp.domain.user.User;

@Entity
public class UserLoanHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;
	private String bookName;
	private boolean isReturn; // tinyint에 매핑됨

	public UserLoanHistory() {
	}

	public UserLoanHistory(User user, String bookName) {
		this.user = user;
		this.bookName = bookName;
		this.isReturn = false;
	}

	public void doReturn() {
		this.isReturn = true;
	}

	public String getBookName() {
		return bookName;
	}
}
