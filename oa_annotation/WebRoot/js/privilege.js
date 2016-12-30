var privilege ={
		/**
		 * 所有初始化的操作
		 */
		init:{
			/**
			 * 所有初始化的事件
			 */
			initEvent:function(){
				//设置权限的click事件
				$("a").each(function(){
					if($(this).text()=="设置权限"){
						$(this).unbind("click");
						$(this).bind("click",function(){
							/**
							 * 1、显示所有div
							 * 2、动态显示用户名
							 * 3、加载动态权限树
							 */
							var hobj = this;
							//privilege.init.initData(hobj);
							//设置initData()方法的调用对象就是this，这样就不用传递参数了
							privilege.init.initData.call(this);
							privilege.pFunction.divOption.showDiv();//显示所有的div
							privilege.pFunction.userOption.showUsername();//动态显示用户名
							privilege.pFunction.privilegeTree.loadPrivilegeTree();//加载权限树
							//判断全选按钮是否需要全选不能在这，因为在这树还不确定是否已经加载
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
			 * 所有初始化的数据
			 */
			initData:function(){
				//alert($(hobj).parent().siblings("td:first").text());
				//alert($(hobj).parent().siblings("input[type='hidden']:first").val());
				var username = $(this).parent().siblings("td:first").text();
				var uid = $(this).parent().siblings("input[type='hidden']:first").val();
				privilege.data.user.uid=uid;
				privilege.data.user.username=username;
				
			}
		},
		
		/**
		 * 按照功能区域的划分
		 */
		pFunction:{
			
			privilegeTree:{//对所有权限树的操作
				zTree:'',
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
	                //设置点击规则
	                checkType:{
	                	"Y":"p",
	                	"N":"s"
	                },
	                
	                callback:{
	                	beforeChange:function(treeId, treeNode){//在点击树的复选框之前触发
	                		//在点击树的复选框之前，改变规则
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
				 * 显示权限树
				 */
				loadPrivilegeTree:function(){
					var params={
							uid:privilege.data.user.uid
					};
					
					$.post("privilegeAction_showPrivilegesByUid",params,function(data){
						alert(data);
						privilege.pFunction.privilegeTree.zTree=$("#privilegeTree").zTree(privilege.pFunction.privilegeTree.setting,data.privilegeList);
						/**
						 * 全选按钮是否被选中要在点击设置权限之后显示出来，
						 * 但是又需要在树加载之后，所有这里是判断全选按钮是否选中的最佳位置
						 */
						if(privilege.pFunction.privilegeTree.zTree.getCheckedNodes(false).length!=0){//如果没有全部选中
							$("#allchecked").attr("checked", false);
						}else{
							$("#allchecked").attr("checked", true);
						}
					});
				},
				
				/**
				 * 对权限树复选框的控制
				 */
				controlCheckBox:function(checkType){//设置setting的属性
					//获取当前树的setting
					var setting = privilege.pFunction.privilegeTree.zTree.getSetting();
					//前一个setting是属性，后一个数传递的值
					setting.checkType = checkType;
					privilege.pFunction.privilegeTree.zTree.updateSetting(setting);
				},
				/**
				 * 针对某个用户的保存权限
				 */
				savePrivilege:function(){
					var checkedNodes = privilege.pFunction.privilegeTree.zTree.getCheckedNodes(true);
					var mids="";
					var length = checkedNodes.length;
					for(var i=0;i<length;i++){
						if(i<length-1){
							mids = mids+checkedNodes[i].mid+",";
						}else{
							mids = mids+checkedNodes[i].mid;
						}
					}
					var params={
							uid:privilege.data.user.uid,
							mids:mids,
							aa:[1,2,3]
					};
					$.post("privilegeAction_savePrivilege.action",params,function(data){
						//alert(data);
					});
				},
			
				/**
				 * 全选复选框的实现
				 */
				checkAll:function(){
					/**
					 * 在点击全选的时候，设置下新的规则，让全选公共可以实现
					 * 为什么可以设置，因为只有当树加载出来的时候，你才会进行全选，所以，可以保证树加载
					 */
					privilege.pFunction.privilegeTree.controlCheckBox({
						"Y":"ps",
						"N":"ps"
					});
					if($("#allchecked").attr("checked")){
						privilege.pFunction.privilegeTree.zTree.checkAllNodes(true);
					}else{
						privilege.pFunction.privilegeTree.zTree.checkAllNodes(false);
					}
				}
			
			},
			
			//用户的所有操作
			userOption:{
				/**
				 * 动态显示用户名，把用户名显示到div中
				 */
				showUsername:function(){
					$("#userImage").text(privilege.data.user.username);
				}
			},
			
			//div的操作
			divOption:{
				/**
				 * 显示所有的div
				 */
				showDiv:function(){
					//把隐藏域变成显示
					$("#userTitle").show();
					$("#privilegeTitle").show();
					$("#privilegeContent").show();
				}
			}
		},
		
		/**
		 * json对象的数据封装
		 */
		data:{
			user:{//封装user数据
				uid:'',
				username:''
			}
		}
};

$().ready(function(){
	privilege.init.initEvent();
});