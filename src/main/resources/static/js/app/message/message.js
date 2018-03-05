//左侧消息列表（目前支持类型为 个人personal与群组group）
app.controller('SessionListCtrl', ['$scope', '$rootScope', '$stateParams', function ($rootScope, $scope, $stateParams) {

}]);


//整个群组窗口控制器
app.controller('GroupCtrl', [' ', '$scope', '$stateParams', function ($rootScope, $scope, $stateParams) {
    $rootScope.group_id = $stateParams.session_id;
}]);


//整个群组窗口控制器
app.controller('GroupSessionCtrl', ['$rootScope', '$scope', '$stateParams', 'imService', function ($rootScope, $scope, $stateParams, imService) {
    $scope.session_id  = $stateParams.session_id + '';
    $scope.session_key = 'GROUP' + $scope.session_id;

    $scope.new_group_members = {};

    $scope.toggle_to_new_group_members = function (item) {
//            var k = 0;
//            var members = $scope.new_group_members;
//            for (var i in members) {
//                k++;
//                if (i == item.account) {
//                    delete $scope.new_group_members[item.account];
//                    break;
//                }
//                $scope.new_group_members[item.account] = item;
//            }
//            console.log(members);
//            if (members == ) {
        $scope.new_group_members[item.account] = item;
    };

    $scope.remove_from_new_group_members = function (item) {
        delete $scope.new_group_members[item.account];
    };

    $scope.deleteGroupMember = function (account) {
        var members = [account];
        imService.deleteGroupMember($scope.session_id, members);
    };

    $scope.save_new_group_members = function () {
        var members = [];
        angular.forEach($scope.new_group_members, function (member) {
            members.push({
                'Member_Account': member.account
            });
        });
        imService.addGroupMember($scope.session_id, members);

    };

    imService.select_session('GROUP', $scope.session_id);

}]);


//群组聊天提交框的控制器
app.controller('GroupMessageFormCtrl', ['$scope', '$rootScope', '$stateParams', 'imService', function ($scope, $rootScope, $stateParams, imService) {

    $scope.session_id  = $stateParams.session_id + '';
    $scope.session_key = 'GROUP' + $scope.session_id;

    $scope.message_input               = '';
    $rootScope.message_loading         = '';
    $rootScope.message_form_upload_bar = 0;


    //joshua 聊天输入窗口上传图片
    $scope.upload_picture = function () {
        imService.upload_picture();
        $scope.scroll_bottom();
    };

    //joshua 聊天输入窗口上传文件
    $scope.upload_file = function () {
        imService.upload_file();
        $scope.scroll_bottom();
    };

    //joshua 聊天输入窗口 提交消息内容
    $scope.form_submit = function () {
        if ($scope.message_input === '') {
            //alert('总得说点什么吧');
        } else {

            imService.send_group_message($stateParams.session_id, $scope.message_input);
            setTimeout(function () {
                $scope.message_input = '';
                $rootScope.$digest();
            }, 500);
            $scope.scroll_bottom();
        }
    };

    //joshua 聊天输入窗口 enter 提交信息
    $scope.form_keyup = function ($event) {
        if ($event.keyCode == 13) {
            $scope.form_submit();
        }
    };

    $scope.scroll_bottom = function () {
        setTimeout(function () {
            $('#session_wrapper').animate({scrollTop: 10000}, 500);
        }, 600);
    }
    $scope.scroll_bottom();

}]);


//群组里单条信息里的，文本类型部分，对应 TIMTextElem
app.controller('GroupTIMTextElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//群组里单条信息里的，地理类型部分，对应 TIMLocationElem
app.controller('GroupTIMLocationElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//群组里单条信息里的，表情类型部分，对应 TIMFaceElem
app.controller('GroupTIMFaceElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//群组里单条信息里的，自定义类型部分，对应 TIMCustomElem
app.controller('GroupTIMCustomElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//群组里单条信息里的，声音类型部分，对应 TIMSoundElem
app.controller('GroupTIMSoundElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//群组里单条信息里的，图片类型部分，对应 TIMImageElem
app.controller('GroupTIMImageElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//群组里单条信息里的，文件类型部分，对应 TIMFileElem
app.controller('GroupTIMFileElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);


//私人聊天窗口
app.controller('C2CCtrl', [' ', '$scope', '$stateParams', function ($rootScope, $scope, $stateParams) {

}]);


//整个群组窗口控制器
app.controller('C2CSessionCtrl', ['$rootScope', '$scope', '$stateParams', 'imService', function ($rootScope, $scope, $stateParams, imService) {
    $scope.session_id  = $stateParams.session_id + '';
    $scope.session_key = 'C2C' + $scope.session_id;

    imService.select_session('C2C', $scope.session_id);

}]);


//群组聊天提交框的控制器
app.controller('C2CMessageFormCtrl', ['$scope', '$rootScope', '$stateParams', 'imService', function ($scope, $rootScope, $stateParams, imService) {

    $scope.session_id  = $stateParams.session_id + '';
    $scope.session_key = 'C2C' + $scope.session_id;

    $scope.message_input               = '';
    $rootScope.message_loading         = '';
    $rootScope.message_form_upload_bar = 0;


    //joshua 聊天输入窗口上传图片
    $scope.upload_picture = function () {
        imService.upload_picture();
        $scope.scroll_bottom();
    };

    //joshua 聊天输入窗口上传文件
    $scope.upload_file = function () {
        imService.upload_file();
        $scope.scroll_bottom();
    };

    //joshua 聊天输入窗口 提交消息内容
    $scope.form_submit = function () {
        if ($scope.message_input === '') {
            //alert('总得说点什么吧');
        } else {

            imService.send_c2c_message($stateParams.session_id, $scope.message_input);
            setTimeout(function () {
                $scope.message_input = '';
                $rootScope.$digest();
            }, 500);
            $scope.scroll_bottom();
        }
    };

    //joshua 聊天输入窗口 enter 提交信息
    $scope.form_keyup = function ($event) {
        if ($event.keyCode == 13) {
            $scope.form_submit();
        }
    };

    $scope.scroll_bottom = function () {
        setTimeout(function () {
            $('#session_wrapper').animate({scrollTop: 10000}, 500);
        }, 600);
    }
    $scope.scroll_bottom();

}]);


//私聊里单条信息里的，文本类型部分，对应 TIMTextElem
app.controller('C2CTIMTextElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//私聊里单条信息里的，地理类型部分，对应 TIMLocationElem
app.controller('C2CTIMLocationElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//私聊里单条信息里的，表情类型部分，对应 TIMFaceElem
app.controller('C2CTIMFaceElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//私聊里单条信息里的，自定义类型部分，对应 TIMCustomElem
app.controller('C2CTIMCustomElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//私聊里单条信息里的，声音类型部分，对应 TIMSoundElem
app.controller('C2CTIMSoundElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//私聊里单条信息里的，图片类型部分，对应 TIMImageElem
app.controller('C2CTIMImageElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);
//私聊里单条信息里的，文件类型部分，对应 TIMFileElem
app.controller('C2CTIMFileElemCtrl', ['$scope', '$stateParams', function ($scope, $stateParams) {


}]);


