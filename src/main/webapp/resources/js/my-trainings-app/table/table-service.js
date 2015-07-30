'use strict';

angular.module('table').factory('tableService', [function () {
	var service = {},
			event = function (type, title, date, time, location, trainerName, placesOccupied, placesAll, tags) {
				return {
					type: type,
					title: title,
					date: date,
					time: time,
					location: location,
					trainerName: trainerName,
					places: placesOccupied + '/' + placesAll,
					tags: tags
				};
			},
			tag = function (title, color) {
				return {
					title: '#' + title,
					color: color
				};
			},
			today = moment().unix();

	service.trainerParsing = function (data) {
		var eventsTrainer = [],
				tags = [],
				type,
				i,
				j;
		for (i = 0; i < data.length; i++) {
			for (j = 0; j < data[i].tags.length; j++) {
				tags.push(tag(data[i].tags[j].name, data[i].tags[j].color));
			}
			if (data[i].approved) {
				if (service.isFuture(today, data[i].date)) {
					type = 'info';
					eventsTrainer.push(event(type, data[i].title, data[i].dateOnString, data[i].time, data[i].location, data[i].trainer.firstName + ' ' + data[i].trainer.lastName, data[i].visitors.length, data[i].maxVisitorsCount, tags));
				} else {
					type = 'danger';
					eventsTrainer.push(event(type, data[i].title, data[i].dateOnString, data[i].time, data[i].location, data[i].trainer.firstName + ' ' + data[i].trainer.lastName, data[i].visitors.length, data[i].maxVisitorsCount, tags));
				}
			} else {
				type = 'active';
				eventsTrainer.push(event(type, data[i].title, data[i].dateOnString, data[i].time, data[i].location, data[i].trainer.firstName + ' ' + data[i].trainer.lastName, data[i].visitors.length, data[i].maxVisitorsCount, tags));
			}
			tags = [];
		}

		return eventsTrainer;
	};

	service.visitorParsing = function (data) {
		var eventsVisitor = [],
				tags = [],
				type,
				i,
				j;
		for (i = 0; i < data.length; i++) {
			for (j = 0; j < data[i].tags.length; j++) {
				tags.push(tag(data[i].tags[j].name, data[i].tags[j].color));
			}
			if (data[i].approved) {
				if (service.isFuture(today, data[i].date)) {
					type = 'success';
					eventsVisitor.push(event(type, data[i].title, data[i].dateOnString, data[i].time, data[i].location, data[i].trainer.firstName + ' ' + data[i].trainer.lastName, data[i].visitors.length, data[i].maxVisitorsCount, tags));
				} else {
					type = 'warning';
					eventsVisitor.push(event(type, data[i].title, data[i].dateOnString, data[i].time, data[i].location, data[i].trainer.firstName + ' ' + data[i].trainer.lastName, data[i].visitors.length, data[i].maxVisitorsCount, tags));
				}
			}
			tags = [];
		}

		return eventsVisitor;
	};

	service.isFuture = function (today, trainingDay) {
		trainingDay /= 1000;
		return today < trainingDay;
	};

	return service;
}]);