app.directive('responseRenderer', function () {
	return {
		restrict: 'A',
		templateUrl: 'js/directives/directive-response-renderer.html',
		scope: {
			content: '=responseRenderer'
		}
	};
});