(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('DoctorDetailController', DoctorDetailController);

    DoctorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Doctor', 'ThirdLevelDepartment'];

    function DoctorDetailController($scope, $rootScope, $stateParams, previousState, entity, Doctor, ThirdLevelDepartment) {
        var vm = this;

        vm.doctor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationBookApp:doctorUpdate', function(event, result) {
            vm.doctor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
