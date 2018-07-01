<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.css"/>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-responsive.css"/>
<link href="../../js/theme/default/layer.css" rel="stylesheet">
<script type="text/javascript" src="../../js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="../../js/bootstrap.min.js"></script>
<script src="../../js/layer.js"></script>
<style>
    #main-nav {
        margin-left: 1px;
    }

    #main-nav.nav-tabs.nav-stacked > li > a {
        padding: 10px 8px;
        font-size: 12px;
        font-weight: 600;
        color: #4A515B;
        background: #E9E9E9;
        background: -moz-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
        background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #FAFAFA), color-stop(100%, #E9E9E9));
        background: -webkit-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
        background: -o-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
        background: -ms-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
        background: linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#FAFAFA', endColorstr='#E9E9E9');
        -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#FAFAFA', endColorstr='#E9E9E9')";
        border: 1px solid #D5D5D5;
        border-radius: 4px;
    }

    #main-nav.nav-tabs.nav-stacked > li > a > span {
        color: #4A515B;
    }

    #main-nav.nav-tabs.nav-stacked > li.active > a, #main-nav.nav-tabs.nav-stacked > li > a:hover {
        color: #FFF;
        background: #3C4049;
        background: -moz-linear-gradient(top, #4A515B 0%, #3C4049 100%);
        background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #4A515B), color-stop(100%, #3C4049));
        background: -webkit-linear-gradient(top, #4A515B 0%, #3C4049 100%);
        background: -o-linear-gradient(top, #4A515B 0%, #3C4049 100%);
        background: -ms-linear-gradient(top, #4A515B 0%, #3C4049 100%);
        background: linear-gradient(top, #4A515B 0%, #3C4049 100%);
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#4A515B', endColorstr='#3C4049');
        -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#4A515B', endColorstr='#3C4049')";
        border-color: #2B2E33;
    }

    #main-nav.nav-tabs.nav-stacked > li.active > a, #main-nav.nav-tabs.nav-stacked > li > a:hover > span {
        color: #FFF;
    }

    #main-nav.nav-tabs.nav-stacked > li {
        margin-bottom: 4px;
    }

    /*定义二级菜单样式*/
    .secondmenu a {
        font-size: 10px;
        color: #4A515B;
        text-align: center;
    }

    .navbar-static-top {
        background-color: #212121;
        margin-bottom: 5px;
    }

    .navbar-brand {
        background: url('') no-repeat 10px 8px;
        display: inline-block;
        vertical-align: middle;
        padding-left: 50px;
        color: #fff;
    }
</style>
<script type="text/javascript">
    function customSearch() {

        var username = $("#name").val();
        var address = $("#gaddress").val();
        window.location.href = "listpage.do?pageNo=1&&username=" + username + "&address=" + address;

    }

    function deleteby(id) {
        var username = $("#name").val();
        var address = $("#gaddress").val();

        $.ajax({
            type: "POST",
            url: "deleteBy.do",
            data: {id: id},
            success: function (data) {
                alert(data.msg);
                if (data.msg == "success") {
                    layer.alert('删除成功', {
                        icon: 0,
                        title: "提示"
                    });
                    window.location.href = "listpage.do?pageNo=1&&username=" + username + "&address=" + address;
                }
                else {
                    layer.alert('错误', {
                        icon: 0,
                        title: "提示"
                    });
                }
            },
            error: function (data) {
                alert(JSON.stringify(data));

            }
        })

    }

    function lookBy(id) {
        $.ajax({
            type: "POST",
            url: "lookBy.do",
            data: {id: id},
            success: function (data) {
                $("#miandiv").html(data);
            }
        })
    }

    function editBy(id) {
        $.ajax({
            type: "POST",
            url: "editBy.do",
            data: {id: id},
            success: function (data) {
                $("#miandiv").html(data);
            }
        })
    }

    function addBy(id) {
        $.ajax({
            type: "POST",
            url: "addBy.do",
            data: {id: id},
            success: function (data) {
                $("#miandiv").html(data);
            },
            error: function (data) {
                alert(JSON.stringify(data));

            }
        })
    }
</script>
<body>
<div class="navbar navbar-duomi navbar-static-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#" id="logo">配置管理系统
            </a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div class="navbar-brand" style="padding-left: 1000px" id="logo">${user.depart.departname}-${user.username}你好
                <a href="loginout.do">退出</a>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
                <li class="active">
                    <a href="#">
                        <i class="glyphicon glyphicon-th-large"></i>
                        首页
                    </a>
                </li>
                <li>
                    <a href="#systemSetting" class="nav-header collapsed" data-toggle="collapse">
                        <i class="glyphicon glyphicon-cog"></i>
                        系统管理
                        <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                    </a>
                    <ul id="systemSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                        <li><a href="#"><i class="glyphicon glyphicon-user"></i>用户管理</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-th-list"></i>菜单管理</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-asterisk"></i>角色管理</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-edit"></i>修改密码</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-eye-open"></i>日志查看</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class="glyphicon glyphicon-credit-card"></i>
                        物料管理
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="glyphicon glyphicon-globe"></i>
                        分发配置
                        <span class="label label-warning pull-right">${count}</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="glyphicon glyphicon-calendar">${userCode}</i>
                        图表统计
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="glyphicon glyphicon-fire"></i>
                        关于系统
                    </a>
                </li>
            </ul>
        </div>
        <div class="col-md-10" id="rightdiv">
            <div class="container-fluid">
                <div>
                    <div id="toolbar-btn" class="btn-group pull-left" style="padding-bottom:10px;">
                        <button onclick="addBy()" class="btn btn-primary btn-space" data-toggle="modal"
                                data-target="#myModal">
                            新增
                        </button>
                        <%--   <button id="btn_save" onclick="saveFunction()" type="button" class="btn btn-success btn-space">
                               <span class="fa fa-save" aria-hidden="true" class="btn-icon-space"></span>
                               保存
                           </button>
                           <button id="btn_delete" onclick="deleteFunction()" type="button" class="btn btn-danger btn-space">
                               <span class="fa fa-trash-o" aria-hidden="true" class="btn-icon-space"></span>
                               删除
                           </button>--%>
                    </div>
                    <div class="pull-right" id="query-form" style="padding-bottom:10px;">
                        <input name="name" id="name" placeholder="姓名" type="text"
                               style="float:left;width:150px;margin-right:5px;" v-model="lookupType"
                               class="form-control">
                        <div style="float:left;margin-right:5px;">
                            <input name="gaddress" id="gaddress" placeholder='地址' type="text"
                                   style="float:left;width:150px;margin-right:5px;" v-model="description"
                                   class="form-control">
                        </div>
                        <div class="btn-group">
                            <button id="btn_search" onclick="customSearch()" type="button"
                                    class="btn btn-primary btn-space">
                                <span class="fa fa-search" aria-hidden="true" class="btn-icon-space"></span>
                                查询
                            </button>
                            <button id="btn_reset" onclick="resetSearch()" type="button"
                                    class="btn btn-default btn-space">
                                <span class="fa fa-eraser" aria-hidden="true" class="btn-icon-space"></span>
                                重置
                            </button>
                        </div>

                    </div>
                </div>
                <table id="table" class="table  table-condensed table-striped">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>姓名</th>
                        <th>年龄</th>
                        <th>部门</th>
                        <th>角色</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userlist}" var="x">
                        <tr>
                            <td>${x.id}</td>
                            <td>${x.username}</td>
                            <td>${x.age}</td>
                            <td>${x.depart.departname}</td>
                            <td>${x.role.rolename}</td>
                            <td>
                                <button onclick="lookBy(${x.id})" class="btn  btn-success" data-toggle="modal"
                                        data-target="#myModal">
                                    查看
                                </button>
                            </td>
                            <td>
                                <button onclick="editBy(${x.id})" class="btn  btn-success" data-toggle="modal"
                                        data-target="#myModal">
                                    编辑
                                </button>
                            </td>
                            <td><input type="button" class="btn btn-success" value="删除" onclick="deleteby(${x.id})"/>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
                <div>
                    <p>当前 ${pageInfo.pageNum }页,总${pageInfo.pages }
                        页,总 ${pageInfo.total } 条记录
                </p>
                <a href="listpage.do?pageNo=${pageInfo.firstPage}">第一页</a>
                <c:if test="${pageInfo.hasPreviousPage }">
                    <a href="listpage.do?listpage=${pageInfo.pageNum-1}">上一页</a>
                </c:if>
                <c:if test="${pageInfo.hasNextPage }">
                    <a href="listpage.do?pageNo=${pageInfo.pageNum+1}">下一页</a>
                </c:if>
                <a href="listpage.do?pageNo=${pageInfo.lastPage}">最后页</a>
            </div>
        </div>


    </div>
</div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:800px;">
        <div class="modal-content">

            <div id="miandiv">

            </div>

        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>

</html>