var menu = {
		setting:{
			isSimpleData: true,
            treeNodeKey: "mid",
            treeNodeParentKey: "pid",
            showLine: true,
            root: {
                isRoot: true,
                nodes: []
            },
		},
		
		loadMenu:function(){
			$.post("menuitemAction_showMenuitemsByUser",null,function(data){
				$("#menuTree").zTree(menu.setting,data.menuitemList);
			});
			
		}
};

$().ready(function(){
	menu.loadMenu();
});