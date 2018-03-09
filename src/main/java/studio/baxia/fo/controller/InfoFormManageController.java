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
import studio.baxia.fo.vo.AuthorVo;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping(value = "")
public class InfoFormManageController {

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/author", method = {RequestMethod.GET})
    public CommonResult about(HttpServletRequest request) {
        Authors author = userService.getAuthor(CommonConstant.AUTHOR_ID);
        return new CommonResult(CommonConstant.SUCCESS_CODE, "",
                author);
    }

    @ExecuteSecurity
    @ResponseBody
    @RequestMapping(value = "/manage/author", method = {RequestMethod.GET})
    public CommonResult getInfo(HttpServletRequest request) {
        Authors author = null;
        author = userService.getInfo(request);
        return new CommonResult(CommonConstant.SUCCESS_CODE, "",
                author);
    }

}
