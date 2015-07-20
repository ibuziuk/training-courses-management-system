'use strict'

var dropdownCtrl = angular.module('dropdownCtrl', []);

dropdownCtrl.controller('dropdownCtrl', function ($scope) {
  
  $scope.toShowRepet = 'One-off ';
  $scope.toShowType = 'Inner training ';
  $scope.whatChosen = 0;

  $scope.chooseRepet = function(rep){
    $scope.whatChosen = rep;
    if (rep == 0){
      $scope.toShowRepet = 'One-off ';
    }
    
    else if (rep == 1){
      $scope.toShowRepet = 'Weekly ';
    }
      
    else{
      $scope.toShowRepet = 'Continuous ';
    }
  }

  $scope.chooseType = function(rep){
    if (rep == 0)
      $scope.toShowType = 'Inner training ';
    else if (rep == 1)
      $scope.toShowType = 'Outer training ';
  }
  
  $scope.toShow = function(){     
        if ($scope.toShowRepet.replace(/\s/g, '') == 'One-off' || $scope.toShowRepet.replace(/\s/g, '') == 'Weekly')
            $scope.$parent.q = 1;
        else 
            $scope.$parent.q = $scope.days;
    
        $scope.$parent.descriptions = [];
        for (var i = 0; i < $scope.$parent.q; i++) {
            $scope.$parent.descriptions.push('');
        }
        return true;
  }
});