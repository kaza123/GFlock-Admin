(function () {
    angular.module("AppModule")
            .factory("Factory", function ($http, systemConfig) {
                var factory = {};
                factory.findAll = function (findAllUrl, callback, errorCallback) {
                    var url = systemConfig.apiUrl + findAllUrl;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };
                factory.save = function (saveUrl, data, callback, errorCallback) {
                    var url = systemConfig.apiUrl + saveUrl;
                    $http.post(url, data)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };
                factory.delete = function (deleteUrl, indexNo, callback, errorCallback) {
                    var url = systemConfig.apiUrl + deleteUrl + indexNo;
                    $http.delete(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };
                factory.findOne = function (findAllUrl, callback, errorCallback) {
                    var url = systemConfig.apiUrl + findAllUrl;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };
                factory.getCountList = function (countUrl, callback, errorCallback) {
                    var url = systemConfig.apiUrl + countUrl;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };
                return factory;
            });
}());