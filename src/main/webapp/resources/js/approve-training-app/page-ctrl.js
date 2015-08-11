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

		if (($scope.durationEd && $scope.durationEd.getHours() === 0 && $scope.durationEd.getMinutes() === 0) || ($scope.duration.getHours() === 0 && $scope.duration.getMinutes() === 0)) {
			$scope.$parent.totalItems = 25;
			return;
		}

		$scope.$parent.totalItems = 35;
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

					if (containsNotNullEl(obj.data.edit) && obj.data.edit[0].language)
						$scope.toShowLanguageEd = obj.data.edit[0].language.value + ' ';
				}

				else {
					if (obj.data.edit && obj.data.edit.language)
						$scope.toShowLanguageEd = obj.data.edit.language.value + ' ';

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
					if (obj.data.old.training.regular) {

						if (obj.data.edit && obj.data.edit.start) {
							$scope.dateStartWeekly = new Date(obj.data.edit.start);
							$scope.hasDateEd = true;
						}
						else {
							$scope.dateStartWeekly = new Date(obj.data.old.training.start);
						}

						if (obj.data.edit && obj.data.edit.end) {
							$scope.dateEndWeekly = new Date(obj.data.edit.end);
							$scope.hasDateEd = true;
						}
						else {
							$scope.dateEndWeekly = new Date(obj.data.old.training.end);
						}

						if (obj.data.edit && obj.data.edit.days) {
							var weekDaysEd = (obj.data.edit.days.substring(0, obj.data.edit.days.length - 1)).split(" ");
							for (var k = 0; k < weekDaysEd.length; k++) {
								if ($scope.datepickers[k]) {
									$scope.datepickers[k].toShowWeekDayEd = days[weekDaysEd[k]];
								}
								else {
									$scope.datepickers[k] = {toShowWeekDayEd: days[weekDaysEd[k]]};
								}
							}
						}

						var weekDays = (obj.data.old.training.days.substring(0, obj.data.old.training.days.length - 1)).split(" ");
						for (var k = 0; k < weekDays.length; k++) {
							if ($scope.datepickers[k]) {
								$scope.datepickers[k].toShowWeekDay = days[weekDays[k]];
							}
							else {
								$scope.datepickers[k] = {toShowWeekDay: days[weekDays[k]]};
							}
						}

						if (obj.data.edit && obj.data.edit.time) {
							var timesEd = (obj.data.edit.time.substring(0, obj.data.edit.time.length - 1)).split(" ");
							var hours, minutes;
							for (var k = 0; k < timesEd.length; k++) {
								timesEd[k] = timesEd[k].split('-')[0];
								hours = timesEd[k].split(':')[0];
								minutes = timesEd[k].split(':')[1];
								if ($scope.datepickers[k]) {
									$scope.datepickers[k].timeEd = new Date(80, 2, 3, hours, minutes);
								}
								else {
									$scope.datepickers[k] = {timeEd: new Date(80, 2, 3, hours, minutes)};
								}
								$scope.datepickers[k].hasTimeEd = true;
							}
						}

						var times = (obj.data.old.training.time.substring(0, obj.data.old.training.time.length - 1)).split(" ");
						var hours, minutes;
						if (obj.data.old.training.location)
							var rooms = obj.data.old.training.location.split(" ");
						for (var k = 0; k < times.length; k++) {
							times[k] = times[k].split('-')[0];
							hours = times[k].split(':')[0];
							minutes = times[k].split(':')[1];
							if ($scope.datepickers[k]) {
								$scope.datepickers[k].time = new Date(80, 2, 3, hours, minutes);
							}
							else {
								$scope.datepickers[k] = {time: new Date(80, 2, 3, hours, minutes)};
							}
							if (rooms && rooms[k])
								$scope.datepickers[k].room = rooms[k];
						}

					}

					else if (!obj.data.old.training.regular) {
						if (obj.data.edit && obj.data.edit.time) {
							var time = obj.data.edit.time.split("-")[0];
							var hour = time.split(":")[0];
							var minute = time.split(":")[1];
							if ($scope.datepickers[0]) {
								$scope.datepickers[0].hasTimeEd = true;
								$scope.datepickers[0].timeEd = new Date(80, 2, 3, hour, minute);
							}
							else {
								$scope.datepickers[0] = {hasTimeEd: true, timeEd: new Date(80, 2, 3, hour, minute)};
							}
						}

						var time = obj.data.old.training.time.split("-")[0];
						var hour = time.split(":")[0];
						var minute = time.split(":")[1];
						if ($scope.datepickers[0])
							$scope.datepickers[0].time = new Date(80, 2, 3, hour, minute);
						else
							$scope.datepickers[0] = {time: new Date(80, 2, 3, hour, minute)};

						if (obj.data.edit && obj.data.edit.date) {
							if ($scope.datepickers[0]) {
								$scope.datepickers[0].dt = new Date(obj.data.edit.date);
							}
							else {
								$scope.datepickers[0] = {dt: new Date(obj.data.edit.date)};
							}
							$scope.hasDateEd = true;
						}
						else {
							if ($scope.datepickers[0]) {
								$scope.datepickers[0].dt = new Date(obj.data.old.training.date);
							}
							else {
								$scope.datepickers[0] = {dt: new Date(obj.data.old.training.date)};
							}
						}

						if (obj.data.old.training.location)
							$scope.datepickers[0].room = obj.data.old.training.location;
					}

					if (obj.data.edit && obj.data.edit.maxVisitorsCount) {
						$scope.guestsEd = obj.data.edit.maxVisitorsCount;
						$scope.hasGuestsEd = true;
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
					$scope.duration = new Date(80, 2, 3, div(obj.data.old.training.duration, 60), obj.data.old.training.duration % 60);
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
					$scope.duration = new Date(80, 2, 3, div(obj.data.old.training.duration, 60), obj.data.old.training.duration % 60);
					if (containsNotNullEl(obj.data.edit) && obj.data.edit[0].maxVisitorsCount) {
						$scope.guestsEd = obj.data.edit[0].maxVisitorsCount;
						$scope.hasGuestsEd = true;
					}

					for (var k = 0; k < obj.data.edit.length; k++) {
						if (obj.data.edit[k] && obj.data.edit[k].date) {
							if ($scope.datepickers[k]) {
								$scope.datepickers[k].dt = new Date(obj.data.edit[k].date);
							}
							else {
								$scope.datepickers[k] = {dt: new Date(obj.data.edit[k].date)};
							}
							$scope.hasDateEd = true;
						}
						else {
							if ($scope.datepickers[k]) {
								$scope.datepickers[k].dt = new Date(obj.data.old.parts[k].date);
							}
							else {
								$scope.datepickers[k] = {dt: new Date(obj.data.old.parts[k].date)};
							}
						}

						if (obj.data.edit[k] && obj.data.edit[k].time) {
							var time = obj.data.edit[k].time.split('-')[0];
							var hour = time.split(':')[0];
							var minute = time.split(':')[1];
							if ($scope.datepickers[k]) {
								$scope.datepickers[k].timeEd = new Date(80, 2, 3, hour, minute);
							}
							else {
								$scope.datepickers[k] = {timeEd: new Date(80, 2, 3, hour, minute)};
							}
							$scope.datepickers[k].hasTimeEd = true;
						}
						var times = obj.data.old.parts[k].time.split('-')[0];
						hours = times.split(':')[0];
						minutes = times.split(':')[1];
						if ($scope.datepickers[k]) {
							$scope.datepickers[k].time = new Date(80, 2, 3, hours, minutes);
						}
						else {
							$scope.datepickers[k] = {time: new Date(80, 2, 3, hours, minutes)};
						}

						if (obj.data.old.parts[k] && obj.data.old.parts[k].location) {
							$scope.datepickers[k].room = obj.data.old.parts[k].location;
						}
					}
				}

				/* Time */

				$scope.hstep = 1;
				$scope.mstep = 10;

				$scope.ismeridian = false;

				$scope.events = [];

				if (obj.data.old.training.regular) {
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

				else if (continuous) {
					if ($scope.hasDateEd) {
						for (var k = 0; k < obj.data.old.parts.length; k++) {
							$scope.events[k] = {
								date: new Date(obj.data.old.parts[k].date),
								status: 'full'
							};
						}
					}
				}

				else {
					if ($scope.hasDateEd) {
						$scope.events[0] = {
							date: new Date(obj.data.old.training.date),
							status: 'full'
						};
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

				$scope.tags = obj.data.old.training.tags;
				$scope.audiences = obj.data.old.training.audiences;

				$scope.guests = obj.data.old.training.maxVisitorsCount;

				$scope.disapproveCreation = function () {
					if (continuous) {
						for (var k in obj.data.old.parts) {
							$http.post('rest/training/disapprove/' + obj.data.old.parts[k].trainingId, {}).then(
									function (data) {
										$window.location.href = 'welcome';
									}
							);
						}
					}
					else {
						$http.post('rest/training/disapprove/' + obj.data.old.training.trainingId, {}).then(
								function (data) {
									$window.location.href = 'welcome';
								}
						);
					}
				};

				$scope.approveCreation = function () {
					var trainingsRequests = [];

					for (var i = 0; i < $scope.qDescr; i++) {
						var training = {};

						if (!$scope.toShowLanguageEd)
							training.language = $scope.toShowLanguage.substring(0, $scope.toShowLanguage.length - 1);
						else
							training.language = $scope.toShowLanguageEd.substring(0, $scope.toShowLanguageEd.length - 1);

						/* Training name */

						if (!$scope.trainingNameEd)
							training.title = $scope.trainingName;
						else
							training.title = $scope.trainingNameEd;

						training.regular = ($scope.toShowRepet === 'Weekly ');

						if ($scope.toShowRepet === 'Continuous ') {
							training.title += (' #' + (i + 1));
						}

						/* Training description */
						if (!$scope.descriptions[i].textEd)
							training.description = $scope.descriptions[i].text;
						else
							training.description = $scope.descriptions[i].textEd;

						/* Training visitors */
						if (!$scope.guestsEd)
							training.maxVisitorsCount = $scope.guests;
						else
							training.maxVisitorsCount = $scope.guestsEd;


						if (!$scope.durationEd)
							training.duration = $scope.duration.getHours() * 60 + $scope.duration.getMinutes();
						else
							training.duration = $scope.durationEd.getHours() * 60 + $scope.durationEd.getMinutes();

						/* Training dates */
						training.days = '';
						training.rooms = '';
						training.date = '';
						training.times = '';

						if ($scope.toShowRepet === 'Weekly ') {
							training.start = $scope.dateStartWeekly.getDate() + '.' + ($scope.dateStartWeekly.getMonth() + 1) + '.' + $scope.dateStartWeekly.getFullYear();
							training.end = $scope.dateEndWeekly.getDate() + '.' + ($scope.dateEndWeekly.getMonth() + 1) + '.' + $scope.dateEndWeekly.getFullYear();
							for (var j = 0; j < $scope.datepickers.length; j++) {
								if (!$scope.datepickers[j].toShowWeekDayEd)
									training.days += days.indexOf($scope.datepickers[j].toShowWeekDay) + ' ';
								else
									training.days += days.indexOf($scope.datepickers[j].toShowWeekDayEd) + ' ';

								if (!$scope.datepickers[j].timeEd)
									training.times += ($scope.datepickers[j].time.getHours() + ':' + $scope.datepickers[j].time.getMinutes()) + ' ';
								else
									training.times += ($scope.datepickers[j].timeEd.getHours() + ':' + $scope.datepickers[j].timeEd.getMinutes()) + ' ';

								if ($scope.datepickers[j].room !== undefined && $scope.datepickers[j].room.length !== 0) {
									training.rooms += ($scope.datepickers[j].room) + ' ';
								}
							}
						}
						else if ($scope.toShowRepet === 'Continuous ' || $scope.toShowRepet === 'One-off ') {
							if ($scope.datepickers[i].room !== undefined && $scope.datepickers[i].room.length !== 0) {
								training.rooms += ($scope.datepickers[i].room) + ' ';
							}

							var date = $scope.datepickers[i].dt.getDate() + '.' + ($scope.datepickers[i].dt.getMonth() + 1) + '.' + $scope.datepickers[i].dt.getFullYear();
							training.date = date;

							if (!$scope.datepickers[i].timeEd)
								training.times += ($scope.datepickers[i].time.getHours() + ':' + $scope.datepickers[i].time.getMinutes()) + ' ';
							else
								training.times += ($scope.datepickers[i].timeEd.getHours() + ':' + $scope.datepickers[i].timeEd.getMinutes()) + ' ';
						}

						var curId;
						if (continuous) {
							var curPart = training.title.substring(training.title.lastIndexOf('#') + 1, training.title.length);
							curId = obj.data.old.parts[curPart - 1].trainingId;
						}
						else {
							curId = obj.data.old.training.trainingId;
						}
						trainingsRequests[i] = $http.post('rest/training/approve/' + curId, training);
					}
					$q.all(trainingsRequests).then(function (results) {
						$window.location.href = 'training/' + results[0].data.id;
					}, function (data) {
						ngNotify.set(data);
					});

				};

			}).catch(function (err) {
				console.log(err);
				ngNotify.set(err.statusText);
			}).finally(function () {
			});
}]);