<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		<title>后台登录</title>
		<link href="${request.contextPath}/css/login.css" rel="stylesheet" />
		<link href="${request.contextPath}/css/login.css" rel="stylesheet" />
		<link rel="shortcut icon" href="${request.contextPath}/img/favicon.ico" type="image/x-icon">
		<style type="text/css">
			.login_form span {
				color: red;
				display: none;
			}
		</style>
	</head>

	<body>
		<div class="login">
			<div class="message">长沙新华盘龙大观园教学点问卷调查系统</div>
			<div id="darkbannerwrap"></div>

			<form class="login_form" method="post" action="${request.contextPath}/login">
				<input id="name" placeholder="登录名" name="username" type="text" value="">
				<span id="username_error">登录名不能为空</span>
				<hr class="hr15">
				<input id="password" placeholder="密码" name="password" type="password" value="">
				<span id="password_error">密码不能为空</span>
				<hr class="hr15">
				<input value="登录 " style="width: 100%;" id="submit" type="button">
				<hr class="hr15">
				<div style="text-align: right;"></div>
				<hr class="hr20">
				<div id="errorInfo" style="color: red;"></div>
			</form>
		</div>
		<script src="${request.contextPath}/js/jquery-3.5.1-min.js" type="text/javascript"></script>
		<script src="${request.contextPath}/js/layer/layer.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#submit").click(function(){
					if($("#name").val()==""){
						layer.msg("用户名不能为空！")
						return ;
					}
					if($("#password").val()==""){
						layer.msg("密码不能为空！")
						return ;
					}
					
					$(this).attr({
						value:"正在登录中.....",
						type:"submit"
					})
					
					$("form").submit();
					
				});

				$(document).keydown(function(event){
					if(event.keyCode == 13){
						$("input[type='button']").click();
					}
				});
			});
		</script>
	</body>

</html>