(function () {
    angular.module("AppModule")
            .service("PrintPane", function ($uibModal, $q) {
                var defer;

                var ctrl = function (type, message, title) {
                    function Controller(modalInstance, $timeout) {
                        //modal instance
                        this.modalInstance = modalInstance;
                        this.timeout = $timeout;

                        //message
                        this.message = message;

                        //title
                        this.title = title;

                        //class and icon
                        switch (type) {
                            case 'Print':
                                this.optionPaneClass = 'option-pane-success';
                                this.optionPaneIcon = 'glyphicon glyphicon-print';
                                this.title = typeof this.title === 'undefined' ? 'Print As..' : this.title;
                                break;

                            default:
                                this.optionPaneClass = 'option-pane-default';
                                this.optionPaneIcon = 'glyphicon-bell';
                                this.title = typeof this.title === 'undefined' ? 'Note' : this.title;
                                break;
                        }

                    }

                    Controller.prototype = {
                        confirm: function () {
                            var scope = this;
                            this.timeout(function () {
                                scope.modalInstance.close();
                                defer.resolve();
                            }, 250);
                        },
                        discard: function () {
                            var scope = this;
                            this.timeout(function () {
                                scope.modalInstance.close();
                                defer.reject();
                            }, 250);
                        }
                    };

                    return ['$uibModalInstance', '$timeout', Controller];
                };

                this.confirm = function (optionType, message, title) {
                    defer = $q.defer();

                    $uibModal.open({
                        animation: true,
                        backdrop: 'static',
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: './app/util/dialog/print-pane.html',
                        controller: ctrl(optionType, message, title),
                        controllerAs: '$ctrl',
                        size: 'md'
                    });

                    return {
                        confirm: function (callback) {
                            defer.promise.then(callback, null);
                            return this;
                        },
                        discard: function (callback) {
                            console.log("print pane ",callback);
                            defer.promise.then(null, callback);
                            return this;
                        }
                    };
                };

                this.printConfirm = function (message, title) {
                    return this.confirm('Print', message, title);
                };


            });
}());