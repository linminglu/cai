package com.example.admin.caipiao33.bean;

import java.util.List;

/**
 * Created by mac on 2017/8/3 0003.
 */

public class GouCaiBean
{

    private List<DataBean> dpc;
    private List<DataBean> gpc;
    private List<DataBean> all;

    public List<DataBean> getDpc()
    {
        return dpc;
    }

    public void setDpc(List<DataBean> dpc)
    {
        this.dpc = dpc;
    }

    public List<DataBean> getGpc()
    {
        return gpc;
    }

    public void setGpc(List<DataBean> gpc)
    {
        this.gpc = gpc;
    }

    public List<DataBean> getAll()
    {
        return all;
    }

    public void setAll(List<DataBean> all)
    {
        this.all = all;
    }

    public static class DataBean
    {
        /**
         * num : 51
         * lastOpen : 6,7,0,7,2
         * name : 三分时时彩
         * pic : /assets/statics/images/icon/51.png
         * endTime : 1501769310000
         * period : 20170803423
         * lastPeriod : 20170803422
         */

        private int num;
        private String lastOpen;
        private String name;
        private String pic;
        private String endTime;
        private String period;
        private String lastPeriod;

        public int getNum()
        {
            return num;
        }

        public void setNum(int num)
        {
            this.num = num;
        }

        public String getLastOpen()
        {
            return lastOpen;
        }

        public void setLastOpen(String lastOpen)
        {
            this.lastOpen = lastOpen;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getPic()
        {
            return pic;
        }

        public void setPic(String pic)
        {
            this.pic = pic;
        }

        public String getEndTime()
        {
            return endTime;
        }

        public void setEndTime(String endTime)
        {
            this.endTime = endTime;
        }

        public String getPeriod()
        {
            return period;
        }

        public void setPeriod(String period)
        {
            this.period = period;
        }

        public String getLastPeriod()
        {
            return lastPeriod;
        }

        public void setLastPeriod(String lastPeriod)
        {
            this.lastPeriod = lastPeriod;
        }
    }
}
