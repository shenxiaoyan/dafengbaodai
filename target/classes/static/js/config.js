// config

var app =

angular.module('app')
    .config(
        ['$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
            function ($controllerProvider, $compileProvider, $filterProvider, $provide) {

                // lazy controller, directive and service
                app.controller = $controllerProvider.register;
                app.directive  = $compileProvider.directive;
                app.filter     = $filterProvider.register;
                app.factory    = $provide.factory;
                app.service    = $provide.service;
                app.constant   = $provide.constant;
                app.value      = $provide.value;
            }
        ])
    .config(['$translateProvider', function ($translateProvider) {
        // Register a loader for the static files
        // So, the module will search missing translation tables under the specified urls.
        // Those urls are [prefix][langKey][suffix].
        $translateProvider.useStaticFilesLoader({
            prefix: '',
            suffix: ''
        });
        // Tell the module what language to use by default
        $translateProvider.preferredLanguage('getEntityInfo');
        // Tell the module to store the language in the local storage
        $translateProvider.useLocalStorage();
    }])
    .config(['$sceDelegateProvider', function ($sceDelegateProvider) {
        $sceDelegateProvider.resourceUrlWhitelist([
            // Allow same origin resource loads.
            'self',
            // Allow loading from our assets domain.  Notice the difference between * and **.
            '**']);
    }])
    .config(['$httpProvider', function ($httpProvider) {

        $httpProvider.defaults.headers.common = {
            "app_code": "XIAOJINPINGTAI",
            "client": "web"
        };

        //金杭：拦截的方式，统一结果处理
        $httpProvider.interceptors.push("httpInterceptor");
    }]);
//拦截器需要的服务
app.factory("httpInterceptor",
    [
        "$injector",
        function ($injector) {
            return {
                // request : function(config) {
                //     console.log(config);
                //     return config;
                // },
                // requestError : function(config){
                //     console.log(config);
                //     return config;
                // },
                response: function (response) {

                    if (response.data.ActionStatus && response.data.ActionStatus === "FAIL") {
                        toastr.error("请求失败！" + response.data.ErrorInfo, response.data.ErrorCode);
                    } else {
                        return response;
                    }

                },
                responseError: function (response) {

                    if (typeof response.data === "object") {
                        toastr.error("请求失败！" + response.data.message, (response.data.error ? response.data.error : ""));
                    } else {
                        toastr.error("请联系管理员！", "请求失败！");
                    }

                    return response;
                }
            };
        }
    ]
);