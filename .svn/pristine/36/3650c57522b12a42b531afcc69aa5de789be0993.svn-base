'use strict';
/* Controllers */

angular.module('app')
        .controller('AppCtrl',
            [
                '$scope',
                '$http',
                '$translate',
                '$localStorage',
                '$window',
                '$rootScope',
                'imService',
            function ($scope, $http, $translate, $localStorage, $window, $rootScope, imService) {

                $rootScope.myinfo = {};

                $rootScope.onMessage = window.onMessage;

                $rootScope.distEntityId = window.distEntityId;

                $rootScope.selectMessage = function ($event) {
                    $($event.delegateTarget).parent().siblings().find("li").removeClass("active");
                    $("#message_container").css("display", "block");
                    $("#common_container").css("display", "none");
                    $("#message_un_read").css("display", "none");
                };

                $rootScope.unSelectMessage = function () {
                    $("#message_container").css("display", "none");
                    $("#common_container").css("display", "block");
                    $state.reload();
                };

                $http.get('/rest/users/' + window.myid + "?projection=userProjection").success(function (response) {

                    window.myinfo = $rootScope.myinfo = response;

                    //loginAndInitIm($rootScope.myinfo);

                    $scope.getMenuTree($rootScope.myinfo);
                });

                $rootScope.sessions = {};
                $rootScope.groups = {};
                $rootScope.friends = {};
                $rootScope.menus = [];
                $rootScope.notices = {};

                $scope.getMenuTree = function (myinfo) {

                    $http.get(myinfo._links.role.href).success(function (resResult) {
                        $http.get(resResult._links.self.href).success(function (resResult2) {
                            $rootScope.menus = resResult2.visibleMenuTree;
                        });
                    });

                };


                /*
            * 重置密码函数
            * @author 金杭
            * */
                $scope.oldPwd     = null;
                $scope.newPwd     = null;
                $scope.confirmPwd = null;

                $scope.resetPassword = function () {
                    $http({
                        method:'POST',
                        url:'/dafeng/enduser/changePswd',
                        params:{
                            username:window.myinfo.username,
                            oldpassword:$scope.oldPwd,
                            newPassword:$scope.newPwd
                        }
                    }).then(function successCallback(response){
                        if(response.data.ErrorCode==100){
                            $rootScope.toasterError('失败','原密码错误，修改失败！');
                        }
                        else if(response.data.ErrorCode==0){
                            $rootScope.toasterSuccess('成功','修改成功！');
                            $("#reset_password_modal").modal("hide");
                            window.location.href = "/logout";
                        }
                    });

                };




                $rootScope.toasterSuccess = function (title, text) {
                    //toaster.pop('success', title, text);
                    toastr.success(text, title);
                };

                $rootScope.toasterError = function (title, text) {
                    //toaster.pop('error', title, text);
                    toastr.error(text, title);
                };

                $rootScope.toasterWarning = function (title, text) {
                    //toaster.pop('warning', title, text);
                    toastr.warning(text, title);
                };

                $rootScope.toasterInfo = function (title, text) {
                    //toaster.pop('info', title, text);
                    toastr.info(text, title);
                };

                /*
                 * 封装 sweetalert，减少代码量
                 * @author 金杭
                 * @date   2017/07/20
                 * @param  {object} options 
                 *    options属性： 
                 *    title     弹出框的标题            string
                 *    text      弹出框的具体内容    string
                 *    type      弹出框 的类型           string
                 *    callback  确认后执行的内容    function
                 * @return 无
                 * */
                $rootScope.sweetConfirm = function (title,callback) {
                    swal({
                        title: title,
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#F05050",
                        cancelButtonText: "不，让我想想",
                        confirmButtonText: "是，我想好了",
                        closeOnConfirm: true
                    }, function () {
                        callback();
                    });
                };

                /*
                 * 设置分页的函数
                 * @author  金杭
                 * @param   selector  {string}    分页的选择器
                 * @param   pageList  {object}    分页参数
                 * @param   callback  {function}  点击页码后的回调函数
                 * */
                $rootScope.setPaginator = function (selector,pageList,callback) {
                    console.log(selector)
                    //分页的配置
                    var pageOptions = {
                        bootstrapMajorVersion: 3, //版本
                        currentPage: pageList.current_page + 1, //当前页数
                        // numberOfPages:pageList.num_page,//显示页数
                        totalPages: pageList.total_page, //总页数
                        tooltipTitles: function (type, page, current) {
                            //修改鼠标悬停title为中文
                            switch (type) {
                                case "next":
                                    return "下一页";
                                case "last":
                                    return "末页";
                                case "page":
                                    return "第" + page + "页";
                            }
                        },
                        itemTexts: function (type, page, current) {
                            //修改按钮文字为中文
                            switch (type) {
                                case "first":
                                    return "首页";
                                case "prev":
                                    return "上一页";
                                case "next":
                                    return "下一页";
                                case "last":
                                    return "末页";
                                case "page":
                                    return page;
                            }
                        },
                        onPageChanged: function (event, oldPage, newPage) {
                            //Ajax来刷新整个list列表
                            callback(newPage - 1);
                        }
                    };

                    //设置分页插件
                    $(selector).bootstrapPaginator(pageOptions);
                };

                $rootScope.global_configs = {
                    isEnabled: [
                        {key: 0, name: '不启用'},
                        {key: 1, name: '启用'}
                    ],
                    product_types: {
                        credit_load: '信用贷',
                        mortgage_load: '抵押',
                        pledge_load: '质押'
                    },
                    repayment_types: {
                        dengerbenxi: '等额本息',
                        dengerbenjin: '等额本金',
                        xianxihouben: '先息后本'
                    },
                    customer_types: {
                        personal: '个人',
                        enterprise: '企业'
                    },
                    time_units: {
                        day: '天',
                        week: '周',
                        month: '月'
                    }
                };

                // add 'ie' classes to html
                var isIE = !!navigator.userAgent.match(/MSIE/i);
                if (isIE) {
                    angular.element($window.document.body).addClass('ie');
                }
                if (isSmartDevice($window)) {
                    angular.element($window.document.body).addClass('smart')
                }

                //websocket
                $scope.number = 0;
                $rootScope.ws = {
                    contents:[],
                    bellUrl:'',
                    bellClick: function () {
                        if($scope.number === 0 || !$scope.number){
                            $rootScope.ws.contents = [];
                            location.href = "/#/insureNotice";
                        }else{
                            $http({
                                method: 'GET',
                                url: '/dafeng/advertise/web/unRead'
                            }).then(function (res) {
                                if($scope.number>0){
                                    $rootScope.ws.contents = res.data.data.contents;
                                }
                            });
                        }
                        $http({
                            method: 'GET',
                            url: '/dafeng/advertise/web/unRead'
                        }).then(function (res) {
                            $scope.number = res.data.data.notReadNum;
                        });
                    },
                    readAll:function () {
                        $scope.number = 0;
                    }
                };
                var webSocket = null;
                function initWs() {
                    //check if your browser supports WebSocket
                    if ('WebSocket' in window) {
                        webSocket = new WebSocket("ws://"+window.location.host+"/websocket/"+window.myid);
                        console.log(window.location.host)
                    }
                    else {
                        alert('Sorry, websocket not supported by your browser.')
                    }

                    //Error callback
                    webSocket.onerror = function () {
                        setMessageContent("error!");
                    };

                    //socket opened callback
                    webSocket.onopen = function (event) {
                        setMessageContent("websocket opened");
                        $http({
                            method: 'GET',
                            url: '/dafeng/advertise/web/unReadNum'
                        }).then(function (res) {
                            console.log(res);
                            $scope.number = res.data.data;
                        });
                    };

                    //message received callback
                    webSocket.onmessage = function (event) {
                        event = JSON.parse(event.data);
                        setMessageContent(event);
                        layer.open({
                            title: event.title,
                            content: event.content,
                            offset: 'rb',
                            shade: 0,
                            time: 2000,
                            closeBtn: 0
                        });


                        if(event.refreshFlag === 1){
                            $scope.$apply(function () {
                                $http({
                                    method: 'GET',
                                    url: '/dafeng/advertise/web/unReadNum'
                                }).then(function (res) {
                                    console.log(res);
                                    $scope.number = res.data.data;
                                })
                            });
                        }

                    };

                    //socket closed callback
                    webSocket.onclose = function () {
                        setMessageContent("websocket closed");
                    };

                    //when browser window closed, close the socket, to prevent server exception
                    window.onbeforeunload = function () {
                        webSocket.close();
                    }
                }
                initWs();

                //update message to vue and then in div
                function setMessageContent(content) {
                    console.log(content);
                }





                // config
                $scope.app = {
                    name: '小金平台',
                    version: '1.0.0',
                    // for chart colors
                    color: {
                        primary: '#7266ba',
                        info: '#23b7e5',
                        success: '#27c24c',
                        warning: '#fad733',
                        danger: '#f05050',
                        light: '#e8eff0',
                        dark: '#3a3f51',
                        black: '#1c2b36'
                    },
                    settings: {
                        themeID: 13,
                        navbarHeaderColor: "bg-dark",
                        navbarCollapseColor: "bg-white-only",
                        asideColor: "bg-dark",
                        headerFixe: true,
                        asideFixed: true,
                        asideFolded: false,
                        asideDock: false,
                        container: false
                    }
                };

                // save settings to local storage
                if (angular.isDefined($localStorage.settings)) {
                    $scope.app.settings = $localStorage.settings;
                } else {
                    $localStorage.settings = $scope.app.settings;
                }

                $scope.$watch('app.settings', function () {
                    if ($scope.app.settings.asideDock && $scope.app.settings.asideFixed) {
                        // aside dock and fixed must set the header fixed.
                        $scope.app.settings.headerFixed = true;
                    }
                    // for box layout, add background image
                    $scope.app.settings.container ? angular.element('html').addClass('bg') : angular.element('html').removeClass('bg');
                    // save to local storage
                    $localStorage.settings = $scope.app.settings;
                }, true);
                // angular translate
                $scope.lang = {isopen: false};

                $translate.use("getEntityInfo");

                function isSmartDevice($window) {
                    // Adapted from http://www.detectmobilebrowsers.com
                    var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
                    // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
                    return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
                }



            }])
        .run(['$rootScope', '$state', '$stateParams', '$templateCache', '$log', function ($rootScope, $state, $log, $stateParams, $templateCache) {


                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
//                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
//                    $log.debug('successfully changed states');
//
//                    $log.debug('event', event);
//                    $log.debug('toState', toState);
//                    $log.debug('toParams', toParams);
//                    $log.debug('fromState', fromState);
//                    $log.debug('fromParams', fromParams);
//                });
//
//                $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
//                    $log.error('The request state was not found: ' + unfoundState);
//                });
//
//                $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
//                    $log.error('An error occurred while changing states: ' + error);
//
//                    $log.debug('event', event);
//                    $log.debug('toState', toState);
//                    $log.debug('toParams', toParams);
//                    $log.debug('fromState', fromState);
//                    $log.debug('fromParams', fromParams);
//                });

                //joshua: 禁止模板缓存 
                $rootScope.$on('$routeChangeStart', function (event, next, current) {
                    if (typeof (current) !== 'undefined') {
                        $templateCache.remove(current.templateUrl);
                    }
                });

            }
        ]);