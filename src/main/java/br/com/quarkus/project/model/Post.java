package br.com.quarkus.project.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "post_text")
	private String text;

	@Column(name = "dateTime ")
	private LocalDateTime dateTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	//Setando o valor da data
	@PrePersist
	public void prePersist() {
		setDateTime(LocalDateTime.now());
	}

}