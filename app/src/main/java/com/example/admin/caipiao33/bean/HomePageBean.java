package com.example.admin.caipiao33.bean;

import java.util.List;

/**
 * 首页Bean
 */

public class HomePageBean
{

    /**
     *
     * scrollNotice : 彩票89（易记域名cp89.com）购彩就上彩票89，下载彩票APP及完善个人资料即送18元并可提升尊享VIP高级服务，六合彩、时时彩赔率业界最高。
     * winList : [{"gid":"51","gname":"三分时时彩","User":"hhh***","WinAmount":"39.6"},{"gid":"51","gname":"三分时时彩","User":"c12***","WinAmount":"15.84"},{"gid":"51","gname":"三分时时彩","User":"hao***","WinAmount":"3.96"},{"gid":"51","gname":"三分时时彩","User":"137***","WinAmount":"170.28"},{"gid":"51","gname":"三分时时彩","User":"cql***","WinAmount":"98.0"},{"gid":"51","gname":"三分时时彩","User":"aol***","WinAmount":"1872.0","type":"r"},{"gid":"51","gname":"三分时时彩","User":"cha***","WinAmount":"19.6"},{"gid":"51","gname":"三分时时彩","User":"Nie***","WinAmount":"294.0"},{"gid":"51","gname":"三分时时彩","User":"131***","WinAmount":"79.2"},{"gid":"51","gname":"三分时时彩","User":"136***","WinAmount":"194.04"},{"gid":"51","gname":"三分时时彩","User":"353***","WinAmount":"198.0"},{"gid":"51","gname":"三分时时彩","User":"aaa***","WinAmount":"198.0"},{"gid":"51","gname":"三分时时彩","User":"cjb***","WinAmount":"78.4"},{"gid":"51","gname":"三分时时彩","User":"yyy***","WinAmount":"19.6"},{"gid":"51","gname":"三分时时彩","User":"190***","WinAmount":"3.96"},{"gid":"5","gname":"重庆时时彩","User":"123***","WinAmount":"9.8"},{"gid":"5","gname":"重庆时时彩","User":"ff0***","WinAmount":"19.6"},{"gid":"5","gname":"重庆时时彩","User":"win***","WinAmount":"9.8"},{"gid":"5","gname":"重庆时时彩","User":"w10***","WinAmount":"98.0"},{"gid":"5","gname":"重庆时时彩","User":"139***","WinAmount":"49.75"},{"gid":"5","gname":"重庆时时彩","User":"QZH***","WinAmount":"49.0"}]
     * scrollImg : ["https://tupian-cdn.com/uploadimg/15011292249780.jpg","https://tupian-cdn.com/uploadimg/14950236858790.jpg","https://tupian-cdn.com/uploadimg/14950236781590.jpg"]
     * typeList : [{"num":51,"name":"三分时时彩","pic":"/assets/statics/images/icon/51.png"},{"num":5,"name":"重庆时时彩","pic":"/assets/statics/images/icon/5.png"},{"num":9,"name":"北京赛车PK10","pic":"/assets/statics/images/icon/9.png"},{"num":52,"name":"三分PK10","pic":"/assets/statics/images/icon/52.png"},{"num":41,"name":"PC蛋蛋(北京28)","pic":"/assets/statics/images/icon/41.png"},{"num":18,"name":"香港⑥合彩","pic":"/assets/statics/images/icon/18.png"},{"num":34,"name":"幸运飞艇","pic":"/assets/statics/images/icon/34.png"},{"num":27,"name":"广东快乐十分","pic":"/assets/statics/images/icon/27.png"}]
     */

    private String scrollNotice;
    private List<WinListBean> winList;
    private List<String> scrollImg;
    private List<TypeListBean> typeList;
    /**
     * popNotice : {"id":"54","content":"\n<br>为保证充值顺畅，及时购彩，彩票89建议您使用银行转帐至公司银行卡　笔笔赠送1%，谢谢！\n\n温馨提示：为避免香港⑥合彩17:00－21:30高峰入款拥堵，彩票89建议各用户提前充值，谢谢！<br><br>   \n\n              请牢记彩票89官方网址　www.cp89.com\n<br>各位会员，为了您的充值快捷和安全，建议各位请勿保存二维码扫码，充值时请到网站获取，谢谢！","title":"手机银行转账、网银转账、支付宝转账赠送公告！","time":"2017-08-14"}
     * typeList : [{"num":53,"pic":"/assets/statics/images/icon/53.png","name":"极速时时彩"},{"num":51,"pic":"/assets/statics/images/icon/51.png","name":"三分时时彩"},{"num":5,"pic":"/assets/statics/images/icon/5.png","name":"重庆时时彩"},{"num":52,"pic":"/assets/statics/images/icon/52.png","name":"三分PK10"},{"num":9,"pic":"/assets/statics/images/icon/9.png","name":"北京赛车(PK10)"},{"num":18,"pic":"/assets/statics/images/icon/18.png","name":"香港⑥合彩"},{"num":34,"pic":"/assets/statics/images/icon/34.png","name":"幸运飞艇"},{"num":41,"pic":"/assets/statics/images/icon/41.png","name":"PC蛋蛋(北京28)"},{"num":27,"pic":"/assets/statics/images/icon/27.png","name":"广东快乐十分"},{"num":28,"pic":"/assets/statics/images/icon/28.png","name":"重庆幸运农场"},{"num":42,"pic":"/assets/statics/images/icon/42.png","name":"新加坡28(新版)"}]
     */

    private PopNoticeBean popNotice;

    public String getScrollNotice()
    {
        return scrollNotice;
    }

    public void setScrollNotice(String scrollNotice)
    {
        this.scrollNotice = scrollNotice;
    }

    public List<WinListBean> getWinList()
    {
        return winList;
    }

    public void setWinList(List<WinListBean> winList)
    {
        this.winList = winList;
    }

    public List<String> getScrollImg()
    {
        return scrollImg;
    }

    public void setScrollImg(List<String> scrollImg)
    {
        this.scrollImg = scrollImg;
    }

    public List<TypeListBean> getTypeList()
    {
        return typeList;
    }

    public void setTypeList(List<TypeListBean> typeList)
    {
        this.typeList = typeList;
    }

    public PopNoticeBean getPopNotice()
    {
        return popNotice;
    }

    public void setPopNotice(PopNoticeBean popNotice)
    {
        this.popNotice = popNotice;
    }

    public static class WinListBean
    {
        /**
         * gid : 51
         * gname : 三分时时彩
         * User : hhh***
         * WinAmount : 39.6
         * type : r
         */

        private String gid;
        private String gname;
        private String User;
        private String WinAmount;
        private String type;

        public String getGid()
        {
            return gid;
        }

        public void setGid(String gid)
        {
            this.gid = gid;
        }

        public String getGname()
        {
            return gname;
        }

        public void setGname(String gname)
        {
            this.gname = gname;
        }

        public String getUser()
        {
            return User;
        }

        public void setUser(String User)
        {
            this.User = User;
        }

        public String getWinAmount()
        {
            return WinAmount;
        }

        public void setWinAmount(String WinAmount)
        {
            this.WinAmount = WinAmount;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }
    }

    public static class TypeListBean
    {
        /**
         * num : 51
         * name : 三分时时彩
         * pic : /assets/statics/images/icon/51.png
         */

        private String num;
        private String name;
        private String pic;

        public String getNum()
        {
            return num;
        }

        public void setNum(String num)
        {
            this.num = num;
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
    }

    public static class PopNoticeBean
    {
        /**
         * id : 54
         * content :
         * <br>为保证充值顺畅，及时购彩，彩票89建议您使用银行转帐至公司银行卡　笔笔赠送1%，谢谢！
         * <p>
         * 温馨提示：为避免香港⑥合彩17:00－21:30高峰入款拥堵，彩票89建议各用户提前充值，谢谢！<br><br>
         * <p>
         * 请牢记彩票89官方网址　www.cp89.com
         * <br>各位会员，为了您的充值快捷和安全，建议各位请勿保存二维码扫码，充值时请到网站获取，谢谢！
         * title : 手机银行转账、网银转账、支付宝转账赠送公告！
         * time : 2017-08-14
         */

        private String id;
        private String content;
        private String title;
        private String time;

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getTime()
        {
            return time;
        }

        public void setTime(String time)
        {
            this.time = time;
        }
    }
}
