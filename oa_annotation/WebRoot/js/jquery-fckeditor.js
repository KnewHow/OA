(function(jQuery){
	$.fckeditor = function(name){
		var oFCKeditor = new FCKeditor(name);
		oFCKeditor.BasePath	= "fckeditor/" ;
		oFCKeditor.ReplaceTextarea() ;
	}
})(jQuery);