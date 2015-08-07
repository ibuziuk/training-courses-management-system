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
	var languages = ['English ', 'Russian '];

	function div(val, by){
		return (val - val % by) / by;
	}

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
						$scope.days = obj.data.training.days.split(" ").length - 1;
					}
				}

				/* Language */
				$scope.toShowLanguage = obj.data.training.language.value + ' ';

				var days = ['Monday ', 'Tuesday ', 'Wednesday ', 'Thursday ', 'Friday ', 'Saturday ', 'Sunday '];

				$scope.chooseWeekDay = function (rep, index) {
					$scope.datepickers[index].whatChosen = rep;
					$scope.datepickers[index].toShowWeekDay = days[rep];
				};

				$scope.toShow = function () {
					$scope.descriptions = [];
					$scope.datepickers = [];
					$scope.qDates = ($scope.toShowRepet === 'Weekly ') ? $scope.days : $scope.qDescr;

					if (!continuous){
						if (obj.data.training.regular){
							$scope.descriptions.push({text: obj.data.training.description});
							$scope.dateStartWeekly = new Date(obj.data.training.start);
							$scope.dateEndWeekly = new Date(obj.data.training.end);
							var times = obj.data.training.time.split(' ');
							var hours = [];
							var minutes = [];
							var rooms = [];
							var weekDays = obj.data.training.days.split(' ');
							for (var l = 0; l < $scope.qDates; l++){
								times[l] = times[l].split('-')[0];
								hours[l] = times[l].split(':')[0];
								minutes[l] = times[l].split(':')[1];
								rooms[l] = obj.data.training.lessons[l].location || '';
							}

							for (var k = 0; k < $scope.qDates; k++){
								$scope.datepickers[k] = {
									'time': new Date(1, 2, 3, hours[k], minutes[k]),
									'room': rooms[k],
									'toShowWeekDay': days[weekDays[k]]
								};
							}
						}
						else {
							$scope.descriptions[0] = {text: obj.data.training.description};
							$scope.datepickers.push({
								'dt': new Date(obj.data.training.date),
								'time': new Date(obj.data.training.date),
								'room': obj.data.training.location || ''
							});
						}
					}
					else {
						for (var l in obj.data.parts){
							var indl = obj.data.parts[l].title.substring(obj.data.parts[l].title.lastIndexOf('#') + 1, obj.data.parts[l].title.length);
							$scope.descriptions[indl - 1] = {text: obj.data.parts[l].description};
							$scope.datepickers[indl - 1] = {
								'dt': new Date(obj.data.parts[l].date),
								'time': new Date(obj.data.parts[l].date),
								'room': obj.data.parts[l].location || ''
							};
						}
					}
					$scope.duration = new Date(1, 2, 3, div(obj.data.training.duration, 60), obj.data.training.duration % 60);
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

				$scope.toShow();

				$scope.guests = obj.data.training.maxVisitorsCount;

				$scope.step1 = function () {
					$scope.$parent.totalItems = 5;

					if ($scope.trainingName === undefined || $scope.trainingName.length === 0) {
						$scope.$parent.totalItems = 5;
						return;
					}

					if ($scope.guests === undefined || $scope.guests.length === 0) {
						$scope.$parent.totalItems = 5;
						return;
					}

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
					if ($scope.duration.getHours() === 0 && $scope.duration.getMinutes() === 0) {
						ngNotify.set('Enter time ');
						return false;
					}

					if (obj.data.training.regular === 'Weekly ') {
						for (var j = 0; j < $scope.datepickers.length; j++) {
							if ($scope.datepickers[j].toShowWeekDay === 'Select day of week ') {
								ngNotify.set('Enter day of week ');
								return false;
							}
						}
					}
					return true;
				};

				$scope.trainingCreation = function () {
					var trainingsRequests = [];

					for (var i = 0; i < $scope.qDescr; i++) {
						var training = {};

						training.language = $scope.toShowLanguage.substring(0, $scope.toShowLanguage.length - 1);

						/* Training name */
						training.title = $scope.trainingName;

						training.regular = ($scope.toShowRepet === 'Weekly ');

						if ($scope.toShowRepet === 'Continuous ') {
							training.title += (' #' + (i + 1));
						}

						/* Training description */
						training.description = $scope.descriptions[i].text;

						/* Training visitors */
						training.maxVisitorsCount = $scope.guests;

						/* Training duration */

						if (!$scope.step3())
							return;

						training.duration = $scope.duration.getHours() * 60 + $scope.duration.getMinutes();

						/* Training dates */
						training.days = '';
						training.rooms = '';
						training.date = '';
						training.times = '';

						if ($scope.toShowRepet === 'Weekly ') {
							training.start = $scope.dateStartWeekly.getDate() + '.' + ($scope.dateStartWeekly.getMonth() + 1) + '.' + $scope.dateStartWeekly.getFullYear();
							training.end = $scope.dateEndWeekly.getDate() + '.' + ($scope.dateEndWeekly.getMonth() + 1) + '.' + $scope.dateEndWeekly.getFullYear();
							for (var j = 0; j < $scope.datepickers.length; j++) {
								training.days += days.indexOf($scope.datepickers[j].toShowWeekDay) + ' ';
								training.times += ($scope.datepickers[j].time.getHours() + ':' + $scope.datepickers[j].time.getMinutes());
								if ($scope.datepickers[j].room !== undefined && $scope.datepickers[j].room.length !== 0) {
									training.rooms += ($scope.datepickers[j].room);
								}
							}
						}
						else if ($scope.toShowRepet === 'Continuous ' || $scope.toShowRepet === 'One-off ') {
							if ($scope.datepickers[i].room !== undefined && $scope.datepickers[i].room.length !== 0) {
								training.rooms += ($scope.datepickers[i].room);
							}
							var date = $scope.datepickers[i].dt.getDate() + '.' + ($scope.datepickers[i].dt.getMonth() + 1) + '.' + $scope.datepickers[i].dt.getFullYear();
							training.date = date;
							training.times += ($scope.datepickers[i].time.getHours() + ':' + $scope.datepickers[i].time.getMinutes());
						}
						trainingsRequests[i] = $http.post('rest' + window.location.pathname, training);
					}
					$q.all(trainingsRequests).then(function (results) {
						$window.location.href = 'training/' + results[0].data.id;
					}, function (data) {
						ngNotify.set(data);
					});
				}

			}).catch(function (err) {
				console.log(err);
				ngNotify.set(err.statusText);
			}).finally(function () {
			});
}]);