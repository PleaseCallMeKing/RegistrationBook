(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('second-level-department', {
            parent: 'entity',
            url: '/second-level-department?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.secondLevelDepartment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/second-level-department/second-level-departments.html',
                    controller: 'SecondLevelDepartmentController',
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
                    $translatePartialLoader.addPart('secondLevelDepartment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('second-level-department-detail', {
            parent: 'second-level-department',
            url: '/second-level-department/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.secondLevelDepartment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/second-level-department/second-level-department-detail.html',
                    controller: 'SecondLevelDepartmentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('secondLevelDepartment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SecondLevelDepartment', function($stateParams, SecondLevelDepartment) {
                    return SecondLevelDepartment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'second-level-department',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('second-level-department-detail.edit', {
            parent: 'second-level-department-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/second-level-department/second-level-department-dialog.html',
                    controller: 'SecondLevelDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SecondLevelDepartment', function(SecondLevelDepartment) {
                            return SecondLevelDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('second-level-department.new', {
            parent: 'second-level-department',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/second-level-department/second-level-department-dialog.html',
                    controller: 'SecondLevelDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                deptName: null,
                                deptEnglishName: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('second-level-department', null, { reload: 'second-level-department' });
                }, function() {
                    $state.go('second-level-department');
                });
            }]
        })
        .state('second-level-department.edit', {
            parent: 'second-level-department',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/second-level-department/second-level-department-dialog.html',
                    controller: 'SecondLevelDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SecondLevelDepartment', function(SecondLevelDepartment) {
                            return SecondLevelDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('second-level-department', null, { reload: 'second-level-department' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('second-level-department.delete', {
            parent: 'second-level-department',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/second-level-department/second-level-department-delete-dialog.html',
                    controller: 'SecondLevelDepartmentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SecondLevelDepartment', function(SecondLevelDepartment) {
                            return SecondLevelDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('second-level-department', null, { reload: 'second-level-department' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
