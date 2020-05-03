<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="include/header.jsp" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>人像-录入</title>
</head>
<script src="${pageContext.request.contextPath}/res/js/jquery-3.4.1.min.js"></script>
<style>

    #video {
        position: absolute;
        right: 300px;
        top: -190px;
    }

    #img {
        position: absolute;
        left: 0;
        top: 0;
    }

    .auto {
        position: absolute;
        left: 50%;
        top: 50%;
        height: 320px;
        margin-top: -160px;
    }

    button {
        cursor: pointer;
        margin: 0 auto;
        border: 1px solid #f0f0f0;
        background: #5CACEE;
        color: #FFF;
        width: 100px;
        height: 36px;
        line-height: 36px;
        border-radius: 8px;
        text-align: center;
        /*禁止选择*/
        -webkit-touch-callout: none;
        /* iOS Safari */
        -webkit-user-select: none;
        /* Chrome/Safari/Opera */
        -khtml-user-select: none;
        /* Konqueror */
        -moz-user-select: none;
        /* Firefox */
        -ms-user-select: none;
        /* Internet Explorer/Edge */
        user-select: none;
        /* Non-prefixed version, currently not supported by any browser */
    }
</style>

<body background="${pageContext.request.contextPath}/res/images/fore/WebsiteImage/bakcgro_faceregis.png">
<div class="auto">
    <video id="video" width="380" height="260" autoplay></video>
    <canvas id="canvas" width="480" height="320" style="display: none;"></canvas>

    <img src="./body_default.png" id="img" width="480" height="320" style="margin-left: 20px; display: none;">
    <div>
        <button id="capture" style="display: none;"></button>
    </div>

</div>


<script>

    var file, stream,judge_face;
    //访问用户媒体设备的兼容方法
    function getUserMedia(constraints, success, error) {
        if (navigator.mediaDevices.getUserMedia) {
            //最新的标准API
            navigator.mediaDevices.getUserMedia(constraints).then(success).catch(error);
        } else if (navigator.webkitGetUserMedia) {
            //webkit核心浏览器
            navigator.webkitGetUserMedia(constraints, success, error)
        } else if (navigator.mozGetUserMedia) {
            //firfox浏览器
            navigator.mozGetUserMedia(constraints, success, error);
        } else if (navigator.getUserMedia) {
            //旧版API
            navigator.getUserMedia(constraints, success, error);
        }
    }

    let video = document.getElementById('video');
    let canvas = document.getElementById('canvas');
    let context = canvas.getContext('2d');

    function success(stream) {
        //兼容webkit核心浏览器
        let CompatibleURL = window.URL || window.webkitURL;
        //将视频流设置为video元素的源
        console.log(stream);
        stream = stream;
        //video.src = CompatibleURL.createObjectURL(stream);
        video.srcObject = stream;
        video.play();
    }

    function error(error) {
        console.log(`访问用户媒体设备失败${error.name}, ${error.message}`);
    }

    if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
        //调用用户媒体设备, 访问摄像头
        getUserMedia({ video: { width: 480, height: 320 } }, success, error);
    } else {
        alert('不支持访问用户媒体');
    }


    // base64转文件
    document.getElementById('capture').addEventListener('click', function () {
        context.drawImage(video, 0, 0, 480, 320);
        // 获取图片base64链接
        var image_js = canvas.toDataURL('image/png');

        $.ajax({
            async:false,
            type: "post",
            url: "/tmall/register/receiveData",
            data: {'base64': JSON.stringify(image_js)},
            dataType: "json",
            mimeType: "multipart/form-data",
            success: function (data) {
                alert(data.msg);
                location.reload();
                if (data.success) {
                    location.href = "/tmall/login";
                    $(".msg").stop(true, true).animate({
                        opacity: 1
                    }, 550, function () {
                        $(".msg").animate({
                            opacity: 0
                        }, 1500, function () {
                            location.href = "/tmall/login";
                        });
                    });
                }else{

                }
            },
            error: function (data) {

            }
        })
    });


    // 2秒后模拟点击
    setTimeout(function () {
        // IE
        if (document.all) {
            document.getElementById("capture").click();
        }
        // 其它浏览器
        else {
            var e = document.createEvent("MouseEvents");
            e.initEvent("click", true, true);
            document.getElementById("capture").dispatchEvent(e);
        }
    }, 2000);


</script>
</body>

</html>