//判断设备系统名称
function isSysName(){
    var ua = navigator.userAgent.toLowerCase();
    if (/iphone|ipad|ipod/.test(ua)) {
        return "iphone";
    } else if (/android/.test(ua)) {
        return "android";
    }
}
var container = new Vue({
    el: '#container',
    data: {
        modelName:"",
        ownerName:"",
        licenseNumber:"",
        boughtTime:"",
        cityName:"",
        insuranceType:[],
        sumPrice:0,
        originalPrice:0,
        originalForcePrice:0,
        additionalPrice:0,
        firstItems:[],
        secondItems:[],
        companyName:"",
        show:false,
        detail:"",
        ratioJson:[],
        startTime:[],
    },
    ready:function(){
        var card = document.getElementById("list_card");
        var height = card.offsetHeight;
        height = height + 300;
        var container = document.getElementById("container");
        container.offsetHeight = height+'px';
    },
    methods:{
        downloadAPP:function(){
            if(isSysName()==='iphone'){
                window.location.href='https://itunes.apple.com/cn/app/%E5%A4%A7%E8%9C%82%E4%BF%9D%E9%99%A9/id1288386903?l=zh&ls=1&mt=8';
            }
            else if(isSysName()==='android'){
                window.location.href='https://www.pgyer.com/kn64';
            }
        },
        showPanel:function () {
                this.show =true;
        },
        hidenPanel:function () {
                this.show =false;
        }
    },
    filters: {
    dateformat:function (time) {
        date = time*1000;
        var d = new Date(date);
        var year = d.getFullYear();
        var month = d.getMonth() + 1;
        month = month<10 ? '0'+ month : month;
        var day = d.getDate() <10 ? '0' + d.getDate() : '' + d.getDate();
        return  year+ '-' + month + '-' + day;
    }
}});
const test = window.location.href;
const id = test.split("id=")[1].split("&")[0];
console.log(id);
axios({
    method:'GET',
    url:'/dafeng/shareOfferResultData?id='+id
}).then(function (response) {
    if(response.data.ErrorCode !== 0){
        container.show =true;
        container.detail = response.data.ErrorInfo;
        return;
    }
    console.log(response);
    response = response.data.data;
    container.modelName = response.modelName ? response.modelName : container.modelName;
    container.ownerName = response.ownerName ? response.ownerName : container.ownerName;
    container.sumPrice = response.sumPrice ? response.sumPrice : container.sumPrice.toFixed(2);
    container.licenseNumber = response.licenseNumber ? response.licenseNumber : container.licenseNumber;
    container.cityName = response.cityName ? response.cityName : container.cityName;
    if(response.data.result.modelJson){
        container.boughtTime = response.data.result.modelJson.boughtTime ? response.data.result.modelJson.boughtTime : container.boughtTime;
    }
    container.companyName = response.data.result.insuranceCompanyName;
    container.originalPrice = response.data.result.offerDetail.originalPrice;
    container.originalForcePrice = response.originalForcePrice;
    container.additionalPrice = response.data.result.offerDetail.additionalPrice;
    container.detail = response.deductibleDetail;
    if(response.data.result.offerDetail.insurances.length > 0){
        container.insuranceType=["商业险"];
        container.firstItems = response.data.result.offerDetail.insurances;
        if(response.data.result.offerDetail.forcePremium.isToubao === 1){
            container.insuranceType=["商业险","强制险"];
            container.secondItems = [
                {
                    insuranceName:'交强险',
                    price:response.data.result.offerDetail.forcePremium.quotesPrice
                },
                {
                    insuranceName:'车船税',
                    price:response.data.result.offerDetail.taxPrice.quotesPrice
                }
            ]
        }
    }

    if(response.data.result.insuranceStartTime){
        container.startTime.push({type:"商业险",time:response.data.result.insuranceStartTime})
    }
    if(response.data.result.forceInsuranceStartTime){
        container.startTime.push({type:"交强险",time:response.data.result.forceInsuranceStartTime})
    }

    if(response.data.result.ratioJson){
        if(response.data.result.ratioJson.settlement){
            container.ratioJson.push(response.data.result.ratioJson.settlement)
        }
        if(response.data.result.ratioJson.illegal){
            container.ratioJson.push(response.data.result.ratioJson.illegal)
        }
        if(response.data.result.ratioJson.channel){
            container.ratioJson.push(response.data.result.ratioJson.channel)
        }
        if(response.data.result.ratioJson.self){
            container.ratioJson.push(response.data.result.ratioJson.self)
        }
    }
});

