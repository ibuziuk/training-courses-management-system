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
	var days = ['Monday ', 'Tuesday ', 'Wednesday ', 'Thursday ', 'Friday ', 'Saturday ', 'Sunday '];

	function div(val, by) {
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

	$scope.chooseWeekDay = function (rep, index) {
		$scope.datepickers[index].whatChosen = rep;
		$scope.datepickers[index].toShowWeekDay = days[rep];
	};

	var pathname = window.location.pathname;

	var containsNotNullEl = function (arr) {
		for (var k = 0; k < arr.length; k++) {
			if (arr[k])
				return true;
		}
		return false;
	};

	$scope.step3 = function () {
		$scope.$parent.totalItems = 25;

		if (($scope.durationEd.getHours() === 0 && $scope.durationEd.getMinutes() === 0) || ($scope.duration.getHours() === 0 && $scope.duration.getMinutes() === 0)) {
			$scope.$parent.totalItems = 25;
			return;
		}

		$scope.$parent.totalItems = 35;

		/*if (obj.data.training.regular === 'Weekly ') {
		 for (var j = 0; j < $scope.datepickers.length; j++) {
		 if ($scope.datepickers[j].toShowWeekDay === 'Select day of week ') {
		 ngNotify.set('Enter day of week ');
		 return false;
		 }
		 }
		 }*/

		//$scope.step4();
	};

	$scope.step2 = function () {
		$scope.$parent.totalItems = 15;

		for (var i = 0; i < $scope.qDescr; i++) {
			if (($scope.descriptions[i].hasEdDescr && (!$scope.descriptions[i].textEd || $scope.descriptions[i].textEd.length === 0)) || (!$scope.descriptions[i].text || $scope.descriptions[i].text.length === 0)) {
				$scope.$parent.totalItems = 15;
				return;
			}
		}
		$scope.$parent.totalItems = 25;

		$scope.step3();
	};

	$scope.step1 = function () {
		$scope.$parent.totalItems = 5;

		if (!$scope.trainingNameEd || $scope.trainingNameEd.length === 0 || ($scope.trainingNameEd === null && ($scope.trainingName === undefined || $scope.trainingName.length === 0))) {
			$scope.$parent.totalItems = 5;
			return;
		}

		if (!$scope.days || $scope.days <= 0) {
			$scope.$parent.totalItems = 5;
			return;
		}

		$scope.$parent.totalItems = 15;

		$scope.step2();
	};

	$http.get('rest/training/approve/' + pathname.substring(pathname.lastIndexOf('/') + 1, pathname.length)).then(
			function (obj) {
				console.log(obj.data.old);
				console.log(obj.data.edit);

				$scope.qDescr = 1;
				var continuous = obj.data.old.training.continuous;

				/* Title */
				$scope.trainingName = obj.data.old.training.title;
				$scope.toShowType = (obj.data.old.training.externalType) ? types[1] : types[0];
				$scope.toShowLanguage = obj.data.old.training.language.value + ' ';

				if (continuous) {
					$scope.trainingName = $scope.trainingName.substring(0, $scope.trainingName.lastIndexOf('#') - 1);
					$scope.qDescr = obj.data.old.parts.length;
					if (containsNotNullEl(obj.data.edit) && obj.data.edit[0].title) {
						$scope.editTitle = obj.data.edit[0].title;
						$scope.editTitle = $scope.editTitle.substring(0, $scope.editTitle.lastIndexOf('#') - 1);
						$scope.trainingNameEd = $scope.editTitle;
					}
					$scope.toShowRepet = repetitions[2];
					$scope.days = obj.data.old.parts.length;
				}

				else {
					if (obj.data.edit && obj.data.edit.title) {
						$scope.editTitle = true;
						$scope.trainingNameEd = obj.data.edit.title;
					}

					if (obj.data.old.training.regular) {
						$scope.toShowRepet = repetitions[1];
						$scope.days = obj.data.old.training.days.split(" ").length - 1;
					}
					else {
						$scope.toShowRepet = repetitions[0];
					}
				}
				$scope.descriptions = [];
				$scope.datepickers = [];
				$scope.qDates = ($scope.toShowRepet === 'Weekly ') ? $scope.days : $scope.qDescr;

				$scope.duration = new Date(80, 2, 3, div(obj.data.old.training.duration, 60), obj.data.old.training.duration % 60);
				$scope.durHours = ($scope.duration.getHours() > 9) ? $scope.duration.getHours() : '0' + $scope.duration.getHours();
				$scope.durMins = ($scope.duration.getMinutes() > 9) ? $scope.duration.getMinutes() : '0' + $scope.duration.getMinutes();

				if (!continuous) {
					if (obj.data.edit && obj.data.edit.start) {
						$scope.dateStartWeekly = new Date(obj.data.edit.start);
						$scope.hasDateEd = true;
					}
					else {
						$scope.dateStartWeekly = new Date(obj.data.old.training.start);
						$scope.hasDateEd = true;
					}
					if (obj.data.edit && obj.data.edit.end) {
						$scope.dateEndWeekly = new Date(obj.data.edit.end);
					}
					else {
						$scope.dateEndWeekly = new Date(obj.data.old.training.end);
					}
					$scope.descriptions[0] = {text: obj.data.old.training.description};
					if (obj.data.edit && obj.data.edit.description) {
						$scope.descriptions[0].hasEdDescr = true;
						$scope.descriptions[0].textEd = obj.data.edit.description;
					}
					if (obj.data.edit && obj.data.edit.duration) {
						$scope.hasEdDur = true;
						$scope.durationEd = new Date(80, 2, 3, div(obj.data.edit.duration, 60), obj.data.edit.duration % 60);
					}
				}
				else {
					for (var l = 0; l < obj.data.old.parts.length; l++) {
						$scope.descriptions[l] = {text: obj.data.old.parts[l].description};
						if (containsNotNullEl(obj.data.edit) && obj.data.edit[l].description) {
							$scope.descriptions[l].hasEdDescr = true;
							$scope.descriptions[l].textEd = obj.data.edit[l].description;
						}
					}
					if (containsNotNullEl(obj.data.edit) && obj.data.edit[0].duration) {
						$scope.hasEdDur = true;
						$scope.durationEd = new Date(80, 2, 3, div(obj.data.edit[0].duration, 60), obj.data.edit[0].duration % 60);
					}
					if(containsNotNullEl(obj.data.edit)) {
						for (var k = 0; k < obj.data.edit.length; k++) {
							if (obj.data.edit[k] && obj.data.edit[k].date){
								$scope.datepickers[k] = {dt: new Date(obj.data.edit[k].date)};
								$scope.hasDateEd = true;
							}
							else {
								$scope.datepickers[k] = {dt: new Date(obj.data.old.parts[k].date)};
							}
						}
					}
				}

				/* Time */

				$scope.hstep = 1;
				$scope.mstep = 10;

				$scope.ismeridian = false;

				var tomorrow = new Date();
				tomorrow.setDate(tomorrow.getDate() + 1);

				$scope.events = [];

				if (obj.data.old.training.regular){
					if (obj.data.edit && obj.data.edit.start || $scope.hasDateEd) {
						$scope.events[0] = {
							date: new Date(obj.data.old.training.start),
							status: 'full'
						};
					}

					if (obj.data.edit && obj.data.edit.end || $scope.hasDateEd) {
						$scope.events[1] = {
							date: new Date(obj.data.old.training.end),
							status: 'full'
						};
					}
				}

				else if (continuous){
					if($scope.hasDateEd){
						for (var k = 0; k < obj.data.old.parts.length; k++){
							$scope.events[k] = {
								date: new Date(obj.data.old.parts[k].date),
								status: 'full'
							};
						}
					}
				}

				$scope.getDayClass = function (date, mode, num) {
					if (mode === 'day') {
						var dayToCheck = new Date(date).setHours(0, 0, 0, 0);
						if ($scope.events[num]) {
							var currentDay = new Date($scope.events[num].date).setHours(0, 0, 0, 0);

							if (dayToCheck === currentDay) {
								return $scope.events[num].status;
							}
						}
					}
					return '';
				};

				$scope.toggleMin = function () {
					$scope.minDate = $scope.minDate ? null : new Date();
				};
				$scope.toggleMin();

				$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
				$scope.format = $scope.formats[0];

			}).catch(function (err) {
				console.log(err);
				ngNotify.set(err.statusText);
			}).finally(function () {
			});
}]);