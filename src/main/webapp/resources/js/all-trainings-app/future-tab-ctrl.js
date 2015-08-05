'use strict';

angular.module('futureTab', [])
		.controller('futureController', ['$scope', 'tableService', 'ngTableParams', function ($scope, tableService, ngTableParams) {
			$scope.url = {
				all: 'rest/training/future',
				search: 'rest/training/search'
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

			$scope.tableParams = new ngTableParams($scope.defaultConfig, {
				counts: [10, 25, 50, 100],
				getData: function ($defer, params) {
					if ($scope.defaultConfig.searching.value) {
						tableService.getSearch($scope.url.search, params.$params)
								.then(function (result) {
									var tmp = tableService.parsing(result.data);
									params.total(tmp.size);
									$defer.resolve(tmp.list);
								});
					} else {
						tableService.get($scope.url.all, params.$params)
								.then(function (result) {
									var tmp = tableService.parsing(result.data);
									params.total(tmp.size);
									$defer.resolve(tmp.list);
								});
					}
				}
			});

		}]);