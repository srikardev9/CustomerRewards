package com.rewardpoints;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rewardpoints.dto.CustomerPointsDTO;
import com.rewardpoints.dto.RewardPointsDTO;
import com.rewardpoints.exceptions.RewardPointsException;
import com.rewardpoints.model.RewardPoints;
import com.rewardpoints.service.RewardPointsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RewardPointsServiceTest {

	@Autowired
	private RewardPointsService rewardsService;

	@Before
	public void before() throws ParseException {

	}

	@Test
	public void getRewardPointsForCutomer1() {
		try {
			insertCustomer1Data();
			CustomerPointsDTO customerDetails = rewardsService.getRewardPoints("customer1");
			assertTrue(customerDetails.getTotalPoints() == 50);
			assertTrue(customerDetails.getRewards().size() == 2);
			List<RewardPointsDTO> rewards = customerDetails.getRewards();
			assertTrue(rewards.get(0).getMonth() == 3);
			assertTrue(rewards.get(0).getMonthPoints() == 50);
		} catch (RewardPointsException | ParseException p) {
			assertFalse(true);
		}
	}

	private void insertCustomer1Data() throws ParseException, RewardPointsException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		RewardPoints reward1 = new RewardPoints();
		reward1.setCustomerName("customer1");
		reward1.setPurchaseAmount(100);
		reward1.setCreatedDate(sdf.parse("13/03/2023"));

		RewardPoints reward2 = new RewardPoints();
		reward2.setCustomerName("customer1");
		reward2.setPurchaseAmount(40);
		reward2.setCreatedDate(sdf.parse("03/04/2023"));
		List<RewardPoints> list = new ArrayList<>();
		list.add(reward1);
		list.add(reward2);
		rewardsService.mockupData(list);
	}

	@Test
	public void getRewardPointsForCutomer2() {
		try {
			insertCustomer2Data();
			CustomerPointsDTO customerDetails = rewardsService.getRewardPoints("customer2");
			assertTrue(customerDetails.getTotalPoints() == 200);
			assertTrue(customerDetails.getRewards().size() == 2);
			List<RewardPointsDTO> rewards = customerDetails.getRewards();
			assertTrue(rewards.get(0).getMonth() == 3);
			assertTrue(rewards.get(0).getMonthPoints() == 90);
		} catch (RewardPointsException | ParseException e) {
			assertFalse(true);
		}
	}

	private void insertCustomer2Data() throws ParseException, RewardPointsException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// 2nd Customer
		RewardPoints c1 = new RewardPoints();
		c1.setCustomerName("customer2");
		c1.setPurchaseAmount(120);
		c1.setCreatedDate(sdf.parse("11/03/2023"));

		RewardPoints c2 = new RewardPoints();
		c2.setCustomerName("customer2");
		c2.setPurchaseAmount(130);
		c2.setCreatedDate(sdf.parse("03/04/2023"));

		List<RewardPoints> list = new ArrayList<>();
		list.add(c1);
		list.add(c2);

		rewardsService.mockupData(list);
	}
}
