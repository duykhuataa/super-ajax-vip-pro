<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.Role" %>

<!-- Global EL variables -->
<c:set var="Role.ROLE_ADMIN" value="${role.ROLE_ADMIN}" scope="request" />

<!-- Bootstrap css -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap-select -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">

<!-- JQuery -->
<script src='https://code.jquery.com/jquery-3.5.1.js'></script>

<!-- Momentjs -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>

<!-- Fontawesome css
<link rel="stylesheet" data-purpose="Layout StyleSheet" title="Web Awesome"
    href="/css/app-wa-02670e9412103b5852dcbe140d278c49.css?vsn=d"> -->
<link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/all.css">
<link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/sharp-solid.css">
<link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/sharp-regular.css">
<link rel="stylesheet" href="https://site-assets.fontawesome.com/releases/v6.4.2/css/sharp-light.css">