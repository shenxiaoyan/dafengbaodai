/**
 * Created by Jh on 2017/8/10.
 */
"use strict";

/* joshua: entity Common Controllers */


app.controller("menuConfigModuleController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$resource",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $resource, $stateParams, $state) {

            $scope.select_branch = function (branch) {
                $state.go("menuConfig.edit", branch);
            };

            console.log("menuConfigModuleController");

        }
    ]
);

//创建
app.controller("menuConfigFormController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$resource",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $resource, $stateParams, $state) {

            $scope.menu_id  = $stateParams.id;
            $scope.ownRoles = [];
            $scope.allRoles = [];
            $scope.menu     = {};

            if ($scope.menu_id) {
                $http.get("rest/menus/" + $scope.menu_id).success(function (resResult) {

                    $scope.menu = resResult;

                    $http.get($scope.menu._links.visibleRoles.href).success(function (resResult2) {
                        $scope.ownRoles = resResult2._embedded["roles"];
                        $scope.getAllRoles($scope.ownRoles);
                    });
                });
            }

            $scope.getAllRoles = function (ownRoles) {
                $http.get("rest/roles?size=10000").success(function (resResult) {
                    $scope.allRoles = resResult._embedded["roles"];

                    angular.forEach($scope.allRoles,function(value,index){
                        value.isSelected = false;

                        angular.forEach(ownRoles,function(v2,i2){
                            if(v2.id === value.id){
                                value.isSelected = true;
                            }
                        });

                    });
                });
            };

            $scope.save = function () {

                $scope.menu.state = "/rest/menuState/2";

                if ($scope.menu.parent > 0 ) {
                    $scope.menu.parent = "rest/menus/" + $scope.menu.parent;
                }

                if ($scope.menu._links) {
                    $http({
                        url: $scope.menu._links.self.href,
                        method: "PATCH",
                        data: $scope.menu
                    }).success(function (resResult) {
                        $state.go("menuConfig", {entity_key:"menu"}, {reload: true});
                        $rootScope.toasterSuccess("成功！", "修改成功！");
                    });
                } else {
                    $http.post("rest/menus", $scope.menu).success(function (resResult) {

                        $http({
                            method: "PATCH",
                            url: "rest/menus/" + resResult.id + "/visibleRoles",
                            data: "rest/roles/1",
                            headers: {"Content-Type": "text/uri-list"}
                        }).success(function (resResult) {
                            $state.go("menuConfig", {entity_key:"menu"}, {reload: true});
                            $rootScope.toasterSuccess("成功！", "新建成功！");
                        });

                    });
                }

            };

            $scope.changeMenuRole = function (role) {
                //增加
                if (role.isSelected) {
                    $http({
                        method: "PATCH",
                        url: $scope.menu._links.visibleRoles.href,
                        data: role._links.self.href,
                        headers: {"Content-Type": "text/uri-list"}
                    }).success(function (resResult) {
                        $rootScope.toasterSuccess("成功！", "关联成功！");
                    });
                } else {//删除

                    $http({
                        method: "DELETE",
                        url: $scope.menu._links.visibleRoles.href + "/" + role.id
                    }).success(function (resResult) {
                        $rootScope.toasterInfo("成功！", "已取消关联！");
                    });

                }
            };

        }
    ]
);
