﻿<%@ page language="java"  pageEncoding="utf-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>持名法州主页</title>
<link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">   
<link rel="stylesheet" type="text/css" href="../themes/IconExtension.css">
<link rel="stylesheet" type="text/css" href="../themes/icon.css">
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/datagrid-detailview.js"></script>
<script type="text/javascript" src="../js/jquery.edatagrid.js"></script>
<script type="text/javascript">
    $(function(){
        $.ajax({
            url:"${pageContext.request.contextPath}/menu/showMenu",
            type:"post",
            dataTyp:"json",
            success:function (result) {
                $.each(result,function(index1,first){
                    var c="";
                    $.each(first.children,function(index2,second){
                        c+="<div><a onclick=\"selectTab('"+second.url+"','"+second.name+"','"+second.iconcls+"')\" style=\"width: 100%;height: 30px\;text-align:center\" id=\"second\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'"+second.iconcls+"'\">"+second.name+"</a></div>";
                    })
                    $("#aa").accordion("add",{
                        title:first.name,
                        iconCls:first.iconcls,
                        content:c,
                    })

                })
            }
        })
        $("#exit").linkbutton({
            onClick: function(){
                // 异步加载统计信息
                $.post("${pageContext.request.contextPath }/manager/exit",function(data){
                },"json");
            }

        });

    })
    function selectTab(href,title,iconcls) {
        var flag=$("#tt").tabs("exists",title);
        if(flag){
            $("#tt").tabs("select",title);
        }else{
            $('#tt').tabs('add', {
                title: title,
                selected: true,
                iconCls: iconcls,
                href:"${pageContext.request.contextPath}/main/"+href,
                closable: true
            });
        }
    }
</script>

</head>
<body class="easyui-layout">   
    <div data-options="region:'north',split:true" style="height:60px;background-color:  #5C160C">
    	<div style="font-size: 24px;color: #FAF7F7;font-family: 楷体;font-weight: 900;width: 500px;float:left;padding-left: 20px;padding-top: 10px" >持名法州后台管理系统</div>
        <div style="font-size: 16px;color: #FAF7F7;font-family: 楷体;width: 500px;float:right;padding-top:15px">
            <c:if test="${sessionScope.manager==null}"><a id="loginButton" href="${pageContext.request.contextPath}/login.jsp" class="easyui-linkbutton" data-options="iconCls:'icon-man'">用户登录</a></c:if>
            <c:if test="${sessionScope.manager!=null}"> 欢迎您:${sessionScope.manager.username} &nbsp;</c:if>
           <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改密码</a>&nbsp;&nbsp;<a id="exit" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-01'">退出系统</a></div>
    </div>   
    <div data-options="region:'south',split:true" style="height: 40px;background: #5C160C">
    	<div style="text-align: center;font-size:15px; color: #FAF7F7;font-family: 楷体" >&copy;百知教育 htf@zparkhr.com.cn</div>
    </div>   
       
    <div data-options="region:'west',title:'导航菜单',split:true" style="width:220px;">
    	<div id="aa" class="easyui-accordion" data-options="fit:true">
    		
		</div>  
    </div>   
    <div data-options="region:'center'">
    	<div id="tt" class="easyui-tabs" data-options="fit:true,narrow:true,pill:true">   
		    <div title="主页" data-options="iconCls:'icon-neighbourhood',"  style="background-image:url(../main/image/shouye.jpg);background-repeat: no-repeat;background-size:100% 100%;"></div>
		</div>  
    </div>   
</body> 
</html>