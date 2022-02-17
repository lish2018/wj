$(document).ready(function(){
    $(".set").click(function set() {
        $(".cover").css("display","block");
        $(".questionnaire_popup").fadeIn("3000");
    });
    $(".cancel").click(function cancel() {
        $(".questionnaire_popup").fadeOut("3000");
        $(".cover").css("display","none");
    });
    $(".save").click(function save() {
        alert("保存成功")
        $(".questionnaire_popup").fadeOut("3000");
        $(".cover").css("display","none");
    });
    $("[name=student]").click(function student(){
        $("[name=student]").css({"backgroundColor":"#428bca","color":"white"});
        $("[name=teacher]").css({"backgroundColor":"ghostwhite","color":"black"});
    });
    $("[name=teacher]").click(function teacher(){
        $("[name=student]").css({"backgroundColor":"ghostwhite","color":"black"});
        $("[name=teacher]").css({"backgroundColor":"#428bca","color":"white"});
    });
    $(".formwork_choice .text").click(function formwork(){
        $(".formwork_choice ul").toggle();
        $(".formwork_choice img").attr("src","../img/triangle_up.png");
    });
    $(".formwork_choice *").not(".text").click(function formwork_change(){
        $(".formwork_choice ul").hide();
        $(".formwork_choice img").attr("src","../img/triangle_down.png");
    });
});