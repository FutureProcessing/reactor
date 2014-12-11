var WidgetContentRefreshService = function($http, $widgetsService) {
    this.dataRefreshListeners = [];

    this.addDataRefreshListener = function($widget, $dataRefreshListener) {
        this.dataRefreshListeners.push({
        	widgetData: $widget,
        	refreshListener: $dataRefreshListener,
            timeoutId: false,
        	refreshData: function(delay) {
                this.timeoutId = setTimeout($.proxy(function() {
                    this.refreshListener.onDataRefreshStarted();
                    $http.post('/rest/', this.widgetData.reactor.input).success($.proxy(function($data) {
                        this.refreshListener.onDataRefreshFinished($data);
                        this.refreshData(this.widgetData.reactor.interval * 1000);
                    }, this)).error($.proxy(function() {
                        this.refreshListener.onDataRefreshFailed();
                        this.refreshData(this.widgetData.reactor.interval * 1000);
                    }, this));
                }, this), delay);
        	}
        });
    };

    this.removeDataRefreshListener = function($widget) {
        for (var refresherIndex in this.dataRefreshListeners) {
            var dataRefreshListener = this.dataRefreshListeners[refresherIndex];
            var widgetData = dataRefreshListener.widgetData;
            if (widgetData.id === $widget.id) {
                clearTimeout(dataRefreshListener.timeoutId);
                return this.dataRefreshListeners.splice(refresherIndex, 1)[0];
            }
        };
    };

    this.updateDataRefreshListener = function ($widget) {
        var removedDataRefreshListener = this.removeDataRefreshListener($widget);
        removedDataRefreshListener.widgetData = $widget;
        removedDataRefreshListener.timeoutId = false;
        this.dataRefreshListeners.push(removedDataRefreshListener);
    }

    this.startDataRefreshTimers = function() {
    	for (var refresherIndex in this.dataRefreshListeners) {
			var dataRefreshListener = this.dataRefreshListeners[refresherIndex];
    		this.startDataRefreshTimer(dataRefreshListener);
    	};
    };

    this.startDataRefreshTimer = function($dataRefreshListener) {
        if ($dataRefreshListener.timeoutId !== false) {
            return;
        }
        $dataRefreshListener.refreshData(0);
    };

    this.initWidgetRemovedListener = function() {
        $widgetsService.onWidgetRemoved($.proxy(this.removeDataRefreshListener, this));
    };

    this.initWidgetUpdatedListener = function () {
        $widgetsService.onWidgetChanged($.proxy(this.updateDataRefreshListener, this));
    };

    this.initWidgetRemovedListener();
    this.initWidgetUpdatedListener();
    setInterval($.proxy(this.startDataRefreshTimers, this), 1000)
}

app.service('$widgetContentRefreshService', WidgetContentRefreshService);