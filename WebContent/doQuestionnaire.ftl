<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
        content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />

    <script src="${request.contextPath}/js/jquery-3.5.1.min.js"></script>
    <!-- <script src="lib/jquery.validate.min.js"></script> -->
    <title>新华问卷</title>
	<link rel="stylesheet" href="${request.contextPath}/css/doquest.css" />
    <link rel="shortcut icon" href="${request.contextPath}/img/favicon.ico" type="image/x-icon">
    <style>
        button {
            width:100px;height:50px;background-color: rgb(148, 221, 253);border:none;border-radius: 5px;
        }
        button:hover {
                background-color: rgb(70, 98, 217);cursor: pointer;
        }
        label{max-width:240px; display:block; word-break: break-all;}
        
    </style>
</head>

<body>
    <div class="main">
        <h1>调查问卷</h1>

        <form id="inputForm">
            <div class="question_body">


                <#list lists as list>
                    <div>
                    <div class="question_box1">
                        <span>${list.number+1}.</span> <span>${list.title}</span> <#if  list.isRequired == 1>  <span class="asterisk">* </#if></span>
                    </div>
                    <div class="question_box2">
                        <span class="required">
                            <#list options[list_index] as opt>   
                             <#if  list.type == 0>
                                <label for='${list_index}${opt.id}'><input type='radio' id='${list_index}${opt.id}' name='${list.number+1}_${list.type}' value='${opt.id}'>${opt.value}<span style="color:red;">(${opt.score}分)</span></label>  
                             <#elseif list.type == 1>
                                <label for='${list_index}${opt.id}'><input type='checkbox' id='${list_index}${opt.id}' name='${list.number+1}_${list.type}' value='${opt.id}'>${opt.value}</label>
                             <#elseif list.type == 2>
                                <textarea cols="80" name='${list.number+1}_${list.type}' rows="10"></textarea>
                             </#if>
                            </#list>
                        </span>
                    </div>
                </div>
                </#list>
                

            <div class="submit">
               <!--  <input type="submit" value="提交" id="submit"> -->
               <input class="btn1" type="button" value="完成">
            </div>
        </form>

    
    </div>

</body>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<script type="text/javascript">
    var urlPath = location.pathname;
    var stringUrl = urlPath.split('/');
    //这个是拿到第几张问卷
    var questionnaireId = stringUrl[stringUrl.length-1];
    //这个是获取班级
    var banji = getQueryVariable("class");


    window.onload = function(){
            $.post("/wj/admin/question/ipsearch", {
                ip: "${ip}",
                questionnaireId: questionnaireId
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
    }



    /*在gameid增加对检查按钮的验证*/
    var flag = false;
    
    function validate(){
    	


        //这一块代码是看必填的redio和checkbox
        var checkbox_list = [];
        var textAreaList = [];
        // 这个是有checked属性的元素的列表，就是redio和checkbox
        <#list lists as list>
            <#if  list.type < 2 && list.isRequired == 1>
                checkbox_list.push('${list.number+1}_${list.type}');
         //   console.log(checkbox_list[${list_index}]);
            <#elseif list.type == 2 && list.isRequired == 1>
                textAreaList.push('${list.number+1}_${list.type}');
               // console.log(textAreaList);
            </#if>
        </#list>

        const checkbox_list_num = checkbox_list.length;
        let tempNum = 0;
        for (let i = 0; i < checkbox_list.length; i++) {
            const element = checkbox_list[i];
            var checkbox = $("input[name="+element+"]");
            // console.log(checkbox);

            // 单选或者复选框的上一级也就是那个 span标题
            let checkbox_parent = checkbox.parent('label')[0].parentElement;
            // console.log(checkbox_parent);

            for (let index = 0; index < checkbox.length; index++) {
                const ele = checkbox[index];
               //  console.log(ele.checked + "?");
                if (ele.checked) {
                    tempNum++;
                    break;
                }
            }
            //如果有一个被填写了，那就说明 i!=tempNum;
            if (i === tempNum) {
                window.scrollTo(0, checkbox_parent.offsetTop - 50);
                alert('还有选项没有填写哦');
                return false;
            }
        }

        for (let i = 0; i < textAreaList.length; i++) {
            var textAreaEle =   $("textarea[name="+textAreaList[i]+"]");
            if(textAreaEle.val().length<1){
                alert('还有选项没有填写哦');
                window.scrollTo(0, textAreaEle.parent('span')[0].offsetTop - 50);
                return false;
            }

        }

        return true;
    }



    function getQueryVariable(variable){
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
    }


    $(".btn1").click(function () {
        flag = validate();
    	
        if(flag != true){
            return ;
        }

        var obj = {
            banjiId: banji,
            ip: "${ip}",
            questionnaireId:questionnaireId,
            formData: $('form').serialize()
        };

        

        if(flag){
            $.post("/wj/json", {
            jsonstr: JSON.stringify(obj),
            formData: $('form').serialize()
        }, function (obj) {
               $("form").html("<div class='returnMessage'>感谢您的本次问卷的作答</div>");           
        });
            $(".btn1").attr("disabled","true");

        }
        
    });
</script>

</html>