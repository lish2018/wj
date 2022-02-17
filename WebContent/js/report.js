function time(id) {
    let start = document.getElementById("datetime_start").value;
    let end = document.getElementById("datetime_end").value;
    if (start.length == 0 || end.length == 0) {} else {
        let startTime = new Date(start);
        let endTime = new Date(end);
        if (startTime.getTime() > endTime.getTime()) {
            document.getElementById(id).value = "";
            alert("截止时间不能小于开始时间");
        }
    }
}

var page;
var now;
var totalPage;

function refreash(pageNum) {
    let starttime = $("#datetime_start").val();
    let endtime = $("#datetime_end").val();
    let startscore = $("#startscore").val();
    let endscore = $("#endscore").val();
    let classId = $("#class_select").val();
    console.log(startscore);
    console.log(endscore);
    $.post("../admin/answer/get", {
        pageNum: pageNum,
        teamId: classId,
        questionnaireId: questionnaireId,
        beginTime: starttime,
        endTime: endtime,
        start: startscore,
        end: endscore
    }, function (obj) {
        console.log(obj);
        let tbody = $(".reportTable");
        let html = "";
        let answers = obj.answers;
        for (let i in answers) {
            //分数
            //console.log(typeof (answers[i].radio));
            let score = 0;
            let optionString = (answers[i].radio).split("`");
            if (optionString.length > 0 && scoreList.length > 0) {
                let option = new Array();
                for (let i = 0; i < optionString.length; i++) {
                    let options = optionString[i].split("^");
                    option.push(options[1]);
                }
                for (let i = 0; i < option.length; i++) {
                    score += parseInt(scoreList[i][option[i]]);
                }
            }
            //提交时间
            let Time = new Date(answers[i].commitTime);
            let commitTime = `${Time.getFullYear()}-${Time.getMonth()+1}-${Time.getDate()} ${Time.getHours()}:${Time.getMinutes()}:${Time.getSeconds()}`;
            //IP
            let IP = answers[i].ip;
            //得分低的单选
            let radio = "";
            for (let i = 0; i < scoreList.length; i++) {
                let max = 0;
                for (let j = 0; j < scoreList[i].length; j++) {
                    if (max < scoreList[i][j]) {
                        max = scoreList[i][j];
                    }
                }
                if (max / 2 + 1 > option[i]) {
                    radio += radioTitles[i];
                }
            }
            //关键项
            let key = answers[i].key;
            if (answers[i].text.indexOf("^") > 0) {
                let t = answers[i].text.split("`");
                let e = new Array();
                for (let k in t) {
                    let x = (t[k].split("^"));
                    e.push(x[1]);
                }

                //console.log(e);

                for (let k in e) {

                    key += `${e[k]}。`;
                }
            }

            html += `<tr><td>${score}</td><td>${commitTime}</td><td>${IP}</td><td>${radio}</td><td>${key}</td></tr>`;
        }
        tbody.html(html);

        /* 翻页按钮部分 */
        page = obj.page;
        now = page.pageNow;
        totalPage = page.totalPage;
        let page_list = $(".page_group_number_list");
        html = "";
        if (totalPage > 5) {
            if (now > totalPage - 5) {
                for (let i = totalPage - 5; i < totalPage; i++) {
                    if (now - 1 == i) {
                        html += `<div class="btn page_btn disabled">${i+1}</div>`;
                    } else {
                        html += `<div class="btn page_btn">${i+1}</div>`;
                    }
                }
            } else {
                for (let i = now - 1; i < now + 4; i++) {
                    if (now - 1 == i) {
                        html += `<div class="btn page_btn disabled">${i+1}</div>`;
                    } else {
                        html += `<div class="btn page_btn">${i+1}</div>`;
                    }
                }
            }
        } else {
            for (let i = 0; i < totalPage; i++) {
                if (now - 1 == i) {
                    html += `<div class="btn page_btn disabled">${i+1}</div>`;
                } else {
                    html += `<div class="btn page_btn">${i+1}</div>`;
                }
            }
        }
        $(".totalPage").html(totalPage);
        $(".totalRecord").html(page.totalCount);
        page_list.html(html);

        if (now == 1) {
            $(".prev").addClass("disabled");
        } else {
            $(".prev").removeClass("disabled");
        }
        if (now == totalPage) {
            $(".next").addClass("disabled");
        } else {
            $(".next").removeClass("disabled");
        }
    });
}

function sendEmail(email, questionnaireId) {
    $("#send").attr("disabled", "true");
    $.post("../report/sendEmail", {
        questionnaireId: questionnaireId,
        email: email
    }, function (status) {
        console.log(status);
        if (status == "true") {
            alert("发送成功!");
        } else {
            alert("发送出现错误啦,请检查邮箱!");
        }
        $("#send").removeAttr("disabled");
    });
}

$(function () {
    $("input[name*='datetime']").datepicker({
        prevText: "上个月",
        nextText: "下个月",
        changeYear: true,
        changeMonth: true,
        monthNamesShort: ["1 月", "2 月", "3 月", "4 月", "5 月", "6 月", "7 月", "8 月", "9 月", "10月", "11月", "12月"],
        dayNamesMin: ["日", "一", "二", "三", "四", "五", "六"],
    });


    $(".page_group_number_list").on("click", ".btn", function (e) {
        refreash($(this).html());
    });

    $(".prev").on("click", function () {
        refreash(now - 1);
    });
    $(".next").on("click", function () {
        refreash(now + 1);
    });

    $(".go_btn:eq(0)").on("click", function () {
        let goPage = $(".input_number").val();
        console.log(goPage);
        if (goPage > 0 && goPage <= totalPage && goPage != now) refreash(goPage);
        else alert("请输入有意义的数字");
        $(".input_number").val("");
    })
})