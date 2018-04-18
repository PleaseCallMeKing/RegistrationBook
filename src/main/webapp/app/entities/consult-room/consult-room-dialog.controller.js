(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ConsultRoomDialogController', ConsultRoomDialogController);

    ConsultRoomDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ConsultRoom', 'ThirdLevelDepartment'];

    function ConsultRoomDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ConsultRoom, ThirdLevelDepartment) {
        var vm = this;

        vm.consultRoom = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.thirdleveldepartments = ThirdLevelDepartment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.consultRoom.id !== null) {
                ConsultRoom.update(vm.consultRoom, onSaveSuccess, onSaveError);
            } else {
                ConsultRoom.save(vm.consultRoom, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationBookApp:consultRoomUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
