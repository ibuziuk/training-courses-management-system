'use strict';

angular.module('languageApp', [])
		.controller('languageCtrl', ['$scope', '$window', '$http', function ($scope, $window, $http) {
			$scope.languageSwitcher = function (language) {
				$http.get($window.location.href, {
					params: {language: language}
				}).then(function () {
					$window.location.reload();
				}).catch().finally(function () {
					$window.location.reload();
				})
			}
		}]);