<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<script type="text/javascript">
   $(function(){
	     //用户注册表单初始化
       $("#chapterForm").form({
           url:"${pageContext.request.contextPath}/album/chapter",
           iframe:false,
           onSubmit: function(){
               return $("#chapterForm").form("validate");
           },
           success:function(data){
               alert("aaaa");
               $("#chapterInsert").dialog("close");
               $("#showAlbumGrid").treegrid("reload");
           }
       })
   });
</script>
		<form id="chapterForm" method="post" enctype="multipart/form-data">
			<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
				<input name="id" hidden />
			</div>
			<div style="height: 40px;margin-bottom: 10px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
				<input name="image"  class="easyui-filebox" style="width:100%;height: 100%">
			</div>
		</form>

