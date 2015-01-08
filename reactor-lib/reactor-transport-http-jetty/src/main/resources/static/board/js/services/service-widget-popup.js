var WidgetPopupService = function () {
    this.popupVisible = false;
    this.newWidgetListener = null;
    this.updateWidgetListener = null;
    this.removeWidgetListener = null;

    this.removeWidgetWindow = function ($widgetData) {
        if (this.removeWidgetListener !== null) {
            this.removeWidgetListener($widgetData);
        }
    };

    this.newWidgetWindow = function () {
        if (this.newWidgetListener !== null) {
            this.newWidgetListener();
        }
    };

    this.updateWidgetWindow = function ($widgetData) {
        if (this.updateWidgetListener !== null) {
            this.updateWidgetListener(angular.copy($widgetData));
        }
    };

    this.setNewWidgetListener = function ($listener) {
        this.newWidgetListener = $listener;
    };

    this.setUpdateWidgetListener = function ($listener) {
        this.updateWidgetListener = $listener;
    };

    this.setRemoveWidgetListener = function ($listener) {
        this.removeWidgetListener = $listener;
    };
};

app.service('$widgetPopupService', WidgetPopupService);