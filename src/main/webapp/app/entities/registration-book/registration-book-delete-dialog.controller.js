(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('RegistrationBookDeleteController',RegistrationBookDeleteController);

    RegistrationBookDeleteController.$inject = ['$uibModalInstance', 'entity', 'RegistrationBook'];

    function RegistrationBookDeleteController($uibModalInstance, entity, RegistrationBook) {
        var vm = this;

        vm.registrationBook = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RegistrationBook.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
