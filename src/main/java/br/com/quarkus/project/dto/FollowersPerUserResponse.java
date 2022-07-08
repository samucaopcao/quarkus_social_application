package br.com.quarkus.project.dto;

import java.util.List;

import lombok.Data;

@Data
public class FollowersPerUserResponse {
	
	private Integer followersCount;
	private List<FollowerResponse> content;

	
}
