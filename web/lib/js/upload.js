const SERVER_ROOT = "";
const TAIL = "";
//URL: [SERVER_ROOT] + [API URL] + [TAIL]
//Sample: http://47.107.236.216/Flexible/speak/SpeakSubmit.php

function submit(has_password) {
    let data = {};
    if (has_password) {
        let content = $("#content-private").val();
        let password = $("#content-password-private").val();
        if (content === "写下你想说的话吧") {
            notice("你还没填写内容呢！", function() {
                closeNotice();
            });
            return;
        }
        let validation = validatePassword(password);
        if (validation === 1) {
            notice("你还没设置密语呢！", function() {
                closeNotice();
            });
            return;
        }
        if (validation === 2) {
            notice("密语不大合适呢，换一个吧", function() {
                closeNotice();
            });
            return;
        }
        data = {
            password: hex_md5(password),
            content: content,
            img: content_img_private
        };
    } else {
        let content = $("#content-public").val();
        if (content === "写下你想说的话吧") {
            notice("你还没填写内容呢！", function() {
                closeNotice();
            });
            return;
        }
        data = {
            content: content,
            img: content_img_public
        };
    }
    $("#loading-icon").show();
    $.ajax({
        method : 'POST',
        dataType : 'json',
        data : data,
        url : SERVER_ROOT + 'speak/SpeakSubmit' + TAIL,
        success : function(result){
            switch (result.code) {
                case 0:
                    notice("提交成功啦！", function() {
                        loadBox(result.obj);
                        closeNotice();
                    });
                    break;
                case 1:
                    notice("密语被用过了，换一个吧！", function() {
                        closeNotice();
                    });
                    break;
                default:
                    notice("真抱歉，提交失败了", function() {
                        closeNotice();
                    });
            }
        },
        error : function() {
            notice("真抱歉，提交失败了", function() {
                closeNotice();
            });
        },
        complete : function() {
            $("#loading-icon").hide();
        }
    });
}

function validate(input, pic) {
    pic.html('<img src="resource/public/loading.gif" style="height: 80%;">');
    $.ajax({
        type : 'POST',
        dataType : 'json',
        data : { password : hex_md5($("#content-password-private").val()) },
        url : SERVER_ROOT + 'speak/CheckOutPassword' + TAIL,
        success : function(result){
            if (result.code < 0) {
                notice("出现未知错误", function() {
                    closeNotice();
                });
                input.val("出现未知错误");
                pic.html('<img src="resource/dialog-speak/public/fault.png" style="height: 80%;">');
            } else {
                if (result.code === 1) {
                    input.val("密语已被使用过了");
                    pic.html('<img src="resource/dialog-speak/public/fault.png" style="height: 80%;">');
                } else {
                    pic.html('<img src="resource/dialog-speak/public/ok.png" style="height: 80%;">');
                }
            }
        },
        error : function(){
            notice("出现未知错误", function() {
                closeNotice();
            });
            input.val("出现未知错误");
            pic.html('<img src="resource/dialog-speak/public/fault.png" style="height: 80%;">');
        }
    });
}

function fetchPublic() {
    $("#loading-icon").show();
    $.ajax({
        type : 'POST',
        dataType : 'json',
        url : SERVER_ROOT + 'browse/BrowseList' + TAIL,
        success : function(result) {
            if (result.code < 0) {
                notice("真抱歉，加载失败了", function() {
                    closeNotice();
                });
            } else {
                loadBox(result.obj);
            }
        },
        error : function() {
            notice("真抱歉，加载失败了", function() {
                closeNotice();
            });
        },
        complete : function() {
            $("#loading-icon").hide();
        }
    });
}

function fetchPrivate() {
    let password = $("#password").val();
    let validation = validatePassword(password);
    if (validation > 0) {
        notice("密语不大合适呢！", function() {
            closeNotice();
        });
        return;
    }
    $("#loading-icon").show();
    $.ajax({
        type : 'POST',
        dataType : 'json',
        data : { password : hex_md5(password) },
        url : SERVER_ROOT + 'browse/BrowseContent' + TAIL,
        success : function(result){
            switch (result.code) {
                case 0:
                    loadBox(result.obj);
                    break;
                case 1:
                    notice("什么也没找到哦！", function() {
                        closeNotice();
                    });
                    break;
                default:
                    notice("真抱歉，加载失败了", function() {
                        closeNotice();
                    });
            }

        },
        error : function(){
            notice("真抱歉，加载失败了", function() {
                closeNotice();
            });
        },
        complete : function () {
            $("#loading-icon").hide();
        }
    });
}