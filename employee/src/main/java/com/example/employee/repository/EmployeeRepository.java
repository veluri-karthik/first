package com.example.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	@Query(value="select e from Employee e where e.id=?1 ")
	Employee getEmployeeById(Long employeeId);
	
	@Query(value="select e from Employee e where e.name=?1 and e.password=?2 ")
	Employee getEmployeeByName(String username,String password);

}
