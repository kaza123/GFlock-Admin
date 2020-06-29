(function () {
    angular.module("AppModule")
            .controller("systemSalesController", function ($scope, PrintPane, printService, $uibModalStack, $uibModal, Factory, Notification, systemConfig) {
                $scope.model = {};
                $scope.ui = {};
                $scope.ui.gifShow = false;
                $scope.ui.image = "";
                var styleIndex = 0;
                var currentStyle = null;
                var lastReturn = false;
                var lastColor = false;

                var date = new Date();
                $scope.model.styleList = [];
                $scope.model.collectionList = [];
                $scope.model.categoryList = [];

                $scope.ui.component = {
                    fromDate: new Date(date.getFullYear(), (date.getMonth() - 2), 1),
                    toDate: new Date(),
                    style: null,
                    collection: null,
                    category: null,
                    styleLabel: null
                };
                $scope.printService = new printService();

                var getDetail = "/api/sv/reports/system_sale";
                var getStyles = "/api/sv/master/item/style-list";
                var getCollection = "/api/sv/master/item/collection-list";
                var getCategory = "/api/sv/master/item/category-list";

                var tableCont = document.querySelector('#table-cont');

                function scrollHandle(e) {
                    var scrollTop = this.scrollTop;
                    this.querySelector('thead').style.transform = 'translateY(' + scrollTop + 'px)';
                };

                tableCont.addEventListener('scroll', scrollHandle);

                $scope.goFullscreen = function () {
                    var elem = document.getElementById("fullScreen");
                    elem.webkitRequestFullscreen();
                };
                $scope.ui.getDetail = function () {
                    var check = true;
                    var detail = $scope.ui.component;
                    detail.styleLabel=null;
                    if (!detail.fromDate) {
                        check = false;
                        Notification.error("From Date is required !");
                    }
                    if (!detail.toDate) {
                        check = false;
                        Notification.error("To Date is required !");
                    }
                    if (!detail.collection) {
                        check = false;
                        Notification.error("Collection No is required !");
                    }
                    if (!detail.style) {
                        detail.style = null;
                    }else{
                        detail.styleLabel=detail.style.imageCode;
                    }

                    if (check) {
                        $scope.model.data = [];
                        console.log(detail);
                        $scope.ui.gifShow = true;
                        Factory.save(getDetail, JSON.stringify(detail),
                                function (data) {
                                    if (data) {
                                        $scope.model.data = data;
                                        console.log(data);
                                        Notification.success("Data added Successfully");
                                        $scope.ui.gifShow = false;
                                    } else {
                                        $scope.ui.gifShow = false;
                                        $scope.model.data = [];
                                        Notification.error("empty data try again !");
                                    }
                                },
                                function () {
                                    $scope.ui.gifShow = false;
                                    Notification.error("some error ! try again");
                                }
                        );
                    }
                };
                $scope.ui.dataSoldTotal = function (data) {
                    return data.a + data.b + data.c + data.d + data.e + data.f + data.g;
                };
                $scope.ui.balanceQty = function (data) {
                    console.log('data.grnQty , $scope.ui.dataSoldTotal(data) , data.previousOutQty');
                    console.log(data.grnQty , $scope.ui.dataSoldTotal(data) , data.previousOutQty);
                    return (data.grnQty - $scope.ui.dataSoldTotal(data)) + data.previousOutQty;
                };
                $scope.ui.styleCount = function (style) {
                    var count = 0;
                    angular.forEach($scope.model.data.detailList, function (data) {
                        if (data.style === style) {
                            count++;
                        }
                        ;
                    });
                    return count;
                };
                $scope.ui.colorCount = function (style) {
                    var colorCount = 0;
                    angular.forEach($scope.model.data.colorList, function (data) {
                        if (data[0] === style) {
                            colorCount++;
                        }
                        ;
                    });
                    var styleCount = $scope.ui.styleCount(style);

                    return styleCount / colorCount;

                };
                $scope.ui.colorShow = function (index, style) {
                    var s = index % $scope.ui.colorCount(style);
                    var ret = s === 0 ? true : false;
                    lastColor = ret;
                    return ret;
                };
                $scope.ui.styleShow = function (style) {
                    var branchCount = $scope.model.data.branchList.length + 1;
                    var colorCount = 0;
                    angular.forEach($scope.model.data.colorList, function (data) {
                        if (data[0] === style) {
                            colorCount++;
                        }
                        ;
                    });
                    if (style !== currentStyle) {
                        styleIndex = 0;
                    } else {
                        styleIndex++;
                    }
                    currentStyle = style;
                    var retVal = styleIndex % (branchCount * colorCount) === 0 ? true : false;
                    lastReturn = retVal;
                    return retVal;


                };
                $scope.ui.imageShow = function (data) {
                    if (lastReturn) {
                        data.image = systemConfig.apiUrl + "/master/image/download-image/" + data.style + ".jpg";
                    }
                    data.stylePrint = lastReturn;
                    return lastReturn;
                };
                $scope.ui.priceShow = function () {
                    return lastColor;
                };
                $scope.ui.getImage = function (style) {
                    if (lastReturn) {
                        return systemConfig.apiUrl + "/master/image/download-image/" + style + ".jpg";
                    }
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
                    console.log(model);
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
                $scope.ui.category = function () {
                    Factory.findAll(getCategory,
                            function (data) {
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
                $scope.model.categoryLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.categoryList, function (data) {
                        if (data.category === model) {
                            lable = data.category;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.ui.print = function () {
                    window.print();
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                $scope.ui.viewModel = function (style) {
                    $scope.ui.image = systemConfig.apiUrl + "/master/image/download-image/" + style + ".jpg";
                    console.log($scope.ui.image);
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'model.html',
                        scope: $scope,
                        size: 'xlg'
                    });
                };
                $scope.ui.init = function () {
                    $scope.ui.style();
                    $scope.ui.collection();
                    $scope.ui.category();
                    console.log("init");
                };
                $scope.ui.init();
            });
}());