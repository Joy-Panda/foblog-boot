package studio.baxia.fo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.baxia.fo.common.CommonConstant;
import studio.baxia.fo.common.CommonResult;
import studio.baxia.fo.pojo.Authors;
import studio.baxia.fo.service.IUserService;
import studio.baxia.fo.util.ExecuteSecurity;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "")
public class InfoManageController {

    @Autowired
    private IUserService userService;

    @ExecuteSecurity
    @ResponseBody
    @RequestMapping(value = "/manage/author", method = {RequestMethod.PUT})
    public CommonResult updateInfo(HttpServletRequest request, @RequestBody Authors info) {
        Boolean result = userService.updateInfo(request, info);
        return new CommonResult(CommonConstant.SUCCESS_CODE, "",
                result);
    }

}
