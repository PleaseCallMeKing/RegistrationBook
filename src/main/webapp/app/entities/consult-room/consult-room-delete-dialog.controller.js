(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ConsultRoomDeleteController',ConsultRoomDeleteController);

    ConsultRoomDeleteController.$inject = ['$uibModalInstance', 'entity', 'ConsultRoom'];

    function ConsultRoomDeleteController($uibModalInstance, entity, ConsultRoom) {
        var vm = this;

        vm.consultRoom = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ConsultRoom.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
