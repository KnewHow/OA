package cn.itcast.oa.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.itcast.oa.dao.DepartmentDao;
import cn.itcast.oa.dao.base.impl.BaseDaoImpl;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.utils.DeleteModel;

@Repository("departmentDao")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao<Department>{

	public void saveDepartment(Department department) {
		// TODO Auto-generated method stub
		this.saveEntry(department);
	}

	public void updateDepartment(Department department) {
		// TODO Auto-generated method stub
		this.updateEntry(department);
	}

	public void deleteDepartmentById(Serializable id,String deleteMode) {
		// TODO Auto-generated method stub
		Department department = (Department) this.getHibernateTemplate().get(Department.class, id);
		if(DeleteModel.DEL.equals(deleteMode)){
			this.getHibernateTemplate().delete(department);
		}else if(DeleteModel.DEL_PRE_RELEASE.equals(deleteMode)){
			Set<User> users = department.getUsers();
			for(User user:users){
				user.setDepartment(null);//解除关系
			}
			this.getHibernateTemplate().flush();
			this.getHibernateTemplate().delete(department);
		}else{//进行级联删除
			Set<User> users = department.getUsers();
			for(User user:users){
				user.setDepartment(null);//解除关系部门关系
				user.setPosts(null);//解除岗位关系
				this.getHibernateTemplate().flush();
				this.getHibernateTemplate().delete(user);
			}
			//删除部门
			this.getHibernateTemplate().delete(department);
		}
		
	}

	public Collection<Department> getAllDepartments() {
		return this.getAllEntries();
	}

	public Department getDepartmentById(Serializable id) {
		return this.getEntryById(id);
	}

}
