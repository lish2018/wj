<!doctype html>
<html lang="zh-cn">

	<head>
		<meta charset="UTF-8">
		<title>信息提示</title>
		<link rel="shortcut icon" href="${request.contextPath}/img/favicon.ico" type="image/x-icon">
		<style>
			body {
				margin: 0;
			}
			
			h1 {
				font-family: 'Lato', sans-serif;
				font-weight: 300;
				letter-spacing: 2px;
				font-size: 35px;
			}
			
			p {
				color: #fff;
				font-size: 25px;
			}
			
			.header {
				position: relative;
				text-align: center;
				background: linear-gradient(60deg, rgba(84, 58, 183, 1) 0%, rgba(0, 172, 193, 1) 100%);
				color: white;
			}
			
			.inner-header {
				height: 65vh;
				width: 100%;
				margin: 0;
				padding: 0;
			}
			
			.flex {
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				text-align: center;
			}
			
			.waves {
				position: relative;
				width: 100%;
				height: 15vh;
				margin-bottom: -7px;
				min-height: 100px;
				max-height: 150px;
			}
			
			.content {
				position: relative;
				height: 20vh;
				text-align: center;
				background-color: white;
			}
			
			.parallax>use {
				animation: move-forever 25s cubic-bezier(.55, .5, .45, .5) infinite;
			}
			
			.parallax>use:nth-child(1) {
				animation-delay: -2s;
				animation-duration: 7s;
			}
			
			.parallax>use:nth-child(2) {
				animation-delay: -3s;
				animation-duration: 10s;
			}
			
			.parallax>use:nth-child(3) {
				animation-delay: -4s;
				animation-duration: 13s;
			}
			
			.parallax>use:nth-child(4) {
				animation-delay: -5s;
				animation-duration: 20s;
			}
			
			@keyframes move-forever {
				0% {
					transform: translate3d(-90px, 0, 0);
				}
				100% {
					transform: translate3d(85px, 0, 0);
				}
			}
			
			@media (max-width:768px) {
				.waves {
					height: 40px;
					min-height: 40px;
				}
				.content {
					height: 30vh;
				}
				h1 {
					font-size: 24px;
				}
			}
			
			#UrlId {
				color: skyblue;
				font-size: 20px;
			}
		</style>
	</head>

	<body>
		<div class="header">

			<div class="inner-header flex">
				<p style="font-size: 35px;">登录名或密码错误</p>
				<h1>您将在<span id="seconds" style="color: lightblue;">3</span>秒后返回登录界面</h1>
				<a id="UrlId" href="${request.contextPath}/index">无需等待，点击进入</a>
			</div>

			<!-- 背景 -->
			<div>
				<svg class="waves" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
					<defs>
						<path id="gentle-wave" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
					</defs>
					<g class="parallax">
						<use xlink:href="#gentle-wave" x="48" y="0" fill="rgba(255,255,255,0.7" />
						<use xlink:href="#gentle-wave" x="48" y="3" fill="rgba(255,255,255,0.5)" />
						<use xlink:href="#gentle-wave" x="48" y="5" fill="rgba(255,255,255,0.3)" />
						<use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
					</g>
				</svg>
			</div>

		</div>
	</body>
	<script src="${request.contextPath}/js/jquery-3.5.1.js" type="text/javascript"></script>
	<script>
		// 获取当前url地址
		var url = $("#UrlId").attr("href");
		console.log(url);
		// 截取传递的url参数
		var seconds = document.getElementById("seconds");
		// 设置秒数
		var num = 3;
		var timer = setInterval(function() {
			num--;
			seconds.innerText = num;
			if(num == 0) {
				window.location.href = url;		//如果值为0，则自动跳转到主页
			}
		}, 1000)
	</script>
</html>
