package com.crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.crud.model.Applicant_Data;
import com.crud.service.ApplicantService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/applicant")    //http://localhost:9999/customer/

public class ApplicantController {

	@Autowired
	ApplicantService as;
					
	@GetMapping(value = "/getApplicantData")	//http://localhost:9027/applicant/getApplicantData
	public ResponseEntity<List<Applicant_Data>> getApplicant()
	{
		log.info("checking method calling using @Slf4j :");
		List<Applicant_Data> list=as.viewApplicant();
		if(list.isEmpty())
		{
			//Long b=20/pid; //for controller based exception handling without try catch
			try {
				//Long a=10/cust_id;		//run with 0 id and empty db
			} catch (Exception e) {
				return new ResponseEntity<List<Applicant_Data>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<List<Applicant_Data>>(HttpStatus.NO_CONTENT); //run with 1 id and empty db
		}
		else {
			return new ResponseEntity<List<Applicant_Data>>(list, HttpStatus.OK);
		}
	}
	
	//controller based exception handling
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<String> ArithmaticExceptionHandler(ArithmeticException a){
		return new ResponseEntity<String>("Arithmatic Exception Occurs", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@PostMapping(value = "/postApplicant")	//http://localhost:9999/applicant/postApplicant
	public ResponseEntity<Applicant_Data> saveCustomer(@RequestBody Applicant_Data a){
		Applicant_Data c1=as.saveApplicant(a);	
		
		return new ResponseEntity<Applicant_Data>(c1, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping(value = "/deleteApplicant/{cust_id}")	//http://localhost:9999/applicant/deleteApplicant/2
	public ResponseEntity<String> deleteApplicant(@PathVariable("cust_id") Long cust_id)		//must use id for delete
	{
		as.deleteData(cust_id);
		return new ResponseEntity<String>("delete Data Successfully ", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/getCustById/{cust_id}")		//http://localhost:9999/applicant/getCustById/7
	public ResponseEntity<Optional<Applicant_Data>> getProByid(@PathVariable("cust_id") Long id)
	{
		Optional<Applicant_Data> op=as.editById(id);
		return new ResponseEntity<Optional<Applicant_Data>>(op, HttpStatus.OK);
	}
	
	@PutMapping("/updateById/{cust_id}")		//http://localhost:9999/applicant/updateById/2
	public ResponseEntity<String> updateCustById(@PathVariable ("cust_id") Long id, @RequestBody Applicant_Data a) 
	{
		Optional<Applicant_Data> up=as.editById(id);	
		Applicant_Data ct=as.updateData(id, a);
				
		return new ResponseEntity<String>("Updated Successfully", HttpStatus.CREATED);
	}
	    	
	    
	
}
