'use strict';

table.factory('tableService', ['moment', function (moment) {
	var service = {},
			event = function (type, title, date, location, trainerName, places, tags) {
				return {
					type: type,
					title: title,
					date: date,
					location: location,
					trainerName: trainerName,
					places: places,
					tags: tags
				};
			},
			today = moment(moment()).unix();

	service.trainerParsing = function (data) {
		var eventsTrainer = [],
				type,
				i;
		for (i = 0; i < data.length; i++) {
			if (data[i].approved) {
				if (service.isFuture(today, data[i].date)) {
					type = 'info';
					eventsTrainer.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
				} else {
					type = 'danger';
					eventsTrainer.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
				}
			} else {
				type = 'active';
				eventsTrainer.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
			}
		}

		return eventsTrainer;
	};

	service.visitorParsing = function (data) {
		var eventsVisitor = [],
				type,
				i;
		for (i = 0; i < data.length; i++) {
			if (data[i].approved) {
				if (service.isFuture(today, data[i].date)) {
					type = 'success';
					eventsVisitor.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
				} else {
					type = 'warning';
					eventsVisitor.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
				}
			}
		}

		return eventsVisitor;
	};

	service.isFuture = function (today, trainingDay) {
		trainingDay /= 1000;
		return today < trainingDay;
	};

	return service;
}]);