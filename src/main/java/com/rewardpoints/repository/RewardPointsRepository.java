package com.rewardpoints.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rewardpoints.model.RewardPoints;

@Repository
public interface RewardPointsRepository extends CrudRepository<RewardPoints, Long>{

	List<RewardPoints> findByCustomerName(String customerName);
}
