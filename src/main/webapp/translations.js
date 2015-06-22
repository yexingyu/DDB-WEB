angular.module('gettext').run(['gettextCatalog', function (gettextCatalog) {
/* jshint -W100 */
    gettextCatalog.setStrings('fr', {"This is English.{{val}}":"Ceci est l'anglais.{{val}}","We want to test globalization and localization.":"Nous voulons tester la mondialisation et de la localisation."});
/* jshint +W100 */
}]);