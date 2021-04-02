(function () {
//module
    angular.module('homeModule', ['ui.bootstrap', 'chart.js']);
    angular.module("homeModule")
            .controller("homeController", function ($scope, Factory, $rootScope, $location, $timeout,$filter, $uibModalStack,$uibModal, Notification) {
                $scope.model = {};
                $scope.model.chart1Lbl = [];
                $scope.model.chart1Val = [];
                $scope.model.chart2Lbl = [];
                $scope.model.chart2Val = [];

                $scope.model.categoryList = [];
                $scope.model.selectedCategory = [];
                $scope.model.categoryTotal = 0.00;
                $scope.model.categoryItemTotal = 0.00;
                $scope.model.branch = 0;

                $scope.model.subCategoryList = [];
                $scope.model.branchList = [];

                $scope.model.todaySales = 0.00;
                $scope.model.uptoDateSales = 0.00;
                $scope.model.lastMonthSales = 0.00;
                $scope.model.basketValue = {};
                $scope.model.basketValueBefore7Day = [];

                $scope.model.orderByText = '0';
                $scope.model.date=new Date();
                $scope.model.filteredDate=$filter('date')($scope.model.date, "yyyy-MM-dd");

                $scope.ui = {};
                $scope.ui.chart1Class = "bar";
                $scope.ui.chart2Class = "bar";

                var today = "/api/sv/home/today-sales/";
                var uptoDate = "/api/sv/home/upto-date-sales/";
                var lastMonthSales = "/api/sv/home/last-month-sales/";
                var getCategory = "/api/sv/home/get-category-details/";
                var getSubCategory = "/api/sv/home/get-sub-category-details/";
                var chart1 = "/api/sv/home/chart1/";
                var chart2 = "/api/sv/home/chart2/";
                var basketValue = "/api/sv/home/basket-values/";
                var basketValueBefore7Day = "/api/sv/home/get-sales-before-7-day/";
                var salesBranchesUrl = "/api/sv/master/branch/is_dashboard";

                $scope.ui.getChart1 = function () {
                    $scope.model.chart1Lbl = [];
                    $scope.model.chart1Val = [];
                    Factory.findOne(chart1 + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (data) {
                                if (data) {
                                    angular.forEach(data, function (value) {
                                        $scope.model.chart1Lbl.push(value[0]);
                                        $scope.model.chart1Val.push(value[1]);
                                    });
                                } else {
                                    $scope.model.chart1Lbl = [];
                                    $scope.model.chart1Val = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getChart2 = function () {
                    $scope.model.chart2Lbl = [];
                    $scope.model.chart2Val = [];
                    Factory.findOne(chart2 + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (data) {
                                if (data) {
                                    angular.forEach(data, function (value) {
                                        $scope.model.chart2Lbl.push(value[0].trim());
                                        $scope.model.chart2Val.push(value[1]);
                                    });
                                } else {
                                    $scope.model.chart2Lbl = [];
                                    $scope.model.chart2Val = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getTodaySalesIncome = function () {
                    $scope.model.todaySales = 0.00;
                    Factory.findOne(today + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (data) {
                                if (data) {
                                    console.log('&&&&&%%%%%%%%%%% todaySales '+data);
                                    $scope.model.todaySales = data;
                                } else {
                                    $scope.model.todaySales = 0.00;
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getUptoIncome = function () {
                    $scope.model.uptoDateSales = 0.00;
                    Factory.findOne(uptoDate + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (data) {
                                if (data) {
                                    $scope.model.uptoDateSales = data;
                                } else {
                                    $scope.model.uptoDateSales = 0.00;
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getLastMonthSales = function () {
                    $scope.model.lastMonthSales = 0.00;
                    Factory.findOne(lastMonthSales + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (data) {
                                if (data) {
                                    $scope.model.lastMonthSales = data;
                                } else {
                                    $scope.model.lastMonthSales = 0.00;
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getCategory = function () {
                    $scope.model.categoryList=[];
                    $scope.model.categoryTotal=0;
                    
                    Factory.findOne(getCategory + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (dataList) {
                                if (dataList) {
                                    $scope.model.categoryList = dataList;
                                    angular.forEach(dataList, function (data) {
                                        data[0] = data[0].trim();
                                        console.log(data[0]);
                                        $scope.model.categoryTotal += data[1];
                                        $scope.model.categoryItemTotal += data[2];
                                    });
                                } else {
                                    $scope.model.categoryList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getSubCategory = function (data) {
                    $scope.model.selectedCategory = data;
                    $scope.ui.getSubCategoryByCategory(data);
                };
                $scope.ui.getSubCategoryByCategory = function (data) {
                    $scope.model.subCategoryList = [];
                    Factory.findOne(getSubCategory + data[3] + "/" + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (dataList) {
                                if (dataList) {
                                    angular.forEach(dataList, function (data) {
                                        data[0] = data[0].trim();
                                        $scope.model.subCategoryList.push(data);
                                    });
                                    $scope.ui.viewModel();
                                } else {
                                    $scope.model.subCategoryList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getBasketValue = function () {
                    console.log("$scope.model.branch "+$scope.model.branch);
                    $scope.model.basketValue = {};
                    Factory.findOne(basketValue + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (data) {
                                if (data) {
                                    $scope.model.basketValue = data;

                                } else {
                                    $scope.model.basketValue = {};
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getBasketValueBefore7Day = function () {
                    $scope.model.basketValueBefore7Day = [];
                    Factory.findOne(basketValueBefore7Day + $scope.model.branch+"/"+$scope.model.filteredDate,
                            function (data) {

                                if (data) {
                                    $scope.model.basketValueBefore7Day = data;
                                } else {
                                    $scope.model.basketValueBefore7Day = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.viewModel = function () {
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'subCategory.html',
                        scope: $scope,
                        size: 'xs'
                    });
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                $scope.ui.changeType = function () {
                    var list = ['line', 'bar', 'doughnut', 'radar', 'pie'];
                    return list[Math.floor((Math.random() * 5))];
                };

                $scope.ui.changeType1 = function () {
                    $scope.ui.chart1Class = $scope.ui.changeType();
                };

                $scope.ui.changeType2 = function () {
                    $scope.ui.chart2Class = $scope.ui.changeType();
                };
                $scope.ui.changeOrderBy = function () {
                    if ($scope.model.orderByText === '0') {
                        $scope.model.orderByText = '-0';
                    } else {
                        $scope.model.orderByText = '0';
                    }
                };
                $scope.ui.changeBranch = function (branch) {
                    $scope.model.branch = branch;

                    $scope.ui.refersh();
                };
                $scope.ui.refersh = function () {
                    $scope.time = 0;

                    $scope.ui.getSalesBranches();
                    $scope.ui.getTodaySalesIncome();
                    $scope.ui.getBasketValueBefore7Day();
                    $scope.ui.getUptoIncome();
                    $scope.ui.getBasketValue();
                    $scope.ui.getLastMonthSales();
                    $scope.ui.getChart1();
                    $scope.ui.getChart2();
                    $scope.ui.getCategory();
                };
                $scope.ui.getSalesBranches = function () {
                    $scope.model.branchList = [];
                    Factory.findOne(salesBranchesUrl,
                            function (data) {
                                if (data) {
                                    $scope.model.branchList = data;
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.changeDate=function () {
//                 console.log($scope.model.date);   
                 $scope.model.filteredDate=$filter('date')($scope.model.date, "yyyy-MM-dd"); 
                 $scope.ui.refersh();
                };

                $scope.init = function () {
                    if ('branch manager' === $rootScope.globals.currentUser.type) {
                        $scope.model.branch = $rootScope.globals.currentUser.branch;
                    } else {
                        $scope.model.branch = 0;
                    }
                    $scope.ui.refersh();

                    //timer callback
//                    var timer = function () {
//                        $scope.time += 1000;
//                        if ($scope.time === (1000 * 60 * 10)) {
//                            console.log("Refersh Data");
//                            $scope.ui.refersh();
//                        }
//                        $timeout(timer, 1000);
//                    };

//                    $timeout(timer, 1000);
                };
                $scope.init();

            });
}());