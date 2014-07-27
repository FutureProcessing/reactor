var NewWidgetButtonController = function($scope, $widgetPopupService) {

    $scope.showNewWidgetWindow = function() {
        $widgetPopupService.newWidgetWindow();
    };
}

var NewWidgetButtonLinker = function($scope, $element, $attrs) {
    
    var initNewWidgetButton = function($element) {
    	$($element).click(function() {
    		$scope.showNewWidgetWindow();
    	});
    };
    initNewWidgetButton($element);
}

app.directive('newWidget', function() {
    return {
        restrict: 'A',
        link: NewWidgetButtonLinker,
        controller: NewWidgetButtonController
    };
});