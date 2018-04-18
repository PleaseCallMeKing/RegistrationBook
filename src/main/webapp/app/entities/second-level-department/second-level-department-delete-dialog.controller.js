(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('SecondLevelDepartmentDeleteController',SecondLevelDepartmentDeleteController);

    SecondLevelDepartmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'SecondLevelDepartment'];

    function SecondLevelDepartmentDeleteController($uibModalInstance, entity, SecondLevelDepartment) {
        var vm = this;

        vm.secondLevelDepartment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SecondLevelDepartment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
