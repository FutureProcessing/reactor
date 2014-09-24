app.controller("ReactorEventsController", function($scope, EventStorageService, WebSocketService){

    $scope.eventsPage = 0;
    $scope.eventsPerPage = 5;
    $scope.eventStorage = EventStorageService.eventStorage;

    $scope.newEvent = function(eventBody) {
        $scope.$apply(function () {
            $scope.eventStorage.events.unshift({
                body: eventBody,
                date: new Date()
            });
        });
    };

    $scope.nextPage = function() {
        if (!$scope.hasNextPage()) {
            return;
        }
        $scope.eventsPage = $scope.eventsPage + 1;
    };

    $scope.previousPage = function() {
        if (!$scope.hasPreviousPage()) {
            return;
        }
        $scope.eventsPage = $scope.eventsPage - 1;
    };

    $scope.firstPage = function() {
        if (!$scope.hasPreviousPage()) {
            return;
        }
        $scope.eventsPage = 0;
    };

    $scope.eventPagesCount = function() {
        return Math.ceil($scope.eventStorage.events.length / $scope.eventsPerPage);
    };

    $scope.currentPage = function() {
        return $scope.eventsPage + 1;
    }

    $scope.totalEvents = function() {
        return $scope.eventStorage.events.length;
    }

    $scope.hasPreviousPage = function() {
        return $scope.eventsPage > 0;
    };

    $scope.hasNextPage = function() {
        return $scope.currentPage() <= $scope.eventPagesCount() - 1;
    };

    WebSocketService.addMessageListener(function(message) {
        $scope.newEvent(message);
    }, "BROADCAST");
});