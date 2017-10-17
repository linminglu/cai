package com.example.admin.caipiao33.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 房间信息
 */

public class BuyRecordBean implements Serializable
{

    /**
     * pageNo : 1
     * pageSize : 20
     * totalSize : 7
     * totalPage : 1
     * items : [{"isWin":-1,"amount":1,"id":6566622,"status":1,"realWinAmount":-0.88,"gameName":"香港⑥合彩","period":"2017098","addTime":"2017-08-22 08:53:52","winAmount":0},{"isWin":-1,"amount":1,"id":5846548,"status":1,"realWinAmount":-1,"gameName":"极速时时彩","period":"20170818572","addTime":"2017-08-18 15:17:25","winAmount":0},{"isWin":1,"amount":0.1,"id":5140417,"status":1,"realWinAmount":0.1,"gameName":"极速时时彩","period":"20170814614","addTime":"2017-08-14 16:19:38","winAmount":0.2},{"isWin":-1,"amount":1,"id":4420804,"status":1,"realWinAmount":-1,"gameName":"三分PK10","period":"20170810273","addTime":"2017-08-10 14:36:28","winAmount":0},{"isWin":-1,"amount":1,"id":4420158,"status":1,"realWinAmount":-1,"gameName":"极速时时彩","period":"20170810543","addTime":"2017-08-10 14:32:48","winAmount":0},{"isWin":1,"amount":1,"id":4420157,"status":1,"realWinAmount":0.98,"gameName":"极速时时彩","period":"20170810543","addTime":"2017-08-10 14:32:48","winAmount":1.98},{"isWin":-1,"amount":1,"id":4420156,"status":1,"realWinAmount":-1,"gameName":"极速时时彩","period":"20170810543","addTime":"2017-08-10 14:32:48","winAmount":0}]
     * itemsCount : 0
     */

    private int pageNo;
    private int pageSize;
    private int totalSize;
    private int totalPage;
    private int itemsCount;
    private List<ItemsBean> items;

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getTotalSize()
    {
        return totalSize;
    }

    public void setTotalSize(int totalSize)
    {
        this.totalSize = totalSize;
    }

    public int getTotalPage()
    {
        return totalPage;
    }

    public void setTotalPage(int totalPage)
    {
        this.totalPage = totalPage;
    }

    public int getItemsCount()
    {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount)
    {
        this.itemsCount = itemsCount;
    }

    public List<ItemsBean> getItems()
    {
        return items;
    }

    public void setItems(List<ItemsBean> items)
    {
        this.items = items;
    }

    public static class ItemsBean
    {
        /**
         * isWin : -1
         * amount : 1
         * id : 6566622
         * status : 1
         * realWinAmount : -0.88
         * gameName : 香港⑥合彩
         * period : 2017098
         * addTime : 2017-08-22 08:53:52
         * winAmount : 0
         */

        private int isWin;
        private double amount;
        private String id;
        private int status;
        private String realWinAmount;
        private String gameName;
        private String period;
        private String addTime;
        private String winAmount;
        private String gId;

        public int getIsWin()
        {
            return isWin;
        }

        public void setIsWin(int isWin)
        {
            this.isWin = isWin;
        }

        public double getAmount()
        {
            return amount;
        }

        public void setAmount(double amount)
        {
            this.amount = amount;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getgId()
        {
            return gId;
        }

        public void setgId(String gId)
        {
            this.gId = gId;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }

        public String getRealWinAmount()
        {
            return realWinAmount;
        }

        public void setRealWinAmount(String realWinAmount)
        {
            this.realWinAmount = realWinAmount;
        }

        public String getGameName()
        {
            return gameName;
        }

        public void setGameName(String gameName)
        {
            this.gameName = gameName;
        }

        public String getPeriod()
        {
            return period;
        }

        public void setPeriod(String period)
        {
            this.period = period;
        }

        public String getAddTime()
        {
            return addTime;
        }

        public void setAddTime(String addTime)
        {
            this.addTime = addTime;
        }

        public String getWinAmount()
        {
            return winAmount;
        }

        public void setWinAmount(String winAmount)
        {
            this.winAmount = winAmount;
        }
    }
}
