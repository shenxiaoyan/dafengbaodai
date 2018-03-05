/**
 * Created by Jh on 2017/8/10.
 */
"use strict";

/* joshua: entity Common Controllers */


app.controller("auditEntityConfigModuleController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$resource",
        "$stateParams",
        function ($rootScope, $scope, $http, $resource, $stateParams) {

            $scope.entity_key = $stateParams.entity_key;//复数
            console.log("auditEntityConfigModuleController");

        }
    ]
);

//行为配置部分
app.controller("auditEntityConfigActListController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$resource",
        "$stateParams",
        function ($rootScope, $scope, $http, $state, $resource, $stateParams) {

            $scope.entity_key = $stateParams.entity_key;//复数
            $scope.acts       = [];

            $scope.getActs = function () {
                $http.get("/rest/" + $scope.entity_key + "Acts?size=10000")
                    .success(function (resResult) {
                        $scope.acts = resResult._embedded[$scope.entity_key + "Acts"];
                    })
                    .error(function (resResult) {

                    });
            };

            $scope.getActs();

            $scope.actEdit = function (act_id) {
                $state.go("auditEntityConfig.actEdit", {act_id: act_id}, {reload: true});
            };

            $scope.actRole = function (act_id) {
                $state.go("auditEntityConfig.actRole", {act_id: act_id}, {reload: true});
            };

        }
    ]
);

app.controller("auditEntityConfigActFormController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$resource",
        "$stateParams",
        function ($rootScope, $scope, $http, $state, $resource, $stateParams) {

            //joshua: 如果参数里带了 act_id 则为修改，否则为创建
            $scope.entity_key = $stateParams.entity_key;
            $scope.act_id     = $stateParams.act_id;

            $scope.act = {};

            $scope.noticeRole           = null;
            $scope.noticeDepartmenttype = null;

            $scope.allRoles           = [];
            $scope.allDepartmenttypes = [];

            //获取所有的角色
            $scope.getAllRoles = function () {
                $http.get("/rest/roles?size=10000")
                    .success(function (resResult) {
                        $scope.allRoles = resResult._embedded["roles"];

                        if ($scope.act_id) {
                            $http.get($scope.act._links.noticeRole.href).success(function (response) {
                                angular.forEach($scope.allRoles, function (value, index) {
                                    if (response.id === value.id) {
                                        $scope.noticeRole = value;
                                    }
                                });
                            });
                        }

                    });
            };

            //获取所有部门类型
            $scope.getAllDepartmenttypes = function () {
                $http.get("/rest/departmenttypes?size=10000")
                    .success(function (resResult) {
                        $scope.allDepartmenttypes = resResult._embedded["departmenttypes"];

                        if ($scope.act_id) {
                            $http.get($scope.act._links.noticeDepartmentType.href).success(function (response) {
                                angular.forEach($scope.allDepartmenttypes, function (value, index) {
                                    if (response.id === value.id) {
                                        $scope.noticeDepartmenttype = value;
                                        $scope.setRoleFromDepartment(true);
                                    }
                                });
                            });
                        }else{
                            $scope.setRoleFromDepartment(false);
                        }

                    });
            };

            if ($scope.act_id) {
                $http.get("rest/" + $scope.entity_key + "Acts/" + $scope.act_id).success(function (resResult) {

                    $scope.act = resResult;

                    $scope.getAllRoles();
                    $scope.getAllDepartmenttypes();
                });
            }else{
                $scope.getAllRoles();
                $scope.getAllDepartmenttypes();
            }

            $scope.setRoleFromDepartment = function (bool) {

                if ($scope.noticeDepartmenttype && bool) {
                    $http.get($scope.noticeDepartmenttype._links.roles.href).success(function (resResult) {

                        $scope.allRoles = resResult._embedded["roles"];

                        $http.get($scope.act._links.noticeRole.href).success(function (response) {
                            angular.forEach($scope.allRoles, function (value, index) {
                                if (response.id === value.id) {
                                    $scope.noticeRole = value;
                                }
                            });
                        });

                    });
                } else if ($scope.noticeDepartmenttype && !bool) {
                    $http.get($scope.noticeDepartmenttype._links.roles.href).success(function (resResult) {
                        $scope.allRoles = resResult._embedded["roles"];
                    });
                }

            };

            $scope.save = function () {

                $scope.act.noticeRole       = $scope.noticeRole ? $scope.noticeRole._links.self.href : null;
                $scope.act.targetState      = $scope.targetState ? $scope.targetState._links.self.href : null;
                $scope.act.noticeDepartmenttype = $scope.noticeDepartmenttype ? "/rest/departmenttypes/" + $scope.noticeDepartmenttype.id : null;

                if ($scope.act._links) {
                    $http({
                        url: $scope.act._links.self.href,
                        method: "PATCH",
                        data: $scope.act
                    }).success(function (resResult) {
                        $state.go("auditEntityConfig.acts", {}, {reload: true});
                        $rootScope.toasterSuccess("成功！", "修改成功！");
                    });
                } else {
                    $http.post("rest/" + $scope.entity_key + "Acts", $scope.act).success(function (resResult) {
                        $state.go("auditEntityConfig.acts", {}, {reload: true});
                        $rootScope.toasterSuccess("成功！", "新建成功！");
                    });
                }

            };
        }
    ]
);

app.controller("auditEntityConfigActRoleController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$resource",
        "$stateParams",
        function ($rootScope, $scope, $http, $resource, $stateParams) {
            $scope.entity_key = $stateParams.entity_key;
            $scope.act_id     = $stateParams.act_id;

            $scope.allRoles = [];
            $scope.actRoles = [];

            $scope.getAllRoles = function (actRoles) {
                $http.get("/rest/roles?size=10000")
                    .success(function (resResult) {

                        $scope.allRoles = resResult._embedded["roles"];

                        angular.forEach($scope.allRoles, function (value, index) {

                            value.isSelected = false;

                            angular.forEach(actRoles, function (v2, i2) {
                                if (v2.id === value.id) {
                                    value.isSelected = true;
                                }
                            });

                        });
                    });
            };


            $scope.getActRoles = function () {
                $http.get("/rest/" + $scope.entity_key + "Acts/" + $scope.act_id + "/roles?size=10000")
                    .success(function (resResult) {

                        $scope.actRoles = resResult._embedded["roles"];

                        $scope.getAllRoles($scope.actRoles);
                    });
            };

            $scope.getActRoles();

            $scope.changeActRole = function (role) {
                //增加
                if (role.isSelected) {
                    $http({
                        method: "PATCH",
                        url: "/rest/" + $scope.entity_key + "Acts/" + $scope.act_id + "/roles",
                        data: role._links.self.href,
                        headers: {"Content-Type": "text/uri-list"}
                    }).success(function (resResult) {
                        $rootScope.toasterSuccess("成功！", "关联成功！");
                    })
                } else {//删除

                    $http({
                        method: "DELETE",
                        url: "/rest/" + $scope.entity_key + "Acts/" + $scope.act_id + "/roles/" + role.id
                    }).success(function (resResult) {
                        $rootScope.toasterInfo("成功！", "已取消关联！");
                    });

                }
            };


        }
    ]
);

//状态配置部分
app.controller("auditEntityConfigStateListController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$resource",
        "$stateParams",
        function ($rootScope, $scope, $http, $state, $resource, $stateParams) {

            $scope.entity_key = $stateParams.entity_key;//复数
            $scope.states     = [];

            $scope.getStates = function () {
                $http.get("/rest/" + $scope.entity_key + "States?size=10000")
                    .success(function (resResult) {
                        $scope.states = resResult._embedded[$scope.entity_key + "States"];
                    })
                    .error(function (resResult) {

                    });
            };
            $scope.getStates();

            $scope.stateEdit = function (state_id) {
                $state.go("auditEntityConfig.stateEdit", {state_id: state_id}, {reload: true});
            };

            $scope.stateAct = function (state_id) {
                $state.go("auditEntityConfig.stateAct", {state_id: state_id}, {reload: true});
            };

        }
    ]
);

app.controller("auditEntityConfigStateFormController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$resource",
        "$stateParams", function ($rootScope, $scope, $http, $state, $resource, $stateParams) {

        $scope.entity_key = $stateParams.entity_key;
        $scope.state_id   = $stateParams.state_id;
        $scope.state      = {};

        if ($scope.state_id) {
            $http.get("rest/" + $scope.entity_key + "States/" + $scope.state_id).success(function (resResult) {
                $scope.state = resResult;
            }).error(function (resResult) {

            });
        }

        $scope.save = function () {

            if ($scope.state._links) {
                $http({
                    url: $scope.state._links.self.href,
                    method: "PATCH",
                    data: $scope.state
                }).success(function (resResult) {
                    $state.go("auditEntityConfig.states", {}, {reload: true});
                    $rootScope.toasterSuccess("成功！", "修改成功！");
                }).error(function (resResult) {

                })
            } else {
                $http.post("rest/" + $scope.entity_key + "States", $scope.state).success(function (resResult) {
                    $state.go("auditEntityConfig.states", {}, {reload: true});
                    $rootScope.toasterSuccess("成功！", "新建成功！");
                })
            }

        };
    }
    ]
);

app.controller("auditEntityConfigStateActController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$resource",
        "$stateParams",
        function ($rootScope, $scope, $http, $resource, $stateParams) {

            $scope.entity_key = $stateParams.entity_key;
            $scope.state_id   = $stateParams.state_id;

            $scope.allActs   = [];
            $scope.stateActs = [];

            $scope.getAllActs = function (stateActs) {
                $http.get("/rest/" + $scope.entity_key + "Acts?size=10000")
                    .success(function (resResult) {

                        $scope.allActs = resResult._embedded[$scope.entity_key + "Acts"];

                        angular.forEach($scope.allActs, function (value, index) {

                            value.isSelected = false;

                            angular.forEach(stateActs, function (v2, i2) {
                                if (v2.id === value.id) {
                                    value.isSelected = true;
                                }
                            });

                        });
                    });
            };


            $scope.getStateActs = function () {
                $http.get("/rest/" + $scope.entity_key + "States/" + $scope.state_id + "/acts?size=10000")
                    .success(function (resResult) {

                        $scope.stateActs = resResult._embedded[$scope.entity_key + "Acts"];

                        $scope.getAllActs($scope.stateActs);

                    });
            };

            $scope.getStateActs();

            $scope.changeStateAct = function (act) {
                //增加
                console.log(act);
                if (act.isSelected) {

                    $http({
                        method: "PATCH",
                        url: "/rest/" + $scope.entity_key + "States/" + $scope.state_id + "/acts",
                        data: act._links.self.href,
                        headers: {"Content-Type": "text/uri-list"}
                    }).success(function (resResult) {
                        $rootScope.toasterSuccess("成功！", "关联成功！");
                    });

                } else {//删除

                    $http({
                        method: "DELETE",
                        url: "/rest/" + $scope.entity_key + "States/" + $scope.state_id + "/acts/" + act.id
                    }).success(function (resResult) {
                        $rootScope.toasterInfo("成功！", "已取消关联！");
                    });

                }
            };

        }
    ]
);