'use strict';

angular.module('table').factory('tableService', ['$http', function ($http) {
	var service = {},
			training = function (type, title, date, time, location, firstName, lastName, placesOccupied, placesAll, tags, trainingId) {
				return {
					type: type,
					title: title,
					date: date,
					time: time,
					location: location,
					coach: firstName + ' ' + lastName,
					places: placesOccupied + '/' + placesAll,
					tags: tags,
					url: 'training/' + trainingId
				};
			},
			tag = function (title, color) {
				return {
					title: title,
					color: color
				};
			},
			today = moment().unix();

	service.parse = function (data) {
		var trainings = [],
				tags = [],
				i,
				j;
		for (i = 0; i < data.trainings.size; i++) {
			for (j = 0; j < data.trainings.list[i].tags.length; j++) {
				tags.push(tag(data.trainings.list[i].tags[j].name, data.trainings.list[i].tags[j].color));
			}

			if (data.trainings.list[i].regular === true) {
				trainings.push(training(service.setType(data.trainings.list[i], data.id),
						data.trainings.list[i].title,
						moment(data.trainings.list[i].start).format('DD.MM.YYYY') + ' - ' + moment(data.trainings.list[i].end).format('DD.MM.YYYY'),
						service.weekDays(data.trainings.list[i].days, data.trainings.list[i].time),
						'(regular)',
						data.trainings.list[i].trainer.firstName,
						data.trainings.list[i].trainer.lastName,
						data.trainings.list[i].visitors.length,
						data.trainings.list[i].maxVisitorsCount,
						tags,
						data.trainings.list[i].trainingId));
			} else {
				trainings.push(training(service.setType(data.trainings.list[i], data.id),
						data.trainings.list[i].title,
						moment(data.trainings.list[i].date).format('DD.MM.YYYY'),
						data.trainings.list[i].time,
						data.trainings.list[i].location,
						data.trainings.list[i].trainer.firstName,
						data.trainings.list[i].trainer.lastName,
						data.trainings.list[i].visitors.length,
						data.trainings.list[i].maxVisitorsCount,
						tags,
						data.trainings.list[i].trainingId));
			}
			tags = [];
		}

		return {
			list: trainings,
			size: data.trainings.size
		};
	};

	service.setType = function (training, id) {
		if (!training.approved) {
			return 'Not approved';
		}

		if (id === training.trainer.userId) {
			if (training.regular) {
				if (service.isFuture(today, training.end)) {
					return 'Future as trainer';
				} else {
					return 'Past as trainer';
				}
			} else {
				if (service.isFuture(today, training.date)) {
					return 'Future as trainer';
				} else {
					return 'Past as trainer';
				}
			}
		} else {
			if (training.regular) {
				if (service.isFuture(today, training.end)) {
					return 'Future as visitor';
				} else {
					return 'Past as visitor';
				}
			} else {
				if (service.isFuture(today, training.date)) {
					return 'Future as visitor';
				} else {
					return 'Past as visitor';
				}
			}
		}
	};

	service.createUrl = function (url, config) {
		var ret = [],
				uri;
		ret.push('pageNumber=' + config.page);
		ret.push('pageSize=' + config.count);
		for (var param in config.sorting) {
			ret.push('sorting=' + param);
			ret.push('order=' + config.sorting[param]);
		}
		uri = url + '?' + ret.join("&");
		return encodeURI(uri);
	};

	service.createSearchUrl = function (url, config) {
		var ret = [],
				uri;
		ret.push('pageNumber=' + config.page);
		ret.push('pageSize=' + config.count);
		ret.push('searchType=' + config.searching.type);
		ret.push('value=' + encodeURIComponent(config.searching.value));
		uri = url + '?' + ret.join("&");
		return encodeURI(uri);
	};

	service.get = function (url, config) {
		console.log(service.createUrl(url, config));
		return $http.get(service.createUrl(url, config));
	};

	service.getSearch = function (url, config) {
		console.log(service.createSearchUrl(url, config));
		return $http.get(service.createSearchUrl(url, config));
	};

	service.isFuture = function (today, trainingDay) {
		trainingDay /= 1000;
		return today < trainingDay;
	};

	service.weekDays = function (days, time) {
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

	return service;
}]);