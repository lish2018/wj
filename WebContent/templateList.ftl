<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>模板后台管理系统</title>

		<link rel="stylesheet" href="${request.contextPath}/css/nav.css" />
		<link rel="stylesheet" href="${request.contextPath}/css/common.css" />
		<link rel="stylesheet" href="${request.contextPath}/css/edit.css" />
		<link rel="shortcut icon" href="${request.contextPath}/img/favicon.ico" type="image/x-icon">
		
		<style>
			body{font-size:14px;}
			.template_list{}
			.template_item{border:1px solid #aaa; padding:10px; margin-bottom: 10px; box-sizing: border-box;}
			.qr_box img{width:120px; height:120px; }
			.template_item>.qxsleft{width:1120px;}
			.template_item .qr_box{display:none;}
			.pageBtn{display:inline-block;display:inline-block;width:50px;height:36px;line-height:36px;}
			.template_item[status='1']>.qxsleft{width:990px;}
			.template_item[status='1'] .qr_box{display:block;}
			.template_item .title{font-weight: bold;}
			.template_item .status{font-weight: bold;}
			.template_item .recovery_count{font-weight: bold;}
			.template_item .type{font-weight: bold;}
			.template_item .create_user{font-weight: bold;}
			.template_item .create_time{}
			.template_item .number{}
			.reloadQuestionData:hover{cursor:pointer;}
			.template_item .over_time{color:darkblue;font-weight: bold;}
			.template_item .create_time{}
			
			.template_item[status='1'] .status{color:green;}
			.template_item[status='1'] .status{color:green;}
			.template_item[status='1'] .recovery_count{color:green;}
			
			.template_item .edit_btn{
				font-weight: bold;
				display: inline-block;
				height: auto;
				background-color: skyblue; 
				width: auto;
				color:white; 
				font-size:12px; 
				text-decoration: none;
				padding: 6px;
				margin: 5px;
				border-radius: 5px;
			}
			.template_item .edit_btn:hover{
				background-color: rgb(66,139,202);
			}
			.template_item .record_btn{
				font-weight: bold;
				display: inline-block;
				height: auto;
				background-color: skyblue; 
				width: auto;
				color:white; 
				font-size:12px; 
				text-decoration: none;
				padding: 6px;
				margin: 5px;
				border-radius: 5px;
			}
			.template_item .record_btn:hover{
				background-color: rgb(66,139,202);
			}
			.typechoose{display: none;}
			.typechoose:checked+label{background-color:#428bca; color:white; }
			.typechoose+label{display: inline-block; padding:5px; cursor:pointer; }
		</style>
	</head>
	<body style="background-color:#ececec!important;">
		<#assign base=request.contextPath />
		<!-- 导航栏 -->
		<div class="navigation">
			<ul class="list clearfix">
				<@shiro.hasPermission name="ques:list">
				<li class="qxsleft"><a href="${request.contextPath}/admin/question/list">问卷列表</a></li>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="template:list">
				<li class="qxsleft"><a href="${request.contextPath}/admin/template/list" class="on">模板列表</a></li>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="admin:list">
				<li class="qxsleft"><a href="${request.contextPath}/admin/center">用户中心</a></li>
				</@shiro.hasPermission>
				<li class="qxsleft"><a href="${request.contextPath}/logout">安全退出</a></li>
				<li class="qxsright"><a href="#">当前用户:<#if Session["admin"]?exists>${Session["admin"].adminname}</#if></a></li>
			</ul>
		</div>
		
		<div class="major_container" style="width:1140px; background-color:white; margin:20px auto 100px; padding:10px;box-shadow: #ccc 0 0 20px;">
			
			<h2>模板列表</h2>
			
			<div class="uniform_border"></div>
			
			<div>
				<@shiro.hasPermission name="template:insert">
				<span class="btn lightblue create_quetn_btn">创建模板</span>
				</@shiro.hasPermission>
				<input type="text" placeholder="请输入模板名称" id="list_search">
				<select name="pageCount" id="page_select">
					<option value="5">每页5条</option>
					<option value="10">每页10条</option>
					<option value="15">每页15条</option>
					<option value="20">每页20条</option>
					<option value="30">每页30条</option>
				</select>
				<span class="btn lightblue searchQuestion" id="list_search_btn">搜索</span>
			</div>
			
			<div class="uniform_border"></div>
			
			<div class="template_list">
				<#list templateList as template>
				<div class="template_item clearfix" status="0"  data-type="${template.type}" data-id="${template.id}">
					<div class="qxsleft">
						<div class="clearfix">
							<div class="qxsleft">
								编号：<span class="number">${template.id}</span>&nbsp;
								创建时间：<span class="create_time">${template.createTime?string('yyyy-MM-dd hh:mm:ss')}</span>&nbsp;
							</div>
							
							<div class="qxsright">
							<@shiro.hasPermission name="template:export">
								<span class="btn exportwj" data-id="${template.id}">导出为问卷</span>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="template:delete">	
								<span class="btn deleteQuestion" data-id="${template.id}">删除</span>
							</@shiro.hasPermission>	
							</div>
						</div>
						<div class="uniform_border"></div>
						<div class="">
							名称：<span class="title">${template.title}</span>&nbsp;
							模板类型：<span class="type"><#if template.type == 0> 综合满意度调查<#else> 教学满意度调查</#if></span>&nbsp;
							创建人：<span class="create_user">${template.admin.adminname}</span>&nbsp;
						</div>
						<div class="uniform_border"></div>
						<div class="images">
						<@shiro.hasPermission name="template:update">
							<a class="edit_btn" href="${request.contextPath}/admin/template/toEditTemplateQuestion/${template.id}" >编辑题目</a>
						</@shiro.hasPermission>	
						</div>
					</div>
					
				</div>
				</#list>
			</div>
			
			
			<div class="page_group">
				
				<#if pageObj.pageNow == 1>
					<div class="prev btn page_btn pre_btn">上一页</div>
					<#else>
					<a style="display:inline-block;font-size:12px;" class="prev btn page_btn" 
					href="${request.contextPath}/admin/template/list?pageNow=${pageObj.pageNow-1}&pageCount=${pageObj.pageCount}">上一页</a>
				</#if>
				<div class="page_group_number_list">
				<#list 1..pageObj.totalPage as t>
					<div style="padding:0;text-align:center;" class="btn page_btn">
						<a class="pageBtn" href="${request.contextPath}/admin/template/list?pageNow=${t}&pageCount=${pageObj.pageCount}">${t}</a>
					</div>
				</#list>
				</div>
				<#if pageObj.hasNext == true>
					<a style="display:inline-block;font-size:12px;" class="next btn page_btn" href="${request.contextPath}/admin/template/list?pageNow=${pageObj.pageNow+1}&pageCount=${pageObj.pageCount}">下一页</a>
				<#else>
					<div class="next btn page_btn next_btn">下一页</div>
				</#if>
				
				<div class="go_num"><input class="input_number" type="number" id="go_num"></div>
				<div class="go_btn btn goNumBtn">跳转</div>
			</div>
			
			<div class="page_desc">
				每页<span class="recordNumPerPage">${pageObj.pageCount}</span>条记录，共有<span class="totalPage">${pageObj.totalPage}</span>页，共有<span class="totalRecord">${pageObj.totalCount}</span>条记录
			</div>
			
		</div>
		
		
		<div class="cover" id="template_add">
			<div class="questionnaire_popup" style="border-radius:2px; width:400px;">
				<div class="questionnaire_popup_head clearfix">
					<div class="qxsleft">编辑</div>
					<div class="qxsright"><span class="btn  cancel chacha" style="background-color:#428bca; border:none;color:white;">X</span></div>
				</div>
				<div class="questionnaire_popup_body">
					<div class="user_edit_row clearfix">
						<div class="qxsleft">模板类型：</div>
						<div class="qxsleft">
							<input id="tqtype1" class="typechoose" name="type" value="0" type="radio" checked><label for="tqtype1">综合满意度调查</label>
							<input id="tqtype2" class="typechoose" name="type" value="1" type="radio"><label for="tqtype2">教学满意度调查</label>
						</div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">模板名称：</div>
						<div class="qxsleft"><input type="text" name="title" ></div>
					</div>
				</div>
				<div class="questionnaire_popup_foot clearfix">
					<div class="qxsright"><span class="btn lightblue save">保存</span></div>
					<div class="qxsright"><span class="btn lightblue cancel">取消</span></div>
				</div>
			</div>
		</div>
		
		
		
		<div class="cover" id="quetn_add">
			<div class="questionnaire_popup" style="border-radius:2px; width:400px;">
				<div class="questionnaire_popup_head clearfix">
					<div class="qxsleft">导出为问卷</div>
					<div class="qxsright"><span class="btn  cancel chacha" style="background-color:#428bca; border:none;color:white;">X</span></div>
				</div>
				<div class="questionnaire_popup_body">
					<div class="user_edit_row clearfix">
						<div class="qxsleft">问卷类型：</div>
						<div class="qxsleft">
							<input id="type1" class="typechoose" name="type2" value="0" type="radio" checked><label for="type1">综合满意度调查</label>
							<input id="type2" class="typechoose" name="type2" value="1" type="radio"><label for="type2">教学满意度调查</label>
						</div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">问卷名称：</div>
						<div class="qxsleft"><input type="text" name="title" ></div>
					</div>
					<div class="user_edit_row clearfix">
						<div class="qxsleft">截止时间：</div>
						<div class="qxsleft"><input type="datetime-local" ></div>
					</div>
				</div>
				<div class="questionnaire_popup_foot clearfix">
					<div class="qxsright"><span class="btn lightblue save">创建</span></div>
					<div class="qxsright"><span class="btn lightblue cancel">取消</span></div>
				</div>
			</div>
		</div>
		
	</body>
	
	<script type="text/javascript" src="${request.contextPath}/js/jquery-3.5.1-min.js" ></script>
	<script type="text/javascript" src="${request.contextPath}/js/edit.js" ></script>
	<script src="${request.contextPath}/js/layer/layer.js"></script>
	
	<script>
		bindSimple(".create_quetn_btn", "#template_add", function(){
			if($("input[name='title']").val()==""){
				layer.msg("请先输入标题");
				return false;
			}
			
		// ajax
			
			$.ajax({
			  type: "POST",
			  url: "${request.contextPath}/admin/template/toEdit",
			  dataType: "json",
			  
			  data:{
			  	title:$("input[name='title']").val(),
			  	type:$("input[type='radio']:checked").val(),
			  	createTime: new Date($("input[type='datetime-local']").val()).getTime(),
			  },
			  success: function(data){
			  },
			  error: function(data){
			  	if(data.responseText=='success'){
			  		layer.load(1);
			  		setTimeout(function(){
			  			layer.closeAll('loading');
			  			location.href = "${request.contextPath}/admin/template/edit"
			  		},1000)
			  	}
			  }
			});
		});
		
		$(".deleteQuestion").click(function(){
			let _this = this
			layer.open({
	            icon: 3,
	            title: '提示',
	            content: '确定删除吗?',
	            anim: 6,
	            btn: ['确定', '取消'],
	            yes: function () {
		            $.ajax({
					  type: "POST",
					  url: "${request.contextPath}/admin/template/deleteTemplateQuestion/"+$(_this).attr("data-id"),
					  dataType: "json",
					  success:function(res){
					  	layer.msg(res.msg);
					  	setTimeout(()=>{
                        	location.reload();
                        },1500)
					  }
					})
	            }
			});
			return false;
		});
		
		
		
		// 数量搜索
		$("select[name='pageCount']").on("change",function(){
			let pageNow = 1; 
			let pageCount = $("select[name='pageCount']").val();
			location.href = "${request.contextPath}/admin/template/list?pageNow="+pageNow+"&pageCount="+pageCount;
		})
		
		//跳转页码
		$(".goNumBtn").click(function(){
			// 如果搜索框内有内容，就不立即刷新
			if($("#list_search").val() != ""){
				return;
			}
			let pageNow = getUrlParam('pageNow');
			if(!pageNow){
				location.href = "${request.contextPath}/admin/template/list?pageNow="+$("#go_num").val()+"&pageCount="+$("#list_search").val();
				return ;
			}
			let pageCount = getUrlParam('pageCount'); 
			//英文正则
			var illageCent = /^[A-Za-z]+$/;
			if($("#go_num").val()<0 || $("#go_num").val()>${pageObj.totalPage} || $("#go_num").val()==""){
				layer.msg("请输入正确的页码~");
				return;
			}
			location.href = "${request.contextPath}/admin/template/list?pageNow="+$("#go_num").val()+"&pageCount="+pageCount;
		})
		
		// select下拉框赋值
		window.onload = function(){
			let pageCount = getUrlParam('pageCount'); 
			let pageNow = getUrlParam('pageNow'); //当前页码
			if(pageCount){
				$("select[name='pageCount']").val(pageCount)
			}else{
				$("select[name='pageCount']").val(5)
			}
			let pageTotal = $(".totalPage").text();
			
			// 页码判断
			if($(".recordNumPerPage").text()>$(".totalRecord").text()){
				$(".page_btn").removeAttr("href");
			}
			
			
			
			// 当前页码
			$(".page_group_number_list").children().each(function(v,i){
				console.log(pageNow);
				if($(this).children().text()==pageNow){
					$(this).addClass("disabled");
					$(this).children().css("color","white");
				}
				if(!pageNow && $(this).text()==1){
					$(this).addClass("disabled");
					$(this).children().css("color","white");
				}
			})
			
			
		}
		
		
		// 获取URL参数值
		function getUrlParam(name) {
			var query = window.location.search.substring(1);
			var param_arr = query.split("&");
			for (var i = 0; i < param_arr.length; i++) {
				var pair = param_arr[i].split("=");
				if (pair[0] == name) {
					return pair[1];
				}
			}
			return (false);
		}
		
		
		
		// 模糊查询
		$("#list_search_btn").click(function(){
			let templateName = $("#list_search").val();
			
			let pageNow = 1; 
			let pageCount = $("select[name='pageCount']").val();
			location.href = "${request.contextPath}/admin/template/list?pageNow="+pageNow+"&pageCount="+pageCount+"&name="+templateName;
		});
		
		$(".template_list").on("click", ".exportwj", function(){
			let titem = $(this).closest(".template_item");
			
			$("#quetn_add").find("input[name='type2'][value='"+ titem.data("type") +"']").prop("checked", true);
			$("#quetn_add").data("id", titem.data("id"));
			$("#quetn_add").find("input[name='title']").val(titem.find(".title").text());
			
			let createTime = new Date();
			
			createTime.setMinutes(createTime.getMinutes() + 30);
			
			$("#quetn_add").find("input[type='datetime-local']").val(getFormat(createTime));
		});
		
		function getFormat(nTime){
			format = "";
			format += nTime.getFullYear()+"-";
			format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
			format += "-";
			format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
			format += "T";
			format += nTime.getHours()<10?"0"+(nTime.getHours()):(nTime.getHours());
			format += ":";
			format += nTime.getMinutes()<10?"0"+(nTime.getMinutes()):(nTime.getMinutes());
			format += ":00";
			return format;
		}
		
		
		bindListen(".template_list", ".exportwj", "#quetn_add", function(){
			if($("#quetn_add input[name='title']").val()==""){
				layer.msg("请先输入标题");
				return false;
			}
			if(isNaN(new Date($("#quetn_add input[type='datetime-local']").val()).getTime())){
				layer.msg("请选择截止时间")
				return false;
			}
			if(new Date($("#quetn_add input[type='datetime-local']").val()).getTime()<new Date().getTime()){
				layer.msg("请选择正确的时间");
				return false;
			}
			
			
			
			$.ajax({
			  type: "POST",
			  url: "${request.contextPath}/admin/question/toCreateByTemplate",
			  dataType: "json",
			  
			  data:{
			  	id:$("#quetn_add").data("id"),
			  	title:$("#quetn_add input[name='title']").val(),
			  	type:$("#quetn_add input[type='radio']:checked").val(),
			  	createTime: new Date($("#quetn_add input[type='datetime-local']").val()).getTime(),
			  },
			  success: function(data){
			  },
			  error: function(data){
			  	if(data.responseText=='success'){
			  		layer.load(1);
			  		setTimeout(function(){
			  			layer.closeAll('loading');
			  			location.href = "${request.contextPath}/admin/question/createByTemplate"
			  		},1000)
			  	}
			  }
			});
		});
	</script>
</html>
