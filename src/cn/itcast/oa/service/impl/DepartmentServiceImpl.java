package cn.itcast.oa.service.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.DepartmentDao;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.service.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	@Resource(name="departmentDao")
	private DepartmentDao departmentDao;
	@Transactional(readOnly=false)
	public void saveDepartment(Department department) {
		// TODO Auto-generated method stub
		this.departmentDao.saveDepartment(department);
	}
	@Transactional(readOnly=false)
	public void updateDepartment(Department department) {
		// TODO Auto-generated method stub
		this.departmentDao.updateDepartment(department);
	}
	@Transactional(readOnly=false)
	public void deleteDepartmentById(Serializable id,String deleteMode) {
		// TODO Auto-generated method stub
		this.departmentDao.deleteDepartmentById(id,deleteMode);
	}
	
	public Collection<Department> getAllDepartments() {
		return this.departmentDao.getAllDepartments();
	}

	public Department getDepartmentById(Serializable id) {
		return this.departmentDao.getDepartmentById(id);
	}
	
	

}
