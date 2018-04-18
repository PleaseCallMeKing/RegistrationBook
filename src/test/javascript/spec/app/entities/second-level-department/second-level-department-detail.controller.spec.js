'use strict';

describe('Controller Tests', function() {

    describe('SecondLevelDepartment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSecondLevelDepartment, MockThirdLevelDepartment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSecondLevelDepartment = jasmine.createSpy('MockSecondLevelDepartment');
            MockThirdLevelDepartment = jasmine.createSpy('MockThirdLevelDepartment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SecondLevelDepartment': MockSecondLevelDepartment,
                'ThirdLevelDepartment': MockThirdLevelDepartment
            };
            createController = function() {
                $injector.get('$controller')("SecondLevelDepartmentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'registrationBookApp:secondLevelDepartmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
