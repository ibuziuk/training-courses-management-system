'use strict';

angular.module('tabsServices', [])
		.factory('tableService', ['$http', function ($http) {
			var service = {},
					event = function (title, date, time, regular, location, firstName, lastName, placesOccupied, placesAll, tags, approved, trainingId) {
						return {
							title: title,
							date: date,
							time: time,
							regular: regular,
							location: location,
							coach: firstName + ' ' + lastName,
							places: placesOccupied + '/' + placesAll,
							tags: tags,
							approved: approved,
							url: 'training/' + trainingId
						};
					},
					tag = function (title, color) {
						return {
							title: title,
							color: color
						};
					};

			service.parse = function (data) {
				var events = [],
						tags = [],
						size = data.size,
						i,
						j;
				for (i = 0; i < data.list.length; i++) {
					for (j = 0; j < data.list[i].tags.length; j++) {
						tags.push(tag(data.list[i].tags[j].name, data.list[i].tags[j].color));
					}
					if (data.list[i].regular === true) {
						events.push(event(data.list[i].title,
								moment(data.list[i].start).format('DD.MM.YYYY') + ' - ' + moment(data.list[i].end).format('DD.MM.YYYY'),
								service.weekDays(data.list[i].days, data.list[i].time),
								data.list[i].regular,
								'(regular)',
								data.list[i].trainer.firstName,
								data.list[i].trainer.lastName,
								data.list[i].visitors.length,
								data.list[i].maxVisitorsCount,
								tags,
								data.list[i].approved,
								data.list[i].trainingId));
					} else {
						events.push(event(data.list[i].title,
								moment(data.list[i].date).format('DD.MM.YYYY'),
								data.list[i].time,
								data.list[i].regular,
								data.list[i].location,
								data.list[i].trainer.firstName,
								data.list[i].trainer.lastName,
								data.list[i].visitors.length,
								data.list[i].maxVisitorsCount,
								tags,
								data.list[i].approved,
								data.list[i].trainingId));
					}
					tags = [];
				}

				return {
					size: size,
					list: events
				};
			};

			service.parseTags = function (data) {
				var tags = [],
						i;

				for (i = 0; i < data.tags.length; i++) {
					tags.push(tag(data.tags[i].name, data.tags[i].color))
				}

				return tags;
			};

			service.createUrl = function (url, config) {
				var ret = [],
						uri;
				ret.push('person=' + 'all');
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
				ret.push('person=' + 'all');
				ret.push('pageNumber=' + config.page);
				ret.push('pageSize=' + config.count);
				ret.push('searchType=' + config.searching.type);
				ret.push('value=' + encodeURIComponent(config.searching.value));
				uri = url + '?' + ret.join("&");
				return encodeURI(uri);
			};

			service.get = function (url, config) {
				return $http.get(service.createUrl(url, config));
			};

			service.getSearch = function (url, config) {
				return $http.get(service.createSearchUrl(url, config));
			};

			service.getRecommend = function (url) {
				return $http.get(url);
			};

			service.getTags = function (url) {
				return $http.get(url);
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