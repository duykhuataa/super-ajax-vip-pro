<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <jsp:include page="../common/header.jsp"/>
</head>

<body style="background: url('https://i.imgur.com/TkZIzzd.png') no-repeat center center fixed; background-size: cover; overflow-y: hidden;">
    <div class="logo" style="position: absolute; top: 50px; left: 300px;">     
        <img src="https://i.imgur.com/I4ViI95.png" width="200" >
    </div>

    <div class="d-flex flex-column justify-content-center align-items-center vh-100 mt-5">
        <div id="logDiv" class="alert alert-danger" style="display: none; width: 23rem;">
            <div class="row">
                <div class="col-10">
                    ${sessionScope.log}
                </div>
                <div class="col-2 text-end">
                    <button type="button" class="btn-close" aria-label="Close" onclick="closeErrorMessage()"></button>
                </div>
            </div>
        </div>
        <div class="p-3 border rounded border-3" style="background-color:#e9e6e6;width: 23rem; border-color: rgb(177, 109, 255) !important;">
            <form action="login" method="post">
                <h2 class="text-center mb-3">EMS Login</h2>
                <a href="${loginGoogleURL}"             
                    class="btn btn-danger w-100 mb-1 text-start">
                    <i class="fa-brands fa-google me-3" style="color: #ffffff;"></i>
                    Login with <b>Google</b>
                </a>
                <hr>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="usernameOrPassword" id="emailInput" placeholder="yourUsername">
                    <label for="emailInput" class="form-label">Username or Email</label>
                </div>
                <div class="form-floating mb-2">
                    <input type="password" class="form-control" name="password" id="passwordInput" placeholder="password">
                    <label for="passwordInput" class="form-label">Password</label>
                </div>
                <!-- <div class="mb-2 form-check">
                    <input type="checkbox" class="form-check-input" name="" id="rememberMeCheckbox">
                    <label for="rememberMeCheckbox" class="form-check-label">Remember me</label>
                </div> -->
                <div>
                    <button class="btn btn-outline-primary w-100">Login</button>
                </div>
                
                <div class="text-end mt-2">
                    <a href="./forgetPassword" style="text-decoration: none;">Forgot your password?</a>
                </div>
            </form>
        </div>
        <!-- <div class="p-3 border rounded border-3 mt-3" style="background-color:#e9e6e6;width: 23rem; border-color: rgb(177, 109, 255) !important;">
            <h2 class="text-center mb-3">New to EMS?</h2>
            <a href="./register" class="btn btn-success justify-content-center w-100">Create an new Account</a>
        </div> -->
    </div>
    <script>
        if ("${sessionScope.log}") {
            document.getElementById("logDiv").style.display = "block";
        }

        function closeErrorMessage() {
            logDiv.style.display = "none";
        }
    </script>
    <c:remove var="log" scope="session" />
    <jsp:include page="../common/footer.jsp"/>
</body>

</html>
