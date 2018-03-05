var app = angular.module("questApp",[]);
app.controller("questContentCtrl",
    [
        "$scope",
        "$http",
        "$filter",
        function($scope, $http, $filter){

            $scope.detailShow1 = false;
            $scope.downShow1 = true;

            $scope.show1 = function(){
                $scope.detailShow1 = !$scope.detailShow1;
                $scope.downShow1 = !$scope.downShow1;
            };

            $scope.detailShow2 = false;
            $scope.downShow2 = true;

            $scope.show2 = function(){
                $scope.detailShow2 = !$scope.detailShow2;
                $scope.downShow2 = !$scope.downShow2;
            };

            $scope.detailShow3 = false;
            $scope.downShow3 = true;

            $scope.show3 = function(){
                $scope.detailShow3 = !$scope.detailShow3;
                $scope.downShow3 = !$scope.downShow3;
            };


            $scope.detailShow4 = false;
            $scope.downShow4 = true;

            $scope.show4 = function(){
                $scope.detailShow4 = !$scope.detailShow4;
                $scope.downShow4 = !$scope.downShow4;
            };

            $scope.detailShow5 = false;
            $scope.downShow5 = true;

            $scope.show5 = function(){
                $scope.detailShow5 = !$scope.detailShow5;
                $scope.downShow5 = !$scope.downShow5;
            };

            $scope.detailShow6 = false;
            $scope.downShow6 = true;

            $scope.show6 = function(){
                $scope.detailShow6 = !$scope.detailShow6;
                $scope.downShow6 = !$scope.downShow6;
            };


            $scope.detailShow7 = false;
            $scope.downShow7 = true;

            $scope.show7 = function(){
                $scope.detailShow7 = !$scope.detailShow7;
                $scope.downShow7 = !$scope.downShow7;
            };


            $scope.detailShow8 = false;
            $scope.downShow8 = true;

            $scope.show8 = function(){
                $scope.detailShow8 = !$scope.detailShow8;
                $scope.downShow8 = !$scope.downShow8;
            };


            $scope.detailShow9 = false;
            $scope.downShow9 = true;

            $scope.show9 = function(){
                $scope.detailShow9 = !$scope.detailShow9;
                $scope.downShow9 = !$scope.downShow9;
            };

        }
    ]
);


// //获取url参数
// function getQueryString(str){
//     var rs=new RegExp("(^|)"+str+"=([^&]*)(&|$)","gi").exec(String(window.document.location.href)),tmp;
//
//     if(tmp=rs)return tmp[2];
//     return null;
// }
// var tm = RegExp("(^|)=([^&]*)(&|$)","gi").exec(String(window.document.location.href));

//
// function isNamme(){
//     var name = navigator.userAgent.toLowerCase();
//     if((/iphone|ipad|ipod/.test(name))){return "iphone";
//     }else if(/android/.test(name))
//         return "android"
// }
