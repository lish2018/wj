var parent = document.getElementById("testItem");
	//添加问卷
	function addTest() {
		var parentNode = document.getElementById("testItem");
		var box = document.createElement("div");
		var top_box = document.createElement("div");
		var top_LeftBox = document.createElement("div");
		var top_RightBox = document.createElement("div");
		var btm_box = document.createElement("div");
		var btm_LeftBox = document.createElement("div");
		var btm_RightBox = document.createElement("div");
		top_box.appendChild(top_LeftBox);
		top_box.appendChild(top_RightBox);
		box.appendChild(top_box);

		btm_box.appendChild(btm_LeftBox);
		btm_box.appendChild(btm_RightBox);
		box.appendChild(btm_box);

		parentNode.insertBefore(box, parentNode.firstChild);
		addNumTop(top_LeftBox);
		addDate(top_LeftBox);
		addTopBtn(box, top_RightBox);
		addNumBtm(btm_LeftBox);
		addBtmBtn(btm_RightBox);

		box.setAttribute("class", "_box");
		top_box.setAttribute("class", "top_box");
		btm_box.setAttribute("class", "btm_box");
		top_LeftBox.setAttribute("class", "top_LeftBox");
		top_RightBox.setAttribute("class", "top_RightBox");
		btm_LeftBox.setAttribute("class", "btm_LeftBox");
		btm_RightBox.setAttribute("class", "btm_RightBox");
	}
	//添加编号
	function addNumTop(top_LeftBox) {
		top_LeftBox.innerHTML = "<span>编号:" + 111 + "</span>&nbsp;&nbsp;&nbsp;";
	}
	//添加日期
	function addDate(top_LeftBox) {
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth();
		var day = date.getDate();
		var hour = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();
		if (seconds < 10) {
			seconds = "0" + seconds;
		}
		if (minutes < 10) {
			minutes = "0" + minutes;
		}
		if (hour < 10) {
			hour = "0" + hour;
		}
		top_LeftBox.innerHTML += "<span>时间:" + year + "年" + month + "月" + day + "日" + hour + ":" + minutes + ":" +
			seconds + "</span>";
	}
	var s = "";
	//添加top按钮
	var i = 0;

	function addTopBtn(box, top_RightBox) {
		s = top_RightBox;
		v = box;
		top_RightBox.innerHTML += "<button onclick=\"\">发布</button>" + "<button>编辑</button>" + "<button>删除</button>";
		var remove = top_RightBox.childNodes;
		remove[2].setAttribute("onclick", "removeNode(" + i + ")");
		v.setAttribute("id", "r" + i);
		i++;
	}
	//添加问卷信息
	function addNumBtm(btm_LeftBox) {
		btm_LeftBox.innerHTML = "<span>名称:" + 111 + "</span>&nbsp;&nbsp;" + "<span>状态:" + 111 + "</span>&nbsp;&nbsp;" +
			"<span>已回收:" + 111 + "</span>&nbsp;&nbsp;" + "<span>问卷类型:" + 111 + "</span>";
	}
	//添加bottom按钮
	function addBtmBtn(btm_RightBox) {
		btm_RightBox.innerHTML += "<button>查看</button>" + "<button>删除</button>" + "<button>删除</button>";
	}
	//删除问卷
	function removeNode(count) {
		var countNode = parent.childNodes;
		var value = "r" + count;
		if (countNode.length == 2) {
			parent.removeChild(countNode[0]);
		}
		for (let n = 0; n < countNode.length; n++) {
			var id = countNode[n].id;
			if (id == value) {
				parent.removeChild(countNode[n]);
			}
		}

	}