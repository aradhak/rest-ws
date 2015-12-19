package com.allpago.rest.model;

public class TimeZone
{
    private String status;

    private String rawOffset;

    private String dstOffset;

    private String timeZoneName;

    private String timeZoneId;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getRawOffset ()
    {
        return rawOffset;
    }

    public void setRawOffset (String rawOffset)
    {
        this.rawOffset = rawOffset;
    }

    public String getDstOffset ()
    {
        return dstOffset;
    }

    public void setDstOffset (String dstOffset)
    {
        this.dstOffset = dstOffset;
    }

    public String getTimeZoneName ()
    {
        return timeZoneName;
    }

    public void setTimeZoneName (String timeZoneName)
    {
        this.timeZoneName = timeZoneName;
    }

    public String getTimeZoneId ()
    {
        return timeZoneId;
    }

    public void setTimeZoneId (String timeZoneId)
    {
        this.timeZoneId = timeZoneId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", rawOffset = "+rawOffset+", dstOffset = "+dstOffset+", timeZoneName = "+timeZoneName+", timeZoneId = "+timeZoneId+"]";
    }
}