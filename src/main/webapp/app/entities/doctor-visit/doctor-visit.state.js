(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('doctor-visit', {
            parent: 'entity',
            url: '/doctor-visit?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.doctorVisit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/doctor-visit/doctor-visits.html',
                    controller: 'DoctorVisitController',
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
                    $translatePartialLoader.addPart('doctorVisit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('doctor-visit-detail', {
            parent: 'doctor-visit',
            url: '/doctor-visit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.doctorVisit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/doctor-visit/doctor-visit-detail.html',
                    controller: 'DoctorVisitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('doctorVisit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DoctorVisit', function($stateParams, DoctorVisit) {
                    return DoctorVisit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'doctor-visit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('doctor-visit-detail.edit', {
            parent: 'doctor-visit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/doctor-visit/doctor-visit-dialog.html',
                    controller: 'DoctorVisitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DoctorVisit', function(DoctorVisit) {
                            return DoctorVisit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('doctor-visit.new', {
            parent: 'doctor-visit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/doctor-visit/doctor-visit-dialog.html',
                    controller: 'DoctorVisitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                visitData: null,
                                visitEndData: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('doctor-visit', null, { reload: 'doctor-visit' });
                }, function() {
                    $state.go('doctor-visit');
                });
            }]
        })
        .state('doctor-visit.edit', {
            parent: 'doctor-visit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/doctor-visit/doctor-visit-dialog.html',
                    controller: 'DoctorVisitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DoctorVisit', function(DoctorVisit) {
                            return DoctorVisit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('doctor-visit', null, { reload: 'doctor-visit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('doctor-visit.delete', {
            parent: 'doctor-visit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/doctor-visit/doctor-visit-delete-dialog.html',
                    controller: 'DoctorVisitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DoctorVisit', function(DoctorVisit) {
                            return DoctorVisit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('doctor-visit', null, { reload: 'doctor-visit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
