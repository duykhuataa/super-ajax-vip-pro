<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin</title>
    <%@include file="../common/header.jsp" %>
</head>

<body style="overflow-x: hidden; background-color: #F0F0F0;">
    <%@include file="../common/navbar.jsp" %>

    <div class="">
        <!-- Sidebar -->
        <%@include file="sidebar.jsp" %>

        <!-- Main -->
        <div class="p-3" style="margin-left: 250px;">
            
            <h3>General statistics from Accounts, Classes and Courses will be shown here for Admins</h3>

        </div>
    </div>


    <%@include file="../common/footer.jsp" %>
</body>

</html>