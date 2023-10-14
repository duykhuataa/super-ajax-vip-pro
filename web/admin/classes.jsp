<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Class Management</title>
        <%@include file="../common/header.jsp" %>
    </head>

    <body style="overflow-x: hidden; background-color: #F0F0F0;">
        <%@include file="../common/navbar.jsp" %>

        <div class="">
            <!-- Sidebar -->
            <%@include file="sidebar.jsp" %>

            <!-- Main -->
            <div class="p-3" style="margin-left: 250px;">
                <!-- Functions -->
                <div class="d-flex flex-row">
                    <form action="admin" method="get" id="searchForm">
                        <div class="input-group mb-3" style="width: 35rem; position: relative;">
                            <input type="hidden" name="section" value="classes">
                            <input type="text" class="form-control" name="keyValue" placeholder="Search for users" id="searchUserString" value="${key}">
                            <button type="button" class="btn-close" aria-label="Close" style="color: #ffffff; background-color: transparent; position: absolute; right: 40px; top: 50%; transform: translateY(-50%);" id="closeButton"></button>
                            <button class="btn btn-outline-secondary" type="button" id="button-addon2">
                                <i class="fa-regular fa-magnifying-glass"></i>
                            </button>
                        </div>
                    </form>
                    <div>
                        <button class="btn btn-primary ms-3" data-bs-toggle="modal"
                                data-bs-target="#createClassModal">
                            <i class="fa-solid fa-user-plus"></i>
                            Create class
                        </button>
                    </div>
                    <div>
                        <button class="btn btn-outline-success ms-3">
                            <i class="fa-regular fa-file-export"></i>
                            Export CSV
                        </button>
                    </div>
                </div>

                <!-- Table -->
                <div class="d-flex flex-row">
                    <div class="w-100">
                        
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Class name</th>
                                            <th>Course code</th>
                                            <th>Teacher</th>
                                            <th>Number of Students</th>
                                            <th>Date created</th>
                                            <th>Is deleted</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="cl" items="${objectList}" varStatus="count">
                                            <tr style="cursor: pointer;" onclick="toggleOffcanvas('${cl.getClassName()}')" id="classRowId">
                                                <th scope="courseItem" class="align-middle">${count.count}</th>
                                                <td ${c1.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${cl.getClassName()}</td>
                                                <td ${c1.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${cl.getCourse().getCourseCode()}</td>
                                                <td ${c1.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${cl.getTeacher().getFullName()}</td>
                                                <td></td>
                                                <td>${cl.getDateCreatedString()}</td>
                                                <td>${cl.getIsDeletedString()}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                  
                    </div>
                </div>
                <div class="col-12">
                    <nav>
                        <ul class="pagination justify-content-center">  
                            <li class="page-item <c:if test="${index eq 1}">disabled</c:if> "><a class="page-link" href="${href}&index=${index-1}">Previous</span></a></li>
                                <c:forEach items="${listPage}" var="p">
                                <li class="page-item <c:if test="${listPageSize ne 1}">active</c:if> "><a class="page-link" href="${href}&index=${p}">${p}</a></li>
                                </c:forEach>
                            <li class="page-item <c:if test="${listPageSize eq index}">disabled</c:if>"><a class="page-link" href="${href}&index=${index + 1}">Next</a></li>
                            </ul>
                        </nav>
                    </div>
                    <span class="fs-1 fw-bold" id="noClassFoundId" style="display: none;">
                        <center>No class found</center>
                    </span>
                </div>

                <!-- Modals -->
                <div class="modal fade" id="createClassModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5">Create class</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="admin" method="post" id="createClassForm">
                                    <input type="hidden" name="queryString"
                                           value="${pageContext.request.queryString}" />
                                <input type="hidden" name="action" value="createClass">

                                <div class="form-floating">
                                    <input type="text" name="className" class="form-control"
                                           id="f-class-name" placeholder="class-name"
                                           oninput="checkClassName()" required>
                                    <label for="f-class-name">Class Name</label>
                                </div>
                                <div class="invalid-feedback" id="class-feedback">
                                    Class had been existed!
                                </div>

                                <div>
                                    <span class="form-text">Course:</span><br>
                                    <select class="selectpicker show-tick" name="courseId"
                                            data-live-search="true">
                                        <c:forEach var="course" items="${courseList}">
                                            <option value="${course.courseId}">${course.courseCode}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <span class="form-text">Teacher</span><br>
                                    <select class="selectpicker show-tick" name="teacherId"
                                            data-live-search="true">
                                        <c:forEach var="teacher" items="${teacherList}">
                                            <option value="${teacher.getUserId()}">${teacher.getFullName()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="btn-submit-createClass"
                                    onclick="submitCreateClass()">Create</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Update Class -->
            <div class="modal fade" id="updateClassModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5">Update class</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="admin" method="post" id="updateClassForm">
                                    <input type="hidden" name="queryString"
                                           value="${pageContext.request.queryString}" />
                                <input type="hidden" name="action" value="updateClass">

                                <div class="form-floating">
                                    <c:forEach var="cl" items="${objectList}" >
                                        <input value="${cl.className}" type="text" name="className" class="form-control"
                                           id="f-class-name" placeholder="class-name" required>
                                    <label for="f-class-name">Class name</label>
                                    </c:forEach>
                                </div>
                                <div>
                                    <span class="form-text">Course:</span><br>
                                    <select class="selectpicker show-tick" name="courseId"
                                            data-live-search="true">
                                        <c:forEach var="course" items="${courseList}">
                                            <option value="${course.courseId}">${course.courseCode}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <span class="form-text">Teacher</span><br>
                                    <select class="selectpicker show-tick" name="teacherId"
                                            data-live-search="true">
                                        <c:forEach var="teacher" items="${teacherList}">
                                            <option value="${teacher.getUserId()}">${teacher.getFullName()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="btn-submit-updateClass"
                                    onclick="submitUpdateClass()">Update</button>
                        </div>
                    </div>
                </div>
            </div>
                                
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
                        <div>
                        <button class="btn btn-outline-success ms-auto" style="width: 10rem" data-bs-toggle="modal"
                                data-bs-target="#updateClassModal">
                            Update class
                        </button>
                    </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            const offcanvasElement = document.getElementById('classDetailsOffcanvas');

            function toggleOffcanvas(className) {

                offcanvasElement.querySelector('.class-name-header').innerHTML = className;

                offcanvasElement.querySelector('.class-name').value = className;

                new bootstrap.Offcanvas(offcanvasElement).toggle();
            }
        </script>

        <script>
            var listClass = <%= request.getAttribute("classListJson") %>;
            function checkClassNameExist(codeInput) {
                for (var i = 0; i < listClass.length; i++) {
                    if (listClass[i].className === codeInput) {
                        return true;
                    }
                }
                return false;
            }

            function checkClassName() {
                var className = document.getElementById('f-class-name');
                var classfeedback = document.getElementById('class-feedback');

                if (checkClassNameExist(className.value)) {
                    classfeedback.style.display = 'block';
                    return false;
                } else {
                    classfeedback.style.display = 'none';
                    return true;
                }
            }

            function submitCreateClass() {
                if (checkClassName()) {
                    document.getElementById('createClassForm').submit();
                }
            }
            
            function submitUpdateClass(){
                document.getElementById('updateClassForm').submit();
            }
        </script>

        <script>
                // Định nghĩa một hàm để xử lý sự kiện tìm kiếm
                function handleSearch() {
                    const searchForm = document.getElementById("searchForm");
                    const searchUserString = document.getElementById("searchUserString");

                    // Lấy giá trị từ ô tìm kiếm
                    const keyValue = searchUserString.value.trim();

                    // Kiểm tra nếu có giá trị, thì gửi yêu cầu GET
                    if (keyValue !== "") {
                        // Thay đổi giá trị của thuộc tính "value" trong input hidden
                        searchForm.querySelector('input[name="keyValue"]').value = keyValue;

                        // Gửi yêu cầu GET
                        searchForm.submit();
                    }
                }

                document.addEventListener("DOMContentLoaded", function () {
                // Gọi hàm handleSearch khi sự kiện click xảy ra
                document.getElementById("button-addon2").addEventListener("click", handleSearch);
                        document.getElementById("closeButton").addEventListener("click", function () {
                        searchForm.querySelector('input[name="keyValue"]').value = null;
                // Sử dụng location.reload() để tải lại trang
               searchForm.submit();
                });
                });
            </script>

        <script>
            function arrayToCSV(data, filename) {

                const csvString = data.join('\n');

                const blob = new Blob([csvString], {type: 'text/csv'});

                const url = window.URL.createObjectURL(blob);

                const a = document.createElement('a');

                a.href = url;
                a.download = filename;
                a.style.display = 'none';
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
                document.body.removeChild(a);
            }

            function exportTableToCSV(filename) {
                const data = [];
                const table = document.querySelector('table');

                const headers = Array.from(table.querySelectorAll('thead th')).map(th => th.textContent);
                console.log(headers);
                data.push(headers);

                table.querySelectorAll('tbody tr').forEach(row => {
                    const rowData = Array.from(row.querySelectorAll('td')).map(td => td.textContent);
                    console.log(rowData);
                    data.push(rowData);
                });
                console.log(data);

                arrayToCSV(data, filename);
            }

            const exportCSVButton = document.querySelector('.btn-outline-success');
            exportCSVButton.addEventListener('click', function () {
                exportTableToCSV('class_info.csv');
            });

        </script>
        <%@include file="../common/footer.jsp" %>
    </body>

</html>