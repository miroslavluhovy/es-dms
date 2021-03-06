package com.github.richardwilly98.esdms.shiro;

/*
 * #%L
 * es-dms-site
 * %%
 * Copyright (C) 2013 es-dms
 * %%
 * Copyright 2012-2013 Richard Louapre
 * 
 * This file is part of ES-DMS.
 * 
 * The current version of ES-DMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * ES-DMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import com.github.richardwilly98.esdms.inject.SystemParametersModule;
import com.github.richardwilly98.esdms.rest.RestAuthenticationService;
import com.google.inject.Inject;

public class EsWebSessionManager extends DefaultWebSessionManager {

    @Override
    public long getGlobalSessionTimeout() {
        log.debug("getGlobalSessionTimeout - " + super.getGlobalSessionTimeout());
        return super.getGlobalSessionTimeout();
    }

    private static Logger log = Logger.getLogger(EsWebSessionManager.class);

    @Inject
    public EsWebSessionManager(SessionDAO sessionDAO, @Named(SystemParametersModule.SESSION_TIMEOUT) final long sessionTimeout) {
        super();
        this.setSessionDAO(sessionDAO);
        Cookie cookie = new SimpleCookie(RestAuthenticationService.ES_DMS_TICKET);
        cookie.setHttpOnly(true);
        setSessionIdCookie(cookie);
        setSessionIdCookieEnabled(true);
        setGlobalSessionTimeout(sessionTimeout);
        getGlobalSessionTimeout();
    }

}
