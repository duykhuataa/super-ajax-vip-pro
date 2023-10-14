<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String questionName= "";
            if(request.getAttribute("questionName")!=null)
                questionName = (String)request.getAttribute("questionName");
        %>
        <form action="question" method="post">
            <input type="hidden" name="action" value="viewQuestion">
            <input type="text" name="questionId"  placeholder="Enter questionId">
            <button type="submit">submit</button><br>
            <input type="text" name="examName" value="<%=questionName%>" placeholder="QuestionName" readonly>
            <c:forEach items="${requestScope.listAnswers}" var="c">
                <br>${c.getAnswerText()}<br>
            </c:forEach>
        </form>
    </body>
</html>
