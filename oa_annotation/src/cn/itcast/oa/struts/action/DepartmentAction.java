package cn.itcast.oa.struts.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.domain.Department;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.struts.action.base.BaseAction;
import cn.itcast.oa.utils.DeleteModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.ObjectFactory;
import com.sun.org.apache.regexp.internal.recompile;

@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{

	/*
	 * 实现ModelDriven，就是为了把ModelDriven放入栈顶，因为我们知道，action默认放入栈顶
	 * 我们可以去访问action里面的属性department，但是我们无法去访问department里面属性
	 * 而ModelDriven，就把department放入栈顶
	 */
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	public String getAllDeparetments(){
		Collection<Department> departments = this.departmentService.getAllDepartments();
		//把departmentList放入到值栈中
				/**
				 * 值栈
				 *   *  值栈的生命周期
				 *        值栈的生命周期就是一次请求
				 *   *  值栈的数据结构
				 *        对象栈
				 *        map栈
				 *   *  对象栈和map栈有什么区别
				 *        对象栈是一个list
				 *        map栈是一个map
				 *   *  怎么样把一个数据放入到map栈中
				 *   *  怎么样把一个数据放入到对象栈中
				 *   		
				 *   *  对象栈中的数据有什么样的特殊之处
				 */
				//把departmentList放入到了对象栈的栈顶
				//ActionContext.getContext().getValueStack().push(departmentList);
				//把departmentList放入到了对象栈的栈顶
				//ActionContext.getContext().getValueStack().getRoot().add(0, departmentList);
				//把departmentList放入到了对象栈的栈底
				//ActionContext.getContext().getValueStack().getRoot().add(departmentList);
				//获取对象栈的栈顶的元素
				//ActionContext.getContext().getValueStack().peek();
				//移除对象栈的栈顶的元素
				//ActionContext.getContext().getValueStack().pop();
				//移除对象栈的栈顶的元素
				//ActionContext.getContext().getValueStack().getRoot().remove(0);
				//把一个map放入到对象栈的栈顶
				//ActionContext.getContext().getValueStack().set(key, o);
				/**
				 * 对象栈的说明
				 *    *  处于对象栈的对象中的属性是可以直接访问的
				 *    *  如果在对象栈中有一样名称的属性，从栈顶开始查找，直到找到为止
				 *    *  一般情况下，回显的数据应该放在对象栈中，这样效果比较高
				 *    *  用ognl表达式访问对象栈，直接属性名称就可以了，不用加#
				 */
				
				//map栈
				/**
				 * 说明
				 *   *  reuqest,session,application都在map栈中
				 *   *  可以把一个对象放入到map中
				 *   *  ognl表达式访问map栈中的内容
				 *       如果一个对象在request中
				 *          #request.对象的key值.属性
				 *       如果一个对象直接放入到map中
				 *          #对象的key值.属性
				 *       把一个对象放入到map栈中，是不能直接访问该对象的属性
				 */
				//把一个对象存放到map栈中
		
		
		//如何迭代list中的list
//		List<List<Department>> lists = new ArrayList<List<Department>>();
//		List<Department> departmentList1 = new ArrayList<Department>();
//		List<Department> departmentList2 = new ArrayList<Department>();
//		Department department1 = new Department();
//		department1.setDname("department1_dname");
//		Department department2 = new Department();
//		department2.setDname("department2_dname");
//		departmentList1.add(department1);
//		departmentList2.add(department2);
//		lists.add(departmentList1);
//		lists.add(departmentList2);
		
		//如何迭代list中的map
//		List<Map<String, Department>> mapList = new ArrayList<Map<String,Department>>();
//		Map<String, Department> map1 = new HashMap<String, Department>();
//		Map<String, Department> map2 = new HashMap<String, Department>();
//		map1.put("d1", department1);
//		map2.put("d2", department2);
//		mapList.add(map1);
//		mapList.add(map2);
		
		//如何迭代map中list
//		Map<String, List<Department>> listMap = new HashMap<String, List<Department>>();
//		listMap.put("list1", departmentList1);
//		listMap.put("list2", departmentList2);
//		ActionContext.getContext().put("listMap", listMap);
		
		ActionContext.getContext().put("departments", departments);
		return listAction;
		
	}
	
	public String deleteDepartment(){
		this.departmentService.deleteDepartmentById(this.getModel().getDid(), DeleteModel.DEL_CASCADE);
		return actionToAction;
	}
	
	public String addUI(){
		return addUI;
	}
	
	public String add(){
		Department department = this.getDepartment();
		this.departmentService.saveDepartment(department);
		return actionToAction;
	}

	public String updateUI(){
		Department department = this.departmentService.getDepartmentById(this.getModel().getDid());
		//把对象放入栈顶，便于回显
		/*如果放在map栈，使用value="%{#department.dname}"进行回显
		ActionContext.getContext().put("department", department);
		*/
		ActionContext.getContext().getValueStack().push(department);
		
		/*
		 * 1、对于回显问题的说明：放在对象栈中数据或者对象可以使用name属性回显
		 * 2、放在map中，需要使用value属性来回显
		 */
		return updateUI;
	}
	
	public String update(){
		/*
		 * 1、首先根据id查询出数据库的apartment对象
		 * 2、把修改以后的数据保存进该对象中
		 * 3、针对该对象进行update操作
		 */
		Department department = this.departmentService.getDepartmentById(this.getModel().getDid());
		//更新数据
		BeanUtils.copyProperties(this.getModel(), department);
		this.departmentService.updateDepartment(department);
		return actionToAction;
	}
	
	
	/**
	 * 重新获取一个Department，因为model是action里面的东西，尽量不要把action里面的东西
	 * 放入dao或者service
	 * @return
	 */
	private Department getDepartment(){
		Department department = new Department();
		BeanUtils.copyProperties(this.getModel(), department);
		return department;
	}

	public void fun1(){
		System.out.println("子类的fun1");
	}
	
}
