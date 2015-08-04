/**
 *
 */
package com.dailydealsbox.web.controller;

import java.util.Calendar;
import java.util.Collections;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.MemberEmail;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_LOGIN_TYPE;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MailService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/credential")
@Api(value = "Credential", description = "Credential Api.")
public class CredentialController {

  @Resource
  MemberService        memberService;
  @Resource
  MailService          mailService;
  @Resource
  AuthorizationService authorizationService;

  /**
   * checkCookie
   *
   * @param tokenString
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Check credential", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Check credential.")
  @DDBAuthorization
  public GenericResponseData checkCookie(HttpServletRequest request) throws Exception {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, token);
  }

  @RequestMapping(value = "facebook", method = RequestMethod.POST)
  @ApiOperation(value = "facebook credential", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Facebook credential.")
  public GenericResponseData facebook(@ApiParam(value = "accessToken", required = true) @RequestBody String accessToken, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Facebook facebook = new FacebookTemplate(accessToken);
    if (facebook.isAuthorized()) {
      // retrieve user profile from facebook
      User me = facebook.userOperations().getUserProfile();

      // save member
      Member member = this.memberService.getByAccount(me.getEmail());
      if (member == null) {
        member = new Member();
        member.setAccount(me.getEmail());
        member.setFirstName(me.getFirstName());
        member.setLastName(me.getLastName());
        member.setRole(MEMBER_ROLE.MEMBER);
        member.setLoginType(MEMBER_LOGIN_TYPE.FACEBOOK);
        MemberEmail email = new MemberEmail();
        email.setEmail(me.getEmail());
        email.setPrimary(true);
        email.setVerified(true);
        email.setVerifiedAt(Calendar.getInstance().getTime());
        email.setVerifiedIp(request.getRemoteAddr());
        email.generateHashCode();
        member.setEmails(Collections.singleton(email));
        this.memberService.insert(member);
      }

      // set cookie
      int expiry = -1;
      boolean rememberMe = true;
      if (rememberMe) {
        expiry = (int) (BaseAuthorization.EXPIRY / 1000);
      }
      Cookie cookie = this.authorizationService
          .buildCookie(AuthorizationToken.newInstance(member.getId(), member.getAccount(), member.getPassword(), this.authorizationService.buildExpiredStamp(), member.getRole()), expiry);
      if (cookie == null) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "002");
      } else {
        response.addCookie(cookie);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, member);
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "001");
    }
  }

  /**
   * verifyEmail
   *
   * @param hashCode
   * @param request
   * @return
   */
  @RequestMapping(value = "verify_email", method = RequestMethod.POST)
  @ApiOperation(value = "email verification", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Email verification.")
  public GenericResponseData verifyEmail(@ApiParam(value = "hash_code", required = true) @RequestBody String hashCode, HttpServletRequest request) {
    if (StringUtils.isBlank(hashCode) || hashCode.length() != 32) {
      GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
    }
    MemberEmail email = this.mailService.verifyEmail(hashCode, request.getRemoteAddr());
    if (email == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "002");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, email);
    }
  }

  /**
   * sendEmailVerification
   *
   * @param email
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "verify_email", method = RequestMethod.GET)
  @ApiOperation(value = "send email verification", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Request send email verification.")
  @DDBAuthorization
  public GenericResponseData sendEmailVerification(@ApiParam(value = "email", required = true) @RequestParam(value = "email", required = true) String email, HttpServletRequest request)
      throws Exception {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member me = this.memberService.get(token.getMemberId());

    // if no this member in database, return error(code=001)
    if (me == null) { return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001"); }

    // if no emails in database, return no_permission(code=002)
    if (me.getEmails() == null) { return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "002"); }

    MemberEmail verifyEmail = null;
    for (MemberEmail objEmail : me.getEmails()) {
      if (StringUtils.equalsIgnoreCase(objEmail.getEmail(), email)) {
        verifyEmail = objEmail;
        break;
      }
    }

    // if email is not belong to this member, return no_permission(code=003)
    if (verifyEmail == null) { return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "003"); }

    this.mailService.sendVerifyEmail(verifyEmail);
    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Success");
  }

  /**
   * register
   *
   * @param member
   * @return
   */
  @RequestMapping(value = "register", method = RequestMethod.POST)
  @ApiOperation(value = "new credential", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "new credential.")
  public GenericResponseData register(@ApiParam(value = "member object", required = true) @RequestBody Member member) throws Exception {
    // fix member data
    if (member.getMiddleName() == null) {
      member.setMiddleName("");
    }
    if (member.getLoginType() == null) {
      member.setLoginType(MEMBER_LOGIN_TYPE.DAILYDEALSBOX);
    }
    member.setRole(MEMBER_ROLE.MEMBER);

    // inserting member
    Member memberFromDb = this.memberService.insert(member);
    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, memberFromDb);
  }

  /**
   * login
   *
   * @param response
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Login", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Login.")
  public GenericResponseData login(@ApiParam(value = "member object", required = true) @RequestBody Member member,
      @ApiParam(value = "remember me", required = false, defaultValue = "false") @RequestParam(value = "rememberMe", required = false, defaultValue = "false") boolean rememberMe,
      HttpServletResponse response) throws Exception {
    Member member_from_db = this.memberService.getByAccount(member.getAccount());
    if (member_from_db != null && StringUtils.equals(member_from_db.getPassword(), member.getPassword())) {
      int expiry = -1;
      if (rememberMe) {
        expiry = (int) (BaseAuthorization.EXPIRY / 1000);
      }
      Cookie cookie = this.authorizationService.buildCookie(
          AuthorizationToken.newInstance(member_from_db.getId(), member_from_db.getAccount(), member_from_db.getPassword(), this.authorizationService.buildExpiredStamp(), member_from_db.getRole()),
          expiry);
      if (cookie == null) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
      } else {
        response.addCookie(cookie);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, member_from_db);
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "002");
    }
  }
}
