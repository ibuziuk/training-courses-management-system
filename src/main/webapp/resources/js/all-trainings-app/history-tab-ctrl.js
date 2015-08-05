'use strict';

angular.module('historyTab', [])
		.controller('historyController', ['$scope', '$http', 'tableService', 'ngTableParams', function ($scope, $http, tableService, ngTableParams) {
			$scope.trainings = [];
			$scope.url = 'rest/training/past';
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
								console.log(tmp.list);
								$defer.resolve(tmp.list);
							});
				}
			});

		}]);