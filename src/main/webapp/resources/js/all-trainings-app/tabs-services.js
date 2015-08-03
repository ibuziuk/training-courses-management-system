'use strict';

angular.module('tabsServices', [])
		.factory('tableService', ['$http', function ($http) {
			var service = {},
					event = function (title, date, time, regular, location, trainerName, placesOccupied, placesAll, tags, approved) {
						return {
							title: title,
							date: date,
							time: time,
							regular: regular,
							location: location,
							trainerName: trainerName,
							places: placesOccupied + '/' + placesAll,
							tags: tags,
							approved: approved
						};
					},
					tag = function (title, color) {
						return {
							title: '#' + title,
							color: color
						};
					};

			service.parsing = function (data) {
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
								'(regular)',
								data.list[i].time,
								data.list[i].regular,
								'(regular)',
								data.list[i].trainer.firstName + ' ' + data.list[i].trainer.lastName,
								data.list[i].visitors.length,
								data.list[i].maxVisitorsCount,
								tags,
								data.list[i].approved));
					} else {
						events.push(event(data.list[i].title,
								data.list[i].dateOnString,
								data.list[i].time,
								data.list[i].regular,
								data.list[i].location,
								data.list[i].trainer.firstName + ' ' + data.list[i].trainer.lastName,
								data.list[i].visitors.length,
								data.list[i].maxVisitorsCount,
								tags,
								data.list[i].approved));
					}

					tags = [];
				}

				return {
					size: size,
					list: events
				};
			};

			service.createUrl = function (url, config) {
				var ret = [];
				ret.push('pageNum=' + config.page);
				ret.push('pageSize=' + config.count);
				for (var param in config.sorting) {
					ret.push('sorting=' + param);
					ret.push('order=' + config.sorting[param]);
				}
				return url + '?' + ret.join("&");
			};

			service.get = function (url, config) {
				return $http.get(service.createUrl(url, config))
			};

			return service;
		}]);