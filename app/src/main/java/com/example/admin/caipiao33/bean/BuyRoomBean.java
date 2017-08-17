package com.example.admin.caipiao33.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 房间信息
 */

public class BuyRoomBean implements Serializable
{

    private String num;
    private String page;
    private String roomName;
    private String lastOpen;
    private String roomId;
    private String period;
    private String playName;
    private String lastPeriod;
    private String endTime;
    private List<PlayDetailListBean> playDetailList;
    private List<PlayListBean> playList;
    private List<RoomListBean> roomList;

    public List<RoomListBean> getRoomList()
    {
        return roomList;
    }

    public void setRoomList(List<RoomListBean> roomList)
    {
        this.roomList = roomList;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getNum()
    {
        return num;
    }

    public void setNum(String num)
    {
        this.num = num;
    }

    public String getPage()
    {
        return page;
    }

    public void setPage(String page)
    {
        this.page = page;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public String getLastOpen()
    {
        return lastOpen;
    }

    public void setLastOpen(String lastOpen)
    {
        this.lastOpen = lastOpen;
    }

    public String getRoomId()
    {
        return roomId;
    }

    public void setRoomId(String roomId)
    {
        this.roomId = roomId;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public String getPlayName()
    {
        return playName;
    }

    public void setPlayName(String playName)
    {
        this.playName = playName;
    }

    public String getLastPeriod()
    {
        return lastPeriod;
    }

    public void setLastPeriod(String lastPeriod)
    {
        this.lastPeriod = lastPeriod;
    }

    public List<PlayDetailListBean> getPlayDetailList()
    {
        return playDetailList;
    }

    public void setPlayDetailList(List<PlayDetailListBean> playDetailList)
    {
        this.playDetailList = playDetailList;
    }

    public List<PlayListBean> getPlayList()
    {
        return playList;
    }

    public void setPlayList(List<PlayListBean> playList)
    {
        this.playList = playList;
    }

    public static class PlayDetailListBean implements Serializable
    {
        /**
         * list : [{"playName":"01","playId":"1-1-1101","bonus":43},{"playName":"02","playId":"1-1-1102","bonus":43},{"playName":"03","playId":"1-1-1103","bonus":43},{"playName":"04","playId":"1-1-1104","bonus":43},{"playName":"05","playId":"1-1-1105","bonus":43},{"playName":"06","playId":"1-1-1106","bonus":43},{"playName":"07","playId":"1-1-1107","bonus":43},{"playName":"08","playId":"1-1-1108","bonus":43},{"playName":"09","playId":"1-1-1109","bonus":43},{"playName":"10","playId":"1-1-1110","bonus":43},{"playName":"11","playId":"1-1-1111","bonus":43},{"playName":"12","playId":"1-1-1112","bonus":43},{"playName":"13","playId":"1-1-1113","bonus":43},{"playName":"14","playId":"1-1-1114","bonus":43},{"playName":"15","playId":"1-1-1115","bonus":43},{"playName":"16","playId":"1-1-1116","bonus":43},{"playName":"17","playId":"1-1-1117","bonus":43},{"playName":"18","playId":"1-1-1118","bonus":43},{"playName":"19","playId":"1-1-1119","bonus":43},{"playName":"20","playId":"1-1-1120","bonus":43},{"playName":"21","playId":"1-1-1121","bonus":43},{"playName":"22","playId":"1-1-1122","bonus":43},{"playName":"23","playId":"1-1-1123","bonus":43},{"playName":"24","playId":"1-1-1124","bonus":43},{"playName":"25","playId":"1-1-1125","bonus":43},{"playName":"26","playId":"1-1-1126","bonus":43},{"playName":"27","playId":"1-1-1127","bonus":43},{"playName":"28","playId":"1-1-1128","bonus":43},{"playName":"29","playId":"1-1-1129","bonus":43},{"playName":"30","playId":"1-1-1130","bonus":43},{"playName":"31","playId":"1-1-1131","bonus":43},{"playName":"32","playId":"1-1-1132","bonus":43},{"playName":"33","playId":"1-1-1133","bonus":43},{"playName":"34","playId":"1-1-1134","bonus":43},{"playName":"35","playId":"1-1-1135","bonus":43},{"playName":"36","playId":"1-1-1136","bonus":43},{"playName":"37","playId":"1-1-1137","bonus":43},{"playName":"38","playId":"1-1-1138","bonus":43},{"playName":"39","playId":"1-1-1139","bonus":43},{"playName":"40","playId":"1-1-1140","bonus":43},{"playName":"41","playId":"1-1-1141","bonus":43},{"playName":"42","playId":"1-1-1142","bonus":43},{"playName":"43","playId":"1-1-1143","bonus":43},{"playName":"44","playId":"1-1-1144","bonus":43},{"playName":"45","playId":"1-1-1145","bonus":43},{"playName":"46","playId":"1-1-1146","bonus":43},{"playName":"47","playId":"1-1-1147","bonus":43},{"playName":"48","playId":"1-1-1148","bonus":43},{"playName":"49","playId":"1-1-1149","bonus":43}]
         * name : 特码A
         */

        private String name;
        private boolean isCollapsed;
        private List<ListBean> list;

        public boolean isCollapsed()
        {
            return isCollapsed;
        }

        public void setCollapsed(boolean collapsed)
        {
            isCollapsed = collapsed;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public List<ListBean> getList()
        {
            return list;
        }

        public void setList(List<ListBean> list)
        {
            this.list = list;
        }

        public static class ListBean implements Serializable
        {
            /**
             * playName : 01
             * playId : 1-1-1101
             * bonus : 43
             */

            private String playName;
            private String playId;
            private String bonus;
            /** 投注金额 自选时为输入的金额，快捷时为0 */
            private String money;

            public String getMoney()
            {
                return money;
            }

            public void setMoney(String money)
            {
                this.money = money;
            }

            public String getPlayName()
            {
                return playName;
            }

            public void setPlayName(String playName)
            {
                this.playName = playName;
            }

            public String getPlayId()
            {
                return playId;
            }

            public void setPlayId(String playId)
            {
                this.playId = playId;
            }

            public String getBonus()
            {
                return bonus;
            }

            public void setBonus(String bonus)
            {
                this.bonus = bonus;
            }

            @Override
            public boolean equals(Object o)
            {
                if (this == o)
                    return true;
                if (o == null || getClass() != o.getClass())
                    return false;

                ListBean listBean = (ListBean) o;

                return playId != null ? playId.equals(listBean.playId) : listBean.playId == null;

            }

            @Override
            public int hashCode()
            {
                return playId != null ? playId.hashCode() : 0;
            }
        }
    }

    public static class PlayListBean implements Serializable
    {
        /**
         * playName : 特码B
         * playId : 1
         * playId1 : 1
         * playPage : tmb
         */

        private String playName;
        private String playId;
        private String playId1;
        private String playPage;

        public String getPlayName()
        {
            return playName;
        }

        public void setPlayName(String playName)
        {
            this.playName = playName;
        }

        public String getPlayId()
        {
            return playId;
        }

        public void setPlayId(String playId)
        {
            this.playId = playId;
        }

        public String getPlayId1()
        {
            return playId1;
        }

        public void setPlayId1(String playId1)
        {
            this.playId1 = playId1;
        }

        public String getPlayPage()
        {
            return playPage;
        }

        public void setPlayPage(String playPage)
        {
            this.playPage = playPage;
        }
    }

    public static class RoomListBean implements Serializable
    {
        private String name;
        private String image;
        private String remark;
        private String id;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

        public String getRemark()
        {
            return remark;
        }

        public void setRemark(String remark)
        {
            this.remark = remark;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }
    }
}
