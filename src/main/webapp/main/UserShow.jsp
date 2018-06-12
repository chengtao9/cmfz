<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
	<script type="text/javascript">
		$(function(){
            $("#showUserGrid").edatagrid({
    			url:"${pageContext.request.contextPath}/user/show",
				updateUrl:"${pageContext.request.contextPath}/user/update",
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
				toolbar: [{
					iconCls:'icon-edit',
					text:"修改用户状态",
					handler: function(){
                        //调用datagrid中的getSelections方法得到被选中的行对象，因修改只对应一行元素所以拿到一个对象
                        var rows=$("#showUserGrid").edatagrid("getSelections");
                        if(rows.length==0){$.messager.alert("警告","请选中需要修改的行");}
                        if(rows.length>1){
                            $.messager.alert("警告","只能编辑一行！");
                            //调用unselectAll方法取消所有选中
                            $("#showUserGrid").edatagrid("unselectAll");
                        }
                        if(rows.length != 0){
                            //拿到第一个元素即要修改的对象
                            var rowData=rows[0];
                            var rowIndex = $("#showUserGrid").edatagrid('getRowIndex', rowData);
                            $("#showUserGrid").edatagrid('editRow', rowIndex);

                        }
					}
				},'-',{
                    iconCls: 'icon-redo',
                    text:"导出",
                    handler: function(){
                        $("#custom_dialog").dialog("open");
                    }
                },'-',{
                    iconCls: 'icon-add',
                    text:"导入",
                    handler: function(){
                        $("#import_dialog").dialog("open");
                    }
                },],
    			columns:[[    
        			{field:"id",title:"用户id",width:100,align:"center",checkbox:true},
        			{field:'phonenum',title:'账户',width:100,align:"center"},
        			{field:'password',title:'密码',width:100,align:'center'},
                    {field:'dharmname',title:'法名',width:100,align:'center'},
                    {field:'province',title:'省份',width:100,align:'center'},
                    {field:'city',title:'城市',width:100,align:'center'},
                    {field:'sex',title:'性别',width:100,align:'center'},
                    {field:'sign',title:'签名',width:100,align:'center'},
                    {field:'headpic',title:'头像',width:100,align:'center'},
        			{field:'status',title:'用户状态',width:100,align:'center',
                        editor: {
                            type: 'combobox',
                            options: {
                                required: true,
                                missingMessage: '请修改用户状态',
                                multiple: false,
                                editable: false,
                                valueField: 'id',
                                textField: 'text',
                                data: [{ 'id': 'y', 'text': 'y' }, { 'id': 'n', 'text': 'n'}]
                            }
                        }
                    },
                    {field:'date',title:'注册日期',width:100,align:'center',formatter : function(value){
                        var date = new Date(value);
                        var y = date.getFullYear();
                        var m = date.getMonth() + 1;
                        var d = date.getDate();
                        return y + '-' +m + '-' + d;
                    }},
    			]],
			});
         $("#btn").click(function () {
             var text=$("#customer_cc").combotree("getText");
             var value=$("#customer_cc").combotree("getValues");
             var c="";
             $.each(value,function (index,iterm) {
                 if(index!=value.length-1){
                     c+=iterm+",";
				 }else{
                     c+=iterm;
				 }

             })
             $("#customer_form").form("submit",{
                 url:"${pageContext.request.contextPath}/user/export",
                 queryParams:{"text":text,"fields":c}
			 })

         })
            $("#import_dialog").dialog({
                title:"导入",
                iconCls:"icon-add",
                width:400,
                cache:false,
                modal:true,
                closed:true,
                top:120,
                href:"ImportForm.jsp",
                buttons:[
                    {
                        text:"取消",
                        iconCls:"icon-redo",
                        handler:function(){
                            $("#import_form").form("reset");

                        }
                    },{
                        text:"添加",
                        iconCls:"icon-ok",
                        handler:function(){
                            $("#import_form").submit();
                        }
                    }
                ]
            });

		});
	
	</script>
	<table id="showUserGrid"></table>
<div id="custom_dialog" class="easyui-dialog" title="导出" style="width:400px;height:200px;"
	 data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
	<form id="customer_form" method="post">
		<select id="customer_cc" class="easyui-combotree" style="width:200px;"
				data-options="url:'${pageContext.request.contextPath}/user/field',required:true,checkbox:true,onlyLeafCheck:true,multiple:true"></select>
	</form>

	<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">确定导出</a>

</div>
<div id="import_dialog" class="easyui-dialog" title="导入" style="width:400px;height:200px;
"data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">

</div>