var checkboxCtrl = angular.module('checkboxCtrl', []);

checkboxCtrl.controller('checkboxCtrl', function ($scope) {
	$scope.checkboxTags = [
		{ type: 'checkbox', tag: '#Java', checked: false},
		{ type: 'checkbox', tag: '#PHP', checked: false},
		{ type: 'checkbox', tag: '#JavaScript', checked: false},
		{ type: 'checkbox', tag: '#C++', checked: false},
		{ type: 'checkbox', tag: '#Scala', checked: false},
		{ type: 'checkbox', tag: '#Go', checked: false},
		{ type: 'checkbox', tag: '#English', checked: false}
	];

	$scope.checkboxAudiences = [
		{ type: 'checkbox', audience: 'Java', checked: false},
		{ type: 'checkbox', audience: 'PHP', checked: false},
		{ type: 'checkbox', audience: 'JavaScript', checked: false},
		{ type: 'checkbox', audience: 'C++', checked: false},
		{ type: 'checkbox', audience: 'Scala', checked: false},
		{ type: 'checkbox', audience: 'Go', checked: false}
	];

	window.scope = $scope;

	$scope.selectAll = false;
	
	$scope.toggleSeleted = function() {
        $scope.selectAll = !$scope.selectAll;
       	for (var i = 0; i < $scope.checkboxAudiences.length; i++) {
            $scope.checkboxAudiences[i].checked = $scope.selectAll;
        }
    };

    function getIndex(aud){
    	for (var i = 0; i < $scope.checkboxAudiences.length; i++) {
    		if ($scope.checkboxAudiences[i].audience == aud.audience)
    			return i;
    	}
    }

    $scope.allChecked = function(aud) {
    	var j = getIndex(aud);
    	$scope.checkboxAudiences[j].checked = !$scope.checkboxAudiences[j].checked;
    	for (var i = 0; i < $scope.checkboxAudiences.length; i++) {
            if (!$scope.checkboxAudiences[i].checked){
            	$scope.selectAll = false;
            	return;
            }
        }
        $scope.selectAll = true;
    }
});