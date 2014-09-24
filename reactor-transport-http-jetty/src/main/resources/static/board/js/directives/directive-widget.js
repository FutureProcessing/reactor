var WidgetController = function($scope, $widgetPopupService, $widgetContentRefreshService) {
    
    $scope.widgetContent = "";
    $scope.processing = true;
    $scope.error = false;

    $scope.validateShowDimmer = function() {
        return $scope.widget.visual.showDimmerLoading === true;
    };

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

    var bindContentRefreshEvents = function($scope) {
        $widgetContentRefreshService.addDataRefreshListener($scope.widget, {
            onDataRefreshStarted: function() {
                $scope.$apply(function() {
                    $scope.processing = true;
                });
            },
            onDataRefreshFinished: function($widgetData) {
                console.log('refresing widget: ' + $scope.widget.id)
                $scope.widgetContent = $widgetData;
                $scope.processing = false;
                $scope.error = false;
            },
            onDataRefreshFailed: function() {
                $scope.error = true;
            }
        });
    };
    bindContentRefreshEvents($scope);
};

var WidgetLinker = function($scope, $element, $attrs) {
    var initWidgetDimmer = function($element) {
        $($element).dimmer({
            on: 'hover'
        });
    };

    initWidgetDimmer($element);
};

app.directive('widget', function() {
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