<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Course Management</title>
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
                <div class="d-flex flex-courseItem">
                    <form action="admin" method="get" id="searchForm">
                        <div class="input-group mb-3" style="width: 35rem; position: relative;">
                            <input type="hidden" name="section" value="courses">
                            <input type="text" class="form-control" name="keyValue" placeholder="Search for users" id="searchUserString" value="${key}">
                            <button type="button" class="btn-close" aria-label="Close" style="color: #ffffff; background-color: transparent; position: absolute; right: 40px; top: 50%; transform: translateY(-50%);" id="closeButton"></button>
                            <button class="btn btn-outline-secondary" type="button" id="button-addon2">
                                <i class="fa-regular fa-magnifying-glass"></i>
                            </button>
                        </div>
                    </form>
                    <div>
                        <button class="btn btn-primary ms-3" data-bs-toggle="modal"
                                data-bs-target="#createCourseModal">
                            <i class="fa-solid fa-user-plus"></i>
                            Create course
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
                <div class="d-flex flex-courseItem">
                    <div class="w-100">
                        <table class="table table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Course code</th>
                                    <th>Course name</th>
                                    <th>Date created</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cl" items="${objectList}" varStatus="count">
                                    <tr style="cursor: pointer;" id="courseRowId"
                                        onclick="toggleOffcanvas('${cl.getCourseCode()}', '${cl.getCourseName()}')">
                                        <th scope="courseItem" class="align-middle">${count.count}
                                        </th>
                                        <td class="${c1.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${cl.getCourseCode()}</td>
                                        <td class="${c1.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${cl.getCourseName()}</td>
                                        <td class="${c1.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${cl.getDateCreated()}</td>
                                        <td>${c1.getIsDeletedString()}</td>
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
                    <span class="fs-1 fw-bold" id="noCourseFoundId" style="display: none;">
                        <center>No course found</center>
                    </span>
                </div>

                <!-- Modals -->
                <div class="modal fade" id="createCourseModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5">Create course</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="admin" method="post" id="createCourseForm">
                                    <input type="hidden" name="queryString"
                                           value="${pageContext.request.queryString}" />
                                <input type="hidden" name="action" value="createCourse">
                                <div class="form-floating">
                                    <input type="text" name="courseCode" class="form-control"
                                           id="f-course-code" placeholder="course-code"
                                           oninput="checkCourseCode()" required>
                                    <label for="f-course-code">Course code</label>
                                </div>
                                <div class="invalid-feedback" id="course-feedback">
                                    Course code had been existed!
                                </div>
                                <div class="form-floating mt-3">
                                    <input type="text" name="courseName" class="form-control"
                                           id="f-course-name" placeholder="course-name" required>
                                    <label for="f-course-name">Course name</label>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="btn-submit-createCourse"
                                    onclick="submitCreateCourse()">Create</button>
                        </div>
                    </div>
                </div>
            </div>
                                
            <!-- Update Class -->
            <div class="modal fade" id="updateCourseModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5">Update course</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                             <div class="modal-body">
                                <form action="admin" method="post" id="updateCourseForm">
                                    <input type="hidden" name="queryString"
                                           value="${pageContext.request.queryString}" />
                                <input type="hidden" name="action" value="updateCourse">
                                <div class="form-floating">
                                    <c:forEach var="course" items="${objectList}" >
                                       <input value="${course.courseCode}" type="text" name="courseCode" class="form-control"
                                           id="f-course-code" placeholder="course-code" required>
                                    <label for="f-course-code">Course code</label>
                                    </c:forEach>
                                </div>
                                <div class="invalid-feedback" id="course-feedback">
                                    Course code had been existed!
                                </div>
                                <div class="form-floating mt-3">
                                    <c:forEach var="course" items="${objectList}" >
                                       <input value="${course.courseName}" type="text" name="courseName" class="form-control"
                                           id="f-course-name" placeholder="course-name" required>
                                    <label for="f-course-name">Course name</label>
                                    </c:forEach>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="btn-submit-updateClass"
                                    onclick="submitUpdateCourse()">Update</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Offcanvas -->
            <div class="offcanvas offcanvas-end" tabindex="-1" id="courseDetailsOffcanvas">
                <div class="offcanvas-header border-bottom">
                    <h2 class="course-name-header"></h2>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas"
                            aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <div class="form-floating">
                        <input type="text" class="form-control course-code" id="lbl-course-code"
                               placeholder="course-code" readonly>
                        <label for="input-course-code">Course code</label>
                    </div>
                    <div class="form-text mb-3">Course code is fixed and cannot be updated</div>
                    <div class="form-floating mb-4">
                        <input type="text" class="form-control course-name" id="input-course-code"
                               placeholder="course-name">
                        <label for="input-course-name">Course name</label>
                    </div>
                    <div class="d-flex">
                        <button class="btn btn-danger align-self-start" style="width: 7rem;">Delete</button>
                        <button class="btn btn-outline-success ms-auto" style="width: 7rem" data-bs-toggle="modal"
                                data-bs-target="#updateCourseModal">
                            <i class="fa-solid fa-user-plus"></i>
                            Update course
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            const offcanvasElement = document.getElementById('courseDetailsOffcanvas');

            function toggleOffcanvas(courseCode, courseName) {
                offcanvasElement.querySelector('.course-name-header').innerHTML = courseName;

                offcanvasElement.querySelector('.course-code').value = courseCode;
                offcanvasElement.querySelector('.course-name').value = courseName;

                new bootstrap.Offcanvas(offcanvasElement).toggle();
            }
        </script>

        <script>
            var listCourse = <%= request.getAttribute("courseListJson") %>;
            function checkCourseCodeExist(codeInput) {
                for (var i = 0; i < listCourse.length; i++) {
                    if (listCourse[i].courseCode === codeInput) {
                        return true;
                    }
                }
                return false;
            }

            function checkCourseCode() {
                var courseCode = document.getElementById('f-course-code');
                var coursefeedback = document.getElementById('course-feedback');

                if (checkCourseCodeExist(courseCode.value)) {
                    coursefeedback.style.display = 'block';
                    return false;
                } else {
                    coursefeedback.style.display = 'none';
                    return true;
                }
            }

            function submitCreateCourse() {
                if (checkCourseCode()) {
                    document.getElementById('createCourseForm').submit();
                }
            }
            
            function submitUpdateCourse(){
                if(checkCourseCode()){
                document.getElementById('updateCourseForm').submit();
            }
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
                exportTableToCSV('course_info.csv');
            });

        </script>

        <%@include file="../common/footer.jsp" %>
    </body>

</html>