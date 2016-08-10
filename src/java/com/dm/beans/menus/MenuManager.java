/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.menus;

/**
 * 菜单管理器类
 *
 * @author DUAN
 */
public class MenuManager {

    /**
     * 组装菜单数据
     *
     * @return
     */
    public static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("天气预报");
        btn11.setType("click");
        btn11.setKey("11");

        CommonButton btn12 = new CommonButton();
        btn12.setName("公交查询");
        btn12.setType("click");
        btn12.setKey("12");

        CommonButton btn13 = new CommonButton();
        btn13.setName("小译在线");
        btn13.setType("click");
        btn13.setKey("13");

        CommonButton btn14 = new CommonButton();
        btn14.setName("手机号查询");
        btn14.setType("click");
        btn14.setKey("14");

        CommonButton btn15 = new CommonButton();
        btn15.setName("历史上的今天");
        btn15.setType("click");
        btn15.setKey("15");

        CommonButton btn21 = new CommonButton();
        btn21.setName("爱音乐");
        btn21.setType("click");
        btn21.setKey("21");

        ViewButton btn22 = new ViewButton();
        btn22.setName("乐玩2048");
        btn22.setType("view");
        btn22.setUrl("http://2048.oubk.com/");

        CommonButton btn23 = new CommonButton();
        btn23.setName("热门精选");
        btn23.setType("click");
        btn23.setKey("23");

        CommonButton btn24 = new CommonButton();
        btn24.setName("人脸识别");
        btn24.setType("click");
        btn24.setKey("24");

        CommonButton btn25 = new CommonButton();
        btn25.setName("你聊我说");
        btn25.setType("click");
        btn25.setKey("25");

        ViewButton btn32 = new ViewButton();
        btn32.setName("快递查询");
        btn32.setType("view");
        btn32.setUrl("http://m.kuaidi100.com/");

        ViewButton btn33 = new ViewButton();
        btn33.setName("兴趣部落");
        btn33.setType("view");
        btn33.setUrl("http://www.imooc.com/");

        ViewButton btn34 = new ViewButton();
        btn34.setName("关于我们");
        btn34.setType("view");
        btn34.setUrl("http://121.40.145.106:8080/wx/index.jsp");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("实用工具");
        mainBtn1.setSub_button(new CommonButton[]{btn11, btn12, btn13, btn14, btn15});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("休闲娱乐");
        mainBtn2.setSub_button(new Button[]{btn21, btn22, btn23, btn24, btn25});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new Button[]{btn32, btn33, btn34});

        /**
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[]{mainBtn1, mainBtn2, mainBtn3});

        return menu;
    }
}
