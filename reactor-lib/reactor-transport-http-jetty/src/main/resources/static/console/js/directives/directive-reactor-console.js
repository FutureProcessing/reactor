app.directive('reactorConsole', function(WebSocketService) {

    this.terminal = {};
    this.onMessageReceived = function(message) {
        console.log(message);
        terminal.echo(message);
    }

    function initTerminal(element) {
        this.terminal = $(element).terminal(function(command, term) {
            term.echo(WebSocketService.push(command));
        }, {
            prompt: '> ',
            greetings: "Welcome in Reactor Console, type 'help' to get list of available commands.\nType '!interactive' to go into interactive mode.",
            width: '100%'
        });
    };

    function initWebSocketMessageListener() {
        WebSocketService.addMessageListener($.proxy(this.onMessageReceived, this), "RESPONSE");
    };


    return {
        restrict: 'A',
        link: function($scope, element) {
            initWebSocketMessageListener();
            initTerminal(element);
        }
    };
});