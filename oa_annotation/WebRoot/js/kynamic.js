var kynamic = {
		kynamicTree:{//对知识管理树的操作
			pNode:'',
			zTree:'',
			setting: {
//				keepParent:true,//这样就可以避免文件夹节点因为删除变成文件节点，但是为了训练更多，我们使用复杂的方法
                isSimpleData: true,
                treeNodeKey: "kid",
                treeNodeParentKey: "pid",
                showLine: true,
                root: {
                    isRoot: true,
                    nodes: []
                },
                
                callback:{
                	rightClick:function(event, treeId, treeNode){
                		
                		/**
                		 * 在点击右键的时候，把treeNode(当前节点)的值赋给pNode
                		 */
                		kynamic.kynamicTree.pNode = treeNode;
                		
                		//判断是否是文件夹节点
                		if(treeNode.isParent){
                			 kynamic.kynamicTree.controlRightMenu({
                				x:event.clientX,
    							y:event.clientY,
                				addFile:true,
                				addFolder:true,
                				deleteNode:true,
                				updateNode:true
                			});
                		}else{
                			kynamic.kynamicTree.controlRightMenu({
                				x:event.clientX,
    							y:event.clientY,
                				addFile:false,
                				addFolder:false,
                				deleteNode:true,
                				updateNode:true
                			});
                		}
                	},
                
                click:function(event, treeId, treeNode){//树的点击事件
                	kynamic.kynamicTree.pNode = treeNode;
                	var params={
                			kid:kynamic.kynamicTree.pNode.kid
                	};
                	//请求服务器，获取版本的数据
                	$.post("kynamicAction_showVersionsByKid",params,function(data){
                		if(data.versionList.length==0){//没有版本
                		}else{//有版本
                			kynamic.version.controlVersion({//控制输入
                				addVersion:false,
                				versionList:true,
                				checkin:false,
                				checkout:false,
                			});
                			
                			kynamic.version.showVersionByKid(data.versionList);
                		}
                	});
                }

                }
			},
			
			loadKynamicTree:function(){
				$.post("kynamicAction_showAllKynamics.action",null,function(data){
					kynamic.kynamicTree.zTree=$("#kynamicTree").zTree(kynamic.kynamicTree.setting,data.kynamicList);
				});
			},
			
			/**
			 * 控制右键显示菜单
			 * 	1、div菜单位置
			 * 	2、菜单的项目
			 */
			controlRightMenu:function(rMenuJson){
				/**
				 * 菜单项的显示逻辑
				 */
				$("#showRMenu").show();
				$("#rMenu").css({"top":rMenuJson.y+"px", "left":rMenuJson.x+"px", "display":"block"});
				
				//判断是否显示文件
				if(rMenuJson.addFile){
					$("#addFile").show();
				}else{
					$("#addFile").hide();
				}
				
				//判断是否显示文件夹
				if(rMenuJson.addFolder){
					$("#addFolder").show();
				}else{
					$("#addFolder").hide();
				}
				
				//判断是否显示删除节点
				if(rMenuJson.deleteNode){
					$("#deleteNode").show();
				}else{
					$("#deleteNode").hide();
				}
				
				//判断是否显示修改节点
				if(rMenuJson.updateNode){
					$("#updateNode").show();
				}else{
					$("#updateNode").hide();
				}
			},
			
			addNode:function(addJson){
				/**
				 * 在数据库中添加数据
				 * 在指定父节点下面添加子节点
				 */
				var folderName = window.prompt(addJson.fileMessage);
				if(folderName!=null){
					if(folderName!=""){
						//请求服务器，查看文件名称是否重复
						$.post("kynamicAction_isExsitByName.action",{name:folderName},function(data1){
							if(data1.message=='1'){
								var params={
										name:folderName,//文件名称
										isParent:addJson.isParent,//是否是父节点
										pid:kynamic.kynamicTree.pNode.kid//父节点id
								};
								$.post("kynamicAction_saveKynamic.action",params,function(data){
									//在无刷新的条件下，添加子节点
									var kid = data.kid;
									var newNode={
										kid:kid,
										pid:kynamic.kynamicTree.pNode.kid,
										isParent:addJson.isParent,
										name:folderName
									};
									kynamic.kynamicTree.zTree.addNodes(kynamic.kynamicTree.pNode,newNode,true);
								});
							}else{
								alert("文件名称已经存在");
							}
						});
						
					}else{
						alert(addJson.errorMessage);
					}
				}
			},
			
			//添加文件夹
			addFolder:function(){
				kynamic.kynamicTree.addNode({
					fileMessage:'请输入文件夹名称',
					isParent:true,
					errorMessage:'文件夹名称不能为空'
				});
			},
			
			//添加文件
			addFile:function(){
				kynamic.kynamicTree.addNode({
					fileMessage:'请输入文件名称',
					isParent:false,
					errorMessage:'文件名称不能为空'
				});
			},
			
			//删除节点
			deleteNode:function(){
				/**
				 * 判断是不是文件节点，
				 * 	是：直接删除
				 * 	否：
				 * 		判断文件夹下面是是不是有子节点
				 * 		没有：删除文件夹
				 * 		有：提示不能删除
				 */
				if(kynamic.kynamicTree.pNode.isParent){//文件夹节点
					//如果正在删除的节点有子节点
					 if (kynamic.kynamicTree.zTree.getNodeByParam("pid", kynamic.kynamicTree.pNode.kid)) {
						alert("文件夹下面还有文件，不能直接删除");
					}else{
						if(window.confirm("您确认要删除吗?")){
							var params={
									kid:kynamic.kynamicTree.pNode.kid
							};
							
							/**
							 * 1、首先判断该节点是否有兄弟节点，
							 * 	如果没有，那么就去获取其父节点，把父节点的isParent设置为true；
							 */
							$.post("kynamicAction_showSiblingNodes.action",params,function(data1){
								if(data1.kynamicList.length<2){//没有兄弟节点
									//获取其父节点，先删除，再设置父节点的isParent=true
									//kynamic.kynamicTree.zTree.getNodeByParam("kid",kynamic.kynamicTree.pNode.pid);这样也可以获取父节点，但是我们练习，增加难度
									$.post("kynamicAction_showParentNode.action",params,function(data2){
										var pNode = kynamic.kynamicTree.zTree.getNodeByParam("kid",data2.kynamic.kid);//获取父节点
										$.post("kynamicAction_deleteNodeById.action",params,function(data){
											kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
											pNode.isParent = true;
											kynamic.kynamicTree.zTree.refresh();//刷新
											alert(data.message);
										});
									});
								}else{//如果有兄弟节点
									$.post("kynamicAction_deleteNodeById.action",params,function(data){
										kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
										kynamic.kynamicTree.zTree.refresh();//刷新
										alert(data.message);
									});
								}
							});
							
						}
					}
				}else{//文件节点
					
					if(window.confirm("您确认要删除吗?")){
						var params={
								kid:kynamic.kynamicTree.pNode.kid
						};
						
						/**
						 * 1、首先判断该节点是否有兄弟节点，
						 * 	如果没有，那么就去获取其父节点，把父节点的isParent设置为true；
						 */
						$.post("kynamicAction_showSiblingNodes.action",params,function(data1){
							if(data1.kynamicList.length<2){//没有兄弟节点
								//获取其父节点，先删除，再设置父节点的isParent=true
								//kynamic.kynamicTree.zTree.getNodeByParam("kid",kynamic.kynamicTree.pNode.pid);这样也可以获取父节点，但是我们练习，增加难度
								$.post("kynamicAction_showParentNode.action",params,function(data2){
									var pNode = kynamic.kynamicTree.zTree.getNodeByParam("kid",data2.kynamic.kid);//获取父节点
									$.post("kynamicAction_deleteNodeById.action",params,function(data){
										kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
										pNode.isParent = true;
										kynamic.kynamicTree.zTree.refresh();//刷新
										alert(data.message);
									});
								});
							}else{//如果有兄弟节点
								$.post("kynamicAction_deleteNodeById.action",params,function(data){
									kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
									alert(data.message);
								});
							}
						});
						
					}
					
				}
			},
			
			//修改节点的名称
			updateNode:function(){
				var newName = window.prompt("请输入新的名称",kynamic.kynamicTree.pNode.name);
				$.post("kynamicAction_isExsitByName.action",{name:newName},function(data1){
					if(data1.message=='1'){
						var params={
								name:newName,
								kid:kynamic.kynamicTree.pNode.kid
						};
						$.post("kynamicAction_updateKynamic.action",params,function(data){
							kynamic.kynamicTree.pNode.name = newName;
							kynamic.kynamicTree.zTree.refresh();//刷新
							alert("修改成功");
							
						});
					}else{
						alert("文件名和以前的一样");
					}
				});
				
			}
		},
		
		
		version:{
			showVersionByKid:function(versionList){
				/**
				 <tr>
		    <td width="240" height="26" align="center" valign="middle" bgcolor="#FFFFFF" style="border-bottom:1px solid #f3f8fd;"><a>1</a></td>
		    <td width="232" align="center" valign="middle" bgcolor="#FFFFFF" style="border-bottom:1px solid #f3f8fd;">2010-5-24 09:56:33</td>
		    <td width="231" align="center" valign="middle" bgcolor="#FFFFFF" style="border-bottom:1px solid #f3f8fd;"><a>删除</a></td>
		  </tr>
				 */
				
				
				
				$("#showVersion").empty();
				for(var i=0;i<versionList.length;i++){
					(function(){
						var version = versionList[i].version;//版本号
						var updateTime = versionList[i].updateTime;
						
						var $versionA = $("<a/>");//定义超链接标签
						$versionA.text(version);
						
						//创建version的td和超链接
						var $versionATD = $("<td/>");
						$versionATD.attr("width","240");
						$versionATD.attr("height","26");
						$versionATD.attr("align","center");
						$versionATD.attr("valign","middle");
						$versionATD.attr("bgcolor","#FFFFFF");
						$versionATD.attr("style","border-bottom:1px solid #f3f8fd;");
						$versionATD.append($versionA);
						
						$versionA.unbind("click");
						$versionA.bind("click",function(){
							/**
							 * 1、首先，我们在这里只是声明一个点击事件，
							 * 因为我们的这个事件定义在回调函数里面
							 * 
							 * 但是用户在执行此事件的时候，回调函数的data值已经被释放
							 * 所有总结：在回调函数里面声明的事件，不能使用回调函数的形参。
							 * 所有必须先使用变量对值进行保存
							 * 2、因为在js中，不是在函数定义的变量剧院引用的特性，所有为了
							 * 使version保证不同的值，需要包代码封装到函数中
							 */
							alert(version);
						});
						
						
						
						//创建updateTime的td
						var $updateTD = $("<td/>");
						$updateTD.attr("align","center");
						$updateTD.attr("valign","middle");
						$updateTD.attr("bgcolor","#FFFFFF");
						$updateTD.attr("style","border-bottom:1px solid #f3f8fd;");
						$updateTD.text(updateTime);
						
						//创建删除的超链接和 TD
						
						var $deleteA = $("<a/>");
						$deleteA.text("删除");
						var $deleteTD = $("<td/>");
						$deleteTD.attr("align","center");
						$deleteTD.attr("valign","middle");
						$deleteTD.attr("bgcolor","#FFFFFF");
						$deleteTD.attr("style","border-bottom:1px solid #f3f8fd;");
						$deleteTD.append($deleteA);
						
						//创建TR
						var $TR = $("<tr/>");
						$TR.append($versionATD);
						$TR.append($updateTD);
						$TR.append($deleteTD);
						
						//把TR添加到table标签
						$("#showVersion").append($TR);
					})();
					
				}
				
			},
			
			//控制版本
			controlVersion:function(versionJson){
				if(versionJson.addVersion){
					$("#addVersion").show();
				}else{
					$("#addVersion").hide();
				}
				
				if(versionJson.versionList){
					$("#versionList").show();
				}else{
					$("#versionList").hide();
				}
				
				if(versionJson.checkin){
					$("#checkin").show();
				}else{
					$("#checkin").hide();
				}
				
				if(versionJson.checkout){
					$("#checkout").show();
				}else{
					$("#checkout").hide();
				}
				
				
			}
		}
};

$().ready(function(){
	kynamic.kynamicTree.loadKynamicTree();
	/**
	 * 这里的hover仅仅是事务的声明，具体的事件触发，要根据操作来触发
	 * 可以保证的是，只有树被加载成功的时候，用户才会使用悬停操作
	 */
	$("#rMenu").hover(function(){//进入悬停
		/**
		 * 声明增、删、改事件
		 */
		$("#addFolder").unbind("click");
		$("#addFolder").bind("click",function(){
			kynamic.kynamicTree.addFolder();
		});
		
		$("#addFile").unbind("click");
		$("#addFile").bind("click",function(){
			kynamic.kynamicTree.addFile();
		});
		
		$("#deleteNode").unbind("click");
		$("#deleteNode").bind("click",function(){
			kynamic.kynamicTree.deleteNode();
		});
		
		$("#updateNode").unbind("click");
		$("#updateNode").bind("click",function(){
			kynamic.kynamicTree.updateNode();
		});
	},
	function(){//出来悬停
		$("#rMenu").hide();
	}
	);
});