
(function () {
    angular.module("AppModule")
            .controller("hourlyReportController", function ($scope, $filter, $uibModal, $uibModalStack, PrintPane, printService, $rootScope, Factory, $location, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.dataSelected = {};
                $scope.model.dataSub = {};
                $scope.model.paymentDetail = [];
                $scope.model.branchList = [];
                $scope.model.categoryList = [];
                $scope.model.subCategoryList = [];
                $scope.model.styleList = [];

                $scope.printService = new printService();

                $scope.ui = {};
                $scope.ui.gifShow = false;

                $scope.ui.component = {
                    fromDate: new Date(),
                    fDate: new Date(),
                    branch: null,
                    fromTime: null,
                    fTime: null,
                    toTime: null,
                    tTime: null
                };

                $scope.ui.detailCount = 0;
                $scope.ui.stockQty = 0;
                $scope.ui.inQty = 0;
                $scope.ui.outQty = 0;
                $scope.ui.balanceQty = 0;

                var getDetail = "/api/sv/reports/hourly-report";
                var getBranches = "/api/sv/master/branch/find-sales-branch";

                $scope.ui.getDetail = function () {
                    var check = true;
                    var detail = $scope.ui.component;
                    $scope.ui.component.fDate = $filter('date')($scope.ui.component.fromDate, "yyyy-MM-dd");
                    $scope.ui.component.fTime = $filter('date')($scope.ui.component.fromTime, "HH:mm:ss");
                    $scope.ui.component.tTime = $filter('date')($scope.ui.component.toTime, "HH:mm:ss");
                    console.log($scope.ui.component);
                    if (!detail.fromDate) {
                        check = false;
                        Notification.error("From Date is required !");
                    }
                    if (!detail.fromTime) {
                        check = false;
                        Notification.error("From Time is required !");
                    }
                    if (!detail.toTime) {
                        check = false;
                        Notification.error("To Time is required !");
                    }
                    if (check) {

                        $scope.ui.gifShow = true;
                        console.log('data');
                        console.log(detail);
                        Factory.save(getDetail, JSON.stringify(detail),
                                function (data) {
                                    if (data) {
                                        $scope.model.data = data;
                                        console.log('respond');
                                        console.log(data);
                                        $scope.ui.stockQty = 0;
                                        $scope.ui.inQty = 0;
                                        $scope.ui.outQty = 0;
                                        $scope.ui.balanceQty = 0;

                                        angular.forEach(data, function (data) {
                                            console.log(data[2] , data[3] , data[4]);
                                            $scope.ui.stockQty += data[2];
                                            $scope.ui.inQty += data[3];
                                            $scope.ui.outQty += data[4];
                                            $scope.ui.balanceQty += (data[2] + data[3] - data[4]);
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
                    $scope.ui.stockQty = 0;
                    $scope.ui.inQty = 0;
                    $scope.ui.outQty = 0;
                    $scope.ui.balanceQty = 0;

                    angular.forEach(filteredData, function (data) {
                        $scope.ui.stockQty += data[2];
                        $scope.ui.inQty += data[3];
                        $scope.ui.outQty += data[4];
                        $scope.ui.balanceQty += (data[2] + data[3] - data[4]);
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
                                $scope.printService.printExcel('printDiv', 'Category_Wise_Sales');
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

                $scope.ui.init = function () {
                    $scope.ui.branch();
                };
                $scope.ui.init();
            });
}());


