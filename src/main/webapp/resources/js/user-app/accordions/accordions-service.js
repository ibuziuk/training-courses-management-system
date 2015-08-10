'use strict';

angular.module('accordions')
		.factory('accordionsService', ['$http', '$location', function ($http, $location) {
			var service = {},
					feedback = function (date, text) {
						return {
							date: date,
							text: text,
							quality: 'danger'
						}
					},
					training = function (type, title, trainer, date, time, feedbacks) {
						return {
							type: type,
							title: title,
							trainer: trainer,
							date: date,
							time: time,
							feedbacks: feedbacks
						};
					};

			service.get = function () {
				var tmp = $location.absUrl().split('/'),
						id = tmp[tmp.length - 1];
				return $http.get('/rest/statistics/user/' + id + '/trainings');
			};

			service.parse = function (data) {
				var trainings = [],
						feedbacks = [],
						i,
						j;
				for (i = 0; i < data.exTrainings.length; i++) {
					for (j = 0; j < data.exTrainings[i].feedbacks.length; j++) {
						feedbacks.push(feedback(data.exTrainings[i].feedbacks[j].date,
								data.exTrainings[i].feedbacks[j].text));
					}
					trainings.push(training('exTrainings',
							data.exTrainings[i].title,
							data.exTrainigs[i].trainer,
							data.exTrainigs[i].date,
							data.exTrainigs[i].time,
							feedbacks));
					feedbacks = [];
				}
				for (i = 0; i < data.subscribedTrainings.length; i++) {
					for (j = 0; j < data.subscribedTrainings[i].feedbacks.length; j++) {
						feedbacks.push(feedback(data.subscribedTrainings[i].feedbacks[j].date,
								data.subscribedTrainings[i].feedbacks[j].text));
					}
					trainings.push(training('subscribedTrainings',
							data.subscribedTrainings[i].title,
							data.subscribedTrainings[i].trainer,
							data.subscribedTrainings[i].date,
							data.subscribedTrainings[i].time,
							feedbacks));
					feedbacks = [];
				}
				for (i = 0; i < data.visitedTrainings.length; i++) {
					for (j = 0; j < data.visitedTrainings[i].feedbacks.length; j++) {
						feedbacks.push(feedback(data.visitedTrainings[i].feedbacks[j].date,
								data.visitedTrainings[i].feedbacks[j].text));
					}
					trainings.push(training('visitedTrainings',
							data.visitedTrainings[i].title,
							data.visitedTrainings[i].trainer,
							data.visitedTrainings[i].date,
							data.visitedTrainings[i].time,
							feedbacks));
					feedbacks = [];
				}
				for (i = 0; i < data.waitingListTrainings.length; i++) {
					for (j = 0; j < data.waitingListTrainings[i].feedbacks.length; j++) {
						feedbacks.push(feedback(data.waitingListTrainings[i].feedbacks[j].date,
								data.waitingListTrainings[i].feedbacks[j].text));
					}
					trainings.push(training('waitingListTrainings',
							data.waitingListTrainings[i].title,
							data.waitingListTrainings[i].trainer,
							data.waitingListTrainings[i].date,
							data.waitingListTrainings[i].time,
							feedbacks));
					feedbacks = [];
				}
				return trainings;
			};

			return service;
		}]);