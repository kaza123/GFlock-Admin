(function () {
    angular.module("AppModule")
            .controller("CalenterController", function ($scope, $filter, $rootScope, Factory, $location, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.data.isActive = true;
                $scope.model.limitedCount = 20;
                $scope.ui = {};

                var checkDate = "/api/sv/master/calender/find-by-date/";
                var pointType = "/api/sv/master/point-type/find-by-active";
                var saveUrl = "/api/sv/master/calender/save";
                var top20Url = "/api/sv/master/calender/top-20/";
                var countUrl = "/api/sv/transaction/sms-discount/get-count";

                $scope.ui.checkDate = function () {
                    var date = $filter('date')($scope.model.data.date, "yyyy-MM-dd");
                    Factory.findOne(checkDate + date,
                            function (data) {
                                if (data) {
                                    $scope.model.data = data;
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.findType = function () {
                    Factory.findAll(pointType,
                            function (data) {
                                if (data) {
                                    $scope.model.pointTypeList = data;
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.model.pointTypeLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.pointTypeList, function (pointType) {
                        if (pointType.indexNo === parseInt(model)) {
                            lable = pointType.name + ' - ' + pointType.point;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.ui.getTop20 = function () {
                    if (!$scope.model.limitedCount) {
                        $scope.model.limitedCount = 20;
                    }
                    Factory.findOne(top20Url + $scope.model.limitedCount,
                            function (data) {
                                if (data) {
                                    $scope.model.dataList = data;
                                }

                            },
                            function () {
                            }
                    );
                };
                $scope.ui.isActive = function (isAct) {
                    if (isAct) {
                        return "text-primary";
                    }
                    return "text-pink";
                };
                $scope.ui.setEdit = function (data) {
                    $scope.model.data = data;
                    $scope.model.data.date = new Date(data.date);

                };
                $scope.ui.clear = function () {
                    $scope.model.data = {};
                    $scope.model.data.isActive = true;
                    $scope.model.limitedCount = 20;
                    $scope.ui.getTop20();
                };
                $scope.ui.save = function () {
                    var check = true;
                    if (!$scope.model.data.date) {
                        check = false;
                        Notification.error('Enter Date !');
                    }
                    if (!$scope.model.data.point) {
                        check = false;
                        Notification.error('Enter point !');
                    }
                    if (!$scope.model.data.pointValue) {
                        check = false;
                        Notification.error('Enter point value !');
                    }
                    if ($filter('date')($scope.model.data.date, "yyyy-MM-dd") < $filter('date')(new Date(), "yyyy-MM-dd")) {
                        check = false;
                        Notification.error('Invalied Date. Date must be grater then current date !');
                    }
                    if (check) {
                        $scope.model.data.date=$filter('date')($scope.model.data.date, "yyyy-MM-dd");
                        var detail = $scope.model.data;
                        console.log(detail);
                        console.log(detail);
                        var detailJSON = JSON.stringify(detail);
                        Factory.save(saveUrl, detailJSON,
                                function (data) {
                                    console.log("success data ");
                                    Notification.success("plant issue success ");
                                    $scope.ui.clear();
                                },
                                function (data) {
                                    console.log(data);
                                    Notification.error(data.message);
                                }
                        );

                    }
                };
                $scope.ui.init = function () {
                    $scope.model.limitedCount = 20;
                    console.log($rootScope.globals.currentUser);
                    $scope.ui.getTop20();
//                    $scope.ui.getCount();
                    $scope.ui.findType();
                };
                $scope.ui.init();
            });
}());

