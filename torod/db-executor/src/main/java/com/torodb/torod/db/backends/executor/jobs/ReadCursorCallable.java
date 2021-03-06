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

package com.torodb.torod.db.backends.executor.jobs;

import com.torodb.torod.core.cursors.CursorId;
import com.torodb.torod.core.dbWrapper.Cursor;
import com.torodb.torod.core.dbWrapper.DbWrapper;
import com.torodb.torod.core.exceptions.ToroException;
import com.torodb.torod.core.exceptions.ToroRuntimeException;
import com.torodb.torod.core.exceptions.UserToroException;
import com.torodb.torod.core.subdocument.SplitDocument;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

/**
 *
 */
public class ReadCursorCallable extends Job<List<? extends SplitDocument>> {

    private final DbWrapper dbWrapper;
    private final CursorId cursorId;
    private final int maxResult;
    private final Report report;
    private Cursor cursor = null;

    @Inject
    public ReadCursorCallable(
            DbWrapper dbWrapper,
            Report report,
            CursorId cursorId,
            int maxResult
    ) {
        this.dbWrapper = dbWrapper;
        this.cursorId = cursorId;
        this.maxResult = maxResult;
        this.report = report;
    }

    @Override
    protected List<? extends SplitDocument> failableCall() throws ToroException,
            ToroRuntimeException {
        try {
            cursor = dbWrapper.getGlobalCursor(cursorId);

            List<SplitDocument> result = cursor.readDocuments(maxResult);
            
            report.readCursorExecuted(cursorId, maxResult, Collections.unmodifiableList(result));
            
            return result;
        }
        catch (IllegalArgumentException ex) {
            throw new UserToroException(ex);
        }
    }

    @Override
    protected List<? extends SplitDocument> onFail(Throwable t) throws
            ToroException, ToroRuntimeException {
        if (cursor != null) {
            cursor.close();
        }
        if (t instanceof ToroException) {
            throw (ToroException) t;
        }
        throw new ToroRuntimeException(t);
    }
    
    public static interface Report {
        public void readCursorExecuted(
                CursorId cursorId, 
                int maxResult, 
                List<SplitDocument> result);
    }
}
