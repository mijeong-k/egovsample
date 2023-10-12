<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>입력화면</title>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script
</head>

<style>
body {
	font-size:12px;
	font-family:돋음;
	color:000000;
}
table {
	width:600px;
	border:1px solid #cccccc;
	border-collapse:collapse; /*셀과 셀사이의 간격을 0*/
}
th,td {
	border:1px solid #cccccc;
	padding : 5px;
}
.button_area {
	width:600px;
	text-align:center;
	margin-top:10px;
}
.caption1 {
	font-size:24px;
	font-weigth:bold;
	color:skyblue;
	padding:5px;
}
input,textarea,button {
	font-size:12px;
}
.input1 {
	width:98%;
}
.textarea1 {
	width:98%;
	height:50px;
}
.button1 {
	padding:10px;
	background-color:#99ff00;
	border:1px solid green;
}
</style>

<script>
function fn_save() {
	
	// 화면에서 ID값(#)이 title로 되어있는 오브젝트
	if( $("#title").val() == "") {
		alert("제목을 입력해주세요.");
		$("#title").focus();
		return false;
	}
	if( $("#pass").val() == "") {
		alert("암호를 입력해주세요.");
		$("#pass").focus();
		return false;
	}	
	if( $("#name").val() == "") {
		$("#name").val("익명");
	}
	if( $("#content").val() == "") {
		alert("내용을 입력해주세요.");
		$("#content").focus();
		return false;
	}
	
	// 동기방식
	// document.frm.submit();
	
	// 비동기방식(ajax)
	// serialize() :: form 일때만 사용. form 안의 값(요소)을 인지하게 해주는 함수
	var form = $("#frm").serialize();
	
	$.ajax({
		
		// 전송세팅
		type : "POST",
		// 전송 data 
		data : form,
		url : "/myproject1/boardWriteSave.do",
		
		// 반환세팅
		datatype : "text",
		// success의 data는 msg 임. 따라서 function(msg)로 해도 됨
		success : function(data){
			//전송성공 시
			if(data == "ok"){
				alert("저장완료");
				location = "/myproject1/boardList.do";
			}else{
				alert("저장실패");
			}
		},
		error : function(){
			alert("전송실패");
		}	
	});
}

function fn_action() {
	location = "/myproject1/boardList.do";
}


</script>

<body>

<!-- ajax 쓰면서 삭제 <form name="frm" method="post" action="/myproject1/boardWriteSave.do"> -->
<form name="frm" id="frm">
<div>
<table>
	<caption class="caption1">게시판 입력화면</caption>
	<tr>
		<th>제목</th>
		<td><input type="text" name="title" id="title" class="input1" 
				   placeholder="제목을 입력해주세요." autofocus></td>
	</tr>
	<tr>
		<th>암호</th>
		<td><input type="password" name="pass" id="pass" class="input1"
		           placeholder="암호를 입력해주세요."></td>
	</tr>
	<tr>
		<th>글쓴이</th>
		<td><input type="text" name="name" id="name" class="input1"></td>
	</tr>
		<!--  
		<tr>
			<th>등록일</th>
			<td><input type="date" name="rdate"></td>
		</tr>
		<tr>
			<th>좋아하는 색</th>
			<td><input type="color" name="color"></td>
		</tr>
		-->
	<tr>
		<th>내용</th>
		<td><textarea name="content" id="content" class="textarea1"></textarea></td>
	</tr>
</table>
</div>
<div class="button_area">
	<button type="submit" onclick="fn_save();return false;" class="button1">저장</button>
	<button type="reset" class="button1">취소</button>
	<button type="button" onclick="fn_action()">목록</button>
</div>
</form>
</body>
</html>



