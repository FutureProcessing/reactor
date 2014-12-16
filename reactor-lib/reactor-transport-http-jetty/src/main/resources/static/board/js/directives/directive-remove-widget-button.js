var RemoveWidgetButtonController = function($scope, $widgetPopupService) {

    $scope.removeWidget = function() {
        $widgetPopupService.removeWidgetWindow($scope.removeWidget);
    };
}

var RemoveWidgetButtonLinker = function($scope, $element, $attrs) {
    
    var initRemoveWidgetButton = function($element) {
    	$($element).click(function() {
    		$scope.removeWidget();
    	});
    };
    initRemoveWidgetButton($element);
}

app.directive('removeWidget', function() {
    return {
        restrict: 'A',
        link: RemoveWidgetButtonLinker,
        controller: RemoveWidgetButtonController,
        scope: {
            removeWidget: '='
        }
    };
});