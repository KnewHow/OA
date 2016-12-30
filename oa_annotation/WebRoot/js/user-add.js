var user={
		formValidate:function(){
			$("input[type='image']").unbind("click");
			$("input[type='image']").bind("click",function(){
				if($("select[name='did'] option:selected").attr("value")==""||!$("select[name='pids'] option:selected").attr("value")){
					alert("部门和岗位都不能为空");
					return false;
				}else{
					if($("#message").text()=="该用户名已经存在"){
						alert("请输入合法的用户名");
						return false;
					}else{
						return true;
					}
					return true;
				}
				
				
			});
		},

		initEvent:function(){
			$("input[name='username']").unbind("blur");
			$("input[name='username']").bind("blur",function(){
				user.checkUser($(this).val());
			});
		},
		
		checkUser:function(username){
			var params={
				username:username	
			};
			
//			$.post("oa_annotation/userJsonAction_ajaxCheckUser.action",params,function(data){
//				$("#message").text(data.message);
//				if(data.message=="该用户名可以使用"){
//					$("#message").css("color","green");
//				}else{
//					$("#message").css("color","red");
//				}
//			});
			/*使用post，如果发生异常，就不执行，使用ajax，不论是否发生异常，都执行success方法*/
			$.ajax({
				type:"POST",
				url:"oa_annotation/userJsonction_ajaxCheckUser.action",
				data:params,
				success:function(){
					alert("aaa");
				},
				error:function(){
					alert("bbbb");
				}
			});
		}
};

$().ready(function(){
	user.initEvent();
	user.formValidate();
});