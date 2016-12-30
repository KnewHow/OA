var kynamic = {
		
		/**
		 * 对kynamicTree的所有操作
		 */
		kynamicTree:{
			pNode:'',
			zTree:'',
			setting: {
                isSimpleData: true,
                treeNodeKey: "kid",
                treeNodeParentKey: "pid",
                showLine: true,
                root: {
                    isRoot: true,
                    nodes: []
                },
                
                callback:{
                	rightClick:function(event, treeId, treeNode){//右击触发的函数
                		
                		//把当前节点赋值，便于重用
                		kynamic.kynamicTree.pNode = treeNode;
                		/**
                		 * 如果是文件夹节点
                		 */
                		if(treeNode.isParent){
                			kynamic.kynamicTree.controlRightMenu({
                				x:event.clientX,
    							y:event.clientY,
                				addFolder:true,
                				addFile:true,
                				deleteNode:true,
                				updateNode:true,
                			});
                		}else{
                			kynamic.kynamicTree.controlRightMenu({
                				x:event.clientX,
    							y:event.clientY,
                				addFolder:false,
                				addFile:false,
                				deleteNode:true,
                				updateNode:true,
                			});
                		}
                	}
                }
			},
			
			loadKynamicTree:function(){
				$.post("kynamicAction_showAllKynamics",null,function(data){
					
					kynamic.kynamicTree.zTree=$("#kynamicTree").zTree(kynamic.kynamicTree.setting,data.kynamicList);
				});
			},
			
			controlRightMenu:function(json){
				
				$("#showRMenu").show();
				$("#rMenu").css({"top":json.y+"px", "left":json.x+"px", "display":"block"});
				
				if(json.addFolder){
					$("#addFolder").show();
				}else{
					$("#addFolder").hide();
				}
				
				if(json.addFile){
					$("#addFile").show();
				}else{
					$("#addFile").hide();
				}
				
				if(json.deleteNode){
					$("#deleteNode").show();
				}else{
					$("#deleteNode").hide();
				}
				
				if(json.updateNode){
					$("#updateNode").show();
				}else{
					$("#updateNode").hide();
				}
			},
			
			/**
			 * 添加节点
			 * 1、向数据库添加一行数据
			 * 2、使用js进行添加
			 */
			addNode:function(json){
				var nodeName = window.prompt(json.message);
				
				//判断节点名称是否重复
				if(nodeName!=null){
					if(nodeName!=""){
						var ppid= kynamic.kynamicTree.pNode.kid;
						$.post("kynamicAction_isExsitByName.action",{name:nodeName,pid:ppid},function(data){
							if(data.message=="1"){
								var params={
									name:nodeName,
									pid:kynamic.kynamicTree.pNode.kid,
									isParent:json.isParent
								};
								$.post("kynamicAction_saveKynamic",params,function(data2){
									//在无刷新的情况下添加子节点
									var newNode={
										kid:data2.kid,
										pid:kynamic.kynamicTree.pNode.kid,
										isParent:json.isParent,
										name:nodeName,
									};
									kynamic.kynamicTree.zTree.addNodes(kynamic.kynamicTree.pNode,newNode,true);
								});
							}else{
								alert("该文件名称已经存在，请重新输入");
							}
						});
					}else{
						alert(json.errorMessage);
					}
				}
				
				
			},
			
			/**
			 * 添加文件夹
			 */
			addFolder:function(){
				kynamic.kynamicTree.addNode({
					message:'请输入文件夹名称',
					isParent:true,
					errorMessage:'文件夹名称不能为空'
				});
			},
			
			/**
			 * 添加文件
			 */
			addFile:function(){
				kynamic.kynamicTree.addNode({
					message:'请输入文件名称',
					isParent:false,
					errorMessage:'文件名称不能为空'
				});
			},
			
			/**
			 * 删除节点
			 * 1、如果是文件节点，直接删除
			 * 2、如果是文件夹节点，看文件节点下面是否有路径，有，不能删除，没有进行删除
			 */
			deleteNode:function(){
				if(kynamic.kynamicTree.pNode.isParent){
					if(kynamic.kynamicTree.zTree.getNodeByParam("pid",kynamic.kynamicTree.pNode.kid)){
						alert("该文件夹下面还有文件，不能删除");
					}else{
						if(window.confirm("确定要删除吗?")){
							var params={
									kid:kynamic.kynamicTree.pNode.kid
							};
							$.post("kynamicAction_showSiblingNodes.action",params,function(data1){
								if(data1.kynamicList.length<2){//没有兄弟节点
									//获取父节点
									$.post("kynamicAction_showParentNode.action",params,function(data2){
										var pNode=kynamic.kynamicTree.zTree.getNodeByParam("kid",data2.kynamic.kid);
										//进行删除操作
										$.post("kynamicAction_deleteNodeById.action",params,function(data3){
											kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
											pNode.isParent=true;
											kynamic.kynamicTree.zTree.refresh();
											alert(data3.message);
										});
									});
									
								}else{//如果有兄弟节点，直接删除
									$.post("kynamicAction_deleteNodeById.action",params,function(data3){
										kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
										kynamic.kynamicTree.zTree.refresh();
										alert(data3.message);
									});
								}
							});
						}
						
					}
				}else{
					/**
					 * 删除文件
					 * 1、查看是否有兄弟节点，如果少于两个(只有自己)，获取父节点，删除之后把父节点的isParent设置为true
					 * 2、如果有多余两个父节点，就直接删除，当然也可以需要这么麻烦，在setting直接设置keepParent:true,，即可，但是我们是练习，增加难度
					 */
					if(window.confirm("确定要删除吗?")){
						var params={
								kid:kynamic.kynamicTree.pNode.kid
						};
						$.post("kynamicAction_showSiblingNodes.action",params,function(data1){
							if(data1.kynamicList.length<2){//没有兄弟节点
								//获取父节点
								$.post("kynamicAction_showParentNode.action",params,function(data2){
									var pNode=kynamic.kynamicTree.zTree.getNodeByParam("kid",data2.kynamic.kid);
									//进行删除操作
									$.post("kynamicAction_deleteNodeById.action",params,function(data3){
										kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
										pNode.isParent=true;
										kynamic.kynamicTree.zTree.refresh();
										alert(data3.message);
									});
								});
								
							}else{//如果有兄弟节点，直接删除
								$.post("kynamicAction_deleteNodeById.action",params,function(data3){
									kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
									kynamic.kynamicTree.zTree.refresh();
									alert(data3.message);
								});
							}
						});
					}
					
				}
			},
			
			/**
			 * 修改节点
			 */
			updateNode:function(){
				var newName = window.prompt("请输入新的名称",kynamic.kynamicTree.pNode.name);
				$.post("kynamicAction_isExsitByName.action",{name:newName},function(data){
					if(data.message=="1"){
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
						alert("您输入的文件名称和以前的一样");
					}
				});
			}
			
		},
		
		/**
		 * 对版本的所有操作
		 */
		version:{
			
		}
};

$().ready(function(){
	kynamic.kynamicTree.loadKynamicTree();
	$("#rMenu").hover(function(){//悬停进入
		/**
		 * 声明事件，并不是触发
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
	
	function(){//悬停离开
		$("#rMenu").hide();
	});
	
});