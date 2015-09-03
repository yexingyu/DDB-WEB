/**
 *
 */
package com.dailydealsbox.database.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.web.base.BaseAuthorization;

/**
 * @author x_ye
 */
@Service
@Transactional
public class AuthorizationServiceImpl extends BaseAuthorization implements AuthorizationService {

}
