package com.example.gateway.config;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppServiceRepository extends JpaRepository<AppServices, Long>{
  
	@Query(value="SELECT * FROM appservices a LEFT JOIN menu_master mm  ON a.menu_id=mm.id LEFT JOIN role_menu rm ON rm.menu_id=mm.id LEFT JOIN role_master rl ON rm.id=rl.id LEFT JOIN \r\n"
			+ "employee_role er ON er.role_id=rl.id LEFT JOIN employee e ON e.id=er.employee_id WHERE e.name=?1",nativeQuery = true)
	public List<AppServices> getAppServicesByUserName(String userName);
	
//	@Query(value="")
//	public List<AppServices> getAppServicesByUserName(String userNmae);
}
