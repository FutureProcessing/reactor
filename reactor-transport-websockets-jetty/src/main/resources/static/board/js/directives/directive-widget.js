var WidgetController = function($scope, $widgetPopupService) {
    
    $scope.editWidget = function() {
        $widgetPopupService.updateWidgetWindow($scope.widget);
    };

    $scope.removeWidget = function() {
        $widgetPopupService.removeWidgetWindow($scope.widget);
    };

    $scope.getWidgetDecoration = function() {
        var decoration = $scope.widget.visual.color;
        if ($scope.widget.visual.inverted === true) {
            decoration = decoration + ' inverted';
        }
        if ($scope.widget.visual.dropShadow === true) {
            decoration = decoration + ' shadow';
        }
        return decoration;
    };
};

var WidgetLinker = function($scope, $element, $attrs, $widgets) {
    var initWidgetDimmer = function($element) {
        $($element).dimmer({
            on: 'hover'
        });
    };

    initWidgetDimmer($element);
};

app.directive('widget', function($timeout) {
    return {
        restrict: 'A',
        link: WidgetLinker,
        controller: WidgetController,
        templateUrl: 'js/directives/directive-widget.html',
        scope: {
            widget: '='
        },
        replace: true
    };
});