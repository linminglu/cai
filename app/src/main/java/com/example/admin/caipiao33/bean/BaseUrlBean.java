package com.example.admin.caipiao33.bean;

/**
 * 获取版本信息以及baseurl
 */

public class BaseUrlBean
{

    /**
     * currentVersion : 1
     * updateUrl : http://www.baidu.com
     * lowVersion : 1
     * url : https://m.cp89001.com
     */

    private String currentVersion;
    private String updateUrl;
    private String lowVersion;
    private String url;

    public String getCurrentVersion()
    {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion)
    {
        this.currentVersion = currentVersion;
    }

    public String getUpdateUrl()
    {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl)
    {
        this.updateUrl = updateUrl;
    }

    public String getLowVersion()
    {
        return lowVersion;
    }

    public void setLowVersion(String lowVersion)
    {
        this.lowVersion = lowVersion;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
