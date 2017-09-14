package com.example.admin.caipiao33.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    /**
     * sxNames : {"鼠":"10,22,34,46","鸡":"01,13,25,37,49","猴":"02,14,26,38","龙":"06,18,30,42","兔":"07,19,31,43","马":"04,16,28,40","狗":"12,24,36,48","猪":"11,23,35,47","羊":"03,15,27,39","牛":"09,21,33,45","蛇":"05,17,29,41","虎":"08,20,32,44"}
     */

    private SxNamesBean sxNames;

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

    public SxNamesBean getSxNames()
    {
        return sxNames;
    }

    public void setSxNames(SxNamesBean sxNames)
    {
        this.sxNames = sxNames;
    }

    public static class PlayDetailListBean implements Serializable, Cloneable
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

        @Override
        public PlayDetailListBean clone()
        {
            PlayDetailListBean p = null;
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out;
            ByteArrayInputStream byteIn;
            ObjectInputStream in;
            try {
                out = new ObjectOutputStream(byteOut);
                out.writeObject(this);
                byteIn = new ByteArrayInputStream(byteOut.toByteArray());
                in =new ObjectInputStream(byteIn);
                p = (PlayDetailListBean) in.readObject();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return p;
        }

        public static class ListBean implements Serializable, Cloneable
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
            /** 玩法列表名称 例如：万位千位百位... */
            private String parentName;

            public String getParentName()
            {
                return parentName;
            }

            public void setParentName(String parentName)
            {
                this.parentName = parentName;
            }

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

            @Override
            public ListBean clone()
            {
                ListBean o = null;
                try
                {
                    o = (ListBean) super.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return o;
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

    public static class SxNamesBean
    {
        /**
         * 鼠 : 10,22,34,46
         * 鸡 : 01,13,25,37,49
         * 猴 : 02,14,26,38
         * 龙 : 06,18,30,42
         * 兔 : 07,19,31,43
         * 马 : 04,16,28,40
         * 狗 : 12,24,36,48
         * 猪 : 11,23,35,47
         * 羊 : 03,15,27,39
         * 牛 : 09,21,33,45
         * 蛇 : 05,17,29,41
         * 虎 : 08,20,32,44
         */

        private String 鼠;
        private String 鸡;
        private String 猴;
        private String 龙;
        private String 兔;
        private String 马;
        private String 狗;
        private String 猪;
        private String 羊;
        private String 牛;
        private String 蛇;
        private String 虎;

        public String get鼠()
        {
            return 鼠;
        }

        public void set鼠(String 鼠)
        {
            this.鼠 = 鼠;
        }

        public String get鸡()
        {
            return 鸡;
        }

        public void set鸡(String 鸡)
        {
            this.鸡 = 鸡;
        }

        public String get猴()
        {
            return 猴;
        }

        public void set猴(String 猴)
        {
            this.猴 = 猴;
        }

        public String get龙()
        {
            return 龙;
        }

        public void set龙(String 龙)
        {
            this.龙 = 龙;
        }

        public String get兔()
        {
            return 兔;
        }

        public void set兔(String 兔)
        {
            this.兔 = 兔;
        }

        public String get马()
        {
            return 马;
        }

        public void set马(String 马)
        {
            this.马 = 马;
        }

        public String get狗()
        {
            return 狗;
        }

        public void set狗(String 狗)
        {
            this.狗 = 狗;
        }

        public String get猪()
        {
            return 猪;
        }

        public void set猪(String 猪)
        {
            this.猪 = 猪;
        }

        public String get羊()
        {
            return 羊;
        }

        public void set羊(String 羊)
        {
            this.羊 = 羊;
        }

        public String get牛()
        {
            return 牛;
        }

        public void set牛(String 牛)
        {
            this.牛 = 牛;
        }

        public String get蛇()
        {
            return 蛇;
        }

        public void set蛇(String 蛇)
        {
            this.蛇 = 蛇;
        }

        public String get虎()
        {
            return 虎;
        }

        public void set虎(String 虎)
        {
            this.虎 = 虎;
        }
    }
}
