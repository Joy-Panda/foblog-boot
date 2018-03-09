/**
 * Created by Pan on 2016/9/11.
 */
app.service("SearchService", function (RequestService) {
    this.search = function (param) {
        return RequestService.getRequest('/search/' + param, cfg_form);
    };
    this.searchA = function (param) {
        return RequestService.getRequest('/search/article/' + param, cfg_form);
    };
    this.searchF = function (param) {
        return RequestService.getRequest('/search/friendlink/' + param, cfg_form);
    };
    this.searchP = function (param) {
        return RequestService.getRequest('/search/project/' + param, cfg_form);
    };
    this.searchR = function (param) {
        return RequestService.getRequest('/search/recommend/' + param, cfg_form);
    };
});
