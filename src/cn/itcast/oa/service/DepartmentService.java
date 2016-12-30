package cn.itcast.oa.service;

import java.io.Serializable;
import java.util.Collection;

import cn.itcast.oa.domain.Department;

public interface DepartmentService {

	public void saveDepartment(Department department);
	public void updateDepartment(Department department);
	public void deleteDepartmentById(Serializable id,String deleteMode);
	public Collection<Department> getAllDepartments();
	public Department getDepartmentById(Serializable id);
}
