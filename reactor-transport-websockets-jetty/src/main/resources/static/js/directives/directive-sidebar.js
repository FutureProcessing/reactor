app.directive('sidebar', function(ConfigurationService) {

    var sidebar = {};

    function initSidebar($element) {
        sidebar = $($element).sidebar();
    };

    function initSidebarListener() {
        ConfigurationService.setToggleListener(function() {
            sidebar.sidebar('toggle');
        });
    };

    return {
        restrict: 'A',
        link: function($scope, element) {
            initSidebar(element);
            initSidebarListener();
        }
    };
});