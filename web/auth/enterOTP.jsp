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
                    <h2 class="text-center mb-3">Forgot your password?</h2>
                    <p>Change your password in 3 easy steps. This is help you to 
                        secure your password!
                    </p>
                    <ol>
                        <li><span class="text-primary text-medium"></span>
                            Enter your email address below.</li>
                        <li><span class="text-primary text-medium"></span>
                            Our system will send you an OTP to your email</li>
                        <li><span class="text-primary text-medium"></span>
                            Enter the OTP on the next page</li>
                    </ol>
                </div>
                <div>
                    <form action="enterOTP" method="post">
                        <div class="form-group last mb-3">
                            <div class="mb-1">
                                <span class="d-inline-block text-danger" style='font-size: small; max-width: 200px;' id='log'>
                                    ${log} 
                                </span>
                            </div>
                            <label for="email-for-pass">Enter OTP</label>                                       
                            <input
                                id="opt" name="otp" placeholder="Enter OTP"
                                class="form-control" type="text" required="required">
                        </div>                                   
                        <input type="submit" value="Enter" class="btn btn-block py-2 btn-primary">
                        <a class="btn btn-block py-2 btn-danger" href="login">Back To Login</a>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="../common/footer.jsp"/> 
    </body>
</html>
