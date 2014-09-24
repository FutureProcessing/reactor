var StreamEventController = function ($scope, EventStorageService) {

    $scope.removeEvent = function () {
        EventStorageService.removeEvent($scope.eventIndex);
    }
};

var StreamEventLinker = function ($scope, element) {
}

app.directive('streamEvent', function(WebSocketService) {

    return {
        restrict: 'A',
        scope: {
            event: '=',
            eventIndex: '='
        },
        templateUrl: 'templates/template-stream-event.html',
        controller: StreamEventController,
        link: StreamEventLinker
    };
});