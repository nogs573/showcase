package com.group3.movieguide.Persistence.hsqldb;

/**
 * We throw this exception when there is unexpected behavior in our persistence layer.
 * We wrap java.sql.SQLException in an unchecked java.lang.RuntimeExceptionso we can
 * throw them around, but not *have* to catch them if we don't want to.
 */
public class PersistenceException extends RuntimeException
{
    public PersistenceException(final Exception cause)
    {
        super(cause);
    }
}
