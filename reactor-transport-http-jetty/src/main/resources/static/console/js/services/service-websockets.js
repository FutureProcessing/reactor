app.service('WebSocketService', function() {
    this.ws = {};
    this.messageListeners = [];
    this.connectListeners = [];
    this.disconnectListeners = [];

    this.push = function(message) {
        this.ws.send(message);
    };

    this.onMessage = function(message) {
        var messageJson = angular.fromJson(message.data);

        for (var index in this.messageListeners) {
            var listener = this.messageListeners[index];
            if (listener.type == messageJson.type) {
                listener.listenerFunction(messageJson.message);
            }
        }
    };

    this.onConnect = function() {
        for (var index in this.connectListeners) {
            var listener = this.connectListeners[index];
            listener();
        }
    };

    this.onDisconnect = function() {
        for (var index in this.disconnectListeners) {
            var listener = this.disconnectListeners[index];
            listener();
        }
    };

    this.addMessageListener = function(messageListener, messageType) {
        this.messageListeners.push({
            type: messageType,
            listenerFunction: messageListener
        });
    };

    this.addConnectListener = function(connectListener) {
        this.connectListeners.push(connectListener);
    };

    this.addDisconnectListener = function(disconnectListener) {
        this.disconnectListeners.push(disconnectListener);
    };

    // initializeWebSockets
    this.ws = new ReconnectingWebSocket('ws://' + document.location.host + '/websockets/');
    this.ws.onmessage = $.proxy(this.onMessage, this);
    this.ws.onopen = $.proxy(this.onConnect, this);
    this.ws.onclose = $.proxy(this.onDisconnect, this);
});