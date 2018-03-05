app.service("entity",
    [
        "$rootScope",
        "$scope",
        "$q",
        "SpringDataRestAdapter",
        function($rootScope, $scope, $q, SpringDtaaRestAdapter){

            //正则验证
            this.getFormat = function(value,type){

                var output = null;

                switch(type){
                    //string的日期格式
                    case "date":
                        if(/^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/.test(value)){
                            output = true;
                        }
                        break;
                    case "phone":
                        if(/^1[3|4|5|8][0-9]\d{4,8}$/.test(value)){
                            output = true;
                        }
                        break;
                }

            }
        }
    ]
);