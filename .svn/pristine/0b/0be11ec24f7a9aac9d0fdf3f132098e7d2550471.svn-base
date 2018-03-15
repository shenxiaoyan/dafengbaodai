'use strict';

/**
 * Config for the router
 */
angular.module('app')

    .config(
        ['$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 'MODULE_CONFIG',
            function ($stateProvider, $urlRouterProvider, JQ_CONFIG, MODULE_CONFIG) {

                $urlRouterProvider.otherwise('/dashboard');


                //工作台
                $stateProvider
                    .state('exceptions', {
                        url: '/exceptions',
                        templateUrl: 'tpl/exceptions/list.html',
                        resolve: load(['bootstrapPaginator'])
                    })
                    .state('dashboard', {
                        url: '/dashboard',
                        templateUrl: 'tpl/dashboards/welcome.html'
                    })
                    .state('statisticsDashboard', {
                        url: '/statisticsDashboard',
                        templateUrl: 'tpl/dashboards/statisticsWelcome.html'
                    })
                    .state('notices', {
                        url: '/notices',
                        templateUrl: "tpl/layouts/notice.html",
                        resolve: load(['bootstrapPaginator'])
                    })
                    .state('users', {
                        url: '/users',
                        templateUrl: "tpl/layouts/users.html"
                    })
                    .state('users.user', {
                        url: '/{id}',
                        templateUrl: "tpl/entities/users/home.html"
                    })
                    .state('users.user.page', {
                        url: '/{entity_type}/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('users.create', {
                        url: '/create',
                        templateUrl: "tpl/entities/users/create.html"
                    })
                    .state('affairs', {
                        url: '/affairs',
                        templateUrl: "tpl/layouts/affairs.html"
                    })
                    .state('affairs.entity', {
                        url: '/:entity_type/:entity_id',
                        views: {
                            '': {
                                templateUrl: function ($stateParams) {
                                    return 'tpl/entities/' + $stateParams.entity_type + '/actlist.html';
                                }
                            },
                            'entity': {
                                templateUrl: function ($stateParams) {
                                    return 'tpl/entities/' + $stateParams.entity_type + '/page.html';
                                }
                            }
                        }
                    })
                    .state('messages', {
                        url: '/messages',
                        templateUrl: "tpl/layouts/message.html",
                        resolve: load(['js/app/message/message.js'])
                    })
                    .state('messages.GROUP', {
                        url: '/GROUP/:session_id',
                        templateUrl: "tpl/layouts/message_group.html"
                    })
                    .state('messages.C2C', {
                        url: '/C2C/:session_id',
                        cache: true,
                        templateUrl: 'tpl/layouts/message_C2C.html',
                        resolve: load(['js/app/message/message.js'])
                    })
                    .state('stores', {
                        url: '/stores',
                        templateUrl: "tpl/layouts/stores.html"
                    })
                    .state('stores.store', {
                        url: '/{id}',
                        templateUrl: "tpl/entities/stores/home.html"
                    })
                    .state('stores.store.page', {
                        url: '/{entity_type}/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('stores.create', {
                        url: '/create',
                        templateUrl: "tpl/entities/stores/create.html"
                    })
                    .state('distributors', {
                        url: '/distributors',
                        templateUrl: "tpl/layouts/distributors.html"
                    })
                    .state('distributors.distributor', {
                        url: '/{id}',
                        templateUrl: "tpl/entities/distributors/home.html"
                    })
                    .state('distributors.distributor.page', {
                        url: '/{entity_type}/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('distributors.create', {
                        url: '/create',
                        templateUrl: "tpl/entities/distributors/create.html"
                    })

                    .state('insureNotice', {
                        url: '/insureNotice',
                        templateUrl: "tpl/layouts/insureNotice.html"
                    });
//                    .state('customer',{
//                    	url:'/{entity_type}/customer',
//                    	templateUrl: 'tpl/layouts/customer.html'
//                    })
//                    .state('customer.page',{
//                    	url:'/{entity_type}/{view_type}',
//                    	templateUrl: function ($stateParams) {
//                            return "tpl/entities/customer/" + $stateParams.view_type + ".html";
//                        }
//                    });


                // //暂时保险账单列表
                // $stateProvider
                //     .state('insurList', {
                //         url: '/insurList',
                //         templateUrl: "tpl/layouts/insurList.html"
                //     })
                //     .state('insurList.page', {
                //         url: '/{view_type}',
                //         templateUrl: function ($stateParams) {
                //             return "tpl/entities/insurList/" + $stateParams.view_type + ".html";
                //         },
                //         resolve: load(['bootstrapPaginator'])
                //     });


                //非车险客户列表
                $stateProvider
                    .state('otherInsureInterestPersons', {
                        url: '/otherInsureInterestPersons',
                        templateUrl: "tpl/layouts/otherInsureInterestPersons.html"
                    })
                    .state('otherInsureInterestPersons.page', {
                        url: '/{id}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/otherInsureInterestPersons/list.html";
                        }
                    });

                //消息通知角色配置
                $stateProvider
                .state('messageRoleConfig', {
                        url: '/messageRoleConfig',
                        cache: false,
                        templateUrl: "tpl/messageConfig/messageRole.html"

                    });

                //管理系统
                $stateProvider
                    .state('investors', {
                        url: '/investors',
                        templateUrl: "tpl/layouts/investors.html"
                    });

                //开发与配置
                $stateProvider
                    .state('workflowEntity', {
                        url: '/workflowEntity/{entity_key}/{type}',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/module.html";
                        }
                    })
                    .state('workflowEntity.list', {
                        url: '/list',
                        cache: false,
                        params:{
                            entity_key:null
                        },
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/list.html";
                        },
                        resolve: load(['bootstrapPaginator'])
                    })
                    .state('workflowEntity.form', {
                        url: '/form',
                        cache: false,
                        params:{
                            id:null
                        },
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/form.html";
                        },
                        resolve: load(['bootstrapPaginator'])
                    })
                    .state('workflowEntity.home', {
                        url: '/{id}/{orderId}/{entityName}/{actCode}/{entityId}/{phone}',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/home.html";
                            return "template/" + $stateParams.entity_key + "/home.html";
                        },
                        resolve: load(['bootstrapPaginator'])
                    })
                    .state('workflowEntity.home.profile', {
                        url: '/profile',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/info.html";
                            return "template/" + $stateParams.entity_key + "/profile.html";
                        },
                        resolve: load(['bootstrapPaginator'])
                    })
                    .state('workflowEntity.home.profile.info', {
                        url: '/info',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/info.html";
                        	console.log($stateParams.entity_key);
                            return "template/" + $stateParams.entity_key + "/info.html";
                        },
                        resolve: load(['bootstrapPaginator'])
                    })
                       .state('workflowEntity.home.profile.info.invite', {
                           url: '/info',
                           cache: false,
                           params:{
                           	'myInvite':null
                           },
                           templateUrl: function ($stateParams) {
                               //return "template/entities/info.html";
                           	console.log($stateParams.entity_key);
                               return "template/" + $stateParams.entity_key + "/info.html";
                           }
                       })
                       .state('workflowEntity.home.profile.info.fan', {
                           url: '/info',
                           cache: false,
                           params:{
                           	'myInvite':null
                           },
                           templateUrl: function ($stateParams) {
                               //return "template/entities/info.html";
                           	console.log($stateParams.entity_key);
                               return "template/" + $stateParams.entity_key + "/info.html";
                           }
                       })
                       .state('workflowEntity.home.profile.info.department', {
                           url: '/info',
                           cache: false,
                           params:{
                           	message:null
                           },
                           templateUrl: function ($stateParams) {
                               //return "template/entities/info.html";
                           	console.log($stateParams.entity_key);
                               return "template/" + $stateParams.entity_key + "/info.html";
                           }
                       })
                    .state('workflowEntity.home.profile.log', {
                        url: '/log',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/info.html";
                            return "template/" + $stateParams.entity_key + "/log.html";
                        }
                    })
                    .state('workflowEntity.home.profile.file', {
                         cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/info.html";
                            return "template/" + $stateParams.entity_key + "/file.html";
                        }
                    })
                    .state('workflowEntity.home.profile.update', {
                        url: '/update',
                        controller: "workflowEntityUpdateController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/form.html";
                        }
                    })
                    .state('workflowEntity.home.profile.create', {
                        url: '/create',
                        controller: "workflowEntityCreateController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/form.html";
                        }
                    })
                    .state('workflowEntity.home.profile.page', {
                        url: '/{entity_key}/{view_type}',
                        cache: false,
                        params:{
                            id:null
                        },
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/" + $stateParams.view_type + ".html";
                            // return  "template/entities/user/list.html";
                        },
                        resolve: load(['bootstrapPaginator'])
                    })
                    .state('workflowEntity.home.page', {
                        url: '/{entity_key}/{view_type}',
                        cache: false,
                        params:{
                            id:null
                        },
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/" + $stateParams.view_type + ".html";
                            // return  "template/entities/user/list.html";
                        },
                        resolve: load(['bootstrapPaginator'])
                    })

                    //工作流配置
                    .state('workflowEntityConfig', {
                        url: '/workflowEntityConfig/{entity_key}',
                        cache: false,
                        controller: "workflowEntityConfigModuleController",
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/entityConfigLayout.html";
                            //return  "template/entities/" + $stateParams.entity_type + "/module.html";
                        }
                    })
                    .state('workflowEntityConfig.acts', {
                        url: '/acts',
                        controller: "workflowEntityConfigActListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/actList.html";
                        }
                    })
                    .state('workflowEntityConfig.actCreate', {
                        url: '/act/create',
                        controller: "workflowEntityConfigActFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/actForm.html";
                        }
                    })
                    .state('workflowEntityConfig.actEdit', {
                        url: '/act/{act_id}',
                        controller: "workflowEntityConfigActFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/actForm.html";
                        }
                    })
                    .state('workflowEntityConfig.actRole', {
                        url: '/act/{act_id}/roles',
                        controller: "workflowEntityConfigActRoleController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/actRole.html";
                        }
                    })


                    .state('workflowEntityConfig.states', {
                        url: '/states',
                        controller: "workflowEntityConfigStateListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/stateList.html";
                        }
                    })
                    .state('workflowEntityConfig.stateCreate', {
                        url: '/state/create',
                        controller: "workflowEntityConfigStateFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/stateForm.html";
                        }
                    })
                    .state('workflowEntityConfig.stateEdit', {
                        url: '/state/{state_id}',
                        controller: "workflowEntityConfigStateFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/stateForm.html";
                        }
                    })
                    .state('workflowEntityConfig.stateAct', {
                        url: '/state/{state_id}/acts',
                        controller: "workflowEntityConfigStateActController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/stateAct.html";
                        }
                    })


                    .state('workflowEntityConfig.workflows', {
                        url: '/workflows',
                        controller: "workflowEntityConfigWorkflowListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/workflowList.html";
                        }
                    })
                    .state('workflowEntityConfig.workflowCreate', {
                        url: '/workflow/create',
                        controller: "workflowEntityConfigWorkflowFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/workflowForm.html";
                        }
                    })
                    .state('workflowEntityConfig.workflowEdit', {
                        url: '/workflow/{workflow_id}',
                        controller: "workflowEntityConfigWorkflowFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/workflowForm.html";
                        }
                    })
                    .state('workflowEntityConfig.workflowState', {
                        url: '/workflow/{workflow_id}/states',
                        controller: "workflowEntityConfigWorkflowStateController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/workflowState.html";
                        }
                    })

                    //审计配置
                    .state('auditEntityConfig', {
                        url: '/auditEntityConfig/{entity_key}',
                        cache: false,
                        controller: "auditEntityConfigModuleController",
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/entityConfigLayout.html";
                            //return  "template/entities/" + $stateParams.entity_type + "/module.html";
                        }
                    })
                    .state('auditEntityConfig.acts', {
                        url: '/acts',
                        controller: "auditEntityConfigActListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/actList.html";
                        }
                    })
                    .state('auditEntityConfig.actCreate', {
                        url: '/act/create',
                        controller: "auditEntityConfigActFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/actForm.html";
                        }
                    })
                    .state('auditEntityConfig.actEdit', {
                        url: '/act/{act_id}',
                        controller: "auditEntityConfigActFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/actForm.html";
                        }
                    })
                    .state('auditEntityConfig.actRole', {
                        url: '/act/{act_id}/roles',
                        controller: "auditEntityConfigActRoleController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/actRole.html";
                        }
                    })


                    .state('auditEntityConfig.states', {
                        url: '/states',
                        controller: "auditEntityConfigStateListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/stateList.html";
                        }
                    })
                    .state('auditEntityConfig.stateCreate', {
                        url: '/state/create',
                        controller: "auditEntityConfigStateFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/stateForm.html";
                        }
                    })
                    .state('auditEntityConfig.stateEdit', {
                        url: '/state/{state_id}',
                        controller: "auditEntityConfigStateFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/stateForm.html";
                        }
                    })
                    .state('auditEntityConfig.stateAct', {
                        url: '/state/{state_id}/acts',
                        controller: "auditEntityConfigStateActController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/stateAct.html";
                        }
                    })

                    //菜单类审计实体配置，最主要区别是“菜单有对应的可显示角色”/还有父菜单

                    .state('menuConfig', {
                        url: '/menuConfig/{entity_key}',
                        cache: false,
                        controller: "menuConfigModuleController",
                        templateUrl: function ($stateParams) {
                            return "tpl/menuConfigs/menuConfigLayout.html";
                        },
                        resolve: load(['js/controllers/menuConfigControllers.js', 'angularBootstrapNavTree'])
                    })
                    .state('menuConfig.create', {
                        url: '/create',
                        controller: "menuConfigFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/menuConfigs/form.html";
                        }
                    })
                    .state('menuConfig.edit', {
                        url: '/edit/{id}',
                        controller: "menuConfigFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/menuConfigs/form.html";
                        }
                    })


                    .state('products', {
                        url: '/products',
                        templateUrl: "tpl/layouts/products.html"
                    })
                    .state('products.product', {
                        url: '/{product_id}',
                        templateUrl: "tpl/entities/products/home.html"
                    })
                    .state('products.product.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/products/" + $stateParams.view_type + ".html";
                        }
                    })

                    .state('fees', {
                        url: '/fees',
                        templateUrl: "tpl/layouts/fees.html"
                    })
                    .state('fees.fee', {
                        url: '/{fee_id}',
                        templateUrl: "tpl/entities/fees/form.html"
                    })
                    .state('applications', {
                        url: '/applications',
                        templateUrl: "tpl/layouts/applications.html"
                    })
                    .state('applications.application', {
                        url: '/{application_id}',
                        templateUrl: "tpl/entities/applications/form.html"
                    })
                    .state('applications.new', {
                        url: '/new',
                        templateUrl: "tpl/entities/applications/form.html"
                    })
                    .state('customerDetail',{
                    	url:'#/workflowEntity/customer/1',
                    	templateUrl: "tpl/entities/applications/form.html"
                    });

                function load(srcs, callback) {
                    return {
                        deps: ['$ocLazyLoad', '$q',
                            function ($ocLazyLoad, $q) {
                                var deferred = $q.defer();
                                var promise = false;
                                srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                                if (!promise) {
                                    promise = deferred.promise;
                                }
                                angular.forEach(srcs, function (src) {
                                    promise = promise.then(function () {
                                        if (JQ_CONFIG[src]) {
                                            return $ocLazyLoad.load(JQ_CONFIG[src]);
                                        }
                                        angular.forEach(MODULE_CONFIG, function (module) {
                                            if (module.name == src) {
                                                name = module.name;
                                            } else {
                                                name = src;
                                            }
                                        });
                                        return $ocLazyLoad.load(name);
                                    });
                                });
                                deferred.resolve();
                                return callback ? promise.then(function () {
                                        return callback();
                                    }) : promise;
                            }]
                    }
                }


            }
        ]
    );
