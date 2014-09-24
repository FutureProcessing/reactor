var SidebarController = function($scope, $sidebarService) {

    var initSidebarToggleListener = function($sidebarService) {
        $sidebarService.setToggleListener(function() {
            $scope.sidebar.sidebar('toggle');
        });
    };

    initSidebarToggleListener($sidebarService);
}

var SidebarLinker = function($scope, $element, $attrs) {
    
    var initSidebar = function($element) {
        $scope.sidebar = $($element).sidebar();
    };

    initSidebar($element);
}

app.directive('sidebar', function() {
    return {
        restrict: 'A',
        scope: {
            sidebarId: '='
        },
        link: SidebarLinker,
        controller: SidebarController
    };
});