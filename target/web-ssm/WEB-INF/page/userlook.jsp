<%--
  Created by IntelliJ IDEA.
  User: ryo
  Date: 2018/6/23
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"
            aria-hidden="true">×
    </button>
    <h4 class="modal-title" id="myModalLabel">
        查看
    </h4>
</div>
<form class="form-horizontal" role="form" >
    <fieldset>
            <div class="form-group">
            <label class="col-sm-2 control-label" for="ds_host">编号</label>
            <div class="col-sm-4">
                <label class="col-sm-2 control-label" for="ds_host">${user.id}</label>
            </div>
            <label class="col-sm-2 control-label" for="ds_name">用户名</label>
            <div class="col-sm-4">
                <label class="col-sm-2 control-label" for="ds_name">${user.username}</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="ds_username">年龄</label>
            <div class="col-sm-4">
                <label class="col-sm-2 control-label" for="ds_name">${user.age}</label>
            </div>
            <label class="col-sm-2 control-label" for="ds_password">地址</label>
            <div class="col-sm-4">
                <label class="col-sm-2 control-label" for="ds_name">${user.address}</label>
            </div>
        </div>
    </fieldset>
</form>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>

</div>
