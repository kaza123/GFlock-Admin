(function () {
    angular.module("AppModule")
            .controller("stockBalanceController", function ($scope, $filter, PrintPane, printService, $uibModal,$uibModalStack, Factory, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.dataSelected = {};
                $scope.model.dataSub = {};
                $scope.model.branchList = [];
                $scope.model.categoryList = [];
                $scope.model.subCategoryList = [];
                $scope.model.styleList = [];
                $scope.printService = new printService();

                $scope.ui = {};
                $scope.ui.gifShow = false;
                var date = new Date();

                $scope.ui.component = {
                    fromDate: new Date(date.getFullYear(), date.getMonth(), 1),
                    toDate: new Date(),
                    branch: null,
                    category: null,
                    subCategory: null,
                    style: null
                };

                $scope.ui.detailCount = 0;
                $scope.ui.detailQty = 0;
                $scope.ui.detailQtyMinus = 0;

                var getDetail = "/api/sv/reports/stock-balance";
                var getBranches = "/api/sv/master/branch/find-sales-branch";
                var getDetailSub = "/api/sv/reports/stock-balance-sub";
                var getCategorys = "/api/sv/master/item/category-list";
                var getSubCategorys = "/api/sv/master/item/sub-category-list";
                var getStyles = "/api/sv/master/item/style-list";

                $scope.ui.getDetail = function () {
                    var detail = $scope.ui.component;
                    detail.fromDate = $filter('date')($scope.ui.component.fromDate, "yyyy-MM-dd");
                    detail.toDate = $filter('date')($scope.ui.component.toDate, "yyyy-MM-dd");
                    if (!detail.branch) {
                        detail.branch = 0;
                    }
                    if (!detail.category) {
                        detail.category = 0;
                    }
                    if (!detail.subCategory) {
                        detail.subCategory = 0;
                    }
                    if (!detail.style) {
                        detail.style = 0;
                    }
                    var model = {
                        'fromDate': detail.fromDate,
                        'toDate': detail.toDate,
                        'branch': detail.branch,
                        'category': detail.category,
                        'subCategory': detail.subCategory,
                        'style': detail.style,
                        'description': null
                    };
                    $scope.ui.gifShow = true;
                    console.log("getDetail,JSON.stringify(model)");
                    console.log(getDetail,JSON.stringify(model));
                    Factory.save(getDetail, JSON.stringify(model),
                            function (data) {
                                if (data) {
                                    console.log(data);
                                    $scope.model.data = data;

                                    $scope.ui.detailCount = 0;
                                    $scope.ui.detailQty = 0;
                                    $scope.ui.detailQtyMinus = 0;
                                    angular.forEach($scope.model.data, function (data) {
                                        $scope.ui.detailCount++;
                                        data[8] = 0;
                                        data[0] = data[0].trim();
                                        data[1] = data[1].trim();
                                        data[2] = data[2].trim();
                                        data[3] = data[3].trim();
                                        data[5] = data[5].trim();
                                        data[7] = data[7].trim();

                                        if (data[4] < 0) {
                                            data[8] = data[4] * -1; 
                                            data[4] = 0;
                                        }
                                        $scope.ui.detailQty += data[4];
                                        $scope.ui.detailQtyMinus += data[8];
                                    });

                                    $scope.ui.gifShow = false;
                                } else {
                                    $scope.model.data = [];
                                    $scope.ui.gifShow = false;
                                }
                            },
                            function () {
                                $scope.ui.gifShow = false;
                            }
                    );
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                $scope.ui.print = function (id) {
                    console.log('id');
                    console.log(id);
                    PrintPane.printConfirm("")
                            .confirm(function () {
                                console.log('print pdf');
                                $scope.printService.printPdf(id);
                            })
                            .discard(function () {
                                $scope.printService.printExcel(id, 'Stock_Balance');
                            });
                };
                $scope.ui.viewModel = function (data) {
                    $scope.ui.getSubData(data);
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'model.html',
                        scope: $scope,
                        size: 'xlg'
                    });
                };
                $scope.ui.count = function (count) {
                    var counter = 0;
                    angular.forEach($scope.model.dataSub, function (detail) {
                        counter += detail[count];
                    });
                    return counter;
                };
                $scope.ui.getSubData = function (data) {
                    var detail = $scope.ui.component;
                    $scope.model.dataSub = [];
                    $scope.model.dataSelected = data;
                    detail.fromDate = $filter('date')($scope.ui.component.fromDate, "yyyy-MM-dd");
                    detail.toDate = $filter('date')($scope.ui.component.toDate, "yyyy-MM-dd");

                    if (!detail.fromDate) {
                        Notification.error("fromDate is required !");
                    }
                    if (!detail.toDate) {
                        Notification.error("toDate is required !");
                    }
                    if (!detail.branch) {
                        detail.branch = 0;
                    }
                    if (!detail.category) {
                        detail.category = 0;
                    }
                    if (!detail.subCategory) {
                        detail.subCategory = 0;
                    }
                    if (!data[0]) {
                        Notification.error("Can't find style No ! ");
                        detail.style = 0;
                    }
                    if (!data[1]) {
                        Notification.error("Can't find description ! ");
                        detail.style = 0;
                    }
                    var model = {
                        'fromDate': detail.fromDate,
                        'toDate': detail.toDate,
                        'branch': data[6],
                        'category': detail.category,
                        'subCategory': detail.subCategory,
                        'style': data[0],
                        'description': data[1]
                    };
                    Factory.save(getDetailSub, JSON.stringify(model),
                            function (data) {
                                $scope.model.dataSub = [];
                                if (data) {
                                    $scope.model.dataSub = data;
                                    var balance = 0.00;
                                    angular.forEach(data, function (detail) {
                                        balance = detail[2] + detail[3] + detail[5];
                                        balance -= detail[4];
                                        balance += detail[6];
                                        balance -= detail[7];
                                        balance += detail[8];
                                        detail[9] = balance;
                                    });
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.branch = function () {
                    Factory.findAll(getBranches,
                            function (data) {
                                if (data) {
                                    $scope.model.branchList = data;

                                } else {
                                    $scope.model.branchList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.model.branchLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.branchList, function (branch) {
                        if (branch.branchCode === model) {
                            lable = branch.branchCode + ' - ' + branch.name;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.ui.totalQty = function () {
                    return $scope.ui.detailQty;
                };
                $scope.ui.totalQtyMinus = function () {
                    return $scope.ui.detailQtyMinus;

                };
                $scope.ui.category = function () {
                    Factory.findAll(getCategorys,
                            function (data) {
                                console.log(data);
                                if (data) {
                                    $scope.model.categoryList = data;

                                } else {
                                    $scope.model.categoryList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                 $scope.model.categoryLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.categoryList, function (name) {
                        if (name.category === model) {
                            lable = name.category;
                            return;
                        }
                    });
                    return lable;
                };
                
                
                $scope.ui.subCategory = function () {
                    Factory.findAll(getSubCategorys,
                            function (data) {
                                if (data) {
                                    $scope.model.subCategoryList = data;

                                } else {
                                    $scope.model.subCategoryList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.model.subCategoryLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.subCategoryList, function (name) {
                        if (name.subCategory === model) {
                            lable = name.subCategory;
                            return;
                        }
                    });
                    return lable;
                };
                
                
                $scope.ui.style = function () {
                    Factory.findAll(getStyles,
                            function (data) {
                                if (data) {
                                    $scope.model.styleList = data;

                                } else {
                                    $scope.model.styleList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.model.styleLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.styleList, function (name) {
                        if (name.imageCode === model) {
                            lable = name.imageCode;
                            return;
                        }
                    });
                    return lable;
                };

                $scope.ui.init = function () {
                    $scope.ui.branch();
                    $scope.ui.category();
                    $scope.ui.subCategory();
                    $scope.ui.style();

                };
                $scope.ui.init();
            });
}());

