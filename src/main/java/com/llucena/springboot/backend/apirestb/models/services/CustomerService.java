package com.llucena.springboot.backend.apirestb.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.llucena.springboot.backend.apirestb.models.entity.Customer;
import com.llucena.springboot.backend.apirestb.models.entity.Region;


public interface CustomerService {
	
	public List<Customer> findAll();
	
	public Page<Customer> findAll(Pageable pageable);
	
	
	public Customer findById(Long id);

	public Customer save(Customer customer);
	
	public void delete(Long id);

	public List<Region> findAllRegions();
}
