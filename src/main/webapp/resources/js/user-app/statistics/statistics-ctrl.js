'use strict';

angular.module('statistics')
		.controller('statisticsController', ['$scope', '$window', 'statisticsService', function ($scope, $window, statisticsService) {

			$scope.onDownloadButtonClick = function () {
				statisticsService.getXls().then(function (result) {
					$window.location.href = result.config.url;
				});
			};
		}]);