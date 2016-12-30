var privilege={
		
		/**
		 * 初始化事件
		 */
		init:{
			
			initEvent:function(){
				//超链接的click事件
				$("a").each(function(){//对超链接标签进行遍历
					if($(this).text()=="设置权限"){
						$(this).unbind("click");
						$(this).bind("click",function(){
							/**
							 * 1、显示隐藏的div
							 * 2、显示用户名
							 * 3、加载权限树
							 */
							privilege.pFunction.divOption.showDiv();
							//把this设置为initData的调用对象
							privilege.init.initData.call(this);
							privilege.pFunction.userOption.showUsername();
							privilege.pFunction.privilegeTree.loadPrivilegeTree();
							//防止刷新
							return false;
						});
					}
				});
				
				/**
				 * 全选事件
				 */
				$("#allchecked").unbind("click");
				$("#allchecked").bind("click",function(){
					privilege.pFunction.privilegeTree.selectAll();
				});
				
				/**
				 * 保存事件
				 */
				$("#savePrivilege").unbind("click");
				$("#savePrivilege").bind("click",function(){
					privilege.pFunction.privilegeTree.savePrivilge();
				});
			},
			
			
			
		
		
		/**
		 * 初始化数据
		 */
			initData:function(){
				//alert($(this).parent().siblings("td:first").text());
				//alert($(this).parent().siblings("input[type='hidden']:first").val());
				var username = $(this).parent().siblings("td:first").text();
				var uid = $(this).parent().siblings("input[type='hidden']:first").val();
				privilege.data.user.uid = uid;
				privilege.data.user.username=username;
			}
		},
		/**
		 * 按功能划分
		 */
		pFunction:{
			
			/**
			 * 对权限树的所有操作
			 */
			privilegeTree:{
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
	                //是权限树带有复选框
	                checkable: true,
	                
	                checkType:{
	                	"Y":"p",
	                	"N":"s"
	                },
	                
	                callback:{
	                	//在点击复选框之前做的事情
	                	beforeChange:function(treeId, treeNode){
	                		/**
	                		 * 在点击复选框之前，要设置我们自己定义的规则
	                		 */
	                		privilege.pFunction.privilegeTree.controlRule({
	                			"Y":"p",
	    	                	"N":"s"
	                		});
	                	},
	                	
	                	/**
	                	 * 在每次选择节点的时候，也需要进行判断，判断节点是不是都被选中
	                	 * 如果都被选中，全选按钮选中，否则全选按钮不被选中
	                	 */
	                	change:function(){
	                		if(privilege.pFunction.privilegeTree.zTree.getCheckedNodes(false).length==0){//如果没有被选中的节点是0个，则就是全选
								$("#allchecked").attr("checked",true);
							}else{
								$("#allchecked").attr("checked",false);
							}
	                	}
	                }
	                
				},
	                
	                
				/**
				 * 加载权限树
				 */
				loadPrivilegeTree:function(){
					$.post("privilegeAction_showAllPrivileges.action",null,function(data){
						privilege.pFunction.privilegeTree.zTree=$("#privilegeTree").zTree(privilege.pFunction.privilegeTree.setting,data.privilegeList);
						/**
						 * 如何判断全选按钮是否应该被选上
						 * 1、在点击权限设置之后就应该判断
						 * 2、必须要在权限树加载之后执行
						 * 所有放在这里是最好的地方
						 */
						if(privilege.pFunction.privilegeTree.zTree.getCheckedNodes(false).length==0){//如果没有被选中的节点是0个，则就是全选
							$("#allchecked").attr("checked",true);
						}else{
							$("#allchecked").attr("checked",false);
						}
					});
				},
				
				selectAll:function(){
					//在全选之前设置状态，让自定义状态消失，使用全选功能
					privilege.pFunction.privilegeTree.controlRule({
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
				 *自定义权限
				 * @param checkType
				 */
				controlRule:function(checkType){
					var setting = privilege.pFunction.privilegeTree.zTree.getSetting();
					setting.checkType = checkType;
					privilege.pFunction.privilegeTree.zTree.updateSetting(setting);
				},
				
				savePrivilge:function(){
					
				}
				
			},
			
			/**
			 * 对用户的操作
			 */
			userOption:{
				/**
				 * 显示用户名
				 */
				showUsername:function(){
					$("#userImage").text(privilege.data.user.username);
				},
				
			},
			
			/**
			 * 对隐藏div的操作
			 */
			divOption:{
				showDiv:function(){
					$("#userTitle").show();
					$("#privilegeTitle").show();
					$("#privilegeContent").show();
				}
			}
		},
		
		/**
		 * 数据封装
		 */
		data:{
			user:{
				username:'',
				uid:''
			}
		}
};

$().ready(function(){
	privilege.init.initEvent();
});