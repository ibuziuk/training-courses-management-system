angular.module('myTrainingsApp').factory('event', [function() {
    var service = [];

    var event = function(title, type, startsAt, endsAt, editable, deletable, draggable, resizable, incrementsBadgeTotal) {
        return {
            title: title,
            type: type,
            startsAt: startsAt,
            endsAt: endsAt,
            editable: false,
            deletable: false,
            draggable: false,
            resizable: false,
            incrementsBadgeTotal: true
        };
    };

    service.parse = function(data) {
        var type = 'important',
            today = new Date();
        for (var i = 0; i < data.length; i++) {
            if (!service.isApproved(data[i].is_approved)) {
                type = 'inverse';
            } else if (service.isFuture(today.getTime(), data[i].date)) {
                type = 'success';
            } else {
                type = 'warning';
            }
            service.push(event(data[i].title, type, new Date(data[i].date), data[i].duration * 3600000));
        }

        return service;
    };

    service.isApproved = function(approv) {
        return approv;
    };

    service.isFuture = function(today, trainingDay) {
        if (today < trainingDay) {
            return true;
        } else {
            return false;
        }
    };
    return service;
}]);