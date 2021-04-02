(function () {
    angular.module("AppModule")
            .controller("itemWiseGrnController", function ($scope, $filter, $uibModal, $uibModalStack, PrintPane, printService, $rootScope, Factory, $location, Notification) {
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
//                var date = new Date();
                $scope.ui.component = {
                    fromDate: new Date(),
                    toDate: new Date(),
                    branch: null,
                    transactionNo: null,
                    barcode: null,
                    style: null,
                    category: null,
                    subCategory: null,
                    supplier: null
                };

                $scope.ui.detailCount = 0;
                $scope.ui.detailQty = 0;
                $scope.ui.detailQtyMinus = 0;

                var getDetail = "/api/sv/reports/item-wise_grn";
                var getBranches = "/api/sv/master/branch/find-sales-branch";
                var getSupplier = "/api/sv/master/supplier";
                var getCategorys = "/api/sv/master/item/category-list";
                var getStyles = "/api/sv/master/item/style-list";
                var getSubCategorys = "/api/sv/master/item/sub-category-list";

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
                    if (!detail.transactionNo) {
                        detail.transactionNo = null;
                    }
                    if (detail.barcode === "") {
                        detail.barcode = null;
                    }
                    if (detail.style === "") {
                        detail.style = null;
                    }
                    if (detail.category === "") {
                        detail.category = null;
                    }
                    if (detail.subCategory === "") {
                        detail.subCategory = null;
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
                                            $scope.ui.detailQty += parseFloat(data[11]);
                                            $scope.ui.detailValue += parseFloat(data[12]);
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
                        $scope.ui.detailQty += data[9];
                        $scope.ui.detailValue += data[10];
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
                $scope.ui.supplier = function () {
                    Factory.findAll(getSupplier,
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
                $scope.model.supplierLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.supplierList, function (supplier) {
                        if (supplier.indexNo === parseInt(model)) {
                            lable = supplier.indexNo + ' - ' + supplier.name;
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
                    $scope.ui.supplier();
                    $scope.ui.category();
                    $scope.ui.style();
                    $scope.ui.subCategory();

                };
                $scope.ui.init();
            });
}());

