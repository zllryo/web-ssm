<%--
  Created by IntelliJ IDEA.
  User: ryo
  Date: 2018/6/23
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: ryo
  Date: 2018/6/23
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    function usersave() {

        var username=$("#username").val();

        if(username=="")
        {
            layer.alert('用户名必填', {
                icon: 0,
                title: "提示"
            });

        }
        else
        {
            $.ajax({
                type:"POST",
                url:"usersave.do",
                dataType: "json",
                async:false,
                data:$('#frmedit').serialize(),
                success:function(data)
                {
                    if(data.msg="success") {
                        window.location.href="listpage.do?pageNo=1";

                    }
                },
                error:function (data) {
                    alert(JSON.stringify(data));

                }
            })
        }

    }
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        编辑
    </h4>
</div>
<form class="form-horizontal" id="frmedit">
    <fieldset>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="ds_host">编号</label>
            <div class="col-sm-4">
                <label class="col-sm-2 control-label" for="ds_host">${user.id}</label>
                <input class="form-control" id="id"  name="id" type="hidden" value="${user.id}"/>
            </div>
            <label class="col-sm-2 control-label" for="ds_name">用户名</label>
            <div class="col-sm-4">
                <input class="form-control" id="username"  name="username"  type="text" value="${user.username}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="ds_username">年龄</label>
            <div class="col-sm-4">
                <input class="form-control" id="age"  name="age"  type="text" value="${user.age}"/>
            </div>
            <label class="col-sm-2 control-label" for="ds_password">地址</label>
            <div class="col-sm-4">
                <input class="form-control" id="address"  name="address"  type="text" value="${user.address}"/>
            </div>
            <div>
                <input type="hidden" id="password"  name="password"  value="${user.password}">
                <input type="hidden" id="roleid"  name="roleid"  value="${user.roleid}">
                <input type="hidden" id="deaprtid"  name="deaprtid"  value="${user.deaprtid}">
                <input type="hidden" id="sex"  name="sex"  value="${user.sex}">
            </div>
        </div>
    </fieldset>
</form>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
    <button type="button" class="btn btn-primary" onclick="usersave()">
        提交更改
    </button>
</div>

