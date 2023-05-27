package com.study.trip.domain.board;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.study.trip.domain.BaseTimeEntity;
import com.study.trip.domain.reply.Reply;
import com.study.trip.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String title;

	@Lob
	private String content;

	private int count; //조회수

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	private String startday;
	private String lastday;
	private int pnum;

	private String city;

	private String state;

	public void update(String title, String content, String startday, String lastday, int punm, String city, String state) {
		this.title = title;
		this.content = content;
		this.startday = startday;
		this.lastday = lastday;
		this.pnum = punm;
		this.city=city;
		this.state= state;
	}



	@OrderBy("id desc")
	@JsonIgnoreProperties({"board"})
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Reply> replyList;
}