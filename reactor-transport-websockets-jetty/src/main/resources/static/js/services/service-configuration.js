app.service('ConfigurationService', function($sessionStorage) {
    this.configurationStorage = $sessionStorage.$default({
        desktopNotificationsEnabled: false
    });
    this.configurationSidebarVisible = false;
    this.sidebarToggleListener = null;

    this.toggleSidebar = function() {
        this.configurationSidebarVisible = !this.configurationSidebarVisible;
        if (this.sidebarToggleListener !== null) {
            this.sidebarToggleListener();
        }
    };

    this.setToggleListener = function(listener) {
        this.sidebarToggleListener = listener;
    };

    this.getConfiguration = function() {
        return this.configurationStorage;
    };
});