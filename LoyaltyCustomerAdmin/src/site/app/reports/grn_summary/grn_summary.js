(function () {
    angular.module("AppModule")
            .controller("grnSummaryController", function ($scope, $filter, $uibModal, $uibModalStack, PrintPane, printService, $rootScope, Factory, $location, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.dataSelected = {};
                $scope.model.dataSub = {};
                $scope.model.branchList = [];
                $scope.model.supplierList = [];
                $scope.model.uniqueList = [];
                $scope.grnIndex = 0;
                $scope.skipCount = 0;

                $scope.printService = new printService();

                $scope.ui = {};
                $scope.ui.gifShow = false;
                var date = new Date();

                $scope.ui.component = {
                    fromDate: new Date(date.getFullYear(), date.getMonth(), 1),
                    toDate: new Date(),
                    branch: null,
                    transactionNo: null,
                    referenceNo: null,
                    supplier: null,
                    style: null
                };

                $scope.ui.detailCount = 0;
                $scope.ui.totalAmount = 0;
                $scope.ui.totalQty = 0;
                $scope.ui.detailQtyMinus = 0;

                var getDetail = "/api/sv/reports/grn-summary";
                var getBranches = "/api/sv/master/branch/find-sales-branch";
                var getDetailSub = "/api/sv/reports/grn-summary-sub";
                var getSuppliers = "/api/sv/master/supplier";
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
                        detail.branch = "0";
                    }
                    if (!detail.transactionNo) {
                        detail.transactionNo = "0";
                    }
                    if (!detail.referenceNo) {
                        detail.referenceNo = "0";
                    }
                    if (!detail.supplier) {
                        detail.supplier = "0";
                    }
                    if (!detail.style) {
                        detail.style = "0";
                    }

                    var model = {
                        'fromDate': detail.fromDate,
                        'toDate': detail.toDate,
                        'branch': detail.branch,
                        'transactionNo': detail.transactionNo,
                        'referenceNo': detail.referenceNo,
                        'supplier': detail.supplier,
                        'style': detail.style
                    };
                    console.log(model);
                    if (check) {
                        $scope.ui.gifShow = true;
                        Factory.save(getDetail, JSON.stringify(model),
                                function (data) {
                                    if (data) {
                                        $scope.model.data = data;
                                        console.log(data);
                                        $scope.ui.detailCount = 0;
                                        $scope.ui.totalAmount = 0;
                                        $scope.ui.totalQty = 0;
                                        $scope.ui.detailQtyMinus = 0;

                                        $scope.lastGrn = 0;
                                        angular.forEach($scope.model.data, function (data) {
                                            $scope.ui.totalAmount += data[4];
                                            $scope.ui.totalQty += data[10];

                                            if ($scope.lastGrn !== data[8]) {
                                                var dataModel = {
                                                    'index': data[8],
                                                    'color': ''
                                                };
                                                $scope.model.uniqueList.push(dataModel);
                                                $scope.lastGrn = data[8];
                                            }
                                        });
                                        console.log($scope.model.uniqueList);
                                        angular.forEach($scope.model.uniqueList, function (dataModel, key) {
                                            if (key % 2 === 0) {
                                                dataModel.color = 'bg-default-dark';
                                            }
                                        });
                                        $scope.ui.gifShow = false;

                                    } else {
                                        $scope.model.data = [];
                                        console.log('empty');
                                        $scope.ui.gifShow = false;
                                    }
                                },
                                function () {
                                    console.log('empty');
                                    $scope.model.data = [];
                                    $scope.ui.gifShow = false;
                                }
                        );
                    }
                };

                $scope.$watch("search", function (query) {
                    $scope.filteredData = $filter("filter")($scope.model.data, query);
                    $scope.ui.totalAmount = 0;
                    $scope.ui.totalQty = 0;
                    $scope.ui.detailQtyMinus = 0;
                    angular.forEach($scope.filteredData, function (data) {
                        $scope.ui.totalAmount += data[4];
                        $scope.ui.totalQty += data[10];
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
                                $scope.printService.printExcel('printDiv', 'GRN_Summary');
                            });
                };
                $scope.ui.viewModel = function (data) {
                    $scope.ui.getSubData(data[8], data[9]);
                    $scope.model.dataSelected = data;
                    console.log('sub data');
                    console.log(data);
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'model.html',
                        scope: $scope,
                        size: 'xlg'
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
                $scope.ui.getSubData = function (index, style) {
                    console.log(index + " index");
                    console.log(style + " style");
                    Factory.findAll(getDetailSub + "/" + index + "/" + style,
                            function (data) {
                                $scope.model.dataSub = [];
                                if (data) {
                                    $scope.model.dataSub = data;
                                    console.log(data);
                                } else {
                                    console.log("empty");
                                }
                            },
                            function () {
                                console.log("error 2");
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
                                $scope.model.branchList = [];
                            }
                    );
                };
                $scope.ui.styles = function () {
                    Factory.findAll(getStyles,
                            function (data) {
                                if (data) {
                                    $scope.model.styleList = data;

                                } else {
                                    $scope.model.styleList = [];
                                }
                            },
                            function () {
                                $scope.model.branchList = [];
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
                $scope.ui.suppliers = function () {
                    Factory.findAll(getSuppliers,
                            function (data) {
                                if (data) {
                                    $scope.model.supplierList = data;

                                } else {
                                    $scope.model.supplierList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.model.supplierLable = function (index) {
                    var lable = "";
                    angular.forEach($scope.model.supplierList, function (supplier) {
                        if (supplier.indexNo === index) {
                            lable = supplier.indexNo + ' - ' + supplier.name;
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
                $scope.ui.colorClass = function (index) {
                    console.log('index');
                    console.log(index);
                    var retColor = "";
                    angular.forEach($scope.model.uniqueList, function (data) {
                        if (data.index === index) {
                            retColor = data.color;
                            return;
                        }
                    });
                    return retColor;
                };
                $scope.ui.init = function () {
                    $scope.ui.branch();
                    $scope.ui.styles();
                    $scope.ui.suppliers();
                };
                $scope.ui.init();
            });
}());

