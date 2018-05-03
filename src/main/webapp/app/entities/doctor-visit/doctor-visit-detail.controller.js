(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('DoctorVisitDetailController', DoctorVisitDetailController);

    DoctorVisitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DoctorVisit', 'Doctor', 'ConsultRoom'];

    function DoctorVisitDetailController($scope, $rootScope, $stateParams, previousState, entity, DoctorVisit, Doctor, ConsultRoom) {
        var vm = this;

        vm.doctorVisit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationBookApp:doctorVisitUpdate', function(event, result) {
            vm.doctorVisit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
