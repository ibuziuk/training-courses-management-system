'use strict';

angular.module('tabs.service')
		.factory('tabsService', ['$http', '$location', function ($http, $location) {
			var service = {},
					training = function (type, title, trainer, date, time, language, trainingId, location) {
						return {
							type: type,
							title: title,
							trainer: trainer.firstName + ' ' + trainer.lastName,
							date: date,
							time: time,
							language: language,
							url: 'training/' + trainingId,
							location: location
						};
					}, profile = function (firstName, lastName, login, email, role) {
						return {
							firstName: firstName,
							lastName: lastName,
							login: login,
							email: email,
							role: role
						};
					}, feedback = function (type, trainingTitle, trainingId, date, coachFirstName, coachLastName, text) {
						return {
							type: type,
							trainingTitle: trainingTitle,
							trainingUrl: 'training/' + trainingId,
							date: date,
							coach: coachFirstName + ' ' + coachLastName,
							text: text
						};
					}, absence = function (trainingId, trainingTitle, date, trainerFirstName, trainerLastName, isReason, text) {
						return {
							url: 'training/' + trainingId,
							title: trainingTitle,
							date: date,
							coach: trainerFirstName + ' ' + trainerLastName,
							isReason: isReason,
							text: text
						};
					}, createUrl = function (url, config) {
						var ret = [];
						ret.push('from=' + 0);
						ret.push('to=' + 0);

						return url + '?' + ret.join('&');
					}, positiveOrNegative = function (mark) {
						if (mark > 4) {
							return 'success';
						} else {
							return 'danger'
						}
					}, weekDays = function (days, time) {
						var schedule = '',
								i,
								j = 0,
								tmpDay = days.split(' '),
								tmpTime = time.split(' ');

						for (i = 0; i < tmpDay.length; i++) {
							switch (tmpDay[i]) {
								case '0':
									schedule += ', Monday(' + tmpTime[j++] + ')';
									break;
								case '1':
									schedule += ', Tuesday(' + tmpTime[j++] + ')';
									break;
								case '2':
									schedule += ', Wednesday(' + tmpTime[j++] + ')';
									break;
								case '3':
									schedule += ', Thursday(' + tmpTime[j++] + ')';
									break;
								case '4':
									schedule += ', Friday(' + tmpTime[j++] + ')';
									break;
								case '5':
									schedule += ', Saturday(' + tmpTime[j++] + ')';
									break;
								case '6':
									schedule += ', Sunday(' + tmpTime[j++] + ')';
									break;
							}
						}
						schedule = schedule.slice(2, schedule.length);

						return schedule;
					};

			service.get = function (type) {
				var tmp = $location.absUrl().split('/'),
						id = tmp[tmp.length - 1],
						url = 'rest/user/' + id + '/' + type;
				return $http.get(createUrl(url, {}));
			};

			service.parse = function (data, type) {
				var result = [],
						i;
				if (type === 'general') {
					return profile(data.firstName, data.lastName, data.login, data.email, data.role);
				} else if (type === 'weekly') {
					for (i = 0; i < data.length; i++) {
						result.push(training(type,
								data[i].title,
								data[i].trainer,
								weekDays(data[i].days, data[i].time),
								moment(data[i].start).format('DD.MM.YYYY') + ' - ' + moment(data[i].end).format('DD.MM.YYYY'),
								data[i].language.value,
								data[i].trainingId,
								'regular'));
					}

					return result;
				} else if (type === 'feedbacks') {
					for (i = 0; i < data.length; i++) {
						result.push(feedback(positiveOrNegative(data[i].mark),
								data[i].trainingTitle,
								data[i].trainingId,
								moment(data[i].date).format('DD.MM.YYYY'),
								data[i].trainerFirstName,
								data[i].trainerLastName,
								data[i].text));
					}

					return result;
				} else if (type === 'absence') {
					for (i = 0; i < data.absence.length; i++) {
						result.push(absence(data.absence[i].training.trainingId,
								data.absence[i].training.title,
								moment(data.absence[i].training.date).format('DD.MM.YYYY'),
								data.absence[i].training.trainer.firstName,
								data.absence[i].training.trainer.lastName,
								data.absence[i].reasonable,
								data.absence[i].reasonText));
					}
					for (i = 0; i < data.absenceLesson.length; i++) {
						result.push(absence(data.absenceLesson[i].training.trainingId,
								data.absenceLesson[i].training.title,
								moment(data.absenceLesson[i].lesson.date).format('DD.MM.YYYY'),
								data.absenceLesson[i].training.trainer.firstName,
								data.absenceLesson[i].training.trainer.lastName,
								data.absenceLesson[i].isReasonable,
								data.absenceLesson[i].reasonText));
					}

					return result;
				} else {
					for (i = 0; i < data.length; i++) {
						result.push(training(type,
								data[i].title,
								data[i].trainer,
								moment(data[i].date).format('DD.MM.YYYY'),
								data[i].time,
								data[i].language.value,
								data[i].trainingId,
								data[i].location));
					}

					return result;
				}
			};

			return service;
		}]);