//监听连接状态回调变化事件
var onConnNotify = function(resp){
    switch(resp.ErrorCode){
        case webim.CONNECTION_STATUS.ON:
            webim.Log.warn('建立连接成功: ' + resp.ErrorInfo);
            break;
        case webim.CONNECTION_STATUS.OFF:
            webim.Log.warn('连接已断开，无法收到新消息，请检查下你的网络是否正常: ' + resp.ErrorInfo);
            break;
        case webim.CONNECTION_STATUS.RECONNECT:
            webim.Log.warn('连接状态恢复正常: ' + resp.ErrorInfo);
            break;
        default:
            webim.Log.error('未知连接状态: =' + resp.ErrorInfo);
            break;
    }
};

//IE9(含)以下浏览器用到的jsonp回调函数
var jsonpCallback = function(rspData){
    webim.setJsonpLastRspData(rspData);
};

//监听新消息事件
var onMsgNotify = function(newMsgList){
    var sess, newMsg;
    //获取所有聊天会话
    var sessMap = webim.MsgStore.sessMap();

    for(var j in newMsgList){
        newMsg = newMsgList[j];

        if(newMsg.getSession().id() == selToID){
            selSess = newMsg.getSession();
            //在聊天窗体中新增一条消息
            console.warn(newMsg);
        }
    }

    //消息已读上报，以及设置会话自动已读标记
    webim.setAutoRead(selSess, true, true);

    for(var i in sessMap){
        sess = sessMap[i];
        if(selToID != sess.id()){
            updateSessDiv(sess.type(), sess.id(), sess.unread());
        }
    }
};

var service = service || {
    loginInfo : {
        'userSig'        : '',
        'appIDAt3rd'     : '',
        'sdkAppID'       : '',
        'identifier'     : '',
        'identifierNick' : '',
        'accountType'    : '0'
    },

    options : {
        'isAccessFormalEnv' : 'False',
        'isLogOn'           : 'False'
    },

    listeners : {
        //监听连接状态回调变化事件
        'onConnNotify' : onConnNotify,
        //IE9(含)以下浏览器用到的jsonp回调函数
        'jsonpCallback' : jsonpCallback,
        //监听新消息(私聊，普通群(非直播聊天室)消息，全员推送消息)事件
        'onMsgNotify' : onMsgNotify,
        //监听（多终端同步）群系统消息事件
        'onGroupSystemNotifys' : '',
        //监听群资料变化事件
        'onGroupInfoChangeNotify' : '',
        //监听好友系统通知事件
        'onFriendSystemNotifys' : '',
        //监听资料系统（自己或好友）通知事件
        'onProfileSystemNotifys' : '',
        //被其他登录实例踢下线
        'onKickedEventCall' : '',
        //监听C2C系统消息通道
        'onC2cEventNotifys' : ''
    },

    //登录
    webimLogin : function(loginInfo, listeners, options){
        webim.login(
            loginInfo,
            listeners,
            options,
            function(resp){
                service.loginInfo.identifierNick = resp.identifierNick;
            },
            function(err){
                console.error(err.ErrorInfo);
            }
        );
    },

    //查询IM用户
    getIMUser : function(id){
        $.ajax({
            url      : '/api/1.0/im/user/' + id,
            type     : 'GET',
            dataType : 'JSON',
            async    : false,
            success  : function(res){
                if(res.success){
                    service.loginInfo.identifier     = res.result.name;
                    service.loginInfo.identifierNick = res.result.nickname;
                    service.loginInfo.appIDAt3rd     = res.result.appId;
                    service.loginInfo.sdkAppID       = res.result.appId;
                    service.loginInfo.userSig        = res.result.sig;
                }else{
                    alert(res.msg);
                }
            },
            error    : function(response){
                console.error(response);
            }
        });
    },

    //查询参数
    getQueryString : function(name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }
};

$(function(){
    var imUserId = service.getQueryString('imUserId');
    service.getIMUser(imUserId);
    service.webimLogin(service.loginInfo, service.listeners, service.options);
});