package studio.baxia.fo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.baxia.fo.common.CommonConstant;
import studio.baxia.fo.common.CommonResult;
import studio.baxia.fo.service.SearchService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/10.
 */
@RequestMapping(value = "")
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ResponseBody
    @RequestMapping(value = "/search/article/{param}", method = RequestMethod.GET)
    public CommonResult article(@PathVariable("param") String param) {
        List result = searchService.searchArticleByParam(param);
        return new CommonResult(CommonConstant.SUCCESS_CODE, null, result);
    }
    @ResponseBody
    @RequestMapping(value = "/search/project/{param}", method = RequestMethod.GET)
    public CommonResult project(@PathVariable("param") String param) {
        List result = searchService.searchProjectByParam(param);
        return new CommonResult(CommonConstant.SUCCESS_CODE, null, result);
    }
    @ResponseBody
    @RequestMapping(value = "/search/friendlink/{param}", method = RequestMethod.GET)
    public CommonResult friendlink(@PathVariable("param") String param) {
        List result = searchService.searchFriendlinkByParam(param);
        return new CommonResult(CommonConstant.SUCCESS_CODE, null, result);
    }
    @ResponseBody
    @RequestMapping(value = "/search/recommend/{param}", method = RequestMethod.GET)
    public CommonResult recommend(@PathVariable("param") String param) {
        List result = searchService.searchRecommendByParam(param);
        return new CommonResult(CommonConstant.SUCCESS_CODE, null, result);
    }
    @ResponseBody
    @RequestMapping(value = "/search/{param}", method = RequestMethod.GET)
    public CommonResult list(@PathVariable("param") String param) {
        List result = searchService.searchByParam(param);
        return new CommonResult(CommonConstant.SUCCESS_CODE, null, result);
    }
}
