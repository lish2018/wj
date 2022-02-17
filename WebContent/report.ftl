<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>问卷回收详情</title>
    <link rel="stylesheet" href="${request.contextPath}/css/report.css">
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${request.contextPath}/css/nav.css" />
    <link rel="shortcut icon" href="${request.contextPath}/img/favicon.ico" type="image/x-icon">
    <script src="${request.contextPath}/js/jquery-3.5.1.min.js"></script>
    <script src="${request.contextPath}/js/echarts.min.js"></script>
    <script src="${request.contextPath}/js/report.js"></script>
    <script src="${request.contextPath}/js/jquery-ui.min.js"></script>
    
    <style>
    	.navigation {
		    margin-bottom: 0px;
		}
		.btn{
			border-radius: 0px; 
			padding:0px;
			cursor: pointer;
			}
		.btn.lightblue,
		.btn.red{
			padding:5px 10px;
			}
		.float_right{
			float:right;
		}
		#sendMail{
			border-left:1px solid #bbbbbb;
			float:right;
			margin-right:100px;
		}
		option{
			border:1px solid #bbbbbb;
		}
    </style>
</head>

<body style="background-color:#ececec!important;">
		
		<!-- 导航栏 -->
		<div class="navigation">
			<ul class="list clearfix">
				<li class="qxsleft"><a href="${request.contextPath}/admin/question/list" class="on">问卷列表</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/admin/template/list">模板列表</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/admin/center">用户中心</a></li>
				<li class="qxsleft"><a href="${request.contextPath}/logout">安全退出</a></li>
				<li class="qxsright"><a href="#">当前用户:<#if Session["admin"]?exists>${Session["admin"].adminname}</#if></a></li>
			</ul>
		</div>
	
    <div id="div_center" class="div"  style="width:1140px; background-color:white; margin:20px auto 100px; padding:10px;box-shadow: #ccc 0 0 20px;">
        <div id="div_table" class="div">
            <div id="table_bar" class="div"></div>
            <div id="table_pie" class="div">
                <div style="width: 400px;height: 40px;text-align: center;">
                    <h4><b>各班满意度</b></h4>
                </div>
                <#assign reports=reportMap?keys>
                <#list reports as report>
            	<#if report="teamNameMap">
            	<#list reportMap[report] as teamNameMap>
        		<#assign teamName=teamNameMap?keys>
        		<#list teamName as name>
    			<div id="${name}"></div>
        		</#list>
        		</#list>
    			<#break>	
            	</#if>
    			</#list>
            </div>
        </div>
        <div id="div_data" class="div">
            <div id="div_data_select" class="div">
                <table>
                    <tbody>
                        <tr>
                            <td>班级：
                                <select name="class" id="class_select">
                                    <option value="" selected>--请选择--</option>
                                    <#assign reports=reportMap?keys>
            						<#list reports as report>
				                	<#if report="teamNameMap">
			                		<#list reportMap[report] as teamNameMap>
			                		<#assign teamName=teamNameMap?keys>
			                		<#list teamName as name>
                        			<option value="${teamNameMap[name]}">${name}</option>
			                		</#list>
			                		</#list>
        							<#break>
				                	</#if>
					    			</#list>
                                </select>
                            </td>
                            <td>&nbsp;提交时间：
                                从&nbsp;<input name="datetime_start" id="datetime_start" onchange="time(this.id)"
                                    placeholder="开始时间" readonly>
                                到&nbsp;<input name="datetime_end" id="datetime_end" onchange="time(this.id)"
                                    placeholder="截止时间" readonly>
                            </td>
                            <td>&nbsp;得分：
                            	从&nbsp;<select name="startscore" id="startscore">
                                    <option value="90" >90</option>
                                    <option value="60" >60</option>
                                    <option value="30" >30</option>
                                    <option value="0" selected>0</option>
                                </select>
                            	到&nbsp;<select name="endscore" id="endscore">
                                    <option value="100" selected>100</option>
                                    <option value="90" >90</option>
                                    <option value="60" >60</option>
                                    <option value="30" >30</option>
                                </select>
                            </td>
                            <td>
                            	<span class="btn lightblue searchQuestion" id="attachSearch">搜索</span> 
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div id="div_data_detail" class="div" style="margin-top:10px; overflow-y: auto; border:1px solid gray;">
                <table class="" style="width:100%;">
                    <thead>
                        <tr>
                            <th style="width:60px">分数</th>
                            <th style="width:160px">提交时间</th>
                            <th style="width:125px">IP</th>
                            <th style="width:400px">得分低的题目</th>
                            <th>建议</th>
                        </tr>
                    </thead>
                    <tbody class="reportTable">
                    </tbody>
                </table>
            </div>
            
            
            <div class="page_group" style="margin-top:20px">
				<div class="prev btn page_btn disabled">上一页</div>
				<div class="page_group_number_list"></div>
				<div class="next btn page_btn">下一页</div>				
				<div class="go_num"><input class="input_number" type="text"></div>
				<div class="go_btn btn">跳转</div>
				<@shiro.hasPermission name="email:send">				
				<div class="go_btn btn" id="sendMail" style="padding:0px;border:none">
					<button id="send" class="lightblue" style="height:40px">报告发送到我的邮箱</button>
				</div>
				</@shiro.hasPermission>
			</div>			
			<div class="page_desc">有<span class="totalPage">3</span>页，共有<span class="totalRecord">15</span>条记录</div>
        </div>
    </div>
</body>

<script>
	let email = "${email}";
	let questionnaireId = ${questionnaireId};
    <#assign reports=reportMap?keys>
	<#list reports as report>
	<#if report="radioTitles">
	let ${report} = [<#list reportMap[report] as radioTitle>"${radioTitle}",</#list>];
	</#if>
	<#if report="scoreList">
	let ${report} = [
	<#list reportMap[report] as option> 
	 [<#list option as score>"${score}",</#list>],
	</#list>
	<#list reportMap[report] as score>
	</#list>
	];
	</#if>
	</#list>
    let bar = echarts.init(document.getElementById('table_bar'));
    let option = {
        title: {
            text: '各班级完成情况'
        },
        tooltip: {},
        legend: {
            x: 'right',
            y: 'top',
            data: ['完成人数', '总人数']
        },
        xAxis: {
            data: [
        	<#assign reports=reportMap?keys>
            <#list reports as report>
        	<#if report="teamNameMap">
    		<#list reportMap[report] as teamNames>
    		<#assign teamName=teamNames?keys>
    		<#list teamName as name>
            	"${name}",
            	</#list>
    		</#list>
    		<#break>
        	</#if>
			</#list>
            ]
        },
        yAxis: {},
        series: [{
            name: '完成人数',
            type: 'bar',
            data: [
       		<#assign reports=reportMap?keys>
            <#list reports as report>
        	<#if report="teamAnswers">
    		<#list reportMap[report] as teamAnswers>
		 		"${teamAnswers?size}",
    		</#list>
		    <#break>
        	</#if>
			</#list>
            ],
        }, {
            name: '总人数',
            type: 'bar',
            data: [
        	<#assign reports=reportMap?keys>
            <#list reports as report>
        	<#if report="teamNums">
    		<#list reportMap[report] as teamNum>
				"${teamNum}",
    		</#list>
   			<#break>
        	</#if>
			</#list>
            	],
        }]
    }
    bar.setOption(option);
    
    <#assign reports=reportMap?keys>
    <#list reports as report>
	<#if report="satisfactionMap">
	<#assign teamNames=reportMap[report]?keys>
	<#list teamNames as teamName>
		drawPieChart("${teamName}", "${teamName}", ${reportMap[report][teamName]});	
	</#list>
    <#break>
	</#if>
	</#list>
    function drawPieChart(id, className, satisfaction) {
        let myChart = echarts.init(document.getElementById(id));
        let option = {
            color: [satisfaction > 80 ? 'red' : 'green', '#fff'],
            series: [{
                name: className,
                type: 'pie',
                radius: ['32%', '50%'],
                label: {
                    normal: {
                        position: 'center',
                        textStyle: {
                            fontSize: '12',
                            color: '#999'
                        },
                        formatter: "{a}\n{d}%"
                    }
                },
                data: [{
                        label: {
                            normal: {
                                show: true
                            }
                        },
                        value: satisfaction,
                        itemStyle: {
                            emphasis: {
                                color: satisfaction > 80 ? 'red' : 'green'
                            }
                        },
                    },
                    {
                        label: {
                            normal: {
                                show: false
                            }
                        },
                        value: 100 - satisfaction,
                        itemStyle: {
                            emphasis: {
                                color: '#fff'
                            }
                        }
                    }
                ]
            }]
        };
        myChart.setOption(option);
    }
    
    refreash(1);
    
    $("#endscore").change(function(e){
	    let start = $("#startscore").val();
	    let end =  $("#endscore").val();
	    if(parseInt(start)>=parseInt(end)){
	    	$(this).val("100");
            alert("起始分数不能大于或等于截止分数");
	    }else{
    		refreash(1);
    	}
    });
    $("#startscore").change(function(){
	    let start = $("#startscore").val();
	    let end =  $("#endscore").val();
	    if(parseInt(start)>=parseInt(end)){
	    	$(this).val("0");
            alert("起始分数不能大于或等于截止分数");
	    }else{
    		refreash(1);
    	}
    });
    $("#class_select").change(function(){
    	refreash(1);
    });
    $("#datetime_start").change(function(){
    	if($(this).val().indexOf("NaN") != -1){
    		$(this).val("");
    	}
    	refreash(1);
    });
    $("#datetime_end").change(function(){
    	if($(this).val().indexOf("NaN") != -1){
    		$(this).val("");
    	}
    	refreash(1);
    });
    
    $("#attachSearch").click(function(){
    	refreash(1);
    });
    
    $("#send").on("click",function(){
    sendEmail(email,questionnaireId)
    //alert("123");
    }
    );

</script>

</html>