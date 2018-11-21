(function () {
    angular.module("appModule")
            .directive('btn', function (rippleService) {
                return {
                    restrict: 'C',
                    link: rippleService.ripple
                };
            })
            .directive('fab', function (rippleService) {
                return {
                    restrict: 'C',
                    link: rippleService.ripple,
                    scope: {
                        show: "=fabShow"
                    }
                };
            });

    angular.module("appModule").service("rippleService", function ($timeout) {
        this.ripple = function (scope, element, attrs) {
            if (typeof scope.show === 'undefined') {
                element.bind('click', function (event) {
                    event.preventDefault();

                    var $div = angular.element('<div/>'),
                            btnOffset = this.getBoundingClientRect(),
                            xPos = event.pageX - btnOffset.left,
                            yPos = event.pageY - btnOffset.top;

                    $div.addClass('ripple-effect');
                    $div.css("height", this.offsetWidth + "px");
                    $div.css("width", this.offsetWidth + "px");
                    $div.css({
                        top: yPos - (this.offsetWidth / 2) + "px",
                        left: xPos - (this.offsetWidth / 2) + "px"
                    });
                    angular.element(this).append($div);

                    $timeout(function () {
                        $div.remove();
                    }, 1000);
                });
            }

            scope.$watch("show", function (newVal, oldVal) {
                if (typeof newVal !== 'undefined') {
                    if (element.hasClass('show') || element.hasClass('hide')) {
                        toggleShow(newVal);
                    } else {
                        if (newVal) {
                            element.addClass('show');
                        } else {
                            element.addClass('hide');
                        }
                    }
                }
            });

            var toggleShow = function (show) {
                var duration = 250;
                element.removeClass('show');
                element.removeClass('hide');

                if (show) {
                    element.addClass('show-start');
                    $timeout(function () {
                        element.addClass('show-active');

                        $timeout(function () {
                            element.addClass('show');
                            element.removeClass('show-active');
                            element.removeClass('show-start');
                        }, duration * 0.9);

                    }, duration);
                } else {
                    element.addClass('hide-start');
                    element.addClass('hide-active');
                    $timeout(function () {
                        element.addClass('hide');
                        element.removeClass('hide-active');
                        element.removeClass('hide-start');
                    }, duration * 0.9);//animation duration
                }
            };
        };
    });

}());
