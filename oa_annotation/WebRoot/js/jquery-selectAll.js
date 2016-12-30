(function(jQuery){
	$.fn.controlCheckBox = function(checkboxName){
		if($(this).attr("checked")){
			$("input[name='"+checkboxName+"']").attr("checked",true);
		}else{
			$("input[name='"+checkboxName+"']").attr("checked",false);
		}
	};
})(jQuery);