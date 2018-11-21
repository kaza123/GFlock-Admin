(function () {
    angular.module("AppModule")
            .controller("PlantController", function ($scope, $rootScope, $location, Factory, Notification) {
                $scope.model = {};
                $scope.ui = {};
                $scope.ui.validateMobile = null;
                $scope.ui.beforeMobile = "";
                $scope.model.limitedCount = 20;
                $scope.model.branchList = [];
                $scope.model.plantNameList = [];
                $scope.model.cityList = [];

                var checkMobileUrl = "/api/sv/master/loyalty/find-by-mobile/";
                var saveUrl = "/api/sv/master/plant/save";
                var top20Url = "/api/sv/master/plant/get-top/";
                var top20MobileUrl = "/api/sv/master/plant/get-top/";
                var countUrl = "/api/sv/master/plant/get-count";
                var salesBranchesUrl = "/api/sv/master/branch/find-sales-branch";
                var plantUrl = "/api/sv/master/plant/get-plants";
                var cityUrl = "/api/sv/master/loyalty/city-list";

                $scope.ui.save = function () {
                    console.log('save function');
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
                    if ($scope.model.data.email && !$scope.ui.checkEmail()) {
                        Notification.error("Please Enter valid email address");
                        check = false;
                    }
                    if (!$scope.model.data.branch) {
                        Notification.error("Please Enter Branch");
                        check = false;
                    }
                    if (!$scope.model.data.plantName) {
                        Notification.error("Please Enter plantName");
                        check = false;
                    }
                    if (check) {
                        $scope.model.data.user = $rootScope.globals.currentUser.username;
                        var detail = $scope.model.data;
                        console.log(detail);
                        var detailJSON = JSON.stringify(detail);
                        if ($scope.ui.beforeMobile !== $scope.model.data.mobileNo) {
                            $scope.ui.beforeMobile = $scope.model.data.mobileNo;
                            Factory.save(saveUrl, detailJSON,
                                    function (data) {
                                        console.log("success data ");
                                        console.log(data);
                                        Notification.success("plant issue success ");
                                        $scope.ui.clear();
                                        $scope.ui.getCount();
                                        $scope.ui.getTop20();
                                        $scope.ui.getPlantList();
                                        $scope.ui.beforeMobile = "";
                                    },
                                    function (data) {
                                        console.log(data);
                                        Notification.error(data.message);
                                        $scope.ui.beforeMobile = "";
                                    }
                            );
                        } else {
                        }
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
                    $scope.ui.validateMobile = "";
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
                                        $scope.model.data.loyaltyIndex = data.indexNo;
                                        $scope.model.data.address = data.address;
                                        $scope.model.data.city = data.city;
                                        $scope.model.data.email = data.email;
                                    } else {
                                        $scope.model.data.mobileNo = $scope.ui.validateMobile;
                                        $scope.model.data.recidance = "";
                                        $scope.model.data.name = "";
                                        $scope.model.data.loyaltyIndex = 0;
                                        $scope.model.data.address = "";
                                        $scope.model.data.city = "";
                                        $scope.model.data.email = "";
                                        $scope.model.data.branch = "";
                                        $scope.model.data.plantName = "";
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
                    Factory.findOne(top20MobileUrl + $scope.model.limitedCount + "/" + $scope.ui.validateMobile,
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
                $scope.ui.getSalesBranches = function () {
                    Factory.findOne(salesBranchesUrl,
                            function (data) {
                                if (data) {
                                    console.log(data);
                                    $scope.model.branchList = data;
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getPlantList = function () {
                    Factory.findOne(plantUrl,
                            function (data) {
                                if (data) {
                                    console.log(data);
                                    $scope.model.plantNameList = data;
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getCityList = function () {
                    Factory.findOne(cityUrl,
                            function (data) {
                                if (data) {
                                    console.log(data);
                                    $scope.model.cityList = data;
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
                $scope.model.branchLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.branchList, function (branch) {
                        if (branch.indexNo === parseInt(model)) {
                            lable = branch.indexNo + ' - ' + branch.name;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.model.plantLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.plantNameList, function (plantName) {
                        if (plantName === model) {
                            lable = plantName;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.model.cityLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.cityList, function (city) {
                        if (city === model) {
                            lable = city;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.ui.init = function () {
                    console.log($rootScope.globals.currentUser.type);
//                    if ($rootScope.globals.currentUser.type !== "admin" || $rootScope.globals.currentUser.type !== "shop") {
//                        Notification.error("No Authentication for your login type");
//                        $location.path('/home');
//                    } else {
                        $scope.model.limitedCount = 20;
                        console.log($rootScope.globals.currentUser);
                        $scope.ui.getTop20();
                        $scope.ui.getCount();
                        $scope.ui.getSalesBranches();
                        $scope.ui.getPlantList();
                        $scope.ui.getCityList();
//                    }
                };
                $scope.ui.init();
            });
}());

