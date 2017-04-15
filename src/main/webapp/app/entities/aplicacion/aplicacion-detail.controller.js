(function() {
    'use strict';

    angular
        .module('artefactCheckApp')
        .controller('AplicacionDetailController', AplicacionDetailController);

    AplicacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Aplicacion'];

    function AplicacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Aplicacion) {
        var vm = this;

        vm.aplicacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artefactCheckApp:aplicacionUpdate', function(event, result) {
            vm.aplicacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
