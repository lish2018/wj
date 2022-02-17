
var curid = 0;

var wj = {
	title:"",
	count:0,
	type:1,
	subject:[
		
	]
};

var current_edit_subject_qid = -1;

function appendRadio(subject){

	subject.qid=curid;
	console.log(subject);
	var html = `<div class="questionItem" id="q${curid}" data-qid="${curid}">
		<div class="questionItem_name">
			<div style="float:left;overflow:auto;width:100%;">
				<span class="questionItem_name_index">${wj.count+1}</span>.
				<span class="sub_title">
					${subject.title}
				</span>
				<span class="questionItem_name_ismustSelect" style="color: red;">${(subject.isRequired == 1)?"*":""}</span>
			</div>
	
			<div style="float: right;">
				<span class="btn btn-default radioEdit change" style="padding: 2px 6px;">编辑</span>
				<span class="btn btn-default delete" style="padding: 2px 6px;">删除</span>
				<span class="btn btn-default upper" style="padding: 2px 6px;">上移</span>
				<span class="btn btn-default downn" style="padding: 2px 6px;">下移</span>
			
				
			</div>
			<div style="clear: both;"></div>
			<hr />
			<div class="questionItem_option">`;
	for(let i=0;i<subject.text.length;i++){
		html+="<label style='cursor:pointer;display:block;width:90%;'><input type='radio' " +
			"style='margin-left:8px;margin-right:5px;' name='t"
			+wj.qid+"'>"+subject.text[i]+"<span width='10px'></span></label>";
		$("label").click(function(){
			$("input:eq("+ i +")").attr("checked","checked");
			$("input:eq("+ (i+1) +")").attr("checked",false);
		});
	}
	
	html+=`</div>
		</div>
	</div>`;
	wj.count++;
	wj.subject.push(subject);
	$(".testlist").append($(html));
	
	curid++;
}

function appendCheckbox(subject){

	subject.qid=curid;
	console.log(subject);
	var html = `<div class="questionItem" id="q${curid}" data-qid="${curid}">
		<div class="questionItem_name">
			<div style="float: left;overflow:auto;width:100%;">
				<span class="questionItem_name_index">${wj.count+1}</span>.
				<span class="sub_title">
					${subject.title}
				</span>
				<span class="questionItem_name_ismustSelect" style="color: red;">${(subject.isRequired == 1)?"*":""}</span>
			</div>
	
			<div style="float: right;">
				<span class="btn btn-default checkBoxEdit  change" style="padding: 2px 6px;">编辑</span>
				<span class="btn btn-default delete" style="padding: 2px 6px;">删除</span>
				<span class="btn btn-default upper" style="padding: 2px 6px;">上移</span>
				<span class="btn btn-default downn" style="padding: 2px 6px;">下移</span>
				
			</div>
			<div style="clear: both;"></div>
			<hr />
			<div class="questionItem_option">`;
				
	for(let i=0;i<subject.text.length;i++){
		html+="<label style='cursor:pointer;display:block;width:90%;'><input type='checkbox' " +
			"style='margin-left:8px;margin-right:5px;' name='t"
			+subject.qid+"'>"+subject.text[i]+"<span width='10px'></span></label>"
		$("label").click(function(){
			$("input:eq("+ i +")").attr("checked","checked");
			$("input:eq("+ (i+1) +")").attr("checked",false);
		});
	}
	
	html+=`</div>
		</div>
	</div>`;
	wj.count++;
	wj.subject.push(subject);
	$(".testlist").append($(html));
	
	curid++;
}

function appendFill(subject){

	subject.qid=curid;
	console.log(subject);
	var html = `<div class="questionItem" id="q${curid}" data-qid="${curid}">
		<div class="questionItem_name">
			<div style="float:left;overflow:auto;width:100%;">
				<span class="questionItem_name_index">${wj.count+1}</span>.
				<span class="sub_title">
					${subject.title}
				</span>
				<span class="questionItem_name_ismustSelect" style="color: red;">${(subject.isRequired == 1)?"*":""}</span>
			</div>
	
			<div style="float: right;">
				<span class="btn btn-default textAreaEdit change" style="padding: 2px 6px;">编辑</span>
				<span class="btn btn-default delete" style="padding: 2px 6px;">删除</span>
				<span class="btn btn-default upper" style="padding: 2px 6px;">上移</span>
				<span class="btn btn-default downn" style="padding: 2px 6px;">下移</span>
				
			</div>
			<div style="clear: both;"></div>
			<hr />
			<div class="questionItem_option">`;
	let	text = subject.text;
	if(text=="one"){
		html+="<input type='text'name='t"+subject.qid+"style='width=200px;height=40px'>";
	}else{
		html+="<textarea name='t"+subject.qid+"' style='display:block;width:60%;height:182px;'></textarea>";
	}		
	html+=`</div>
		</div>
	</div>`;
	wj.count++;
	wj.subject.push(subject);
	$(".testlist").append($(html));
	
	curid++;
}

//单选框
bind(".questionTypeSelect", ".radio", ".testlist", ".radioEdit", ".cover_radio", function(){
	
	loadObjT({title:"", score:[10, 7, 5, 3, 1], text:["很满意", "比较满意", "一般", "比较不满意", "很不满意"], type:0, isRequired:1});
	
	
}, function(e){
	
	let qitem = $(e).closest(".questionItem");
	let s=parseInt(qitem.data("qid"));
	let xh = parseInt(qitem.find(".questionItem_name_index").text());
	
	current_edit_subject_qid = s;
	
	console.log(wj.subject[xh-1]);
	
	loadObjT(wj.subject[xh-1]);
	
}, function(e) {
	
	$(".empty_content").remove();
	
	var subject = addObjT();
	
	appendRadio(subject);
	
}, function(){
	
	let subject = addObjT();
	subject.qid = current_edit_subject_qid;
	current_edit_subject_qid = -1;
	
	let index = -1;
	
	for(let i = 0 ; i < wj.subject.length; ++i){
		if(subject.qid == wj.subject[i].qid){
			wj.subject[i] = subject;
			index = i;
			break;
		}
	}
	
	let qitem = $(".questionItem:eq("+index+")");
	
	qitem.find(".questionItem_name_ismustSelect").text((subject.isRequired==1)?"*":"");
	
	let html = "";
	for(let i=0;i<subject.text.length;i++){
		html+="<label style='cursor:pointer;display:block;width:90%;'><input type='radio' style='margin-left:10px;' name='t"
			+subject.qid+"'>"+subject.text[i]+"<span width='10px'></span></label>";
		$("label").click(function(){
			$("input:eq("+ i +")").attr("checked","checked");
			$("input:eq("+ (i+1) +")").attr("checked",false);
		});
	}
	qitem.find(".questionItem_option").html(html);
	qitem.find(".sub_title").text(subject.title);
});

//多选框
bind(".questionTypeSelect", ".checkBox", ".testlist", ".checkBoxEdit", ".cover_checkbox", function(){
	
	loadObjC({title:"", count:[10, 5, 2, 0], text:["", "", "", ""], type:1, isRequired:1});
	
	
}, function(e){
	
	let qitem = $(e).closest(".questionItem");
	let s=parseInt(qitem.data("qid"));
	let xh = parseInt(qitem.find(".questionItem_name_index").text());
	
	current_edit_subject_qid = s;
	
	loadObjC(wj.subject[xh-1]);
	
}, function (e) {
	
	$(".empty_content").remove();
	
	var subject = addObjC();
	
	appendCheckbox(subject);
	
}, function(){
	
	let subject = addObjC();
	subject.qid = current_edit_subject_qid;
	current_edit_subject_qid = -1;
	
	let index = -1;
	
	for(let i = 0 ; i < wj.subject.length; ++i){
		if(subject.qid == wj.subject[i].qid){
			wj.subject[i] = subject;
			index = i;
			break;
		}
	}
	
	let qitem = $(".questionItem:eq("+index+")");
	
	qitem.find(".questionItem_name_ismustSelect").text((subject.isRequired==1)?"*":"");
	
	let html = "";
	for(let i=0;i<subject.text.length;i++){
		html+="<label style='cursor:pointer;display:block;width:90%;'><input type='checkbox' " +
			"style='margin-left:8px;margin-right:5px;' name='t"
			+subject.qid+"'>"+subject.text[i]+"<span width='10px'></span></label>"
		$("label").click(function(){
			$("input:eq("+ i +")").attr("checked","checked");
			$("input:eq("+ (i+1) +")").attr("checked",false);
		});
	}
	
	qitem.find(".questionItem_option").html(html);
	qitem.find(".sub_title").text(subject.title);
});

//填空框
bind(".questionTypeSelect", ".textArea", ".testlist", ".textAreaEdit", ".cover_textarea", function(){
	
	loadObjA({title:"", text:"", type:0, isRequired:1});
	
	
}, function(e){
	
	let qitem = $(e).closest(".questionItem");
	let s=parseInt(qitem.data("qid"));
	let xh = parseInt(qitem.find(".questionItem_name_index").text());
	
	current_edit_subject_qid = s;
	
	loadObjA(wj.subject[xh-1]);
	
}, function (e) {
	$(".empty_content").remove();
	
	var subject = addObjA();
	
	appendFill(subject);
	
}, function(){
	let subject = addObjA();
	subject.qid = current_edit_subject_qid;
	current_edit_subject_qid = -1;
	
	let index = -1;
	
	for(let i = 0 ; i < wj.subject.length; ++i){
		if(subject.qid == wj.subject[i].qid){
			
			wj.subject[i] = subject;
			index = i;
			break;
		}
	}
	
	let qitem = $(".questionItem:eq("+index+")");
	
	qitem.find(".questionItem_name_ismustSelect").text((subject.isRequired==1)?"*":"");
	
	console.log(qitem);
	let html = "";
	let	text = subject.text;
	if(text=="one"){
		html+="<input type='text'name='t"+subject.qid+"' style='width=200px;height=40px'>";
	}else{
		html+="<textarea name='t"+subject.qid+"' style='width=750px;height:182px'></textarea>";
	}	
	qitem.find(".questionItem_option").html(html);
	
	qitem.find(".sub_title").text(subject.title);
});



$(".testlist").on("click",".upper", function(){
	let qitem = $(this).closest(".questionItem");
	let s=qitem.data("qid");
	let xh = qitem.find(".questionItem_name_index").text();
	if(parseInt(xh)==1){
		alert("无法上移！");
		return;
	}
	let qitem2 = qitem.prev();
	
	let qitem2_xh = qitem2.find(".questionItem_name_index").text();
	
	qitem.find(".questionItem_name_index").text(qitem2_xh);
	qitem2.find(".questionItem_name_index").text(xh);
	
	
	let clone_qitem = qitem.clone();
	let temp = $("<div></div>");
	qitem.before(temp);
	qitem.replaceWith(qitem2);
	temp.replaceWith(clone_qitem);
	
	for(let i=0;i<wj.subject.length;i++){
		if(wj.subject[i].qid==s){
			let temp = wj.subject[i];
			wj.subject[i] = wj.subject[i-1];
			wj.subject[i-1] = temp;
			console.log(wj);
			break;
		}
	}
});

$(".testlist").on("click",".downn", function(){
	let qitem = $(this).closest(".questionItem");
	let s=qitem.data("qid");
	let xh = qitem.find(".questionItem_name_index").text();
	if(parseInt(xh)==wj.subject.length){
		alert("无法下移！");
		return;
	}
	let qitem2 = qitem.next();
	
	let qitem2_xh = qitem2.find(".questionItem_name_index").text();
	
	qitem.find(".questionItem_name_index").text(qitem2_xh);
	qitem2.find(".questionItem_name_index").text(xh);
	
	
	let clone_qitem = qitem.clone();
	let temp = $("<div></div>");
	qitem.after(temp);
	qitem.replaceWith(qitem2);
	temp.replaceWith(clone_qitem);
	
	for(let i=0;i<wj.subject.length;i++){
		if(wj.subject[i].qid==s){
			let temp = wj.subject[i];
			wj.subject[i] = wj.subject[i+1];
			wj.subject[i+1] = temp;
			console.log(wj);
			break;
		}
	}
});


$("[name='tbody']").on("click", ".btn1", function(){
	var tr = $(this).closest("tr");
	var tr1 = tr.clone();
	tr1.find(".text").val("");
	tr1.find(".btn2").attr("isFirst", 0);
	tr1.find(".Cnum").val(0);
	tr.after(tr1);
});

$("[name='tbody']").on("click", ".btn2[isFirst=1]", function(){
	alert("第一个不能删");
});

$("[name='tbody']").on("click", ".btn2[isFirst=0]", function(){
	var tr = $(this).closest("tr");
	tr.remove();
});

function loadObjT(obj){
	let container = $(".cover_radio");
	
	container.find(".Btitle").val(obj.title);
	
	container.find("[name='tbody']>tr:gt(0)").remove();
	
	currentEditQuestionId = obj.id;
	
	//isFirst="1"
	for(let i = 0; i < obj.text.length; ++i){
		
		let html = $(`<tr height="40px" id="i0">
			<td><input type="button" name="" class="btn1" value="+"/></td>
			<td><input type="button" class="btn2" value="-"/></td>
			<td colspan="8"><input type="text" name="" required  value="${obj.text[i]}"placeholder="请输入选项内容" class="text"/></td>
			<td><input type="text" name="" required class="Cnum" value="${obj.score[i]}" class="num1"/></td>
		</tr>`);
		
		if(i == 0){
			html.find(".btn2").attr("isFirst", 1);
		}else{
			html.find(".btn2").attr("isFirst", 0);
		}
		container.find("[name='tbody']").append(html);
	}
	
	if(obj.isRequired == 1){
		$("input#risRequired").attr("checked", "");
	}else{
		$("input#risRequired").removeAttr("checked");
	}
}

function loadObjC(obj){
	
	let container = $(".cover_checkbox");
	
	container.find(".Btitle").val(obj.title);
	
	container.find("[name='tbody']>tr:gt(0)").remove();
	
	currentEditQuestionId = obj.id;
	
	//isFirst="1"
	for(let i = 0; i < obj.text.length; ++i){
		
		let html = $(`<tr height="40px" id="i0">
			<td><input type="button" name="" class="btn1" value="+"/></td>
			<td><input type="button" class="btn2"  value="-"/></td>
			<td colspan="8"><input type="text"required name=""  required value="${obj.text[i]}"placeholder="请输入选项内容" class="text"/></td>
		</tr>`);
		
		if(i == 0){
			html.find(".btn2").attr("isFirst", 1);
		}else{
			html.find(".btn2").attr("isFirst", 0);
		}
		container.find("[name='tbody']").append(html);
	}
	
	if(obj.isRequired == 1){
		$("input#cisRequired").attr("checked", "");
	}else{
		$("input#cisRequired").removeAttr("checked");
	}
}

function loadObjA(obj){
	let container = $(".cover_textarea");
	
	container.find(".Ctitle").val(obj.title);
	
	currentEditQuestionId = obj.id;
	
	if(obj.text == "one"){
		$("input#dan").attr("checked", "");
	}else{
		$("input#suan").attr("checked", "");
	}
	
	if(obj.isimpt){
		$("input#isimpt").attr("checked", "");
	}
	
	if(obj.isRequired == 1){
		$("input#fisRequired").attr("checked", "");
	}else{
		$("input#fisRequired").removeAttr("checked");
	}
}

let currentEditQuestionId;

//单选款数据
function addObjT(){
	let texts = new Array();//选项
	let counts = new Array();//分数
	let trs = $(".cover_radio [name='tbody']").children("tr");
	let titleT=$(".cover_radio .header").find(".Btitle").val();
	
	
	
	for(let i=1;i<trs.length;i++){
	 	let text = trs.eq(i).children("td").eq(2).find("input").val();
		let num = trs.eq(i).children("td").eq(3).find("input").val();
	 	texts.push(text);
		counts.push(num);
	 }
	let isRequired = $("input#risRequired").get(0).checked ? 1 : 0;
	var testObj = {
		id:currentEditQuestionId,
		title:titleT,//标题
		text:texts,//选项
		score:counts,//分数
		isRequired:isRequired,
		type:0,
	};
	$(".cover_radio [name='tbody'] .btn2[isFirst=0]").closest("tr").remove();
	let tr = $(".cover_radio [name='tbody'] .btn2[isFirst=1]").closest("tr");
	tr.children().eq(2).find("input").val("");
	tr.children().eq(3).find("input").val("");
	firstbegin="true";
	return testObj;
}
//多选款数据
function addObjC(){
	let texts = new Array();//选项
	let trs = $(".cover_checkbox [name='tbody']").children("tr");
	
	let id = $(".cover_checkbox").attr("realid");
	let titleT=$(".cover_checkbox .header").find(".Btitle").val();
	for(let i=1;i<trs.length;i++){
	 	let text = trs.eq(i).children("td").eq(2).find("input").val();
	 	texts.push(text);
	 }
	let isRequired = $("input#cisRequired").get(0).checked ? 1 : 0;
	var testObj = {
		id:currentEditQuestionId,	//id名
		title:titleT,//标题
		text:texts,//选项
		isRequired:isRequired,
		type:1,
	};
	$(".cover_checkbox .header").find(".Btitle").val("");
	$(".cover_checkbox [name='tbody'] .btn2[isFirst=0]").closest("tr").remove();
	let tr = $(".cover_checkbox [name='tbody'] .btn2[isFirst=1]").closest("tr");
	tr.children().eq(2).find("input").val("");
	firstbegin1="true";
	return testObj;
}
//填空数据
function addObjA(){
	
	let titleT=$(".cover_textarea .header").find(".Ctitle").val();
	
	let check = $("input[name='sel']:checked").val();
	
	let isimpt = $("input#isimpt").get(0).checked;
	
	let isRequired = $("input#fisRequired").get(0).checked ? 1 : 0;
	
	let id = $(".cover_textarea").attr("realid");
	
	
	
	
	var testObj = {
		id:currentEditQuestionId,
		title:titleT,//标题
		text:check,//选项
		isimpt:isimpt,//选项
		isRequired:isRequired,
		type:2,
	};
	return testObj;
}
