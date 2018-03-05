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

var tm = RegExp("(^|)=([^&]*)(&|$)","gi").exec(String(window.document.location.href));

var app = angular.module("personBCApp",[]);
app.controller("personBCCtrl",
    [
        "$scope",
        "$http",
        function($scope, $http){

            //手机号码
            $scope.phone = "";
            //分享的团长或者成员姓名
            $scope.name = "";
            //头像
            $scope.img = "";
            //个人介绍
            $scope.introduce = "";
            //个人荣誉
            $scope.honor = "";
            //照片
            $scope.picture = "";

            //加入团队
            $scope.getInfo = function(){
                $http({
                    method : "POST",
                    url : "/dafeng/perCard/" + tm[2]
                }).success(function(resResult){
                    $scope.list = resResult.data;

                    $scope.listInfo = $scope.list.perDetails.perInfoDesc;

                    $scope.listHonor = $scope.list.perDetails.perHonor.split(",");

                    $scope.listPicture = $scope.list.perDetails.imageUrl.split(",");
                })
            };
            $scope.getInfo();

        }
    ]
);