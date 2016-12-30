(function(jQuery){
//	jQuery.fn=function(message){//加载在原型上面的方法
//		window.confirm(message);
//	}
	
	//使用全局方法
	$.config=function(configJson){//做一个插件
		$("a").each(function(){
			if($(this).text()=="删除"){
				$(this).unbind("click");
				$(this).bind("click",function(){
					configJson.callback();//使用回调函数
					return window.confirm(configJson.message);
				});
			}
		});
	}
})(jQuery);