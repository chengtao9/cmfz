<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
	<script type="text/javascript">
		$(function(){
            $.extend($.fn.datagrid.defaults.editors, {
                file: {
                    init: function(container, options){
                        var input = $('<input type="file" name="imgPath" class="easyui-filebox">').appendTo(container);
                        return input;
                    },
                    getValue: function(target){
                        return $(target).val();
                    },
                    setValue: function(target, value){
                        $(target).val(value);
                    },
                    resize: function(target, width){
                        var input = $(target);
                        if ($.boxModel == true){
                            input.width(width - (input.outerWidth() - input.width()));
                        } else {
                            input.width(width);
                        }
                    }
                }
            });

            $("#showDateGrid").edatagrid({
    			url:"${pageContext.request.contextPath}/titlePic/queryAll",
                saveUrl:"${pageContext.request.contextPath}/titlePic/insert",
				updateUrl:"${pageContext.request.contextPath}/titlePic/update",
                destroyUrl:"${pageContext.request.contextPath}/titlePic/delete",
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
				toolbar:"#user_dlg_toolbar",
    			columns:[[    
        			{field:"id",title:"用户id",width:100,align:"center",checkbox:true},
        			{field:'title',title:'标题',width:100,align:"center"},
        			{field:'imgPath',title:'图片路径',width:100,align:'center'},
        			{field:'status',title:'状态',width:100,align:'center',
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
                    {field:'date',title:'日期',width:100,align:'center',formatter : function(value){
                        var date = new Date(value);
                        var y = date.getFullYear();
                        var m = date.getMonth() + 1;
                        var d = date.getDate();
                        return y + '-' +m + '-' + d;
                    }},
    			]],
                view: detailview,
                detailFormatter: function(rowIndex, rowData){
                    return '<table><tr>' +
                        '<td rowspan=2 style="border:0"><img src="http://localhost:9999' + rowData.imgPath+'" style="height:50px;"></td>' +
                        '<td style="border:0">' +
                        '<p>Attribute: ' + rowData.title + '</p>' +
                        '<p>Status: ' + rowData.date + '</p>' +
                        '<p>Status: ' + rowData.status + '</p>' +
                        '</td>' +
                        '</tr></table>';
                }
			});
            $("#userRegDialog").dialog({
                title:"添加轮播图",
                iconCls:"icon-add",
                width:400,
                href:"${pageContext.request.contextPath}/main/register.jsp",
                cache:false,
                modal:true,
                closed:true,
                top:120,
                buttons:[
                    {
                        text:"取消",
                        iconCls:"icon-redo",
                        handler:function(){
                            $("#regUserForm").form("reset");

                        }
                    },{
                        text:"添加",
                        iconCls:"icon-ok",
                        handler:function(){
                            $("#regUserForm").submit();
                        }
                    }
                ]
            });
            $("#addbtn").linkbutton({
					iconCls: 'icon-add',
					text:"添加",
					onClick: function(){
                        $("#userRegDialog").dialog("open");
					}



			});
			$("#updatebtn").linkbutton({
				iconCls: 'icon-edit',
					text:"修改",
					onClick: function(){
						//调用datagrid中的getSelections方法得到被选中的行对象，因修改只对应一行元素所以拿到一个对象
						var rows=$("#showDateGrid").edatagrid("getSelections");
						if(rows.length==0){$.messager.alert("警告","请选中需要修改的行");}
						if(rows.length>1){
							$.messager.alert("警告","只能编辑一行！");
							//调用unselectAll方法取消所有选中
							$("#showDateGrid").edatagrid("unselectAll");
						}
						if(rows.length != 0){
							//拿到第一个元素即要修改的对象
							var rowData=rows[0];
                            var rowIndex = $("#showDateGrid").edatagrid('getRowIndex', rowData);
                            $("#showDateGrid").edatagrid('editRow', rowIndex);

						}
					}
			}); 
			$("#deletebtn").linkbutton({
					iconCls:'icon-cut',
					text:"删除",
					onClick: function(){
						/*var row= $("#showDateGrid").edatagrid("getSelected");
						var index=$("#showDateGrid").edatagrid("getRowIndex",row);*/
                        $("#showDateGrid").edatagrid("destroyRow");

					}
			
			});

		});
	
	</script>

       	<div id="userloginDialog" > </div>
		<div id="userUpdateDialog" > </div>
		<div id="userRegDialog" > </div>
	<table id="showDateGrid"></table>
<div id="user_dlg_toolbar" style="padding:2px 0;height: 30px">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="padding-left:2px">
					<a id="addbtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
					<a id="updatebtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
					<a id="deletebtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:true">删除</a>
					<a id="savebtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
				</td>
			</tr>
		</table>
</div>