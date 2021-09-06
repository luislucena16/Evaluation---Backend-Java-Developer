package com.llucena.springboot.backend.apirestb.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.llucena.springboot.backend.apirestb.models.entity.Customer;
import com.llucena.springboot.backend.apirestb.models.entity.Region;
import com.llucena.springboot.backend.apirestb.models.services.CustomerService;
import com.llucena.springboot.backend.apirestb.models.services.IUploadFileService;


@CrossOrigin(origins= {"http://localhost:3306"})
@RestController
@RequestMapping("/api")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private IUploadFileService uploadService;
	
	
	@GetMapping("/customers")
	public List<Customer> index(){
		return customerService.findAll();
	}
	
	@GetMapping("/customers/page/{page}")
	public Page<Customer> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 4);
		return customerService.findAll(pageable);
	} 
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/customers/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		
		Customer customer = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			customer = customerService.findById(id);
		} catch (DataAccessException e) {
			response.put("message", "Error in database query");
			response.put("error",e.getMessage()
					.concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(customer == null) {
				response.put("message", "Customer ID: ".concat(id.toString()
						.concat(" Does not exist in the database")));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/customers")
	public ResponseEntity<?> create(@Valid @RequestBody Customer customer,BindingResult result) {
		
		Customer customerNew = null;
		Map<String,Object> response = new HashMap<>();

		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field ¿" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			customerNew = customerService.save(customer);			
		} catch (DataAccessException e) {
			response.put("message", "Error when inserting into the database");
			response.put("error", e.getMessage().concat(": ")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			response.put("message", "Customer was successfully created");
			response.put("customer", customerNew);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/customer/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Customer customer, 
			BindingResult result,@PathVariable Long id) {
		
		Customer customerRecovered = customerService.findById(id);
		Customer customerUpdated = null;
		
		Map<String,Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors",errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(customerRecovered ==null) {
			response.put("message", "Error: cannot be edited, client ID: "
					.concat(id.toString().concat(" Does not exist in the database")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			customerRecovered.setName(customer.getName());
			customerRecovered.setLastname(customer.getLastname());
			customerRecovered.setEmail(customer.getEmail());
			customerRecovered.setCreateAt(customer.getCreateAt());
			customerRecovered.setRegion(customer.getRegion());
			
			customerUpdated = customerService.save(customerRecovered);
			
		} catch (DataAccessException e) {
			response.put("message","Error the client you are looking for does not exist");
			response.put("error", e.getMessage().concat(": ")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Customer was successfully upgraded");
		response.put("customer",customerService.save(customerUpdated));
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String,Object> response = new HashMap<>();
		
		
		try {
			Customer customer = customerService.findById(id);
			String namePreviousPhoto = customer.getPhoto();
			
			uploadService.remove(namePreviousPhoto);
			
			customerService.delete(id);
			
		} catch (DataAccessException e) {
			response.put("message", "Error deleting customer from database");
			response.put("error", e.getMessage().concat(": ")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Customer was successfully removed");
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/customers/upload")
	public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file, 
			@RequestParam("id") Long id){
		
		Map<String,Object> response = new HashMap<>();
		Customer customer = customerService.findById(id);
		
		if(!file.isEmpty()) {
			String nameFile = null;
			
			try {
				nameFile = uploadService.copy(file);
			} catch (IOException e) {
				response.put("message", "Error uploading customer image ");
				response.put("error", e.getMessage().concat(" : ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String namePreviousPhoto = customer.getPhoto();
			
			uploadService.remove(namePreviousPhoto);
			
			customer.setPhoto(nameFile);
			
			customerService.save(customer);
			
			response.put("customer", customer);
			response.put("message", "You have correctly uploaded the image: " + nameFile);
		}
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/img/{namePhoto:.+}")
	public ResponseEntity<Resource> viewPhoto(@PathVariable String namePhoto){
		
		Resource resource = null;
		
		try {
			resource = uploadService.upload(namePhoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(resource,header,HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/customer/regions")
	public List<Region> listRegions(){
		return customerService.findAllRegions();
	}
}
