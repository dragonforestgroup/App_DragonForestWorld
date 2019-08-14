package com.dragonforest.app.dragonforestworld.beans;

import com.dragonforest.app.dragonforestworld.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/8/13 19:06
 */
public class Article implements Serializable {
    String publisher;
    String title;
    String time;
    String imgUrl;
    int imgId;
    String content;
    String link;

    public Article() {
    }

    public Article(String publisher, String title, String time, String imgUrl, String content) {
        this.publisher = publisher;
        this.title = title;
        this.time = time;
        this.imgUrl = imgUrl;
        this.content = content;
    }

    public Article(String publisher, String title, String time, String imgUrl, int imgId, String content) {
        this.publisher = publisher;
        this.title = title;
        this.time = time;
        this.imgUrl = imgUrl;
        this.content = content;
        this.imgId = imgId;
    }

    public Article(String publisher, String title, String time, String imgUrl, int imgId, String content, String link) {
        this.publisher = publisher;
        this.title = title;
        this.time = time;
        this.imgUrl = imgUrl;
        this.content = content;
        this.imgId = imgId;
        this.link = link;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getImgId() {
        return imgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }


    // 测试方法

    /**
     * 获取android测试数据
     *
     * @return
     */
    public static List<Article> getAndroidData() {
        List<Article> list = new ArrayList<>();
        list.add(new Article("智哪儿", "如何客观评价华为的ios,鸿蒙", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/QzhfIiFLIC7GPwc6Ey5oPw"));
        list.add(new Article("玉刚说", "做了那么久Android,有什么想对大家说的", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/XyN2v8HdVcMmLEGkmD6PIg"));
        list.add(new Article("玉刚说", "谈谈程序员个人品牌的塑造", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/MH0y1XukafaGBB_37U8L9Q"));
        list.add(new Article("刘望舒", "写给Android Gradle知识体系", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/YFMkJZXSqmc0OOuyAnc4mA"));
        list.add(new Article("刘望舒", "JakeWharton评价我的代码像是在打地鼠？", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/QRzUPiMKcw5q9HjVPYZYuA"));
        list.add(new Article("刘望舒", "看了这篇文章，再也不怕ANR了！", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/aQ7UENbxdlZjGagKfyi6VQ"));
        list.add(new Article("刘望舒", "实现马蜂窝旅游头像泡泡动画", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/wERc2Dv021sXSr8yQOLD1g"));
        list.add(new Article("架构师社区", "到底选择PostgreSOL还是MySQL？看这里！", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/ac7bZ4eHVrnQsPEagX_YWg"));
        list.add(new Article("架构师社区", "头条创始人：我面试了两千个年轻人，发现混的好的都有这5种特质", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/lK8VdhoQy3NLgz2lnwYGQw"));
        return list;
    }

    /**
     * 获取java测试数据
     *
     * @return
     */
    public static List<Article> getJavaData() {
        List<Article> list = new ArrayList<>();
        list.add(new Article("智哪儿", "如何客观评价华为的ios,鸿蒙", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/QzhfIiFLIC7GPwc6Ey5oPw"));
        list.add(new Article("玉刚说", "做了那么久Android,有什么想对大家说的", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/XyN2v8HdVcMmLEGkmD6PIg"));
        list.add(new Article("玉刚说", "谈谈程序员个人品牌的塑造", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/MH0y1XukafaGBB_37U8L9Q"));
        list.add(new Article("刘望舒", "写给Android Gradle知识体系", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/YFMkJZXSqmc0OOuyAnc4mA"));
        list.add(new Article("刘望舒", "JakeWharton评价我的代码像是在打地鼠？", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/QRzUPiMKcw5q9HjVPYZYuA"));
        list.add(new Article("刘望舒", "看了这篇文章，再也不怕ANR了！", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/aQ7UENbxdlZjGagKfyi6VQ"));
        list.add(new Article("刘望舒", "实现马蜂窝旅游头像泡泡动画", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/wERc2Dv021sXSr8yQOLD1g"));
        list.add(new Article("架构师社区", "到底选择PostgreSOL还是MySQL？看这里！", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/ac7bZ4eHVrnQsPEagX_YWg"));
        list.add(new Article("架构师社区", "头条创始人：我面试了两千个年轻人，发现混的好的都有这5种特质", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/lK8VdhoQy3NLgz2lnwYGQw"));
        return list;
    }

    /**
     * 获取推荐测试数据
     *
     * @return
     */
    public static List<Article> getRecommendData() {
        List<Article> list = new ArrayList<>();
        list.add(new Article("智哪儿", "如何客观评价华为的ios,鸿蒙", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/QzhfIiFLIC7GPwc6Ey5oPw"));
        list.add(new Article("玉刚说", "做了那么久Android,有什么想对大家说的", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/XyN2v8HdVcMmLEGkmD6PIg"));
        list.add(new Article("玉刚说", "谈谈程序员个人品牌的塑造", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/MH0y1XukafaGBB_37U8L9Q"));
        list.add(new Article("刘望舒", "写给Android Gradle知识体系", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/YFMkJZXSqmc0OOuyAnc4mA"));
        list.add(new Article("刘望舒", "JakeWharton评价我的代码像是在打地鼠？", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/QRzUPiMKcw5q9HjVPYZYuA"));
        list.add(new Article("刘望舒", "看了这篇文章，再也不怕ANR了！", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/aQ7UENbxdlZjGagKfyi6VQ"));
        list.add(new Article("刘望舒", "实现马蜂窝旅游头像泡泡动画", "1小时前", "", R.drawable.app_bg_android, "","https://mp.weixin.qq.com/s/wERc2Dv021sXSr8yQOLD1g"));
        list.add(new Article("架构师社区", "到底选择PostgreSOL还是MySQL？看这里！", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/ac7bZ4eHVrnQsPEagX_YWg"));
        list.add(new Article("架构师社区", "头条创始人：我面试了两千个年轻人，发现混的好的都有这5种特质", "1小时前", "", R.drawable.app_bg_java, "","https://mp.weixin.qq.com/s/lK8VdhoQy3NLgz2lnwYGQw"));
        return list;
    }
}
