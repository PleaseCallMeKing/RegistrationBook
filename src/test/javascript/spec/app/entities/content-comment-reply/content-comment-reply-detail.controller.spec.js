'use strict';

describe('Controller Tests', function() {

    describe('ContentCommentReply Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContentCommentReply, MockContentComment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContentCommentReply = jasmine.createSpy('MockContentCommentReply');
            MockContentComment = jasmine.createSpy('MockContentComment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ContentCommentReply': MockContentCommentReply,
                'ContentComment': MockContentComment
            };
            createController = function() {
                $injector.get('$controller')("ContentCommentReplyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'registrationBookApp:contentCommentReplyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
