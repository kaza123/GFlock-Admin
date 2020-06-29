(function () {
    angular.module("AppModule")
            .controller("transactionSummaryController", function ($scope, $filter, $uibModal, $uibModalStack, PrintPane, printService, $rootScope, Factory, $location, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.dataSelected = {};
                $scope.model.dataSub = {};
                $scope.model.branchList = [];
                $scope.model.categoryList = [];
                $scope.model.styleList = [];
                $scope.model.transactionList = [
                    {name: "INVOICE", value: "INVOICE"},
                    {name: "GRN", value: "TRANSACTION_GRN"},
                    {name: "RETURN", value: "RETURN"},
                    {name: "BRANCH_TRANSFER", value: "TRANSACTION_BRANCH_STOCK_TRANSFER"},
                    {name: "INTERNAL_TRANSFER", value: "TRNSACTION_INTERNAL_STOCK_TRANSFER"}
                ];
                $scope.printService = new printService();

                $scope.ui = {};
                $scope.ui.gifShow = false;
                var date = new Date();

                $scope.ui.component = {
                    fromDate: new Date(date.getFullYear(), date.getMonth(), 1),
                    toDate: new Date(),
                    branch: null,
                    transaction: null,
                    category: null,
                    style: null,
                    traIndex: null,
                    traNumber: null
                };

                $scope.ui.detailCount = 0;
                $scope.ui.detailQty = 0;
                $scope.ui.detailQtyMinus = 0;

                var getDetail = "/api/sv/reports/transaction-summary";
                var getBranches = "/api/sv/master/branch/find-sales-branch";
                var getDetailSub = "/api/sv/reports/transaction-summary-sub";
                
                var getCategorys = "/api/sv/master/item/category-list";
                var getStyles = "/api/sv/master/item/style-list";

                $scope.ui.getDetail = function () {
                    var check = true;
                    var detail = $scope.ui.component;
                    detail.fromDate = $filter('date')($scope.ui.component.fromDate, "yyyy-MM-dd");
                    detail.toDate = $filter('date')($scope.ui.component.toDate, "yyyy-MM-dd");
                    if (!detail.fromDate) {
                        check = false;
                        Notification.error("From Date is required !");
                    }
                    if (!detail.toDate) {
                        check = false;
                        Notification.error("To Date is required !");
                    }
                    if (!detail.branch) {
                        check = false;
                        Notification.error("Branch is required !");
                    }
                    if (!detail.category) {
                        detail.category = 0;
                    }
                    if (!detail.transaction) {
                        detail.transaction = 0;
                    }
                    if (!detail.style) {
                        detail.style = 0;
                    }
                    var model = {
                        'fromDate': detail.fromDate,
                        'toDate': detail.toDate,
                        'branch': detail.branch,
                        'transaction': detail.transaction,
                        'category': detail.category,
                        'style': detail.style
                    };
                    if (check) {
                        $scope.ui.gifShow = true;
                        Factory.save(getDetail, JSON.stringify(model),
                                function (data) {
                                    if (data) {
                                        $scope.model.data = data;
                                        console.log(data);
                                        $scope.ui.detailCount = 0;
                                        $scope.ui.detailQty = 0;
                                        $scope.ui.detailQtyMinus = 0;
                                        angular.forEach($scope.model.data, function (data) {
                                            data[7] = 0;
                                            if (data[6] < 0) {
                                                data[7] = data[6] * -1;
                                                data[6] = 0;
                                            }
                                            $scope.ui.detailQty += data[6];
                                            $scope.ui.detailQtyMinus += data[7];
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
                    }
                };

                $scope.$watch("search", function (query) {
                    $scope.filteredData = $filter("filter")($scope.model.data, query);
                    $scope.ui.detailQty = 0;
                    $scope.ui.detailQtyMinus = 0;
                    angular.forEach($scope.filteredData, function (data) {
                        $scope.ui.detailQty += data[6];
                        $scope.ui.detailQtyMinus += data[7];
                    });
                });
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                $scope.ui.print = function () {

                    PrintPane.printConfirm("")
                            .confirm(function () {
                                $scope.printService.printPdf('printDiv');
                            })
                            .discard(function () {
                                $scope.printService.printExcel('printDiv', 'Transaction_Summary');
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
                        size: 'lg'
                    });
                };
                $scope.ui.getTransactionName = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.transactionList, function (transaction) {
                        if (transaction.value === model) {
                            lable = transaction.name;
                            return;
                        }
                    });
                    return lable;
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
                    var check = true;
                    $scope.model.dataSub = [];
                    $scope.model.dataSelected = data;
                    detail.fromDate = $filter('date')($scope.ui.component.fromDate, "yyyy-MM-dd");
                    detail.toDate = $filter('date')($scope.ui.component.toDate, "yyyy-MM-dd");

                    if (!detail.fromDate) {
                        Notification.error("fromDate is required !");
                        check = false;
                    }
                    if (!detail.toDate) {
                        Notification.error("toDate is required !");
                        check = false;
                    }
                    if (!detail.branch) {
                        Notification.error("branch is required !");
                        check = false;
                    }
                    if (!detail.category) {
                        detail.category = 0;
                    }
                    if (!detail.transaction) {
                        detail.transaction = 0;
                    }
                    var model = {
                        'fromDate': detail.fromDate,
                        'toDate': detail.toDate,
                        'branch': data[4],
                        'transaction': data[0],
                        'category': detail.category,
                        'style': detail.style,
                        'traIndex': data[1],
                        'traNumber': data[3]
                    };
                    if (check) {
                        console.log(model);
                        Factory.save(getDetailSub, JSON.stringify(model),
                                function (data) {
                                    $scope.model.dataSub = [];
                                    if (data) {
                                        $scope.model.dataSub = data;
                                        console.log(data);
                                    }
                                },
                                function () {
                                }
                        );
                    }
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
                $scope.model.transactionLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.transactionList, function (transaction) {
                        if (transaction.value === model) {
                            lable = transaction.name;
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

                $scope.ui.init = function () {
                    $scope.ui.branch();
                    $scope.ui.category();
                    $scope.ui.style();

                };
                $scope.ui.init();
            });
}());

