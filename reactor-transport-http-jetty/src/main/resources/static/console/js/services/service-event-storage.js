app.service('EventStorageService', function($sessionStorage) {
    this.eventStorage = $sessionStorage.$default({
        events: []
    });

    this.removeEvent = function(eventIndex) {
        this.eventStorage.events.splice(eventIndex, 1);
    }

    this.newEvent = function(event) {
        this.eventStorage.events.push(event);
    }
});