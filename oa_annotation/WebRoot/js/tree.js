var tree={
		//在tree对象中设置属性
		zTree:'',
		pNode:'',
		setting:{
			isSimpleData: true,
			treeNodeKey: "mid",
			treeNodeParentKey: "pid",
			showLine: true,
			root:{ 
				isRoot:true,
				nodes:[]
			},
			
			callback:{
				/*
				 * collback:是一个回调函数
				 * expand:在节点被鼠标点击导致展开后触发的
				 * 	参数
				 * 		*event:鼠标事件
				 * 		*treeId:树的容器ID
				 * 		*treeNode:当前点击的节点
				 */
				expand:function(event, treeId, treeNode){
					tree.pNode = treeNode;//给属性赋值
					tree.loadChildNodes();
				}
			}
			
		},
		//需求：异步加载子节点
		
		/*
		 * 1、回调函数由服务器触发，什么时候执行由服务器决定
		 * 2、回调函数的由jQuery内部调用
		 * 3、客户端存在两个线程，主线程和回调函数线程
		 * 4、在js中，有一些代码要使用回调函数里面数据，这些代码必须放在回调函数中
		 */
		loadTree:function(){
			$.post("menuitemAction_getAllMenuitems.action",null,function(data){
				$("#tree").zTree(tree.setting,data.menuitemList);
			});
			
		},
		
		//加载根节点
		loadRootNode:function(){
			var params={
				pid:0
			};
			
			$.post("menuitemAction_showMenuitemsByPid.action",params,function(data){
				//返回结果
				tree.zTree = $("#tree").zTree(tree.setting,data.menuitemList);
			});
		},
		
		/**
		 * 总结：1、
		 * 为什么可以在loadChildNodes，使用tree.zTree
		 * 首先要明确一点，所有的post都是异步，但是我们是在点击的时候出发loadChildNodes函数
		 * 也就是只有根节点出来，我们才可以点击，所以当我们调用loadChildNodes，根节点的函数早就已经
		 * 调用完毕，所以一定可以使用tree.zTree，这个变量
		 * 2、如果给一个函数传递参数，可以使用function(a),但是如果一个js中，有很多函数都要使用
		 * 同一个参数，怎么办，可以使用面向对象的思想，在对象中定义属性，这种在这个对象中的
		 * 所有函数都可以访问此属性。
		 * 3、把一二相结合，在使用一个变量的时候，要清楚该对象的使用范围，是不是由服务器的回调函数
		 * 产生，禁止使用异步中不确定的变量(禁止在服务器可能还没由调用完成，就使用该变量)
		 * 
		 */
		loadChildNodes:function(){
			var params={
				//获取正在被点击的父节点的id
				pid:tree.pNode.mid
			};
			//如果没有获取到子节点，就添加子节点
			if(!tree.zTree.getNodeByParam("pid",tree.pNode.mid)){
				$.post("menuitemAction_showMenuitemsByPid.action",params,function(data){
					tree.zTree.addNodes(tree.pNode,data.menuitemList,true);
				});
			}
		}
};

$().ready(function(){
	//tree.loadTree();
	tree.loadRootNode();
	/**
	 * 这种做法是非常危险，因为服务器还没有结束回调函数的时候，就在使用毁掉函数产生的变量
	 * 这种做法是禁止的，在以后的开发中一定要注意
	 */
	//alert(tree.zTree);
	
});