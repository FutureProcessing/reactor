var app = angular.module('ReactorConsole',['ngStorage', 'TransportConfiguration']);

app.filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});

app.filter('parseUrlFilter', function() {
    var urlPattern = /(http|ftp|https):\/\/[\w-]+(\.[\w-]+)+([\w.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?/gi;
    return function(text, target, otherProp) {
        angular.forEach(text.match(urlPattern), function(url) {
            text = text.replace(url, '<a target="' + target + '" href='+ url + '>' + url + '</a>');
        });
        return text + " | " + otherProp;
    };
});

app.directive('ngBindHtmlUnsafe', [function() {
    return function(scope, element, attr) {
        element.addClass('ng-binding').data('$binding', attr.ngBindHtmlUnsafe);
        scope.$watch(attr.ngBindHtmlUnsafe, function ngBindHtmlUnsafeWatchAction(value) {
            element.html(value || '');
        });
    }
}]);

app.filter('parseUrl', function() {
	    var replacePattern = /\b((http:\/\/|https:\/\/|ftp:\/\/|mailto:|news:)|www\.|ftp\.|[^ \,\;\:\!\)\(\""\'\<\>\f\n\r\t\v]+@)([^ \,\;\:\!\)\(\""\'\<\>\f\n\r\t\v]+)\b/gim;
        return function(text, target, otherProp) {
	    	var originText = text;
		    if(text == undefined || text == "") {
				return "";
			} else {
                return text.replace(replacePattern, function($0, $1) {
                    var match = $0;
                    var protocol = $1;

                    if ((/^www\./i).test(match))
                    {
                        return "<a href=" + match + "\">" + match + "</a>";
                    }
                    if ((/^ftp\./i).test(match))
                    {
                        return "<a href=" + match + "\">" + match + "</a>";
                    }

                    if (protocol && protocol.charAt(0) === '@')
                    {
                        return "<a href=\"mailto:" + match + "\">" + match + "</a>";
                    }

                    return "<a href=" + match + "\">" + match + "</a>";
                });
	        }
	    };
});
