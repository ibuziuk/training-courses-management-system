'use strict';

angular.module('tabs.controllers')
		.controller('subscribedController', ['$scope', 'tabsService', function ($scope, tabsService) {
			var type = 'subscribed';
			tabsService.get(type)
					.then(function (data) {
						$scope.subscribedTrainings = tabsService.parse(data.data, type);
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);