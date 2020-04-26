package com.phu.onlineshop;

public class Configuration
{
    private int pageSize;
    private int maxLogSize;
    private long maxLogFlushAttemp;

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(final int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getMaxLogSize()
    {
        return maxLogSize;
    }

    public void setMaxLogSize(final int maxLogSize)
    {
        this.maxLogSize = maxLogSize;
    }

    public long getMaxLogFlushAttemp()
    {
        return maxLogFlushAttemp;
    }

    public void setMaxLogFlushAttemp(final long maxLogFlushAttemp)
    {
        this.maxLogFlushAttemp = maxLogFlushAttemp;
    }
}
