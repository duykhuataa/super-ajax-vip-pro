<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../common/header.jsp"/>
    </head>
    <body style="background: url('https://i.imgur.com/TkZIzzd.png') no-repeat center center fixed; background-size: cover;">

        <div class="logo" style="position: absolute; top: 50px; left: 300px;">     
            <img src="https://i.imgur.com/I4ViI95.png" width="200" >
        </div>

        <div class="d-flex justify-content-center align-items-center p-3 vh-100">
            <div class="p-3 border rounded border-3" style="background-color:#e9e6e6;width: 23rem; border-color: rgb(177, 109, 255) !important;">
                <div>
                    <h2 class="text-center mb-3">New password</h2>
                    <p>
                        Please create a new password for EMS
                    </p>
                </div>
                <form action="resetPassword" method="post">
                    <div class="form-group first">
                        <label for="password">New password</label>
                        <input type="password" class="form-control" placeholder="Your New Password" name="newPassword" id="newPassword">
                    </div>
                    <div class="form-group first">
                        <label for="password">Confirm password</label>
                        <input type="password" class="form-control" placeholder="Confirm Password" name="confirmPassword" id="confirmPassword">
                    </div>
                    <div class="mb-1">
                        <span class="d-inline-block text-danger" style='font-size: small; max-width: 200px;' id='log'>
                            ${log}
                        </span>
                    </div>
                    <input type="submit" value="Reset Password" class="btn btn-block py-2 btn-primary">
                    
                </form>
            </div>
        </div>
        <jsp:include page="../common/footer.jsp"/> 
    </body>
</html>
