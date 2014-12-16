var ReactorBoardController = function($scope, $widgetsService, $widgetsService) {

	$scope.gristerOptions = {
        columns: 12,
        margins: [15, 15],
        pushing: true,
		floating: true,
		colWidth: 190,
		rowHeight: 110,
		defaultSizeX: 1,
        defaultSizeY: 1
    };

    $scope.widgetLayoutMap = {
        sizeX: 'widget.layout.gridX',
        sizeY: 'widget.layout.gridY',
        row: 'widget.layout.row',
        col: 'widget.layout.column'
    };

    $scope.widgets = $widgetsService.getWidgets();

    $scope.addWidget = function($widgetData) {
    	$scope.widgets.push($widgetData);
    };

    $scope.removeWidget = function($widgetToRemove) {
        for (var index in $scope.widgets) {
            var widget = $scope.widgets[index];
            if (widget.id == $widgetToRemove.id) {
                $scope.widgets.splice(index, 1);
                return;
            }
        }
    };

    var initWidgetAddedListener = function($scope, $widgetsService) {
    	$widgetsService.onWidgetAdded(function($newWidget) {
    		$scope.addWidget($newWidget);
            $scope.$apply();
    	});
    };

    var initWidgetRemovedListener = function($scope, $widgetsService) {
        $widgetsService.onWidgetRemoved(function($widgetToRemove) {
            $scope.removeWidget($widgetToRemove);
        });
    };

    function copyWidgetProperties($widget, $widgetDataToCopy) {
    	$widget.layout = $widgetDataToCopy.layout;
    	$widget.visual = $widgetDataToCopy.visual;
    	$widget.reactor = $widgetDataToCopy.reactor;
    };

    function FILTER_WIDGET_ID_MATCHES($widgetId) {
    	return function($widget) {
    		return $widget.id == $widgetId;
    	};
    }

    var initWidgetChangedListener = function($scope, $widgetsService) {
    	$widgetsService.onWidgetChanged(function($updatedWidget) {
    		var widget = $scope.widgets.filter(FILTER_WIDGET_ID_MATCHES($updatedWidget.id))[0];
    		if (widget) {
    			copyWidgetProperties(widget, $updatedWidget);
                $scope.$apply();
    		}
    	});
    };

    initWidgetChangedListener($scope, $widgetsService);
    initWidgetAddedListener($scope, $widgetsService);
    initWidgetRemovedListener($scope, $widgetsService);
};

app.controller("ReactorBoardController", ReactorBoardController);