<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <!-- Latest minified Bootstrap & JQuery-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <!-- Custom styles for this template -->
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>
</head>
<body>
<main role="main" class="container">
    <h1>Simple WebRTC Signaling Server</h1>
    <div id="container">
        <p>
            This part receives a room number (or generates new one), and redirects current user there.
        </p>
        <form method="post" action="/room" id="form">
            <input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
            <div class="row justify-content-md-center">
                <div class="input-group col-md-6 mb-3 justify-content-md-center">
                    <div class="mb-3" c:if test="${!empty rooms}">
                        <label>Select one of the rooms created:</label><br>
                        <h4>
                            <c:forEach var="room" items="${rooms}">
                                <a href="/room/${room.id}" id="button-link-${room.id}">
                                    <button type="button" name="action" text="${room.id}" value="${room.id}"
                                            class="btn badge badge-primary" onclick="addUuidToButtonLink(this);">
                                            ${room.id}
                                    </button>
                                </a>
                            </c:forEach>
                        </h4>
                    </div>
                    <div class="mb-3">
                        <label for="max_num">To create a new room enter your room number,
                            or press 'Random #' button to get a random one</label>
                        <input class="form-control" name="max_num" id="max_num" type="number" placeholder="Min: 0, max: 99"
                               min="0" max="99" value="${max_num}" required>
                    </div>
                    <div>
                        <a href="/room/random">
                            <button class="btn btn-outline-success" type="button">Random #</button>
                        </a>
                        <button name="action" value="create" type="submit" class="btn btn-outline-primary">
                            Create Selected Room</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</main>

<%--<script type="text/javascript">--%>
<%--    $(function(){--%>
<%--        const uuidInput = document.querySelector('input#uuid');--%>

<%--        function guid() {--%>
<%--            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {--%>
<%--                let r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);--%>
<%--                return v.toString(16);--%>
<%--            });--%>
<%--        }--%>

<%--        if (localStorage.getItem("uuid") === null) {--%>
<%--            localStorage.setItem("uuid", guid());--%>
<%--        }--%>
<%--        uuidInput.value = localStorage.getItem("uuid");--%>
<%--        console.log("local.uuid:" + localStorage.getItem("uuid"));--%>
<%--        // console.log("input.value:" + uuidInput.value);--%>
<%--    });--%>

<%--    function addUuidToButtonLink(button) {--%>
<%--        let id = 'button-link-' + button.value;--%>
<%--        let ref = document.getElementById(id).href;--%>
<%--        document.getElementById(id).href = ref + '/user/' + localStorage.getItem("uuid");--%>
<%--        console.log("link.href:" + document.getElementById(id).href);--%>
<%--    }--%>

<%--</script>--%>
<script src="/js/create_room.js"></script>
</body>

</html>
