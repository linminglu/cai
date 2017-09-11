package com.example.admin.caipiao33.bean;

import java.util.ArrayList;

/**
 * 推荐Bean
 */

public class TuiJianBean
{

    /**
     * spread : 0.1
     * mySpread : {"spreadCount":5,"spreadMember":[{"code":"te***21","isDanger":0},{"code":"te***01","isDanger":0},{"code":"te***02","isDanger":0},{"code":"te***01","isDanger":1},{"code":"te***02","isDanger":0}],"spreadIn":100}
     */

    private String spread;
    private MySpreadBean mySpread;

    public String getSpread()
    {
        return spread;
    }

    public void setSpread(String spread)
    {
        this.spread = spread;
    }

    public MySpreadBean getMySpread()
    {
        return mySpread;
    }

    public void setMySpread(MySpreadBean mySpread)
    {
        this.mySpread = mySpread;
    }

    public static class MySpreadBean
    {
        /**
         * spreadCount : 5
         * spreadMember : [{"code":"te***21","isDanger":0},{"code":"te***01","isDanger":0},{"code":"te***02","isDanger":0},{"code":"te***01","isDanger":1},{"code":"te***02","isDanger":0}]
         * spreadIn : 100.0
         */

        private String spreadCount;
        private String spreadIn;
        private ArrayList<SpreadMemberBean> spreadMember;

        public String getSpreadCount()
        {
            return spreadCount;
        }

        public void setSpreadCount(String spreadCount)
        {
            this.spreadCount = spreadCount;
        }

        public String getSpreadIn()
        {
            return spreadIn;
        }

        public void setSpreadIn(String spreadIn)
        {
            this.spreadIn = spreadIn;
        }

        public ArrayList<SpreadMemberBean> getSpreadMember()
        {
            return spreadMember;
        }

        public void setSpreadMember(ArrayList<SpreadMemberBean> spreadMember)
        {
            this.spreadMember = spreadMember;
        }

        public static class SpreadMemberBean
        {
            /**
             * code : te***21
             * isDanger : 0
             */

            private String code;
            private int isDanger;

            public String getCode()
            {
                return code;
            }

            public void setCode(String code)
            {
                this.code = code;
            }

            public int getIsDanger()
            {
                return isDanger;
            }

            public void setIsDanger(int isDanger)
            {
                this.isDanger = isDanger;
            }
        }
    }
}
