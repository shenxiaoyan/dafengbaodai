//判断设备系统名称
function isSysName(){
    var ua = navigator.userAgent.toLowerCase();
    if (/iphone|ipad|ipod/.test(ua)) {
        return "iphone";
    } else if (/android/.test(ua)) {
        return "android";
    }
}
//获取url参数
function getQueryString(str) {
    var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(String(window.document.location.href)), tmp;

    console.log(rs);
    if (tmp = rs) return tmp[2];
    return null;
}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
var Request = new Object();
Request = GetRequest();

console.log(Request)

var urlPhone = Request.phone;
// var urlteamInviteCode = Request.teamInviteCode;
// var myInvite = Request.myInvite;



var app = angular.module("teamInviteApp",[]);
app.controller("teamInviteCtrl",
    [
        "$scope",
        "$http",
        "$interval",
        function($scope, $http, $interval){

            //手机号码
            $scope.phone = "";
            //验证码
            $scope.inviteCode = "";
            //错误信息提示
            $scope.errorInfo = "";
            //是否显示错误信息
            $scope.isErrorInfo = false;
            //发送验证码之后的秒数
            $scope.second = "";
            //是否显示秒数
            $scope.isSecond = false;
            //分享的团长或者成员姓名
            $scope.name = "";
            //团队名称
            $scope.teamLabel = "";
            //头像
            $scope.img = "";
            //角色
            $scope.role = "";
            //电话
            $scope.teamPhone = "";


            //根据APP带来的邀请码来获取当前分享信息
            $scope.getTeamInfo = function(){
                $http.get("/dafeng/team/invite/data?phone=" + urlPhone).then(function(resResult){

                    console.log(resResult);

                    if(resResult.ErrorCode){
                        $scope.isErrorInfo = true;
                        $scope.isErrorInfo = resResult.ErrorInfo;
                    }else{
                        $scope.name = resResult.data.data.realName;
                        $scope.img = resResult.data.headimgurl;
                        $scope.teamLabel = resResult.data.data.teamLabel;
                        $scope.role = resResult.data.data.role;
                        $scope.teamPhone = resResult.data.data.contactPhone;
                        $scope.teamInviteCode = resResult.data.data.teamInviteCode;
                        $scope.myInvite = resResult.data.data.myInvite;
                    }
                })
            };
            $scope.getTeamInfo();

            //获取验证码
            $scope.getInviteCode = function(){
                if(!$scope.phone){
                    $scope.isErrorInfo = true;
                    $scope.errorInfo = "请输入手机号码";
                }else if(!(/^1[34578]\d{9}$/.test($scope.phone))){
                    $scope.isErrorInfo = true;
                    $scope.errorInfo = "请输入正确格式的手机号码";
                }else{
                    $scope.isErrorInfo = false;
                    $http.get("/dafeng/getIdentify?phone=" + $scope.phone).then(function(resResult){
                        if(resResult.ErrorCode){
                            $scope.isErrorInfo = true;
                            $scope.errorInfo = resResult.ErrorInfo;
                        }else{
                            $scope.isSecond = true;
                            $scope.second = 60;

                            $interval(function(){
                                $scope.second--;
                                if($scope.second === 0){
                                    $scope.isSecond = false;
                                    $scope.second = 60;
                                }
                            },1000,60);


                        }
                    })
                }
            };


            //加入团队
            $scope.joinTeam = function(){

                console.log($scope.phone)

                if(!$scope.phone){
                    $scope.isErrorInfo = true;
                    $scope.errorInfo = "请输入手机号码";
                }else if(!(/^1[34578]\d{9}$/.test($scope.phone))){
                    $scope.isErrorInfo = true;
                    $scope.errorInfo = "请输入正确格式的手机号码";
                }else if(!$scope.inviteCode){
                    $scope.isErrorInfo = true;
                    $scope.errorInfo = "请输入验证码";
                }else{
                    $scope.isErrorInfo = false;
                    $http({
                        method : "POST",
                        url : "/dafeng/team/invite/join",
                        data : {
                            phone : $scope.phone,
                            code : $scope.inviteCode,
                            invite : $scope.myInvite,
                            teamInviteCode : $scope.teamInviteCode
                        }
                    }).success(function(resResult){
                        if(resResult.ErrorCode){
                            $scope.isErrorInfo = true;
                            $scope.errorInfo = resResult.ErrorInfo;
                        }else{
                            $scope.phone = "";
                            $scope.inviteCode = "";
                            $scope.isErrorInfo = true;
                            $scope.errorInfo = "您已成功加入团队！";
                        }
                    })
                }
            }

        }
    ]
);