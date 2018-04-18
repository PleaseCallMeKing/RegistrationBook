(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('third-level-department', {
            parent: 'entity',
            url: '/third-level-department?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.thirdLevelDepartment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/third-level-department/third-level-departments.html',
                    controller: 'ThirdLevelDepartmentController',
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
                    $translatePartialLoader.addPart('thirdLevelDepartment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('third-level-department-detail', {
            parent: 'third-level-department',
            url: '/third-level-department/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.thirdLevelDepartment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/third-level-department/third-level-department-detail.html',
                    controller: 'ThirdLevelDepartmentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('thirdLevelDepartment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ThirdLevelDepartment', function($stateParams, ThirdLevelDepartment) {
                    return ThirdLevelDepartment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'third-level-department',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('third-level-department-detail.edit', {
            parent: 'third-level-department-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/third-level-department/third-level-department-dialog.html',
                    controller: 'ThirdLevelDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThirdLevelDepartment', function(ThirdLevelDepartment) {
                            return ThirdLevelDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('third-level-department.new', {
            parent: 'third-level-department',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/third-level-department/third-level-department-dialog.html',
                    controller: 'ThirdLevelDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                deptName: null,
                                deptEnglishName: null,
                                appointmentable: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('third-level-department', null, { reload: 'third-level-department' });
                }, function() {
                    $state.go('third-level-department');
                });
            }]
        })
        .state('third-level-department.edit', {
            parent: 'third-level-department',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/third-level-department/third-level-department-dialog.html',
                    controller: 'ThirdLevelDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThirdLevelDepartment', function(ThirdLevelDepartment) {
                            return ThirdLevelDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('third-level-department', null, { reload: 'third-level-department' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('third-level-department.delete', {
            parent: 'third-level-department',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/third-level-department/third-level-department-delete-dialog.html',
                    controller: 'ThirdLevelDepartmentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThirdLevelDepartment', function(ThirdLevelDepartment) {
                            return ThirdLevelDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('third-level-department', null, { reload: 'third-level-department' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
