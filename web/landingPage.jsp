<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Management System - EMS</title>
        <jsp:include page="common/header.jsp"/>
    </head>

    <style>

        .center-content {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            height: 100vh;
            margin-left: 10%;
        }
    </style>

    <body style="background: url('https://i.imgur.com/a6HaZ26.png') no-repeat center center fixed; background-size: cover; overflow-y: hidden;">
        <%@include file="common/navbar.jsp" %>

        <div class="center-content">
            <h2 class="text-center">EMS - Exam Management System</h2>
            <h4 class="text-center">Free quiz for education <br></h4>
            <p><br>EMS is a free website serving teachers and students for educational <br>
                support purposes. This website will provide tools to create tests and <br>
                practice exercises with the highest quality and fastest, in addition to <br> 
                tools to capture scores for each test...</p>
            <h5 class="text-center">   <a href="./login" class="btn btn-success" style="width: 6rem;">Try quiz</a></h5>
            <h1 class="text-center"></h1>

        </div>

        <jsp:include page="common/footer.jsp"/>
    </body>
</html>
