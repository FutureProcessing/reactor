var NewWidgetWindowController = function($scope, $widgetPopupService, $widgetsService, $timeout) {

    $scope.hasErrors = true;
    $scope.popup = {};
    $scope.widgetData = {};

    $scope.emptyForm = function() {
        $scope.widgetData = {
            visual: {
                title: '',
                fontSize: '',
                colorSettings: {
                    dynamic: false,
                    dynamicModel: {
                        blue: '',
                        orange: '',
                        green: '',
                        red: '',
                        purple: '',
                        teal: ''
                    },
                    staticModel: {
                        color: 'default'
                    },
                    inverted: false
                },
                textAlign: 'widget-align-left',
                dropShadow: false,
                showDimmerLoading: true
            },
            layout: {
                column: 0,
                row: 0,
                gridX: 1,
                gridY: 1

            },
            reactor: {
                input: '',
                interval: 1
            }
        };
    };

    $scope.isNewWidget = function() {
        return typeof $scope.widgetData.id  === 'undefined';
    }

    $scope.addOrUpdateWidget = function() {
        if ($scope.isNewWidget()) {
            $widgetsService.addNewWidget($scope.widgetData);
            return;
        }
        $widgetsService.updateWidget($scope.widgetData);
    };

    var initNewWidgetWindowListener = function($scope, $widgetPopupService) {
        $widgetPopupService.setNewWidgetListener(function() {
            $scope.popup.modal('show');
        });
    };

    var initUpdateWidgetWindowListener = function($scope, $widgetPopupService) {
        $widgetPopupService.setUpdateWidgetListener(function($widgetData) {
            $timeout(function() {
                $scope.widgetData = $widgetData;
                $scope.popup.modal('show');
            });
        });
    };

    var initWindowFormData = function($scope) {
        $scope.emptyForm();
    };

    initNewWidgetWindowListener($scope, $widgetPopupService);
    initUpdateWidgetWindowListener($scope, $widgetPopupService);
    initWindowFormData($scope);
}

var NewWidgetWindowLinker = function($scope, $element, $attrs) {
    var initPopup = function($scope, $element) {
        $scope.popup = $($element).modal({
            onApprove: function() {
                $($element).form('validate form');
                if ($scope.hasErrors) {
                    return false;
                }
                $scope.addOrUpdateWidget();
            },
            onHidden: function() {
                clearFormValidation($element);
                $scope.emptyForm();
                $scope.$apply();
            }
        });
    };

    var clearFormValidation = function($element) {
        $($element).find(".error").removeClass('error');
        $($element).find(".prompt").remove();
    };

    var initFormValidation = function($scope, $element) {
        $($element).form({
            title: {
                identifier: 'title',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter widget title'
                }]
            }, 
            reactorInput: {
                identifier: 'reactorInput',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter reactor input text'
                }]
            },
            gridX: {
                identifier: 'gridX',
                rules: [{
                    type: 'isInteger',
                    prompt: 'Value needs to be a number'
                }, {
                    type: 'minValue[0]',
                    prompt: 'Value needs to be greather than 0'
                }]
            },
            gridY: {
                identifier: 'gridY',
                rules: [{
                    type: 'isInteger',
                    prompt: 'Value needs to be a number'
                }, {
                    type: 'minValue[0]',
                    prompt: 'Value needs to be greather than 0'
                }]
            },
            interval: {
                identifier: 'interval',
                rules: [{
                    type: 'isInteger',
                    prompt: 'Value needs to be a number'
                }, {
                    type: 'minValue[1]',
                    prompt: 'Value needs to be at least 1 (second)'
                }]
            }
        }, {
            inline: true,
            on: 'blur',
            onSuccess: function() {
                $scope.hasErrors = false;
            },
            onFailure: function() {
                $scope.hasErrors = true;
            }
        });
    };

    var initTabs = function($element) {
        $('.pointing.secondary.menu .item', $element).tab({
            history: true,
            historyType: 'hash',
            alwaysRefresh: true
        });
    };

    initPopup($scope, $element);    
    initFormValidation($scope, $element);
    initTabs($element);
}

app.directive('widgetWindow', function() {
    return {
        restrict: 'A',
        link: NewWidgetWindowLinker,
        controller: NewWidgetWindowController,
        templateUrl: 'js/directives/directive-new-widget-window.html',
        scope: { }
    };
});
