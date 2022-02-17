<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
        content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />

    <script src="${request.contextPath}/js/jquery-3.5.1.min.js"></script>
    <title>选择您的班级</title>	
	<link rel="stylesheet" href="${request.contextPath}/css/doquest.css" />
	<script src="https://cdn.bootcdn.net/ajax/libs/layer/3.1.1/layer.min.js"></script>
	<style>
		#inputForm{
			padding:40px;
		}
		
		.question_body{
			margin:0px; 
		}
		
	</style>
</head>

<body>
    <div class="main">
        <h1>调查问卷</h1>

        <form action="${request.contextPath}/questions/qaq/${questionInfo.id}" method="get" id="inputForm">
            <div class="question_body">


            <div>
                <div class="question_box1">
                    <span>1.</span> <span>请选择班级的呢？</span> <span class="asterisk">*</span>
                </div>
                <div class="question_box2">
                	<input type="hidden" value="${questionInfo.id}">
                    <span class="required">
                        <select id="selectClass" required name="class" style="margin: 10px 0;">
                            <option value="null">请选择你的班级</option>
                            <#list teams as team>
                            <option value="${team.id}">${team.name}</option>
                            </#list>
                        </select>
                    </span>
                </div>
            </div>

            <div class="submit" style="width:100%;">
                <input type="submit" value="开始答卷" id="submit" class="btn1">
            </div>

        </form>
    </div>
</body>
<script type="text/javascript">

    /*在gameid增加对检查按钮的验证*/
    $("#inputForm").submit(function () {
        return validate();
    });
    
    function validate(){
        if (document.getElementById('selectClass').options[0].selected == true) {
            alert("请选择你的班级");
            return false;
        }
        return true;
    }
    
	if(${questionInfo.status}==0){ // 无权访问
		location.href = "${request.contextPath}/index"
	}
	if(${questionInfo.status}==2){ // 问卷截止
		layer.msg("问卷已经截止~");
		setTimeout(function(){
			$("form").html("<div class='returnMessage'>感谢您本次问卷的作答</div>");
		},1500);
	}
	//这里就是写判断

    $.post("/wj/admin/question/ipsearch", {
        ip:  "${ip}",
        questionnaireId: ${questionInfo.id}
    }, function (obj) {
        var jsonObject =  JSON.parse(obj);
        console.log(jsonObject.status);
        
        if(jsonObject.status == "no"){
            $("form").html("<div class='returnMessage'>您已经完成过一次作答了，请不要重复作答</div>");
        }else{
            //跳转
            console.log("开始答题");
        }
    });
    
</script>

</html>