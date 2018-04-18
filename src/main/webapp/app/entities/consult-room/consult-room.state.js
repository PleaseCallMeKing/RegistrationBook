(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('consult-room', {
            parent: 'entity',
            url: '/consult-room?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.consultRoom.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/consult-room/consult-rooms.html',
                    controller: 'ConsultRoomController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('consultRoom');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('consult-room-detail', {
            parent: 'consult-room',
            url: '/consult-room/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.consultRoom.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/consult-room/consult-room-detail.html',
                    controller: 'ConsultRoomDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('consultRoom');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ConsultRoom', function($stateParams, ConsultRoom) {
                    return ConsultRoom.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'consult-room',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('consult-room-detail.edit', {
            parent: 'consult-room-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consult-room/consult-room-dialog.html',
                    controller: 'ConsultRoomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConsultRoom', function(ConsultRoom) {
                            return ConsultRoom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('consult-room.new', {
            parent: 'consult-room',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consult-room/consult-room-dialog.html',
                    controller: 'ConsultRoomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                consultRoomName: null,
                                consultRoomNo: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('consult-room', null, { reload: 'consult-room' });
                }, function() {
                    $state.go('consult-room');
                });
            }]
        })
        .state('consult-room.edit', {
            parent: 'consult-room',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consult-room/consult-room-dialog.html',
                    controller: 'ConsultRoomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConsultRoom', function(ConsultRoom) {
                            return ConsultRoom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('consult-room', null, { reload: 'consult-room' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('consult-room.delete', {
            parent: 'consult-room',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consult-room/consult-room-delete-dialog.html',
                    controller: 'ConsultRoomDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ConsultRoom', function(ConsultRoom) {
                            return ConsultRoom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('consult-room', null, { reload: 'consult-room' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
