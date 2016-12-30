var privilege ={
		/**
		 * 初始化的东西全部在init里面完成
		 */
		init:{
			
			/**
			 * 初始化事件，所有的初始化事件都在initEvent完成
			 */
			initEvent:function(){
				//点击设置权限按钮的事件
				$("a").each(function(){
					if($(this).text()=="设置权限"){
						$(this).unbind("click");
						$(this).bind("click",function(){
							/**
							 * 在点击设置权限的时候，首先要初始化数据
							 * 1、显示div
							 * 2、显示用户名
							 * 3、加载权限树
							 * 
							 * 说明：使用nitData.call(param)，可以把
							 * initData的这个函数的调用者设置为param
							 * 
							 * 在这里进行调用，然后在在每个具体的方法里面进行实现，
							 * 做到了面向对象的变成
							 */
							
							privilege.init.initData.call(this);
							privilege.pFunction.divOption.showDiv();
							privilege.pFunction.userOption.showUsername();
							privilege.pFunction.privilegeTree.loadPrivilegeTree();
							return false;
						});
					}
				});
				
				/**
				 * 全选按钮事件
				 */
				$("#allchecked").unbind("click");
				$("#allchecked").bind("click",function(){
					privilege.pFunction.privilegeTree.checkAll();
				});
				
				/**
				 * 保存权限事件
				 */
				$("#savePrivilege").unbind("click");
				$("#savePrivilege").bind("click",function(){
					privilege.pFunction.privilegeTree.savePrivilege();
				});
				
			},

			/**
			 * 初始化数据，对数据的初始化操作全部在initData完成
			 */
			initData:function(){
				/*
				 * 使用jQuery赋值
				 * $(this).parent().siblings("td:first").text()
				 * 表示调用该方法的节点的父节点的兄弟节点中第一个的文本值
				 * 也就是<td><s:property value="username"/></td>
				 * $(this).parent().siblings("input[type='hidden']:first").val();
				 * 表示：调用此方法的节点的父节点的兄弟节点类型为input type=hidden的第一个的值
				 * 也就是    <s:hidden name="uid"></s:hidden>
				 * 
				 * 下面是具体的jsp页面的对应值
				 * <s:iterator>
			            <tr class="TableDetail1 template">
			                <td><s:property value="username"/></td>
			                <s:hidden name="uid"></s:hidden>
			                <td><s:property value="department.dname"/></td>
			                <td>
				                <s:iterator value="posts">
				                	<s:property value="pname"/>
				                </s:iterator>
			                 </td>
			                <td><a onClick="return delConfirm()" href="list.html">删除</a>
			                    <a href="saveUI.html">修改</a>
								<s:a>设置权限</s:a>
			                </td>
			            </tr>
			       	</s:iterator>
				 */
				var username = $(this).parent().siblings("td:first").text();
				var uid = $(this).parent().siblings("input[type='hidden']:first").val();
				//给data数据中数据赋值
				privilege.data.user.uid = uid;
				privilege.data.user.username=username;
				
			},
			
			
		},
		
		
		/**
		 * 所有功能的操作都是在这里面完成
		 */
		pFunction:{
			
			/**
			 * 所有和权限树有关的操作都在privilegeTree里面完成
			 */
			privilegeTree:{
				zTree:'',//设置一个zTree变量
				setting: {
	                isSimpleData: true,
	                treeNodeKey: "mid",
	                treeNodeParentKey: "pid",
	                showLine: true,
	                root: {
	                    isRoot: true,
	                    nodes: []
	                },
	                
	                checkable: true,
	                
	                //定义选中规则
	                checkType:{
	                	"Y":"p",
	                	"N":"s"
	                },
	                
	                callback:{
	                	//每次在选中之前，需要重新定义规则
	                	beforeChange:function(treeId, treeNode){
	                		privilege.pFunction.privilegeTree.controlCheckBox({
	                			"Y":"p",
	    	                	"N":"s"
	                		});
	                	},
	                	
	                	/**
	                	 * 节点点击的时候触发,如果已经全选了，那就把全选按钮选中，否则全选按钮不选中
	                	 * @param treeId
	                	 * @param treeNode
	                	 */
	                	change:function(treeId, treeNode){
	                		if(privilege.pFunction.privilegeTree.zTree.getCheckedNodes(false).length!=0){//如果没有全部选中
								$("#allchecked").attr("checked", false);
							}else{
								$("#allchecked").attr("checked", true);
							}
	                	}
	                }
	                
	               
				},
				
				/**
				 * loadPrivilegeTree：完成对的加载
				 */
				loadPrivilegeTree:function(){
					var params={
							uid:privilege.data.user.uid
					};
					//向服务器发送异步请求
					$.post("privilegeAction_showPrivilegesByUid",params,function(data){
						privilege.pFunction.privilegeTree.zTree=$("#privilegeTree").zTree(privilege.pFunction.privilegeTree.setting,data.privilegeList);
						//在权限树被加载以后，就要判断该权限树是不是全选
						if(privilege.pFunction.privilegeTree.zTree.getCheckedNodes(false).length!=0){//如果全选树没有被选中的不等于0，也就是没有全选
							$("#allchecked").attr("checked",false);
						}else{
							$("#allchecked").attr("checked",true);
						}
						 
					});
					
					
				},
				
				
				/**
				 * controlCheckBox：完成对树规则控制
				 */
				controlCheckBox:function(checkType){
					//获取当前树的setting
					var setting = privilege.pFunction.privilegeTree.zTree.getSetting();
					//前一个setting是属性，后一个数传递的值
					setting.checkType = checkType;
					privilege.pFunction.privilegeTree.zTree.updateSetting(setting);
				},
				
				
				/**
				 * checkAll：完成对树的全选操作
				 */
				checkAll:function(){
					privilege.pFunction.privilegeTree.controlCheckBox({
						"Y":"ps",
						"N":"ps"
            		});
					if($("#allchecked").attr("checked")){
						privilege.pFunction.privilegeTree.zTree.checkAllNodes(true);
					}else{
						privilege.pFunction.privilegeTree.zTree.checkAllNodes(false);
					}
				},
				
				/**
				 * savePrivilege：完成对树权限的保存
				 */
				savePrivilege:function(){
					
				}
			},
			
			/**
			 * userOption：完成所有的用户功能的实现
			 */
			userOption:{
				/**
				 * 显示用户名的操作
				 */
				showUsername:function(){
					$("#userImage").text(privilege.data.user.username);
				}
			},
			
			/**
			 * divOption：对div的所有操作
			 */
			divOption:{
				/**
				 * 显示div的所有操作
				 */
				showDiv:function(){
					$("#userTitle").show();
					$("#privilegeTitle").show();
					$("#privilegeContent").show();
				}
			}
			
		},
		
		
		/**
		 * 所有需要的数据都可以在这里面进行定义
		 */
		data:{
			user:{//user数据
				uid:'',
				username:''
			}
		}	
};

$().ready(function(){
	privilege.init.initEvent();
});