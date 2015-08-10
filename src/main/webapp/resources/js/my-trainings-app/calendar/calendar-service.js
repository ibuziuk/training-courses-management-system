'use strict';

angular.module('calendar').factory('calendarService', ['$http', function ($http) {
	var service = {},
			training = function (title, typeText, type, startsAt, endsAt, trainingId) {
				return {
					title: title + ' (' + typeText + ')',
					type: type,
					startsAt: startsAt,
					endsAt: endsAt,
					deletable: false,
					editable: false,
					draggable: false,
					resizable: false,
					incrementsBadgeTotal: true,
					url: 'training/' + trainingId
				};
			},
			today = moment().unix();

	service.get = function (url) {
		return $http.get(url);
	};

	service.parse = function (data) {
		var trainings = [],
				type,
				i,
				j;

		for (i = 0; i < data.trainings.list.length; i++) {
			if (data.trainings.list[i].regular) {
				for (j = 0; j < data.trainings.list[i].lessons.length; j++) {
					type = service.setType(data.trainings.list[i], data.id, j);
					trainings.push(training(data.trainings.list[i].title,
							type.text,
							type.type,
							data.trainings.list[i].lessons[j].date,
							service.endsAt(data.trainings.list[i].lessons[j].date, data.trainings.list[i].duration),
							data.trainings.list[i].trainingId));
				}
			} else {
				type = service.setType(data.trainings.list[i], data.id);
				trainings.push(training(data.trainings.list[i].title,
						type.text,
						type.type,
						data.trainings.list[i].date,
						service.endsAt(data.trainings.list[i].date, data.trainings.list[i].duration),
						data.trainings.list[i].trainingId));
			}
		}
		return trainings;
	};

	service.setType = function (training, id, index) {
		if (id === training.trainer.userId) {
			if (!training.approved) {
				return {
					text: 'Not approved',
					type: 'inverse'
				};
			} else if (training.regular) {
				if (service.isFuture(today, moment(training.lessons[index].date).unix())) {
					return {
						text: 'Future as trainer',
						type: 'info'
					};
				} else {
					return {
						text: 'Past as trainer',
						type: 'warning'
					};
				}
			} else {
				if (service.isFuture(today, moment(training.date).unix())) {
					return {
						text: 'Future as trainer',
						type: 'info'
					};
				} else {
					return {
						text: 'Past as trainer',
						type: 'warning'
					};
				}
			}
		} else {
			if (training.regular) {
				if (service.isFuture(today, moment(training.lessons[index].date).unix())) {
					return {
						text: 'Future as visitor',
						type: 'success'
					};
				} else {
					return {
						text: 'Past as visitor',
						type: 'important'
					};
				}
			} else {
				if (service.isFuture(today, moment(training.date).unix())) {
					return {
						text: 'Future as visitor',
						type: 'success'
					};
				} else {
					return {
						text: 'Past as visitor',
						type: 'important'
					};
				}
			}
		}
	};

	service.isFuture = function (today, trainingDay) {
		return today < trainingDay;
	};

	service.endsAt = function (date, duration) {
		return date + duration * 60000;
	};

	return service;
}]);