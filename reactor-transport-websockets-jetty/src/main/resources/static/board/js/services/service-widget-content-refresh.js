var WidgetContentRefreshService = function() {
    this.dataRefreshListeners = [];

    this.addDataRefreshListener = function($dataRefreshListener) {
        this.dataRefreshListeners.push($dataRefreshListener);
    };
}

app.service('$widgetContentRefreshService', WidgetContentRefreshService);