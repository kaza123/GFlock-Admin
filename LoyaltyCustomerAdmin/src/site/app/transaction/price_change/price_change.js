(function () {
    angular.module("AppModule")
            .controller("PriceChangeController", function ($scope, $rootScope, $location, $filter, Factory, Notification) {
                $scope.model = {};
                $scope.ui = {};
                $scope.departmentList = [];
                $scope.categoryList = [];
                $scope.itemList = [];
                $scope.dataList = [];

                $scope.tempData = {
                    "from_date": new Date,
                    "to_date": new Date,
                    "department": null,
                    "category": null,
                    "item": null
                };
                $scope.masterData = {
                    "discount_type": null,
                    "discount": null
                };

                var api = "/api/sv/transaction/price_change";

                $scope.getAllDepartment = function () {
                    Factory.findOne(api + "/get-department",
                            function (data) {
                                console.log(data);
                                $scope.departmentList = data;
                            },
                            function (data) {
                                console.log(data);
                                Notification.error(data.message);
                            }
                    );
                };
                $scope.getAllCategory = function () {
                    Factory.findOne(api + "/get-category",
                            function (data) {
                                console.log(data);
                                $scope.categoryList = data;
                            },
                            function (data) {
                                console.log(data);
                                Notification.error(data.message);
                            }
                    );
                };
                $scope.getItem = function (catId) {
                    Factory.findOne(api + "/get-item/" + catId,
                            function (data) {
                                console.log(data);
                                $scope.itemList = data;
                            },
                            function (data) {
                                console.log(data);
                                Notification.error(data.message);
                            }
                    );
                };
                $scope.viewItem = function () {
                    if ($scope.viewValidation()) {
                        $scope.tempData.from_date = $filter('date')($scope.tempData.from_date, 'yyyy-MM-dd');
                        $scope.tempData.to_date = $filter('date')($scope.tempData.to_date, 'yyyy-MM-dd');
                        var request = JSON.stringify($scope.tempData);
                        console.log("request");
                        console.log(request);
                        Factory.save(api + "/get-item-detail", request,
                                function (data) {
                                    console.log('item detail');
                                    console.log(data);
                                    $scope.dataList = data;
                                },
                                function (data) {
                                    console.log(data);
                                    Notification.error(data.message);
                                }
                        );
                    }
                };
                $scope.viewValidation = function () {
                    var check = true;
                    if (!$scope.tempData.from_date) {
                        check = false;
                        Notification.error("Select From Date");
                    }
                    if (!$scope.tempData.to_date) {
                        check = false;
                        Notification.error("Select To Date");
                    }
                    return check;
                };

                $scope.depLable = function (index) {
                    var lable = "";
                    angular.forEach($scope.departmentList, function (dep) {
                        if (parseInt(dep[0]) === parseInt(index)) {
                            lable = dep[1];
                            return;
                        }
                    });
                    return lable;
                };
                $scope.catLable = function (index) {
                    var lable = "";
                    angular.forEach($scope.categoryList, function (cat) {
                        if (parseInt(cat[0]) === parseInt(index)) {
                            lable = cat[1] + ' - ' + cat[2];
                            return;
                        }
                    });
                    return lable;
                };
                $scope.itemLable = function (index) {
                    var lable = "";
                    angular.forEach($scope.itemList, function (item) {
                        if (parseInt(item[0]) === parseInt(index)) {
                            lable = item[1];
                            return;
                        }
                    });
                    return lable;
                };
                $scope.changeDiscountPer = function (data) {
//                    return parseFloat(Math.round(num * Math.pow(10, n)) /Math.pow(10,n)).toFixed(n);
                    if (!data.discount_rate) {
                        data.discount_rate = 0.00;
                    }
                    var selling_price = parseFloat(data.selling_price);
                    var discP = parseFloat(data.discount_rate);
                    var discAmount = parseFloat(Math.round(selling_price * discP / 100 * Math.pow(10, 2)) / Math.pow(10, 2)).toFixed(2);
                    var newPrice = selling_price - discAmount;

                    data.discount = discAmount;
                    data.new_selling_price = newPrice;
                };
                $scope.changeDiscountAmount = function (data) {
                    if (!data.discount) {
                        data.discount = 0.00;
                    }
                    var selling_price = parseFloat(data.selling_price);
                    var discAmount = parseFloat(data.discount);
                    var discPer = parseFloat(Math.round((discAmount * 100 / selling_price) * Math.pow(10, 2)) / Math.pow(10, 2)).toFixed(2);
                    var newPrice = selling_price - discAmount;

                    data.discount_rate = discPer;
                    data.new_selling_price = newPrice;
                };
                $scope.applyDiscount = function () {
                    if (!$scope.masterData.discount_type) {
                        Notification.error("Select a Discount Type");
                    } else if (parseInt($scope.masterData.discount_type) === 1) {
                        //disc %
                        angular.forEach($scope.dataList, function (data) {
                            if ($scope.masterData.discount) {
                                data.discount_rate = $scope.masterData.discount;
                            } else {
                                data.discount_rate = 0.00;
                            }
                            var selling_price = parseFloat(data.selling_price);
                            var discP = parseFloat(data.discount_rate);
                            var discAmount = parseFloat(Math.round(selling_price * discP / 100 * Math.pow(10, 2)) / Math.pow(10, 2)).toFixed(2);
                            var newPrice = selling_price - discAmount;

                            data.discount = discAmount;
                            data.new_selling_price = newPrice;
                        });

                    } else if (parseInt($scope.masterData.discount_type) === 0) {
                        //disc RS
                        angular.forEach($scope.dataList, function (data) {
                            if ($scope.masterData.discount) {
                                data.discount = $scope.masterData.discount;
                            } else {
                                data.discount = 0.00;
                            }
                            var selling_price = parseFloat(data.selling_price);
                            var discAmount = parseFloat(data.discount);
                            var discPer = parseFloat(Math.round((discAmount * 100 / selling_price) * Math.pow(10, 2)) / Math.pow(10, 2)).toFixed(2);
                            var newPrice = selling_price - discAmount;
                            data.discount_rate = discPer;
                            data.new_selling_price = newPrice;
                        });
                    } else {
                        Notification.error("Discount Type doesnt match");
                    }
                };
                $scope.save = function () {
                    var data = {
                        'tempData': $scope.tempData,
                        'masterData': $scope.masterData,
                        'dataList': $scope.dataList
                    };
                    var request = JSON.stringify(data);
                    console.log("request");
                    console.log(request);
                    Factory.save(api + "/save", request,
                            function (respond) {
                                console.log('save respond');
                                console.log(respond);
                                Notification.success("Price Change Successfully saved!");
                                $scope.clear();
                            },
                            function (data) {
                                console.log(data);
                                Notification.error(data.message);
                            }
                    );

                };
                $scope.clear = function () {
                    $scope.tempData = {
                        "from_date": new Date,
                        "to_date": new Date,
                        "department": null,
                        "category": null,
                        "item": null
                    };
                    $scope.masterData = {
                        "discount_type": null,
                        "discount": null
                    };
                    $scope.dataList=[];
                };
                $scope.ui.init = function () {
                    console.log($rootScope.globals.currentUser.type);
                    $scope.getAllDepartment();
                    $scope.getAllCategory();
//                    $scope.getAllItem();
                };
                $scope.ui.init();
            });
}());
