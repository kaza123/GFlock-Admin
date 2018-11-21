(function () {
    angular.module("AppModule")
            .controller("SmsDiscountController", function ($scope, $rootScope, Factory, $location, Notification) {
                $scope.model = {};
                $scope.ui = {};
                $scope.ui.validateMobile = null;
                $scope.ui.beforeMobile = "";
                $scope.model.limitedCount = 20;

                var checkMobileUrl = "/api/sv/master/loyalty/find-by-mobile/";
                var saveUrl = "/api/sv/transaction/sms-discount/save";
                var top20Url = "/api/sv/transaction/sms-discount/get-sms-discount-detail/";
                var top20MobileUrl = "/api/sv/transaction/sms-discount/get-sms-discount-detail/";
                var countUrl = "/api/sv/transaction/sms-discount/get-count";

                $scope.ui.save = function () {
                    var check = true;
                    if (!$scope.model.data.recidance) {
                        Notification.error("Select Mr Miss Mrs ");
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
                    if (!$scope.model.data.discount) {
                        Notification.error("Please Enter discount percentage");
                        check = false;
                    }
                    if (!$scope.model.data.password) {
                        Notification.error("Please Enter transaction password");
                        check = false;
                    }
                    if (check) {
                        $scope.model.data.userName = $rootScope.globals.currentUser.username;
                        var detail = $scope.model.data;
                        console.log(detail);
                        var detailJSON = JSON.stringify(detail);
                        if ($scope.ui.beforeMobile !== $scope.model.data.mobileNo) {
                            $scope.ui.beforeMobile = $scope.model.data.mobileNo;
                            Factory.save(saveUrl, detailJSON,
                                    function (data) {
                                        console.log("success data ");
                                        console.log(data);
                                        Notification.success("you have sent one time discount code to " + $scope.model.data.name);
                                        $scope.ui.clear();
                                        $scope.ui.getCount();
                                        $scope.ui.getTop20();
                                    },
                                    function (data) {
                                        console.log(data);
                                        Notification.error(data.message);
                                        $scope.ui.beforeMobile = "";
                                    }
                            );
                        } else {
                            Notification.error("diplicated entry !");
                        }
                    }
                };

                $scope.ui.clear = function () {
                    $scope.model.data = {};
                    $scope.ui.validateMobile = null;
                };

                $scope.ui.checkMobile = function () {
                    var check = true;
                    if ($scope.model.data.mobileNo.length !== 10) {
                        Notification.error("Invalied mobile no !");
                        check = false;
                    }
                    if ($scope.model.data.mobileNo.substring(0, 1) !== "0") {
                        Notification.error("Invalied mobile no. First letter must be zero");
                        check = false;
                    }
                    if (check) {
                        $scope.ui.validateMobile = "94" + $scope.model.data.mobileNo.substring(1, 10);
                        $scope.ui.getTop20ByMobile();
                        Factory.findOne(checkMobileUrl + $scope.ui.validateMobile,
                                function (data) {
                                    if (data) {
                                        console.log(data);
                                        $scope.model.data.mobileNo = data.mobileNo;
                                        $scope.model.data.recidance = data.recidance;
                                        $scope.model.data.name = data.name;
                                        $scope.model.data.address = data.address;
                                        $scope.model.data.city = data.city;
                                        $scope.model.data.email = data.email;
                                        $scope.model.data.loyaltyIndex = data.indexNo;
                                    } else {
                                        $scope.model.data.mobileNo = $scope.ui.validateMobile;
                                        $scope.model.data.recidance = "";
                                        $scope.model.data.name = "";
                                        $scope.model.data.address = "";
                                        $scope.model.data.city = "";
                                        $scope.model.data.email = "";
                                        $scope.model.data.loyaltyIndex = 0;
                                    }

                                },
                                function () {
                                }
                        );
                    }
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
                $scope.ui.getTop20ByMobile = function () {
                    Factory.findOne(top20MobileUrl + $scope.ui.validateMobile + "/" + $scope.model.limitedCount,
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
                $scope.ui.isActive = function (isAct) {
                    if (isAct) {
                        return "text-primary";
                    }
                    return "text-pink";
                };

                $scope.ui.init = function () {
//                    if ($rootScope.globals.currentUser.type !== "admin" || $rootScope.globals.currentUser.type !== "director") {
//                        Notification.error("No Authentication for your login type");
//                        if ($rootScope.globals.currentUser.type === 'shop') {
//                            $location.path('/plant');
//                        }
//                        if ($rootScope.globals.currentUser.type === 'manager') {
//                            $location.path('/home');
//
//                        }
//                    } else {
                        $scope.model.limitedCount = 20;
                        console.log($rootScope.globals.currentUser);
                        $scope.ui.getTop20();
                        $scope.ui.getCount();
//                    }
                };
                $scope.ui.init();
            });
}());
