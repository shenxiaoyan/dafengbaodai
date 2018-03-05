
// im sdk 登录 示例代码                     js/login/login.js
// im sdk 登出 示例代码                     js/logout/logout.js
// im 解析一条消息 示例代码                 js/common/show_one_msg.js
// im demo 基本逻辑                         js/base.js
// im sdk 资料管理 api 示例代码             js/profile/profile_manager.js
// im sdk 好友管理 api 示例代码             js/friend/friend_manager.js
// im sdk 好友申请管理 api 示例代码             js/friend/friend_pendency_manager.js
// im sdk 好友黑名单管理 api 示例代码        js/friend/friend_black_list_manager.js
// im sdk 最近联系人 api 示例代码           js/recentcontact/recent_contact_list_manager.js
// im sdk 群组管理 api 示例代码             js/group/group_manager.js
// im sdk 群成员管理 api 示例代码               js/group/group_member_manager.js
// im sdk 加群申请管理 api 示例代码             js/group/group_pendency_manager.js
// im 切换聊天好友或群组 示例代码               js/switch_chat_obj.js
// im sdk 获取c2c获取群组历史消息 示例代码      js/msg/get_history_msg.js
// im sdk 发送普通消息(文本和表情) api 示例代码 js/msg/send_common_msg.js
// im sdk 上传和发送图片消息 api 示例代码       js/msg/upload_and_send_pic_msg.js
// im sdk 上传和发送文件消息 api 示例代码       js/msg/upload_and_send_file_msg.js
// im sdk 切换播放语音消息 示例代码         js/msg/switch_play_sound_msg.js
// im sdk 发送自定义消息 api 示例代码       js/msg/send_custom_msg.js
// im sdk 发送群自定义通知 api 示例代码     js/msg/send_custom_group_notify_msg.js
// im 监听新消息(c2c或群) 示例代码         js/msg/receive_new_msg.js
// im 监听群系统通知消息 示例代码           js/msg/receive_group_system_msg.js
// im 监听好友系统通知消息 示例代码         js/msg/receive_friend_system_msg.js
// im 监听资料系统通知消息 示例代码         js/msg/receive_profile_system_msg.js


angular.module('app').service('imService', function ($rootScope) {


    //帐号模式，0-表示独立模式，1-表示托管模式
    var accountMode = 0;

    //官方 demo appid,需要开发者自己修改（托管模式）
    var sdkAppID = 1400030321;
    var accountType = 12691;


    //当前用户身份
    var loginInfo = {
        'sdkAppID': sdkAppID, //用户所属应用id,必填
        'accountType': accountType, //用户所属应用帐号类型，必填
        'identifier': '2', //当前用户ID,必须是否字符串类型，必填
        'userSig': 'eJxlz01PgzAcBvA7n6LhqtGWUhwmHszmCwxi5kCrF9LRQjpiy6BzQ*N3d6LGJp5-z--leXcAAG6WLE9YWeqtMoUZWuGCc*BC9-gP21bygpkCd-wfin0rO1GwyohuRI*EHoR2RHKhjKzkb8CinjfFuH4U5B8GMcQesiOyHjG9yqfRYsYylpgGNzN0s5mvTtPnOH*j1xO9C*ujOD6jebOCj2UU3Pm7qJ6uFX1IBzLH-GkL19UykJcyqUuS0cM-epJtFvdq6G9zqi*sk0a*iJ*HQuL7EMHA0lfR9VKr7y4QEYRQCL8aOx-OJ6m3Wzk', //当前用户身份凭证，必须是字符串类型，必填
        'identifierNick': '杨为乾', //当前用户昵称，不用填写，登录接口会返回用户的昵称，如果没有设置，则返回用户的id
        'headurl': 'img/me.jpg' //当前用户默认头像，选填，如果设置过头像，则可以通过拉取个人资料接口来得到头像信息
    };

    var AdminAcount = 'admin_wuliu';
    var selType = webim.SESSION_TYPE.C2C; //当前聊天类型
    var selToID = null; //当前选中聊天id（当聊天类型为私聊时，该值为好友帐号，否则为群号）
    var selSess = null; //当前聊天会话对象
    var recentSessMap = {}; //保存最近会话列表
    var reqRecentSessCount = 50; //每次请求的最近会话条数，业务可以自定义

    //默认好友头像
    var friendHeadUrl = 'img/friend.jpg'; //仅demo使用，用于没有设置过头像的好友
    //默认群头像
    var groupHeadUrl = 'img/group.jpg'; //仅demo使用，用于没有设置过群头像的情况


    //存放c2c或者群信息（c2c用户：c2c用户id，昵称，头像；群：群id，群名称，群头像）
    var infoMap = {}; //初始化时，可以先拉取我的好友和我的群组信息


    var maxNameLen = 12; //我的好友或群组列表中名称显示最大长度，仅demo用得到
    var reqMsgCount = 15; //每次请求的历史消息(c2c获取群)条数，仅demo用得到

    var pageSize = 15; //表格的每页条数，bootstrap table 分页时用到
    var totalCount = 200; //每次接口请求的条数，bootstrap table 分页时用到

    var emotionFlag = false; //是否打开过表情选择框

    var curPlayAudio = null; //当前正在播放的audio对象

    var getPrePageC2CHistroyMsgInfoMap = {}; //保留下一次拉取好友历史消息的信息
    var getPrePageGroupHistroyMsgInfoMap = {}; //保留下一次拉取群历史消息的信息

    var defaultSelGroupId = null; //登录默认选中的群id，选填，仅demo用得到

    //监听（多终端同步）群系统消息方法，方法都定义在receive_group_system_msg.js文件中
    //注意每个数字代表的含义，比如，
    //1表示监听申请加群消息，2表示监听申请加群被同意消息，3表示监听申请加群被拒绝消息
    var onGroupSystemNotifys = {
        "1": onApplyJoinGroupRequestNotify, //申请加群请求（只有管理员会收到）
        "2": onApplyJoinGroupAcceptNotify, //申请加群被同意（只有申请人能够收到）
        "3": onApplyJoinGroupRefuseNotify, //申请加群被拒绝（只有申请人能够收到）
        "4": onKickedGroupNotify, //被管理员踢出群(只有被踢者接收到)
        "5": onDestoryGroupNotify, //群被解散(全员接收)
        "6": onCreateGroupNotify, //创建群(创建者接收)
        "7": onInvitedJoinGroupNotify, //邀请加群(被邀请者接收)
        "8": onQuitGroupNotify, //主动退群(主动退出者接收)
        "9": onSetedGroupAdminNotify, //设置管理员(被设置者接收)
        "10": onCanceledGroupAdminNotify, //取消管理员(被取消者接收)
        "11": onRevokeGroupNotify, //群已被回收(全员接收)
        "15": onReadedSyncGroupNotify, //群消息已读同步通知
        "255": onCustomGroupNotify //用户自定义通知(默认全员接收)
    };

    //监听好友系统通知函数对象，方法都定义在receive_friend_system_msg.js文件中
    var onFriendSystemNotifys = {
        "1": onFriendAddNotify, //好友表增加
        "2": onFriendDeleteNotify, //好友表删除
        "3": onPendencyAddNotify, //未决增加
        "4": onPendencyDeleteNotify, //未决删除
        "5": onBlackListAddNotify, //黑名单增加
        "6": onBlackListDeleteNotify //黑名单删除
    };

    var onC2cEventNotifys = {
        "92": onMsgReadedNotify, //消息已读通知
    };

    //监听资料系统通知函数对象，方法都定义在receive_profile_system_msg.js文件中
    var onProfileSystemNotifys = {
        "1": onProfileModifyNotify //资料修改
    };

    //监听连接状态回调变化事件
    var onConnNotify = function (resp) {
        var info;
        switch (resp.ErrorCode) {
            case webim.CONNECTION_STATUS.ON:
                webim.Log.warn('建立连接成功: ' + resp.ErrorInfo);
                break;
            case webim.CONNECTION_STATUS.OFF:
                info = '连接已断开，无法收到新消息，请检查下你的网络是否正常: ' + resp.ErrorInfo;
                // alert(info);
                webim.Log.warn(info);
                break;
            case webim.CONNECTION_STATUS.RECONNECT:
                info = '连接状态恢复正常: ' + resp.ErrorInfo;
                // alert(info);
                webim.Log.warn(info);
                break;
            default:
                webim.Log.error('未知连接状态: =' + resp.ErrorInfo);
                break;
        }
    };

    //IE9(含)以下浏览器用到的jsonp回调函数
    function jsonpCallback(rspData) {
        webim.setJsonpLastRspData(rspData);
    }

    //监听事件
    var listeners = {
        "onConnNotify": onConnNotify //监听连接状态回调变化事件,必填
        ,
        "jsonpCallback": jsonpCallback //IE9(含)以下浏览器用到的jsonp回调函数，
        ,
        "onMsgNotify": onMsgNotify //监听新消息(私聊，普通群(非直播聊天室)消息，全员推送消息)事件，必填
        ,
        "onBigGroupMsgNotify": onBigGroupMsgNotify //监听新消息(直播聊天室)事件，直播场景下必填
        ,
        "onGroupSystemNotifys": onGroupSystemNotifys //监听（多终端同步）群系统消息事件，如果不需要监听，可不填
        ,
        "onGroupInfoChangeNotify": onGroupInfoChangeNotify //监听群资料变化事件，选填
        ,
        "onFriendSystemNotifys": onFriendSystemNotifys //监听好友系统通知事件，选填
        ,
        "onProfileSystemNotifys": onProfileSystemNotifys //监听资料系统（自己或好友）通知事件，选填
        ,
        "onKickedEventCall": onKickedEventCall //被其他登录实例踢下线
        ,
        "onC2cEventNotifys": onC2cEventNotifys //监听C2C系统消息通道
        ,
        "onAppliedDownloadUrl": onAppliedDownloadUrl //申请文件/音频下载地址的回调
    };

    var isAccessFormalEnv = true; //是否访问正式环境

    if (webim.Tool.getQueryString("isAccessFormalEnv") == "false") {
        isAccessFormalEnv = false; //访问测试环境
    }

    var isLogOn = false; //是否开启sdk在控制台打印日志

    //初始化时，其他对象，选填
    var options = {
        'isAccessFormalEnv': isAccessFormalEnv, //是否访问正式环境，默认访问正式，选填
        'isLogOn': isLogOn //是否开启控制台打印日志,默认开启，选填
    }

    if (accountMode == 1) { //托管模式
        //判断是否已经拿到临时身份凭证
        if (webim.Tool.getQueryString('tmpsig')) {
            if (loginInfo.identifier == null) {
                webim.Log.info('start fetchUserSig');
                //获取正式身份凭证，成功后会回调tlsGetUserSig(res)函数
                TLSHelper.fetchUserSig();
            }
        } else { //未登录
            if (loginInfo.identifier == null) {
                //弹出选择应用类型对话框
                $('#select_app_dialog').modal('show');
                $("body").css("background-color", 'white');
            }
        }
    } else { //独立模式
        $('#login_dialog').modal('show');
    }


    var msgflow = document.getElementsByClassName("msgflow")[0];
    var bindScrollHistoryEvent = {
        init: function () {
            msgflow.onscroll = function () {
                if (msgflow.scrollTop == 0) {
                    msgflow.scrollTop = 10;
                    if (selType == webim.SESSION_TYPE.C2C) {
                        getPrePageC2CHistoryMsgs();
                    } else {
                        getPrePageGroupHistoryMsgs();
                    }

                }
            }
        },
        reset: function () {
            msgflow.onscroll = null;
        }
    };




    //joshua 登录

    this.webim_login = function () {
        webimLogin();
    }



    this.selectSession = function(){
        
    };











    // 登录 示例代码
    //js/login/login.js

//tls登录
    function tlsLogin() {
        //跳转到TLS登录页面
        TLSHelper.goLogin({
            sdkappid: loginInfo.sdkAppID,
            acctype: loginInfo.accountType,
            url: window.location.href
        });
    }
//第三方应用需要实现这个函数，并在这里拿到UserSig
    function tlsGetUserSig(res) {
        //成功拿到凭证
        if (res.ErrorCode == webim.TLS_ERROR_CODE.OK) {
            //从当前URL中获取参数为identifier的值
            loginInfo.identifier = webim.Tool.getQueryString("identifier");
            //拿到正式身份凭证
            loginInfo.userSig = res.UserSig;
            //从当前URL中获取参数为sdkappid的值
            loginInfo.sdkAppID = loginInfo.appIDAt3rd = Number(webim.Tool.getQueryString("sdkappid"));
            //从cookie获取accountType
            var accountType = webim.Tool.getCookie('accountType');
            if (accountType) {
                loginInfo.accountType = accountType;
                //sdk登录
                webimLogin();

            } else {
                alert('accountType非法');
            }
        } else {
            //签名过期，需要重新登录
            if (res.ErrorCode == webim.TLS_ERROR_CODE.SIGNATURE_EXPIRATION) {
                tlsLogin();
            } else {
                alert("[" + res.ErrorCode + "]" + res.ErrorInfo);
            }
        }
    }

//sdk登录
    function webimLogin() {
        webim.login(
                loginInfo, listeners, options,
                function (resp) {
                    loginInfo.identifierNick = resp.identifierNick;//设置当前用户昵称
                    initDemoApp();
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    }






    // 登出 示例代码
    //js/logout/logout.js
//退出
    function quitClick() {
        if (loginInfo.identifier) {
            //sdk登出
            webim.logout(
                    function (resp) {
                        loginInfo.identifier = null;
                        loginInfo.userSig = null;
                        document.getElementById("webim_demo").style.display = "none";
                        var indexUrl = window.location.href;
                        var pos = indexUrl.indexOf('?');
                        if (pos >= 0) {
                            indexUrl = indexUrl.substring(0, pos);
                        }
                        window.location.href = indexUrl;
                    }
            );
        } else {
            alert('未登录');
        }
    }

//被新实例踢下线的回调处理
    function onKickedEventCall() {
        webim.Log.error("其他地方登录，被T了");
        document.getElementById("webim_demo").style.display = "none";
    }







    // 解析一条消息 示例代码
    //js/common/show_one_msg.js
//聊天页面增加一条消息
    function addMsg(msg, prepend) {
        var isSelfSend, fromAccount, fromAccountNick, fromAccountImage, sessType, subType;


        //获取会话类型，目前只支持群聊
        //webim.SESSION_TYPE.GROUP-群聊，
        //webim.SESSION_TYPE.C2C-私聊，
        sessType = msg.getSession().type();

        isSelfSend = msg.getIsSend();//消息是否为自己发的

        fromAccount = msg.getFromAccount();
        if (!fromAccount) {
            return;
        }
        if (isSelfSend) {//如果是自己发的消息
            if (loginInfo.identifierNick) {
                fromAccountNick = loginInfo.identifierNick;
            } else {
                fromAccountNick = fromAccount;
            }
            //获取头像
            if (loginInfo.headurl) {
                fromAccountImage = loginInfo.headurl;
            } else {
                fromAccountImage = friendHeadUrl;
            }
        } else {//如果别人发的消息
            var key = webim.SESSION_TYPE.C2C + "_" + fromAccount;
            var info = infoMap[key];
            if (info && info.name) {
                fromAccountNick = info.name;
            } else {
                fromAccountNick = fromAccount;
            }
            //获取头像
            //if(info && info.image){
            //    fromAccountImage=info.image;
            //}else{
            //    fromAccountImage= friendHeadUrl;
            //}
        }

        var onemsg = document.createElement("div");
        if (msg.sending) {
            onemsg.id = "id_" + msg.random;
            //发送中
            var spinner = document.createElement("div");
            spinner.className = "spinner";
            spinner.innerHTML = '<div class="bounce1"></div> <div class="bounce2"></div> <div class="bounce3"></div>';
            onemsg.appendChild(spinner);
        } else {
            $("#id_" + msg.random).remove();
        }

        onemsg.className = "onemsg";
        var msghead = document.createElement("p");
        var msgbody = document.createElement("p");
        var msgPre = document.createElement("pre");
        msghead.className = "msghead";
        msgbody.className = "msgbody";


        //如果是发给自己的消息
        if (!isSelfSend)
            msghead.style.color = "blue";
        //昵称  消息时间
        msghead.innerHTML = webim.Tool.formatText2Html(fromAccountNick + "&nbsp;&nbsp;" + webim.Tool.formatTimeStamp(msg.getTime()));


        //解析消息

        //获取消息子类型
        //会话类型为群聊时，子类型为：webim.GROUP_MSG_SUB_TYPE
        //会话类型为私聊时，子类型为：webim.C2C_MSG_SUB_TYPE
        subType = msg.getSubType();


        switch (subType) {

            case webim.GROUP_MSG_SUB_TYPE.COMMON://群普通消息
                msgPre.innerHTML = convertMsgtoHtml(msg);
                break;
            case webim.GROUP_MSG_SUB_TYPE.REDPACKET://群红包消息
                msgPre.innerHTML = "[群红包消息]" + convertMsgtoHtml(msg);
                break;
            case webim.GROUP_MSG_SUB_TYPE.LOVEMSG://群点赞消息
                //业务自己可以增加逻辑，比如展示点赞动画效果
                msgPre.innerHTML = "[群点赞消息]" + convertMsgtoHtml(msg);
                //展示点赞动画
                //showLoveMsgAnimation();
                break;
            case webim.GROUP_MSG_SUB_TYPE.TIP://群提示消息
                msgPre.innerHTML = "[群提示消息]" + convertMsgtoHtml(msg);
                break;
        }

        msgbody.appendChild(msgPre);

        onemsg.appendChild(msghead);
        onemsg.appendChild(msgbody);
        //消息列表
        var msgflow = document.getElementsByClassName("msgflow")[0];
        if (prepend) {
            //300ms后,等待图片加载完，滚动条自动滚动到底部
            msgflow.insertBefore(onemsg, msgflow.firstChild);
            if (msgflow.scrollTop == 0) {
                setTimeout(function () {
                    msgflow.scrollTop = 0;
                }, 300);
            }
        } else {
            msgflow.appendChild(onemsg);
            //300ms后,等待图片加载完，滚动条自动滚动到底部
            setTimeout(function () {
                msgflow.scrollTop = msgflow.scrollHeight;
            }, 300);
        }


    }
//把消息转换成Html
    function convertMsgtoHtml(msg) {
        var html = "", elems, elem, type, content;
        elems = msg.getElems();//获取消息包含的元素数组
        var count = elems.length;
        for (var i = 0; i < count; i++) {
            elem = elems[i];
            type = elem.getType();//获取元素类型
            content = elem.getContent();//获取元素对象
            switch (type) {
                case webim.MSG_ELEMENT_TYPE.TEXT:
                    html += convertTextMsgToHtml(content);
                    //转义，防XSS
                    html = webim.Tool.formatText2Html(html);
                    break;
                case webim.MSG_ELEMENT_TYPE.FACE:
                    html += convertFaceMsgToHtml(content);
                    break;
                case webim.MSG_ELEMENT_TYPE.IMAGE:
                    if (i <= count - 2) {
                        var customMsgElem = elems[i + 1];//获取保存图片名称的自定义消息elem
                        var imgName = customMsgElem.getContent().getData();//业务可以自定义保存字段，demo中采用data字段保存图片文件名
                        html += convertImageMsgToHtml(content, imgName);
                        i++;//下标向后移一位
                    } else {
                        html += convertImageMsgToHtml(content);
                    }
                    break;
                case webim.MSG_ELEMENT_TYPE.SOUND:
                    html += convertSoundMsgToHtml(content);
                    break;
                case webim.MSG_ELEMENT_TYPE.FILE:
                    html += convertFileMsgToHtml(content);
                    break;
                case webim.MSG_ELEMENT_TYPE.LOCATION:
                    html += convertLocationMsgToHtml(content);
                    break;
                case webim.MSG_ELEMENT_TYPE.CUSTOM:
                    html += convertCustomMsgToHtml(content);
                    //转义，防XSS
                    html = webim.Tool.formatText2Html(html);
                    break;
                case webim.MSG_ELEMENT_TYPE.GROUP_TIP:
                    html += convertGroupTipMsgToHtml(content);
                    //转义，防XSS
                    html = webim.Tool.formatText2Html(html);
                    break;
                default:
                    webim.Log.error('未知消息元素类型: elemType=' + type);
                    break;
            }
        }
        return html;
    }

//解析文本消息元素
    function convertTextMsgToHtml(content) {
        return content.getText();
    }
//解析表情消息元素
    function convertFaceMsgToHtml(content) {
        var faceUrl = null;
        var data = content.getData();
        var index = webim.EmotionDataIndexs[data];

        var emotion = webim.Emotions[index];
        if (emotion && emotion[1]) {
            faceUrl = emotion[1];
        }
        if (faceUrl) {
            return "<img src='" + faceUrl + "'/>";
        } else {
            return data;
        }
    }
//解析图片消息元素
    function convertImageMsgToHtml(content, imageName) {
        var smallImage = content.getImage(webim.IMAGE_TYPE.SMALL);//小图
        var bigImage = content.getImage(webim.IMAGE_TYPE.LARGE);//大图
        var oriImage = content.getImage(webim.IMAGE_TYPE.ORIGIN);//原图
        if (!bigImage) {
            bigImage = smallImage;
        }
        if (!oriImage) {
            oriImage = smallImage;
        }
        return "<img name='" + imageName + "' src='" + smallImage.getUrl() + "#" + bigImage.getUrl() + "#" + oriImage.getUrl() + "' style='CURSOR: hand' id='" + content.getImageId() + "' bigImgUrl='" + bigImage.getUrl() + "' onclick='imageClick(this)' />";
    }
//解析语音消息元素
    function convertSoundMsgToHtml(content) {
        var second = content.getSecond();//获取语音时长
        var downUrl = content.getDownUrl();
        if (webim.BROWSER_INFO.type == 'ie' && parseInt(webim.BROWSER_INFO.ver) <= 8) {
            return '[这是一条语音消息]demo暂不支持ie8(含)以下浏览器播放语音,语音URL:' + downUrl;
        }
        return '<audio id="uuid_' + content.uuid + '" src="' + downUrl + '" controls="controls" onplay="onChangePlayAudio(this)" preload="none"></audio>';
    }
//解析文件消息元素
    function convertFileMsgToHtml(content) {
        var fileSize, unitStr;
        fileSize = content.getSize();
        unitStr = "Byte";
        if (fileSize >= 1024) {
            fileSize = Math.round(fileSize / 1024);
            unitStr = "KB";
        }
        // return '<a href="' + content.getDownUrl() + '" title="点击下载文件" ><i class="glyphicon glyphicon-file">&nbsp;' + content.getName() + '(' + fileSize + unitStr + ')</i></a>';

        return '<a href="javascript:;" onclick=\'webim.onDownFile("' + content.uuid + '")\' title="点击下载文件" ><i class="glyphicon glyphicon-file">&nbsp;' + content.name + '(' + fileSize + unitStr + ')</i></a>';
    }
//解析位置消息元素
    function convertLocationMsgToHtml(content) {
        return '经度=' + content.getLongitude() + ',纬度=' + content.getLatitude() + ',描述=' + content.getDesc();
    }
//解析自定义消息元素
    function convertCustomMsgToHtml(content) {
        var data = content.getData();//自定义数据
        var desc = content.getDesc();//描述信息
        var ext = content.getExt();//扩展信息
        return "data=" + data + ", desc=" + desc + ", ext=" + ext;
    }
//解析群提示消息元素
    function convertGroupTipMsgToHtml(content) {
        var WEB_IM_GROUP_TIP_MAX_USER_COUNT = 10;
        var text = "";
        var maxIndex = WEB_IM_GROUP_TIP_MAX_USER_COUNT - 1;
        var opType, opUserId, userIdList;
        var groupMemberNum;
        opType = content.getOpType();//群提示消息类型（操作类型）
        opUserId = content.getOpUserId();//操作人id
        switch (opType) {
            case webim.GROUP_TIP_TYPE.JOIN://加入群
                userIdList = content.getUserIdList();
                //text += opUserId + "邀请了";
                for (var m in userIdList) {
                    text += userIdList[m] + ",";
                    if (userIdList.length > WEB_IM_GROUP_TIP_MAX_USER_COUNT && m == maxIndex) {
                        text += "等" + userIdList.length + "人";
                        break;
                    }
                }
                text = text.substring(0, text.length - 1);
                text += "加入该群，当前群成员数：" + content.getGroupMemberNum();
                break;
            case webim.GROUP_TIP_TYPE.QUIT://退出群
                text += opUserId + "离开该群，当前群成员数：" + content.getGroupMemberNum();
                break;
            case webim.GROUP_TIP_TYPE.KICK://踢出群
                text += opUserId + "将";
                userIdList = content.getUserIdList();
                for (var m in userIdList) {
                    text += userIdList[m] + ",";
                    if (userIdList.length > WEB_IM_GROUP_TIP_MAX_USER_COUNT && m == maxIndex) {
                        text += "等" + userIdList.length + "人";
                        break;
                    }
                }
                text += "踢出该群";
                break;
            case webim.GROUP_TIP_TYPE.SET_ADMIN://设置管理员
                text += opUserId + "将";
                userIdList = content.getUserIdList();
                for (var m in userIdList) {
                    text += userIdList[m] + ",";
                    if (userIdList.length > WEB_IM_GROUP_TIP_MAX_USER_COUNT && m == maxIndex) {
                        text += "等" + userIdList.length + "人";
                        break;
                    }
                }
                text += "设为管理员";
                break;
            case webim.GROUP_TIP_TYPE.CANCEL_ADMIN://取消管理员
                text += opUserId + "取消";
                userIdList = content.getUserIdList();
                for (var m in userIdList) {
                    text += userIdList[m] + ",";
                    if (userIdList.length > WEB_IM_GROUP_TIP_MAX_USER_COUNT && m == maxIndex) {
                        text += "等" + userIdList.length + "人";
                        break;
                    }
                }
                text += "的管理员资格";
                break;

            case webim.GROUP_TIP_TYPE.MODIFY_GROUP_INFO://群资料变更
                text += opUserId + "修改了群资料：";
                var groupInfoList = content.getGroupInfoList();
                var type, value;
                for (var m in groupInfoList) {
                    type = groupInfoList[m].getType();
                    value = groupInfoList[m].getValue();
                    switch (type) {
                        case webim.GROUP_TIP_MODIFY_GROUP_INFO_TYPE.FACE_URL:
                            text += "群头像为" + value + "; ";
                            break;
                        case webim.GROUP_TIP_MODIFY_GROUP_INFO_TYPE.NAME:
                            text += "群名称为" + value + "; ";
                            break;
                        case webim.GROUP_TIP_MODIFY_GROUP_INFO_TYPE.OWNER:
                            text += "群主为" + value + "; ";
                            break;
                        case webim.GROUP_TIP_MODIFY_GROUP_INFO_TYPE.NOTIFICATION:
                            text += "群公告为" + value + "; ";
                            break;
                        case webim.GROUP_TIP_MODIFY_GROUP_INFO_TYPE.INTRODUCTION:
                            text += "群简介为" + value + "; ";
                            break;
                        default:
                            text += "未知信息为:type=" + type + ",value=" + value + "; ";
                            break;
                    }
                }
                break;

            case webim.GROUP_TIP_TYPE.MODIFY_MEMBER_INFO://群成员资料变更(禁言时间)
                text += opUserId + "修改了群成员资料:";
                var memberInfoList = content.getMemberInfoList();
                var userId, shutupTime;
                for (var m in memberInfoList) {
                    userId = memberInfoList[m].getUserId();
                    shutupTime = memberInfoList[m].getShutupTime();
                    text += userId + ": ";
                    if (shutupTime != null && shutupTime !== undefined) {
                        if (shutupTime == 0) {
                            text += "取消禁言; ";
                        } else {
                            text += "禁言" + shutupTime + "秒; ";
                        }
                    } else {
                        text += " shutupTime为空";
                    }
                    if (memberInfoList.length > WEB_IM_GROUP_TIP_MAX_USER_COUNT && m == maxIndex) {
                        text += "等" + memberInfoList.length + "人";
                        break;
                    }
                }
                break;
            default:
                text += "未知群提示消息类型：type=" + opType;
                break;
        }
        return text;
    }





    //web im demo 基本逻辑
    //js/base.js
//解决IE8之下document不支持getElementsByClassName方法
    if (!document.getElementsByClassName) {
        document.getElementsByClassName = function (className, element) {
            var children = (element || document).getElementsByTagName('*');
            var elements = new Array();
            for (var i = 0; i < children.length; i++) {
                var child = children[i];
                var classNames = child.className.split(' ');
                for (var j = 0; j < classNames.length; j++) {
                    if (classNames[j] == className) {
                        elements.push(child);
                        break;
                    }
                }
            }
            return elements;
        };
    }

//切换应用类型单选按钮事件
    function changeAppType(item) {
        var appType = item.value;
        if (appType == 1) {//测试应用
            $('#myself_type_desc').hide();
            $('#demo_type_desc').show();
            $('#sdkAppIdDiv').hide();
            $('#accountTypeDiv').hide();
            $('#accountModeDiv').hide();
        } else if (appType == 0) {//自建应用
            $('#demo_type_desc').hide();
            $('#myself_type_desc').show();
            $('#sdkAppIdDiv').show();
            $('#accountTypeDiv').show();
            $('#accountModeDiv').show();
        }
    }
    $("input[name=accountMode]").change(function () {
        accountMode = $("input[name=accountMode]:checked").val();
        console.debug(accountMode);
    });
//选择应用类型
    function selectApp() {
        var appType = $('input[name="app_type_radio"]:checked').val();
        if (appType == 1) {//测试应用
            loginInfo.sdkAppID = loginInfo.appIDAt3rd = sdkAppID;
            loginInfo.accountType = accountType;
        } else if (appType == 0) {//自建应用
            if ($("#sdk_app_id").val().length == 0) {
                alert('请输入sdkAppId');
                return;
            }
            if (!validNumber($("#sdk_app_id").val())) {
                alert('sdkAppId非法,只能输入数字');
                return;
            }
            if ($("#account_type").val().length == 0) {
                alert('请输入accountType');
                return;
            }
            if (!validNumber($("#account_type").val())) {
                alert('accountType非法,只能输入数字');
                return;
            }
            loginInfo.sdkAppID = loginInfo.appIDAt3rd = $('#sdk_app_id').val();
            loginInfo.accountType = $('#account_type').val();
        }
        //将account_type保存到cookie中,有效期是1天
        webim.Tool.setCookie('accountType', loginInfo.accountType, 3600 * 24);
        $('#select_app_dialog').modal('hide');

        if (accountMode == 1) {
            //调用tls登录服务
            tlsLogin();
        } else {
            $('#login_dialog').modal('show');
        }
    }

//弹出登录框
    function showLoginDialog() {
        $('#select_app_dialog').modal('hide');
        //显示登录窗体
        $('#login_dialog').modal('show');
        $("#login_account").focus();
    }

//点击登录按钮(独立模式)
    function independentModeLogin() {
        if ($("#login_account").val().length == 0) {
            alert('请输入帐号');
            return;
        }
        if ($("#login_pwd").val().length == 0) {
            alert('请输入UserSig');
            return;
        }
        loginInfo.identifier = $('#login_account').val();
        loginInfo.userSig = $('#login_pwd').val();
        $('#login_dialog').modal('hide');
        webimLogin();
    }

//初始化demo
    function initDemoApp() {
        //$("body").css("background-color", '#2f2f2f');
        //document.getElementById("webim_demo").style.display = "block";//展开聊天界面
        //document.getElementById("p_my_face").src = loginInfo.headurl;
        if (loginInfo.identifierNick) {
            $rootScope.myinfo.name = webim.Tool.formatText2Html(loginInfo.identifierNick);
        } else {
            $rootScope.myinfo.name = webim.Tool.formatText2Html(loginInfo.identifier);
        }

        //菜单
        //$("#t_my_menu").menu();

        //$("#send_msg_text").focus();
        //初始化我的加群申请表格
        //initGetApplyJoinGroupPendency([]);
        //初始化我的群组系统消息表格
        //initGetMyGroupSystemMsgs([]);
        //初始化我的好友系统消息表格
        //initGetMyFriendSystemMsgs([]);
        //初始化我的资料系统消息表格
        //initGetMyProfileSystemMsgs([]);

        //初始化好友和群信息
        initInfoMap(initInfoMapCallbackOK);

    }

    function initInfoMap(cbOk) {
        //读取我的好友列表
        initInfoMapByMyFriends(
                //读取我的群组列表
                initInfoMapByMyGroups(
                        cbOk
                        )
                );
    }

    function initInfoMapCallbackOK() {
        initRecentContactList(initRecentContactListCallbackOK);
    }

//初始化我的最近会话列表框回调函数
    function initRecentContactListCallbackOK() {
        onSelSess(selType, selToID);

    }

//判断str是否只包含数字
    function validNumber(str) {
        if (!str) {
            str = str.toString();
            return str.match(/(^\d+$)/g);
        } else {
            return str;
        }
    }



    function onAppliedDownloadUrl(data) {
        console.debug(data);
    }







    // 资料管理 api 示例代码
    //js/profile/profile_manager.js
//搜索用户菜单点击事件
    function searchProfileByUserIdClick() {
        $('#sp_form')[0].reset();
        initSearchProfileTable([]);
        $('#search_profile_table').bootstrapTable('load', []);
        $('#search_profile_dialog').modal('show');

    }
//设置个人资料菜单点击事件
    function setProfilePortraitClick() {

        //重置表单
        $('#spp_form')[0].reset();

        var tag_list = [
            "Tag_Profile_IM_Nick",
            "Tag_Profile_IM_Gender",
            "Tag_Profile_IM_AllowType",
            "Tag_Profile_IM_Image"
        ];
        var options = {
            'To_Account': [loginInfo.identifier],
            'TagList': tag_list
        };

        webim.getProfilePortrait(
                options,
                function (resp) {
                    if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
                        for (var i in resp.UserProfileItem) {
                            var nick, gender, allowType, image;
                            for (var j in resp.UserProfileItem[i].ProfileItem) {
                                switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
                                    case 'Tag_Profile_IM_Nick':
                                        nick = resp.UserProfileItem[i].ProfileItem[j].Value;
                                        break;
                                    case 'Tag_Profile_IM_Gender':
                                        gender = resp.UserProfileItem[i].ProfileItem[j].Value;
                                        break;
                                    case 'Tag_Profile_IM_AllowType':
                                        allowType = resp.UserProfileItem[i].ProfileItem[j].Value;
                                        break;
                                    case 'Tag_Profile_IM_Image':
                                        image = resp.UserProfileItem[i].ProfileItem[j].Value;
                                        break;
                                }
                            }
                            $("#spp_image").val(image);
                            //
                            $("#spp_nick").val(nick);
                            //$("input[type=radio][name='spp_gender_radio'][value=" + gender + "]").attr("checked", "checked");//此方法，在IE浏览器下，第一次查看个人资料时，选中没有效果
                            var spp_gender_radios = document.spp_form.spp_gender_radio;
                            for (var i = 0; i < spp_gender_radios.length; i++) {
                                if (spp_gender_radios[i].value == gender) {
                                    spp_gender_radios[i].checked = true;
                                    break;
                                }
                            }
                            //$("input[type=radio][name='spp_allow_type_radio'][value=" + allowType + "]").attr("checked", "checked");
                            var spp_allow_type_radio = document.spp_form.spp_allow_type_radio;
                            for (var i = 0; i < spp_allow_type_radio.length; i++) {
                                if (spp_allow_type_radio[i].value == allowType) {
                                    spp_allow_type_radio[i].checked = true;
                                    break;
                                }
                            }

                        }
                    }
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
        $('#set_profile_portrait_dialog').modal('show');
    }
//定义搜索用户表格每行按钮
    function spOperateFormatter(value, row, index) {
        return [
            '<a class="plus" href="javascript:void(0)" title="添加好友">',
            '<i class="glyphicon glyphicon-plus"></i>',
            '</a>'

        ].join('');
    }
//搜索用户表格每行按钮单击事件
    window.spOperateEvents = {
        'click .plus': function (e, value, row, index) {

            $('#af_to_account').val(row.To_Account);

            //重新获取对方的加好友方式
            searchProfileAllowTypeByUserId(row.To_Account, function (allowType) {

                $('#af_allow_type').val(allowType);
                webim.Log.info(allowType);
                if (allowType == null) {
                    alert('获取对方加好友设置失败');
                    return;
                }
                if (allowType == '拒绝任何人') {
                    alert('对方拒绝任何人加好友，无法加对方为好友');
                    return;
                }

                if (allowType == '允许任何人') {
                    applyAddFriend();
                } else {
                    $("#af_add_wording").val('你好，我想和你成为朋友~~');
                    $('#add_friend_dialog').modal('show');
                }
            }, function (errmsg) {
                alert(errmsg);
            });

        }
    };
//初始化搜索用户表格
    function initSearchProfileTable(data) {
        $('#search_profile_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "To_Account", title: "账号", align: "center", valign: "middle", sortable: "true"},
                {field: "Nick", title: "昵称", align: "center", valign: "middle", sortable: "true"},
                {field: "Gender", title: "性别", align: "center", valign: "middle", sortable: "true"},
                {field: "AllowType", title: "加好友设置", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "Image", title: "头像地址", align: "center", valign: "middle", sortable: "true", visible: false},
                {
                    field: "spOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "spOperateFormatter",
                    events: "spOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }
//搜索用户
    var searchProfileByUserId = function () {

        if ($("#sp_to_account").val().length == 0) {
            alert('请输入用户ID');
            return;
        }

        if (webim.Tool.trimStr($("#sp_to_account").val()).length == 0) {
            alert('您输入的用户ID全是空格,请重新输入');
            return;
        }

        var tag_list = [
            "Tag_Profile_IM_Nick", //昵称
            "Tag_Profile_IM_Gender", //性别
            "Tag_Profile_IM_AllowType", //加好友方式
            "Tag_Profile_IM_Image"//头像
        ];
        var options = {
            'To_Account': [$("#sp_to_account").val()],
            'TagList': tag_list
        };

        webim.getProfilePortrait(
                options,
                function (resp) {
                    var data = [];
                    if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
                        for (var i in resp.UserProfileItem) {
                            var to_account = resp.UserProfileItem[i].To_Account;
                            var nick = null, gender = null, allowType = null, imageUrl = null;
                            for (var j in resp.UserProfileItem[i].ProfileItem) {
                                switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
                                    case 'Tag_Profile_IM_Nick':
                                        nick = resp.UserProfileItem[i].ProfileItem[j].Value;
                                        break;
                                    case 'Tag_Profile_IM_Gender':
                                        switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
                                            case 'Gender_Type_Male':
                                                gender = '男';
                                                break;
                                            case 'Gender_Type_Female':
                                                gender = '女';
                                                break;
                                            case 'Gender_Type_Unknown':
                                                gender = '未知';
                                                break;
                                        }
                                        break;
                                    case 'Tag_Profile_IM_AllowType':
                                        switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
                                            case 'AllowType_Type_AllowAny':
                                                allowType = '允许任何人';
                                                break;
                                            case 'AllowType_Type_NeedConfirm':
                                                allowType = '需要确认';
                                                break;
                                            case 'AllowType_Type_DenyAny':
                                                allowType = '拒绝任何人';
                                                break;
                                            default:
                                                allowType = '需要确认';
                                                break;
                                        }
                                        break;
                                    case 'Tag_Profile_IM_Image':
                                        imageUrl = resp.UserProfileItem[i].ProfileItem[j].Value;
                                        break;
                                }
                            }
                            data.push({
                                'To_Account': webim.Tool.formatText2Html(to_account),
                                'Nick': webim.Tool.formatText2Html(nick),
                                'Gender': gender,
                                'AllowType': allowType,
                                'Image': imageUrl
                            });
                        }
                    }
                    $('#search_profile_table').bootstrapTable('load', data);
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//搜索用户的加好友设置项
    var searchProfileAllowTypeByUserId = function (to_account, cbok, cberr) {

        var allowType = '需要确认';
        if (to_account.length == 0) {
            if (cberr) {
                cberr('对方帐号为空');
                return;
            }
        }
        var tag_list = [
            "Tag_Profile_IM_AllowType"
        ];
        var options = {
            'To_Account': [to_account],
            'TagList': tag_list
        };

        webim.getProfilePortrait(
                options,
                function (resp) {
                    if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
                        for (var i in resp.UserProfileItem) {
                            for (var j in resp.UserProfileItem[i].ProfileItem) {
                                switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
                                    case 'Tag_Profile_IM_AllowType':
                                        switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
                                            case 'AllowType_Type_AllowAny':
                                                allowType = '允许任何人';
                                                break;
                                            case 'AllowType_Type_NeedConfirm':
                                                allowType = '需要确认';
                                                break;
                                            case 'AllowType_Type_DenyAny':
                                                allowType = '拒绝任何人';
                                                break;
                                            default:
                                                allowType = '需要确认';
                                                break;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    if (cbok) {
                        cbok(allowType);
                    }
                },
                function (errmsg) {
                    if (cberr) {
                        cberr(errmsg);
                    }
                }
        );

    };

//设置个人资料
    var setProfilePortrait = function () {

        var image = $("#spp_image").val();

        if ($("#spp_nick").val().length == 0) {
            alert('请输入昵称');
            return;
        }
        if (webim.Tool.trimStr($("#spp_nick").val()).length == 0) {
            alert('您输入的昵称全是空格,请重新输入');
            return;
        }
        var gender = $('input[name="spp_gender_radio"]:checked').val();
        if (!gender) {
            alert('请选择性别');
            return;
        }
        var profile_item = [
            {
                "Tag": "Tag_Profile_IM_Nick",
                "Value": $("#spp_nick").val()
            },
            {
                "Tag": "Tag_Profile_IM_Gender",
                "Value": $('input[name="spp_gender_radio"]:checked').val()
            },
            {
                "Tag": "Tag_Profile_IM_AllowType",
                "Value": $('input[name="spp_allow_type_radio"]:checked').val()
            }
        ];
        if (image) {//如果设置了头像URL
            profile_item.push(
                    {
                        "Tag": "Tag_Profile_IM_Image",
                        "Value": image
                    }
            );
        }
        var options = {
            'ProfileItem': profile_item
        };

        webim.setProfilePortrait(
                options,
                function (resp) {
                    $('#set_profile_portrait_dialog').modal('hide');
                    loginInfo.identifierNick = $("#spp_nick").val();//更新昵称
                    document.getElementById("t_my_name").innerHTML = webim.Tool.formatText2Html(loginInfo.identifierNick);
                    alert('设置个人资料成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };





    // 好友管理 api 示例代码
    //js/friend/friend_manager.js
//定义我的好友表格每行的操作按钮
    function gmfOperateFormatter(value, row, index) {
        return [
            '<a class="envelope" href="javascript:void(0)" title="发消息">',
            '<i class="glyphicon glyphicon-envelope"></i>',
            '</a>',
            '<a class="ban-circle" href="javascript:void(0)" title="拉黑">',
            '<i class="glyphicon glyphicon-ban-circle"></i>',
            '</a>',
            '<a class="remove ml10" href="javascript:void(0)" title="删除">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'

        ].join('');
    }
//我的好友表格每行的操作按钮点击事件
    window.gmfOperateEvents = {
        'click .envelope': function (e, value, row, index) {
            $('#scm_to_account').val(row.Info_Account);
            $('#send_c2c_msg_dialog').modal('show');
        },
        'click .ban-circle': function (e, value, row, index) {
            if (confirm("确定将该好友拉黑吗？")) {
                addBlackList(row.Info_Account);
            }
        },
        'click .remove': function (e, value, row, index) {
            $('#df_to_account').val(row.Info_Account);
            $('#delete_friend_dialog').modal('show');
        }
    };
//初始化我的好友表格
    function initGetMyFriendTable(data) {
        $('#get_my_friend_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            sidePagination: 'client',
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "Info_Account", title: "账号", align: "center", valign: "middle", sortable: "true"},
                {field: "Nick", title: "昵称", align: "center", valign: "middle", sortable: "true"},
                {field: "Gender", title: "性别", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "gmfOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "gmfOperateFormatter",
                    events: "gmfOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }
//申请加好友
    var applyAddFriend = function () {

        var len = webim.Tool.getStrBytes($("#af_add_wording").val());
        if (len > 120) {
            alert('您输入的附言超过字数限制(最长40个汉字)');
            return;
        }
        var add_friend_item = [
            {
                'To_Account': $("#af_to_account").val(),
                "AddSource": "AddSource_Type_Unknow",
                "AddWording": $("#af_add_wording").val() //加好友附言，可为空
            }
        ];
        var options = {
            'From_Account': loginInfo.identifier,
            'AddFriendItem': add_friend_item
        };

        webim.applyAddFriend(
                options,
                function (resp) {
                    if (resp.Fail_Account && resp.Fail_Account.length > 0) {
                        for (var i in resp.ResultItem) {
                            alert(resp.ResultItem[i].ResultInfo);
                            break;
                        }
                    } else {

                        if ($('#af_allow_type').val() == '允许任何人') {
                            //重新加载好友列表
                            //getAllFriend(getAllFriendsCallbackOK);
                            alert('添加好友成功');
                        } else {
                            $('#add_friend_dialog').modal('hide');
                            alert('申请添加好友成功');
                        }

                    }
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//删除好友
    var deleteFriend = function () {

        if (!confirm("确定删除该好友吗？")) {
            return;
        }

        var to_account = [];

        to_account = [
            $("#df_to_account").val()
        ];

        if (to_account.length <= 0) {
            return;
        }
        var options = {
            'From_Account': loginInfo.identifier,
            'To_Account': to_account,
            //Delete_Type_Both'//单向删除："Delete_Type_Single", 双向删除："Delete_Type_Both".
            'DeleteType': $('input[name="df_type_radio"]:checked').val()
        };

        webim.deleteFriend(
                options,
                function (resp) {
                    //在表格中删除对应的行
                    $('#get_my_friend_table').bootstrapTable('remove', {
                        field: 'Info_Account',
                        values: [$("#df_to_account").val()]
                    });
                    //重新加载好友列表
                    //getAllFriend(getAllFriendsCallbackOK);
                    deleteSessDiv(webim.SESSION_TYPE.C2C, $("#df_to_account").val());//在最近联系人列表中删除好友(如果存在的话)
                    $('#delete_friend_dialog').modal('hide');
                    alert('删除好友成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );

    };
//获取我的好友
    var getMyFriend = function () {

        //清空
        initGetMyFriendTable([]);

        var options = {
            'From_Account': loginInfo.identifier,
            'TimeStamp': 0,
            'StartIndex': 0,
            'GetCount': totalCount,
            'LastStandardSequence': 0,
            "TagList": [
                "Tag_Profile_IM_Nick",
                "Tag_SNS_IM_Remark",
                "Tag_Profile_IM_Gender"
            ]
        };

        webim.getAllFriend(
                options,
                function (resp) {

                    if (resp.FriendNum > 0) {

                        var table_friends_data = [];
                        var friends = resp.InfoItem;
                        if (!friends || friends.length == 0) {
                            alert('你目前还没有好友');
                            return;
                        }

                        var count = friends.length;

                        for (var i = 0; i < count; i++) {
                            var friend_name = friends[i].Info_Account;
                            var gender = "未知";
                            if (friends[i].SnsProfileItem) {
                                for (var j in friends[i].SnsProfileItem) {
                                    switch (friends[i].SnsProfileItem[j].Tag) {
                                        case 'Tag_Profile_IM_Nick':
                                            friend_name = friends[i].SnsProfileItem[j].Value;
                                            break;
                                        case 'Tag_Profile_IM_Gender':
                                            switch (friends[i].SnsProfileItem[j].Value) {
                                                case 'Gender_Type_Male':
                                                    gender = '男';
                                                    break;
                                                case 'Gender_Type_Female':
                                                    gender = '女';
                                                    break;
                                                case 'Gender_Type_Unknown':
                                                    gender = '未知';
                                                    break;
                                            }
                                            break;
                                    }
                                }
                            }
                            table_friends_data.push({
                                'Info_Account': friends[i].Info_Account,
                                'Nick': webim.Tool.formatText2Html(friend_name),
                                'Gender': gender
                            });
                        }

                        //打开我的好友列表对话框
                        $('#get_my_friend_table').bootstrapTable('load', table_friends_data);
                        $('#get_my_friend_dialog').modal('show');
                    } else {
                        alert('您目前还没有好友');
                    }

                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//从我的好友列表中给好友发消息
    function sendC2cMsg() {

        toAccount = $("#scm_to_account").val();
        msgtosend = $("#scm_content").val();

        var msgLen = webim.Tool.getStrBytes(msgtosend);

        var maxLen, errInfo;

        maxLen = webim.MSG_MAX_LENGTH.C2C;
        errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";

        if (msgtosend.length < 1) {
            alert("发送的消息不能为空!");
            $("#send_msg_text").val('');
            return;
        }

        if (msgLen > maxLen) {
            alert(errInfo);
            return;
        }

        var sess = webim.MsgStore.sessByTypeId(webim.SESSION_TYPE.C2C, toAccount);
        if (!sess) {
            sess = new webim.Session(webim.SESSION_TYPE.C2C, toAccount, toAccount, friendHeadUrl, Math.round(new Date().getTime() / 1000));
        }
        var isSend = true;//是否为自己发送
        var seq = -1;//消息序列，-1表示sdk自动生成，用于去重
        var random = Math.round(Math.random() * 4294967296);//消息随机数，用于去重
        var msgTime = Math.round(new Date().getTime() / 1000);//消息时间戳
        var subType;//消息子类型

        subType = webim.C2C_MSG_SUB_TYPE.COMMON;

        var msg = new webim.Msg(sess, isSend, seq, random, msgTime, loginInfo.identifier, subType, loginInfo.identifierNick);

        var text_obj;

        text_obj = new webim.Msg.Elem.Text(msgtosend);
        msg.addText(text_obj);

        webim.sendMsg(msg, function (resp) {


            if (!selToID) {//没有聊天会话
                selType = webim.SESSION_TYPE.C2C;
                selToID = toAccount;
                selSess = sess;
                addSess(selType, toAccount, toAccount, friendHeadUrl, 0, 'sesslist');
                setSelSessStyleOn(toAccount);
                //私聊时，在聊天窗口手动添加一条发的消息，群聊时，长轮询接口会返回自己发的消息
                addMsg(msg);
            } else {//有聊天会话
                if (selToID == toAccount) {//聊天对象不变
                    addMsg(msg);
                } else {//聊天对象发生改变
                    var tempSessDiv = document.getElementById("sessDiv_" + toAccount);
                    if (!tempSessDiv) {//不存在这个会话
                        addSess(webim.SESSION_TYPE.C2C, toAccount, toAccount, friendHeadUrl, 0, 'sesslist');//增加一个会话
                    }

                    onSelSess(webim.SESSION_TYPE.C2C, toAccount);//再进行切换
                }
            }
            webim.Tool.setCookie("tmpmsg_" + toAccount, '', 0);
            $("#scm_content").val('');
            $('#send_c2c_msg_dialog').modal('hide');
            $('#get_my_friend_dialog').modal('hide');

        }, function (err) {
            alert(err.ErrorInfo);
            $("#scm_content").val('');
        });
    }

//将我的好友资料（昵称和头像）保存在infoMap
    var initInfoMapByMyFriends = function (cbOK) {

        var options = {
            'From_Account': loginInfo.identifier,
            'TimeStamp': 0,
            'StartIndex': 0,
            'GetCount': totalCount,
            'LastStandardSequence': 0,
            "TagList": [
                "Tag_Profile_IM_Nick",
                "Tag_Profile_IM_Image"
            ]
        };

        webim.getAllFriend(
                options,
                function (resp) {
                    if (resp.FriendNum > 0) {

                        var friends = resp.InfoItem;
                        if (!friends || friends.length == 0) {
                            if (cbOK)
                                cbOK();
                            return;
                        }
                        var count = friends.length;

                        for (var i = 0; i < count; i++) {
                            var friend = friends[i];
                            var friend_account = friend.Info_Account;
                            var friend_name = friend_image = '';
                            for (var j in friend.SnsProfileItem) {
                                switch (friend.SnsProfileItem[j].Tag) {
                                    case 'Tag_Profile_IM_Nick':
                                        friend_name = friend.SnsProfileItem[j].Value;
                                        break;
                                    case 'Tag_Profile_IM_Image':
                                        friend_image = friend.SnsProfileItem[j].Value;
                                        break;
                                }
                            }
                            var key = webim.SESSION_TYPE.C2C + "_" + friend_account;
                            infoMap[key] = {
                                'name': friend_name,
                                'image': friend_image
                            };
                        }
                        if (cbOK)
                            cbOK();
                    }
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };






    // 好友申请管理 api 示例代码
    //js/friend/friend_pendency_manager.js
//定义我的好友申请表格每行按钮
    function gpOperateFormatter(value, row, index) {
        return [
            '<a class="edit" href="javascript:void(0)" title="处理">',
            '<i class="glyphicon glyphicon-edit"></i>',
            '</a>',
            '<a class="remove ml10" href="javascript:void(0)" title="删除">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'

        ].join('');
    }
//我的好友申请表格每行的按钮点击事件
    window.gpOperateEvents = {
        'click .edit': function (e, value, row, index) {

            $("#rf_to_account").val(row.To_Account);
            $('#response_friend_dialog').modal('show');

        },
        'click .remove': function (e, value, row, index) {
            if (confirm("确定删除这条记录吗？")) {
                deletePendency(row.To_Account);
            }

        }
    };
//初始化我的好友申请表格
    function initGetPendencyTable(data) {
        $('#get_pendency_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "To_Account", title: "对方账号", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "Nick", title: "对方昵称", align: "center", valign: "middle", sortable: "true"},
                {field: "AddWording", title: "附言", align: "center", valign: "middle", sortable: "true"},
                {field: "AddSource", title: "来源", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "AddTime", title: "申请时间", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "gpOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "gpOperateFormatter",
                    events: "gpOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }
//读取好友申请列表
//notifyFlag： true表示来自好友系统通知触发，false表示用户主动点击【获取好友申请】菜单
    var getPendency = function (notifyFlag) {
        initGetPendencyTable([]);
        var options = {
            //请求发起方的帐号，一般情况下为用户本人帐号，在管理员代替用户发起请求的情况下，这里应该填写被代替的用户的帐号
            'From_Account': loginInfo.identifier,
            //Pendency_Type_ComeIn: 别人发给我的; Pendency_Type_SendOut: 我发给别人的
            'PendencyType': 'Pendency_Type_ComeIn',
            //好友申请的起始时间
            'StartTime': 0,
            //分页大小，如果取值为30 则表示客户端要求服务器端每页最多返回30个好友申请
            'MaxLimited': totalCount,
            //好友申请数据的版本号，用户每收到或删除一条好友申请，服务器端就自增一次好友申请数据版本号，一般传0，表示拉取最新的数据
            'LastSequence': 0
        };

        webim.getPendency(
                options,
                function (resp) {
                    var data = [];
                    if (resp.UnreadPendencyCount > 0) {//存在未读未决
                        for (var i in resp.PendencyItem) {
                            var apply_time = webim.Tool.formatTimeStamp(resp.PendencyItem[i].AddTime);
                            var nick = webim.Tool.formatText2Html(resp.PendencyItem[i].Nick);
                            if (nick == '') {
                                nick = resp.PendencyItem[i].To_Account;
                            }
                            var addWording = webim.Tool.formatText2Html(resp.PendencyItem[i].AddWording);
                            data.push({
                                To_Account: webim.Tool.formatText2Html(resp.PendencyItem[i].To_Account),
                                Nick: nick,
                                AddWording: addWording,
                                AddSource: resp.PendencyItem[i].AddSource,
                                AddTime: apply_time
                            });
                        }
                        $('#get_pendency_table').bootstrapTable('load', data);
                        $('#get_pendency_dialog').modal('show');
                    } else {
                        if (!notifyFlag) {
                            alert('没有加好友申请');
                        }
                    }

                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//删除申请列表
    var deletePendency = function (del_account) {

        var options = {
            'From_Account': loginInfo.identifier,
            'PendencyType': 'Pendency_Type_ComeIn',
            'To_Account': [del_account]

        };

        webim.deletePendency(
                options,
                function (resp) {

                    //在表格中删除对应的行
                    $('#get_pendency_table').bootstrapTable('remove', {
                        field: 'To_Account',
                        values: [del_account]
                    });
                    alert('删除好友申请成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };
//处理好友申请
    var responseFriend = function () {

        var response_friend_item = [
            {
                'To_Account': $("#rf_to_account").val(),
                "ResponseAction": $('input[name="rf_action_radio"]:checked').val()//类型：Response_Action_Agree 或者 Response_Action_AgreeAndAdd
            }
        ];
        var options = {
            'From_Account': loginInfo.identifier,
            'ResponseFriendItem': response_friend_item
        };

        webim.responseFriend(
                options,
                function (resp) {
                    //在表格中删除对应的行
                    $('#get_pendency_table').bootstrapTable('remove', {
                        field: 'To_Account',
                        values: [$("#rf_to_account").val()]
                    });
                    $('#response_friend_dialog').modal('hide');

                    if (response_friend_item[0].ResponseAction == 'Response_Action_AgreeAndAdd') {
                        //getAllFriend(getAllFriendsCallbackOK);
                    }

                    alert('处理好友申请成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );

    };







    // 好友黑名单管理 api 示例代码
    //js/friend/friend_black_list_manager.js
//定义我的黑名单表格每行的操作按钮
    function gblOperateFormatter(value, row, index) {
        return [
            '<a class="remove ml10" href="javascript:void(0)" title="删除">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'

        ].join('');
    }
//定义我的黑名单表格每行的操作按钮点击事件
    window.gblOperateEvents = {
        'click .remove': function (e, value, row, index) {
            if (confirm("确定将该联系人从黑名单删除吗？")) {
                deleteBlackList(row.To_Account);
            }
        }
    };
//初始化我的黑名单表格
    function initGetBlackListTable(data) {
        $('#get_black_list_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "To_Account", title: "账号", align: "center", valign: "middle", sortable: "true"},
                {field: "AddBlackTimeStamp", title: "操作时间", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "operate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "gblOperateFormatter",
                    events: "gblOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }
//添加黑名单
    var addBlackList = function (add_account) {

        var to_account = [];
        if (add_account) {
            to_account = [add_account];
        }
        if (0 == to_account.length) {
            alert('需要拉黑的帐号为空');
            return;
        }
        var options = {
            'From_Account': loginInfo.identifier,
            'To_Account': to_account
        };

        webim.addBlackList(
                options,
                function (resp) {

                    //在表格中删除对应的行
                    $('#get_my_friend_table').bootstrapTable('remove', {
                        field: 'Info_Account',
                        values: [add_account]
                    });
                    //重新加载好友列表
                    //getAllFriend(getAllFriendsCallbackOK);
                    deleteSessDiv(webim.SESSION_TYPE.C2C, add_account);//在最近联系人列表中删除对应拉黑的好友(如果存在的话)
                    alert('添加黑名单成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//删除黑名单
    var deleteBlackList = function (del_account) {

        var to_account = [
            del_account
        ];
        var options = {
            'From_Account': loginInfo.identifier,
            'To_Account': to_account
        };

        webim.deleteBlackList(
                options,
                function (resp) {
                    //在表格中删除对应的行
                    $('#get_black_list_table').bootstrapTable('remove', {
                        field: 'To_Account',
                        values: to_account
                    });

                    alert('删除黑名单成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//我的黑名单
    var getBlackList = function (cbOK, cbErr) {
        initGetBlackListTable([]);
        var options = {
            'From_Account': loginInfo.identifier,
            'StartIndex': 0,
            'MaxLimited': totalCount,
            'LastSequence': 0
        };

        webim.getBlackList(
                options,
                function (resp) {
                    var data = [];
                    if (resp.BlackListItem && resp.BlackListItem.length > 0) {

                        for (var i in resp.BlackListItem) {
                            var add_time = webim.Tool.formatTimeStamp(resp.BlackListItem[i].AddBlackTimeStamp);
                            data.push({
                                To_Account: webim.Tool.formatText2Html(resp.BlackListItem[i].To_Account),
                                AddBlackTimeStamp: add_time
                            });
                        }
                    }
                    $('#get_black_list_table').bootstrapTable('load', data);
                    $('#get_black_list_dialog').modal('show');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };





    // 最近联系人 api 示例代码
    //js/recentcontact/recent_contact_list_manager.js
//初始化我的最近会话表格
    function initGetRecentContactListTable(data) {
        $('#get_recent_contact_list_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "SessionType", title: "会话类型(英文)", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "SessionTypeZh", title: "会话类型", align: "center", valign: "middle", sortable: "true"},
                {field: "SessionId", title: "会话ID", align: "center", valign: "middle", sortable: "true"},
                {field: "SessionNick", title: "会话昵称", align: "center", valign: "middle", sortable: "true"},
                {field: "SessionImage", title: "会话头像", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "C2cAccount", title: "发送者ID", align: "center", valign: "middle", sortable: "true"},
                {field: "C2cNick", title: "发送者昵称", align: "center", valign: "middle", sortable: "true"},
                {field: "UnreadMsgCount", title: "未读数", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgSeq", title: "Seq", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgRandom", title: "Random", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgTimeStamp", title: "时间", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgShow", title: "内容", align: "center", valign: "middle", sortable: "true"}
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }

//我的最近联系人
    var getRecentContactList = function () {
        initGetRecentContactListTable([]);
        var options = {
            'Count': reqRecentSessCount//最近的会话数
        };
        webim.getRecentContactList(
                options,
                function (resp) {
                    var data = [];
                    var tempSess, tempSessMap = {};//临时会话变量
                    if (resp.SessionItem && resp.SessionItem.length > 0) {

                        for (var i in resp.SessionItem) {
                            var item = resp.SessionItem[i];
                            var type = item.Type;//接口返回的会话类型
                            var sessType, typeZh, sessionId, sessionNick = '', sessionImage = '', senderId = '', senderNick = '';
                            if (type == webim.RECENT_CONTACT_TYPE.C2C) {//私聊
                                typeZh = '私聊';
                                sessType = webim.SESSION_TYPE.C2C;//设置会话类型
                                sessionId = item.To_Account;//会话id，私聊时为好友ID或者系统账号（值为@TIM#SYSTEM，业务可以自己决定是否需要展示），注意：从To_Account获取,

                                if (sessionId == '@TIM#SYSTEM') {//先过滤系统消息，，
                                    webim.Log.warn('过滤好友系统消息,sessionId=' + sessionId);
                                    continue;
                                }
                                var key = sessType + "_" + sessionId;
                                var c2cInfo = infoMap[key];
                                if (c2cInfo && c2cInfo.name) {//从infoMap获取c2c昵称
                                    sessionNick = c2cInfo.name;//会话昵称，私聊时为好友昵称，接口暂不支持返回，需要业务自己获取（前提是用户设置过自己的昵称，通过拉取好友资料接口（支持批量拉取）得到）
                                } else {//没有找到或者没有设置过
                                    sessionNick = sessionId;//会话昵称，如果昵称为空，默认将其设成会话id
                                }
                                if (c2cInfo && c2cInfo.image) {//从infoMap获取c2c头像
                                    sessionImage = c2cInfo.image;//会话头像，私聊时为好友头像，接口暂不支持返回，需要业务自己获取（前提是用户设置过自己的昵称，通过拉取好友资料接口（支持批量拉取）得到）
                                } else {//没有找到或者没有设置过
                                    sessionImage = friendHeadUrl;//会话头像，如果为空，默认将其设置demo自带的头像
                                }
                                senderId = senderNick = '';//私聊时，这些字段用不到，直接设置为空

                            } else if (type == webim.RECENT_CONTACT_TYPE.GROUP) {//群聊
                                typeZh = '群聊';
                                sessType = webim.SESSION_TYPE.GROUP;//设置会话类型
                                sessionId = item.ToAccount;//会话id，群聊时为群ID，注意：从ToAccount获取
                                sessionNick = item.GroupNick;//会话昵称，群聊时，为群名称，接口一定会返回

                                if (item.GroupImage) {//优先考虑接口返回的群头像
                                    sessionImage = item.GroupImage;//会话头像，群聊时，群头像，如果业务设置过群头像（设置群头像请参考wiki文档-设置群资料接口），接口会返回
                                } else {//接口没有返回或者没有设置过群头像，再从infoMap获取群头像
                                    var key = sessType + "_" + sessionId;
                                    var groupInfo = infoMap[key];
                                    if (groupInfo && groupInfo.image) {//
                                        sessionImage = groupInfo.image
                                    } else {//不存在或者没有设置过，则使用默认头像
                                        sessionImage = groupHeadUrl;//会话头像，如果没有设置过群头像，默认将其设置demo自带的头像
                                    }
                                }
                                senderId = item.MsgGroupFrom_Account;//群消息的发送者id

                                if (!senderId) {//发送者id为空
                                    webim.Log.warn('群消息发送者id为空,senderId=' + senderId + ",groupid=" + sessionId);
                                    continue;
                                }
                                if (senderId == '@TIM#SYSTEM') {//先过滤群系统消息，因为接口暂时区分不了是进群还是退群等提示消息，
                                    webim.Log.warn('过滤群系统消息,senderId=' + senderId + ",groupid=" + sessionId);
                                    continue;
                                }

                                senderNick = item.MsgGroupFromCardName;//优先考虑群成员名片
                                if (!senderNick) {//如果没有设置群成员名片
                                    senderNick = item.MsgGroupFromNickName;//再考虑接口是否返回了群成员昵称
                                    if (!senderNick) {//如果接口没有返回昵称或者没有设置群昵称，从infoMap获取昵称
                                        var key = webim.SESSION_TYPE.C2C + "_" + senderId;
                                        var c2cInfo = infoMap[key];
                                        if (c2cInfo && c2cInfo.name) {
                                            senderNick = c2cInfo.name;//发送者群昵称
                                        } else {
                                            sessionNick = senderId;//如果昵称为空，默认将其设成发送者id
                                        }
                                    }
                                }

                            } else {
                                typeZh = '未知类型';
                                sessionId = item.ToAccount;//
                            }

                            if (!sessionId) {//会话id为空
                                webim.Log.warn('会话id为空,sessionId=' + sessionId);
                                continue;
                            }

                            if (sessionId == '@TLS#NOT_FOUND') {//会话id不存在，可能是已经被删除了
                                webim.Log.warn('会话id不存在,sessionId=' + sessionId);
                                continue;
                            }

                            if (sessionNick.length > maxNameLen) {//帐号或昵称过长，截取一部分，出于demo需要，业务可以自己决定
                                sessionNick = sessionNick.substr(0, maxNameLen) + "...";
                            }

                            tempSess = tempSessMap[sessType + "_" + sessionId];
                            if (!tempSess) {//先判断是否存在（用于去重），不存在增加一个
                                tempSessMap[sessType + "_" + sessionId] = true;
                                data.push({
                                    SessionType: sessType, //会话类型
                                    SessionTypeZh: typeZh, //会话类型中文
                                    SessionId: webim.Tool.formatText2Html(sessionId), //会话id
                                    SessionNick: webim.Tool.formatText2Html(sessionNick), //会话昵称
                                    SessionImage: sessionImage, //会话头像
                                    C2cAccount: webim.Tool.formatText2Html(senderId), //发送者id
                                    C2cNick: webim.Tool.formatText2Html(senderNick), //发送者昵称
                                    UnreadMsgCount: item.UnreadMsgCount, //未读消息数
                                    MsgSeq: item.MsgSeq, //消息seq
                                    MsgRandom: item.MsgRandom, //消息随机数
                                    MsgTimeStamp: webim.Tool.formatTimeStamp(item.MsgTimeStamp), //消息时间戳
                                    MsgShow: item.MsgShow//消息内容
                                });
                            }
                        }
                    }
                    $('#get_recent_contact_list_table').bootstrapTable('load', data);
                    $('#get_recent_contact_list_dialog').modal('show');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//初始化聊天界面左侧最近会话列表
    var initRecentContactList = function (cbOK, cbErr) {

        var options = {
            'Count': reqRecentSessCount//要拉取的最近会话条数
        };
        webim.getRecentContactList(
                options,
                function (resp) {
                    var tempSess;//临时会话变量
                    var firstSessType;//保存第一个会话类型
                    var firstSessId;//保存第一个会话id


                    //清空聊天对象列表
                    var sessList = document.getElementsByClassName("sesslist")[0];
                    sessList.innerHTML = "";
                    if (resp.SessionItem && resp.SessionItem.length > 0) {//如果存在最近会话记录

                        for (var i in resp.SessionItem) {
                            var item = resp.SessionItem[i];
                            var type = item.Type;//接口返回的会话类型
                            var sessType, typeZh, sessionId, sessionNick = '', sessionImage = '', senderId = '', senderNick = '';
                            if (type == webim.RECENT_CONTACT_TYPE.C2C) {//私聊
                                typeZh = '私聊';
                                sessType = webim.SESSION_TYPE.C2C;//设置会话类型
                                sessionId = item.To_Account;//会话id，私聊时为好友ID或者系统账号（值为@TIM#SYSTEM，业务可以自己决定是否需要展示），注意：从To_Account获取,

                                if (sessionId == '@TIM#SYSTEM') {//先过滤系统消息，，
                                    webim.Log.warn('过滤好友系统消息,sessionId=' + sessionId);
                                    continue;
                                }
                                var key = sessType + "_" + sessionId;
                                var c2cInfo = infoMap[key];
                                if (c2cInfo && c2cInfo.name) {//从infoMap获取c2c昵称
                                    sessionNick = c2cInfo.name;//会话昵称，私聊时为好友昵称，接口暂不支持返回，需要业务自己获取（前提是用户设置过自己的昵称，通过拉取好友资料接口（支持批量拉取）得到）
                                } else {//没有找到或者没有设置过
                                    sessionNick = sessionId;//会话昵称，如果昵称为空，默认将其设成会话id
                                }
                                if (c2cInfo && c2cInfo.image) {//从infoMap获取c2c头像
                                    sessionImage = c2cInfo.image;//会话头像，私聊时为好友头像，接口暂不支持返回，需要业务自己获取（前提是用户设置过自己的昵称，通过拉取好友资料接口（支持批量拉取）得到）
                                } else {//没有找到或者没有设置过
                                    sessionImage = friendHeadUrl;//会话头像，如果为空，默认将其设置demo自带的头像
                                }
                                senderId = senderNick = '';//私聊时，这些字段用不到，直接设置为空
                            } else if (type == webim.RECENT_CONTACT_TYPE.GROUP) {//群聊
                                typeZh = '群聊';
                                sessType = webim.SESSION_TYPE.GROUP;//设置会话类型
                                sessionId = item.ToAccount;//会话id，群聊时为群ID，注意：从ToAccount获取
                                sessionNick = item.GroupNick;//会话昵称，群聊时，为群名称，接口一定会返回

                                if (item.GroupImage) {//优先考虑接口返回的群头像
                                    sessionImage = item.GroupImage;//会话头像，群聊时，群头像，如果业务设置过群头像（设置群头像请参考wiki文档-设置群资料接口），接口会返回
                                } else {//接口没有返回或者没有设置过群头像，再从infoMap获取群头像
                                    var key = sessType + "_" + sessionId;
                                    var groupInfo = infoMap[key];
                                    if (groupInfo && groupInfo.image) {//
                                        sessionImage = groupInfo.image
                                    } else {//不存在或者没有设置过，则使用默认头像
                                        sessionImage = groupHeadUrl;//会话头像，如果没有设置过群头像，默认将其设置demo自带的头像
                                    }
                                }
                                senderId = item.MsgGroupFrom_Account;//群消息的发送者id

                                if (!senderId) {//发送者id为空
                                    webim.Log.warn('群消息发送者id为空,senderId=' + senderId + ",groupid=" + sessionId);
                                    continue;
                                }
                                if (senderId == '@TIM#SYSTEM') {//先过滤群系统消息，因为接口暂时区分不了是进群还是退群等提示消息，
                                    webim.Log.warn('过滤群系统消息,senderId=' + senderId + ",groupid=" + sessionId);
                                    continue;
                                }

                                senderNick = item.MsgGroupFromCardName;//优先考虑群成员名片
                                if (!senderNick) {//如果没有设置群成员名片
                                    senderNick = item.MsgGroupFromNickName;//再考虑接口是否返回了群成员昵称
                                    if (!senderNick && !sessionNick) {//如果接口没有返回昵称或者没有设置群昵称，从infoMap获取昵称
                                        var key = webim.SESSION_TYPE.C2C + "_" + senderId;
                                        var c2cInfo = infoMap[key];
                                        if (c2cInfo && c2cInfo.name) {
                                            senderNick = c2cInfo.name;//发送者群昵称
                                        } else {
                                            sessionNick = senderId;//如果昵称为空，默认将其设成发送者id
                                        }
                                    }
                                }

                            } else {
                                typeZh = '未知类型';
                                sessionId = item.ToAccount;//
                            }
                            if (!sessionId) {//会话id为空
                                webim.Log.warn('会话id为空,sessionId=' + sessionId);
                                continue;
                            }

                            if (sessionId == '@TLS#NOT_FOUND') {//会话id不存在，可能是已经被删除了
                                webim.Log.warn('会话id不存在,sessionId=' + sessionId);
                                continue;
                            }

                            if (sessionNick.length > maxNameLen) {//帐号或昵称过长，截取一部分，出于demo需要，业务可以自己决定
                                sessionNick = sessionNick.substr(0, maxNameLen) + "...";
                            }

                            tempSess = recentSessMap[sessType + "_" + sessionId];
                            if (!tempSess) {//先判断是否存在（用于去重），不存在增加一个

                                if (!firstSessId) {
                                    firstSessType = sessType;//记录第一个会话类型
                                    firstSessId = sessionId;//记录第一个会话id
                                }
                                recentSessMap[sessType + "_" + sessionId] = {
                                    SessionType: sessType, //会话类型
                                    SessionId: sessionId, //会话对象id，好友id或者群id
                                    SessionNick: sessionNick, //会话昵称，好友昵称或群名称
                                    SessionImage: sessionImage, //会话头像，好友头像或者群头像
                                    C2cAccount: senderId, //发送者id，群聊时，才有用
                                    C2cNick: senderNick, //发送者昵称，群聊时，才有用
                                    UnreadMsgCount: item.UnreadMsgCount, //未读消息数,私聊时需要通过webim.syncMsgs(initUnreadMsgCount)获取,参考后面的demo，群聊时不需要。
                                    MsgSeq: item.MsgSeq, //消息seq
                                    MsgRandom: item.MsgRandom, //消息随机数
                                    MsgTimeStamp: webim.Tool.formatTimeStamp(item.MsgTimeStamp), //消息时间戳
                                    MsgGroupReadedSeq: item.MsgGroupReadedSeq || 0,
                                    MsgShow: item.MsgShow//消息内容,文本消息为原内容，表情消息为[表情],其他类型消息以此类推
                                };

                                //在左侧最近会话列表框中增加一个会话div
                                addSess(sessType, webim.Tool.formatText2Html(sessionId), webim.Tool.formatText2Html(sessionNick), sessionImage, item.UnreadMsgCount, 'sesslist');
                            }

                        }
                        //清空聊天界面
                        document.getElementsByClassName("msgflow")[0].innerHTML = "";

                        tempSess = recentSessMap[firstSessType + "_" + firstSessId];//选中第一个会话
                        selType = tempSess.SessionType;//初始化当前聊天类型

                        selToID = tempSess.SessionId;//初始化当前聊天对象id

                        selSess = webim.MsgStore.sessByTypeId(selType, selToID);//初始化当前会话对象

                        setSelSessStyleOn(selToID);//设置当前聊天对象选中样式

                        webim.syncMsgs(initUnreadMsgCount);//初始化最近会话的消息未读数


                        if (cbOK)//回调
                            cbOK();

                    }

                },
                cbErr
                );
    };

//初始化最近会话的消息未读数
    function initUnreadMsgCount() {
        var sess;
        var sessMap = webim.MsgStore.sessMap();
        for (var i in sessMap) {
            sess = sessMap[i];
            if (selToID && selToID != sess.id()) {//更新其他聊天对象的未读消息数
                updateSessDiv(sess.type(), sess.id(), sess.name(), sess.unread());
            }
        }
    }







    // 群组管理 api 示例代码
    //js/group/group_manager.js
//创建群菜单点击事件
    function createGroupMenuClick() {
        //重置表单
        $('#cg_form')[0].reset();

        var sessList = document.getElementsByClassName("sesslist-group")[0];
        var num = 1;
        if (sessList && sessList.childNodes) {
            num = sessList.childNodes.length + 1;
        }
        $("#cg_name").val('群' + num);

        $('#create_group_dialog').modal('show');
    }
//搜索群(按ID)菜单单击事件
    function searchGroupMenuClick() {

        $("#sg_form")[0].reset();
        initSearchGroupTable([]);
        $('#search_group_table').bootstrapTable('load', []);
        $('#search_group_dialog').modal('show');
    }
//搜索群(按名称)菜单单击事件
    function searchGroupByNameMenuClick() {

        $("#sgbn_form")[0].reset();
        initSearchGroupByNameTable([]);
        $('#search_group_by_name_table').bootstrapTable('load', []);
        $('#search_group_by_name_dialog').modal('show');
    }
//定义搜索群(按ID)结果表格每行的操作按钮
    function sgOperateFormatter(value, row, index) {
        return [
            '<a class="plus" href="javascript:void(0)" title="申请加入">',
            '<i class="glyphicon glyphicon-plus"></i>',
            '</a>'

        ].join('');
    }
//搜索群(按ID)结果表格按钮事件
    window.sgOperateEvents = {
        'click .plus': function (e, value, row, index) {

            $("#ajg_group_id").val(row.GId);
            $("#ajg_group_type").val(row.Type);
            $("#ajg_group_name").val(row.Name);

            if (row.Type == 'ChatRoom') {//聊天室直接申请加入
                applyJoinGroup();
            } else {
                $("#ajg_apply_msg").val('你好，我想加入贵群~');
                $('#apply_join_group_dialog').modal('show');
            }
        }
    };
//初始化搜索群(按ID)结果表格
    function initSearchGroupTable(data) {
        $('#search_group_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "GroupId", title: "ID", align: "center", valign: "middle", sortable: "true"},
                {field: "TypeZh", title: "类型", align: "center", valign: "middle", sortable: "true"},
                {field: "Type", title: "类型(英文)", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "Name", title: "名称", align: "center", valign: "middle", sortable: "true"},
                {field: "Owner_Account", title: "群主", align: "center", valign: "middle", sortable: "true"},
                {field: "CreateTime", title: "创建时间", align: "center", valign: "middle", sortable: "true"},
                {field: "MemberNum", title: "群成员数", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "sgOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "sgOperateFormatter",
                    events: "sgOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }


//定义搜索群（按名称）结果表格每行的操作按钮
    function sgbnOperateFormatter(value, row, index) {
        return [
            '<a class="plus" href="javascript:void(0)" title="申请加入">',
            '<i class="glyphicon glyphicon-plus"></i>',
            '</a>'

        ].join('');
    }
//搜索群（按名称）结果表格按钮事件
    window.sgbnOperateEvents = {
        'click .plus': function (e, value, row, index) {

            $("#ajg_group_id").val(row.GId);
            $("#ajg_group_type").val(row.Type);
            $("#ajg_group_name").val(row.Name);

            if (row.Type == 'ChatRoom') {//聊天室直接申请加入
                applyJoinGroup();
            } else {
                $("#ajg_apply_msg").val('你好，我想加入贵群~');
                $('#apply_join_group_dialog').modal('show');
            }
        }
    };
//初始化搜索群（按名称）结果表格
    function initSearchGroupByNameTable(data) {
        $('#search_group_by_name_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "GroupId", title: "ID", align: "center", valign: "middle", sortable: "true"},
                {field: "TypeZh", title: "类型", align: "center", valign: "middle", sortable: "true"},
                {field: "Type", title: "类型(英文)", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "Name", title: "名称", align: "center", valign: "middle", sortable: "true"},
                {field: "FaceUrl", title: "群头像", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "Owner_Account", title: "群主", align: "center", valign: "middle", sortable: "true"},
                {field: "CreateTime", title: "创建时间", align: "center", valign: "middle", sortable: "true"},
                {field: "MemberNum", title: "群成员数", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "sgbnOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "sgbnOperateFormatter",
                    events: "sgbnOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }


//定义我的群组表格每行的操作按钮
    function gmgOperateFormatter(value, row, index) {
        return [
            '<a class="plus ml10" href="javascript:void(0)" title="邀请成员">',
            '<i class="glyphicon glyphicon-plus"></i>',
            '</a>',
            '<a class="user ml10" href="javascript:void(0)" title="查看群成员">',
            '<i class="glyphicon glyphicon-user"></i>',
            '</a>',
            '<a class="bell ml10" href="javascript:void(0)" title="设置群消息提示">',
            '<i class="glyphicon glyphicon-bell"></i>',
            '</a>',
            '<a class="logout ml10" href="javascript:void(0)" title="退出该群">',
            '<i class="glyphicon glyphicon-log-out"></i>',
            '</a>',
            '<a class="pencil ml10" href="javascript:void(0)" title="修改群资料">',
            '<i class="glyphicon glyphicon-pencil"></i>',
            '</a>',
            '<a class="share ml10" href="javascript:void(0)" title="转让该群">',
            '<i class="glyphicon glyphicon-share"></i>',
            '</a>',
            '<a class="trash ml10" href="javascript:void(0)" title="解散该群">',
            '<i class="glyphicon glyphicon-trash"></i>',
            '</a>',
            '<a class="envelope ml10" href="javascript:void(0)" title="发消息">',
            '<i class="glyphicon glyphicon-envelope"></i>',
            '</a>'

        ].join('');
    }
//我的群组表格每行的操作按钮点击事件
    window.gmgOperateEvents = {
        'click .plus': function (e, value, row, index) {
            if (row.TypeEn != 'Private') {
                alert('公开群或聊天室不支持直接拉人操作');
                return;
            }
            $('#gmfg_group_id').val(row.GId);
            getMyFriendGroup();
        },
        'click .user': function (e, value, row, index) {
            $('#ggm_group_id').val(row.GId);
            $('#ggm_my_role').val(row.Role);
            $('#ggm_group_type').val(row.TypeEn);

            getGroupMemberInfo(row.GId);
        },
        'click .bell': function (e, value, row, index) {
            $('#mgmf_sel_row_index').val(index);
            $('#mgmf_group_id').val(row.GId);
            //设置群消息提示类型
            //var msg_flag = webim.Tool.groupRoleCh2En(row.Role);
            var msg_flag = row.MsgFlagEn;
            //$("input[type=radio][name='mgmf_msg_flag_radio'][value=" + msg_flag + "]").attr("checked", 'checked');//此方法存在问题，当修改单选按钮值，并且关闭对话框，再打开对话时，无法选中真正的值
            var mgmf_msg_flag_radio = document.mgmf_form.mgmf_msg_flag_radio;
            for (var i = 0; i < mgmf_msg_flag_radio.length; i++) {
                if (mgmf_msg_flag_radio[i].value == msg_flag) {
                    mgmf_msg_flag_radio[i].checked = true;
                    break;
                }
            }
            $('#modify_group_msg_flag_dialog').modal('show');

        },
        'click .logout': function (e, value, row, index) {
            if (row.Role == '群主' && row.TypeEn != 'Private') {
                alert('您是群主，不能从公开群或聊天室退出');
                return;
            }
            if (confirm("确定退出该群吗？")) {
                quitGroup(row.GId);
            }
        },
        'click .pencil': function (e, value, row, index) {

            //成员可以修改讨论组的基本资料
            if (row.Role == '成员' && row.Type != 'Private') {
                alert('你不是群主或管理员，无法进行此操作');
                return;
            }
            $('#mg_sel_row_index').val(index);
            $('#mg_group_id').val(row.GId);
            $('#mg_faceurl').val(row.FaceUrl);
            $('#mg_name').val(webim.Tool.formatHtml2Text(row.Name));
            $('#mg_introduction').val(webim.Tool.formatHtml2Text(row.Introduction));
            $('#mg_notification').val(webim.Tool.formatHtml2Text(row.Notification));
            $('#modify_group_dialog').modal('show');
        },
        'click .share': function (e, value, row, index) {
            if (row.Role != '群主') {
                alert('你不是群主，无法进行此操作');
            } else {
                if (row.TypeEn == 'AVChatRoom') {
                    alert('直播聊天室不能进行转让');
                    return;
                }
                $("#cgo_form")[0].reset();//清空原来表单的值
                $('#cgo_sel_row_index').val(index);
                $('#cgo_group_id').val(row.GId);
                $('#change_group_owner_dialog').modal('show');
            }
        },
        'click .trash': function (e, value, row, index) {
            if (row.Role != '群主') {
                alert('你不是群主，无法进行此操作');
            } else {
                if (row.TypeEn == 'Private') {
                    alert('您是群主，不能解散讨论组');
                    return;
                }
                if (confirm("确定解散该群组吗？")) {
                    destroyGroup(row.GId);
                }
            }
        },
        'click .envelope': function (e, value, row, index) {
            $('#sgm_to_groupid').val(row.GId);
            $('#sgm_to_group_name').val(row.Name);

            $('#send_group_msg_dialog').modal('show');
        }
    };
//初始化我的群组表格
    function initGetMyGroupTable(data) {
        $('#get_my_group_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "GroupId", title: "ID", align: "center", valign: "middle", sortable: "true"},
                {field: "Type", title: "类型", align: "center", valign: "middle", sortable: "true"},
                {field: "TypeEn", title: "类型(英文)", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "FaceUrl", title: "群头像", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "Name", title: "名称", align: "center", valign: "middle", sortable: "true"},
                {field: "RoleEn", title: "角色(英文)", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "Role", title: "角色", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "MsgFlagEn",
                    title: "消息提示类型(英文)",
                    align: "center",
                    valign: "middle",
                    sortable: "true",
                    visible: false
                },
                {field: "MsgFlag", title: "消息提示类型", align: "center", valign: "middle", sortable: "true"},
                {field: "JoinTime", title: "加入时间", align: "center", valign: "middle", sortable: "true"},
                {field: "MemberNum", title: "成员数", align: "center", valign: "middle", sortable: "true"},
                {field: "Notification", title: "公告", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "Introduction", title: "简介", align: "center", valign: "middle", sortable: "true", visible: false},
                {
                    field: "gmgOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "gmgOperateFormatter",
                    events: "gmgOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }
//定义邀请好友加群表格每行的操作按钮
    function gmfgOperateFormatter(value, row, index) {
        return [
            '<a class="plus" href="javascript:void(0)" title="邀请加入">',
            '<i class="glyphicon glyphicon-plus"></i>',
            '</a>'

        ].join('');
    }
//邀请好友加群表格每行按钮单击事件
    window.gmfgOperateEvents = {
        'click .plus': function (e, value, row, index) {
            $('#agm_group_id').val($('#gmfg_group_id').val());
            $('#agm_account').val(row.Info_Account);
            $('#add_group_member_dialog').modal('show');
        }
    };
//初始化邀请好友加群表格
    function initGetMyFriendGroupTable(data) {
        $('#get_my_friend_group_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "Info_Account", title: "账号", align: "center", valign: "middle", sortable: "true"},
                {field: "Nick", title: "昵称", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "gmfgOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "gmfgOperateFormatter",
                    events: "gmfgOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }

//读取群组公开资料-高级接口（可用于搜索群，按ID）
    var getGroupPublicInfo = function () {
        if ($("#sg_group_id").val().length == 0) {
            alert('请输入群组ID');
            return;
        }
        if (webim.Tool.trimStr($("#sg_group_id").val()).length == 0) {
            alert('您输入的群组ID全是空格,请重新输入');
            return;
        }
        var options = {
            'GroupIdList': [
                $("#sg_group_id").val()
            ],
            'GroupBasePublicInfoFilter': [
                'Type',
                'Name',
                'Introduction',
                'Notification',
                'FaceUrl',
                'CreateTime',
                'Owner_Account',
                'LastInfoTime',
                'LastMsgTime',
                'NextMsgSeq',
                'MemberNum',
                'MaxMemberNum',
                'ApplyJoinOption'
            ]
        };
        webim.getGroupPublicInfo(
                options,
                function (resp) {
                    var data = [];
                    if (resp.GroupInfo && resp.GroupInfo.length > 0) {
                        for (var i in resp.GroupInfo) {
                            if (resp.GroupInfo[i].ErrorCode > 0) {
                                alert(resp.GroupInfo[i].ErrorInfo);
                                return;
                            }
                            var group_id = resp.GroupInfo[i].GroupId;
                            var name = webim.Tool.formatText2Html(resp.GroupInfo[i].Name);
                            var type_zh = webim.Tool.groupTypeEn2Ch(resp.GroupInfo[i].Type);
                            var type = resp.GroupInfo[i].Type;
                            var owner_account = resp.GroupInfo[i].Owner_Account;
                            var create_time = webim.Tool.formatTimeStamp(resp.GroupInfo[i].CreateTime);
                            var member_num = resp.GroupInfo[i].MemberNum;
                            var notification = webim.Tool.formatText2Html(resp.GroupInfo[i].Notification);
                            var introduction = webim.Tool.formatText2Html(resp.GroupInfo[i].Introduction);
                            data.push({
                                'GId': group_id,
                                'GroupId': webim.Tool.formatText2Html(group_id),
                                'Name': name,
                                'TypeZh': type_zh,
                                'Type': type,
                                'Owner_Account': owner_account,
                                'MemberNum': member_num,
                                'Notification': notification,
                                'Introduction': introduction,
                                'CreateTime': create_time
                            });
                        }
                    }
                    $('#search_group_table').bootstrapTable('load', data);
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//搜索群（按名称）
    var searchGroupByName = function () {
        if ($("#sgbn_group_name").val().length == 0) {
            alert('请输入群名称');
            return;
        }
        if (webim.Tool.trimStr($("#sgbn_group_name").val()).length == 0) {
            alert('您输入的群名称都是空格,请重新输入');
            return;
        }
        var options = {
            'Content': $("#sgbn_group_name").val(), //群名称
            "PageNum": 0, //0表示从第1页开始拉取
            "GroupPerPage": 20, //每页拉取20条
            'ResponseFilter': {
                "GroupBasePublicInfoFilter": [//基础信息过滤器，可以通过它设置需要拉取的公开的基础信息
                    'Type', //群组类型
                    'Name', //群名称
                    "FaceUrl", //群头像
                    "Introduction", //群简介
                    'CreateTime', //群创建时间
                    'Owner_Account', //群创建者
                    'MemberNum', //成员个数
                    'MaxMemberNum', //群成员最大个数
                    'ApplyJoinOption'//申请加群选项
                ]
            }
        };
        webim.searchGroupByName(
                options,
                function (resp) {
                    var data = [];
                    if (resp.GroupInfo && resp.GroupInfo.length > 0) {
                        for (var i in resp.GroupInfo) {
                            if (resp.GroupInfo[i].ErrorCode > 0) {
                                alert(resp.GroupInfo[i].ErrorInfo);
                                return;
                            }
                            var group_id = resp.GroupInfo[i].GroupId;
                            var name = webim.Tool.formatText2Html(resp.GroupInfo[i].Name);
                            var type_zh = webim.Tool.groupTypeEn2Ch(resp.GroupInfo[i].Type);
                            var type = resp.GroupInfo[i].Type;
                            var owner_account = resp.GroupInfo[i].Owner_Account;
                            var create_time = webim.Tool.formatTimeStamp(resp.GroupInfo[i].CreateTime);
                            var member_num = resp.GroupInfo[i].MemberNum;
                            var faceUrl = resp.GroupInfo[i].FaceUrl;
                            var introduction = webim.Tool.formatText2Html(resp.GroupInfo[i].Introduction);
                            //var maxMemberNum = resp.GroupInfo[i].MaxMemberNum;
                            //var applyJoinOption = resp.GroupInfo[i].ApplyJoinOption;
                            data.push({
                                'GId': group_id,
                                'GroupId': webim.Tool.formatText2Html(group_id),
                                'Name': name,
                                'TypeZh': type_zh,
                                'Type': type,
                                'Owner_Account': owner_account,
                                'MemberNum': member_num,
                                'FaceUrl': faceUrl,
                                'Introduction': introduction,
                                'CreateTime': create_time
                            });
                        }
                    }
                    $('#search_group_by_name_table').bootstrapTable('load', data);
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//创建群组
    var createGroup = function () {
        var sel_friends = $('#select_friends').val();

        var member_list = [];
        var members = sel_friends.split(";"); //字符分割
        for (var i = 0; i < members.length; i++) {
            if (members[i] && members[i].length > 0) {
                member_list.push(members[i]);
            }
        }
        var faceurl = $("#cg_faceurl").val();
        var cg_id = $("#cg_id").val().trim();
        if (cg_id && !/^[A-Za-z0-9_]+$/gi.test(cg_id)) {
            alert('群组ID只能是数字、字母或下划线');
            return
        }
        if ($("#cg_name").val().length == 0) {
            alert('请输入群组名称');
            return;
        }
        if ($("#cg_name").val().length == 0) {
            alert('请输入群组名称');
            return;
        }
        if (webim.Tool.trimStr($("#cg_name").val()).length == 0) {
            alert('您输入的群组名称全是空格,请重新输入');
            return;
        }
        if (webim.Tool.getStrBytes($("#cg_name").val()) > 30) {
            alert('您输入的群组名称超出限制(最长10个汉字)');
            return;
        }
        if (webim.Tool.getStrBytes($("#cg_notification").val()) > 150) {
            alert('您输入的群组公告超出限制(最长50个汉字)');
            return;
        }
        if (webim.Tool.getStrBytes($("#cg_introduction").val()) > 120) {
            alert('您输入的群组简介超出限制(最长40个汉字)');
            return;
        }
        var groupType = $('input[name="cg_type_radio"]:checked').val();
        var options = {
            'GroupId': cg_id,
            'Owner_Account': loginInfo.identifier,
            'Type': groupType, //Private/Public/ChatRoom/AVChatRoom
            'Name': $("#cg_name").val(),
            'Notification': $("#cg_notification").val(),
            'Introduction': $('#cg_introduction').val(),
            'MemberList': member_list
        };
        if (faceurl) {
            options.FaceUrl = faceurl;
        }
        if (groupType != 'Private') {//非讨论组才支持ApplyJoinOption属性
            options.ApplyJoinOption = $('input[name="cg_ajp_type_radio"]:checked').val();
        }
        webim.createGroup(
                options,
                function (resp) {
                    $('#create_group_dialog').modal('hide');
                    alert('创建群成功');
                    //读取我的群组列表
                    //getJoinedGroupListHigh(getGroupsCallbackOK);
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };
//修改群资料
    var modifyGroup = function () {
        var faceurl = $("#mg_faceurl").val();
        if ($("#mg_name").val().length == 0) {
            alert('请输入群组名称');
            return;
        }
        if (webim.Tool.trimStr($("#mg_name").val()).length == 0) {
            alert('您输入的群组名称全是空格,请重新输入');
            return;
        }
        if (webim.Tool.getStrBytes($("#fsm_name").val()) > 30) {
            alert('您输入的群组名称超出限制(最长10个汉字)');
            return;
        }
        if (webim.Tool.getStrBytes($("#fsm_notification").val()) > 150) {
            alert('您输入的群组公告超出限制(最长50个汉字)');
            return;
        }
        if (webim.Tool.getStrBytes($("#fsm_introduction").val()) > 120) {
            alert('您输入的群组简介超出限制(最长40个汉字)');
            return;
        }
        var options = {
            'GroupId': $('#mg_group_id').val(),
            'Name': $('#mg_name').val(),
            'Notification': $('#mg_notification').val(),
            'Introduction': $('#mg_introduction').val()
        };
        if (faceurl) {
            options.FaceUrl = faceurl;
        }
        webim.modifyGroupBaseInfo(
                options,
                function (resp) {
                    //在表格中修改对应的行
                    $('#get_my_group_table').bootstrapTable('updateRow', {
                        index: $('#mg_sel_row_index').val(),
                        row: {
                            Type: $('input[name="mg_type_radio"]:checked').val(),
                            Name: webim.Tool.formatText2Html($('#mg_name').val()),
                            Introduction: webim.Tool.formatText2Html($('#mg_introduction').val()),
                            Notification: webim.Tool.formatText2Html($('#mg_notification').val())
                        }
                    });
                    $('#modify_group_dialog').modal('hide');
                    alert('修改群资料成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//转让群组
    var changeGroupOwner = function () {
        var newOwer = $("#cgo_new_owner").val();
        if (newOwer.length == 0) {
            alert('请输入新的群主账号');
            return;
        }
        if (webim.Tool.trimStr(newOwer).length == 0) {
            alert('您输入的新群主账号全是空格,请重新输入');
            return;
        }
        if (newOwer == loginInfo.identifier) {
            alert('不能给自己转让群组');
            return;
        }

        var options = {
            'GroupId': $('#cgo_group_id').val(),
            'NewOwner_Account': newOwer
        };
        webim.changeGroupOwner(
                options,
                function (resp) {
                    //在表格中修改对应的行
                    $('#get_my_group_table').bootstrapTable('updateRow', {
                        index: $('#cgo_sel_row_index').val(),
                        row: {
                            RoleEn: "Member",
                            Role: "成员"
                        }
                    });
                    $('#change_group_owner_dialog').modal('hide');
                    alert('转让群组成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//申请加群
    var applyJoinGroup = function () {
        if (webim.Tool.getStrBytes($("#ajg_apply_msg").val()) > 300) {
            alert('您输入的附言超出限制(最长100个汉字)');
            return;
        }
        var options = {
            'GroupId': $("#ajg_group_id").val(),
            'ApplyMsg': $("#ajg_apply_msg").val(),
            'UserDefinedField': ''
        };
        webim.applyJoinGroup(
                options,
                function (resp) {
                    $('#apply_join_group_dialog').modal('hide');
                    var joinedStatus = resp.JoinedStatus;//JoinedSuccess--成功加入，WaitAdminApproval--等待管理员审核
                    if (joinedStatus == "JoinedSuccess") {
                        //刷新我的群组列表
                        //getJoinedGroupListHigh(getGroupsCallbackOK);
                        alert('成功加入该群');
                    } else {
                        alert('申请成功，请等待群主审核');
                    }
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };
//获取我的群组
    var getMyGroup = function () {
        initGetMyGroupTable([]);
        var options = {
            'Member_Account': loginInfo.identifier,
            'Limit': totalCount,
            'Offset': 0,
            //'GroupType':'',
            'GroupBaseInfoFilter': [
                'Type',
                'Name',
                'Introduction',
                'Notification',
                'FaceUrl',
                'CreateTime',
                'Owner_Account',
                'LastInfoTime',
                'LastMsgTime',
                'NextMsgSeq',
                'MemberNum',
                'MaxMemberNum',
                'ApplyJoinOption'
            ],
            'SelfInfoFilter': [
                'Role',
                'JoinTime',
                'MsgFlag',
                'UnreadMsgNum'
            ]
        };
        webim.getJoinedGroupListHigh(
                options,
                function (resp) {
                    if (!resp.GroupIdList || resp.GroupIdList.length == 0) {
                        alert('你目前还没有加入任何群组');
                        return;
                    }
                    var data = [];
                    for (var i = 0; i < resp.GroupIdList.length; i++) {

                        var group_id = resp.GroupIdList[i].GroupId;
                        var name = webim.Tool.formatText2Html(resp.GroupIdList[i].Name);
                        var faceUrl = resp.GroupIdList[i].FaceUrl;
                        var type_en = resp.GroupIdList[i].Type;
                        var type = webim.Tool.groupTypeEn2Ch(resp.GroupIdList[i].Type);
                        var role_en = resp.GroupIdList[i].SelfInfo.Role;
                        var role = webim.Tool.groupRoleEn2Ch(resp.GroupIdList[i].SelfInfo.Role);
                        var msg_flag = webim.Tool.groupMsgFlagEn2Ch(resp.GroupIdList[i].SelfInfo.MsgFlag);
                        var msg_flag_en = resp.GroupIdList[i].SelfInfo.MsgFlag;
                        var join_time = webim.Tool.formatTimeStamp(resp.GroupIdList[i].SelfInfo.JoinTime);
                        var member_num = resp.GroupIdList[i].MemberNum;
                        var notification = webim.Tool.formatText2Html(resp.GroupIdList[i].Notification);
                        var introduction = webim.Tool.formatText2Html(resp.GroupIdList[i].Introduction);
                        data.push({
                            'GId': group_id,
                            'GroupId': webim.Tool.formatText2Html(group_id),
                            'Name': name,
                            'FaceUrl': faceUrl,
                            'TypeEn': type_en,
                            'Type': type,
                            'RoleEn': role_en,
                            'Role': role,
                            'MsgFlagEn': msg_flag_en,
                            'MsgFlag': msg_flag,
                            'MemberNum': member_num,
                            'Notification': notification,
                            'Introduction': introduction,
                            'JoinTime': join_time
                        });
                    }
                    //打开我的群组列表对话框
                    $('#get_my_group_table').bootstrapTable('load', data);
                    $('#get_my_group_dialog').modal('show');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };
//退群
    var quitGroup = function (group_id) {
        var options = null;
        if (group_id) {
            options = {
                'GroupId': group_id
            };
        }
        if (options == null) {
            alert('退群时，群组ID非法');
            return;
        }
        webim.quitGroup(
                options,
                function (resp) {
                    //在表格中删除对应的行
                    $('#get_my_group_table').bootstrapTable('remove', {
                        field: 'GroupId',
                        values: [group_id]
                    });
                    //刷新我的群组列表
                    //getJoinedGroupListHigh(getGroupsCallbackOK);
                    deleteSessDiv(webim.SESSION_TYPE.GROUP, group_id);//在最近的联系人列表删除可能存在的群会话
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };
//读取群组基本资料-高级接口
    var getGroupInfo = function (group_id, cbOK, cbErr) {
        var options = {
            'GroupIdList': [
                group_id
            ],
            'GroupBaseInfoFilter': [
                'Type',
                'Name',
                'Introduction',
                'Notification',
                'FaceUrl',
                'CreateTime',
                'Owner_Account',
                'LastInfoTime',
                'LastMsgTime',
                'NextMsgSeq',
                'MemberNum',
                'MaxMemberNum',
                'ApplyJoinOption'
            ],
            'MemberInfoFilter': [
                'Account',
                'Role',
                'JoinTime',
                'LastSendMsgTime',
                'ShutUpUntil'
            ]
        };
        webim.getGroupInfo(
                options,
                function (resp) {
                    if (cbOK) {
                        cbOK(resp);
                    }
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//解散群组
    var destroyGroup = function (group_id) {
        var options = null;
        if (group_id) {
            options = {
                'GroupId': group_id
            };
        }
        if (options == null) {
            alert('解散群时，群组ID非法');
            return;
        }
        webim.destroyGroup(
                options,
                function (resp) {
                    //在表格中删除对应的行
                    $('#get_my_group_table').bootstrapTable('remove', {
                        field: 'GroupId',
                        values: [group_id]
                    });
                    //读取我的群组列表
                    //getJoinedGroupListHigh(getGroupsCallbackOK);
                    deleteSessDiv(webim.SESSION_TYPE.GROUP, group_id);//在最近的联系人列表删除可能存在的群会话
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//获取我的好友，然后邀请加群
    var getMyFriendGroup = function () {

        initGetMyFriendGroupTable([]);
        var options = {
            'From_Account': loginInfo.identifier,
            'TimeStamp': 0,
            'StartIndex': 0,
            'GetCount': totalCount,
            'LastStandardSequence': 0,
            "TagList": [
                "Tag_Profile_IM_Nick",
                "Tag_SNS_IM_Remark"
            ]
        };

        webim.getAllFriend(
                options,
                function (resp) {

                    if (resp.FriendNum > 0) {
                        var table_friends_data = [];
                        var friends = resp.InfoItem;
                        if (!friends || friends.length == 0) {
                            alert('你目前还没有好友，无法邀请好友加入该群');
                            return;
                        }
                        var count = friends.length;

                        for (var i = 0; i < count; i++) {
                            var friend_name = friends[i].Info_Account;
                            if (friends[i].SnsProfileItem && friends[i].SnsProfileItem[0] && friends[i].SnsProfileItem[0].Tag) {
                                friend_name = friends[i].SnsProfileItem[0].Value;
                            }

                            table_friends_data.push({
                                'Info_Account': webim.Tool.formatText2Html(friends[i].Info_Account),
                                'Nick': webim.Tool.formatText2Html(friend_name)
                            });
                        }
                        //打开我的好友列表对话框
                        $('#get_my_friend_group_table').bootstrapTable('load', table_friends_data);
                        $('#get_my_friend_group_dialog').modal('show');

                    } else {
                        alert('你目前还没有好友，无法邀请好友加入该群');
                    }

                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };


//选择我的好友
    var selectMyFriendGroup = function () {

        initSelectMyFriendGroupTable([]);
        var options = {
            'From_Account': loginInfo.identifier,
            'TimeStamp': 0,
            'StartIndex': 0,
            'GetCount': totalCount,
            'LastStandardSequence': 0,
            "TagList": [
                "Tag_Profile_IM_Nick",
                "Tag_SNS_IM_Remark"
            ]
        };

        webim.getAllFriend(
                options,
                function (resp) {

                    if (resp.FriendNum > 0) {
                        var table_friends_data = [];
                        var friends = resp.InfoItem;
                        if (!friends || friends.length == 0) {
                            alert('你目前还没有好友，无法建群');
                            return;
                        }
                        var count = friends.length;

                        for (var i = 0; i < count; i++) {
                            var friend_name = friends[i].Info_Account;
                            if (friends[i].SnsProfileItem && friends[i].SnsProfileItem[0] && friends[i].SnsProfileItem[0].Tag) {
                                friend_name = friends[i].SnsProfileItem[0].Value;
                            }

                            table_friends_data.push({
                                'Info_Account': webim.Tool.formatText2Html(friends[i].Info_Account),
                                'Nick': webim.Tool.formatText2Html(friend_name)
                            });
                        }
                        //打开选择我的好友列表对话框
                        $('#select_my_friend_group_table').bootstrapTable('load', table_friends_data);
                        $('#select_my_friend_group_dialog').modal('show');

                    } else {
                        alert('你目前还没有好友，无法建群');
                    }

                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//定义（创建群之前）选择好友表格每行按钮
    function smfgOperateFormatter(value, row, index) {
        return [
            '<a class="plus" href="javascript:void(0)" title="选中">',
            '<i class="glyphicon glyphicon-plus"></i>',
            '</a>',
            '<a class="minus" href="javascript:void(0)" title="取消">',
            '<i class="glyphicon glyphicon-minus"></i>',
            '</a>',
        ].join('');
    }
//（创建群之前）选择好友表格每行按钮单击事件
    window.smfgOperateEvents = {
        'click .plus': function (e, value, row, index) {

            var sel_friends = $('#select_friends').val();
            var account = row.Info_Account;
            //先判断是否已选择
            if (sel_friends && sel_friends != '') {
                var pos = sel_friends.indexOf(";" + account + ";");
                if (pos >= 0) {
                    alert('已选择该好友，请选择其他好友');
                    return;
                }
            } else {
                sel_friends = ';';
            }
            sel_friends += row.Info_Account + ";";
            $('#select_friends').val(sel_friends);
            alert('选择成功');

        },
        'click .minus': function (e, value, row, index) {
            var sel_friends = $('#select_friends').val();
            var account = row.Info_Account;
            //先判断是否已选择
            if (!sel_friends || sel_friends == '') {

                alert('还未选择好友，不能进行此操作');
                return;


            } else {
                var pos = sel_friends.indexOf(";" + account + ";");
                var strlen = (account + ";").length;
                if (pos >= 0) {
                    var begin = sel_friends.substr(0, pos);
                    var end = sel_friends.substr(pos + strlen);
                    var new_sel_friends = begin + end;
                } else {
                    alert('未选择该好友，不能进行此操作');
                    return;
                }

            }
            if (new_sel_friends == ';') {
                new_sel_friends = '';
            }
            $('#select_friends').val(new_sel_friends);
            alert('取消成功');
        }
    };
//初始化（创建群之前）选择好友表格
    function initSelectMyFriendGroupTable(data) {
        $('#select_my_friend_group_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "Info_Account", title: "账号", align: "center", valign: "middle", sortable: "true"},
                {field: "Nick", title: "昵称", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "smfgOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "smfgOperateFormatter",
                    events: "smfgOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }

//从我的群组列表中发群消息
    function sendGroupMsg() {

        toAccount = $("#sgm_to_groupid").val();
        toName = $("#sgm_to_group_name").val();
        msgtosend = $("#sgm_content").val();

        var msgLen = webim.Tool.getStrBytes(msgtosend);

        var maxLen, errInfo;

        maxLen = webim.MSG_MAX_LENGTH.GROUP;
        errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";

        if (msgtosend.length < 1) {
            alert("发送的消息不能为空!");
            return;
        }

        if (msgLen > maxLen) {
            alert(errInfo);
            return;
        }

        var sess = webim.MsgStore.sessByTypeId(webim.SESSION_TYPE.GROUP, toAccount);
        if (!sess) {
            sess = new webim.Session(webim.SESSION_TYPE.GROUP, toAccount, toName, groupHeadUrl, Math.round(new Date().getTime() / 1000));
        }
        var isSend = true;//是否为自己发送
        var seq = -1;//消息序列，-1表示sdk自动生成，用于去重
        var random = Math.round(Math.random() * 4294967296);//消息随机数，用于去重
        var msgTime = Math.round(new Date().getTime() / 1000);//消息时间戳
        var subType;//消息子类型

        subType = webim.GROUP_MSG_SUB_TYPE.COMMON;

        var msg = new webim.Msg(sess, isSend, seq, random, msgTime, loginInfo.identifier, subType, loginInfo.identifierNick);

        var text_obj;

        text_obj = new webim.Msg.Elem.Text(msgtosend);
        msg.addText(text_obj);

        webim.sendMsg(msg, function (resp) {

            if (!selToID) {//没有聊天会话
                selType = webim.SESSION_TYPE.GROUP;
                selToID = toAccount;
                selSess = sess;
                addSess(selType, toAccount, toName, groupHeadUrl, 0, 'sesslist');
                setSelSessStyleOn(toAccount);
                //群聊时，长轮询接口会返回自己发的消息
                //addMsg(msg);
            } else {//有聊天会话
                if (selToID == toAccount) {//聊天对象不变
                    //不做任何操作
                } else {//聊天对象发生改变
                    var tempSessDiv = document.getElementById("sessDiv_" + toAccount);
                    if (!tempSessDiv) {//不存在这个会话
                        addSess(webim.SESSION_TYPE.GROUP, toAccount, toName, groupHeadUrl, 0, 'sesslist');//增加一个会话
                    }
                    onSelSess(webim.SESSION_TYPE.GROUP, toAccount);//再进行切换
                }
            }
            webim.Tool.setCookie("tmpmsg_" + toAccount, '', 0);
            $("#sgm_content").val('');
            $('#send_group_msg_dialog').modal('hide');
            $('#get_my_group_dialog').modal('hide');

        }, function (err) {
            alert(err.ErrorInfo);
            $("#sgm_content").val('');
        });
    }

//将我的群组资料（群名称和群头像）保存在infoMap
    var initInfoMapByMyGroups = function (cbOK) {

        var options = {
            'Member_Account': loginInfo.identifier,
            'Limit': totalCount,
            'Offset': 0,
            'GroupBaseInfoFilter': [
                'Name',
                'FaceUrl'
            ]
        };
        webim.getJoinedGroupListHigh(
                options,
                function (resp) {
                    if (resp.GroupIdList && resp.GroupIdList.length) {
                        for (var i = 0; i < resp.GroupIdList.length; i++) {
                            var group_name = resp.GroupIdList[i].Name;
                            var group_image = resp.GroupIdList[i].FaceUrl;
                            var key = webim.SESSION_TYPE.GROUP + "_" + resp.GroupIdList[i].GroupId;
                            infoMap[key] = {
                                'name': group_name,
                                'image': group_image
                            }
                        }
                    }
                    if (cbOK) {
                        cbOK();
                    }
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };







    // 群成员管理 api 示例代码
    //js/group/group_member_manager.js
//定义群组成员表格每行的按钮
    function ggmOperateFormatter(value, row, index) {
        return [
            '<a class="pencil ml10" href="javascript:void(0)" title="修改群成员角色">',
            '<i class="glyphicon glyphicon-pencil"></i>',
            '</a>',
            '<a class="time ml10" href="javascript:void(0)" title="设置禁言时间">',
            '<i class="glyphicon glyphicon-time"></i>',
            '</a>',
            '<a class="remove ml10" href="javascript:void(0)" title="移除该成员">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'

        ].join('');
    }
//定义群组成员表格每行的按钮单击事件
    window.ggmOperateEvents = {
        'click .remove': function (e, value, row, index) {
            switch ($('#ggm_my_role').val()) {
                case '成员':
                    alert('你不是管理员，无法进行此操作');
                    break;
                case '管理员':
                    if (row.Role == '成员') {
                        $('#dgm_group_id').val($('#ggm_group_id').val());
                        $('#dgm_account').val(row.Member_Account);
                        $('#delete_group_member_dialog').modal('show');
                    } else {
                        alert('你不能移除群主或管理员');
                    }
                    break;
                case '群主':
                    if (row.Role == '群主') {
                        alert('你不能移除自己');
                    } else {
                        $('#dgm_group_id').val($('#ggm_group_id').val());
                        $('#dgm_account').val(row.Member_Account);
                        $('#delete_group_member_dialog').modal('show');
                    }
                    break;
            }
        },
        'click .pencil': function (e, value, row, index) {


            switch ($('#ggm_my_role').val()) {
                case '成员':
                    alert('你不是群主，无法进行此操作');
                    break;
                case '管理员':
                    alert('你不是群主，无法进行此操作');
                    break;
                case '群主':
                    if ($('#ggm_group_type').val() == 'Private') {
                        alert('讨论组不支持设置群成员角色操作');
                        return;
                    }
                    if (row.Role == '群主') {
                        alert('你不能设置自己的群角色');
                    } else {
                        $('#mgm_sel_row_index').val(index);
                        $('#mgm_group_id').val($('#ggm_group_id').val());
                        $('#mgm_account').val(row.Member_Account);
                        //设置群身份
                        var role_en = webim.Tool.groupRoleCh2En(row.Role);
                        //$("input[type=radio][name='mgm_role_radio'][value=" + role_en + "]").attr("checked", 'checked');
                        var mgm_role_radio = document.mgm_form.mgm_role_radio;
                        for (var i = 0; i < mgm_role_radio.length; i++) {
                            if (mgm_role_radio[i].value == role_en) {
                                mgm_role_radio[i].checked = true;
                                break;
                            }
                        }
                        $('#modify_group_member_dialog').modal('show');
                    }
                    break;
            }
        },
        'click .time': function (e, value, row, index) {
            switch ($('#ggm_my_role').val()) {
                case '成员':
                    alert('你不是管理员，无法进行此操作');
                    break;
                case '管理员':
                    if (row.Role == '成员') {
                        $('#fsm_sel_row_index').val(index);
                        $('#fsm_group_id').val($('#ggm_group_id').val());
                        $('#fsm_account').val(row.Member_Account);

                        var shutup_time = 0;
                        if (row.ShutUpUntil != '-') {

                            var shutup_until_time = new Date(row.ShutUpUntil).getTime();
                            var now_time = new Date().getTime();
                            shutup_time = shutup_until_time - now_time;
                            if (shutup_time > 0) {
                                shutup_time = Math.round(shutup_time / 1000);
                            } else {
                                shutup_time = 0;
                            }
                        }
                        $('#fsm_shut_up_time').val(shutup_time);
                        $('#forbid_send_msg_dialog').modal('show');
                    } else {
                        alert('你不能设置群主或管理禁言时间');
                    }
                    break;
                case '群主':
                    if (row.Role == '群主') {
                        alert('你不能对自己设置禁言时间');
                    } else {

                        $('#fsm_sel_row_index').val(index);
                        $('#fsm_group_id').val($('#ggm_group_id').val());
                        $('#fsm_account').val(row.Member_Account);

                        var shutup_time = 0;

                        if (row.ShutUpUntil != '-') {

                            var shutup_until_time = new Date(row.ShutUpUntil).getTime();
                            var now_time = new Date().getTime();
                            shutup_time = shutup_until_time - now_time;
                            if (shutup_time > 0) {
                                shutup_time = Math.round(shutup_time / 1000);
                            } else {
                                shutup_time = 0;
                            }
                        }
                        $('#fsm_shut_up_time').val(shutup_time);
                        $('#forbid_send_msg_dialog').modal('show');
                    }
                    break;
            }
        }
    };
//初始化群组成员表格
    function initGetGroupMemberTable(data) {
        $('#get_group_member_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "GroupId", title: "群ID", align: "center", valign: "middle", sortable: "true"},
                {field: "Member_Account", title: "帐号", align: "center", valign: "middle", sortable: "true"},
                {field: "Role", title: "角色", align: "center", valign: "middle", sortable: "true"},
                {field: "JoinTime", title: "入群时间", align: "center", valign: "middle", sortable: "true"},
                {field: "ShutUpUntil", title: "禁言截至时间", align: "center", valign: "middle", sortable: "true"},
                {
                    field: "ggmOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "ggmOperateFormatter",
                    events: "ggmOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }

//读取群组成员
    var getGroupMemberInfo = function (group_id) {
        initGetGroupMemberTable([]);
        var options = {
            'GroupId': group_id,
            'Offset': 0, //必须从0开始
            'Limit': totalCount,
            'MemberInfoFilter': [
                'Account',
                'Role',
                'JoinTime',
                'LastSendMsgTime',
                'ShutUpUntil'
            ]
        };
        webim.getGroupMemberInfo(
                options,
                function (resp) {
                    if (resp.MemberNum <= 0) {
                        alert('该群组目前没有成员');
                        return;
                    }
                    var data = [];
                    for (var i in resp.MemberList) {
                        var account = resp.MemberList[i].Member_Account;
                        var role = webim.Tool.groupRoleEn2Ch(resp.MemberList[i].Role);
                        var join_time = webim.Tool.formatTimeStamp(resp.MemberList[i].JoinTime);
                        var shut_up_until = webim.Tool.formatTimeStamp(resp.MemberList[i].ShutUpUntil);
                        if (shut_up_until == 0) {
                            shut_up_until = '-';
                        }
                        data.push({
                            GId: group_id,
                            GroupId: webim.Tool.formatText2Html(group_id),
                            Member_Account: webim.Tool.formatText2Html(account),
                            Role: role,
                            JoinTime: join_time,
                            ShutUpUntil: shut_up_until
                        });
                    }
                    $('#get_group_member_table').bootstrapTable('load', data);
                    $('#get_group_member_dialog').modal('show');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//修改群组成员角色
    var modifyGroupMemberRole = function () {
        var role_en = $('input[name="mgm_role_radio"]:checked').val();
        var role_zh = webim.Tool.groupRoleEn2Ch(role_en);
        var options = {
            'GroupId': $('#mgm_group_id').val(),
            'Member_Account': $('#mgm_account').val(),
            'Role': role_en
        };
        webim.modifyGroupMember(
                options,
                function (resp) {
                    //在表格中修改对应的行
                    $('#get_group_member_table').bootstrapTable('updateRow', {
                        index: $('#mgm_sel_row_index').val(),
                        row: {
                            Role: role_zh
                        }
                    });
                    $('#modify_group_member_dialog').modal('hide');
                    alert('修改群成员角色成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };
//设置成员禁言时间
    var forbidSendMsg = function () {
        if (!webim.Tool.validNumber($('#fsm_shut_up_time').val())) {
            alert('您输入的禁言时间非法,只能是数字(0-31536000)');
            return;
        }
        var shut_up_time = parseInt($('#fsm_shut_up_time').val());
        if (shut_up_time > 31536000) {
            alert('您输入的禁言时间非法,只能是数字(0-31536000)');
            return;
        }
        var shut_up_until = '-';
        if (shut_up_time != 0) {
            //当前时间+禁言时间=禁言截至时间
            shut_up_until = webim.Tool.formatTimeStamp(Math.round(new Date().getTime() / 1000) + shut_up_time);
        }
        var options = {
            'GroupId': $('#fsm_group_id').val(), //群id
            'Members_Account': [$('#fsm_account').val()], //被禁言的成员帐号列表
            'ShutUpTime': shut_up_time//禁言时间，单位：秒
        };
        webim.forbidSendMsg(
                options,
                function (resp) {
                    //在表格中修改对应的行
                    $('#get_group_member_table').bootstrapTable('updateRow', {
                        index: $('#fsm_sel_row_index').val(),
                        row: {
                            ShutUpUntil: shut_up_until
                        }
                    });
                    $('#forbid_send_msg_dialog').modal('hide');
                    alert('设置成员禁言时间成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };

//修改群消息提示类型
    var modifyGroupMsgFlag = function () {
        var msg_flag_en = $('input[name="mgmf_msg_flag_radio"]:checked').val();
        var msg_flag_zh = webim.Tool.groupMsgFlagEn2Ch(msg_flag_en);
        var options = {
            'GroupId': $('#mgmf_group_id').val(),
            'Member_Account': loginInfo.identifier,
            'MsgFlag': msg_flag_en
        };
        webim.modifyGroupMember(
                options,
                function (resp) {
                    //在表格中修改对应的行
                    $('#get_my_group_table').bootstrapTable('updateRow', {
                        index: $('#mgmf_sel_row_index').val(),
                        row: {
                            MsgFlag: msg_flag_zh,
                            MsgFlagEn: msg_flag_en
                        }
                    });
                    $('#modify_group_msg_flag_dialog').modal('hide');
                    alert('设置群消息提示类型成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };
//删除群组成员
    var deleteGroupMember = function () {
        if (!confirm("确定移除该成员吗？")) {
            return;
        }
        var options = {
            'GroupId': $('#dgm_group_id').val(),
            //'Silence': $('input[name="dgm_silence_radio"]:checked').val(),//只有ROOT用户采用权限设置该字段（是否静默移除）
            'MemberToDel_Account': [$('#dgm_account').val()]
        };
        webim.deleteGroupMember(
                options,
                function (resp) {
                    //在表格中删除对应的行
                    $('#get_group_member_table').bootstrapTable('remove', {
                        field: 'Member_Account',
                        values: [$('#dgm_account').val()]
                    });
                    $('#delete_group_member_dialog').modal('hide');
                    alert('移除群成员成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };
//邀请好友加群
    var addGroupMember = function () {
        var options = {
            'GroupId': $('#agm_group_id').val(),
            'MemberList': [
                {
                    'Member_Account': $('#agm_account').val()
                }

            ]
        };
        webim.addGroupMember(
                options,
                function (resp) {
                    //在表格中删除对应的行
                    $('#get_my_friend_group_table').bootstrapTable('remove', {
                        field: 'Info_Account',
                        values: [$('#agm_account').val()]
                    });
                    $('#add_group_member_dialog').modal('hide');
                    alert('邀请好友加群成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };






    // 加群申请管理 api 示例代码
    //js/group/group_pendency_manager.js
//定义我的加群申请表格每行按钮
    function gajgpOperateFormatter(value, row, index) {
        return [
            '<a class="edit" href="javascript:void(0)" title="处理">',
            '<i class="glyphicon glyphicon-edit"></i>',
            '</a>'

        ].join('');
    }
//我的加群申请表格每行的按钮点击事件
    window.gajgpOperateEvents = {
        'click .edit': function (e, value, row, index) {

            $("#hajg_group_id").val(row.GroupId);
            $("#hajg_to_account").val(row.Operator_Account);
            $("#hajg_msg_key").val(row.MsgKey);
            $("#hajg_authentication").val(row.Authentication);
            $("#hajg_user_defined_field").val(row.UserDefinedField);

            $("#hajg_from_account").val(row.From_Account);
            $("#hajg_msg_seq").val(row.MsgSeq);
            $("#hajg_msg_random").val(row.MsgRandom);

            $('#handle_ajg_dialog').modal('show');
        }
    };
//初始化我的加群申请表格
    function initGetApplyJoinGroupPendency(data) {
        $('#get_apply_join_group_pendency_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "GroupId", title: "群ID", align: "center", valign: "middle", sortable: "true"},
                {field: "GroupName", title: "群名称", align: "center", valign: "middle", sortable: "true"},
                {field: "Operator_Account", title: "对方账号", align: "center", valign: "middle", sortable: "true"},
                //{field: "ApplyNick", title: "对方昵称", align: "center", valign: "middle", sortable: "true"},

                {field: "MsgTime", title: "申请时间", align: "center", valign: "middle", sortable: "true"},
                {field: "RemarkInfo", title: "附言", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgKey", title: "MsgKey", align: "center", valign: "middle", sortable: "true", visible: false},
                {
                    field: "Authentication",
                    title: "Authentication",
                    align: "center",
                    valign: "middle",
                    sortable: "true",
                    visible: false
                },
                {
                    field: "UserDefinedField",
                    title: "UserDefinedField",
                    align: "center",
                    valign: "middle",
                    sortable: "true",
                    visible: false
                },
                {field: "From_Account", title: "发送者", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "MsgSeq", title: "消息序号", align: "center", valign: "middle", sortable: "true", visible: false},
                {field: "MsgRandom", title: "消息随机数", align: "center", valign: "middle", sortable: "true", visible: false},
                {
                    field: "gajgpOperate",
                    title: "操作",
                    align: "center",
                    valign: "middle",
                    formatter: "gajgpOperateFormatter",
                    events: "gajgpOperateEvents"
                }
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }


//查看未决加群申请
    var getApplyJoinGroupPendency = function () {
        $('#get_apply_join_group_pendency_dialog').modal('show');
    };

//处理加群申请
    var handleApplyJoinGroupPendency = function () {

        var options = {
            'GroupId': $("#hajg_group_id").val(), //群id
            'Applicant_Account': $("#hajg_to_account").val(), //申请人id
            'HandleMsg': $('input[name="hajg_action_radio"]:checked').val(), //Agree-同意 Reject-拒绝
            'Authentication': $("#hajg_authentication").val(), //申请凭证
            'MsgKey': $("#hajg_msg_key").val(),
            'ApprovalMsg': $("#hajg_approval_msg").val(), //处理附言
            'UserDefinedField': $("#hajg_group_id").val()//用户自定义字段
        };

        //要删除的群未决消息
        var delApplyJoinGroupPendencys = {
            'DelMsgList': [
                {
                    "From_Account": $("#hajg_from_account").val(),
                    "MsgSeq": parseInt($("#hajg_msg_seq").val()),
                    "MsgRandom": parseInt($("#hajg_msg_random").val())
                }
            ]
        };


        webim.handleApplyJoinGroupPendency(
                options,
                function (resp) {
                    //在表格中删除对应的行
                    $('#get_apply_join_group_pendency_table').bootstrapTable('remove', {
                        field: 'Authentication',
                        values: [$("#hajg_authentication").val()]
                    });
                    $('#handle_ajg_dialog').modal('hide');

                    //删除已处理的加群未决消息，否则下次登录的时候会重复收到加群未决消息
                    deleteApplyJoinGroupPendency(delApplyJoinGroupPendencys);

                    alert('处理加群申请成功');

                },
                function (err) {

                    alert(err.ErrorInfo);
                }
        );
    };

//删除已处理的加群未决消息
    var deleteApplyJoinGroupPendency = function (opts) {

        webim.deleteApplyJoinGroupPendency(opts,
                function (resp) {
                    webim.Log.info('delete group pendency msg success');
                },
                function (err) {
                    alert(err.ErrorInfo);
                    webim.Log.error('delete group pendency msg failed');
                }
        );
        return;
    };







    // 切换聊天好友或群组 示例代码
    //js/switch_chat_obj.js
//更新最近会话的未读消息数
    function updateSessDiv(sess_type, to_id, name, unread_msg_count) {
        var badgeDiv = document.getElementById("badgeDiv_" + to_id);
        if (badgeDiv && unread_msg_count > 0) {
            if (unread_msg_count >= 100) {
                unread_msg_count = '99+';
            }
            badgeDiv.innerHTML = "<span>" + unread_msg_count + "</span>";
            badgeDiv.style.display = "block";
        } else if (badgeDiv == null) { //没有找到对应的聊天id
            var headUrl;
            if (sess_type == webim.SESSION_TYPE.C2C) {
                headUrl = friendHeadUrl;
            } else {
                headUrl = groupHeadUrl;
            }
            addSess(sess_type, to_id, name, headUrl, unread_msg_count, 'sesslist');
        }
    }

//新增一条最近会话
    function addSess(sess_type, to_id, name, face_url, unread_msg_count, sesslist, addPositonType) {
        var sessDivId = "sessDiv_" + to_id;
        var sessDiv = document.getElementById(sessDivId);
        if (sessDiv) { //先判断是否存在会话DIV，已经存在，则不需要增加
            return;
        }
        var sessList = document.getElementsByClassName(sesslist)[0];
        sessDiv = document.createElement("div");
        sessDiv.id = sessDivId;
        //如果当前选中的用户是最后一个用户
        sessDiv.className = "sessinfo";
        //添加单击用户头像事件
        sessDiv.onclick = function () {
            if (sessDiv.className == "sessinfo-sel")
                return;
            onSelSess(sess_type, to_id);
        };
        var faceImg = document.createElement("img");
        faceImg.id = "faceImg_" + to_id;
        faceImg.className = "face";
        faceImg.src = face_url;

        if (name.length > maxNameLen) { //名称过长，截取一部分
            name = name.substr(0, maxNameLen) + "...";
        }

        var nameDiv = document.createElement("div");
        nameDiv.id = "nameDiv_" + to_id;
        nameDiv.className = "name";
        nameDiv.innerHTML = name;
        var badgeDiv = document.createElement("div");
        badgeDiv.id = "badgeDiv_" + to_id;
        badgeDiv.className = "badge";
        if (unread_msg_count > 0) {
            if (unread_msg_count >= 100) {
                unread_msg_count = '99+';
            }
            badgeDiv.innerHTML = "<span>" + unread_msg_count + "</span>";
            badgeDiv.style.display = "block";
        }
        sessDiv.appendChild(faceImg);
        sessDiv.appendChild(nameDiv);
        sessDiv.appendChild(badgeDiv);
        if (!addPositonType || addPositonType == 'TAIL') {
            sessList.appendChild(sessDiv); //默认插入尾部
        } else if (addPositonType == 'HEAD') {
            sessList.insertBefore(sessDiv); //插入头部
        } else {
            console.log(webim.Log.error('未知addPositonType' + addPositonType));
        }
    }

//切换好友或群组聊天对象
    function onSelSess(sess_type, to_id) {
        if (selToID != null) {

            //将之前选中用户的样式置为未选中样式
            setSelSessStyleOff(selToID);

            //设置之前会话的已读消息标记
            webim.setAutoRead(selSess, false, false);

            //保存当前的消息输入框内容到草稿
            //获取消息内容
            var msgtosend = document.getElementsByClassName("msgedit")[0].value;
            var msgLen = webim.Tool.getStrBytes(msgtosend);
            if (msgLen > 0) {
                webim.Tool.setCookie("tmpmsg_" + selToID, msgtosend, 3600);
            }

            //清空聊天界面
            document.getElementsByClassName("msgflow")[0].innerHTML = "";

            selToID = to_id;
            //设置当前选中用户的样式为选中样式
            setSelSessStyleOn(to_id);

            var tmgmsgtosend = webim.Tool.getCookie("tmpmsg_" + selToID);
            if (tmgmsgtosend) {
                $("#send_msg_text").val(tmgmsgtosend);
            } else {
                $("#send_msg_text").val('');
            }

            bindScrollHistoryEvent.reset();


            if (sess_type == webim.SESSION_TYPE.GROUP) {
                if (selType == webim.SESSION_TYPE.C2C) {
                    selType = webim.SESSION_TYPE.GROUP;
                }
                selSess = null;
                webim.MsgStore.delSessByTypeId(selType, selToID);

                getLastGroupHistoryMsgs(function (msgList) {
                    getHistoryMsgCallback(msgList);
                    bindScrollHistoryEvent.init();
                }, function (err) {
                    alert(err.ErrorInfo);
                });
            } else {
                if (selType == webim.SESSION_TYPE.GROUP) {
                    selType = webim.SESSION_TYPE.C2C;
                }

                //如果是管理员账号，则为全员推送，没有历史消息
                if (selToID == AdminAcount) {
                    var sess = webim.MsgStore.sessByTypeId(selType, selToID);
                    if (sess && sess.msgs() && sess.msgs().length > 0) {
                        getHistoryMsgCallback(sess.msgs());
                    } else {
                        getLastC2CHistoryMsgs(function (msgList) {
                            getHistoryMsgCallback(msgList);
                            bindScrollHistoryEvent.init();
                        }, function (err) {
                            alert(err.ErrorInfo);
                        });
                    }
                    return;
                }

                //拉取漫游消息
                getLastC2CHistoryMsgs(function (msgList) {
                    getHistoryMsgCallback(msgList);
                    //绑定滚动操作
                    bindScrollHistoryEvent.init();
                }, function (err) {
                    alert(err.ErrorInfo);
                });
            }
        }
    }

//删除会话
    function deleteSessDiv(sess_type, to_id) {
        var sessDiv = document.getElementById("sessDiv_" + to_id);
        sessDiv && sessDiv.parentNode.removeChild(sessDiv);
    }


//更新最近会话的名字
    function updateSessNameDiv(sess_type, to_id, newName) {

        var nameDivId = "nameDiv_" + to_id;
        var nameDiv = document.getElementById(nameDivId);
        if (nameDiv) {
            if (newName.length > maxNameLen) { //帐号或昵称过长，截取一部分
                newName = newName.substr(0, maxNameLen) + "...";
            }
            nameDiv.innerHTML = webim.Tool.formatText2Html(newName);
        }
    }

//更新最近会话的头像
    function updateSessImageDiv(sess_type, to_id, newImageUrl) {
        if (!newImageUrl) {
            return;
        }
        var faceImageId = "faceImg_" + to_id;
        var faceImage = document.getElementById(faceImageId);
        if (faceImage) {
            nameDiv.innerHTML = webim.Tool.formatText2Html(newName);
        }
    }

    function setSelSessStyleOn(newSelToID) {

        var selSessDiv = document.getElementById("sessDiv_" + newSelToID);
        if (selSessDiv) {
            selSessDiv.className = "sessinfo-sel"; //设置当前选中用户的样式为选中样式
        } else {
            webim.Log.warn("不存在selSessDiv: selSessDivId=" + "sessDiv_" + newSelToID);
        }

        var selBadgeDiv = document.getElementById("badgeDiv_" + newSelToID);
        if (selBadgeDiv) {
            selBadgeDiv.style.display = "none";
        } else {
            webim.Log.warn("不存在selBadgeDiv: selBadgeDivId=" + "badgeDiv_" + selToID);
        }
    }

    function setSelSessStyleOff(preSelToId) {
        var preSessDiv = document.getElementById("sessDiv_" + preSelToId);
        if (preSessDiv) {
            preSessDiv.className = "sessinfo"; //将之前选中用户的样式置为未选中样式
        } else {
            webim.Log.warn("不存在preSessDiv: selSessDivId=" + "sessDiv_" + preSelToId);
        }
    }





    // 获取c2c获取群组历史消息 示例代码
    //js/msg/get_history_msg.js
//获取历史消息(c2c或者group)成功回调函数
//msgList 为消息数组，结构为[Msg]
    function getHistoryMsgCallback(msgList, prepage) {
        var msg;
        prepage = prepage || false;

        //如果是加载前几页的消息，消息体需要prepend，所以先倒排一下
        if (prepage) {
            msgList.reverse();
        }

        for (var j in msgList) {//遍历新消息
            msg = msgList[j];
            if (msg.getSession().id() == selToID) {//为当前聊天对象的消息
                selSess = msg.getSession();
                //在聊天窗体中新增一条消息
                addMsg(msg, prepage);
            }
        }
        //消息已读上报，并将当前会话的消息设置成自动已读
        webim.setAutoRead(selSess, true, true);
    }

//获取最新的c2c历史消息,用于切换好友聊天，重新拉取好友的聊天消息
    var getLastC2CHistoryMsgs = function (cbOk, cbError) {
        if (selType == webim.SESSION_TYPE.GROUP) {
            alert('当前的聊天类型为群聊天，不能进行拉取好友历史消息操作');
            return;
        }
        if (!selToID || selToID == '@TIM#SYSTEM') {
            alert('当前的聊天id非法，selToID=' + selToID);
            return;
        }
        var lastMsgTime = 0;//第一次拉取好友历史消息时，必须传0
        var msgKey = '';
        var options = {
            'Peer_Account': selToID, //好友帐号
            'MaxCnt': reqMsgCount, //拉取消息条数
            'LastMsgTime': lastMsgTime, //最近的消息时间，即从这个时间点向前拉取历史消息
            'MsgKey': msgKey
        };
        selSess = null;
        webim.MsgStore.delSessByTypeId(selType, selToID);


        webim.getC2CHistoryMsgs(
                options,
                function (resp) {
                    var complete = resp.Complete;//是否还有历史消息可以拉取，1-表示没有，0-表示有

                    if (resp.MsgList.length == 0) {
                        webim.Log.warn("没有历史消息了:data=" + JSON.stringify(options));
                        return;
                    }
                    getPrePageC2CHistroyMsgInfoMap[selToID] = {//保留服务器返回的最近消息时间和消息Key,用于下次向前拉取历史消息
                        'LastMsgTime': resp.LastMsgTime,
                        'MsgKey': resp.MsgKey
                    };
                    //清空聊天界面
                    document.getElementsByClassName("msgflow")[0].innerHTML = "";
                    if (cbOk)
                        cbOk(resp.MsgList);
                },
                cbError
                );
    };

//向上翻页，获取更早的好友历史消息
    var getPrePageC2CHistoryMsgs = function (cbOk, cbError) {
        if (selType == webim.SESSION_TYPE.GROUP) {
            alert('当前的聊天类型为群聊天，不能进行拉取好友历史消息操作');
            return;
        }
        var tempInfo = getPrePageC2CHistroyMsgInfoMap[selToID];//获取下一次拉取的c2c消息时间和消息Key
        var lastMsgTime;
        var msgKey;
        if (tempInfo) {
            lastMsgTime = tempInfo.LastMsgTime;
            msgKey = tempInfo.MsgKey;
        } else {
            alert('获取下一次拉取的c2c消息时间和消息Key为空');
            return;
        }
        var options = {
            'Peer_Account': selToID, //好友帐号
            'MaxCnt': reqMsgCount, //拉取消息条数
            'LastMsgTime': lastMsgTime, //最近的消息时间，即从这个时间点向前拉取历史消息
            'MsgKey': msgKey
        };
        webim.getC2CHistoryMsgs(
                options,
                function (resp) {
                    var complete = resp.Complete;//是否还有历史消息可以拉取，1-表示没有，0-表示有
                    if (resp.MsgList.length == 0) {
                        webim.Log.warn("没有历史消息了:data=" + JSON.stringify(options));
                        return;
                    }
                    getPrePageC2CHistroyMsgInfoMap[selToID] = {//保留服务器返回的最近消息时间和消息Key,用于下次向前拉取历史消息
                        'LastMsgTime': resp.LastMsgTime,
                        'MsgKey': resp.MsgKey
                    };
                    if (cbOk) {
                        cbOk(resp.MsgList);
                    } else {
                        getHistoryMsgCallback(resp.MsgList, true);
                    }
                },
                cbError
                );
    };

//获取最新的群历史消息,用于切换群组聊天时，重新拉取群组的聊天消息
    var getLastGroupHistoryMsgs = function (cbOk) {
        if (selType == webim.SESSION_TYPE.C2C) {
            alert('当前的聊天类型为好友聊天，不能进行拉取群历史消息操作');
            return;
        }
        getGroupInfo(selToID, function (resp) {
            //拉取最新的群历史消息
            var options = {
                'GroupId': selToID,
                'ReqMsgSeq': resp.GroupInfo[0].NextMsgSeq - 1,
                'ReqMsgNumber': reqMsgCount
            };
            if (options.ReqMsgSeq == null || options.ReqMsgSeq == undefined || options.ReqMsgSeq <= 0) {
                webim.Log.warn("该群还没有历史消息:options=" + JSON.stringify(options));
                return;
            }
            selSess = null;
            webim.MsgStore.delSessByTypeId(selType, selToID);
            recentSessMap[webim.SESSION_TYPE.GROUP + "_" + selToID].MsgGroupReadedSeq = resp.GroupInfo[0].MsgSeq;
            webim.syncGroupMsgs(
                    options,
                    function (msgList) {
                        if (msgList.length == 0) {
                            webim.Log.warn("该群没有历史消息了:options=" + JSON.stringify(options));
                            return;
                        }
                        var msgSeq = msgList[0].seq - 1;
                        getPrePageGroupHistroyMsgInfoMap[selToID] = {
                            "ReqMsgSeq": msgSeq
                        };
                        //清空聊天界面
                        document.getElementsByClassName("msgflow")[0].innerHTML = "";
                        if (cbOk)
                            cbOk(msgList);
                    },
                    function (err) {
                        alert(err.ErrorInfo);
                    }
            );
        });
    };

//向上翻页，获取更早的群历史消息
    var getPrePageGroupHistoryMsgs = function (cbOk) {
        if (selType == webim.SESSION_TYPE.C2C) {
            alert('当前的聊天类型为好友聊天，不能进行拉取群历史消息操作');
            return;
        }
        var tempInfo = getPrePageGroupHistroyMsgInfoMap[selToID];//获取下一次拉取的群消息seq
        var reqMsgSeq;
        if (tempInfo) {
            reqMsgSeq = tempInfo.ReqMsgSeq;
            if (reqMsgSeq <= 0) {
                webim.Log.warn('该群没有历史消息可拉取了');
                return;
            }
        } else {
            webim.Log.error('获取下一次拉取的群消息seq为空');
            return;
        }
        var options = {
            'GroupId': selToID,
            'ReqMsgSeq': reqMsgSeq,
            'ReqMsgNumber': reqMsgCount
        };

        webim.syncGroupMsgs(
                options,
                function (msgList) {
                    if (msgList.length == 0) {
                        webim.Log.warn("该群没有历史消息了:options=" + JSON.stringify(options));
                        return;
                    }
                    var msgSeq = msgList[0].seq - 1;
                    getPrePageGroupHistroyMsgInfoMap[selToID] = {
                        "ReqMsgSeq": msgSeq
                    };

                    if (cbOk) {
                        cbOk(msgList);
                    } else {
                        getHistoryMsgCallback(msgList, true);
                    }
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };





    // 发送普通消息(文本和表情) api 示例代码
    //js/msg/send_common_msg.js
//发送消息(文本或者表情)
    function onSendMsg() {

        if (!selToID) {
            alert("你还没有选中好友或者群组，暂不能聊天");
            $("#send_msg_text").val('');
            return;
        }
        //获取消息内容
        var msgContent = document.getElementsByClassName("msgedit")[0].value;
        var msgLen = webim.Tool.getStrBytes(msgContent);

        if (msgContent.length < 1) {
            alert("发送的消息不能为空!");
            $("#send_msg_text").val('');
            return;
        }
        var maxLen, errInfo;
        if (selType == webim.SESSION_TYPE.C2C) {
            maxLen = webim.MSG_MAX_LENGTH.C2C;
            errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";
        } else {
            maxLen = webim.MSG_MAX_LENGTH.GROUP;
            errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";
        }
        if (msgLen > maxLen) {
            alert(errInfo);
            return;
        }
        //发消息处理
        handleMsgSend(msgContent);
    }


    function handleMsgSend(msgContent) {
        if (!selSess) {
            var selSess = new webim.Session(selType, selToID, selToID, friendHeadUrl, Math.round(new Date().getTime() / 1000));
        }
        var isSend = true;//是否为自己发送
        var seq = -1;//消息序列，-1表示sdk自动生成，用于去重
        var random = Math.round(Math.random() * 4294967296);//消息随机数，用于去重
        var msgTime = Math.round(new Date().getTime() / 1000);//消息时间戳
        var subType;//消息子类型
        if (selType == webim.SESSION_TYPE.C2C) {
            subType = webim.C2C_MSG_SUB_TYPE.COMMON;
        } else {
            //webim.GROUP_MSG_SUB_TYPE.COMMON-普通消息,
            //webim.GROUP_MSG_SUB_TYPE.LOVEMSG-点赞消息，优先级最低
            //webim.GROUP_MSG_SUB_TYPE.TIP-提示消息(不支持发送，用于区分群消息子类型)，
            //webim.GROUP_MSG_SUB_TYPE.REDPACKET-红包消息，优先级最高
            subType = webim.GROUP_MSG_SUB_TYPE.COMMON;
        }
        var msg = new webim.Msg(selSess, isSend, seq, random, msgTime, loginInfo.identifier, subType, loginInfo.identifierNick);

        var text_obj, face_obj, tmsg, emotionIndex, emotion, restMsgIndex;
        //解析文本和表情
        var expr = /\[[^[\]]{1,3}\]/mg;
        var emotions = msgContent.match(expr);
        if (!emotions || emotions.length < 1) {
            text_obj = new webim.Msg.Elem.Text(msgContent);
            msg.addText(text_obj);
        } else {
            for (var i = 0; i < emotions.length; i++) {
                tmsg = msgContent.substring(0, msgContent.indexOf(emotions[i]));
                if (tmsg) {
                    text_obj = new webim.Msg.Elem.Text(tmsg);
                    msg.addText(text_obj);
                }
                emotionIndex = webim.EmotionDataIndexs[emotions[i]];
                emotion = webim.Emotions[emotionIndex];

                if (emotion) {
                    face_obj = new webim.Msg.Elem.Face(emotionIndex, emotions[i]);
                    msg.addFace(face_obj);
                } else {
                    text_obj = new webim.Msg.Elem.Text(emotions[i]);
                    msg.addText(text_obj);
                }
                restMsgIndex = msgContent.indexOf(emotions[i]) + emotions[i].length;
                msgContent = msgContent.substring(restMsgIndex);
            }
            if (msgContent) {
                text_obj = new webim.Msg.Elem.Text(msgContent);
                msg.addText(text_obj);
            }
        }
        msg.sending = 1;
        msg.originContent = msgContent;
        addMsg(msg);
        $("#send_msg_text").val('');
        turnoffFaces_box();

        webim.sendMsg(msg, function (resp) {
            //发送成功，把sending清理
            $("#id_" + msg.random).find(".spinner").remove();
            webim.Tool.setCookie("tmpmsg_" + selToID, '', 0);
        }, function (err) {
            //alert(err.ErrorInfo);
            //提示重发
            showReSend(msg);
        });
    }

    function showReSend(msg) {
        //resend a dom
        var resendBtn = $('<a href="javascript:;">重发</a>');
        //绑定重发事件
        resendBtn.click(function () {
            //删除当前的dom
            $("#id_" + msg.random).remove();
            //发消息处理
            handleMsgSend(msg.originContent);
        });
        $("#id_" + msg.random).find(".spinner").empty().append(resendBtn);
    }


//发送文本框按下键盘事件
    function onTextareaKeyDown() {
        if (event.keyCode == 13) {
            onSendMsg();
        }
    }
//关闭聊天界面
    function onClose() {
        document.getElementById("webim_demo").style.display = "none";
        //离线
        webim.logout();
    }

//打开表情窗体
    var showEmotionDialog = function () {
        if (emotionFlag) {
            $('#wl_faces_box').css({
                "display": "block"
            });
            return;
        }
        emotionFlag = true;

        for (var index in webim.Emotions) {
            var emotions = $('<img>').attr({
                "id": webim.Emotions[index][0],
                "src": webim.Emotions[index][1],
                "style": "cursor:pointer;"
            }).click(function () {
                selectEmotionImg(this);
            });
            $('<li>').append(emotions).appendTo($('#emotionUL'));
        }
        $('#wl_faces_box').css({
            "display": "block"
        });
    };

//表情选择div的关闭方法
    var turnoffFaces_box = function () {
        $("#wl_faces_box").fadeOut("slow");
    };
//选中表情
    var selectEmotionImg = function (selImg) {
        var txt = document.getElementsByClassName("msgedit")[0];
        txt.value = txt.value + selImg.id;
        txt.focus();
    };





    // 上传和发送图片消息 api 示例代码
    //js/msg/upload_and_send_pic_msg.js
//弹出发图对话框
    function selectPicClick() {
        //判断浏览器版本
        if (webim.BROWSER_INFO.type == 'ie' && parseInt(webim.BROWSER_INFO.ver) <= 9) {
            //if(1==1){
            $('#updli_form')[0].reset();
            $('#upload_pic_low_ie_dialog').modal('show');
        } else {
            $('#upd_form')[0].reset();
            var preDiv = document.getElementById('previewPicDiv');
            preDiv.innerHTML = '';
            var progress = document.getElementById('upd_progress');//上传图片进度条
            progress.value = 0;
            $('#upload_pic_dialog').modal('show');
        }
    }
//选择图片触发事件
    function fileOnChange(uploadFile) {

        if (!window.File || !window.FileList || !window.FileReader) {
            alert("您的浏览器不支持File Api");
            return;
        }

        var file = uploadFile.files[0];
        var fileSize = file.size;

        //先检查图片类型和大小
        if (!checkPic(uploadFile, fileSize)) {
            return;
        }

        //预览图片
        var reader = new FileReader();
        var preDiv = document.getElementById('previewPicDiv');
        reader.onload = (function (file) {
            return function (e) {
                preDiv.innerHTML = '';
                var span = document.createElement('span');
                span.innerHTML = '<img class="img-responsive" src="' + this.result + '" alt="' + file.name + '" />';
                //span.innerHTML = '<img class="img-thumbnail" src="' + this.result + '" alt="' + file.name + '" />';
                preDiv.insertBefore(span, null);
            };
        })(file);
        //预览图片
        reader.readAsDataURL(file);
    }

//上传图片进度条回调函数
//loadedSize-已上传字节数
//totalSize-图片总字节数
    function onProgressCallBack(loadedSize, totalSize) {
        var progress = document.getElementById('upd_progress');//上传图片进度条
        progress.value = (loadedSize / totalSize) * 100;
    }

//上传图片
    function uploadPic() {
        var uploadFiles = document.getElementById('upd_pic');
        var file = uploadFiles.files[0];
        var businessType;//业务类型，1-发群图片，2-向好友发图片
        if (selType == webim.SESSION_TYPE.C2C) {//向好友发图片
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.C2C_MSG;
        } else if (selType == webim.SESSION_TYPE.GROUP) {//发群图片
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.GROUP_MSG;
        }
        //封装上传图片请求
        var opt = {
            'file': file, //图片对象
            'onProgressCallBack': onProgressCallBack, //上传图片进度条回调函数
            //'abortButton': document.getElementById('upd_abort'), //停止上传图片按钮
            'To_Account': selToID, //接收者
            'businessType': businessType//业务类型
        };
        //上传图片
        webim.uploadPic(opt,
                function (resp) {
                    //上传成功发送图片
                    sendPic(resp, file.name);
                    $('#upload_pic_dialog').modal('hide');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    }

//上传图片(用于低版本IE)
    function uploadPicLowIE() {
        var businessType;//业务类型，1-发群图片，2-向好友发图片
        if (selType == webim.SESSION_TYPE.C2C) {//向好友发图片
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.C2C_MSG;
        } else if (selType == webim.SESSION_TYPE.GROUP) {//发群图片
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.GROUP_MSG;
        }
        //封装上传图片请求
        var opt = {
            'formId': 'updli_form', //上传图片表单id
            'fileId': 'updli_file', //file控件id
            'To_Account': selToID, //接收者
            'businessType': businessType//图片的使用业务类型
        };
        webim.submitUploadFileForm(opt,
                function (resp) {
                    $('#upload_pic_low_ie_dialog').modal('hide');
                    //发送图片
                    sendPic(resp);
                },
                function (err) {
                    $('#upload_pic_low_ie_dialog').modal('hide');
                    alert(err.ErrorInfo);
                }
        );
    }

//上传图片(通过base64编码)
    function uploadPicByBase64() {
        var businessType;//业务类型，1-发群图片，2-向好友发图片
        if (selType == webim.SESSION_TYPE.C2C) {//向好友发图片
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.C2C_MSG;
        } else if (selType == webim.SESSION_TYPE.GROUP) {//发群图片
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.GROUP_MSG;
        }
        //封装上传图片请求
        var opt = {
            'toAccount': selToID, //接收者
            'businessType': businessType, //图片的使用业务类型
            'fileMd5': '6f25dc54dc2cd47375e8b43045de642a', //图片md5
            'totalSize': 56805, //图片大小,Byte
            'base64Str': //图片base64编码
                    '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'
        };
        webim.uploadPicByBase64(opt,
                function (resp) {
                    //alert('success');
                    //发送图片
                    sendPic(resp);
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    }
//发送图片消息
    function sendPic(images, imgName) {
        if (!selToID) {
            alert("您还没有好友，暂不能聊天");
            return;
        }

        if (!selSess) {
            selSess = new webim.Session(selType, selToID, selToID, friendHeadUrl, Math.round(new Date().getTime() / 1000));
        }
        var msg = new webim.Msg(selSess, true, -1, -1, -1, loginInfo.identifier, 0, loginInfo.identifierNick);
        var images_obj = new webim.Msg.Elem.Images(images.File_UUID);
        for (var i in images.URL_INFO) {
            var img = images.URL_INFO[i];
            var newImg;
            var type;
            switch (img.PIC_TYPE) {
                case 1://原图
                    type = 1;//原图
                    break;
                case 2://小图（缩略图）
                    type = 3;//小图
                    break;
                case 4://大图
                    type = 2;//大图
                    break;
            }
            newImg = new webim.Msg.Elem.Images.Image(type, img.PIC_Size, img.PIC_Width, img.PIC_Height, img.DownUrl);
            images_obj.addImage(newImg);
        }
        msg.addImage(images_obj);
        //if(imgName){
        //    var data=imgName;//通过自定义消息中的data字段保存图片名称
        //    var custom_obj = new webim.Msg.Elem.Custom(data, '', '');
        //    msg.addCustom(custom_obj);
        //}
        //调用发送图片消息接口
        webim.sendMsg(msg, function (resp) {
            if (selType == webim.SESSION_TYPE.C2C) {//私聊时，在聊天窗口手动添加一条发的消息，群聊时，长轮询接口会返回自己发的消息
                addMsg(msg);
            }
        }, function (err) {
            alert(err.ErrorInfo);
        });
    }
//检查文件类型和大小
    function checkPic(obj, fileSize) {
        var picExts = 'jpg|jpeg|png|bmp|gif|webp';
        var photoExt = obj.value.substr(obj.value.lastIndexOf(".") + 1).toLowerCase();//获得文件后缀名
        var pos = picExts.indexOf(photoExt);
        if (pos < 0) {
            alert("您选中的文件不是图片，请重新选择");
            return false;
        }
        fileSize = Math.round(fileSize / 1024 * 100) / 100; //单位为KB
        if (fileSize > 30 * 1024) {
            alert("您选择的图片大小超过限制(最大为30M)，请重新选择");
            return false;
        }
        return true;
    }

//单击图片事件
    function imageClick(imgObj) {
        var imgUrls = imgObj.src;
        var imgUrlArr = imgUrls.split("#"); //字符分割
        var smallImgUrl = imgUrlArr[0];//小图
        var bigImgUrl = imgUrlArr[1];//大图
        var oriImgUrl = imgUrlArr[2];//原图
        var bigPicDiv = document.getElementById('bigPicDiv');
        bigPicDiv.innerHTML = '';
        var span = document.createElement('span');
        span.innerHTML = '<img class="img-thumbnail" src="' + bigImgUrl + '" />';
        bigPicDiv.insertBefore(span, null);
        $('#click_pic_dialog').modal('show');
    }








    // 上传和发送文件消息 api 示例代码
    //js/msg/upload_and_send_file_msg.js
//弹出发文件对话框
    function selectFileClick() {
        //判断浏览器版本
        if (webim.BROWSER_INFO.type == 'ie' && parseInt(webim.BROWSER_INFO.ver) <= 9) {
            alert('上传文件暂不支持ie9(含)以下浏览器');
            //$('#updli_file_form')[0].reset();
            //$('#upload_file_low_ie_dialog').modal('show');
        } else {
            $('#upd_file_form')[0].reset();
            //var preDiv = document.getElementById('previewFileDiv');
            //preDiv.innerHTML = '';
            var progress = document.getElementById('upd_file_progress');//上传文件进度条
            progress.value = 0;
            $('#upload_file_dialog').modal('show');
        }
    }

//上传文件进度条回调函数
//loadedSize-已上传字节数
//totalSize-文件总字节数
    function onFileProgressCallBack(loadedSize, totalSize) {
        var progress = document.getElementById('upd_file_progress');//上传文件进度条
        progress.value = (loadedSize / totalSize) * 100;
    }

//上传文件
    function uploadFile() {
        var uploadFiles = document.getElementById('upd_file');
        var file = uploadFiles.files[0];
        //先检查图片类型和大小
        if (!checkFile(file)) {
            return;
        }
        var businessType;//业务类型，1-发群文件，2-向好友发文件
        if (selType == webim.SESSION_TYPE.C2C) {//向好友发文件
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.C2C_MSG;
        } else if (selType == webim.SESSION_TYPE.GROUP) {//发群文件
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.GROUP_MSG;
        }

        //封装上传文件请求
        var opt = {
            'file': file, //文件对象
            'onProgressCallBack': onFileProgressCallBack, //上传文件进度条回调函数
            //'abortButton': document.getElementById('upd_abort'), //停止上传文件按钮
            'To_Account': selToID, //接收者
            'businessType': businessType, //业务类型
            'fileType': webim.UPLOAD_RES_TYPE.FILE//表示上传文件
        };
        //上传文件
        webim.uploadFile(opt,
                function (resp) {
                    //上传成功发送文件
                    sendFile(resp, file.name);
                    $('#upload_file_dialog').modal('hide');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    }

//上传文件(用于低版本IE)
    function uploadFileLowIE() {
        var businessType;//业务类型，1-发群文件，2-向好友发文件
        if (selType == webim.SESSION_TYPE.C2C) {//向好友发文件
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.C2C_MSG;
        } else if (selType == webim.SESSION_TYPE.GROUP) {//发群文件
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.GROUP_MSG;
        }
        //封装上传文件请求
        var opt = {
            'formId': 'updli_file_form', //上传文件表单id
            'fileId': 'upload_low_ie_file', //file控件id
            'To_Account': selToID, //接收者
            'businessType': businessType, //文件的使用业务类型
            'fileType': webim.UPLOAD_RES_TYPE.FILE//表示上传文件
        };
        webim.submitUploadFileForm(opt,
                function (resp) {
                    $('#upload_file_low_ie_dialog').modal('hide');
                    //发送文件
                    sendFile(resp);
                },
                function (err) {
                    $('#upload_file_low_ie_dialog').modal('hide');
                    alert(err.ErrorInfo);
                }
        );
    }

//上传文件(通过base64编码)
    function uploadFileByBase64() {
        var businessType;//业务类型，1-发群文件，2-向好友发文件
        if (selType == webim.SESSION_TYPE.C2C) {//向好友发文件
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.C2C_MSG;
        } else if (selType == webim.SESSION_TYPE.GROUP) {//发群文件
            businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.GROUP_MSG;
        }
        //封装上传文件请求
        var opt = {
            'toAccount': selToID, //接收者
            'businessType': businessType, //文件的使用业务类型
            'fileType': webim.UPLOAD_RES_TYPE.FILE, //表示文件
            'fileMd5': '6f25dc54dc2cd47375e8b43045de642a', //文件md5
            'totalSize': 56805, //文件大小,Byte
            'base64Str': //文件base64编码
                    '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'
        };
        webim.uploadPicByBase64(opt,
                function (resp) {
                    //alert('success');
                    //发送文件
                    sendFile(resp);
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    }
//发送文件消息
    function sendFile(file, fileName) {
        if (!selToID) {
            alert("您还没有好友，暂不能聊天");
            return;
        }

        if (!selSess) {
            selSess = new webim.Session(selType, selToID, selToID, friendHeadUrl, Math.round(new Date().getTime() / 1000));
        }
        var msg = new webim.Msg(selSess, true, -1, -1, -1, loginInfo.identifier, 0, loginInfo.identifierNick);
        var uuid = file.File_UUID;//文件UUID
        var fileSize = file.File_Size;//文件大小
        var senderId = loginInfo.identifier;
        var downloadFlag = file.Download_Flag;
        if (!fileName) {
            var random = Math.round(Math.random() * 4294967296);
            fileName = random.toString();
        }
        var fileObj = new webim.Msg.Elem.File(uuid, fileName, fileSize, senderId, selToID, downloadFlag, selType);
        msg.addFile(fileObj);
        //调用发送文件消息接口
        webim.sendMsg(msg, function (resp) {
            if (selType == webim.SESSION_TYPE.C2C) {//私聊时，在聊天窗口手动添加一条发的消息，群聊时，长轮询接口会返回自己发的消息
                addMsg(msg);
            }
        }, function (err) {
            alert(err.ErrorInfo);
        });
    }
//检查文件类型和大小
    function checkFile(file) {
        //var legalExts = 'jpg|jpeg|png|bmp|gif|webp|txt|doc|docx|xls|ppt|zip|rar|gz';
        //var ext = obj.value.substr(obj.value.lastIndexOf(".") + 1).toLowerCase();//获得文件后缀名
        //var pos = legalExts.indexOf(ext);
        //if (pos < 0) {
        //    alert("您选中的文件类型非法，请重新选择");
        //    return false;
        //}
        fileSize = Math.round(file.size / 1024 * 100) / 100; //单位为KB
        if (fileSize > 20 * 1024) {
            alert("您选择的文件大小超过限制(最大为20M)，请重新选择");
            return false;
        }
        return true;
    }








    // 切换播放语音消息 示例代码
    //js/msg/switch_play_sound_msg.js
//切换播放audio对象
    function onChangePlayAudio(playAudio) {
        if (curPlayAudio) {
            if (curPlayAudio != playAudio) {
                curPlayAudio.currentTime = 0;
                curPlayAudio.pause();
                curPlayAudio = playAudio;
            }
        } else {
            curPlayAudio = playAudio;
        }
    }









    // 发送自定义消息 api 示例代码
    //js/msg/send_custom_msg.js
//弹出发自定义消息对话框
    function showEditCustomMsgDialog() {
        $('#ecm_form')[0].reset();
        $('#edit_custom_msg_dialog').modal('show');
    }
//发送自定义消息
    function sendCustomMsg() {
        if (!selToID) {
            alert("您还没有好友或群组，暂不能聊天");
            return;
        }
        var data = $("#ecm_data").val();
        var desc = $("#ecm_desc").val();
        var ext = $("#ecm_ext").val();

        var msgLen = webim.Tool.getStrBytes(data);

        if (data.length < 1) {
            alert("发送的消息不能为空!");
            return;
        }
        var maxLen, errInfo;
        if (selType == webim.SESSION_TYPE.C2C) {
            maxLen = webim.MSG_MAX_LENGTH.C2C;
            errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";
        } else {
            maxLen = webim.MSG_MAX_LENGTH.GROUP;
            errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";
        }
        if (msgLen > maxLen) {
            alert(errInfo);
            return;
        }

        if (!selSess) {
            selSess = new webim.Session(selType, selToID, selToID, friendHeadUrl, Math.round(new Date().getTime() / 1000));
        }
        var msg = new webim.Msg(selSess, true, -1, -1, -1, loginInfo.identifier, 0, loginInfo.identifierNick);
        var custom_obj = new webim.Msg.Elem.Custom(data, desc, ext);
        msg.addCustom(custom_obj);
        //调用发送消息接口
        msg.sending = 1;
        webim.sendMsg(msg, function (resp) {
            addMsg(msg);
            // if (selType == webim.SESSION_TYPE.C2C) {
            //     //私聊时，在聊天窗口手动添加一条发的消息，群聊时，长轮询接口会返回自己发的消息
            //     addMsg(msg);
            // }
            $('#edit_custom_msg_dialog').modal('hide');
        }, function (err) {
            alert(err.ErrorInfo);
        });
    }









    // 发送群自定义通知 api 示例代码
    //js/msg/send_custom_group_notify_msg.js
//点击发送群自定义通知菜单事件
    function showSendCustomGroupNotifyDialog() {
        if (selType == webim.SESSION_TYPE.GROUP && selToID) {
            $("#sgsm_group_id").val(selToID);
        }
        $('#send_group_system_msg_dialog').modal('show');
    }

//发送自定义群系统通知
    var sendCustomGroupNotify = function () {

        var groupId = $("#sgsm_group_id").val();
        var content = $("#sgsm_content").val();

        if (content.length < 1) {
            alert("发送的消息不能为空!");
            return;
        }
        if (webim.Tool.getStrBytes(content) > webim.MSG_MAX_LENGTH.GROUP) {
            alert("消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)");
            return;
        }
        if (groupId.length < 1) {
            alert("群ID不能为空!");
            return;
        }
        //详细参数，请参考链接：http://avc.qcloud.com/wiki2.0/im/%E6%9C%8D%E5%8A%A1%E7%AB%AF%E9%9B%86%E6%88%90/%E7%BE%A4%E7%BB%84%E7%AE%A1%E7%90%86/%E5%9C%A8%E7%BE%A4%E7%BB%84%E4%B8%AD%E5%8F%91%E9%80%81%E7%B3%BB%E7%BB%9F%E9%80%9A%E7%9F%A5/%E5%9C%A8%E7%BE%A4%E7%BB%84%E4%B8%AD%E5%8F%91%E9%80%81%E7%B3%BB%E7%BB%9F%E9%80%9A%E7%9F%A5.html
        var options = {
            'GroupId': groupId,
            'Content': content
        };
        webim.sendCustomGroupNotify(
                options,
                function (resp) {
                    $('#send_group_system_msg_dialog').modal('hide');
                    alert('发送成功');
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
        );
    };





    // 监听新消息(c2c或群) 示例代码
    //js/msg/receive_new_msg.js

//监听新消息事件
//newMsgList 为新消息数组，结构为[Msg]
    function onMsgNotify(newMsgList) {
        //console.warn(newMsgList);
        var sess, newMsg;
        //获取所有聊天会话
        var sessMap = webim.MsgStore.sessMap();

        for (var j in newMsgList) {//遍历新消息
            newMsg = newMsgList[j];

            if (!selToID) {//没有聊天对象
                selToID = newMsg.getSession().id();
                selType = newMsg.getSession().type();
                selSess = newMsg.getSession();
                var headUrl;
                if (selType == webim.SESSION_TYPE.C2C) {
                    headUrl = friendHeadUrl;
                } else {
                    headUrl = groupHeadUrl;
                }
                addSess(selType, selToID, newMsg.getSession().name(), headUrl, 0, 'sesslist');//新增一个对象
                setSelSessStyleOn(selToID);
            }
            if (newMsg.getSession().id() == selToID) {//为当前聊天对象的消息
                //在聊天窗体中新增一条消息
                //console.warn(newMsg);
                addMsg(newMsg);
            }
        }
        //消息已读上报，以及设置会话自动已读标记
        webim.setAutoRead(selSess, true, true);

        for (var i in sessMap) {
            sess = sessMap[i];
            if (selToID != sess.id()) {//更新其他聊天对象的未读消息数
                updateSessDiv(sess.type(), sess.id(), sess.name(), sess.unread());
                console.debug(sess.unread());
                // stopPolling = true;
            }
        }
    }

//监听直播聊天室新消息事件
//newMsgList 为新消息数组，结构为[Msg]
//监听大群新消息（普通，点赞，提示，红包）
    function onBigGroupMsgNotify(newMsgList) {
        var newMsg;
        for (var i = newMsgList.length - 1; i >= 0; i--) {//遍历消息，按照时间从后往前
            newMsg = newMsgList[i];
            webim.Log.warn('receive a new group(AVChatRoom) msg: ' + newMsg.getFromAccountNick());
            //显示收到的消息
            addMsg(newMsg);
        }
    }


//处理已读消息
    function handleC2cMsgReadedNotify(item) {
        var sessMap = webim.MsgStore.sessMap()[webim.SESSION_TYPE.C2C + item.From_Account];
        if (sessMap) {
            var msgs = _.clone(sessMap.msgs());
            var rm_msgs = _.remove(msgs, function (m) {
                return m.time <= item.LastReadTime
            });
            var unread = sessMap.unread() - rm_msgs.length;
            unread = unread > 0 ? unread : 0;
            //更新sess的未读数
            sessMap.unread(unread);
            // console.debug('更新C2C未读数:',item.From_Account,unread);
            //更新页面的未读数角标
            if (unread > 0) {
                $("#badgeDiv_" + item.From_Account).text(unread).show();
            } else {
                $("#badgeDiv_" + item.From_Account).val("").hide();
            }
        }
    }
//消息已读通知
    function onMsgReadedNotify(notify) {
        _.each(notify.ReadC2cMsgNotify.UinPairReadArray, function (item) {
            handleC2cMsgReadedNotify(item);
        });
    }








    // 监听群系统通知消息 示例代码
    //js/msg/receive_group_system_msg.js
//监听 申请加群 系统消息
    function onApplyJoinGroupRequestNotify(notify) {
        webim.Log.info("执行 加群申请 回调：" + JSON.stringify(notify));
        var data = [];
        var timestamp = notify.MsgTime;
        notify.MsgTimeStamp = timestamp;
        notify.MsgTime = webim.Tool.formatTimeStamp(notify.MsgTime);
        data.push(notify);
        $('#get_apply_join_group_pendency_table').bootstrapTable('append', data);
        $('#get_apply_join_group_pendency_dialog').modal('show');

        var reportTypeCh = "[申请加群]";
        var content = notify.Operator_Account + "申请加入你的群";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, timestamp);
    }

//监听 申请加群被同意 系统消息
    function onApplyJoinGroupAcceptNotify(notify) {
        webim.Log.info("执行 申请加群被同意 回调：" + JSON.stringify(notify));
        //刷新我的群组列表
        //getJoinedGroupListHigh(getGroupsCallbackOK);

        var reportTypeCh = "[申请加群被同意]";
        var content = notify.Operator_Account + "同意你的加群申请，附言：" + notify.RemarkInfo;
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);

    }
//监听 申请加群被拒绝 系统消息
    function onApplyJoinGroupRefuseNotify(notify) {
        webim.Log.info("执行 申请加群被拒绝 回调：" + JSON.stringify(notify));
        var reportTypeCh = "[申请加群被拒绝]";
        var content = notify.Operator_Account + "拒绝了你的加群申请，附言：" + notify.RemarkInfo;
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 被踢出群 系统消息
    function onKickedGroupNotify(notify) {
        webim.Log.info("执行 被踢出群  回调：" + JSON.stringify(notify));
        //刷新我的群组列表
        //getJoinedGroupListHigh(getGroupsCallbackOK);
        deleteSessDiv(webim.SESSION_TYPE.GROUP, notify.GroupId);//在最近的联系人列表删除可能存在的群会话
        var reportTypeCh = "[被踢出群]";
        var content = "你被管理员" + notify.Operator_Account + "踢出该群";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 解散群 系统消息
    function onDestoryGroupNotify(notify) {
        webim.Log.info("执行 解散群 回调：" + JSON.stringify(notify));
        //刷新我的群组列表
        //getJoinedGroupListHigh(getGroupsCallbackOK);
        deleteSessDiv(webim.SESSION_TYPE.GROUP, notify.GroupId);//在最近的联系人列表删除可能存在的群会话
        var reportTypeCh = "[群被解散]";
        var content = "群主" + notify.Operator_Account + "已解散该群";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 创建群 系统消息
    function onCreateGroupNotify(notify) {
        webim.Log.info("执行 创建群 回调：" + JSON.stringify(notify));
        var reportTypeCh = "[创建群]";
        var content = "你创建了该群";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 被邀请加群 系统消息
    function onInvitedJoinGroupNotify(notify) {
        webim.Log.info("执行 被邀请加群  回调: " + JSON.stringify(notify));
        //刷新我的群组列表
        //getJoinedGroupListHigh(getGroupsCallbackOK);
        var reportTypeCh = "[被邀请加群]";
        var content = "你被管理员" + notify.Operator_Account + "邀请加入该群";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 主动退群 系统消息
    function onQuitGroupNotify(notify) {
        webim.Log.info("执行 主动退群  回调： " + JSON.stringify(notify));
        deleteSessDiv(webim.SESSION_TYPE.GROUP, notify.GroupId);//在最近的联系人列表删除可能存在的群会话
        var reportTypeCh = "[主动退群]";
        var content = "你退出了该群";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 被设置为管理员 系统消息
    function onSetedGroupAdminNotify(notify) {
        webim.Log.info("执行 被设置为管理员  回调：" + JSON.stringify(notify));
        var reportTypeCh = "[被设置为管理员]";
        var content = "你被群主" + notify.Operator_Account + "设置为管理员";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 被取消管理员 系统消息
    function onCanceledGroupAdminNotify(notify) {
        webim.Log.info("执行 被取消管理员 回调：" + JSON.stringify(notify));
        var reportTypeCh = "[被取消管理员]";
        var content = "你被群主" + notify.Operator_Account + "取消了管理员资格";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 群被回收 系统消息
    function onRevokeGroupNotify(notify) {
        webim.Log.info("执行 群被回收 回调：" + JSON.stringify(notify));
        //刷新我的群组列表
        //getJoinedGroupListHigh(getGroupsCallbackOK);
        deleteSessDiv(webim.SESSION_TYPE.GROUP, notify.GroupId);//在最近的联系人列表删除可能存在的群会话
        var reportTypeCh = "[群被回收]";
        var content = "该群已被回收";
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }
//监听 用户自定义 群系统消息
    function onCustomGroupNotify(notify) {
        webim.Log.info("执行 用户自定义系统消息 回调：" + JSON.stringify(notify));
        var reportTypeCh = "[用户自定义系统消息]";
        var content = notify.UserDefinedField;//群自定义消息数据
        addGroupSystemMsg(notify.ReportType, reportTypeCh, notify.GroupId, notify.GroupName, content, notify.MsgTime);
    }

//监听 群资料变化 群提示消息
    function onGroupInfoChangeNotify(notify) {
        webim.Log.info("执行 群资料变化 回调： " + JSON.stringify(notify));
        var groupId = notify.GroupId;
        var newFaceUrl = notify.GroupFaceUrl;//新群组图标, 为空，则表示没有变化
        var newName = notify.GroupName;//新群名称, 为空，则表示没有变化
        var newOwner = notify.OwnerAccount;//新的群主id, 为空，则表示没有变化
        var newNotification = notify.GroupNotification;//新的群公告, 为空，则表示没有变化
        var newIntroduction = notify.GroupIntroduction;//新的群简介, 为空，则表示没有变化

        if (newName) {
            updateSessNameDiv(webim.SESSION_TYPE.GROUP, groupId, newName);
        }
        if (newFaceUrl) {
            updateSessImageDiv(webim.SESSION_TYPE.GROUP, groupId, newFaceUrl);
        }
    }

//已读消息同步
//告诉你那个类型的那个群组已读消息的情况
    function onReadedSyncGroupNotify(notify) {
        var seq = notify.groupReportTypeMsg.MsgReadedSeq;

        //更新当前的seq
        var currentUnRead = recentSessMap[webim.SESSION_TYPE.GROUP + "_" + notify.GroupId].MsgGroupReadedSeq;
        var unread = seq - currentUnRead;
        unread = unread > 0 ? unread : 0;
        webim.Log.info("群消息同步的回调:已读消息情况 ## 未读数：GroupId:[" + notify.GroupId + "]:" + unread, currentUnRead, seq);
        if (unread > 0) {
            $(document.getElementById("badgeDiv_" + notify.GroupId)).text(unread).show();
        } else {
            $(document.getElementById("badgeDiv_" + notify.GroupId)).val("").hide();
        }
    }

//初始化我的群组系统消息表格
    function initGetMyGroupSystemMsgs(data) {
        $('#get_my_group_system_msgs_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "ReportType", title: "类型", align: "center", valign: "middle", sortable: "false", visible: false},
                {field: "ReportTypeCh", title: "类型", align: "center", valign: "middle", sortable: "true"},
                {field: "GroupId", title: "群ID", align: "center", valign: "middle", sortable: "true"},
                {field: "GroupName", title: "群名称", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgContent", title: "内容", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgTime", title: "时间", align: "center", valign: "middle", sortable: "true"}
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }

//查看我的群组系统消息
    var getMyGroupSystemMsgs = function () {
        $('#get_my_group_system_msgs_dialog').modal('show');
    };

//增加一条群组系统消息
    function addGroupSystemMsg(type, typeCh, group_id, group_name, msg_content, msg_time) {
        var data = [];
        data.push({
            "ReportType": type,
            "ReportTypeCh": typeCh,
            "GroupId": webim.Tool.formatText2Html(group_id),
            "GroupName": webim.Tool.formatText2Html(group_name),
            "MsgContent": webim.Tool.formatText2Html(msg_content),
            "MsgTime": webim.Tool.formatTimeStamp(msg_time)
        });
        $('#get_my_group_system_msgs_table').bootstrapTable('append', data);
        //$('#get_my_group_system_msgs_dialog').modal('show');
    }





    //监听好友系统通知消息 示例代码
    //js/msg/receive_friend_system_msg.js
//监听 好友表添加 系统通知
    /*notify对数示例：
     {
     'Type':1,//通知类型
     'Accounts':['jim','bob']//用户ID列表
     }
     */
    function onFriendAddNotify(notify) {
        webim.Log.info("执行 好友表添加 回调：" + JSON.stringify(notify));
        //好友表发生变化，需要重新加载好友列表或者单独添加notify.Accounts好友帐号
        //getAllFriend(getAllFriendsCallbackOK);
        var typeCh = "[好友表添加]";
        var content = "新增以下好友：" + notify.Accounts;
        addFriendSystemMsg(notify.Type, typeCh, content);
    }

//监听 好友表删除 系统通知
    /*notify对数示例：
     {
     'Type':2,//通知类型
     'Accounts':['jim','bob']//用户ID列表
     }
     */
    function onFriendDeleteNotify(notify) {
        webim.Log.info("执行 好友表删除 回调：" + JSON.stringify(notify));
        //好友表发生变化，需要重新加载好友列表或者单独删除notify.Accounts好友帐号
        //getAllFriend(getAllFriendsCallbackOK);
        var typeCh = "[好友表删除]";
        var content = "减少以下好友：" + notify.Accounts;
        addFriendSystemMsg(notify.Type, typeCh, content);
    }

//监听 未决添加 系统通知
    /*notify对象示例：
     {
     "Type":3,//通知类型
     "PendencyList":[
     {
     "PendencyAdd_Account": "peaker1",//对方帐号
     "ProfileImNic": "匹克1",//对方昵称
     "AddSource": "AddSource_Type_Unknow",//来源
     "AddWording": "你好"//申请附言
     },
     {
     "PendencyAdd_Account": "peaker2",//对方帐号
     "ProfileImNic": "匹克2",//对方昵称
     "AddSource": "AddSource_Type_Unknow",//来源
     "AddWording": "你好"//申请附言
     }
     ]
     }
     */
    function onPendencyAddNotify(notify) {
        webim.Log.info("执行 未决添加 回调：" + JSON.stringify(notify));
        //收到加好友申请，弹出拉取好友申请列表
        getPendency(true);
        var typeCh = "[未决添加]";
        var pendencyList = notify.PendencyList;
        var content = "收到以下加好友申请：" + JSON.stringify(pendencyList);
        addFriendSystemMsg(notify.Type, typeCh, content);
    }

//监听 未决删除 系统通知
    /*notify对数示例：
     {
     'Type':4,//通知类型
     'Accounts':['jim','bob']//用户ID列表
     }
     */
    function onPendencyDeleteNotify(notify) {
        webim.Log.info("执行 未决删除 回调：" + JSON.stringify(notify));
        var typeCh = "[未决删除]";
        var content = "以下好友未决已被删除：" + notify.Accounts;
        addFriendSystemMsg(notify.Type, typeCh, content);
    }

//监听 好友黑名单添加 系统通知
    /*notify对数示例：
     {
     'Type':5,//通知类型
     'Accounts':['jim','bob']//用户ID列表
     }
     */
    function onBlackListAddNotify(notify) {
        webim.Log.info("执行 黑名单添加 回调：" + JSON.stringify(notify));
        var typeCh = "[黑名单添加]";
        var content = "新增以下黑名单：" + notify.Accounts;
        addFriendSystemMsg(notify.Type, typeCh, content);
    }

//监听 好友黑名单删除 系统通知
    /*notify对数示例：
     {
     'Type':6,//通知类型
     'Accounts':['jim','bob']//用户ID列表
     }
     */
    function onBlackListDeleteNotify(notify) {
        webim.Log.info("执行 黑名单删除 回调：" + JSON.stringify(notify));
        var typeCh = "[黑名单删除]";
        var content = "减少以下黑名单：" + notify.Accounts;
        addFriendSystemMsg(notify.Type, typeCh, content);
    }
//初始化我的好友系统消息表格
    function initGetMyFriendSystemMsgs(data) {
        $('#get_my_friend_system_msgs_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "Type", title: "类型", align: "center", valign: "middle", sortable: "false", visible: false},
                {field: "TypeCh", title: "类型", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgContent", title: "内容", align: "center", valign: "middle", sortable: "true"}
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }

//查看我的好友系统消息
    function getMyFriendSystemMsgs() {
        $('#get_my_friend_system_msgs_dialog').modal('show');
    }

//增加一条好友系统消息
    function addFriendSystemMsg(type, typeCh, msgContent) {
        var data = [];
        data.push({
            "Type": type,
            "TypeCh": typeCh,
            "MsgContent": webim.Tool.formatText2Html(msgContent)
        });
        $('#get_my_friend_system_msgs_table').bootstrapTable('append', data);
    }





    // 监听资料系统通知消息 示例代码
    //js/msg/receive_profile_system_msg.js
//监听 资料变化（自己或好友） 系统通知
    /*notify对数示例：
     {
     "Type":1,//子通知类型
     "Profile_Account": "Jim",//用户帐号
     "ProfileList": [
     {
     "Tag": "Tag_Profile_IM_Nick",//昵称
     "ValueBytes": "吉姆"
     },
     {
     "Tag": "Tag_Profile_IM_Gender",//性别
     "ValueBytes": "Gender_Type_Male"
     },
     {
     "Tag": "Tag_Profile_IM_AllowType",//加好友认证方式
     "ValueBytes": "AllowType_Type_NeedConfirm"
     }
     ]
     }
     */
    function onProfileModifyNotify(notify) {
        webim.Log.info("执行 资料修改 回调：" + JSON.stringify(notify));
        var typeCh = "[资料修改]";
        var profile, account, nick, sex, allowType, content;
        account = notify.Profile_Account;
        content = "帐号：" + account + ", ";
        for (var i in notify.ProfileList) {
            profile = notify.ProfileList[i];
            switch (profile.Tag) {
                case 'Tag_Profile_IM_Nick':
                    nick = profile.ValueBytes;
                    break;
                case 'Tag_Profile_IM_Gender':
                    sex = profile.ValueBytes;
                    break;
                case 'Tag_Profile_IM_AllowType':
                    allowType = profile.ValueBytes;
                    break;
                default:
                    webim.log.error('未知资料字段：' + JSON.stringify(profile));
                    break;
            }
        }
        content += "最新资料：【昵称】：" + nick + ",【性别】：" + sex + ",【加好友方式】：" + allowType;
        addProfileSystemMsg(notify.Type, typeCh, content);

        if (account != loginInfo.identifier) {//如果是好友资料更新
            //好友资料发生变化，需要重新加载好友列表或者单独更新account的资料信息
            //getAllFriend(getAllFriendsCallbackOK);
            if (account && nick) {
                updateSessNameDiv(webim.SESSION_TYPE.C2C, account, nick);//更新最近聊天会话中的好友昵称
            }

        }
    }


//初始化我的资料系统消息表格
    function initGetMyProfileSystemMsgs(data) {
        $('#get_my_profile_system_msgs_table').bootstrapTable({
            method: 'get',
            cache: false,
            height: 500,
            striped: true,
            pagination: true,
            pageSize: pageSize,
            pageNumber: 1,
            pageList: [10, 20, 50, 100],
            search: true,
            showColumns: true,
            clickToSelect: true,
            columns: [
                {field: "Type", title: "类型", align: "center", valign: "middle", sortable: "false", visible: false},
                {field: "TypeCh", title: "类型", align: "center", valign: "middle", sortable: "true"},
                {field: "MsgContent", title: "内容", align: "center", valign: "middle", sortable: "true"}
            ],
            data: data,
            formatNoMatches: function () {
                return '无符合条件的记录';
            }
        });
    }

//查看我的资料系统消息
    function getMyProfileSystemMsgs() {
        $('#get_my_profile_system_msgs_dialog').modal('show');
    }

//增加一条资料系统消息
    function addProfileSystemMsg(type, typeCh, msgContent) {
        var data = [];
        data.push({
            "Type": type,
            "TypeCh": typeCh,
            "MsgContent": webim.Tool.formatText2Html(msgContent)
        });
        $('#get_my_profile_system_msgs_table').bootstrapTable('append', data);
    }




    return this;
});