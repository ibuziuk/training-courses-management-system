'use strict';

angular.module('tabs.controllers')
		.controller('profileController', ['$scope', 'tabsService', function ($scope, tabsService) {
			var type = 'general';
			$scope.loading = true;
			tabsService.get(type)
					.then(function (data) {
						$scope.profile = tabsService.parse(data.data, type);
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);