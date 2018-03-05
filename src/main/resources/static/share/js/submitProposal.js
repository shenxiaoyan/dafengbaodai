//获取url参数
function getQueryString(str){
    var rs=new RegExp("(^|)"+str+"=([^&]*)(&|$)","gi").exec(String(window.document.location.href)),tmp;

    if(tmp=rs)return tmp[2];
    return null;
}
var tm = RegExp("(^|)=([^&]*)(&|$)","gi").exec(String(window.document.location.href));

// console.log(tm[2])


var app = angular.module("submitApp",[]);
app.controller("subContentCtrl",
    [
        "$scope",
        "$http",
        "$filter",
        function($scope, $http, $filter){

            $scope.submitInfo = {};
            $scope.insureAddition = [];
            $scope.insureRatioJson = [];
            $scope.insureRatio = [];

            $scope.detailShow = false;

            $http({
                method:"GET",
                url:"/dafeng/underwritingResultHander?orderId=" + tm[2]
            }).then(function(resResult){
                if(resResult.data.ErrorCode){
                    alert(resResult.data.ErrorInfo);
                }else{
                    $scope.submitInfo = resResult.data.data;

                    $scope.subBi = $scope.submitInfo.biBeginDate + "000";
                    $scope.subCi = $scope.submitInfo.ciBeginDate + "000";

                    angular.forEach($scope.submitInfo.offerDetail.insurances,function(value,index){
                       $scope.insureAddition.push(value.englishName);
                    });

                    angular.forEach($scope.submitInfo.ratioJson,function(value,index){
                        $scope.insureRatio.push(value);
                        $scope.insureRatioJson.push(value)
                    });

                    console.log($scope.submitInfo.schemeName)

                    if($scope.submitInfo.stateCode === "待付款"){
                        $scope.stateImg = "img/underwriting_pay.png";
                    }else if($scope.submitInfo.stateCode === "核保中"){
                        $scope.stateImg = "img/underwriting_review.png"
                    }else if($scope.submitInfo.stateCode === "核保失败"){
                        $scope.stateImg = "img/underwriting_fail.png"
                    }else if($scope.submitInfo.stateCode === "支付成功"){
                        $scope.stateImg = "img/order_pay_success.png"
                    }else if($scope.submitInfo.stateCode === "承保失败"){
                        $scope.stateImg = "img/order_underwrting_fail.png"
                    }else if($scope.submitInfo.stateCode === "承保成功"){
                        $scope.stateImg = "img/underwriting_success.png"
                    }else if($scope.submitInfo.stateCode === "人工核保中"){
                        $scope.stateImg = "img/underwriting_review.png"
                    }else if($scope.submitInfo.stateCode === "待确认"){
                        $scope.stateImg = "img/underwriting_review.png"
                    }else{
                        $scope.stateImg = "img/underwriting_review.png"
                    }

                }
            });

            $scope.showDetail = function(){
                $scope.detailShow = true;
            };

            $scope.hideDetail = function(){
                $scope.detailShow = false;

            };

            // console.log($scope.detailShow)

        }


    ]
);




function isNamme(){
    var name = navigator.userAgent.toLowerCase();
    if((/iphone|ipad|ipod/.test(name))){
        return "iphone";
    }else if(/android/.test(name)){
        return "android"
    }
}
