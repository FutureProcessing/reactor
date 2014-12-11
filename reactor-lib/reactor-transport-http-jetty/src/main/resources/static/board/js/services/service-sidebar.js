var SidebarService = function() {
    this.sidebarVisible = false;
    this.sidebarToggleListener = null;

    this.toggleSidebar = function() {
        this.sidebarVisible = !this.sidebarVisible;
        if (this.sidebarToggleListener !== null) {
            this.sidebarToggleListener();
        }
    };

    this.setToggleListener = function(listener) {
        this.sidebarToggleListener = listener;
    };

    this.isVisible = function() {
        return this.sidebarVisible;
    };

}

app.service('$sidebarService', SidebarService);