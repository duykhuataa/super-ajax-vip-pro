<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.*" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Class name</title>
        <jsp:include page="common/header.jsp" />
    </head>

    <body style="overflow-x: hidden; background-color: #f0f0f0;">
        <style>
            .right-aligned-modal {
                position: fixed;
                margin: 0;
                right: 0;
                width: 30%;
                max-width: none;
                z-index: 1050;
                transform: translateX(100%);
            }
        </style>
        <%@include file="common/navbar.jsp" %>

        <jsp:include page="common/sidebar.jsp" />

        <div class="p-3" style="margin-left: 250px;">
            <c:if test="${sessionScope.user.getRole().getRoleId() eq Role.ROLE_TEACHER}"> 
                <div class="d-flex flex-row">
                    <button class="btn ms-3" style="background-color: #ad95ff; color:#ffffff; " data-bs-toggle="modal" data-bs-target="#createExamModal">
                        Create exam
                    </button>
                    <button class="btn ms-3" style="background-color: #ad95ff" data-bs-toggle="modal" data-bs-target="#viewListStudent">
                        <i class="fa-solid fa-bars" style="color: #ffffff;"></i>
                    </button>
                </div>
            </c:if> 

            <!-- Table -->
            <div class="d-flex flex-row">
                <div class="w-100">
                    <table class="table table-hover align-middle">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Exam name</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Duration</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ex" items="${examList}" varStatus="count">
                                <tr style="cursor: pointer;" onclick="goToExamDetail('${ex.getClassId()}','${ex.getExamId()}')" id="classRowId">
                                    <th scope="courseItem" class="align-middle">${count.count}</th>
                                    <td>${ex.getExamName()}</td>
                                    <td>${ex.getDateStartString()}</td>
                                    <td>${ex.getDateEndString()}</td>
                                    <td>${ex.getDurationString()}</td>
                                    <td>${ex.getStatusString()}</td>
                                    <td><li class="page-item <c:if test="${ex.status ne 0}">disabled</c:if> ">
                                <a class="page-link btn btn-primary" href="./examAttemp?examId=${ex.examId}">Join</a>
                            </li></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>                  
                </div>
            </div>

            <!-- Modal create exam-->
            <div class="modal fade" id="createExamModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5">Create exam</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="exam" method="post" id="createExamForm">
                                <input type="hidden" name="action" value="createExam">
                                <input type="hidden" name="classId" value="${param.classId}">
                                <div class="form-floating mb-2">
                                    <input type="text" name="examName" class="form-control"
                                           id="f-exam-name" required>
                                    <label for="f-exam-name">Exam Name</label>
                                </div>
                                <div class="input-group mb-2">
                                    <div class="form-floating">
                                        <input type="datetime-local" name="dateStart" class="form-control"
                                               id="f-date-start" required>
                                        <label for="f-date-start">Date start</label>
                                    </div>
                                    <div class="form-floating">
                                        <input type="datetime-local" name="dateEnd" class="form-control"
                                               id="f-date-end" required>
                                        <label for="f-date-end">Date end</label>
                                    </div>
                                </div>
                                <span class="form-text">Duration</span>
                                <div class="d-flex flex-row mb-2">
                                    <div class="input-group">
                                        <input type="number" class="form-control pe-1" name="durationHours">
                                        <span class="input-group-text" id="basic-addon2">Hours</span>
                                    </div>
                                    <div class="input-group">
                                        <input type="number" class="form-control" name="durationMinutes">
                                        <span class="input-group-text" id="basic-addon2">Minutes</span>
                                    </div>
                                </div>
                                <div class="form-floating">
                                    <input type="number" name="maxAttempts" class="form-control"
                                           id="f-max-attempts" required>
                                    <label for="f-max-attempts">Max Attempts</label>
                                </div>
                                <div class="d-flex flex-row">
                                    <div class="form-floating">
                                        <span class="form-text">Display answers after submitted</span>
                                        <select name="isDisplayAnswers" class="selectpicker border rounded">
                                            <option value="1">Yes</option>
                                            <option value="0" selected>No</option>
                                        </select>
                                    </div>
                                    <div class="form-floating">
                                        <span class="form-text">Exam visibility</span>
                                        <select name="isVisible" class="selectpicker border rounded">
                                            <option value="1">Public</option>
                                            <option value="0" selected>Private</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="btn-submit-createExam" onclick="document.getElementById('createExamForm').submit()">Create</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--Model view list student in class-->
            <div class="modal fade" id="viewListStudent" tabindex="-1">
                <div class="modal-dialog right-aligned-modal">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-3">List Student</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <c:if test="${empty requestScope.studentList}">
                                Không có học sinh trong lớp
                            </c:if>
                            <c:if test="${not empty requestScope.studentList}">
                                <c:forEach items="${requestScope.studentList}" var="c">
                                    <br>${c.getFullName()}<br>
                                </c:forEach>
                            </c:if>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"style="background-color: #ad95ff; color:#ffffff;">Close</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>    
<script>                          
         function goToExamDetail(classId,examId) {
        // Get the current URL
        var examDetail = './class?classId=' + classId+'&examId='+examId;

        // Redirect to the new URL
        window.location.href = examDetail;
    }
    </script>
    <%@include file="common/footer.jsp" %>
</body>

</html>