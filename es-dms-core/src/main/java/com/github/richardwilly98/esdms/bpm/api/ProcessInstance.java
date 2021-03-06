package com.github.richardwilly98.esdms.bpm.api;

/*
 * #%L
 * es-dms-core
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


import java.util.Date;
import java.util.Set;

import com.github.richardwilly98.esdms.api.ItemBase;

public interface ProcessInstance extends ItemBase {

    public abstract Date getStart();

    public abstract void setStart(Date start);

    public abstract Date getModified();

    public abstract void setModified(Date modified);

    public abstract Date getEnd();

    public abstract void setEnd(Date end);

    public abstract String getStatus();

    public abstract void setStatus(String status);

    public abstract String getProcessDefinitionId();

    public abstract void setProcessDefinitionId(String processDefinitionId);

    public abstract String getInitiator();

    public abstract void setInitiator(String initiator);

    public abstract String getOwner();

    public abstract void setOwner(String owner);

    public abstract Set<Object> getVariables();

}