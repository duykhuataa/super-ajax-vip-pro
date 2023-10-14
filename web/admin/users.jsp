<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Management</title>
        <%@include file="../common/header.jsp" %>
    </head>

    <body style="overflow-x: hidden; background-color: #F0F0F0;">
        <%@include file="../common/navbar.jsp"%>

        <div class="">
            <!-- Sidebar -->
            <%@include file="sidebar.jsp"%>

            <!-- Main -->
            <div class="p-3" style="margin-left: 250px;">
                <!-- Functions -->
                <div class="d-flex flex-row">
                    <form action="admin" method="get" id="searchForm">
                        <div class="input-group mb-3" style="width: 35rem; position: relative;">
                            <input type="hidden" name="section" value="users">
                            <input type="text" class="form-control" name="keyValue" placeholder="Search for users" id="searchUserString" value="${key}">
                            <button type="button" class="btn-close" aria-label="Close" style="color: #ffffff; background-color: transparent; position: absolute; right: 40px; top: 50%; transform: translateY(-50%);" id="closeButton"></button>
                            <button class="btn btn-outline-secondary" type="button" id="button-addon2">
                                <i class="fa-regular fa-magnifying-glass"></i>
                            </button>
                        </div>
                    </form>
                    <div>
                        <button class="btn btn-primary ms-3" data-bs-toggle="modal" data-bs-target="#createUserModal">
                            <i class="fa-solid fa-user-plus"></i>
                            Create user
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
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Full Name</th>
                                    <th>Image</th>
                                    <th>Date created</th>
                                    <th>Is deleted</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${objectList}" varStatus="count">                                 
                                        <tr style="cursor: pointer;" onclick="goToUserProfile('${user.userId}')" id="userRowId">
                                            <td scope="row" class="align-middle">${count.count}</td>
                                            <td class="${user.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${user.username}</td>
                                            <td class="${user.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${user.email}</td>
                                            <td class="${user.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${user.role.roleName}</td>
                                            <td class="${user.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${user.fullName}</td>
                                            <td><img src="${user.image}" alt="" style="width: 2rem;"></td>
                                            <td class="${user.isDeleted == 1 ? 'text-decoration-line-through' : ''}">${user.dateCreated}</td>
                                            <td>${user.getIsDeletedString()}</td>                       
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
                    <span class="fs-1 fw-bold" id="noUserFoundId" style="display: none;"><center>No user found</center></span>
                </div>

                <!-- Modals -->
                <div class="modal fade" id="createUserModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5">Create user</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="admin" method="post" id="createUserForm">
                                    <input type="hidden" name="queryString" value="${pageContext.request.queryString}" />
                                <input type="hidden" name="action" value="createUser">
                                <div class="form-floating">
                                    <input type="email" name="email" class="form-control" id="fl-email" placeholder="username@fpt.edu.vn"
                                           oninput="genUsername(); genEmail(); checkFptEmail()" required>
                                    <label for="fl-email">Email address</label>
                                </div>
                                <div class="invalid-feedback" id="fl-email-feedback">
                                    Only accept @fpt.edu.vn email or @fe.edu.vn
                                </div>
                                <div class="invalid-feedback" id="fl-email-feedback1">
                                    Email had been existed!
                                </div>
                                <div class="form-floating mt-3 mb-3">
                                    <input type="text" name="username" class="form-control" id="fl-username" placeholder="username"
                                           required>
                                    <label for="fl-email">Username</label>
                                </div>
                                <div>
                                    <span class="form-text">Role</span><br>
                                    <select class="selectpicker show-tick" name="role">
                                        <option value="0">Student</option>
                                        <option value="1">Teacher</option>
                                    </select>                          
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="btn-submit-createUser" onclick="submitCreateUser()">Create</button>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                function displayMessage() {
                    let mess = "${requestScope.messProfile}";
                    if (typeof mess === 'string' && mess !== "") {
                        alert(mess);
                    }
                }
                displayMessage();
                var listUser = <%= request.getAttribute("userListJson") %>;
                function genUsername() {
                    var email = document.getElementById('fl-email').value;
                    document.getElementById('fl-username').value = email.split("@")[0];
                }

                function genEmail() {
                    var fl_email = document.getElementById('fl-email');
                    if (fl_email.value.endsWith("@")) {
                        fl_email.value += "fpt.edu.vn";
                    }
                }
                function checkEmailExist(emailInput) {
                    for (var i = 0; i < listUser.length; i++) {
                        if (listUser[i].email === emailInput && listUser[i].isDeleted === 0) {
                            return true;
                        }
                    }
                    return false;
                }

                function checkFptEmail() {
                    var fl_email = document.getElementById('fl-email');
                    var fl_email_feedback = document.getElementById('fl-email-feedback');
                    var btn_submit_createUser = document.getElementById('btn-submit-createUser');
                    var fl_email_feedback1 = document.getElementById('fl-email-feedback1');

                    if (fl_email.value !== "" && fl_email.value.includes('@') && !fl_email.value.endsWith("@fpt.edu.vn") && !fl_email.value.endsWith("@fe.edu.vn")) {
                        fl_email.classList.add('is-invalid');
                        fl_email_feedback.style.display = 'block';
                        return false;
                    } else {
                        if (checkEmailExist(fl_email.value)) {
                            fl_email.classList.add('is-invalid');
                            fl_email_feedback1.style.display = 'block';
                            return false;
                        } else {
                            fl_email.classList.remove('is-invalid');
                            fl_email_feedback1.style.display = 'none';
                            return true;
                        }
                    }
                }

                function submitCreateUser() {
                    if (checkFptEmail()) {
                        document.getElementById('createUserForm').submit();
                    }
                }
                function goToUserProfile(userId) {
                    var userProfileUrl = './profile?id=' + userId;
                    window.location.href = userProfileUrl;
                }
                function submitSearch() {

                    document.getElementById('createUserForm').submit();
                }
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
                    data.push(headers);
                    listUser.forEach(user => {
                        const rowData = Object.values(user);
                        data.push(rowData);
                    });
                    console.log(data);
//                    table.querySelectorAll('tbody tr').forEach(row => {
//                        const rowData = Array.from(row.querySelectorAll('td')).map(td => td.textContent);
//                        data.push(rowData);
//                    });

                    arrayToCSV(data, filename);
                }

                const exportCSVButton = document.querySelector('.btn-outline-success');
                exportCSVButton.addEventListener('click', function () {
                    exportTableToCSV('user_info.csv');
                }
                );

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
        </div>

        <%@include file="../common/footer.jsp"%>
    </body>

</html>
