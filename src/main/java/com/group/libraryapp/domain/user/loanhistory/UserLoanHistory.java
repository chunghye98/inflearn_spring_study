package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
