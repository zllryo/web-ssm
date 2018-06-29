<%--
  Created by IntelliJ IDEA.
  User: ryo
  Date: 2018/6/13
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404页面</title>
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet" >
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/style.min.css" rel="stylesheet">
    <base target="_self">
</head>
<body >
<div class="middle-box text-center animated fadeInDown" >
    <h1>error</h1>
    <h3 > 系统出错！! !</h3>
    <div >
        ${message}
        <form class="form-inline m-t" role="form" action="/login.do">
            <button type="submit" class=" btn btn-primary">返回登录页</button>
        </form>
    </div>
</div>

<script src="../../js/jquery.min.js" ></script>
<script src="../../js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>
