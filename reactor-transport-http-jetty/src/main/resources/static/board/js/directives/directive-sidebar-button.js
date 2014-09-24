var SidebarToggleButtonController = function($scope, $sidebarService) {

    $scope.buttonLabel = "show settings";

    $scope.toggleSidebar = function() {
        $sidebarService.toggleSidebar();
        
        $scope.$apply(function($scope) {
            if ($sidebarService.isVisible() === true) {
            $scope.buttonLabel = "hide settings";
                return;
            }
            $scope.buttonLabel = "show settings";
        });
    };
}

var SidebarToggleButtonLinker = function($scope, $element, $attrs) {
    
    var initSidebarToggleButton = function($element) {
    	$($element).click(function() {
    		$scope.toggleSidebar();
    	});
    };
    initSidebarToggleButton($element);
}

app.directive('sidebarToggle', function() {
    return {
        restrict: 'A',
        link: SidebarToggleButtonLinker,
        controller: SidebarToggleButtonController
    };
});