(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ThirdLevelDepartmentDeleteController',ThirdLevelDepartmentDeleteController);

    ThirdLevelDepartmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThirdLevelDepartment'];

    function ThirdLevelDepartmentDeleteController($uibModalInstance, entity, ThirdLevelDepartment) {
        var vm = this;

        vm.thirdLevelDepartment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ThirdLevelDepartment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
