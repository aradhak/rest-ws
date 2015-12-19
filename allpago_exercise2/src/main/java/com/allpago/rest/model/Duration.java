package com.allpago.rest.model;

public class Duration
{
    private String text;

    private int value;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public int getValue ()
    {
        return value;
    }

    public void setValue (int value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+", value = "+value+"]";
    }
}