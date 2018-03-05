function isNamme(){
    var name = navigator.userAgent.toLowerCase();
    if((/iphone|ipad|ipod/.test(name))){
        return "iphone";
    }else if(/android/.test(name)){
        return "android"
    }
}

//获取url参数
function getQueryString(str){
    var rs=new RegExp("(^|)"+str+"=([^&]*)(&|$)","gi").exec(String(window.document.location.href)),tmp;

    console.log(window.document.location.href)
    console.log(rs);

    if(tmp=rs)return tmp[2];
    return null;
}
var tm = RegExp("(^|)=([^&]*)(&|$)","gi").exec(String(window.document.location.href));

console.log(tm[2])

var payReward = new Vue({
    el: "#payReward",
    data:{
        insuranceCompanyName: "",
        contactName:"",
        licenseNumber:"",
        schemeName:"",
        sumPrice:"",
        currentPrice:"",
        originalPrice:"",
        payQrcodeContent:"",
        payQrcodeDesc:"",
        payQrcodeName:"",
        payQrcodeImg:"",
        payNoValue:"",
        checkCodeValue:""


    },
    methdos: {
        downloadApp: function(){
            if(isName() === "iphone"){

                window.location.href='https://itunes.apple.com/cn/app/%E5%A4%A7%E8%9C%82%E4%BF%9D%E9%99%A9/id1288386903?l=zh&ls=1&mt=8';

            }else if(isNAme() === "android"){

                window.location.href='https://www.pgyer.com/kn64';

            }
        }

    }
});

//109-20171108151632-e5707

axios({
    method:"GET",
    url:"/dafeng/underwritingResultHander?orderId=" + tm[2]
}).then(function(resResult){

    console.log(resResult);

    payReward.insuranceCompanyName = resResult.data.data.company.name;


    payReward.contactName = resResult.data.data.contactName;
    payReward.licenseNumber = resResult.data.data.licenseNumber;
    payReward.schemeName = resResult.data.data.schemeName;
    payReward.sumPrice = (resResult.data.data.sumPrice).toFixed(2);
    payReward.currentPrice = resResult.data.data.offerDetail.currentPrice;
    payReward.originalPrice = resResult.data.data.offerDetail.originalPrice;
    payReward.ownerName = resResult.data.data.params.ownerName;

    payReward.biNoValue = resResult.data.data.underwritingJson.biNo.value;
    payReward.insuranceId = resResult.data.data.offerDetail.insurances;

    if(resResult.data.data.underwritingJson.payNo){
        payReward.payNoValue = resResult.data.data.underwritingJson.payNo.value;

    }
    if(resResult.data.data.underwritingJson.checkCode){
        payReward.checkCodeValue = resResult.data.data.underwritingJson.checkCode.value;

    }

    if(resResult.data.data.underwritingJson.payJson.payQrcode){
        payReward.payQrcodeContent = resResult.data.data.underwritingJson.payJson.payQrcode.content;
        payReward.payQrcodeImg = resResult.data.data.underwritingJson.payJson.payQrcode.img;
    }

    if(resResult.data.data.underwritingJson.payJson.payUrl) {
        payReward.payQrcodeContent = resResult.data.data.underwritingJson.payJson.payUrl.content;
        payReward.payQrcodeImg = resResult.data.data.underwritingJson.payJson.payUrl.img;
    }


});







