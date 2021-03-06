(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('RegistrationBookDialogController', RegistrationBookDialogController);

    RegistrationBookDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity',
         'DoctorDoctorVisit', 'RegistrationBook','AlertService','Principal',
        'ThirdLevelDepartment', '$resource'];

    function RegistrationBookDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity,
                                               DoctorDoctorVisit, RegistrationBook,AlertService,Principal,
                                               ThirdLevelDepartment, $resource) {
        var vm = this;
        // vm.thirdLevelDepartment.doctor = "";
        vm.registrationBook = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.thirdLevelDepartments = ThirdLevelDepartment.query();


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.registrationBook.deptId = vm.thirdLevelDepartment.id;
            vm.registrationBook.deptName = vm.thirdLevelDepartment.deptName;
            vm.registrationBook.doctorId = vm.thirdLevelDepartment.doctor.id;
            vm.registrationBook.doctorName = vm.thirdLevelDepartment.doctor.fullName;
            vm.registrationBook.consultId = vm.doctorVisit.roomId;
            vm.registrationBook.consultName = vm.doctorVisit.roomName;
            vm.registrationBook.consultNo = vm.doctorVisit.roomNo;
            vm.registrationBook.visitDateTime = vm.doctorVisit.visitData;

            if (vm.registrationBook.id !== null) {
                RegistrationBook.update(vm.registrationBook, onSaveSuccess, onSaveError);
            } else {
                RegistrationBook.save(vm.registrationBook, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationBookApp:registrationBookUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.visitDateTime = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.getDoctorVisit = function () {
            vm.doctorVisits = DoctorDoctorVisit.query({doctorId: vm.thirdLevelDepartment.doctor.id});
        }
    }
})();
