package com.rewardpoints.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewardpoints.exceptions.RewardPointsException;
import com.rewardpoints.service.RewardPointsService;
import com.rewardpoints.util.RewardPointsUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RewardPointsController {

	private Logger logger = LoggerFactory.getLogger(RewardPointsController.class);

	@Autowired
	private RewardPointsService rewardsService;

	@PostMapping("/mockupData")
	public ResponseEntity<?> mockupData() throws ParseException {
		logger.info("creating mockup data");
		try {
			rewardsService.mockupData(RewardPointsUtil.getDataSet());
			return new ResponseEntity<String>("Mockup Data is created", HttpStatus.CREATED);
		} catch (RewardPointsException e) {
			logger.error("Error in creating mockup data", e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getRewardPoints/{customerName}")
	public ResponseEntity<?> getRewardPoints(@PathVariable("customerName") String customerName) {
		logger.info("Get reward points ");
		try {
			return new ResponseEntity<>(rewardsService.getRewardPoints(customerName), HttpStatus.OK);
		} catch (RewardPointsException e) {
			logger.error("Error while getting the reward points", e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
