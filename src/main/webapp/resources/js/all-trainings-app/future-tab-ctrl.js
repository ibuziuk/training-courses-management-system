'use strict';

angular.module('futureTab', [])
		.controller('futureController', ['$scope', 'tableService', '$anchorScroll', '$location', 'ngTableParams', function ($scope, tableService, $anchorScroll, $location, ngTableParams) {
			$scope.url = {
				all: 'rest/training/future',
				search: 'rest/training/future/search'
			};
			$scope.defaultConfig = {
				page: 1,
				count: 10,
				sorting: {
					date: 'asc'
				},
				searching: {
					type: '',
					value: ''
				}
			};
			$scope.loading = true;

			var temp = $location.absUrl(),
					array = temp.split('/'),
					hash = array[array.length - 1];

			if (hash === 'rec') {
				$('.tabs a[href="#rec"]').tab('show');
			}

			$scope.tableParams = new ngTableParams($scope.defaultConfig, {
				counts: [10, 25, 50, 100],
				getData: function ($defer, params) {
					if (hash === 'gen') {
						tableService.getCustomSearch($scope.url.search)
								.then(function (data) {
									var tmp = tableService.parse(data.data);
									params.total(tmp.size);
									$defer.resolve(tmp.list);
								}, function (data) {
									console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
								})
								.finally(function () {
									$scope.loading = false;
								});
					} else if (hash === 'new' || hash === 'hot') {
						tableService.getNewHotSearch($scope.url.all, hash)
								.then(function (data) {
									var tmp = tableService.parse(data.data);
									params.total(tmp.size);
									$defer.resolve(tmp.list);
								}, function (data) {
									console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
								})
								.finally(function () {
									$scope.loading = false;
								});
					} else if ($scope.defaultConfig.searching.value) {
						tableService.getSearch($scope.url.search, params.$params)
								.then(function (data) {
									var tmp = tableService.parse(data.data);
									params.total(tmp.size);
									$defer.resolve(tmp.list);
								}, function (data) {
									console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
								})
								.finally(function () {
									$scope.loading = false;
								});
					} else {
						tableService.get($scope.url.all, params.$params)
								.then(function (data) {
									var tmp = tableService.parse(data.data);
									params.total(tmp.size);
									$defer.resolve(tmp.list);
								}, function (data) {
									console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
								})
								.finally(function () {
									$scope.loading = false;
								});
					}
				}
			});

		}]);