<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html>

    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <jsp:include page="common/header.jsp" />
        <style>
            body {
                background-color: #f0f0f0;
            }

            header {
                background-color: #333;
                color: #fff;
                padding: 20px;
            }

            main {
                max-width: 600px;
                margin: 0 auto;
                padding: 20px;
                background-color: #fff;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                display: flex;
                flex-wrap: nowrap;
            }

            img {
                max-width: 100%;
                height: auto;
            }

            .flex-container {
                display: flex;
                justify-content: center;
                margin-top: 3%;
            }

            .flex-container>div {
                color: black;
                margin: 10px;
                text-align: center;
                z-index: 3;
            }
        </style>
    </head>

    <body style="overflow-x: hidden;">
        <%@include file="common/navbar.jsp" %>

        <jsp:include page="common/sidebar.jsp" />
        <div class="p-3" style="margin-left: 250px;">
            <div class="flex-container">
                <form action="class" method="get" id="searchForm">
                        <div class="input-group mb-3" style="width: 35rem; position: relative;">
                            <input type="hidden" name="section" value="users">
                            <input type="text" class="form-control" name="keyValue" placeholder="Search name of class or course code" id="searchClassString" value="${key}">
                            <button type="button" class="btn-close" aria-label="Close" style="color: #ffffff; background-color: transparent; position: absolute; right: 40px; top: 50%; transform: translateY(-50%);" id="closeButton"></button>
                            <button class="btn btn-outline-secondary" type="button" id="button-addon2">
                                <i class="fa-regular fa-magnifying-glass"></i>
                            </button>
                        </div>
                </form>
            </div>
            <c:if test="${sessionScope.user.getRole().getRoleId() eq Role.ROLE_STUDENT}"> 
                <form action="process" method="get">
                    <div class="flex-container">
                        <div>
                            <input type="hidden" name="action" value="searchClass">
                            <input type="text" class="form-control" name="classEnrollKey" placeholder=""
                                   required> 
                        </div>
                        <div>
                            <button type="submit" class="btn btn-secondary" id="btn-submit-findClass"
                                    style="size: 10px; background-color: #af94fc;">Enroll class</button>
                        </div>
                    </div>
                </form>
                 <div style="color: red">${mess}</div>
            </c:if>
            <div class="row">
                <c:forEach items="${requestScope.classList}" var="c">
                    <div class="col-4 mt-3 class-item">
                        <div class="card">
                            <h5 class="card-header">${c.classCode}</h5>
                            <div class="card-body">
                                <h5 class="card-title">${c.className}</h5><br>
                                <a href="./class?classId=${c.classId}" class="btn btn-secondary mt-2"
                                   style=" background-color: #af94fc;">
                                    <span>Details</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="col-12">
                    <nav>
                        <ul class="pagination justify-content-center">  
                            <li class="page-item <c:if test="${index eq 1}">disabled</c:if> "><a class="page-link" href="${href}?index=${index-1}">Previous</span></a></li>
                                <c:forEach items="${listPage}" var="p">
                                <li class="page-item <c:if test="${listPageSize ne 1}">active</c:if> "><a class="page-link" href="${href}?index=${p}">${p}</a></li>
                                </c:forEach>
                            <li class="page-item <c:if test="${listPageSize eq index}">disabled</c:if>"><a class="page-link" href="${href}?index=${index + 1}">Next</a></li>
                            </ul>
                        </nav>
                    </div>
                    <div class="p-3" style="display: none;" id="noClassFoundId">
                        <span class="fs-1 fw-bold"><center>No class found!</center></span>
                    </div>
                </div>
            </div>

            <script>
                
                // Định nghĩa một hàm để xử lý sự kiện tìm kiếm
                function handleSearch() {
                    const searchForm = document.getElementById("searchForm");
                    const searchUserString = document.getElementById("searchClassString");

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
            
                function displayMessage() {
                    let mess = "${sessionScope.messIndex}";
                    if (typeof mess === 'string' && mess !== "") {
                        alert(mess);
                    }
                }
                displayMessage();
        </script>
        <jsp:include page="common/footer.jsp" />
    </body>

</html>