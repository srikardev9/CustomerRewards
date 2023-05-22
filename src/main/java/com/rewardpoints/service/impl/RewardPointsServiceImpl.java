package com.rewardpoints.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardpoints.dto.CustomerPointsDTO;
import com.rewardpoints.dto.RewardPointsDTO;
import com.rewardpoints.exceptions.RewardPointsException;
import com.rewardpoints.model.RewardPoints;
import com.rewardpoints.repository.RewardPointsRepository;
import com.rewardpoints.service.RewardPointsService;

@Service
public class RewardPointsServiceImpl implements RewardPointsService {

	private Logger logger = LoggerFactory.getLogger(RewardPointsServiceImpl.class);
	
	@Autowired
	private RewardPointsRepository repositoy;

	@Override
	public CustomerPointsDTO getRewardPoints(String customerName) throws RewardPointsException {
		logger.info("Calculating customer reward points");
		try {
			List<RewardPoints> rewards = repositoy.findByCustomerName(customerName);
			Map<Object, Long> months = rewards.stream()
					.collect(Collectors.groupingBy(element -> element.getCreatedDate().getMonth(),
							Collectors.summingLong(element -> element.getPoints())));

			List<RewardPointsDTO> rewardsDTO = new ArrayList<RewardPointsDTO>();
			Long total = 0l;
			for (Entry<Object, Long> entry : months.entrySet()) {
				RewardPointsDTO dto = new RewardPointsDTO();
				dto.setMonth(((Integer) entry.getKey() + 1));
				Long monthTotal = entry.getValue();
				total += monthTotal;
				dto.setMonthPoints(monthTotal);

				rewardsDTO.add(dto);
			}

			CustomerPointsDTO customerDetails = new CustomerPointsDTO();
			customerDetails.setTotalPoints(total);
			customerDetails.setRewards(rewardsDTO);
			return customerDetails;
		} catch (Exception e) {
			logger.error("Error while calculating customer reward points",e);
			throw new RewardPointsException("Unable to caluclate the reward points :" + e.getMessage());
		}
	}

	private Integer getPoints(RewardPoints r) {
		Integer amount = r.getPurchaseAmount();
		if (amount <= 50) {
			return 0;
		} else if (amount <= 100) {
			return (amount - 50) * 1;
		} else {
			return 50 + (amount % 100) * 2;
		}
	}

	@Override
	public void mockupData(List<RewardPoints> list) throws RewardPointsException {
		try {
			for (RewardPoints r : list) {
				Integer points = getPoints(r);
				r.setPoints(points);
				repositoy.save(r);
			}
		} catch (Exception e) {
			logger.error("Error while creating mockup data",e);
			throw new RewardPointsException("Unable to mockup the data:"+e.getMessage());
		}
	}
}
