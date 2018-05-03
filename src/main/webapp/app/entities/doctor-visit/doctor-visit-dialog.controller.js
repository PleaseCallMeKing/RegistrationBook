(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('DoctorVisitDialogController', DoctorVisitDialogController);

    DoctorVisitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DoctorVisit', 'Doctor', 'ConsultRoom'];

    function DoctorVisitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DoctorVisit, Doctor, ConsultRoom) {
        var vm = this;

        vm.doctorVisit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.doctors = Doctor.query();
        vm.consultrooms = ConsultRoom.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.doctorVisit.id !== null) {
                DoctorVisit.update(vm.doctorVisit, onSaveSuccess, onSaveError);
            } else {
                DoctorVisit.save(vm.doctorVisit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationBookApp:doctorVisitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.visitData = false;
        vm.datePickerOpenStatus.visitEndData = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
