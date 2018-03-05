"use strict";

/* joshua: entity Common Controllers */
app.controller("workflowEntityModuleController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        function ($rootScope, $scope, $http, $stateParams) {

            $scope.entity_key = $stateParams.entity_key;//单数 例user

        }
    ]
);

app.controller("workflowEntityStatesController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        function ($rootScope, $scope, $http, $state, $stateParams) {

            $scope.entity_key = $stateParams.entity_key;   //单数 例user
            $scope.states     = [];

            $scope.getStates = function () {
                // if ($scope.entity_key === "submitProposal") {
                //
                //     $http.get("/rest/submitProposalStates/search/getAllState?projection=onlyLabel")
                //         .success(function (resResult) {
                //             $scope.states = resResult._embedded[$scope.entity_key + "States"];
                //         });
                //
                // } else {
                    //joshua: 取得单数的key
                    $http.get("/rest/" + $scope.entity_key + "States")
                        .success(function (resResult) {

                            $scope.states = resResult._embedded[$scope.entity_key + "States"];
                        });

                }
            // };

            $scope.getStates();
        }
    ]
);

app.controller("workflowEntityHomeController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        function ($rootScope, $scope, $http, $stateParams) {

        }
    ]
);

app.controller("workflowEntityListController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        function ($rootScope, $scope, $http, $stateParams) {

            $scope.pageList = {
                size: 0,
                total_elements: 0,
                current_page: 0,
                num_page: 1,
                total_page: 1
            };

            if ($stateParams.entity_key === "orderWdsjsh") {
                $scope.entity_key = $stateParams.entity_key + "e";
            } else {
                $scope.entity_key = $stateParams.entity_key;
            }
            //单数 例user
            $scope.state_code = $stateParams.state_code;

            $scope.list = [];

            $scope.getList = function (page) {
                //此处的查询需要根据角色判断listOwn之类的
                $scope.link = "/rest/" + $scope.entity_key + "s/search/listStateOwnDepartmentAndChildren?stateCode=" + $scope.state_code + "&size=20&page=" + page;
                $http({method: "GET", url: $scope.link}).success(function (resResult) {
                    $scope.list = resResult._embedded[$scope.entity_key + "s"];
                    $scope.pageList = {
                        size: resResult.page.size,
                        total_elements: resResult.page.totalElements,
                        current_page: resResult.page.number,
                        num_page: resResult.page.totalPages,
                        total_page: resResult.page.totalPages
                    };

                    if ($scope.list.length > 0) {
                        $rootScope.setPaginator("#" + $stateParams.entity_key + "_list_paginator", $scope.pageList, $scope.getList);
                    }

                });
            };

            $scope.getList(0);

        }
    ]
);

app.controller("workflowEntityProfileController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            if ($stateParams.entity_key === "orderWdsjsh") {
                $scope.entity_key = $stateParams.entity_key + "e";
            } else {
                $scope.entity_key = $stateParams.entity_key;
            }

            $scope.id = $stateParams.id;

            $scope.profile = {};
            $scope.canActs = [];
            $scope.links   = [];

            $scope.getProfile = function () {

                $http.get("/rest/" + $scope.entity_key + "s/" + $scope.id).success(function (resResult) {
                    $scope.profile = resResult;

                    $scope.canActs = $scope.profile.currentUserCanActList;
                    // angular.forEach($scope.profile.currentUserCanActList,function (val,key) {
                    //     if(val.actGroup == "UPDATE" || val.actGroup == "OPERATE"){
                    //         $scope.canActs.push(val) ;
                    //     }
                    // });

                    $scope.links = $scope.profile._links;
                });
            };

            $scope.getProfile();

            $scope.excute = function (actCode) {
                if (actCode === "update") {
                    $state.go("workflowEntity.home.profile.update", {}, {reload: true});
                }

            };

        }
    ]
);

app.controller("workflowEntityInfoController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {
        }
    ]
);

app.controller("workflowEntityLogController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            if ($stateParams.entity_key === "orderWdsjsh") {
                $scope.entity_key = $stateParams.entity_key + "e";
            } else {
                $scope.entity_key = $stateParams.entity_key;
            }

            $scope.id = $stateParams.id;

            $scope.logs = [];

            $scope.getLogs = function () {
                $http.get("/rest/" + $scope.entity_key + "s/" + $scope.id + "/logs").success(function (resResult) {

                    $scope.logs = resResult._embedded[$stateParams.entity_key + "Logs"];

                    angular.forEach($scope.logs, function (value, index) {
                        $http.get(value._links.createdBy.href).success(function (resResult) {
                            value.createdBy = resResult.nickname;
                        });
                    });

                });
            };

            $scope.getLogs();

            $scope.jumpToLog = function (log) {

                $scope.log = log;

                $("#log_modal").modal("show");
            };
        }
    ]
);

app.controller("workflowEntityFileController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        "FileUploader",
        function ($rootScope, $scope, $http, $stateParams, $state, FileUploader) {

            if ($stateParams.entity_key === "orderWdsjsh") {
                $scope.entity_key = $stateParams.entity_key + "e";
            } else {
                $scope.entity_key = $stateParams.entity_key;
            }

            $scope.id = $stateParams.id;

            $scope.level2Tree = [];
            $scope.level3Tree = [];

            $scope.firstFolder  = {opened: true, folderName: ""};
            $scope.secondFolder = {opened: false, folderName: ""};
            $scope.thirdFolder  = {opened: false, folderName: ""};

            /*
             * 获取该个实体的文件夹目录
             * @author  金杭
             * @return  无
             * */
            $scope.getFileTree = function () {
                $http.get("/rest/" + $scope.entity_key + "s/" + $scope.id).success(function (resResult) {

                    if (JSON.stringify(resResult.fileCategoryTree) !== "{}") {
                        $scope.fileTree = resResult.fileCategoryTree;
                    } else {
                        $scope.fileTree = null;
                    }

                });
            };

            $scope.getFileTree();

            /*
             * 点击进入二级文件夹
             * @author  金杭
             * @param   folderName  {string}  本级文件夹名称
             * @param   level2      {object}  二级文件夹
             * @return  无
             * */
            $scope.jumpToLevel2 = function (folderName, level2) {

                $scope.firstFolder.folderName  = folderName;
                $scope.secondFolder.folderName = "";

                $scope.level2Tree = level2;

                $scope.firstFolder.opened  = false;
                $scope.secondFolder.opened = true;
                $scope.thirdFolder.opened  = false;
            };

            /*
             * 点击查看该文件夹下所有文件
             * @author  金杭
             * @param   folderName  {string}  二级文件夹名称
             * @return  无
             * */
            $scope.jumpToLevel3 = function (folderName) {

                $scope.secondFolder.folderName = folderName;

                $http.get("/rest/orderWdsjshFiles/search/findByEntityIdAndTopcategoryAndSubcategory?id=" + $scope.id + "&topcategory=" + $scope.firstFolder.folderName + "&subcategory=" + $scope.secondFolder.folderName)
                    .success(function (resResult) {

                            $scope.level3Tree = resResult._embedded[$stateParams.entity_key + "Files"];

                            $scope.firstFolder.opened  = false;
                            $scope.secondFolder.opened = false;
                            $scope.thirdFolder.opened  = true;

                        }
                    );
            };

            /*
             * 点击回到上一级文件夹
             * @author  金杭
             * @return  无
             * */
            $scope.backFolder = function () {

                if ($scope.thirdFolder.opened) {

                    $scope.firstFolder.opened  = false;
                    $scope.secondFolder.opened = true;
                    $scope.thirdFolder.opened  = false;

                } else if ($scope.secondFolder.opened) {

                    $scope.firstFolder.opened  = true;
                    $scope.secondFolder.opened = false;
                    $scope.thirdFolder.opened  = false;

                }

                $scope.secondFolder.folderName = "";
            };

            //以下是上传文件的动作详情参考前端框架示例 js/controllers/file-upload.js
            $scope.uploader = new FileUploader({
                url: '/fileUpload'
            });

            $scope.uploader.filters.push({
                name: 'customFilter',
                fn: function (item, options) {
                    return this.queue.length < 20;//文件上传数量不超过10个
                }
            });

            $scope.uploader.onAfterAddingFile = function (fileItem) {
                //增加行为code
                fileItem.actCode = $scope.actCode;

                if (fileItem.file.type.indexOf("image") !== -1) {
                    fileItem.formData = [{fileType: "image"}]
                } else {
                    fileItem.formData = [{fileType: "file"}]
                }

            };

            $scope.uploader.onErrorItem = function (fileItem, response, status, headers) {
                $rootScope.toasterError("上传失败！", $scope.subcategory + "：" + fileItem.file.name + " 上传失败！");
            };

            $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {

                //增加目录结构
                response.result.topcategory = $scope.topcategory;
                response.result.subcategory = $scope.subcategory;

                $http({
                    method: "PATCH",
                    url: "rest/" + $scope.entity_key + "s/" + $scope.id,//这里是关联的实体
                    data: {
                        fileObject: response.result,//fileObject是上传完文件后的文件对象
                        act: fileItem.actCode//上传文件的行为
                    }
                }).success(function (data) {
                    $rootScope.toasterSuccess("上传成功！", $scope.subcategory + "：" + fileItem.file.name + " 上传成功！");
                });
            };

            $scope.uploader.onCompleteAll = function () {
            };

            $("#upload_file_modal").on("hidden.bs.modal", function (e) {
                $state.reload();
            })
        }
    ]
);

app.controller("workflowEntityUpdateController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            $scope.save = function () {
                alert("save");
            };
        }
    ]
);

app.controller("workflowEntityCreateController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            $scope.save = function () {
                alert("save");
            };
        }
    ]
);

app.controller("workflowEntityActsController",
    [
        "$scope",
        function ($scope) {
        }
    ]
);