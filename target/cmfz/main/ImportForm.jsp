<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<script type="text/javascript">
   $(function(){
	     //用户注册表单初始化
		  $("#import_form").form({
			  url:"${pageContext.request.contextPath}/user/import",
			  iframe:false,
			  onSubmit: function(){
			      return $("#import_form").form("validate");
			  },
			  success:function(data){
				 $("#import_dialog").dialog("close");
				 $("#showUserGrid").edatagrid("reload");
			  }
		  })
   });
</script>
<form id="import_form" method="post" enctype="multipart/form-data">
	<div style="height: 40px;margin-bottom: 10px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
		<input name="imageFile"  class="easyui-filebox" style="width:100%;height: 100%">
	</div>
</form>
