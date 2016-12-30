package cn.itcast.oa.dao;

import java.io.Serializable;
import java.util.Collection;

import cn.itcast.oa.dao.base.BaseDao;
import cn.itcast.oa.domain.Department;

public interface DepartmentDao<T> extends BaseDao<T>{

	public void saveDepartment(Department department);
	public void updateDepartment(Department department);
	public void deleteDepartmentById(Serializable id,String deleteMode);
	public Collection<Department> getAllDepartments();
	public Department getDepartmentById(Serializable id);
}
