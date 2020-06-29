
(function () {
    angular.module("AppModule")
            .controller("invoiceWiseController", function ($scope, $filter, $uibModal, $uibModalStack, PrintPane, printService, $rootScope, Factory, $location, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.dataSelected = {};
                $scope.model.dataSub = {};
                $scope.model.paymentDetail = [];
                $scope.model.branchList = [];
                $scope.model.terminalList = [
                    {id: "1", name: "Terminal 01"},
                    {id: "2", name: "Terminal 02"},
                    {id: "3", name: "Terminal 03"},
                    {id: "4", name: "Terminal 04"},
                    {id: "5", name: "Terminal 05"},
                    {id: "6", name: "Terminal 06"},
                    {id: "7", name: "Terminal 07"},
                    {id: "8", name: "Terminal 08"},
                    {id: "9", name: "Terminal 09"},
                    {id: "10", name: "Terminal 10"},
                    {id: "11", name: "Terminal 11"},
                    {id: "12", name: "Terminal 12"},
                    {id: "13", name: "Terminal 13"}
                ];
                $scope.model.typeList = [
                    {name: "Invoice"},
                    {name: "Return"}
                ];

                $scope.printService = new printService();

                $scope.ui = {};
                $scope.ui.gifShow = false;

                $scope.ui.component = {
                    fromDate: new Date(),
                    toDate: new Date(),
                    branch: null,
                    terminal: null
                };

                $scope.ui.detailCount = 0;
                $scope.ui.detailQty = 0;
                $scope.ui.detailQtyMinus = 0;

                var getDetail = "/api/sv/reports/invoice-wise";
                var getBranches = "/api/sv/master/branch/find-sales-branch";
                var getDetailSub = "/api/sv/reports/invoice-wise-sum/";
                var getPaymentDetail = "/api/sv/reports/invoice-wise-payment/";

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
                    if (!detail.terminal) {
                        detail.terminal = "0";
                    }
                    if (!detail.type) {
                        detail.type = "0";
                    }
                    if (!detail.reference) {
                        detail.reference = "0";
                    }
                    var model = {
                        'fromDate': detail.fromDate,
                        'toDate': detail.toDate,
                        'branch': detail.branch,
                        'terminal': detail.terminal,
                        'type': detail.type,
                        'reference': detail.reference
                    };
                    if (check) {
                        $scope.ui.gifShow = true;
                        Factory.save(getDetail, JSON.stringify(model),
                                function (data) {
                                    if (data) {
                                        $scope.model.data = data;
                                        console.log(data);
                                        $scope.ui.detailQty = 0;
                                        $scope.ui.detailQtyMinus = 0;
                                        angular.forEach(data, function (data) {
                                            data[8] = 0;
                                            if (data[5] < 0) {
                                                data[5] = data[5] * -1;
                                            }
                                            if (data[7] < 0) {
                                                data[8] = data[7] * -1;
                                                data[7] = 0;
                                            }
                                            $scope.ui.detailQty += data[7];
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
                    }
                };

                $scope.$watch("search", function (query) {
                    $scope.filteredData = $filter("filter")($scope.model.data, query);
                    $scope.ui.detailQty = 0;
                    $scope.ui.detailQtyMinus = 0;
                    angular.forEach($scope.filteredData, function (data) {
                        $scope.ui.detailQty += data[7];
                        $scope.ui.detailQtyMinus += data[8];
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
                                $scope.printService.printExcel('printDiv', 'Invoice_Wise_Sales');
                            });
                };
                $scope.ui.viewModel = function (data) {
                    $scope.ui.getSubData(data);
                    $scope.ui.getPayment(data);
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
                $scope.ui.getSubData = function (data) {
                    $scope.model.dataSub = [];
                    $scope.model.dataSelected = data;

                    Factory.findAll(getDetailSub + data[2],
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
                };
                $scope.ui.getPayment = function (data) {
                    $scope.model.paymentDetail = [];

                    Factory.findAll(getPaymentDetail + data[2],
                            function (data) {
                                $scope.model.paymentDetail = [];
                                if (data) {
                                    $scope.model.paymentDetail = data;
                                    console.log(data);
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
                $scope.model.terminalLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.terminalList, function (terminal) {
                        if (terminal.id === model) {
                            lable = terminal.id + " - " + terminal.name;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.model.typeLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.typeList, function (type) {
                        if (type.name === model) {
                            lable = type.name;
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

                $scope.ui.init = function () {
                    $scope.ui.branch();
                    console.log("success !");

                };
                $scope.ui.init();
            });
}());

