/**
 *
 */
package com.dailydealsbox.web.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;
import com.dailydealsbox.web.database.service.AuthorizationService;

/**
 * @author x_ye
 */
//@Component
public class MyFilterImpl extends OncePerRequestFilter {
  @Autowired
  AuthorizationService authService;

  /*
   * (non-Javadoc)
   * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
   * javax.servlet.FilterChain)
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // run for api
    if (StringUtils.startsWithIgnoreCase(request.getRequestURI(), "/api/")) {
      //request.getRequestDispatcher("/api/profile111").forward(request, response);

      // uri
      System.out.println(request.getRequestURI());

      // attributes
      List<String> attributes = Collections.list(request.getAttributeNames());
      System.out.println(attributes);

      // parameters
      List<String> names = Collections.list(request.getParameterNames());
      System.out.println(names);

      // cookies
      Cookie[] cookies = request.getCookies();
      AuthorizationToken token = null;
      for (Cookie cookie : cookies) {
        System.out.println(cookie.getName() + "=" + cookie.getValue());
        if (StringUtils.equalsIgnoreCase("token", cookie.getName())) {
          token = this.authService.verify(cookie.getValue());
        }
      }
      if (token != null) {
        request.setAttribute(BaseAuthorization.TOKEN, token);
      }

      // response
      System.out.println(response);
    }
    filterChain.doFilter(request, response);
  }

}
