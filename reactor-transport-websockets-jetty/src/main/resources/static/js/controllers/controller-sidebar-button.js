app.controller("SidebarButtonController", function($scope, ConfigurationService){

    $scope.toggleSidebar = function() {
        ConfigurationService.toggleSidebar();
    };
});