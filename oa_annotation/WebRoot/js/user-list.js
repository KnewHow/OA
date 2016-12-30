$().ready(function(){
	$("input[name='selectAll']").unbind("click");
	$("input[name='selectAll']").bind("click",function(){
		$(this).controlCheckBox("userCheckBox");
	});
});