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
		<link rel="stylesheet" href="${request.contextPath}/css/edit.css">
		<link rel="stylesheet" href="${request.contextPath}/css/common.css">
		<link href="${request.contextPath}/css/tiankong.css" rel="stylesheet">
		<link href="${request.contextPath}/css/nav.css" rel="stylesheet">
		<link rel="shortcut icon" href="${request.contextPath}/img/favicon.ico" type="image/x-icon">
		<script src="${request.contextPath}/js/jquery-3.5.1.js"></script>
		<script src="${request.contextPath}/js/jquery.form.min.js"></script>
		<script src="${request.contextPath}/js/jquery.validate.js"></script>
	</head>

	<body style="background-color:#ececec!important;">
		
		<!-- 导航栏 -->
		<div class="navigation">
			<ul class="list clearfix">
				<li class="qxsleft"><a href="${request.contextPath}/admin/question/list">问卷列表</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/admin/template/list" class="on">模板列表</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/admin/center">用户中心</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/logout">安全退出</a></li>
				<li class="qxsright"><a href="#">当前用户:<#if Session["admin"]?exists>${Session["admin"].adminname}</#if></a></li>
			</ul>
		</div>
		
		<div style="width:1140px; background-color:white; margin:20px auto 100px; padding:10px;box-shadow: #ccc 0 0 20px;">

			<!-- 功能性Tab -->
			<div class="questionTab_div">
				<div style="float: left;font-size: 16px;">
					<span class="">问卷标题：</span><input id="wj_title" value="${template.title}"/>

				</div>
				<div style="float: right;">
					<a class="btn lightblue" id="questionnaire_save">保存</a>&nbsp;
					<a class="btn lightblue" href="${request.contextPath}/admin/template/list">返回列表</a>
				</div>
				<div style="clear: both;"></div>
			</div>
			<!-- 调查问卷的nav -->
			<div class="questionTypeSelect">
				<a class="radio">单选</a>
				<a class="checkBox">多选</a>
				<a class="textArea">填空</a>
			</div>

			<!-- 单选框 -->
			<div class="cover cover_radio">
				<div class="questionnaire_popup">
					<div class="qc_panel">
						<form method="get" action="#">
							<div class="Cbox">
								<div class="Cform">
									<div class="header" style="background:none">
										<span> 标题:</span>
										<input type="text" required class="Btitle" style="width:700px" />
									</div>
									<div class="body1">
										<span>选项:</span>
										<table border="1" class="table1" id="table1" style="width:100%;">
											<tbody id="tbody" name="tbody">
												<tr height="40px">
													<th colspan="2" width="80px">操作</th>
													<th colspan="8" width="380px">选项</th>
													<th style="width: 40px;">设置分数</th>
												</tr>
											</tbody>
										</table>
									</div>
									<div class="body1">
										<span>选项:</span>
										<input type="checkbox" id="risRequired" />是否必填
									</div>
									<div class="body3 clearfix">
										<span class="btn save lightblue" style="width:100%">保存</span>
									</div>
								</div>
							</div>
						</form>
					</div>
					<!-- panel -->
				</div>
			</div>

			<!-- 多选框 -->
			<div class="cover cover_checkbox">
				<div class="questionnaire_popup">
					<div class="qc_panel">
						<form method="get">
							<div class="Cbox">
								<div class="Cform">
									<div class="header" style="background:none">
										<span> 标题:</span>
										<input type="text" required class="Btitle" style="width:700px" />
									</div>
									<div class="body1">
										<span>选项:</span>
										<table border="1" class="table1" id="table1" style="width:100%;">
											<tbody id="tbody" name="tbody">
												<tr height="40px">
													<th colspan="2" width="80px">操作</th>
													<th colspan="8" width="380px">选项</th>
												</tr>
											</tbody>
										</table>
									</div>
									<div class="body1">
										<span>选项:</span>
										<input type="checkbox" id="cisRequired" />是否必填
									</div>
									<div class="body3 clearfix">
										<span type="submit" class="btn save lightblue" style="width:100%">保存</span>
									</div>
								</div>
							</div>
						</form>
					</div>
					<!-- panel -->
				</div>
			</div>

			<!-- 填空框 -->
			<div class="cover cover_textarea">
				<div class="questionnaire_popup">
					<div class="qc_panel">
						<form method="get" action="#" class="form1">
							<div class="Cbox">
								<div class="Cform">
									<div class="header" style="background:none">
										<span> 标题:</span>
										<input required type="text" style="width:700px" class="Ctitle" />
									</div>
									<div class="body1">
										<span>选项:</span>
										<input type="radio" name="sel" id="dan" value="one" checked />单行输入
										<input type="radio" name="sel" id="suan" value="many" />多行输入
										<input type="checkbox" id="isimpt" />是否为关键项
										<input type="checkbox" id="fisRequired" />是否必填
									</div>
									<div class="body3 clearfix">
										<span class="btn save lightblue" data-status="0" style="width:100%">保存</span>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!--问卷调查所有题-->
			<link href="${request.contextPath}/css/questionManager_css.css" rel="stylesheet" />
			<div class="testlist" style="margin-top:10px;">
				<div class="empty_content" style="text-align: center;">请添加题目</div>
			</div>
		</div>
	</body>
	<script src="${request.contextPath}/js/jquery-3.5.1-min.js"></script>
	<script src="${request.contextPath}/js/edit.js"></script>
	<script src="${request.contextPath}/js/radioEdit.js"></script>
	<script src="${request.contextPath}/js/layer/layer.js"></script>
	<script>
		window.onbeforeunload = function() {
			return 'Your own message goes here...';
		}
		
		// 如果不是未发布状态 则不可编辑任何
		$(".noAdd").click(function(){
			layer.msg("已发布或者已截止，不可编辑！")
		})
		
		wj.id=${template.id};
		wj.title="${template.title}";
		wj.type=${template.type};
		
		$("#wj_title").blur(function(){
			wj.title = $(this).val();
		});
		
		
		$.post("../editTemplateQuestion", {id:${template.id}}, function(inObj){
			
			console.log(inObj);
			
			$(".empty_content").remove();
			
			let obj = JSON.parse(inObj);
			
			for(let i = 0; i < obj.subject.length; ++i){
				if(obj.subject[i].type == 0){
					obj.subject[i].score = obj.subject[i].score.split("`");
					obj.subject[i].text = obj.subject[i].text.split("`");
					appendRadio(obj.subject[i]);
				}else if(obj.subject[i].type == 1){
					obj.subject[i].text = obj.subject[i].text.split("`");
					appendCheckbox(obj.subject[i]);
				}else if(obj.subject[i].type == 2){
					appendFill(obj.subject[i]);
				}
			}
		});

		$("#questionnaire_save").on("click", function() {
			if(wj.subject.length == 0){
				layer.alert('请添加题目');
				return;
			}
		
			layer.confirm('是否保存数据？', {
			  btn: ['保存','取消'] 
			}, function(){
				  $.post("${request.contextPath}/admin/template/editTemplateQuestions",{json:JSON.stringify(wj)},function(res){
					res = JSON.parse(res);
					layer.msg(res.msg);
					window.onbeforeunload = null;
					setTimeout(function(){
						location.href = "${request.contextPath}/admin/template/list"
					},1500)
				})
				return;
			}, function(){
			  	console.log(wj);
			  	return;
			});
			
		});
		
		$(".testlist").on("click",".delete", function(){
			let decistion = confirm("是否确定删除");
			if(!decistion){
				return;
			}
			
			let qitem = $(this).closest(".questionItem");
			let s=qitem.data("qid");
			let xh = qitem.find(".questionItem_name_index").text();
			let hxqt = $(".questionItem:gt("+(parseInt(xh)-1)+")").find(".questionItem_name_index");
			
			for(let i = 0; i < hxqt.length; ++i){
				let chxqt = hxqt.get(i);
				chxqt.innerText = parseInt(chxqt.innerText) - 1;
			}
			
			qitem.remove();
			wj.count--;
			let id;
			for(let i=0;i<wj.subject.length;i++){
				if(wj.subject[i].qid==s){
					id = wj.subject[i].id;
					wj.subject.splice(i, 1);
					break;
				}
			}
			$.post("${request.contextPath}/admin/template/deleteOneTemplateQuestion",{id:id},function(res){
				res = JSON.parse(res);
				layer.msg(res.msg);
			})
			console.log(id);
		});
	</script>

</html>