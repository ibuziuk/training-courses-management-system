'use strict';

angular.module('editTrainingApp').controller('pageCtrl', ['$scope', '$http', '$q', '$window', 'ngNotify', function ($scope, $http, $q, $window, ngNotify) {
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

	$http.get('rest/training/' + window.location.pathname.substring(pathname.lastIndexOf('/') + 1, pathname.length)).then(
			function(obj){
				console.log(obj.data);
				$scope.qDescr = 1;

				var continuous = obj.data.training.continuous;

				/* Title */
				$scope.trainingName = obj.data.training.title;

				if (continuous){
					$scope.trainingName = $scope.trainingName.substring(0, $scope.trainingName.lastIndexOf('#'));
				}

				/* Repetition */
				if (obj.data.training.continuous){
					$scope.toShowRepet = repetitions[2];
					$scope.days = obj.data.parts.length;
					$scope.qDescr = obj.data.parts.length;
				}
				else {
					$scope.toShowRepet = (obj.data.training.regular) ? repetitions[1] : repetitions[0];
					if (obj.data.training.regular) {
						$scope.days = obj.data.training.lessons.length;
					}
				}

				/* Type */
				$scope.toShowType = (obj.data.training.externalType) ? types[1] : types[0];

				/* Language */
				$scope.toShowLanguage = obj.data.training.language.value + ' ';

				$scope.toShow = function () {
					$scope.descriptions = [];

					if (!continuous){
						$scope.descriptions.push({text: obj.data.training.description});
					}
					else {
						var ind1 = $scope.trainingName.substring($scope.trainingName.lastIndexOf('#') + 1, $scope.trainingName.length);
						$scope.descriptions[ind1] = {text: obj.data.training.description};
						for (var l in obj.data.parts){
							var indl = obj.data.parts[l].title.substring(obj.data.parts[l].title.lastIndexOf('#') + 1, obj.data.parts[l].title.length);
							$scope.descriptions[indl] = {text: obj.data.parts[l].description};
						}
					}


					$scope.datepickers = [];
					$scope.qDates = ($scope.toShowRepet === 'Weekly ') ? $scope.days : $scope.qDescr;

					for (var i = 0; i < $scope.qDates; i++) {
						$scope.datepickers[i] = {
							'dt': new Date(),
							'time': new Date(),
							'toShowWeekDay': 'Select day of week ',
							'room': ''
						};
					}
					$scope.today();
				};

				$scope.datepickers = [];

				/* Date */

				$scope.today = function () {
					for (var i = 0; i < $scope.qDates; i++) {
						$scope.datepickers[i].dt = new Date();
					}
					$scope.dateStartWeekly = new Date();
					$scope.dateEndWeekly = new Date();
				};

				$scope.toggleMin = function () {
					$scope.minDate = $scope.minDate ? null : new Date();
				};
				$scope.toggleMin();

				$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
				$scope.format = $scope.formats[0];

				$scope.getDayClass = function (date, mode) {
					if (mode === 'day') {
						var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

						for (var i = 0; i < $scope.events.length; i++) {
							var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

							if (dayToCheck === currentDay) {
								return $scope.events[i].status;
							}
						}
					}
					return '';
				};

				/* Time */

				$scope.hstep = 1;
				$scope.mstep = 10;

				$scope.ismeridian = false;

				$scope.updateTime = function () {
					var d = new Date();
					d.setHours(0);
					d.setMinutes(0);
					$scope.duration = d;
				};

				$scope.updateTime();

				var days = ['Monday ', 'Tuesday ', 'Wednesday ', 'Thursday ', 'Friday ', 'Saturday ', 'Sunday '];

				$scope.chooseWeekDay = function (rep, index) {
					$scope.datepickers[index].whatChosen = rep;
					$scope.datepickers[index].toShowWeekDay = days[rep];
				};

				$scope.toShow();

				$scope.step1 = function () {
					$scope.$parent.totalItems = 5;

					if ($scope.trainingName === undefined || $scope.trainingName.length === 0) {
						$scope.$parent.totalItems = 5;
						return;
					}

					/* Type of training */
					if ($scope.toShowType === 'Select type ') {
						$scope.$parent.totalItems = 5;
						return;
					}

					/* Repetition frequency */
					if ($scope.toShowRepet === 'Select repetition ') {
						$scope.$parent.totalItems = 5;
						return;
					}

					/* Quantity of days */
					if ($scope.toShowRepet === 'Weekly ' || $scope.toShowRepet === 'Continuous ') {
						if ($scope.days === undefined || $scope.days.length === 0) {
							$scope.$parent.totalItems = 5;
							return;
						}
					}
					$scope.$parent.totalItems = 15;
				};

				$scope.step2 = function () {
					$scope.$parent.totalItems = 15;

					/* Training language */
					if ($scope.toShowLanguage === 'Select language ') {
						$scope.$parent.totalItems = 15;
						return;
					}

					for (var i = 0; i < $scope.qDescr; i++) {
						if ($scope.descriptions[i].text === undefined || $scope.descriptions[i].text.length === 0) {
							$scope.$parent.totalItems = 15;
							return;
						}
					}
					$scope.$parent.totalItems = 25;
				};

				$scope.step3 = function () {
					$scope.$parent.totalItems = 25;
					if ($scope.duration.getHours() === 0 && $scope.duration.getMinutes() === 0) {
						$scope.$parent.totalItems = 25;
						return;
					}

					if ($scope.toShowRepet === 'Weekly ') {
						for (var j = 0; j < $scope.datepickers.length; j++) {
							if ($scope.datepickers[j].toShowWeekDay === 'Select day of week ') {
								$scope.$parent.totalItems = 25;
								return;
							}
						}
					}
					$scope.$parent.totalItems = 35;
				};

			}).catch(function (err) {
				ngNotify.set(err.data);
			}).finally(function () {
			});
}]);