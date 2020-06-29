(function () {
    angular.module("AppModule")
            .controller("imageUploadController", function ($scope, $timeout, systemConfig, Factory, Notification) {
                $scope.ui = {};
                $scope.ui.data = {
                    style: null,
                    collection: null

                };
                $scope.model = {};
                $scope.model.collectionList = [];
                $scope.model.styleList = [];
                $scope.model.pendingStyleList = [];
                $scope.model.styleModelList = [];
                $scope.imagemodelX = null;
                $scope.imagemodel = null;
                $scope.pendingStyleEdit = false;

                var imageUploadUrl = "/master/image/upload-image/";
                var collectionListUrl = "/api/sv/master/item/collection-list";
                var updatePendingStyleUrl = "/api/sv/reports/pending-style-update/";
                var getStyleUrl = "/api/sv/master/item/style-list";
                var getPendingStyleUrl = "/api/sv/reports/pending-style";
                var styleListUrl = "/api/sv/master/item/style-list-by-collection/";

                $scope.ui.changeFunction = function (event) {

                    var files = event.target.files;
                    var file = files[0];

                    $scope.imagemodel = file;

                    var reader = new FileReader();
                    reader.onload = $scope.imageIsLoaded;
                    reader.readAsDataURL(file);
                };
                $scope.imageIsLoaded = function (e) {
                    $scope.$apply(function () {
                        $scope.imagemodelX = e.target.result;
                    });
                };
                $scope.ui.collection = function () {
                    Factory.findAll(collectionListUrl,
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
                $scope.ui.getStyle = function () {
                    Factory.findAll(getStyleUrl,
                            function (data) {
                                if (data) {
                                    $scope.model.getStyleList = data;
                                } else {
                                    $scope.model.getStyleList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.getPendingStyle = function () {
                    Factory.findAll(getPendingStyleUrl,
                            function (data) {
                                if (data) {
                                    $scope.model.pendingStyleList = data;
                                } else {
                                    $scope.model.pendingStyleList = [];
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.pendingStyleEdit = function (style) {
                    $scope.ui.data.style = style;
                    $scope.ui.changeStyle(style);
                    $scope.pendingStyleEdit = true;
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
                $scope.model.styleLable = function (model) {
                    var lable = "";
                    angular.forEach($scope.model.getStyleList, function (data) {
                        if (data.imageCode === model) {
                            lable = data.imageCode;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.ui.clear = function () {
                    $scope.ui.data.style = null;
                    $scope.imagemodelX = null;
                };
                $scope.ui.changeCollection = function () {
                    if ($scope.ui.data.collection) {
                        $scope.model.styleList = [];
                        $scope.ui.data.style = null;
                        $scope.imagemodelX = null;
                        $scope.model.styleModelList = [];
                        Factory.findAll(styleListUrl + $scope.ui.data.collection,
                                function (data) {
                                    if (data) {
                                        $scope.model.styleModelList = [];
                                        $scope.model.styleList = [];
                                        $scope.model.styleList = data;
                                        angular.forEach(data, function (style) {
                                            var model = {
                                                name: style.imageCode,
                                                image: systemConfig.apiUrl + "/master/image/download-image/" + style.imageCode + ".jpg"
                                            };
                                            $scope.model.styleModelList.push(model);
                                        });
                                    } else {
                                        $scope.model.styleList = [];
                                    }
                                },
                                function () {
                                }
                        );
                    }
                    $scope.ui.data.style = null;
                    $scope.imagemodelX = null;
                };
                $scope.ui.changeStyle = function (style) {
                    $scope.imagemodelX = systemConfig.apiUrl + "/master/image/download-image/" + style + ".jpg";
                    $scope.pendingStyleEdit = false;
                };
                $scope.ui.edit = function (data) {
                    $scope.pendingStyleEdit = false;
                    $scope.ui.data.style = data.name;
                    $scope.ui.changeStyle(data.name);
                };
                $scope.ui.uploardImages = function () {
                    var url = systemConfig.apiUrl + imageUploadUrl + $scope.ui.data.style;
                    var formData = new FormData();
                    formData.append("file", $scope.imagemodel);

                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", url);
                    xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
                    xhr.send(formData);
                    Notification.success('Image Save Success !');
                    $timeout(function () {
                        $scope.ui.changeCollection();
                    }, 1000);
                    if ($scope.pendingStyleEdit) {
                        $scope.ui.updateIsImage($scope.ui.data.style);
                    }
                    $scope.pendingStyleEdit = false;
                };
                $scope.ui.updateIsImage = function (style) {
                    Factory.findAll(updatePendingStyleUrl + style,
                            function (data) {
                                if (data) {
                                    $scope.ui.getPendingStyle();
                                }
                            },
                            function () {
                            }
                    );
                };
                $scope.ui.init = function () {
                    $scope.ui.collection();
                    $scope.ui.getStyle();
                    $scope.ui.getPendingStyle();
                };
                $scope.ui.init();
            });
}());