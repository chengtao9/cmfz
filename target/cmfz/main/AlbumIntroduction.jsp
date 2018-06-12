<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<script type="text/javascript">
   $(function(){
	    //初始化日期控件
	   $("#date4").datebox({
			formatter:function myformatter(date){
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				var d = date.getDate();
				return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
			},
			parser:function(s){
				if (!s) return new Date();
				var ss = (s.split('-'));
				var y = parseInt(ss[0],10);
				var m = parseInt(ss[1],10);
				var d = parseInt(ss[2],10);
				if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
					return new Date(y,m-1,d);
				} else {
					return new Date();
				}
			}
		});
	     //用户注册表单初始化
		  $("#albuminsertForm").form({
			  url:"${pageContext.request.contextPath}/album/insert",
			  iframe:false,
			  onSubmit: function(){
			      return $("#albuminsertForm").form("validate");
			  },
			  success:function(data){
	   						alert("aaaa");
				 $("#albuminsertDialog").dialog("close");
				 $("#showAlbumGrid").treegrid("reload");
			  }
		  })
   });
</script>
	<form id="albuminsertForm" method="post" enctype="multipart/form-data">
		<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
			<input name="title" required="true" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'标题'" style="width:100%;height: 100%">
		</div>
		<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
			<input name="count" required="true" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'章节数'" style="width:100%;height: 100%">
		</div>
		<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
			<input name="score" required="true" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'评分'" style="width:100%;height: 100%">
		</div>
		<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
			<input name="author" required="true" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'作者'" style="width:100%;height: 100%">
		</div>
		<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
			<input name="broadcast" required="true" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'播音'" style="width:100%;height: 100%">
		</div>
		<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
			<input name="breif" required="true" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'内容简介'" style="width:100%;height: 100%">
		</div>
		<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
			<input id="date4" required="true"  name="publishDate" class="easyui-datebox" data-options="prompt:'上传日期',editable:false" style="width:100%;height: 100%">
		</div>
		<div style="height: 40px;margin-bottom: 10px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
			<input name="imageFile"  class="easyui-filebox" style="width:100%;height: 100%">
		</div>

	</form>
