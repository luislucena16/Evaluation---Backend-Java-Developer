package com.llucena.springboot.backend.apirestb.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.llucena.springboot.backend.apirestb.models.entity.Customer;
import com.llucena.springboot.backend.apirestb.models.entity.Region;

public interface ICustomerDao extends JpaRepository<Customer, Long> {
	
	@Query("from Region")
	public List<Region> findAllRegions();
}
