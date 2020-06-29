(function () {
    angular.module("AppModule")
            .controller("paymentSummaryController", function ($scope, $filter, $uibModal, $uibModalStack, PrintPane, printService, $rootScope, Factory, $location, Notification) {
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

                var getDetail = "/api/sv/reports/payment-summary";
                var getBranches = "/api/sv/master/branch/find-sales-branch";

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

                    if (check) {
                        console.log(detail);
                        $scope.ui.gifShow = true;
                        Factory.save(getDetail, JSON.stringify(detail),
                                function (data) {
                                    if (data) {
                                        $scope.model.data = data;
                                        Notification.success("Success !");
                                        $scope.ui.gifShow = false;

                                    } else {
                                        $scope.model.data = [];
                                        Notification.success("Failed !");
                                        $scope.ui.gifShow = false;
                                    }
                                },
                                function () {
                                    $scope.ui.gifShow = false;
                                }
                        );
                    }
                };
                $scope.model.getNetSale = function () {
                    console.log($scope.model.data['itemGrossSale'],
                            $scope.model.data['itemReturn'],
                            $scope.model.data['otherDiscount'],
                            $scope.model.data['itemDiscount']);

                    return  $scope.model.data['itemGrossSale'] +
                            $scope.model.data['itemReturn'] -
                            $scope.model.data['otherDiscount'] -
                            $scope.model.data['itemDiscount'];
                };
                $scope.model.totalSale = function () {
                    return  $scope.model.data['itemGrossSale'] -
                            $scope.model.data['itemReturn'] -
                            $scope.model.data['otherDiscount'] -
                            $scope.model.data['itemDiscount'] +
                            $scope.model.data['giftVoucherSale'] +
                            $scope.model.data['returnNote'] +
                            $scope.model.data['otherIncome'] +
                            $scope.model.data['creditNoteIssued'];
                };
                $scope.model.totalReceived = function () {
                    console.log($scope.model.data['returnExchange'],
                            $scope.model.data['cardSaleVisa'],
                            $scope.model.data['cardSaleMaster'],
                            $scope.model.data['cardSaleAmex'],
                            $scope.model.data['frimi'],
                            $scope.model.data['creditNoteRedeem'],
                            $scope.model.data['giftVoucherRedeem'],
                            $scope.model.data['cashBalance'],
                            $scope.model.data['cheque']
                            );

                    return  ($scope.model.data['returnExchange'] * -1) +
                            $scope.model.data['cardSaleVisa'] +
                            $scope.model.data['cardSaleMaster'] +
                            $scope.model.data['cardSaleAmex'] +
                            $scope.model.data['frimi'] +
                            $scope.model.data['creditNoteRedeem'] +
                            $scope.model.data['giftVoucherRedeem'] +
                            $scope.model.data['cashBalance'] +
                            $scope.model.data['cheque'];
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                $scope.ui.print = function () {

                    PrintPane.printConfirm("")
                            .confirm(function () {
                                $scope.printService.printPdf('printDiv');
                            })
                            .discard(function () {
                                $scope.printService.printExcel('printDiv', 'Item_Wise_Sales');
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
                $scope.ui.totalQty = function () {
                    return $scope.ui.detailQty;
                };
                $scope.ui.totalQtyMinus = function () {
                    return $scope.ui.detailQtyMinus;

                };

                $scope.ui.init = function () {
                    $scope.ui.branch();

                };
                $scope.ui.init();
            });
}());

