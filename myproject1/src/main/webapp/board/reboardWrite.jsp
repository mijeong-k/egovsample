
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>reboardWrite(답글입력화면)</title>
 <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
 <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
</head>

<style>
body {
   padding: 0;
   margin: 0-auto;
   font-size: 12px;
   font-family: 맑은고딕;
   color: 000;
   font-size: 12px;
}

table {
   width: 600px;
   border-collapse: collapse; /* 셀 사이 간격 0으로 */
   border: 1px solid #ccc;
}

th, td {
   border: 1px solid #ccc;
   padding: 5px;
}

.button_area {
   width: 600px;
   text-align: center;
   margin-top: 10px;
}

.caption1 {
   font-size: 20px;
   font-weigth: bold;
   color: brown;
   padding: 5px;
}

input, textarea, button {
   font-size: 12px;
}

.input1 {
   width: 98%;
}

.textarea1 {
   width: 98%;
   height: 50px;
}
</style>

<script>
   //제이쿼리 형식
   function fn_save() {
      //제목과 암호 앞, 뒤 공백(띄어쓰기) 제거
      let title = $.trim($("#title").val());
      let pass = $.trim($("#pass").val());
      
      $("#title").val(title);
      $("#pass").val(pass);
      
      if($("#title").val() == "" ){ //화면에서 ID값이 title로 되어있는 오브젝트
         alert("제목을 입력해주세요.");
         $("#title").focus();
         return false;
      }
      if($("#pass").val() == "" ){
         alert("암호을 입력해주세요.");
         $("#pass").focus();
         return false;
      }
      
      //serialize() : 폼(폼일때) 안의 값(요소)을 인지하게 해주는 함수
      var form = $("#frm").serialize();
      // 여기 폼은 아래 데이터타입 : 폼 임   
      $.ajax({ //비동기 전송 방식
         //전송할 때
         type : "POST",
         data : form, //폼을 포스트 방식으로 전송한다
         url  : "/myProject1/reBoardWriteSave.do",
         
         //리턴할 때(결과값을 볼 때)
         //리턴 데이터타입
         datatype : "text",
         //여기서 말하는 msg의 datatype
         success : function(msg){
            if(msg == "ok"){
               alert("저장완료");
               location="/myProject1/reboardList.do"
            }else{
               alert("저장실패");
            }
         },
         error : function(){
            alret("전송 실패");
         }
      });
   }
   
   function fn_action(){
      location = "/myProject1/reboardList.do";
   }
</script>

<body>
   <form name="frm" id="frm">
   <!-- js 방식 <form name="frm" method="post" action="/myProject1/boardWriteSave.do">  -->
      <div>
         <table>
            <caption class="caption1">답글게시판 입력화면</caption>
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
            <tr>
               <th>내용</th>
               <td><textarea name="content" id="content" class="textarea1"></textarea></td>
            </tr>
         </table>
      </div>
      <div class="button_area">
         <button type="submit" onclick="fn_save(); return false;"
            class="button1">저장</button>
         <button type="reset" class="button1">취소</button>
         <button type="button" onclick="fn_action()">목록</button>
      </div>
   </form>
</body>
</html>