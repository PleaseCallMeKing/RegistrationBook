'use strict';

describe('Controller Tests', function() {

    describe('ThirdLevelDepartment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockThirdLevelDepartment, MockConsultRoom, MockDoctor, MockSecondLevelDepartment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockThirdLevelDepartment = jasmine.createSpy('MockThirdLevelDepartment');
            MockConsultRoom = jasmine.createSpy('MockConsultRoom');
            MockDoctor = jasmine.createSpy('MockDoctor');
            MockSecondLevelDepartment = jasmine.createSpy('MockSecondLevelDepartment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ThirdLevelDepartment': MockThirdLevelDepartment,
                'ConsultRoom': MockConsultRoom,
                'Doctor': MockDoctor,
                'SecondLevelDepartment': MockSecondLevelDepartment
            };
            createController = function() {
                $injector.get('$controller')("ThirdLevelDepartmentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'registrationBookApp:thirdLevelDepartmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
