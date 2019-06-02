package com.wcytk.controller;


import com.wcytk.entity.Teacher;
import com.wcytk.entity.User;
import com.wcytk.service.UserService;
import com.wcytk.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Controller
@SessionAttributes(value = {"user", "teacher"})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loginRequest", method = RequestMethod.POST)
    public String loginRequest(String strId, String passwd, String remember, ModelMap session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        if (isInteger(strId)) {
            passwd = Md5.MD5(passwd);

            if(strId.length() == 8) {
                Teacher teacher = userService.selectTeacher(Integer.parseInt(strId), passwd);
                if(teacher != null) {
                    session.addAttribute("teacher", teacher);
                    if (remember == null) {
                        remember = "0";
                    }
                    if (remember.equals("1")) {
                        Cookie cookie1 = new Cookie("id", strId);
                        Cookie cookie2 = new Cookie("passwd", passwd);
                        cookie1.setMaxAge(60 * 60 * 24 * 7);
                        cookie2.setMaxAge(60 * 60 * 24 * 7);
                        cookie1.setPath("/");
                        cookie2.setPath("/");
                        response.addCookie(cookie1);
                        response.addCookie(cookie2);
                    } else {
                        invalidateCookie(request, response);
                    }
                    return "system/index";
                }
            } else if(strId.length() == 10) {
                User user = userService.SelectStudent(Integer.parseInt(strId), passwd);
                if (user != null) {
                    session.addAttribute("user", user);
                    if (remember == null) {
                        remember = "0";
                    }
                    if (remember.equals("1")) {
                        Cookie cookie1 = new Cookie("id", strId);
                        Cookie cookie2 = new Cookie("passwd", passwd);
                        cookie1.setMaxAge(60 * 60 * 24 * 7);
                        cookie2.setMaxAge(60 * 60 * 24 * 7);
                        cookie1.setPath("/");
                        cookie2.setPath("/");
                        response.addCookie(cookie1);
                        response.addCookie(cookie2);
                    } else {
                        invalidateCookie(request, response);
                    }
                    return "system/index";
                }
            }
        }
        return "login";
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private void invalidateCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name") || cookie.getName().equals("passwd")) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }

    private int getRandom() {
        return (int) (Math.random() * 10);
    }

    @RequestMapping(value = "/registerRequest", method = RequestMethod.POST)
    public String registerRequest(String name, String passwd, String checkTeacher) {
        String password = Md5.MD5(passwd);
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        if (checkTeacher.equals("teacher")) {
            int teacher_id;

            while (true) {
                String id = year.format(new Date()) + getRandom() + getRandom() + getRandom() + getRandom();
                if (userService.selectTeacherId(Integer.parseInt(id)) == null) {
                    teacher_id = Integer.parseInt(id);
                    break;
                }
            }

            boolean success = userService.addTeacher(name, password, teacher_id);
            if (success) {
                return "login";
            }
        } else {
            int student_id;

            while (true) {
                String id = year.format(new Date()) + getRandom() + getRandom() + getRandom() + getRandom() + getRandom() + getRandom();
                if (userService.selectStudentId(Integer.parseInt(id)) == null) {
                    student_id = Integer.parseInt(id);
                    break;
                }
            }

            boolean success = userService.AddUser(name, password, student_id);
            if (success) {
                return "login";
            }
        }

        return "register";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(ModelMap model, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String cookieId = "";
            String cookiePasswd = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    cookieId = cookie.getValue();
                }
                if (cookie.getName().equals("passwd")) {
                    cookiePasswd = cookie.getValue();
                }
            }
            if (!cookieId.equals("") && !cookiePasswd.equals("")) {
                if (isInteger(cookieId)) {
                    if(cookieId.length() == 8) {
                        Teacher teacher = userService.selectTeacher(Integer.parseInt(cookieId), cookiePasswd);
                        if (teacher != null) {
                            model.addAttribute("teacher", teacher);
                            return "system/index";
                        }
                    } else if(cookieId.length() == 10) {
                        User user = userService.SelectStudent(Integer.parseInt(cookieId), cookiePasswd);
                        if (user != null) {
                            model.addAttribute("user", user);
                            return "system/index";
                        }
                    }
                }
            }
        }
        return "login";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(HttpServletRequest request, HttpServletResponse response, SessionStatus sessionStatus) {
        invalidateCookie(request, response);
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
        if (cookies != null) {
            String cookieId = "";
            String cookiePasswd = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("")) {
                    cookieId = cookie.getValue();
                }
                if (cookie.getName().equals("passwd")) {
                    cookiePasswd = cookie.getValue();
                }
            }
            if (!cookieId.equals("") && !cookiePasswd.equals("")) {
                if (isInteger(cookieId)) {
                    if(cookieId.length() == 8) {
                        Teacher teacher = userService.selectTeacher(Integer.parseInt(cookieId), cookiePasswd);
                        if (teacher != null) {
                            session.addAttribute("teacher", teacher);
                            return "system/index";
                        }
                    } else if(cookieId.length() == 10) {
                        User user = userService.SelectStudent(Integer.parseInt(cookieId), cookiePasswd);
                        if (user != null) {
                            session.addAttribute("user", user);
                            return "system/index";
                        }
                    }
                }
            }
        }
        return "system/index";
    }
}
