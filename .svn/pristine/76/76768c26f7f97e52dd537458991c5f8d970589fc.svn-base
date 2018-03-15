"use strict";

//JSON的过滤器
app.filter("myJson", function () {
    return function (json) {
        return JSON.parse(json);
    }
})
    .filter("myParseFloat",function(){
       return function(data) {
           return data.toFixed(2);
       }
    })
    .filter("myParse",function(){
        return function(data) {
            return parseFloat(data,2);
        }
    })
    .filter("createStateCode",function(){
        return function(stateCode){
            var output = null;
            switch(stateCode){
                case "INENQUIRY":
                    output = "询价中";
                        break;
                case "CANCEL":
                    output = "询价取消";
                    break;
                case "ENQUIRY_RESULT":
                    output = "已有询价结果";
                    break;
                case "SUBMIT_ALREADY":
                    output = "已提交核保";
                    break;
                case "ENQUIRY_TIMEOUT":
                    output = "询价超时";
                    break;
                case "ALL":
                    output = "询价中";
                    break;
            }
            return output;
        }
    })
    .filter("salesmanStateCode",function(){
        return function(stateCode){
            var state = null;
            switch(stateCode){
                case "ENABLED":
                    state = "启用";
                    break;
                case "DISABLED":
                    state = "禁用";
                    break;
                case "CREATED":
                    state = "已创建";
                    break;
                case "DELETED":
                    state = "已删除";
                    break;
            }
            return state;
        }
    })
    .filter("teamStateCode",function(){
        return function(stateCode){
            var state = null;
            switch(stateCode){
                case "ENABLED":
                    state = "有效";
                    break;
                case "APPLIED":
                    state = "待审核";
                    break;
                case "DISABLED":
                    state = "禁用";
                    break;
                case "REJECTED":
                    state = "审核不通过";
                    break;
                case "DELETED":
                    state = "已删除";
                    break;
            }
            return state;
        }
    })
    .filter("teamAdvState",function(){
        return function(stateCode){
            var state = null;
            switch(stateCode){
                case "PUBLISHED":
                    state = "已发布";
                    break;
                case "DELETED":
                    state = "已删除";
                    break;
            }
            return state;
        }
    })
    .filter("teamMumberRole",function(){
        return function(stateCode){
            var state = null;
            switch(stateCode){
                case "captain":
                    state = "队长";
                    break;
                case "member":
                    state = "成员";
                    break;
            }
            return state;
        }
    })
    .filter("carTypeCode",function(){
        return function(stateCode){
            var state = null;
            switch(stateCode){
                case "01":
                    state = "大型车";
                    break;
                case "02":
                    state = "小型车";
                    break;
                case "15":
                    state = "挂车";
                    break;
            }
            return state;
        }
    })
    .filter("departmentStateCode",function(){
        return function(stateCode){
            var state = null;
            switch(stateCode){
                case "ENABLED":
                    state = "正常";
                    break;
                case "DISABLED":
                    state = "禁用";
                    break;
                case "CREATED":
                    state = "已创建";
                    break;
                case "DELETED":
                    state = "已删除";
                    break;
            }
            return state;
        }

    })
    .filter("posterType",function () {
        return function (type) {
            var typeName = null;
            switch (type){
                case "APHORISM" :
                    typeName = "名言";
                    break;
                case "FESTIVAL" :
                    typeName = "节日";
                    break;
                case "PRODUCT" :
                    typeName = "产品";
                    break;
                case "OTHER" :
                    typeName = "其他";
                    break;
            }
            return typeName;
        }
    })
    .filter("friendsType",function () {
        return function (type) {
            var typeName = null;
            switch (type){
                case "UNPUBLISHED" :
                    typeName = "未发布";
                    break;
                case "PUBLISHED" :
                    typeName = "已发布";
                    break;
                case "DELETED" :
                    typeName = "已删除";
                    break;
            }
            return typeName;
        }
    })
    .filter("profitState",function () {
        return function (type) {
            var typeName = null;
            switch (type){
                case "SETTLING" :
                    typeName = "结算中";
                    break;
                case "SETTLED" :
                    typeName = "已结算";
                    break;
                case "SURRENDERED" :
                    typeName = "退保取消";
                    break;
                case "WITHDRAWING" :
                    typeName = "提现中";
                    break;
                case "WITHDRAW_SUCCESS" :
                    typeName = "提现成功";
                    break;
                case "WITHDRAW_FAILED" :
                    typeName = "提现失败";
                    break;
                case 1 :
                    typeName = "返佣";
                    break;
                case 2 :
                    typeName = "团队收益";
                    break;
                case 3 :
                    typeName = "鼓励金";
                    break;
                case 4 :
                    typeName = "提现";
                    break;
            }
            return typeName;
        }
    })
    .filter("profitProductType",function () {
        return function (type) {
            var typeName = null;
            switch (type){
                case "AUTO_INSURANCE" :
                    typeName = "车险";
                    break;
                case " NON_AUTO_INSURANCE" :
                    typeName = "非车险";
                    break;
                case " LIFE_INSURANCE" :
                    typeName = "寿险";
                    break;
            }
            return typeName;
        }
    });





