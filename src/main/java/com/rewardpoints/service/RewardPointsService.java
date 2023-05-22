package com.rewardpoints.service;

import java.util.List;

import com.rewardpoints.dto.CustomerPointsDTO;
import com.rewardpoints.exceptions.RewardPointsException;
import com.rewardpoints.model.RewardPoints;

public interface RewardPointsService {

	void mockupData(List<RewardPoints> list)throws RewardPointsException;

	CustomerPointsDTO getRewardPoints(String customerName)throws RewardPointsException;
}
