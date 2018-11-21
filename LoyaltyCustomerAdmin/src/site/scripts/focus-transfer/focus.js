/* 
 *  focus.js
 *  
 *  @author Channa Mohan
 *     hjchanna@gmail.com
 *  
 *  Created on Oct 17, 2016, 4:41:40 PM
 *  All rights reserved.
 *  Copyrights supervision technology (pvt.) ltd.
 *  
 */
(function () {
    angular.module("appModule")
            .directive('input', function (focusService) {
                return {
                    restrict: 'E',
                    link: focusService.focus
                };
            });

    angular.module("appModule")
            .service("focusService", function () {
                this.focus = function ($scope, selem, attrs) {
                    selem.bind('keypress', function (e) {
                        var code = e.keyCode || e.which;

                        if (code === 13) {
                            e.preventDefault();
                            var pageElems = document.querySelectorAll('input, select, button'),
                                    elem = e.srcElement,
                                    focusNext = false,
                                    len = pageElems.length;

                            for (var i = 0; i < len; i++) {
                                var pe = pageElems[i];
                                if (focusNext) {
                                    if (pe.style.display !== 'none' && pe.disabled === false) {
                                        pe.focus();
                                        break;
                                    }
                                } else if (pe === elem) {
                                    focusNext = true;
                                }
                            }
                        }
                    });
                };
            });
}());

