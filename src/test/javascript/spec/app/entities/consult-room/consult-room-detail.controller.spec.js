'use strict';

describe('Controller Tests', function() {

    describe('ConsultRoom Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConsultRoom, MockThirdLevelDepartment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConsultRoom = jasmine.createSpy('MockConsultRoom');
            MockThirdLevelDepartment = jasmine.createSpy('MockThirdLevelDepartment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ConsultRoom': MockConsultRoom,
                'ThirdLevelDepartment': MockThirdLevelDepartment
            };
            createController = function() {
                $injector.get('$controller')("ConsultRoomDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'registrationBookApp:consultRoomUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
