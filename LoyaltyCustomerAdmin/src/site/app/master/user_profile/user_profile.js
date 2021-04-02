(function () {
    angular.module("AppModule")
            .controller("UserProfileController", function ($scope, Factory, Notification, $rootScope) {
                $scope.model = {};
                $scope.ui = {};
                $scope.model.user = {
                    userIndex: null,
                    userName: null,
                    password: null,
                    newPassword: null,
                    reEnterPassword: null
                };

                var saveUrl = "/api/wms/master/user/update-user-password";

                $scope.ui.save = function () {
                    if ($scope.validate()) {
                        var detail = $scope.model.user;
                        var detailJSON = JSON.stringify(detail);

                        Factory.save(saveUrl, detailJSON,
                                function (data) {
                                    if (data) {
                                        Notification.success($scope.model.user.userName + " - " + "Password Successfully Updated");
                                    } else {
                                        Notification.error($scope.model.user.userName + " - " + "Password update failed!");
                                    }
                                    $scope.ui.reset();
                                },
                                function (data) {
                                    Notification.error(data.message);
                                    $scope.ui.reset();
                                }
                        );
                    }
                };
                $scope.validate = function () {
                    var check = true;
                    if (!$scope.model.user.userIndex) {
                        Notification.error("Cant find user");
                        check = false;
                    }
                    if (!$scope.model.user.password) {
                        Notification.error("Enter Password");
                        check = false;
                    }
                    if (!$scope.model.user.newPassword) {
                        Notification.error("Enter New Password");
                        check = false;
                    }
                    if (!$scope.model.user.reEnterPassword) {
                        Notification.error("RE-Enter New Password");
                        check = false;
                    }
                    if ($scope.model.user.reEnterPassword !== $scope.model.user.newPassword) {
                        Notification.error("Password Doesn't match");
                        check = false;
                    }
                    return check;

                };
                $scope.ui.reset = function () {
                    $scope.model.user = {
                        userIndex: $rootScope.globals.currentUser.indexNo,
                        userName: $rootScope.globals.currentUser.username,
                        password: null,
                        newPassword: null,
                        reEnterPassword: null
                    };
                };

                $scope.ui.init = function () {
                    $scope.model.user.userName = $rootScope.globals.currentUser.username;
                    $scope.model.user.userIndex = $rootScope.globals.currentUser.indexNo;
                };
                $scope.ui.init();
            });
}());