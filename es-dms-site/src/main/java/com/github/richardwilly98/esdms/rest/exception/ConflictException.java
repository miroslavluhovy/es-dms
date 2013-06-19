package com.github.richardwilly98.esdms.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class ConflictException extends WebApplicationException {

	public ConflictException(String message) {
		super(Response.status(Response.Status.BAD_REQUEST)
	             .entity(message).type(MediaType.TEXT_PLAIN).build());
	}
}