(function () {
    angular.module("AppModule")
            .controller("ipgController", function ($scope, $filter, $uibModal, $uibModalStack, PrintPane, printService, $rootScope, Factory, $location, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.dataSelected = {};
                $scope.model.dataSub = {};
                $scope.model.paymentDetail = [];
                $scope.model.branchList = [];
                $scope.model.supplierList = [];
                $scope.model.categoryList = [];
                $scope.model.styleList = [];
                $scope.model.subCategoryList = [];
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

                $scope.printService = new printService();

                $scope.ui = {};
                $scope.ui.gifShow = false;
                var date = new Date();

                $scope.ui.component = {
                    fromDate: new Date(date.getFullYear(), date.getMonth(), date.getDate() - 1),
                    toDate: new Date(date.getFullYear(), date.getMonth(), date.getDate() - 1)
                };

                $scope.ui.detailCount = 0;
                $scope.ui.detailQty = 0;
                $scope.ui.detailQtyMinus = 0;

                var getDetail = "/api/sv/reports/ipg_report";
//                var getBranches = "/api/sv/master/branch/find-sales-branch";
//                var getSupplier = "/api/sv/master/supplier";
//                var getCategorys = "/api/sv/master/item/category-list";
//                var getStyles = "/api/sv/master/item/style-list";
//                var getSubCategorys = "/api/sv/master/item/sub-category-list";

                $scope.ui.getDetail = function () {
                    var check = true;
                    var detail = $scope.ui.component;
                    detail.fDate = $filter('date')($scope.ui.component.fromDate, "yyyy-MM-dd");
                    detail.tDate = $filter('date')($scope.ui.component.toDate, "yyyy-MM-dd");

                    $scope.ui.component.fDate=detail.fDate;
                    $scope.ui.component.tDate=detail.tDate;
                    if (!detail.fromDate) {
                        check = false;
                        Notification.error("From Date is required !");
                    }
                    if (!detail.toDate) {
                        check = false;
                        Notification.error("To Date is required !");
                    }

                    if (check) {
                        console.log(detail);
                        $scope.ui.gifShow = true;
                        Factory.save(getDetail, JSON.stringify(detail),
                                function (data) {
                                    if (data) {
                                        $scope.model.data = data;
                                        console.log(data);
                                        $scope.ui.detailQty = 0;
                                        $scope.ui.detailValue = 0;
                                        angular.forEach(data, function (data) {
                                            $scope.ui.detailQty += data[4];
                                            $scope.ui.detailValue += data[5];
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
                    $scope.ui.detailValue = 0;
                    angular.forEach($scope.filteredData, function (data) {
                        $scope.ui.detailQty += data[4];
                        $scope.ui.detailValue += data[5];
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
                                $scope.printService.printExcel('printDiv', 'IPG_Report');
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

                $scope.ui.totalQty = function () {
                    return $scope.ui.detailQty;
                };
                $scope.ui.totalQtyMinus = function () {
                    return $scope.ui.detailQtyMinus;

                };

                $scope.ui.init = function () {

                };
                $scope.ui.init();
            });
}());

