<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
	<script type="text/javascript">
	$(function(){
        $("#showAlbumGrid").treegrid({
			url:"${pageContext.request.contextPath}/album/queryAll",
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
            onDblClickRow:function(row){
                $("#bfDialog").dialog("open");
                $("#music").prop("src","http://localhost:9999"+row.downPath)
            },
            toolbar: [{
                iconCls:'icon-edit',
                text:"专辑详情",
                handler: function(){
                    var row=$("#showAlbumGrid").treegrid("getSelected");
                    if(row==null){
                        $.messager.confirm('请选择', '请选择要展示的专辑'
                        );
                    }else{
                        if(row.size!=null){
                            $.messager.alert('提示消息','请选择专辑选项！');
                        }else{
                            $("#album_ff").form("load",row);
                            $("#coverImg").prop("src", "http://localhost:9999/upload/"+row.coverImg);
                            $("#albumShowDialog").dialog("open");
						}
					}
                }
            },'-',{
                iconCls: 'icon-add',
                text:"添加专辑",
                handler: function(){
                    $("#albuminsertDialog").dialog("open");
                }
            },'-',{
                iconCls: 'icon-add',
                text:"添加章节",
                handler: function() {
                    var row=$("#showAlbumGrid").treegrid("getSelected");
                    if(row==null){
                        $.messager.confirm('请选择', '请选择要添加的专辑'
                        );
                    }else{
                        if(row.size!=null){
                            $.messager.alert('提示消息','请选择专辑选项！');
                        }else{

                            $("#chapterInsert").data("row",row);
                            $("#chapterInsert").dialog("open");
                        }
                    }
                }
            },'-',{
                iconCls: 'icon-save',
                text:"下载专辑",
                handler: function(){
                    var row=$("#showAlbumGrid").treegrid("getSelected");
                    if(row==null){
                        $.messager.confirm('请选择', '请选择要下载的章节'
                        );
                    }else{
                        if(row.size==null){
                            $.messager.alert('提示消息','请选择要下载的章节选项！');
                        }else{
                            alert(row.oldName+"   "+row.downPath);
                            location.href="${pageContext.request.contextPath}/album/down?oldName="+row.oldName+"&downPath="+row.downPath;
                        }
                    }
                }
            }
            ],
            idField:'id',
            treeField:'title',
            columns:[[
                {title:'标题',field:'title',width:180},
                {field:'size',title:'章节大小',width:60,align:'right'},
                {field:'duration',title:'播放时长',width:80},
                {field:'downPath',title:'下载路径',width:80},
            ]],
		});
            $("#albumShowDialog").dialog({
            title:"专辑详情",
            iconCls:"icon-add",
            width:400,
            cache:false,
            modal:true,
            closed:true,
            top:120,
            buttons:[
                {
                    text:"确认",
                    iconCls:"icon-redo",
                    handler:function(){
                        $("#albumShowDialog").dialog("close");

                    }
                }
            ]
        });
        $("#albuminsertDialog").dialog({
            title:"添加专辑",
            iconCls:"icon-add",
            width:400,
            cache:false,
            modal:true,
            closed:true,
            top:120,
            href:"${pageContext.request.contextPath}/main/AlbumIntroduction.jsp",
            buttons:[
                {
                    text:"取消",
                    iconCls:"icon-redo",
                    handler:function(){
                        $("#albumShowDialog").dialog("close");

                    }
                },{
                    text:"提交",
                    iconCls:"icon-ok",
                    handler:function(){
                        $("#albuminsertForm").submit();

                    }
				}
            ]
        });
        $("#chapterInsert").dialog({
            title:"添加章节",
            iconCls:"icon-add",
            width:400,
            cache:false,
            modal:true,
            closed:true,
            href:"${pageContext.request.contextPath}/main/UploadChapter.jsp",
            top:120,
            onLoad:function(){
                var user=$("#chapterInsert").data("row");
                $("#chapterForm").form('load',user);//数据回显
            },
            buttons:[
                {
                    text:"取消",
                    iconCls:"icon-redo",
                    handler:function(){
                        $("#albumShowDialog").dialog("close");

                    }
                },{
                    text:"提交",
                    iconCls:"icon-ok",
                    handler:function(){
                        $("#chapterForm").submit();

                    }
                }
            ]
        });
        $("#bfDialog").dialog({
            title:"音频播放",
            iconCls:"icon-add",
            width:400,
            cache:false,
            modal:true,
            closed:true,
            top:120,
            buttons:[
                {
                    text:"关闭",
                    iconCls:"icon-redo",
                    handler:function(){
                        $("#bfDialog").dialog("close");

                    }
                }
            ]
        });

	});
	
	</script>
	<div id="bfDialog" >
		<audio id="music" src="" controls="controls" preload="preload"></audio>
	</div>
	<div id="albumShowDialog" >
			<form id="album_ff" method="post">
				<div>
					<label for="name">名&nbsp;&nbsp;称:</label>
					<input class="easyui-validatebox" id="name" type="text" name="title"/>
				</div>        <div>
				<label for="name">作&nbsp;&nbsp;者:</label>
				<input class="easyui-validatebox" id="author" type="text" name="author"/>
			</div>
				<div>
					<label for="zhangjieshu">集&nbsp;&nbsp;数:</label>
					<input class="easyui-validatebox" type="text" id="zhangjieshu" name="count"/>
				</div>  <div>
				<label for="boyinyuan">播音员:</label>
				<input class="easyui-validatebox" type="text" id="boyinyuan" name="broadcast"/>
			</div>  <div>
				<label for="jianjie">简&nbsp;&nbsp;介:</label>
				<input class="easyui-textbox" type="text" id="jianjie" name="breif" style="height:100px" multiline="true"/>
			</div>  <div>
				<label for="score">评&nbsp;&nbsp;分:</label>
				<input class="easyui-validatebox" type="text" id="score" name="score"/>
			</div>
				<div>
					<label for="coverImg">封&nbsp;&nbsp;面:</label>
					<img id="coverImg" src="" width="60px" height="80px">
				</div>
			</form>
	</div>
<div id="albuminsertDialog" ></div>

	<table id="showAlbumGrid"></table>
<div id="chapterInsert">

</div>

