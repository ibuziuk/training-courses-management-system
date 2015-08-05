'use strict';

angular.module('futureTab', [])
		.controller('futureController', ['$scope', 'tableService', 'ngTableParams', function ($scope, tableService, ngTableParams) {
			$scope.url = 'rest/training/future';
			$scope.defaultConfig = {
				page: 1,
				count: 10,
				sorting: {
					date: 'asc'
				}
			};

			$scope.tableParams = new ngTableParams($scope.defaultConfig, {
				counts: [10, 25, 50, 100],
				getData: function ($defer, params) {
					tableService.get($scope.url, params.$params)
							.then(function (result) {
								var tmp = tableService.parsing(result.data);
								params.total(tmp.size);
								$defer.resolve(tmp.list);
							});
				}
			});

		}]);