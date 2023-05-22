package com.rewardpoints.dto;

import java.util.List;

public class CustomerPointsDTO {
	private Long totalPoints;
	private List<RewardPointsDTO> rewards;
	
	
	public Long getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Long totalPoints) {
		this.totalPoints = totalPoints;
	}
	public List<RewardPointsDTO> getRewards() {
		return rewards;
	}
	public void setRewards(List<RewardPointsDTO> rewards) {
		this.rewards = rewards;
	}
	
	

}
