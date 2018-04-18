(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ThirdLevelDepartmentDetailController', ThirdLevelDepartmentDetailController);

    ThirdLevelDepartmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ThirdLevelDepartment', 'ConsultRoom', 'Doctor', 'SecondLevelDepartment'];

    function ThirdLevelDepartmentDetailController($scope, $rootScope, $stateParams, previousState, entity, ThirdLevelDepartment, ConsultRoom, Doctor, SecondLevelDepartment) {
        var vm = this;

        vm.thirdLevelDepartment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationBookApp:thirdLevelDepartmentUpdate', function(event, result) {
            vm.thirdLevelDepartment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
