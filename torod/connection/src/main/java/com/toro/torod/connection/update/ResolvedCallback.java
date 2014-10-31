/*
 *     This file is part of ToroDB.
 *
 *     ToroDB is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ToroDB is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with ToroDB. If not, see <http://www.gnu.org/licenses/>.
 *
 *     Copyright (c) 2014, 8Kdata Technology
 *     
 */

package com.toro.torod.connection.update;

/*
 * #%L
 * Torod: Connection
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2014 8Kdata
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.torodb.kvdocument.values.ArrayValue;
import com.torodb.kvdocument.values.DocValue;
import com.torodb.kvdocument.values.ObjectValue;

/**
 *
 */
public interface ResolvedCallback<R> {
    
    public <K> R objectReferenced(
            BuilderCallback<K> parentBuilder, 
            K key,
            ObjectValue.Builder child
    );
    
    public <K> R arrayReferenced(
            BuilderCallback<K> parentBuilder, 
            K key,
            ArrayValue.Builder child
    );
    
    public <K> R valueReferenced(
            BuilderCallback<K> parentBuilder, 
            K key,
            DocValue child
    );
    
    public <K> R newElementReferenced(
            BuilderCallback<K> parentBuilder, 
            K key
    );

}