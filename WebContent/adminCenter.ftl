<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>问卷后台管理系统</title>
		<!--bootstrap-->
		<link href="${request.contextPath}/css/bootstrap.css" rel="stylesheet" />
		<link href="${request.contextPath}/css/page.css" rel="stylesheet" />
		<link href="${request.contextPath}/css/common.css" rel="stylesheet" />
		<link href="${request.contextPath}/css/nav.css" rel="stylesheet" />
		<link href="${request.contextPath}/css/edit.css" rel="stylesheet"/>
		<link href="${request.contextPath}/img/favicon.ico" rel="shortcut icon" type="image/x-icon">
		<script src="${request.contextPath}/js/jquery-3.5.1.min.js"></script>
		<script src="${request.contextPath}/js/jquery.form.min.js"></script>
		<script src="${request.contextPath}/js/jquery.validate.js"></script>
		<script src="${request.contextPath}/js/edit.js"type="text/javascript" ></script>
		<script src="${request.contextPath}/js/layer/layer.js"></script>

		<style>
			.btn {
				border-radius: 0px;
				padding: 0px;
				cursor: pointer;
			}
			
			.btn.lightblue,
			.btn.red {
				padding: 5px 10px;
			}
			
			.manager_span{
				cursor: pointer;
				width:110px;
				height:22px;
				float:left;
			}
			.all_selection{
				cursor: pointer;
				color: skyblue;
				display: block;
				width: 80px;
			}
		</style>
	</head>

	<body style="background-color:#ececec!important;">

		<!-- 导航栏 -->
		<div class="navigation">
			<ul class="list clearfix">
				<li class="qxsleft"><a href="${request.contextPath}/admin/question/list">问卷列表</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/admin/template/list">模板列表</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/admin/center" class="on">用户中心</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/logout">安全退出</a></li>
				<li class="qxsright"><a href="#">当前用户:<#if Session["admin"]?exists>${Session["admin"].adminname}</#if></a></li>
			</ul>
		</div>

		<!--用户中心界面-->
		<div class="container-fluid" style="min-height:674px;">
			<div class="container-fluid" style="min-height:674px;">
				<div class="row" style="width:1170px;background-color:white;margin:30px auto;padding:10px;box-shadow: #ccc 0 0 20px;">
					<div class="col-xs-12 main-right">
						<div class="row userCenterDiv">
							<div class="col-xs-2">
								<ul class="list-group">
									<@shiro.hasPermission name="admin:update">
									<li class="list-group-item" style="background-color:#f5f5f5;" data-url="MyInfoModifyView">个人信息</li>
									</@shiro.hasPermission>
									
									<@shiro.hasPermission name="admin:changepwd">
									<li class="list-group-item" data-url="PasswordModifyView">密码修改</li>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="admin:manage">
									<li class="list-group-item" data-url="MemberManager">成员管理</li>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="perm:manage">
									<li class="list-group-item" data-url="PowerManager">权限管理</li>
									</@shiro.hasPermission>
								</ul>
							</div>
							<div class="col-xs-10" id="MemberContentInfo" style="margin-left:0px;border:1px solid #ddd;padding:15px;">
							<@shiro.hasPermission name="admin:update">
								<!--表单1-->
								<form id="MyInfoModifyView" novalidate="novalidate" action="${request.contextPath}/admin/center">
									<div class="form-horizontal col-sm-6">
										<div class="form-group">
											<label for="LoginName" class="control-label col-sm-3">登录名：</label>
											<div class="col-sm-9">
												<input id="LoginName" name="LoginName" class="form-control" disabled="disabled" value="<#if Session["admin"]?exists>${Session["admin"].adminname}</#if>" type="text">
											</div>
										</div>
										<div class="form-group">
											<label for="Phone" class="control-label col-sm-3">手机号：</label>
											<div class="col-sm-9">
												<input id="Phone" name="Phone" class="form-control" type="text" value="<#if Session['admin']?exists>${Session['admin'].phone}</#if>">
												<span id="tel_null" style="display: none;color: red;">手机号不能为空</span>
												<span id="tel_format" style="display: none;color: red;">手机号输入格式不正确</span>
											</div>
										</div>
										<div class="form-group">
											<label for="Email" class="control-label col-sm-3">邮箱：</label>
											<div class="col-sm-9">
												<input id="Email" name="Email" class="form-control" type="text" value="<#if Session["admin"]?exists>${Session["admin"].email}</#if>">
												<span id="email_null" style="display: none;color: red;">邮箱号不能为空</span>
												<span id="email_format" style="display: none;color: red;">邮箱号输入格式不正确</span>
											</div>
										</div>
										<div class="form-group" style="text-align:center;">
											<input type="button" value="修改" id="userFixed" class="btn lightblue" />
										</div>
									</div>
								</form>
								<script>
									let passwordFormat = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
									let emailFormat = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
									let a = true;
									let b = true;

									$(function() {
										//手机号检测功能
										$("#Phone").click(function(e) {
											$("#tel_format").hide();
											$("#tel_null").hide();
											$(this).css({
												borderColor: "rgb(30,144,255)"
											});
										}).blur(function(e) {
											let phonevalue = $(this).val();
											if(phonevalue == "" || phonevalue == null || phonevalue == undefined) {
												$(this).css({
													borderColor: "red"
												});
												$("#tel_null").show();
												$("#tel_format").hide();
												a = false;
											} else if(!passwordFormat.test(phonevalue)) {
												$(this).css({
													borderColor: "red"
												});
												$("#tel_format").show();
												a = false;
											} else {
												$(this).css({
													borderColor: "rgb(187,187,187)"
												});
												a = true;
											}
										})
										//邮箱检测功能
										$("#Email").click(function(event) {
											$("#email_format").hide();
											$("#email_null").hide();
											$(this).css({
												borderColor: "rgb(30,144,255)"
											});
										}).blur(function(event) {
											let emailvalue = $(this).val();
											if(emailvalue == "" || emailvalue == null || emailvalue == undefined) {
												$(this).css({
													borderColor: "red"
												});
												$("#email_null").show();
												$("#email_format").hide();
												b = false;
												return b;
											} else if(!emailFormat.test(emailvalue)) {
												$(this).css({
													borderColor: "red"
												});
												$("#email_format").show();
												b = false;
												return b;
											} else {
												$(this).css({
													borderColor: "rgb(187,187,187)"
												});
												b = true;
											}
										})
										/**
										 * 个人信息修改功能
										 * */
										$("#userFixed").click(function() {

											let userEdit = $("#MyInfoModifyView");

											if(!a || !b) {
												return;
											}

											layer.confirm('是否确认保存？', {
												btn: ['确认', '取消'] //按钮
											}, function() {
												let data = {
													adminname: userEdit.find("[name='LoginName']").val(),
													phone: userEdit.find("[name='Phone']").val(),
													email: userEdit.find("[name='Email']").val()
												};

												$.post("${request.contextPath}/admin/update", data, function(ret) {
													if(parseInt(ret) > 0) {
														layer.alert("修改成功");
													}
												});
											});
										});
									})
								</script>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="admin:changepwd">
								<!--表单2-->
								<form id="PasswordModifyView" style="display: none;" action="${request.contextPath}/admin/center">
									<div class="form-horizontal col-sm-6">
										<div class="form-group">
											<label for="oldPwd" class="control-label col-sm-3">原密码：</label>
											<div class="col-sm-9">
												<input type="password" id="oldPwd" name="oldPwd" class="form-control">
												<span style="color: red;display: none;" id="span_num1">密码不能为空</span>
											</div>
										</div>
										<div class="form-group">
											<label for="newPwd" class="control-label col-sm-3">新密码：</label>
											<div class="col-sm-9">
												<input type="password" id="newPwd" name="newPwd" class="form-control">
												<span style="color: red;display: none;" id="span_num2">新密码不能为空</span>
												<span style="color: red;display: none;" id="span_num2_1">
												以字母开头，长度在6~18之间，只能包含字母、数字和下划线
											</span>
											</div>
										</div>
										<div class="form-group">
											<label for="newPwd1" class="control-label col-sm-3">重复新密码：</label>
											<div class="col-sm-9">
												<input type="password" id="newPwd1" name="newPwd1" class="form-control">
												<span style="color: red;display: none;	" id="span_num3">重复密码不能为空</span>
											</div>
										</div>
										<div class="form-group" style="text-align:center;">
											<input type="button" value="修改" id="passwordfixed" class="btn lightblue" />
										</div>
									</div>
								</form>
								<script>
									$(window).ready(function() {
										let passwordformat = /^[a-zA-Z]\w{5,17}$/;

										let c = false;
										let d = false;
										let e = false;

										$("#oldPwd").blur(function() {
											let oldpw = $(this).val();
											if(oldpw == "" || oldpw == null || oldpw == undefined) {
												$(this).css({
													borderColor: "red"
												});
												$("#span_num1").show();
												c = false;
											} else {
												$(this).css({
													borderColor: "rgb(187,187,187)"
												});
												c = true;
											}
										}).click(function() {
											$("#span_num1").hide();
											$(this).css({
												borderColor: "rgb(30,144,255)"
											});
										})

										//新密码创建功能
										$("#newPwd").blur(function() {
											let newpw = $(this).val();
											if(newpw == "" || newpw == null || newpw == undefined) {
												$(this).css({
													borderColor: "red"
												});
												$("#span_num2_1").hide();
												$("#span_num2").show();
												d = false;
											} else if(!passwordformat.test(newpw)) {
												$(this).css({
													borderColor: "red"
												});
												$("#span_num2_1").show();
												d = false;
											} else {
												$(this).css({
													borderColor: "rgb(187,187,187)"
												});
												d = true;
											}
										}).click(function() {
											$("#span_num2_1").hide();
											$("#span_num2").hide();
											$(this).css({
												borderColor: "rgb(30,144,255)"
											});

										})

										//新密码重复验证功能
										$("#newPwd1").blur(function() {
											let newpw1 = $(this).val();
											if(newpw1 == "" || newpw1 == null || newpw1 == undefined) {
												$(this).css({
													borderColor: "red"
												});
												$("#span_num3").show();
												e = false;
											} else {
												$(this).css({
													borderColor: "rgb(187,187,187)"
												});
												e = true;
											}
										}).click(function() {
											$("#span_num3").hide();
											$(this).css({
												borderColor: "rgb(30,144,255)"
											});
										})

										/**
										 * 密码修改功能
										 * */
										$("#passwordfixed").click(function() {
											if(!(c && d && e)) {
												return;
											}

											let oldpw = $("#oldPwd").val();
											let newpw = $("#newPwd").val();
											let newpw1 = $("#newPwd1").val();

											layer.confirm('是否确认修改？', {
												btn: ['确认', '取消'] //按钮
											}, function() {
												let data = {
													oldpw: oldpw,
													newpw: newpw
												};


												$.post("${request.contextPath}/admin/changepass", data, function(ret) {
													if(ret > 0) {
														layer.alert("密码修改成功");
													} else if(ret == -1) {
														layer.alert("原密码错误");
													} else {
														layer.alert("服务器繁忙");
													}
												});

											});

										});

									});
								</script>
								</@shiro.hasPermission>
								<!-- ！表单3 -->
								<div id="MemberManager" class="row MemberContainer" style="display: none;">
									<div class="col-xs-12">
										<div class="">
											<form class="form-inline" id="formSearch">
												<input type="hidden" name="pageIndex" id="pageIndex" value="1">
												<div class="form-group">
													<input name="LoginName" id="LoginNameSearch" style="width:200px;" placeholder="登录名" value="" class="form-control" type="text">
												</div>
												<select id="choosePageCount" class="form-control" name="pageSize">
													<option value="1">每页1条</option>
													<option value="5">每页5条</option>
													<option value="10" selected="selected">每页10条</option>
													<option value="15">每页15条</option>
													<option value="20">每页20条</option>
													<option value="30">每页30条</option>
													<option value="50">每页50条</option>
													<option value="100">每页100条</option>
												</select>
												<span class="btn lightblue" id="listSearch" aria-hidden="true">搜索</span>
												<@shiro.hasPermission name="admin:user:insert">
												<div class="btn lightblue" data-toggle="modal" id="user_add_btn" data-target="#myWindows" data-id="0" aria-hidden="true">添加</div>
												</@shiro.hasPermission>
											</form>
										</div>
										<div id="loanTable" class="" style="border-bottom: 0px;font-size:14px;margin-bottom:3px;margin-top:3px;">
											<!--后台分页使用-->
											<table class="table table-bordered ">
												<thead>
													<tr class="info">
														<th>ID</th>
														<th>登录名</th>
														<th>角色名</th>
														<th>手机号</th>
														<th>邮箱</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody id="admin_list">

												</tbody>
											</table>
											<input type="hidden" id="hidden_rows" value="2">
											<input type="hidden" id="hidden_pageSize" value="10">
											<input type="hidden" id="hidden_pageCount" value="1">
											<input type="hidden" id="hidden_pageIndex" value="1">

											<div id="user_list_page">
											</div>
										</div>
									</div>
								</div>
								<!--表单4-->
								<div id="PowerManager" class="row PowerContainer" style="display: none;">
									<div class="col-xs-12">
										<div class="">
											<form class="form-inline" id="formSearch">
												<input type="hidden" name="pageIndex" id="pageIndex" value="1">
												<div class="form-group">
													<input name="RoleName" id="RoleNameSearch" style="width:200px;" placeholder="角色名" value="" class="form-control" type="text">		<!--搜索角色名-->
												</div>
												<span class="btn lightblue" id=role_search_btn aria-hidden="true">搜索</span>
												<@shiro.hasPermission name="admin:role:insert">
												<div class="btn lightblue" data-toggle="modal" id="power_add_btn" data-target="#myWin0dows" data-id="0" aria-hidden="true">添加</div>
												</@shiro.hasPermission>
											</form>
										</div>
										<div id="loanTable" class="" style="border-bottom: 0px;font-size:14px;margin-bottom:3px;margin-top:3px;">
											<!--后台分页使用-->
											<table class="table table-bordered ">
												<thead>
													<tr class="info">
														<th>ID</th>
														<th>角色名</th>
														<th>具体描述</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody id="role_list">

												</tbody>
											</table>
											<input type="hidden" id="hidden_rows" value="2">
											<input type="hidden" id="hidden_pageSize" value="10">
											<input type="hidden" id="hidden_pageCount" value="1">
											<input type="hidden" id="hidden_pageIndex" value="1">

											<div id="user_list_page">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<@shiro.hasPermission name="admin:user:update">
		<!--用户管理的编辑-->
		<div class="cover" id="user_edit">
			<div class="questionnaire_popup" style="border-radius:2px; width:400px;">
				<div class="questionnaire_popup_head clearfix">
					<div class="qxsleft">编辑</div>
					<div class="qxsright"><span class="btn cancel chacha" style="background-color:#428bca; border:none;color:white">X</span></div>
				</div>
				<div class="questionnaire_popup_body">
					<div class="user_edit_row clearfix">
						<div class="qxsleft">登录名</div>
						<div class="qxsleft"><input name="adminname" type="text" readonly="readonly"></div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">手机号</div>
						<div class="qxsleft"><input name="phone" isRequired="true" type="text"></div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">邮箱</div>
						<div class="qxsleft"><input name="email" isRequired="true" type="email"></div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">用户角色</div>
						<div class="qxsleft">
							<select name="rolelist">
								
							</select>
						</div>
					</div>
				</div>
				<div class="questionnaire_popup_foot clearfix">
					<div class="qxsright"><span class="btn lightblue save">保存</span></div>
					<div class="qxsright"><span class="btn lightblue cancel">取消</span></div>
				</div>
			</div>
		</div>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="admin:user:insert">
		<!--用户管理的添加-->
		<div class="cover" id="user_add">
			<div class="questionnaire_popup" style="border-radius:2px; width:400px;">
				<div class="questionnaire_popup_head clearfix">
					<div class="qxsleft">添加用户</div>
					<div class="qxsright"><span class="btn cancel chacha" style="background-color:#428bca; border:none;color:white">X</span></div>
				</div>
				<div class="questionnaire_popup_body">
					<div class="user_edit_row clearfix">
						<div class="qxsleft">登录名</div>
						<div class="qxsleft"><span><input name="adminname" isRequired="true" type="text"></div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">密码</div>
						<div class="qxsleft"><input name="password" isRequired="true" type="password"></div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">手机号</div>
						<div class="qxsleft"><input name="phone" isRequired="true" type="text"></div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">邮箱</div>
						<div class="qxsleft"><input name="email" isRequired="true" type="email"></div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">用户角色</div>
						<div class="qxsleft">
							<select name="rolelist">
								
							</select>
						</div>
					</div>
				</div>
				<div class="questionnaire_popup_foot clearfix">
					<div class="qxsright"><span class="btn lightblue save">保存</span></div>
					<div class="qxsright"><span class="btn lightblue cancel">取消</span></div>
				</div>
			</div>
		</div>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="admin:role:insert">
		<!--权限管理的添加-->
		<div class="cover" id="power_add">
			<div class="questionnaire_popup" style="border-radius:2px; width:550px;">
				<div class="questionnaire_popup_head clearfix">
					<div class="qxsleft">添加权限</div>
					<div class="qxsright"><span class="btn cancel chacha" style="background-color:#428bca; border:none;color:white">X</span></div>
				</div>
				<div class="questionnaire_popup_body">
					<div class="user_edit_row clearfix">
						<div class="qxsleft">角色名</div>
						<div class="qxsleft">
							<input name="rolename" style="cursor: pointer;" isRequired="true" type="text" placeholder="请选择">
						</div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft" >权限管理</div>
						<div class="qxsleft" id="qxlist">
							<label class="all_selection"><input style="width: 20px;" class="all_checked" type="checkbox" value="">一键全选</label>
							<span class="resourceList">
								
							</span>
						</div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">具体描述</div>
						<div class="qxsleft">
							<textarea name="description" style="width: 100%;height: 120px;resize: vertical;" name="describe" isRequired="true"></textarea>
						</div>
					</div>
				</div>
				<div class="questionnaire_popup_foot clearfix">
					<div class="qxsright"><span class="btn lightblue save">保存</span></div>
					<div class="qxsright"><span class="btn lightblue cancel">取消</span></div>
				</div>
			</div>
		</div>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="admin:role:update">
		<!--权限管理的编辑-->
		<div class="cover" id="power_edit">
			<div class="questionnaire_popup" style="border-radius:2px; width:550px;">
				<div class="questionnaire_popup_head clearfix">
					<div class="qxsleft">编辑权限</div>
					<div class="qxsright"><span class="btn cancel chacha" style="background-color:#428bca; border:none;color:white">X</span></div>
				</div>
				<div class="questionnaire_popup_body">
					<div class="user_edit_row clearfix">
						<div class="qxsleft">角色名</div>
						<div class="qxsleft">
							<input name="rolename" style="cursor: pointer;" isRequired="true" type="text" placeholder="请选择">
						</div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft" >权限管理</div>
						<div class="qxsleft" id="qxlist">
							<label class="all_selection"><input style="width: 20px;" class="all_checked" type="checkbox" value="">一键全选</label>
							<span class="resourceList">
								
							</span>
						</div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">具体描述</div>
						<div class="qxsleft">
							<textarea name="description" style="width: 100%;height: 120px;resize: vertical;" name="describe" isRequired="true"></textarea>
						</div>
					</div>
				</div>
				<div class="questionnaire_popup_foot clearfix">
					<div class="qxsright"><span class="btn lightblue save">保存</span></div>
					<div class="qxsright"><span class="btn lightblue cancel">取消</span></div>
				</div>
			</div>
		</div>
		</@shiro.hasPermission>
	</body>
	<script type="text/javascript">
		$("#loanTable").on("click", ".user_edit_btn", function() {
			let tr = $(this).closest("tr");
			$("#user_edit").find("[name='adminname']").val(tr.find("td:eq(1)").text());
			$("#user_edit").find("[name='phone']").val(tr.find("td:eq(3)").text());
			$("#user_edit").find("[name='email']").val(tr.find("td:eq(4)").text());
			
			
			let options = $("#user_edit").find("[name='rolelist'] option");
			for(let i = 0; i < options.length; ++i){
				let option = $(options[i]);

				if(option.val() == tr.data("role")){
					option.prop("selected", true);
				}
			}
		});

		$("#loanTable").on("click", ".user_del_btn", function() {
			let currBtn = $(this);


			
			layer.open({
	            icon: 3,
	            title: '提示',
	            content: '确定删除吗?',
	            anim: 6,
	            btn: ['确定', '取消'],
	            yes: function() {

					let adminname = currBtn.closest("tr").find("td:eq(1)").text();

					$.post("${request.contextPath}/admin/del", {
						adminname: adminname
					}, function(ret) {
						if(parseInt(ret) > 0) {
							layer.alert("删除成功");
							refresh(currentPage);
						}
					});
				}
			});
		});

		bindListen("#loanTable", ".user_edit_btn", "#user_edit", function() {
			let userEdit = $("#user_edit");
			let currBtn = $(this);

			layer.confirm('是否确认保存？', {
				btn: ['确认', '取消'] //按钮
			}, function() {
				let data = {
					adminname: userEdit.find("[name='adminname']").val(),
					password: userEdit.find("[name='password']").val(),
					phone: userEdit.find("[name='phone']").val(),
					email: userEdit.find("[name='email']").val(),
					roleId: userEdit.find("[name='rolelist']").val()
				};
				let adminname = currBtn.closest("tr").find("td:eq(1)").text();

				$.post("${request.contextPath}/admin/update", data, function(ret) {
					if(parseInt(ret) > 0) {
						layer.alert("编辑成功");
						refresh(currentPage);
					}
				});
			});
		});
		
	
		//用户管理
		$("#user_add_btn").click(function() {
			let userEdit = $("#user_add");
			userEdit.find("[name='adminname']").val("")
			userEdit.find("[name='password']").val("")
			userEdit.find("[name='phone']").val("")
			userEdit.find("[name='email']").val("")
		});

		bindListen("body", "#user_add_btn", "#user_add", function() {

			let userEdit = $("#user_add");

			layer.confirm('是否确认保存？', {
				btn: ['确认', '取消'] //按钮
			}, function() {
				let data = {
					adminname: userEdit.find("[name='adminname']").val(),
					password: userEdit.find("[name='password']").val(),
					phone: userEdit.find("[name='phone']").val(),
					email: userEdit.find("[name='email']").val(),
					roleId: userEdit.find("[name='rolelist']").val()
				};


				$.post("${request.contextPath}/admin/add", data, function(ret) {
					if(ret == "-1") {
						layer.alert("创建失败，该用户已被创建");
					} else if(ret != "0") {
						layer.alert("保存成功");
						refresh(currentPage);
					} else {

					}
				});
			});
		});

		$(function() {
			$(".userCenterDiv li").mouseover(function() {
				$(this).css({
					backgroundColor: "#white",
					cursor: "pointer"
				});
			}).click(function() {
				$(this).css({
					backgroundColor: "#f5f5f5"
				}).siblings().css({
					backgroundColor: "white"
				});
				var url = $(this).attr("data-url"); //将li中的data-url属性返回值给url，也就是将data-url的value给url
				$("#" + url).show(); //等同于$("#"+PasswordModifyView).show();将其展示
				$("#" + url).siblings().hide();
				$(".userCenterDiv li").removeClass("on");
				$(this).addClass("on");
			});
		});

		let currentPage = 1;
		refresh(currentPage);
		
		refreshRole();
		//==========================================================================================================刷新角色列表
		function refreshRole(){

			$.post("${request.contextPath}/Role/get", {
			}, function(obj) {
				let roles = JSON.parse(obj);
				
				let roleListSelect  = $("[name='rolelist']");
				
				let html = "";
				
				for(let i = 0; i < roles.length; ++i){
					html += '<option value="'+roles[i].id+'">'+roles[i].roleName+'</option>';
				}
				
				roleListSelect.html(html);
				
			});
		}
		
		let totalPage = -1;

		//用户管理编辑
		function refresh(pageNum) {
			let adminName = $("#LoginNameSearch").val();
			let pageCount = $("#choosePageCount").val();

			$.post("${request.contextPath}/admin/page/get", {
				"pageNum": pageNum,
				"adminName": adminName,
				"pageCount": pageCount
			}, function(obj) {
				
				obj = JSON.parse(obj);
				let page = obj.page;
				let admins = obj.admins;

				
				totalPage = page.totalPage;

				currentPage = page.pageNow;

				let admin_list = $("#admin_list");
				admin_list.html("");

				for(let i = 0; i < admins.length; ++i) {

					let html = '<tr class="" data-id="' + admins[i].id + '" data-role="'+admins[i].role.id+'">\
				    <td>' + admins[i].id + '</td>\
				    <td>' + admins[i].adminname + '</td>\
				    <td>' + admins[i].role.roleName + '</td>\
				    <td>' + admins[i].phone + '</td>\
				    <td>' + admins[i].email + '</td>\
				    <td>\
				    <@shiro.hasPermission name="admin:user:update">
				        <span class="btn lightblue user_edit_btn">编辑</span>\
				        </@shiro.hasPermission> 
				    <@shiro.hasPermission name="admin:user:delete">
				        <span class="btn red user_del_btn">删除</span>\
				        </@shiro.hasPermission>   
				    </td>\
				</tr>';

					admin_list.append(html);
				}


				let pagegroup = $("#user_list_page");

				let html = '<div class="page_group">\
				<div class="prev btn page_btn">上一页</div>\
				<div class="page_group_number_list">'

				for(let i = 1; i <= page.totalPage; ++i) {
					if(i == page.pageNow) {
						html += '<div class="btn to page_btn disabled">' + i + '</div>';
					} else {
						html += '<div class="btn to page_btn">' + i + '</div>';
					}
				}

				html += '</div>\
				<div class="next btn page_btn">下一页</div>\
				<div class="go_num"><input class="input_number" type="text"></div>\
				<div class="go_btn btn">跳转</div>\
			</div>\
			<div class="page_desc">\
				共有<span class="totalPage">' + page.totalPage + '</span>页，共有<span class="totalRecord">' + page.totalCount + '</span>条记录\
			</div>';
				pagegroup.html(html);
			});
		}

		$("#user_list_page").on("click", ".prev", function() {
			if(currentPage == 1){
				return;
			}
			refresh(currentPage - 1);
		});

		$("#user_list_page").on("click", ".next", function() {
			if(currentPage == totalPage){
				return;
			}
			refresh(currentPage + 1);
		});

		$("#user_list_page").on("click", ".to", function() {
			refresh($(this).text());
		});

		$("#user_list_page").on("click", ".go_btn", function() {
			refresh($(this).prev().find(".input_number").val());
		});

		$("#listSearch").click(function() {
			refresh(currentPage);
		});
	</script>
	
	<script>
	//=======================================================================================================保存
	$("#power_add_btn").click(function() {
		let userEdit = $("#power_add");
		userEdit.find("[name='rolename']").val("");
		userEdit.find("[name='description']").val("");
	});
	bindListen("body", "#power_add_btn", "#power_add", function() {
		let userEdit = $("#power_add");
		layer.confirm('是否确认保存？', {
			btn: ['确认', '取消'] //按钮
		}, function() {
			let ids = [];
			let inputlist = userEdit.find(".manager_span");
			
			for(let i = 0; i < inputlist.length; ++i){
				if($(inputlist[i]).find($("input[name='management']")).prop("checked") == true){
					ids.push($(inputlist[i]).data("id"));
				}
			}
			
			let data = {
				rolename: userEdit.find("[name='rolename']").val(),
				ids: ids,
				description: userEdit.find("[name='description']").val(),
			};

			$.post("${request.contextPath}/Role/add", {
				jsonobj: JSON.stringify(data)
			}, function(ret) {
				if(ret == -1) {
					layer.alert("创建失败，该角色已被创建");
				} else if(ret > 0) {
					layer.alert("保存成功");
					refreshRole();
					powerfresh();
				} else {
					
				}
			});
		});
	});
	
	

	//=====================================================================================================================删除角色
	$("#PowerManager").on("click", ".power_del_btn", function() {
		let currBtn = $(this);
		
		layer.open({
            icon: 3,
            title: '提示',
            content: '确定删除吗?',
            anim: 6,
            btn: ['确定', '取消'],
            yes: function() {
				let roleId = currBtn.closest("tr").data("id");
	
				$.post("${request.contextPath}/Role/delete", {
					roleId: roleId
				}, function(ret) {
					if(parseInt(ret) == -1){
						layer.alert("删除失败，有用户正在使用改角色");
					}else if(parseInt(ret) > 0) {
						layer.alert("删除成功");
						refreshRole();
						powerfresh();
					}
				});
            }
		});
	});
	

	//=====================================================================================================================编辑角色
	$("#PowerManager").on("click", ".power_edit_btn", function() {
		
		
		let tr = $(this).closest("tr");
		let roleId = tr.data("id");
		
		role_edit_id = roleId;
		
		let userEdit = $("#power_edit");
		userEdit.find("[name='rolename']").val(tr.find("td:eq(1)").text());
		userEdit.find("[name='description']").val(tr.find("td:eq(2)").text());
		
		$.post("${request.contextPath}/Role/getRoleResources", {
			roleId: roleId
		}, function(obj){
			let resources = JSON.parse(obj);
			let labels = $("#power_edit label.manager_span");
			
			console.log(resources);

			for(let j = 0; j < labels.length; ++j){
				$(labels[j]).find(".management_checked").prop("checked", false);
				for(let i = 0; i < resources.length; ++i){
					if($(labels[j]).data("id") == resources[i].id){
						$(labels[j]).find(".management_checked").prop("checked", true);
						break;
					}
				}
			}
		});
	});
	
	let role_edit_id = -1;
	
	bindListen("#PowerManager", ".power_edit_btn", "#power_edit", function(){
		let userEdit = $("#power_edit");
		layer.confirm('是否确认保存？', {
			btn: ['确认', '取消'] //按钮
		}, function() {
			let ids = [];
			let inputlist = userEdit.find(".manager_span");
			
			for(let i = 0; i < inputlist.length; ++i){
				if($(inputlist[i]).find($("input[name='management']")).prop("checked") == true){
					ids.push($(inputlist[i]).data("id"));
				}
			}
			
			let data = {
				roleId: role_edit_id,
				rolename: userEdit.find("[name='rolename']").val(),
				ids: ids,
				description: userEdit.find("[name='description']").val(),
			};
			
			console.log(data);

			$.post("${request.contextPath}/Role/update", {
				jsonobj: JSON.stringify(data)
			}, function(ret) {
				layer.alert("保存成功");
				refreshRole();
				powerfresh();
			});
		});
	});
	
	
	
	
	//=======================================全选
	$(".all_selection").click(function(){
		let cover = $(this).closest(".cover");
		
		let allChecked = cover.find(".all_checked");
		
		cover.find("input[name='management']").prop("checked", allChecked.prop("checked"));
	});
	

	//=====================================================================================================================权限列表刷新
	refreshResource();
	function refreshResource(){
		
		$.post("${request.contextPath}/Role/getResources", {
			
		}, function(obj){
			let data = JSON.parse(obj);
			
			let container = $(".resourceList");
			container.html("");

			let div = $("#qxlist");
			for(let i = 0;i<data.length;i++){
				let html = $('<label class="manager_span"> <input style="width: 20px;" class="management_checked" name="management"  type="checkbox"> <span class="text"></span></label>');
				html.data("id",data[i].id);
				html.attr("level", data[i].id);
				html.find(".text").text(data[i].permName);
				container.append(html);
			}
		});
	}
	
	//============================================================================================================================刷新角色列表
	$("#role_search_btn").click(function(){
		powerfresh();
	});
	
	powerfresh();
	//角色管理编辑
	function powerfresh(){
		let rolename = $("#RoleNameSearch").val();
		
		
		
		$.post("${request.contextPath}/Role/getbyname", {
			"roleName": rolename,
		}, function(obj) {
			let roles = JSON.parse(obj);
			
			let role_list = $("#role_list");
			role_list.html("");
			
			for(let i = 0; i < roles.length; ++i) {

				let html = '<tr class="" data-id="' + roles[i].id + '">\
			    <td>' + roles[i].id + '</td>\
			    <td>' + roles[i].roleName + '</td>\
			    <td>' + roles[i].remark + '</td>\
			    <td>';
			    if(i!=0){
			    	html += '\
			    		<@shiro.hasPermission name="admin:role:update">
			    		<span class="btn lightblue power_edit_btn">编辑</span>\
			    		</@shiro.hasPermission>
			    		<@shiro.hasPermission name="admin:role:delete">
				        <span class="btn red power_del_btn">删除</span>\
				        </@shiro.hasPermission>
				        ';
			    }
			    html += '</td>\
				</tr>';

				role_list.append(html);
			}
		});
		
		
		
		$(".resourceList").on("change", "[level] input", function(){
			let list = $(this).closest(".resourceList");
			let level = parseInt($(this).parent().attr("level"));
			let status = $(this).prop("checked");
			
			switch(level){
			case 2:
				list.find("[level='3'] input").prop("checked", status);
				list.find("[level='4'] input").prop("checked", status);
				list.find("[level='5'] input").prop("checked", status);
				list.find("[level='6'] input").prop("checked", status);
				list.find("[level='7'] input").prop("checked", status);
				list.find("[level='19'] input").prop("checked", status);
				break;
			case 3:
			case 4:
			case 5:
			case 6:
				if(status == true) list.find("[level='2'] input").prop("checked", status);
				break;
			case 7:
				if(status == true) list.find("[level='2'] input").prop("checked", status);
				list.find("[level='19'] input").prop("checked", status);
				break;
			case 19:
				if(status == true) list.find("[level='7'] input").prop("checked", status);
				if(status == true) list.find("[level='2'] input").prop("checked", status);
				break;
			case 8:
				list.find("[level='9'] input").prop("checked", status);
				list.find("[level='10'] input").prop("checked", status);
				list.find("[level='11'] input").prop("checked", status);
				list.find("[level='12'] input").prop("checked", status);
				break;
			case 9:
			case 10:
			case 11:
			case 12:
				if(status == true) list.find("[level='8'] input").prop("checked", status);
				break;
			case 13:
				list.find("[level='14'] input").prop("checked", status);
				list.find("[level='15'] input").prop("checked", status);
				list.find("[level='16'] input").prop("checked", status);
				list.find("[level='17'] input").prop("checked", status);
				list.find("[level='18'] input").prop("checked", status);
				list.find("[level='20'] input").prop("checked", status);
				list.find("[level='21'] input").prop("checked", status);
				list.find("[level='22'] input").prop("checked", status);
				list.find("[level='23'] input").prop("checked", status);
				list.find("[level='24'] input").prop("checked", status);
				break;
			case 14:
				if(status == true) list.find("[level='13'] input").prop("checked", status);
				break;
			case 15:
				if(status == true) list.find("[level='13'] input").prop("checked", status);
				break;
			case 23:
				if(status == true) list.find("[level='13'] input").prop("checked", status);
				list.find("[level='16'] input").prop("checked", status);
				list.find("[level='17'] input").prop("checked", status);
				list.find("[level='18'] input").prop("checked", status);
				break;
			case 16:
			case 17:
			case 18:
				if(status == true) list.find("[level='23'] input").prop("checked", status);
				if(status == true) list.find("[level='13'] input").prop("checked", status);
				break;
			case 24:
				if(status == true) list.find("[level='13'] input").prop("checked", status);
				list.find("[level='20'] input").prop("checked", status);
				list.find("[level='21'] input").prop("checked", status);
				list.find("[level='22'] input").prop("checked", status);
				break;
			case 20:
			case 21:
			case 22:
				if(status == true) list.find("[level='24'] input").prop("checked", status);
				if(status == true) list.find("[level='13'] input").prop("checked", status);
				break;
			}
		});
	}
	</script>

</html>

