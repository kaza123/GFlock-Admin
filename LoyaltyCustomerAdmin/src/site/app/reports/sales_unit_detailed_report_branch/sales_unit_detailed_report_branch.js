(function () {
    angular.module("AppModule")
            .controller("salesUnitDetailedReportBranchController", function ($scope, $filter, PrintPane, printService, Factory, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.dataSelected = {};
                $scope.model.dataSub = {};
                $scope.model.categoryList = [];
                $scope.model.subCategoryList = [];
                $scope.model.styleList = [];
                $scope.model.designerList = [];
                $scope.model.collectionList = [];
                $scope.model.branchList = [];

                $scope.printService = new printService();

                $scope.ui = {};
                $scope.ui.gifShow = false;

                $scope.ui.component = {
                    fromDate: null,
                    toDate: null,
                    collection: null,
                    style: null,
                    designer: null,
                    category: null,
                    subCategory: null,
                    styleLabel: null,
                    branch: null
                };

                $scope.ui.detailCount = 0;
                $scope.ui.detailQty = 0;
                $scope.ui.detailQtyMinus = 0;

                var getDetail = "/api/sv/reports/sales_unit_detailed_report_branch";
                var getDetailSub = "/api/sv/reports/stock-balance-sub";

                var getCategorys = "/api/sv/master/item/category-list";
                var getSubCategorys = "/api/sv/master/item/sub-category-list";
                var getStyles = "/api/sv/master/item/style-list";
                var getCollection = "/api/sv/master/item/collection-list";
                var getDesigners = "/api/sv/master/designer/find-all";
                var getBranches = "/api/sv/master/branch/find-sales-branch";

                $scope.ui.getDetail = function () {
                    var detail = $scope.ui.component;
                    detail.fromDate = $filter('date')($scope.ui.component.fromDate, "yyyy-MM-dd");
                    detail.toDate = $filter('date')($scope.ui.component.toDate, "yyyy-MM-dd");

                    if (!detail.fromDate) {
                        detail.fromDate = null;
                    }
                    if (!detail.toDate) {
                        detail.toDate = null;
                    }
                    if (!detail.collection) {
                        detail.collection = null;
                    }
                    if (!detail.category) {
                        detail.category = null;
                    }
                    if (!detail.subCategory) {
                        detail.subCategory = null;
                    }
                    if (!detail.style) {
                        detail.style = null;
                    } else {
                        detail.styleLabel = detail.style.imageCode;
                    }
                    if (!detail.designer) {
                        detail.designer = null;
                    }
                    if (!detail.branch) {
                        detail.branch = null;
                    }
                    var model = {
                        'fromDate': detail.fromDate,
                        'toDate': detail.toDate,
                        'category': detail.category,
                        'subCategory': detail.subCategory,
                        'style': detail.styleLabel,
                        'collection': detail.collection,
                        'designer': detail.designer,
                        'branch': detail.branch
                    };
                    $scope.ui.gifShow = true;
                    Factory.save(getDetail, JSON.stringify(model),
                            function (data) {
                                if (data) {
                                    console.log(data);
                                    $scope.model.data = data;
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
                $scope.goFullscreen = function () {
                    var elem = document.getElementById("fullScreen");
                    elem.webkitRequestFullscreen();
                };

                $scope.ui.print = function () {
                    PrintPane.printConfirm("")
                            .confirm(function () {
                                console.log('print pdf');
                                $scope.printService.printPdf('printDiv');
                            })
                            .discard(function () {
                                $scope.printService.printExcel('printDiv', 'Sales Unit Detail Report Branch');
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
                $scope.ui.designer = function () {
                    Factory.findAll(getDesigners,
                            function (data) {
                                if (data) {
                                    $scope.model.designerList = data;

                                } else {
                                    $scope.model.designerList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.model.designerLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.designerList, function (designer) {
                        if (designer.designerId === model) {
                            lable = designer.designerId + " - " + designer.designerName;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.model.styleLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.styleList, function (style) {
                        if (style.imageCode === model.imageCode && style.category === model.category) {
                            $scope.ui.component.collection = style.category;
                            lable = style.imageCode + " - " + style.category;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.ui.collection = function () {
                    Factory.findAll(getCollection,
                            function (data) {
                                if (data) {
                                    $scope.model.collectionList = data;

                                } else {
                                    $scope.model.collectionList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.model.collectionLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.collectionList, function (data) {
                        if (data.category === model) {
                            lable = data.category;
                            return;
                        }
                    });
                    return lable;
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
                    $scope.ui.collection();
                    $scope.ui.category();
                    $scope.ui.subCategory();
                    $scope.ui.style();
                    $scope.ui.designer();

                };
                $scope.ui.init();
            });
}());

