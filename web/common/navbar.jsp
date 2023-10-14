<%@page contentType="text/html" pageEncoding="UTF-8" %>

<script>
    function calculateTimeAgo(dateCreated) {
        var createdDate = moment(dateCreated);
        var now = moment();
        var duration = moment.duration(now.diff(createdDate));

        var hours = duration.hours();
        var minutes = duration.minutes();

        var timeAgo = "";
        if (hours > 0) {
            timeAgo += hours + "h";
        }
        if (minutes > 0) {
            timeAgo += (timeAgo.length > 0 ? " " : "") + minutes + "m";
        }

        return timeAgo.length > 0 ? timeAgo + " ago" : "Just now";
    }

    function markAllAsRead() {
        $.post("notification", { 
            "action": "markAllAsRead"
        }, function (result) {
            loadNotifications();
        });
    }

    function markAsRead(notificationId) {
        $.post("notification", { 
            "action": "markAsRead",
            "notificationId": notificationId 
        }, function (result) {
            loadNotifications();
        });
    }

    $(document).ready(function () {
        $(".dropdown-notifications-class").click(function(event){
            event.stopPropagation();
        });

        function loadNotifications() {
            $.get("notification", { "action": "getNotifications" }, function (notifications) {
                var notiList = $("#notification-list");
                notiList.empty();

                if (notifications.length > 0) { 
                    var isNotReadCount = 0;
                    notifications.forEach(function (notification) {
                        var item = $("<li>").addClass("dropdown-item");
                        var title = $("<div>").addClass("fs-5 fw-bold").text(notification.title);
                        var content = $("<div>").addClass("fs-6").text(notification.content);

                        var timeAgo = calculateTimeAgo(notification.dateCreated);
                        var time = $("<span>").addClass("form-text").text(timeAgo);

                        item.append(title);
                        item.append(content);
                        item.append(time);

                        if (notification.isRead === 0) {
                            var isNotRead = $("<span>").addClass("ms-1 badge text-bg-danger").text('Not read');
                            item.append(isNotRead);
                            isNotReadCount++;
                        }

                        notiList.append(item);

                        var notiBell = $("#notification-bell");
                        notiBell.text(isNotReadCount > 0 ? isNotReadCount : '');
                    });  
                } else {
                    var item = $("<li>").addClass("dropdown-item disabled");
                    var content = $("<div>").addClass("fs-6").text('No notifications');
                    item.append(content);
                    notiList.append(item);
                }
            });
        }

        loadNotifications();
        setInterval(loadNotifications, 5000);

        $("#notification-list-div").on("click", "li", function() {
            markAsRead(notificationId);
        });

        $("#notification-list-div").click(function() {
            markAllAsRead();
        });
    });

</script>

<div class="row border-bottom sticky-top" style="background-color: #FFFFFF; height: 4rem;">
    <!-- Home page -->
    <div class="col-2 align-self-center ps-5">
        <a href="${pageContext.request.contextPath}/${sessionScope.user.role.roleId == Role.ROLE_ADMIN ? 'admin' : ''}"
            class="align-left">
            <img src="https://i.imgur.com/I4ViI95.png" width="100">
        </a>
    </div>

    <!-- Empty -->
    <div class="col-6">

    </div>

    <c:choose>
        <c:when test="${sessionScope.user == null}">
            <div class="col-2">

            </div>
            <div class="col-2 justify-content-end align-self-center d-flex" style="margin-left: -1rem;">
                <a href="./login" class="btn btn-success" style="width: 6rem;">Log in</a>
            </div>
        </c:when>
        <c:otherwise>
            <!-- Notifications dropdown -->
            <div class="col-2 justify-content-end align-self-center d-flex">
                <div class="dropdown" id="notification-list-div">
                    <div class="btn btn-sm bnt-primary" type="button" data-bs-toggle="dropdown"
                            data-bs-auto-close="outside" aria-expanded="false">
                        <i class="fa-regular fa-bell position-relative" style="font-size: 2rem;">
                            <span class="position-absolute top-0 start-100 translate-middle badge bg-danger align-self-center" id="notification-bell"
                                style="font-size: 11px;"></span>
                        </i>
                        <ul class='dropdown-menu dropdown-menu-end dropdown-notifications-class' id="notification-list">

                        </ul>
                    </div>
                </div>
            </div>

            <!-- Dropdowns -->
            <div class="col-2 justify-content-end align-self-center d-flex" style="margin-left: -1rem;">
                <div class="dropdown">
                    <!-- Account dropdowns -->
                    <div class="btn btn-sm bnt-primary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                        aria-expanded="false">
                        <span class="me-3">
                            ${sessionScope.user.fullName}
                        </span>
                        <img src="${user.image}" style="width: 2rem; height: 2rem; border-radius: 50%">
                    </div>

                    <ul class='dropdown-menu dropdown-menu-end notification-list'>
                        <!-- User info -->
                        <li>
                            <button class='dropdown-item disabled'>
                                ${sessionScope.user.fullName}
                                <br>
                                @${sessionScope.user.username}
                            </button>
                        </li>

                        <!-- Settings -->
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class='dropdown-item'
                                href="${pageContext.request.contextPath}/${sessionScope.user.role.roleId == Role.ROLE_ADMIN ? 'admin' : ''}">
                                <div class="row">
                                    <div class="col-2">
                                        <i class="fa-solid fa-house"></i>
                                    </div>
                                    <div class="col-10">
                                        Home
                                    </div>
                                </div>
                            </a>
                        </li>
                        <c:if test="${sessionScope.user.userId ne 18}">

                            <!-- Settings -->
                            <li><a class='dropdown-item' href="./profile?id=${sessionScope.user.userId}">
                                    <div class="row">
                                        <div class="col-2">
                                            <i class="fa-solid fa-user"></i>
                                        </div>
                                        <div class="col-10">
                                            Profile
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:if>

                        <!-- Logout -->
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li>
                            <a class='dropdown-item' href='./logout'>
                                <div class="row">
                                    <div class="col-2">
                                        <i class="fa-solid fa-arrow-right-from-bracket"></i>
                                    </div>
                                    <div class="col-10">
                                        Log out
                                    </div>
                                </div>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</div>