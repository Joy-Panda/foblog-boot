app.service("RecommendService", function(RequestService) {
	this.list = function (pageNum, pageSize) {
        return RequestService.postRequest('/recommend' ,$.param({
        	"pageNum": pageNum,
        	"pageSize":pageSize
        	}), cfg_form);
    };
    this.hits = function(id){
        return RequestService.postRequest('/recommend/hits' ,$.param({
            "id": id
        }), cfg_form);
    }

})