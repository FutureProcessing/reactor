var RemoveWidgetWindowController = function($scope, $widgetPopupService, $widgetsService) {

    $scope.popup = {};
    $scope.widgetData = {};

    $scope.removeWidget = function() {
        $widgetsService.removeWidget($scope.widgetData);
    };

    var initRemoveWidgetWindowListener = function($scope, $widgetPopupService) {
        $widgetPopupService.setRemoveWidgetListener(function($widgetData) {
            $scope.widgetData = $widgetData;
            $scope.popup.modal('show');
        });
    };

    initRemoveWidgetWindowListener($scope, $widgetPopupService);
}

var RemoveWidgetWindowLinker = function($scope, $element, $attrs) {
    var initPopup = function($scope, $element) {
        $scope.popup = $($element).modal({
            onApprove: function() {
                $scope.removeWidget();
            }
        });
    };

    initPopup($scope, $element);    
}

app.directive('removeWidgetWindow', function() {
    return {
        restrict: 'A',
        link: RemoveWidgetWindowLinker,
        controller: RemoveWidgetWindowController,
        templateUrl: 'js/directives/directive-remove-widget-window.html',
        scope: { }
    };
});