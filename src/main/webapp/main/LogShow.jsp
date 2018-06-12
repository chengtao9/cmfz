<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
	<script type="text/javascript">
		$(function(){
            $("#showLogGrid").edatagrid({
    			url:"${pageContext.request.contextPath}/log/show",
				fitColumns:true,
				fit:true,
				pagination:true,
				singleSelect:false,
				width:100,
		  		striped:true,
				ctrlSelect:true,
                autoSave:true,
				rowStyler:function(){
					return "height:70";
				
				},
    			columns:[[    
        			{field:"id",title:"日志id",width:100,align:"center",checkbox:true},
        			{field:'username',title:'管理员',width:100,align:"center"},
        			{field:'userid',title:'管理员编号',width:100,align:'center'},
                    {field:'method',title:'操作',width:100,align:'center'},
                    {field:'status',title:'是否成功',width:100,align:'center'},
                    {field:'date',title:'操作时间',width:50,align:'center',formatter : function(value){
                        var date = new Date(value);
                        var y = date.getFullYear();
                        var m = date.getMonth() + 1;
                        var d = date.getDate();
                        var hour = date.getHours();
                        var min = date.getMinutes();
                        var sec = date.getSeconds();
                        return y + '-' +m + '-' + d + ' '+hour+':'+min+':'+sec;
                    }}
    			]],
			});

		});
	</script>
	<table id="showLogGrid"></table>