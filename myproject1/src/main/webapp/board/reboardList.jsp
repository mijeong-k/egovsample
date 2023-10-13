<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>reboardList(답글목록화면)</title>
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
   text-align: right;
   margin-top: 10px;
}

.pagging_area {
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
   function fn_action() {
      location = "/myproject1/reboardWrite.do";
   }
</script>

<body>
   <div>
      <table>
         <caption class="caption1">답글 게시판 목록화면</caption>
         <tr>
            <th>번호</th>
            <th>제목</th>
            <th>등록일</th>
            <th>글쓴이</th>
            <th>조회수</th>
         </tr>
         <c:set var="count" value="${pageno }" />
		 <c:set var="len" value="${fn:length(list.thread) }"/>
         <c:forEach var="list" items="${ resultList }">
            <tr>
               <td></td>
               <!-- ? 뒤의 내용은 파라미터임   웹주소?파라미터값
                                         웹주소와 파라미터 사이엔 ?만 올 수 있음  값을 전송하기 위함임   웹주소?변수명=변수값-->
               <td>
               <c:forEach var="i" begin="2" end="${fn:length(list.thread)}">
               		&nbsp;&nbsp;
               </c:forEach>
               <c:if test="${fn:length(list.thread)>1 }">[re]</c:if>
               <a href="/myproject1/reboardDetail.do?unq=${list.unq }">${list.title }</a>
               </td>            
               
               <td>${fn:substring(list.rdate,0,10) }</td>
               <td>${list.name }</td>
               <td>${list.hits }</td>
            </tr>

            <c:set var="count" value="${count-1}" />
         </c:forEach>
      </table>
   </div>
   <!-- 
   <%
   // int lastpage = (int) pageContext.getAttribute("lastpage");%>
   lastpage : ${lastpage }  -->
   <div class="pagging_area">
      <!-- var = 내부에서 쓸 변수 이름  -->
      <c:forEach var="i" begin="1" end="${lastpage }">
      <a href="/myproject1/reboardList.do?page=${i }">${i }</a>
      </c:forEach>
   </div>
   <div class="button_area">
      <button type="button" onclick="fn_action()">글쓰기</button>
   </div>
</body>
</html>