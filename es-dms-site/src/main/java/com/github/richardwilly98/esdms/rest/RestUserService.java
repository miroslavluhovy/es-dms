package com.github.richardwilly98.esdms.rest;

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

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.richardwilly98.esdms.api.User;
import com.github.richardwilly98.esdms.services.AuthenticationService;
import com.github.richardwilly98.esdms.services.HashService;
import com.github.richardwilly98.esdms.services.UserService;

@Path(RestUserService.USERS_PATH)
public class RestUserService extends RestItemBaseService<User> {

    public static final String USERS_PATH = "users";
    private final HashService hashService;

    @Inject
    public RestUserService(final AuthenticationService authenticationService, final HashService hashService, final UserService userService) {
	super(authenticationService, userService);
	this.hashService = hashService;
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User item) {
	checkNotNull(item);
	checkNotNull(item.getEmail());
	item.setId(item.getEmail());
	if (item.getPassword() != null) {
	    String encodedHash = hashService.toBase64(String.valueOf(item.getPassword()).getBytes());
	    log.trace("From service - hash: " + encodedHash);
	    item.setHash(encodedHash);
	    item.setPassword(null);
	} else {
	    log.warn("Missing password");
	}
	return super.create(item);
    }

    @Override
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, User item) {
	if (item.getPassword() != null) {
	    String encodedHash = hashService.toBase64(String.valueOf(item.getPassword()).getBytes());
	    log.trace("From service - hash: " + encodedHash);
	    item.setHash(encodedHash);
	    item.setPassword(null);
	}
	return super.update(id, item);
    }
}
