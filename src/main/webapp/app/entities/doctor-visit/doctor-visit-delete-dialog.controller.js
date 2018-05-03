(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('DoctorVisitDeleteController',DoctorVisitDeleteController);

    DoctorVisitDeleteController.$inject = ['$uibModalInstance', 'entity', 'DoctorVisit'];

    function DoctorVisitDeleteController($uibModalInstance, entity, DoctorVisit) {
        var vm = this;

        vm.doctorVisit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DoctorVisit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
