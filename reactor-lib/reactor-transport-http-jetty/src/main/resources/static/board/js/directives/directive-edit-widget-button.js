var EditWidgetButtonController = function($scope, $widgetPopupService) {

    $scope.showNewWidgetWindow = function() {
        $widgetPopupService.newWidgetWindow();
    };
};

var EditWidgetButtonLinker = function($scope, $element) {
    
    var initNewWidgetButton = function($element) {
    	$($element).click(function() {
    		$scope.showNewWidgetWindow();
    	});
    };
    initNewWidgetButton($element);
};

app.directive('editWidget', function() {
    return {
        restrict: 'A',
        link: EditWidgetButtonLinker,
        controller: EditWidgetButtonController,
        scope: {
            widget: '='
        }
    };
});