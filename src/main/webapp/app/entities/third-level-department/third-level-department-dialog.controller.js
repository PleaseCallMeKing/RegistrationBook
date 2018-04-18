(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ThirdLevelDepartmentDialogController', ThirdLevelDepartmentDialogController);

    ThirdLevelDepartmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThirdLevelDepartment', 'ConsultRoom', 'Doctor', 'SecondLevelDepartment'];

    function ThirdLevelDepartmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThirdLevelDepartment, ConsultRoom, Doctor, SecondLevelDepartment) {
        var vm = this;

        vm.thirdLevelDepartment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.consultrooms = ConsultRoom.query();
        vm.doctors = Doctor.query();
        vm.secondleveldepartments = SecondLevelDepartment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.thirdLevelDepartment.id !== null) {
                ThirdLevelDepartment.update(vm.thirdLevelDepartment, onSaveSuccess, onSaveError);
            } else {
                ThirdLevelDepartment.save(vm.thirdLevelDepartment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationBookApp:thirdLevelDepartmentUpdate', result);
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
