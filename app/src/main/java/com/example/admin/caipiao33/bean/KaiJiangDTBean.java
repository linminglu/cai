package com.example.admin.caipiao33.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2017/8/8
 */

public class KaiJiangDTBean
{
    /**
     * num : 5
     * openTime :
     * period : 20170514052
     * content : 1,2,3
     * ”name” : ”重庆时时彩”
     */

    private int num;
    private String openTime;
    private String period;
    private String content;
    @SerializedName("”name”")
    private String _$Name165; // FIXME check this code

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public String getOpenTime()
    {
        return openTime;
    }

    public void setOpenTime(String openTime)
    {
        this.openTime = openTime;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String get_$Name165()
    {
        return _$Name165;
    }

    public void set_$Name165(String _$Name165)
    {
        this._$Name165 = _$Name165;
    }
}
