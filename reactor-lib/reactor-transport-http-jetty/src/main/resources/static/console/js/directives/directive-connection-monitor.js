var ConnectionMonitorController = function ($scope, WebSocketService) {

    $scope.connectionClosed = function () {
        console.log("DISCONNECTED!");
        $scope.modalDialog.modal("show");
    };

    $scope.connectionOpened = function () {
        console.log("CONNECTED!");
        $scope.modalDialog.modal("hide");
    };

    WebSocketService.addConnectListener($scope.connectionOpened);
    WebSocketService.addDisconnectListener($scope.connectionClosed);
};

var ConnectionMonitorLinker = function ($scope, element) {
    $scope.modalDialog = $(element).modal("setting", "closable", false);
}

app.directive("connectionMonitor", function(){
   return {
       restrict: 'A',
       link: ConnectionMonitorLinker,
       controller: ConnectionMonitorController
   };
});