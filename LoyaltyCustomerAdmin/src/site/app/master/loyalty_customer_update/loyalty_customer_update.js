(function () {
    angular.module("AppModule")
            .controller("LoyaltyUpdateController", function ($scope, $rootScope, $filter, Factory, Notification) {
                $scope.model = {};
                $scope.ui = {};
                $scope.model.limitedCount = 20;
                $scope.model.getLoyaltyTypeList = [];

//                var checkMobileUrl = "/api/sv/master/loyalty/find-by-mobile/";
                var saveUrl = "/api/sv/master/loyalty/save";
                var top20Url = "/api/sv/master/loyalty/find-top20/";
                var getLoyaltyTypeList = "/api/sv/master/loyalty-type/findAll";
                var countUrl = "/api/sv/master/loyalty/get-count";

                $scope.ui.save = function () {
                    var check = true;
                    $scope.model.data.bYear = $filter('date')($scope.model.data.yearMonth, "yyyy");
                    $scope.model.data.bMonth = $filter('date')($scope.model.data.yearMonth, "MM");
                    if (!$scope.model.data.recidance) {
                        Notification.error("Select Mr Miss Mrs ");
                        check = false;
                    }
                    if (!$scope.model.data.indexNo) {
                        Notification.error("loyalty Customer update only");
                        check = false;
                    }
                    if (!$scope.model.data.name) {
                        Notification.error("Please Enter name");
                        check = false;
                    }
                    if (!$scope.model.data.mobileNo) {
                        Notification.error("Please Enter mobile number");
                        check = false;
                    }
                    if (!$scope.model.data.loyaltyNo) {
                        Notification.error("Please Enter loyalty number");
                        check = false;
                    }
                    if (!$scope.model.data.mobileNo.length === 11) {
                        Notification.error("Please Enter your valid mobile number with 94 ");
                        check = false;
                    }
                    if ($scope.model.data.bYear && ($scope.model.data.bYear > 9999 || $scope.model.data.bYear <= 0)) {
                        Notification.error("Please Enter the year");
                        check = false;
                    }
                    if ($scope.model.data.bMonth && ($scope.model.data.bMonth > 12 || $scope.model.data.bMonth <= 0)) {
                        Notification.error("Please Enter the month");
                        check = false;
                    }
                    if ($scope.model.data.bDate && ($scope.model.data.bDate > 31 || $scope.model.data.bDate <= 0)) {
                        Notification.error("Please Enter the day");
                        check = false;
                    }
                    if ($scope.model.data.bDate && (!$scope.model.data.bYear || !$scope.model.data.bMonth)) {
                        Notification.error("Please Enter the year and month");
                        check = false;
                    }
                    if (!$scope.model.data.bDate && ($scope.model.data.bYear || $scope.model.data.bMonth)) {
                        Notification.error("Please Enter the day");
                        check = false;
                    }
                    if ($scope.model.data.email && !$scope.ui.checkEmail()) {
                        Notification.error("Please Enter valid email address");
                        check = false;
                    }
                    if (check) {
                        var detail = $scope.model.data;
                        console.log(detail);
                        var detailJSON = JSON.stringify(detail);
                        Factory.save(saveUrl, detailJSON,
                                function (data) {
                                    console.log("success data ");
                                    console.log(data);
                                    Notification.success("Loyalty customer update success " + $scope.model.data.mobileNo);
                                    $scope.ui.clear();
                                    $scope.ui.getCount();
                                    $scope.ui.getTop20();
                                },
                                function (data) {
                                    console.log(data);
                                    Notification.error(data.message);
                                }
                        );

                    }
                };
                $scope.ui.checkEmail = function () {
                    if ($scope.model.data.email) {

                        var email = $scope.model.data.email;
                        var emailSplit = email.split('');
                        var check1 = false;
                        var check2 = false;
                        angular.forEach(emailSplit, function (char) {
                            if (!check1 && char === '.') {
                                check1 = true;
                            }
                            if (!check2 && char === '@') {
                                check2 = true;
                            }
                        });
                        if (check1 && check2) {
                            return true;
                        }
                        return false;
                    } else {
                        return true;
                    }
                };

                $scope.ui.clear = function () {
                    $scope.model.data = {};
                    $scope.ui.getTop20();
                };

                $scope.ui.getTop20 = function () {
                    if (!$scope.model.limitedCount) {
                        $scope.model.limitedCount = 20;
                    }
                    Factory.findOne(top20Url + $scope.model.limitedCount,
                            function (data) {
                                if (data) {
                                    console.log(data);
                                    $scope.model.dataList = data;
                                }

                            },
                            function () {
                            }
                    );
                };
                $scope.ui.updateLoyalty = function (data) {
                    $scope.model.data = data;
                    var yearMonth = data.bYear + "-" + data.bMonth + "-" + data.bDate;
                    $scope.model.data.yearMonth = new Date(yearMonth);
                    $scope.model.data.bDate = "" + data.bDate;


                };
                $scope.ui.getCount = function () {
                    Factory.findOne(countUrl,
                            function (data) {
                                if (data) {
                                    $scope.model.count = data;
                                }

                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getLoyaltyTypeList = function () {
                    Factory.findAll(getLoyaltyTypeList,
                            function (data) {
                                if (data) {
                                    console.log(data);
                                    $scope.model.getLoyaltyTypeList = data;
                                }

                            },
                            function () {
                            }
                    );
                };
                $scope.model.loyaltyTypeLable = function (data) {
                    var lable = "";
                    angular.forEach($scope.model.getLoyaltyTypeList, function (type) {
                        if (type.indexNo === data) {
                            lable = type.indexNo + ' - ' + type.name;
                            return;
                        }
                    });
                    return lable;
                };

                $scope.ui.isActive = function (isAct) {
                    if (isAct) {
                        return "text-pink";
                    }
                    return "text-primary";
                };

                $scope.ui.init = function () {
//                  
                    $scope.model.limitedCount = 20;
                    $scope.ui.getTop20();
                    $scope.ui.getCount();
                    $scope.ui.getLoyaltyTypeList();
                };
                $scope.ui.init();
            });
}());
