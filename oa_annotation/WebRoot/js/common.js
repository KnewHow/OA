$().ready(function(){//在所有页面加载的时候
	$.config({//直接把json数据传递过去
		message:"您是否确认删除?",
		callback:function(){
			alert("回调函数");
		}
	});
});

	