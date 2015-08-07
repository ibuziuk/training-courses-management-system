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

				/* Type */
				$scope.toShowType = (obj.data.training.externalType) ? types[1] : types[0];

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
						$scope.descriptions.push({text: obj.data.training.description});
						if (obj.data.training.regular){
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
							$scope.datepickers[0] = {
								'dt': new Date(obj.data.training.date),
								'time': new Date(obj.data.training.date),
								'room': obj.data.training.location || ''
							};
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
					$scope.minDate = new Date();
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

				$http.get('rest/tag').then(function (objTag) {
					$scope.checkboxTags = objTag.data;
					for (var k in $scope.checkboxTags) {
						$scope.checkboxTags[k].name = '#' + $scope.checkboxTags[k].name;
						$scope.checkboxTags[k].id = $scope.checkboxTags[k].name;
					}

					$http.get('rest/audience').then(function (objAud) {
						$scope.checkboxAudiences = objAud.data;
						for (var k in $scope.checkboxAudiences) {
							$scope.checkboxAudiences[k].id = $scope.checkboxAudiences[k].value;
						}

						for (var k = 0; k < obj.data.training.tags.length; k++){
							$scope.selectedTags[k] = '#' + obj.data.training.tags[k].name;
						}

						for (var l = 0; l < obj.data.training.audiences.length; l++){
							$scope.selectedAudiences[l] = obj.data.training.audiences[l].value;
						}

						$scope.guests = obj.data.training.maxVisitorsCount;
					});
				});

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

				$scope.trainingCreation = function () {
					var trainingsRequests = [];

					for (var i = 0; i < $scope.qDescr; i++) {
						var training = {};

						/* Tags */
						training.tags = angular.copy($scope.selectedTags);

						for (var k = 0; k < training.tags.length; k++) {
							training.tags[k] = training.tags[k].substring(1);
						}

						if (training.tags.length === 0) {
							ngNotify.set('You should choose at list one tag!');
							return;
						}

						/* Audience */
						training.audience = $scope.selectedAudiences;

						if (training.audience.length === 0) {
							ngNotify.set('You should choose an audience for your training!');
							return;
						}

						training.type = !($scope.toShowType === 'Inner training ');
						training.regular = ($scope.toShowRepet === 'Weekly ');
						training.language = $scope.toShowLanguage.substring(0, $scope.toShowLanguage.length - 1);
						training.continuous = continuous;


						/* Training name */
						training.title = $scope.trainingName;

						if ($scope.toShowRepet === 'Continuous ') {
							training.title += (' #' + (i + 1));
						}

						/* Training description */
						training.description = $scope.descriptions[i].text;

						/* Training visitors */
						training.visitors = $scope.guests;

						if (training.visitors === undefined || training.visitors.length === 0) {
							ngNotify.set('You should enter quantity of guests!');
							return;
						}

						/* Training duration */

						training.duration = $scope.duration.getHours() * 60 + $scope.duration.getMinutes();

						/* Training dates */
						training.days = '';
						training.rooms = [];
						training.date = '';
						training.times = [];

						if ($scope.toShowRepet === 'Weekly ') {
							training.date = $scope.dateStartWeekly.getDate() + '.' + ($scope.dateStartWeekly.getMonth() + 1) + '.' + $scope.dateStartWeekly.getFullYear();
							training.end = $scope.dateEndWeekly.getDate() + '.' + ($scope.dateEndWeekly.getMonth() + 1) + '.' + $scope.dateEndWeekly.getFullYear();
							for (var j = 0; j < $scope.datepickers.length; j++) {
								training.days += days.indexOf($scope.datepickers[j].toShowWeekDay) + ' ';
								training.times.push($scope.datepickers[j].time.getHours() + ':' + $scope.datepickers[j].time.getMinutes());
								if ($scope.datepickers[j].room !== undefined && $scope.datepickers[j].room.length !== 0) {
									training.rooms.push($scope.datepickers[j].room);
								}
							}
						}
						else if ($scope.toShowRepet === 'Continuous ' || $scope.toShowRepet === 'One-off ') {
							if ($scope.datepickers[i].room !== undefined && $scope.datepickers[i].room.length !== 0) {
								training.rooms.push($scope.datepickers[i].room);
							}
							var date = $scope.datepickers[i].dt.getDate() + '.' + ($scope.datepickers[i].dt.getMonth() + 1) + '.' + $scope.datepickers[i].dt.getFullYear();
							training.date = date;
							training.times.push($scope.datepickers[i].time.getHours() + ':' + $scope.datepickers[i].time.getMinutes());
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