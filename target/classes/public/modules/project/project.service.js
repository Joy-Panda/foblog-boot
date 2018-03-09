app.service("ProjectService",function(RequestService){
    this.list = function (pageNum, pageSize) {
        return RequestService.getRequest('/project' , cfg_form);
    };
    this.hits = function(id){
        return RequestService.postRequest('/project/hits' ,$.param({
            "id": id
        }), cfg_form);
    }
})