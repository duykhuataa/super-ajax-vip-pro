<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create question</title>
        <jsp:include page="common/header.jsp" />
        <title>Exam Detail</title>
        <style>             
    
    .question-table1 {
        max-width: 700px;
        max-height: 200px;
        position: relative; 
        border: 2px solid #000000; /* Đường viền đen */
        border-collapse: collapse; /* Để làm cho đường viền đứt đoạn trong trường hợp có nhiều bảng */
        overflow: auto;
    }

         .container{
        max-width: 1000px;
        max-height: 300px;
        position: relative; 
        background: rgb(247, 247, 247);
        border: 2px solid #000000; /* Đường viền đen 2px xung quanh container */

    }
        </style> 
    </head>

    <body style="overflow-x: hidden; background-color: #f0f0f0;">
        <%@include file="common/navbar.jsp" %>

        <jsp:include page="common/sidebar.jsp" />

        <div class="p-3" style="margin-left: 250px;">
                <div id="accordionPanelsStayOpenExample" class="accordion">
            <c:if test="${sessionScope.user.getRole().getRoleId() eq 1}">
                <div class="container">
                            <div class="text-center">
                                <h1>Exam Information</h1>
                            </div>
                    <form action="exam" method="post" >
                         <input type="hidden" name="action" value="exam">
                        <input type="hidden" name="examId" value="${exam.getExamId()}">
                        <input type="hidden" name="classId" value="${exam.getClassId()}">
                        <button type="submit">submit</button>
                        <div class="row">
                            <!-- Column 1 -->
                            <div class="col-md-4">

                                <div class="form-group">
                                    <label for="examName">Exam Name:</label>
                                    <input type="text" class="form-control" id="examName" value="${exam.getExamName()}" name="examName">
                                </div>
                                <div class="form-group">
                                    <label for="examStatus">Exam Status:</label>
                                    <input type="text" class="form-control" id="examStatus" value="${exam.getStatus()}" name="status">
                                </div>
                                <div class="form-group">
                                    <label for="maxAttempts">Exam Max Attempts:</label>
                                    <input type="text" class="form-control" id="maxAttempts" value="${exam.getMaxAttempts()}"name="maxAttempts">
                                </div>
                            </div>
                            <!-- Column 2 -->
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="isDeleted">Exam Is Deleted:</label>
                                    <input type="text" class="form-control" id="isDeleted" value="${exam.getIsDeletedString()}"name="deleted">
                                </div>
                                <div class="form-group">
                                    <label for="isVisible">Exam Is Visible:</label>
                                    <input type="text" class="form-control" id="isVisible" value="${exam.getIsVisibleString()}"name="isVisible">
                                </div>
                                <div class="form-group">
                                    <label for="isDisplay">Exam Is Display:</label>
                                    <input type="text" class="form-control" id="isDisplay" value="${exam.getIsDisplayString()}"name="isDisplay">
                                </div>
                            </div>
                            <!-- Column 3 -->
                            <div class="col-md-4">

                                <div class="form-group">
                                    <label for="dateStart">Date Start:</label>
                                    <input type="text" class="form-control" id="dateStart" value="${exam.getDateStart()}"name="dateStart">
                                </div>
                                <div class="form-group">
                                    <label for="dateEnd">Date End:</label>
                                    <input type="text" class="form-control" id="dateEnd" value="${exam.getDateEnd()}"name="dateEnd">
                                </div>
                                <div class="form-group">
                                    <label for="duration">Duration:</label>
                                    <input type="text" class="form-control" id="duration" value="${exam.getDuration()}"name="duration">
                                </div>

                            </div>
                        </div> </form></div>
                    </c:if>                              
                    <c:if test="${sessionScope.user.getRole().getRoleId() eq 0}">
                        <div class="container">
                            <div class="text-center">
                                <h1>Exam Information</h1>
                            </div>
                            <div class="row">
                                <!-- Column 1 -->
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label for="examName">Exam Name:</label>
                                        <input type="text" class="form-control" id="examName" value="${exam.getExamName()}" name="examName" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="examStatus">Exam Status:</label>
                                        <input type="text" class="form-control" id="examStatus" value="${exam.getStatus()}" name="status" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="maxAttempts">Exam Max Attempts:</label>
                                        <input type="text" class="form-control" id="maxAttempts" value="${exam.getMaxAttempts()}"name="maxAttempts" readonly>
                                    </div>                                </div>
                                <!-- Column 2 -->
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label for="duration">Duration:</label>
                                        <input type="text" class="form-control" id="duration" value="${exam.getDuration()}" name="duration" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="dateCreated">Date Created:</label>
                                        <input type="text" class="form-control" id="dateCreated" value="${exam.getDateCreated()}" name="dateCreated" readonly>
                                    </div>                                </div>
                                <!-- Column 3 -->
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label for="dateStart">Date Start:</label>
                                        <input type="text" class="form-control" id="dateStart" value="${exam.getDateStart()}" name="dateStart" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="dateEnd">Date End:</label>
                                        <input type="text" class="form-control" id="dateEnd" value="${exam.getDateEnd()}" name="dateEnd" readonly>
                                    </div>                                </div>
                            </div>
                        </div>
                        
                </c:if>
<br><br>
            </div>
<!-- Đoạn mã HTML/JSP hiện thị danh sách câu hỏi và điểm số -->
<div class="question-table1" >
<table class="question-table table table-primary " >
    <thead>
        <tr>
            <th>Question</th>
            <th>Score</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="q" items="${listQuestions}" varStatus="index">
            <c:if test="${sessionScope.user.getRole().getRoleId() eq 1}">
            <tr style="cursor: pointer;"class="" onclick="toggleOffcanvas('${q.questionText}')" id="userRowId">
                </c:if>
                <td class="">${q.questionText}</td>
                <td class="">${score}</td>
            </tr>
        </c:forEach>
    </tbody>
</table></div><button onclick="goToEditExam('${examId}')" type="button" class="btn btn-primary">Edit Exam</button>

        <!-- Offcanvas -->
            <div class="offcanvas offcanvas-end" tabindex="-1" id="classDetailsOffcanvas">
                <div class="offcanvas-header border-bottom">
                    <h2 class="class-name-header"></h2>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas"
                            aria-label="Close"></button>
                </div>

                <div class="offcanvas-body">
                    <div class="form-floating">
                        <input type="text" class="form-control class-name" id="input-class-name"
                               placeholder="class-name">
                        <label for="input-class-name">Class name</label>
                    </div>

                    <div class="d-flex">
                        <button class="btn btn-danger align-self-start" style="width: 7rem;">Delete</button>
                        <button class="btn btn-outline-success ms-auto" style="width: 7rem;">Save</button>
                    </div>
                </div>        
            </div> 

        <script>                           
         function goToEditExam(examId) {
        // Get the current URL
        var examDetail = './exam?examId='+examId+"&action=edit";

        // Redirect to the new URL
        window.location.href = examDetail;
    }
    </script>
        </div>
        <script>
            const offcanvasElement = document.getElementById('classDetailsOffcanvas');

            function toggleOffcanvas(Question) {

                offcanvasElement.querySelector('.class-name-header').innerHTML = Question;

                offcanvasElement.querySelector('.class-name').value = Question;

                new bootstrap.Offcanvas(offcanvasElement).toggle();
            }
        </script>
        <jsp:include page="common/footer.jsp" />

    </body>

</html>
