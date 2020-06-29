(function () {
    angular.module("AppModule")
            .controller("salesUnitDepartmentController", function ($scope, $filter, PrintPane, printService, Factory, Notification) {
                $scope.model = {};
                $scope.model.data = {};
                $scope.model.categoryList = [];
                $scope.model.subCategoryList = [];
                $scope.model.styleList = [];
                $scope.model.designerList = [];
                $scope.model.collectionList = [];
                $scope.tempList = [];

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
                    styleLabel: null
                };

                $scope.ui.detailCount = 0;
                $scope.ui.detailQty = 0;
                $scope.ui.detailQtyMinus = 0;

                var getDetail = "/api/sv/reports/sales_unit_department_report";
                var getCategorys = "/api/sv/master/item/category-list";
                var getSubCategorys = "/api/sv/master/item/sub-category-list";
                var getStyles = "/api/sv/master/item/style-list";
                var getCollection = "/api/sv/master/item/collection-list";
                var getDesigners = "/api/sv/master/designer/find-all";

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
                    var model = {
                        'fromDate': detail.fromDate,
                        'toDate': detail.toDate,
                        'category': detail.category,
                        'subCategory': detail.subCategory,
                        'style': detail.styleLabel,
                        'collection': detail.collection,
                        'designer': detail.designer
                    };
                    $scope.ui.gifShow = true;
                    console.log(model);
                    Factory.save(getDetail, JSON.stringify(model),
                            function (data) {
                                if (data) {
                                    console.log(data);
//                                    $scope.setTotal(data);
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
                $scope.setTotal = function (dataList) {
                    $scope.obj = ['', '', '', 0, 0, 0, 0, 0, 0, 0, 0, '2'];
                    var obj=[];
                    var department = '';

                    var orderQty = 0;
                    angular.forEach(dataList, function (data, index) {
                        $scope.obj[0] = data[0]+" Total";
                        if ((department === data[0] ) || index === 0) {
                            department = data[0];

                            orderQty += data[3];
                        } else {
                            $scope.obj[3] = orderQty;
                            obj=$scope.obj;
                            $scope.tempList.push(obj);

                            orderQty = data[3];
                        }
                        if (dataList.length === index + 1) {
                            $scope.obj[3] = orderQty;
                            orderQty = 0;
                            obj=$scope.obj;
                            $scope.tempList.push(obj);
                        }
                    });
                    $scope.model.data = dataList;
                    
                    angular.forEach($scope.tempList, function (data) {
                        $scope.model.data.push(data);
                    });
                    console.log('$scope.model.data');
                    console.log($scope.model.data);
                };
                $scope.setRowColor=function (orderNo){
                    if (orderNo==='2') {
                        return 'set-row-color';
                    }
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
                                $scope.printService.printExcel('printDiv', 'Sales Units Department KPI Report');
                            });
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

                $scope.ui.init = function () {
                    $scope.ui.collection();
                    $scope.ui.category();
                    $scope.ui.subCategory();
                    $scope.ui.style();
                    $scope.ui.designer();

                };
                $scope.ui.init();
            });
}());




