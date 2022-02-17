$(document).ready(function(){
	
	$(".cover").on("click", function(){
		cover_hid($(this));
	});
	
	$(".questionnaire_popup").on("click", function(event){
		event.stopPropagation();	
	});
});


function bindListen(listenBox, btn, boxname, func){
	
	let box = $(boxname);
	
	$(listenBox).on("click", btn, function(){
    	cover_show(box);
	});
	
    box.find(".cancel").click(function() {
    	cover_hid(box);
    });
    	
    box.find(".save").click(function(){

		let requireds = $(this).closest(".cover").find("[isRequired]");
		
		for(let i = 0; i < requireds.length; ++i){
			if(requireds.get(i).value == ""){
				alert("请完善信息");
				requireds.get(i).focus();
				return;
			}
		}
    	
    	cover_hid(box);
    	
		func();
    });
}


function bindSimple(btn, boxname, func){
	let box = $(boxname);
	
	$(btn).click(function(){
    	cover_show(box);
	});
	
    box.find(".cancel").click(function() {
    	cover_hid(box);
    });
    	
    box.find(".save").click(function(){
    	cover_hid(box);
		func();
    });
}

function bind(listenBox, btn, listenBox2, btn2, boxname, funcprev, funcprev2, func, func2){
	let box = $(boxname);
	
	$(listenBox).on("click", btn, function(){
		funcprev();
		box.find(".save").data("status", 0);
    	cover_show(box);
	});
	
	$(listenBox2).on("click", btn2, function(){
		funcprev2(this);
		box.find(".save").data("status", 1);
		cover_show(box);
	});
    
    box.find(".cancel").click(function() {
    	cover_hid(box);
    });
    
    box.find(".save").click(function(){
    	
		let requireds = $(this).closest(".qc_panel").find("[required]");
		
		for(let i = 0; i < requireds.length; ++i){
			if(requireds.get(i).value == ""){
				alert("请完善题目信息");
				requireds.get(i).focus();
				return;
			}
		}
    	
    	cover_hid(box);
    	
		if(box.find(".save").data("status") == 0){
			func(this);
		}else{
			func2(this);
		}
    });
}


function cover_show(cover){
   	cover.stop();
    cover.css("display","block");
    cover.animate({
    	"opacity":"1.0"
    });
    cover.find(".questionnaire_popup").animate({
    	"display":"block",
    	"margin-top":"40px"
    }, "fast", "linear", function(){
    	
    });
}

function cover_hid(cover){
   	cover.stop();
    cover.find(".questionnaire_popup").animate({
    	"display":"none",
    	"margin-top":"0px"
    }, "fast", "linear", function(){
    });
    	
    cover.animate({
    	"opacity":"0.0"
    }, "fast", "linear", function(){
    	cover.css("display","none");
    });
}
