package com.wcytk.controller;



import com.wcytk.Util.Util;
import com.wcytk.entity.User;
import com.wcytk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
@SessionAttributes(value = {"user"})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loginRequest", method = RequestMethod.POST)
    public String loginRequest(String name, String passwd, String remember, ModelMap session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        Util.MD5 md5 = new Util.MD5();
        passwd = md5.encryptMD5(passwd);

        User user = userService.SelectUser(name, passwd);

        if (user != null) {
            session.addAttribute("user", user);
            if(remember == null) {
                remember = "0";
            }
            if (remember.equals("1")) {
                String username = URLEncoder.encode(user.getName(), "GBK");
                Cookie cookie1 = new Cookie("name", username);
                Cookie cookie2 = new Cookie("passwd", passwd);
                cookie1.setMaxAge(60 * 60 * 24 * 7);
                cookie2.setMaxAge(60 * 60 * 24 * 7);
                cookie1.setPath("/");
                cookie2.setPath("/");
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            } else {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("name")|| cookie.getName().equals("passwd")){
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }
            return "system/index";
        }
        return "login";
    }

    @RequestMapping(value = "/registerRequest", method = RequestMethod.POST)
    public String registerRequest(String name, String passwd, String checkTeacher) {

        int teacher = 0;
        if (checkTeacher.equals("teacher")) {
            teacher = 1;
        }

        Util.MD5 md5 = new Util.MD5();
        passwd = md5.encryptMD5(passwd);

        boolean success = userService.AddUser(name, passwd, teacher);
        if (success) {
            return "login";
        }
        return "register";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(ModelMap model, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            String cookieName = "";
            String cookiePasswd = "";
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("name")){
                    cookieName = URLDecoder.decode(cookie.getValue(), "GBK");
                }
                if(cookie.getName().equals("passwd")){
                    cookiePasswd = cookie.getValue();
                }
            }
            if(!cookieName.equals("") && !cookiePasswd.equals("")) {
                User user = userService.SelectUser(cookieName, cookiePasswd);
                if(user != null) {
                    model.addAttribute("user", user);
                    return "system/index";
                }
            }
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "system/index";
        }
        return "login";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(HttpServletRequest request, HttpServletResponse response, SessionStatus sessionStatus) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("name")|| cookie.getName().equals("passwd")){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        sessionStatus.setComplete();
        request.getSession().removeAttribute("user");
        return "system/index";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String register(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return "register";
    }

    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    public String index(HttpServletRequest request, ModelMap session) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            String cookieName = "";
            String cookiePasswd = "";
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("name")){
                    cookieName = URLDecoder.decode(cookie.getValue(), "GBK");
                }
                if(cookie.getName().equals("passwd")){
                    cookiePasswd = cookie.getValue();
                }
            }
            if(!cookieName.equals("") && !cookiePasswd.equals("")) {
                User user = userService.SelectUser(cookieName, cookiePasswd);
                if(user != null) {
                    session.addAttribute("user", user);
                }
            }
        }
        return "system/index";
    }
}
