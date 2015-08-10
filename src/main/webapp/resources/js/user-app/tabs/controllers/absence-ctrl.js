'use strict';

angular.module('tabs.controllers')
		.controller('absenceController', ['$scope', 'tabsService', function ($scope, tabsService) {
			var type = 'absence';
			$scope.loading = true;
			tabsService.get(type)
					.then(function (data) {
						var tmp = tabsService.parse(data.data, type);
						$scope.absenceList = tmp;
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);