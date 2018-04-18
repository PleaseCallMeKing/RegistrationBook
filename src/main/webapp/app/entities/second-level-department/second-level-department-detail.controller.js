(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('SecondLevelDepartmentDetailController', SecondLevelDepartmentDetailController);

    SecondLevelDepartmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SecondLevelDepartment', 'ThirdLevelDepartment'];

    function SecondLevelDepartmentDetailController($scope, $rootScope, $stateParams, previousState, entity, SecondLevelDepartment, ThirdLevelDepartment) {
        var vm = this;

        vm.secondLevelDepartment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationBookApp:secondLevelDepartmentUpdate', function(event, result) {
            vm.secondLevelDepartment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
