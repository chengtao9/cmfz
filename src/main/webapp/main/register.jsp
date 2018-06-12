<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<script type="text/javascript">
   $(function(){
	    //初始化日期控件
	   $("#updateBirth").datebox({
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
		  $("#regUserForm").form({
			  url:"${pageContext.request.contextPath}/titlePic/insert",
//			  iframe:false,
//			  onSubmit: function(){
//			      return $("#regUserForm").form("validate");
//			  },
			  success:function(data){
	   
				 $("#userRegDialog").dialog("close");
				 $("#showDateGrid").edatagrid("reload");
			  }    
		  })
   });
</script>
<form id="regUserForm" method="post" enctype="multipart/form-data">
	<input name="id" readonly="readonly"  hidden="hidden" />
	<div style="height: 40px;margin-top: 30px;padding-left: 20%;padding-right: 20%">
		<input name="title" required="true" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'标题'" style="width:100%;height: 100%">
	</div> 
	<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
		<span style="height: 100%;width: 100%;display: inline-block;margin: 0;padding: 0;float: right">
		   <input name="status" required="true" class="easyui-switchbutton" data-options="onText:'Yes',offText:'No',handleWidth:150,handleText:'状态'"  style="width:100%;height: 100%">
		</span>
	</div> 
	<div style="height: 40px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
		<input id="date" required="true"  name="date" class="easyui-datebox" data-options="prompt:'上传日期',editable:false" style="width:100%;height: 100%">
	</div>  
	<div style="height: 40px;margin-bottom: 30px;margin-top: 10px;padding-left: 20%;padding-right: 20%">
		<input name="imageFile"  class="easyui-filebox" style="width:100%;height: 100%">
	</div>  
</form>