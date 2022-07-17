<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chat Room</title>
    <!-- Latest minified Bootstrap & JQuery-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js" type="text/javascript"></script>
    <script src="/webjars/sockjs-client/1.5.1/sockjs.js" type="text/javascript"></script>
    <!-- Custom styles for this template -->
    <style>
        .btn.active {
            display: none;
        }

        .btn span:nth-of-type(1)  {
            display: none;
        }
        .btn span:last-child  {
            display: block;
        }

        .btn.active span:nth-of-type(1)  {
            display: block;
        }
        .btn.active span:last-child  {
            display: none;
        }
    </style>
<%--    <link rel="stylesheet" type="text/css" href="/css/main.css"/>--%>
</head>
<body>
<main role="main" class="container-fluid">
    <h1>Simple WebRTC Signalling Server</h1>
    <input type="hidden" id="id" name="id" value="${id}"/>
    <div class="col-lg-12 mb-3">
        <div class="mb-3" text="'User: ' + ${uuid} + ' @ Room #' + ${id}">
            User: ${uuid} & Room #: ${id}
        </div>
        <div class="col-lg-12 mb-3">
            <div class="d-flex justify-content-around mb-3">
                <div id="buttons" class="row">
                    <div class="btn-group mr-2" role="group">
                        <div class="mr-2" data-toggle="buttons">
                            <label class="btn btn-outline-success" id="video_off">
                                <input type="radio" name="options" style="display:none" autocomplete="off">Video On
                            </label>
                            <label class="btn btn-outline-warning active" id="video_on">
                                <input type="radio" name="options" style="display:none" autocomplete="off" checked>Video Off
                            </label>
                        </div>
                        <div class="mr-2" data-toggle="buttons">
                            <label class="btn btn-outline-success" id="audio_off">
                                <input type="radio" name="options" style="display:none" autocomplete="off">Audio On
                            </label>
                            <label class="btn btn-outline-warning active" id="audio_on">
                                <input type="radio" name="options" style="display:none" autocomplete="off" checked>Audio Off
                            </label>
                        </div>
                    </div>

                    <!--<button type="button" class="btn btn-outline-success" id="audio" data-toggle="button">Audio</button>-->
                    <a href="/room/${id}/user/${uuid}/exit">
                        <button type="button" class="btn btn-outline-danger" id="exit" name="exit">
                            Exit Room
                        </button>
                    </a>
                </div>
            </div>
        </div>

        <div class="row justify-content-around mb-3">
            <div class="col-lg-6 mb-3">
                <video id="local_video" autoplay playsinline></video>
            </div>
            <div class="col-lg-6 mb-3">
                <video id="remote_video" autoplay playsinline></video>
            </div>
        </div>
    </div>
</main>
<%--<script src="/webjars/sockjs-client/1.0.2/sockjs.min.js">--%>
<%--<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@5.0.0/bundles/stomp.umd.min.js"></script>--%>
<%--<script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>--%>



<script src="/js/webrtc_client.js"></script>
</body>
</html>
