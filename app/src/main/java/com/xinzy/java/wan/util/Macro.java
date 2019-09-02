package com.xinzy.java.wan.util;

public class Macro {

    ///////////////////////////////////////////////////////////////////////////////////////////
    /*
     * API
     */
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static final String BASE_URL = "https://www.wanandroid.com";


    ///////////////////////////////////////////////////////////////////////////////////////////
    /*
     * Keys
     */
    ///////////////////////////////////////////////////////////////////////////////////////////
    public static final String KEY_URL = "URL";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_CHAPTER = "CHAPTER";
    public static final String KEY_CHAPTER_ID = "CHAPTER_ID";



    ///////////////////////////////////////////////////////////////////////////////////////////
    /*
     * Router
     */
    ///////////////////////////////////////////////////////////////////////////////////////////
    private static final String BASE_PATH = "/xinzy/wan/";
    private static final String PATH_VIEW = BASE_PATH + "view/";

    /**首页*/
    public static final String ROUTER_MAIN = PATH_VIEW + "main";

    /** 文章列表 */
    public static final String ROUTER_TOPICS = PATH_VIEW + "topics";

    /** WebView */
    public static final String ROUTER_WEB = PATH_VIEW + "web";


}
