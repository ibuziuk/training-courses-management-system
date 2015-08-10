'use strict';

angular.module('statistics')
		.factory('statisticsService', ['$http', '$location', function ($http, $location) {
			var service = {};
			var tmp = $location.absUrl().split('/'),
					id = tmp[tmp.length - 1];

			service.getXls = function () {
				var url = service.createXlsUrl('rest/statistics');
				return $http.get(url);
			};

			service.getHtml = function (config) {

			};

			service.createXlsUrl = function (url) {
				var ret = [],
						uri;
				ret.push('from=' + 0);
				ret.push('to=' + 0);
				ret.push('userId=' + id);
				ret.push('mask=' + 63);

				uri = url + '?' + ret.join('&');
				return encodeURI(uri);
			};

			service.createHtmlUrl = function (config) {

			};

			return service;
		}]);