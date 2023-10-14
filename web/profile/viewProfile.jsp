<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Profile</title>
        <%@include file="../common/header.jsp" %>
    </head>

    <body
        style="background: url('https://i.imgur.com/hRjdH2b.png') no-repeat center center fixed; background-size: cover;">
        <%@include file="../common/navbar.jsp" %>

        <input type="hidden" name="id" value="${user.userId}">
        <br>
        <div class="container mt-4" style="margin-left: 17%;">
            <div class="row">
                <div class="col-sm-6">
                    <h3 class="mt-4"><br><br>User Detail</h3>
                    <div class="fakeimg"><br><br><img src="${user.image}"
                                                      style="width: 20%; border-radius: 50%"><br><br><br></div>
                    <table border="0" cellpadding="7" style="text-align: left">
                        <tr>
                            <td>
                                <h5>Full name:</h5>
                            </td>
                            <td>
                                ${user.fullName}                                     
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h5>User name:</h5>
                            </td>
                            <td>
                                ${user.username}

                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h5>Password:</h5>
                            </td>
                            <td>
                                ${user.password}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h5>Email:</h5>
                            </td>
                            <td>
                                ${user.email}                                       
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h5>Role:</h5>
                            </td>
                            <c:if test="${sessionScope.user.userId eq user.userId}">
                                <td>
                                    <input type="email" name="email" value="${user.role.roleName}" style="border: none" readonly>
                                </td>
                            </c:if>
                            <td>
                                <c:if test="${sessionScope.user.userId ne user.userId}">                                   
                                    <input type="email" name="email" value="${user.role.roleName}" style="border: none" readonly>                                  
                                </c:if>
                            </td>                             
                        </tr>
                    </table>
                </div>
                <c:if test="${user.getRole().getRoleId() ne Role.ROLE_ADMIN}">
                    <div class="col-sm-4" >
                        <h3 class="mt-4"><br><br>Class Detail</h3>
                        <ul class="nav nav-pills flex-column">
                            <c:if test="${empty classList}" >
                                User has not attended any classes.
                            </c:if>
                            <c:if test="${not empty classList}">
                                <c:forEach items="${classList}" var="clas">
                                    <li class="nav-item">
                                        <a class="nav-link" href="#">${clas.className}</a>
                                    </li>
                                </c:forEach>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">...Xem thêm</a>
                                </li>
                            </c:if>
                            <h3 class="mt-4"><br><br>Setting Profile</h3>
                            <li class="nav-item">
                                <button class="btn btn-primary ms-3" data-bs-toggle="modal" data-bs-target="#editProfile">
                                    <i class="fa-solid fa-user-plus"></i>
                                    Edit profile
                                </button>
                                <c:if test="${sessionScope.user.getRole().getRoleId() eq Role.ROLE_ADMIN}">
                                    <form action="admin" method="post" id="deleteUserForm">
                                        <input type="hidden" name="queryString" value="section=users" />
                                        <input type="hidden" name="action" value="deleteUser">
                                        <input type="hidden" name="userId" value="${user.userId}">
                                    </form>
                                    <button class="btn btn-outline-danger ms-3 mt-2" onclick="if (confirm('Do you really want to delete this User?'))
                                                document.getElementById('deleteUserForm').submit();
                                            return false;">
                                        Delete this User
                                    </button>
                                </c:if>
                            </li>
                        </ul>
                        <hr class="d-sm-none">
                    </div>
                </c:if>
            </div>
        </div>

        <div class="modal fade" id="editProfile" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5">Edit Profile</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="profile" method="post" id="editProfileForm">
                            <input type="hidden" name="email" value="${user.email}">
                            <input type="hidden" name="id" value="${user.userId}">
                            <input type="hidden" name="imgur" value="${user.image}">
                            <div class="form-floating">
                                <input type="text" name="name" value="${user.username}" class="form-control"
                                       required>
                                <label for="f-course-code">Your Name</label><br>
                            </div>

                            <div class="form-floating">
                                <input type="text" name="fullname" value="${user.fullName}" class="form-control" 
                                       required>
                                <label for="f-course-code">Full Name</label><br>
                            </div>

                            <div class="form-floating">
                                <input type="text" name="pass" value="${user.password}" class="form-control"
                                       required>
                                <label for="f-course-code">Password</label>
                            </div>
                                       
                            <div class="form-floating">
                                <select name="role">
                                    <option value="${Role.ROLE_STUDENT}" ${user.getRole().getRoleId() eq Role.ROLE_STUDENT ? 'selected' : ''}>Student</option>
                                    <option value="${Role.ROLE_TEACHER}" ${user.getRole().getRoleId() eq Role.ROLE_TEACHER ? 'selected' : ''}>Teacher</option>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="btn-submit-createCourse" onclick="submitEditProfile()">Save</button>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </body>
    <script>
        function submitEditProfile() {
            document.getElementById('editProfileForm').submit();
        }
    </script>
</html>