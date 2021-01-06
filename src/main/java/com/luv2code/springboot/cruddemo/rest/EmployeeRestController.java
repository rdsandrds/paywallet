package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private final EmployeeService employeeService;

	// Quick & Dirty: Inject Employee DAO
	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// Expose "/employees" & return list of employees
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return this.employeeService.findAll();
	}

	// Add mapping for GET /employees/{employeeId}
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable(name = "employeeId") int eId) {

		Employee theEmployee = this.employeeService.findById(eId);

		if (theEmployee == null) {
			throw new RuntimeException("Employee id not found - " + eId);
		}

		return theEmployee;
	}

	// Add mapping for POST /employees - add new employee
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {

		// Also just in case user pass an id in JSON, set id to 0
		// This is to force a save of new item instead of update

		theEmployee.setId(0);

		this.employeeService.save(theEmployee);

		return theEmployee;
	}

	// Add mapping for PUT /employees - update existing employee
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {

		this.employeeService.save(theEmployee);

		return theEmployee;
	}

	// Add mapping for DELETE /employees/{employeeId} - delete employee
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable(name = "employeeId") int eId) {

		Employee tempEmployee = this.employeeService.findById(eId);

		if (tempEmployee == null) {
			throw new RuntimeException("Employee id not found - " + eId);
		}

		this.employeeService.deleteById(eId);

		return "Delted employee id - " + eId;

	}
}
