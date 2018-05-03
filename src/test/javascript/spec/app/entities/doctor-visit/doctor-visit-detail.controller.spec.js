'use strict';

describe('Controller Tests', function() {

    describe('DoctorVisit Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDoctorVisit, MockDoctor, MockConsultRoom;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDoctorVisit = jasmine.createSpy('MockDoctorVisit');
            MockDoctor = jasmine.createSpy('MockDoctor');
            MockConsultRoom = jasmine.createSpy('MockConsultRoom');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DoctorVisit': MockDoctorVisit,
                'Doctor': MockDoctor,
                'ConsultRoom': MockConsultRoom
            };
            createController = function() {
                $injector.get('$controller')("DoctorVisitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'registrationBookApp:doctorVisitUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
