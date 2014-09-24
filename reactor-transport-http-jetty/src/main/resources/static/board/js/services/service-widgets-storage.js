var WidgetsStorageService = function($localStorage) {
	
	this.widgetsStorage = $localStorage.$default({
		widgets: []
	})

	this.getWidgetById = function($widgetId) {
        for (var index in this.widgetsStorage.widgets) {
            var widget = this.widgetsStorage.widgets[index];
            if (widget.id == $widgetId) {
                return widget;
            }
        };
    };

	this.loadWidgets = function() {
		return this.widgetsStorage.widgets.slice();
	};

	var doFindHighestId = function($allWidgets) {
		var highestId = 0;
        for (var index in $allWidgets) {
            var widget = $allWidgets[index];
            if (widget.id > highestId) {
                highestId = widget.id;
            }
        };
        return highestId;
	}

	this.doAddNewWidget = function($widgetData) {
        var highestId = doFindHighestId(this.widgetsStorage.widgets);
        $widgetData.id = highestId + 1;
        this.widgetsStorage.widgets.push($widgetData);
        return $widgetData;
    };

	this.addNewWidget = function($newWidget) {
		return this.doAddNewWidget($newWidget);
	};

    this.removeWidget = function($widgetToRemove) {
        for (var index in this.widgetsStorage.widgets) {
            var widget = this.widgetsStorage.widgets[index];
            if (widget.id == $widgetToRemove.id) {
                this.widgetsStorage.widgets.splice(index, 1);
                return;
            }
        }
    };

    var doUpdateWidget = function($widget, $widgetData) {
        $widget.visual = $widgetData.visual;
        $widget.layout = $widgetData.layout;
        $widget.reactor = $widgetData.reactor;
    };

    this.updateWidget = function($widgetData) {
    	var widget = this.getWidgetById($widgetData.id);
        if (widget) {
            var updatedWidget = angular.extend(widget, $widgetData);
            doUpdateWidget(widget, updatedWidget);
            return widget;
        }
    }

};

app.service('$widgetsStorageService', WidgetsStorageService);