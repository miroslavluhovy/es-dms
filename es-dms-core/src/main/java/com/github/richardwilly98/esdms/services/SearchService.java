package com.github.richardwilly98.esdms.services;

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

import java.util.List;
import java.util.Map;

import com.github.richardwilly98.esdms.api.ItemBase;
import com.github.richardwilly98.esdms.exception.ServiceException;
import com.github.richardwilly98.esdms.search.api.Facet;
import com.github.richardwilly98.esdms.search.api.FacetRequest;
import com.github.richardwilly98.esdms.search.api.SearchResult;

public interface SearchService<T extends ItemBase> {

    public abstract SearchResult<T> search(String criteria, int first, int pageSize) throws ServiceException;

    public abstract SearchResult<T> search(String criteria, int first, int pageSize, List<FacetRequest> facets) throws ServiceException;

    public abstract SearchResult<T> search(String criteria, int first, int pageSize, List<FacetRequest> facets, Map<String, Object> filters)
	    throws ServiceException;

    public abstract SearchResult<T> moreLikeThis(String criteria, int first, int pageSize, int minTermFrequency, int maxItems)
	    throws ServiceException;
    
    public abstract Facet suggestTags(String criteria, int size)
            throws ServiceException;
}