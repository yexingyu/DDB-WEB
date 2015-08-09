/**
 *
 */
package com.dailydealsbox.web.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;

/**
 * @author x_ye
 */
@Component
public class DDBAuthorizationInterceptor implements HandlerInterceptor {

  @Autowired
  AuthorizationService authService;

  /*
   * (non-Javadoc)
   * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
   * java.lang.Object)
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

    if (handler instanceof ParameterizableViewController) { return true; }
    DDBAuthorization authAnnotation = ((HandlerMethod) handler).getMethod().getDeclaredAnnotation(DDBAuthorization.class);

    // anonymous
    request.setAttribute(BaseAuthorization.TOKEN, null);
    if (authAnnotation == null) { return true; }

    // cookies
    AuthorizationToken token = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (StringUtils.equalsIgnoreCase("token", cookie.getName())) {
          token = this.authService.verify(cookie.getValue());
        }
      }
    }

    if (authAnnotation.requireAuthorization()) {
      // authorization checking
      if (token == null) {
        request.getRequestDispatcher("/need_login").forward(request, response);
        return false;
      }

      // roles checking
      request.setAttribute(BaseAuthorization.TOKEN, token);
      if (!ArrayUtils.isEmpty(authAnnotation.value()) && !ArrayUtils.contains(authAnnotation.value(), token.getRole())) {
        request.getRequestDispatcher("/no_permission").forward(request, response);
        return false;
      }
    } else {
      // authorization checking
      if (token == null) {
        return true;
      } else {
        request.setAttribute(BaseAuthorization.TOKEN, token);
      }
    }

    return true;
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
   * java.lang.Object, org.springframework.web.servlet.ModelAndView)
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    //System.out.println("postHandler: ");
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
   * java.lang.Object, java.lang.Exception)
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    //System.out.println("afterCompletion: ");
  }
}
