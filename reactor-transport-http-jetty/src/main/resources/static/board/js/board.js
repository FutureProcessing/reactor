var app = angular.module('ReactorBoard',['ngStorage', 'semantic', 'gridster']);

var semantic = angular.module('semantic', []);
semantic.directive('dropdown', function ($timeout) {
    return {
        restrict: "C",
        scope: {
            model: '=ngModel'
        },
        require: '^ngModel',
        link: function ($scope, $element, $attrs, ngModel) {
            $timeout(function () {
                $($element).dropdown().dropdown('setting', {
                    onChange: function (value) {
                        $scope.$apply(function() {
                            ngModel.$setViewValue(value);
                        });
                    }
                });
                $scope.$watch('model', function($newValue) {
                    $($element).dropdown('set selected', $newValue)
                });
            }, 0);
        }
    };
});