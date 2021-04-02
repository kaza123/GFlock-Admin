(function () {
    angular.module("AppModule")
            .controller("UserController", function ($scope, Factory, Notification) {
                $scope.model = {};
                $scope.ui = {};
                $scope.model.user= {};
                $scope.model.userList = [];
                $scope.model.departmentList = [];
                $scope.model.employeeList = [];

                var findAllDepartmentUrl = "/api/wms/master/department/find-all-department";
                var findAllEmployeeUrl = "/api/wms/master/employee/find-all-employee";
                var findAllUrl = "/api/wms/master/user/find-all-user";
                var saveUrl = "/api/wms/master/user/save-user";
                var deleteUrl = "/api/wms/master/user/delete-user/";

                $scope.ui.reset = function () {
                    $scope.model.user = {};
                };
                $scope.ui.edit = function (user, index) {
                    $scope.model.userList.splice(index, 1);
                    $scope.model.user = user;
                };
                
                $scope.ui.delete = function (indexNo, index) {
                    Factory.delete(deleteUrl, indexNo,
                            function (data) {
                                Notification.success("User Delete Successfully");
                                $scope.model.userList.splice(index, 1);
                            },
                            function (data) {
                                Notification.error(data.message);
                            }
                    );
                };

                $scope.ui.save = function () {
                    if ($scope.validate()) {
                        var detail = $scope.model.user;
                        var detailJSON = JSON.stringify(detail);

                        Factory.save(saveUrl, detailJSON,
                                function (data) {
                                    Notification.success(data.indexNo + " - " + "User Save Successfully");
                                    $scope.model.userList.push(data);
                                    $scope.ui.reset();
                                },
                                function (data) {
                                    Notification.error(data.message);
                                }
                        );
                    }
                };
                $scope.ui.employeeLable = function (indexNo) {
                    var employee;
                    angular.forEach($scope.model.employeeList, function (value) {
                        if (value.indexNo === parseInt(indexNo)) {
                            employee = value.name;
                            return;
                        }
                    });
                    return employee;
                };
                $scope.ui.departmentLable = function (indexNo) {
                    var department;
                    angular.forEach($scope.model.departmentList, function (value) {
                        if (value.indexNo === parseInt(indexNo)) {
                            department = value.name;
                            return;
                        }
                    });
                    return department;
                };

                $scope.validate = function () {
                    if (!$scope.model.user.userName) {
                        Notification.error("Please Input user Name !!!");
                        return false;
                    } else if (!$scope.model.user.password) {
                        Notification.error("Please Input User Password !!!");
                        return false;
                    } else if (!$scope.model.user.reEnterPassword) {
                        Notification.error("Please Input Re-Enter Password !!!");
                        return false;
                    } else if (!$scope.model.user.type) {
                        Notification.error("Please Input User Type!!!");
                        return false;
                    } else if (!$scope.model.user.department) {
                        Notification.error("Please Input User Department !!!");
                        return false;
                    } else if (!$scope.model.user.employee) {
                        Notification.error("Please Input User Descripton !!!");
                        return false;
                    } else if ($scope.model.user.password !== $scope.model.user.reEnterPassword) {
                        Notification.error("Password Not Match !!!");
                        return false;
                    } else if ($scope.model.user.userName && $scope.model.user.password && $scope.model.user.type && $scope.model.user.department && $scope.model.user.employee) {
                        return true;
                    }
                };


                $scope.ui.init = function () {
                    Factory.findAll(findAllUrl, function (data) {
                        $scope.model.userList = data;
                    });
                    Factory.findAll(findAllDepartmentUrl, function (data) {
                        $scope.model.departmentList = data;
                    });
                    Factory.findAll(findAllEmployeeUrl, function (data) {
                        $scope.model.employeeList = data;
                    });

                };
                $scope.ui.init();
            });
}());