<%--
  Created by IntelliJ IDEA.
  User: ryo
  Date: 2018/6/10
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

<head>
    <meta charset="utf-8" />
    <title>注册</title>
</head>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.css" />
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-responsive.css" />
<link href="../../js/theme/default/layer.css" rel="stylesheet">
<script type="text/javascript" src="../../js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="../../js/bootstrap.min.js"></script>
<script src="../../js/layer.js"></script>
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function(){

    })
    function Check(){
        var username=$("#username").val();
        var password=$("#password").val();
        var password2=$("#password2").val();
        if(username=="")
        {
            layer.alert('用户名必填', {
                icon: 0,
                title: "提示"
            });

        }
        if(password=="")
        {
            layer.alert('密码必填', {
                icon: 0,
                title: "提示"
            });

        }
        if(password2==""||password!=password2)
        {
            layer.alert('密码不一致', {
                icon: 0,
                title: "提示"
            });

        }
        else
        {
            $.ajax({
                type:"POST",
                url:"<%=basePath%>saveregister.do",
                dataType: "json",
                async:false,
                data:$('#frmLogin').serialize(),
                success:function(data)
                {
                    if(data.msg="success") {
                        window.location.href = "<%=basePath%>login.do";

                    }
                    else{
                        layer.alert('用户已存在', {
                            icon: 0,
                            title: "提示"
                        });

                    }
                },
                error:function (data) {
                    alert(JSON.stringify(data));

                }
            })
        }
    }
</script>
<style>
    body{
        background: url("../../img/bg2.jpg");
        animation-name:myfirst;
        animation-duration:12s;
        /*变换时间*/
        animation-delay:2s;
        /*动画开始时间*/
        animation-iteration-count:infinite;
        /*下一周期循环播放*/
        animation-play-state:running;
        /*动画开始运行*/
    }
    @keyframes myfirst
    {
        0%   {background:url("../../img/bg2.jpg");}
        34%  {background:url("../../img/bg2.jpg");}
        67%  {background:url("../../img/bg3.jpg");}
        100% {background:url("../../img/bg2.jpg");}
    }
    .form{background: rgba(255,255,255,0.2);width:400px;margin:120px auto;}
    /*阴影*/
    .fa{display: inline-block;top: 27px;left: 6px;position: relative;color: #ccc;}
    input[type="text"],input[type="password"]{padding-left:26px;}
</style>
<%--<body>
<div class="container">
    <div class="row">
        <div class="">
            <br />
            <br />
            <div>
                <form id="frmLogin">
                    <!--表单控件-->
                    <input type="text" name="username" id="username" placeholder="用户名" class="form-control " />
                    <br />
                    <input type="password" name="password" id="password" placeholder="密码" class="form-control" />
                    <br />
                    <input type="password" name="password2" id="password2" placeholder="请再次输入密码" class="form-control" />
                    <br />
                    <br />
                    <input type="button" value="保存" class="btn btn-success" id="btn_save" onclick="Check()">
                    &nbsp: &nbsp:
                    <input type="button" value="返回" class="btn btn-success" id="btn_h" onclick="window.location.href='login.do'">
                </form>
            </div>
        </div>

    </div>
</div>
</body>--%>
<body>
<div class="container">
    <div class="form row">
        <div class="form-horizontal col-md-offset-3" id="login_form">
            <h3 class="form-title">REGISTER</h3>
            <form  id="frmLogin">
                <div class="col-md-9">
                    <div class="form-group">
                        <i class="fa fa-user fa-lg"></i>
                        <input class="form-control required" type="text" placeholder="用户名" id="username" name="username" autofocus="autofocus" maxlength="20"/>
                    </div>
                    <div class="form-group">
                        <i class="fa fa-lock fa-lg"></i>
                        <input class="form-control required" type="password" placeholder="密码" id="password" name="password" maxlength="8"/>
                    </div>
                    <div class="form-group">
                        <i class="fa fa-lock fa-lg"></i>
                        <input class="form-control required" type="password" placeholder="请再次输入密码" id="password2" name="password2" maxlength="8"/>
                    </div>
                    <div class="form-group col-md-offset-9">
                        <button type="button" class="btn btn-success pull-left" onclick="Check()" >保存</button>
                        &nbsp;&nbsp;
                        <button type="button" class="btn btn-success pull-right" onclick="window.location.href='login.do'" >返回</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
