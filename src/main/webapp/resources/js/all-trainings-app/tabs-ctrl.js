'use strict';

var tabsCtrl = angular.module('tabsCtrl', []),
    tag = function(title, color) {
        return { 
            title: title,
            color: color
        };
    },
    row = function(title, date, location, trainer, places, tags){
        return { 
            title: title,
            date: date,
            location: location,
            trainer: trainer,
            places: places,
            tags: tags
        }
    };

tabsCtrl.controller('tabsCtrl', ['$scope', '$sce', function($scope, $sce) {
    $scope.tabs = [
        {title: 'Future trainings', isSearch: true, isTable: true, isTags: false},
        {title: 'Recomendations', isSearch: true, isTable: true, isTags: true},
        {title: 'History of trainings', isSearch: true, isTable: true, isTags: false},
        {title: 'Waiting list', isSearch: true, isTable: true, isTags: false}
    ];
    
    $scope.columns = [
        {name: 'Title'},
        {name: 'Date'},
        {name: 'Location'},
        {name: 'Trainer'},
        {name: 'Places free/all'},
        {name: 'Tags'}
    ];
    
    $scope.tags1 = [
                tag("#java", "info"),
                tag("#c++", "default")
            ];
    $scope.tags2 = [
                tag("#js", "danger")
            ];
    $scope.tags3 = [
                tag("#sql", "success")
            ];
    $scope.rows = [
                row("Back-end", "25.05.2012", "234", "Yaroshevich", "12/15", $scope.tags1),
                row("Front-end", "28.05.2014", "243", "Shchaurouski", "10/15", $scope.tags2),
                row("SQL", "05.08.1999", "145", "Romashko", "1/15", $scope.tags3),
            ];
}]);