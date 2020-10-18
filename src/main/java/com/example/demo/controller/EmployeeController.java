package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	//Get All Employee
	@GetMapping("/employees")
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
	}
	
	//create New Employee
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee emp) {
		return employeeRepository.save(emp);
	}
	
	//get Employee by Id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee= employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee with ID:-"+id+" does not exist"));
		return ResponseEntity.ok(employee);
	}
	
	//update Employee 
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
		
		//retrieve employee by id to update that employee
		Employee emp= employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee with ID:-"+id+" does not exist"));
		
		//set the updated details
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setEmailId(employee.getEmailId());
		
		//save the updated employee in db and return status
		Employee updatedEmployee=employeeRepository.save(emp);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	//delete emnployee
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		
		//retrieve employee by id to check that employee is present
				Employee emp= employeeRepository.findById(id).
						orElseThrow(()-> new ResourceNotFoundException("Employee with ID:-"+id+" does not exist"));
				
		//delete employee and sent the respose to client with key-value 		
		employeeRepository.delete(emp);
		Map<String, Boolean>response=new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	
}
