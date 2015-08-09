'use strict';

angular.module('approveTrainingApp').controller('pageCtrl', ['$scope', '$http', '$q', '$window', 'ngNotify', function ($scope, $http, $q, $window, ngNotify) {
	ngNotify.config({
		theme: 'pastel',
		position: 'bottom',
		duration: 3000,
		type: 'error',
		sticky: true,
		html: false
	});

	$scope.select2Options = {
		multiple: true
	};

	/* Dropdowns Pages 1-2 */

	var repetitions = ['One-off ', 'Weekly ', 'Continuous '];
	var types = ['Inner training ', 'Outer training '];
	var languages = ['English ', 'Russian '];

	function div(val, by){
		return (val - val % by) / by;
	}

	$scope.chooseRepet = function (rep) {
		$scope.toShowRepet = repetitions[rep];
	};

	$scope.chooseType = function (rep) {
		$scope.toShowType = types[rep];
	};

	$scope.chooseLanguage = function (rep) {
		$scope.toShowLanguage = languages[rep];
	};

	var pathname = window.location.pathname;

	var containsNotNullEl = function (arr){
		for (var k = 0; k < arr.length; k++){
			if (arr[k] != null)
				return true;
		}
		return false;
	}

	$http.get('rest/training/approve/' + window.location.pathname.substring(pathname.lastIndexOf('/') + 1, pathname.length)).then(
			function(obj){
				console.log(obj.data.old);
				console.log(obj.data.edit);

				$scope.qDescr = 1;

				var continuous = obj.data.old.training.continuous;

				/* Title */
				$scope.trainingName = obj.data.old.training.title;

				if (continuous) {
					$scope.trainingName = $scope.trainingName.substring(0, $scope.trainingName.lastIndexOf('#'));
					if (containsNotNullEl(obj.data.edit) && obj.data.edit[0].title) {
						var editTitle = obj.data.edit[0].title;
						editTitle = editTitle.substring(0, editTitle.lastIndexOf('#'));
						$scope.trainingName += ' / <b>' + editTitle + '</b>';
					}
				}
				else{
					if (obj.data.edit != null && obj.data.edit.title){
						$scope.trainingName += ' / <b>' + obj.data.edit.title + '</b>';
					}
				}

				/* Repetition */
				/*if (continuous){
					$scope.toShowRepet = repetitions[2];
					$scope.days = obj.data.parts.length;
					$scope.qDescr = obj.data.parts.length;
				}
				else {
					$scope.toShowRepet = (obj.data.training.regular) ? repetitions[1] : repetitions[0];
					if (obj.data.training.regular) {
						$scope.days = obj.data.training.days.split(" ").length - 1;
					}
				}*/

			}).catch(function (err) {
				console.log(err);
				ngNotify.set(err.statusText);
			}).finally(function () {
			});
}]);