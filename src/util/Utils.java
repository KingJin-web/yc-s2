package util;


import biz.BizException;
import dao.UserDao;

import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    private static final UserDao userDao = new UserDao();


    /**
     * 判断传值是否为空 或空字符串
     *
     * @param value 传的值
     * @param msg 返回的提示信息
     * @throws BizException 自定义错误
     */
    public static void checkNull(Object value, String msg) throws BizException {
        if (value == null) {
            throw new BizException(msg);
        }
        if (value instanceof String) {
            String svalue = (String) value;
            if (svalue.trim().isEmpty()) {
                throw new BizException(msg);
            }
        }
    }

    public static void isEmail(String email, String msg) throws BizException {
        if (!isEmail(email))
            throw new BizException(msg);

    }

    public static void isPhone(String phone) throws BizException {
        if (!isPhoneLegal(phone))
            throw new BizException("请输入合法的手机号码");

    }

    public static void isQq(String qq) throws BizException {
        checkNull(qq, "请输入qq");
        if (!isNumeric(qq)) {
            throw new BizException("请输入合法的qq号码");
        }
    }


    /**
     *
     * @param name
     * @throws BizException
     */
    public static void nameIsUse(String name) throws BizException {
        if (name.isEmpty() || name == null || name.equals(null)) {
            throw new BizException("请输入用户名");
        }
        try {
            int count = userDao.selectNumByName(name);
            if (count >= 1) {
                throw new BizException("用户名已经使用请换一个");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 判断是否是qq号的方法
     */
    public static boolean isNumeric(String string) {
        if (string.length() >= 5 && string.length() <= 12) {
            Pattern pattern = Pattern.compile("[0-9]+");
            return pattern.matcher(string).matches();
        } else {
            return false;
        }

    }


    /**
     * 判断Email合法性
     *
     * @param email 邮箱
     * @return 是便ture不是便false
     */
    public static boolean isEmail(String email) throws BizException {
        if (email.isEmpty() || email == null) {
            throw new BizException("请输入邮箱");
        }
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws BizException {
        checkNull(str, "请输入手机号码");
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 生成随机昵称
     *
     * @return
     */
    public static String generateName() {
        int adjLen = adjective.length;
        int nLen = noun.length;
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        sb.append(adjective[random.nextInt(adjLen)]);
        sb.append(noun[random.nextInt(nLen)]);
        return sb.toString();
    }

    private static String adjective[] = {
            "一样的", "喜欢的", "美丽的", "一定的", "原来的", "美好的", "开心的", "可能的", "可爱的",
            "明白的", "所有的", "后来的", "重要的", "经常的", "自然的", "真正的", "害怕的", "空中的",
            "红色的", "痛苦的", "干净的", "辛苦的", "精彩的", "欢乐的", "进步的", "影响的", "黄色的",
            "亲爱的", "根本的", "完美的", "金黄的", "聪明的", "清新的", "迷人的", "光明的", "共同的",
            "直接的", "真实的", "听说的", "用心的", "飞快的", "雪白的", "着急的", "乐观的", "主要的",
            "鲜艳的", "冰冷的", "细心的", "奇妙的", "水平的", "动人的", "大量的", "无知的", "礼貌的",
            "暖和的", "深情的", "正常的", "平淡的", "光亮的", "落后的", "大方的", "老大的", "刻苦的",
            "晴朗的", "专业的", "永久的", "大气的", "知己的", "刚好的", "相对的", "平和的", "友好的",
            "广大的", "秀丽的", "日常的", "高级的", "相同的", "笔直的", "安定的", "知足的", "结实的",
            "许久的", "听话的", "知名的", "闷热的", "众多的", "拥挤的", "天生的", "迷你的", "老实的",
            "友爱的", "原始的", "可笑的", "合格的", "公共的", "大红的", "得力的", "洁净的", "暗淡的",
            "鲜红的", "桃红的", "吓人的", "多余的", "秀美的", "繁忙的", "冰凉的", "热心的", "空旷的",
            "冷清的", "公开的", "冷淡的", "齐全的", "草绿的", "能干的", "发火的", "可心的", "业余的",
            "空心的", "凉快的", "长远的", "土黄的", "和好的", "合法的", "明净的", "过时的", "低下的",
            "不快的", "低级的", "中用的", "不定的", "公办的", "用功的", "少许的", "忙乱的", "日用的",
            "要紧的", "少见的", "非分的", "怕人的", "大忙的", "幸福的", "特别的", "未来的", "伟大的",
            "困难的", "伤心的", "实在的", "现实的", "丰富的", "同样的", "巨大的", "耐心的", "优秀的",
            "亲切的", "讨厌的", "严厉的", "积极的", "整齐的", "环保的"};

    private static String[] noun = {
            "生活", "一起", "不是", "人们", "今天", "孩子", "心里", "奶奶", "眼睛",
            "学校", "原来", "爷爷", "地方", "过去", "事情", "以后", "可能", "晚上",
            "里面", "才能", "知识", "故事", "多少", "比赛", "冬天", "所有", "样子",
            "成绩", "后来", "以前", "童年", "问题", "日子", "活动", "公园", "作文",
            "旁边", "下午", "自然", "房间", "空气", "笑容", "明天", "风景", "音乐",
            "岁月", "文化", "生气", "机会", "身影", "天气", "空中", "红色", "书包",
            "今年", "汽车", "早晨", "道路", "认识", "办法", "精彩",
            "中午", "礼物", "星星", "习惯", "树木", "女儿", "友谊", "夜晚", "意义",
            "家长", "耳朵", "家人", "门口", "班级", "人间", "厨房", "风雨", "影响",
            "过年", "电话", "黄色", "种子", "广场", "清晨", "根本", "故乡", "笑脸",
            "水面", "思想", "伙伴", "美景", "照片", "水果", "彩虹", "刚才", "月光",
            "先生", "鲜花", "灯光", "感情", "亲人", "语言", "爱心", "光明", "左右",
            "新年", "角落", "青蛙", "电影", "行为", "阳台", "用心", "题目", "天地",
            "动力", "花园", "诗人", "树林", "雨伞", "去年", "少女", "乡村", "对手",
            "上午", "分别", "活力", "作用", "古代", "公主", "力气", "从前", "作品",
            "空间", "黑夜", "说明", "青年", "面包", "往事", "大小", "司机",
            "中心", "对面", "心头", "嘴角", "家门", "书本", "雪人", "笑话",
            "云朵", "早饭", "右手", "水平", "行人", "乐园", "花草", "人才", "左手",
            "目的", "课文", "优点", "灰尘", "年代", "沙子", "小说", "儿女", "明星",
            "难题", "本子", "水珠", "彩色", "路灯", "把握", "房屋", "心愿", "左边",
            "新闻", "早点", "市场", "雨点", "细雨", "书房", "毛巾", "画家", "元旦",
            "绿豆", "本领", "起点", "青菜", "土豆", "总结", "礼貌", "右边", "窗帘",
            "萝卜", "深情", "楼房", "对话", "面条", "北方", "木头", "商店", "疑问",
            "后果", "现场", "诗词", "光亮", "白菜", "男子", "风格", "大道", "梦乡",
            "姐妹", "毛衣", "园丁", "两边", "大风", "乡下", "广播", "规定", "围巾",
            "意见", "大方", "头脑", "老大", "成语", "专业", "背景", "大衣", "黄豆",
            "高手", "叶片", "过往", "选手", "奶油", "时空", "大气", "借口", "抹布",
            "画笔", "山羊", "晚会", "拖鞋", "手心", "手工", "明年", "手术", "火苗",
            "知己", "价格", "树苗", "主意", "摇篮", "对比", "胖子", "专家", "年级",
            "落日", "东风", "屋子", "创意", "报道", "下巴", "面子", "迷宫", "雪山",
            "友好", "烟雾", "西方", "姨妈", "问号", "年轮", "居民", "选手", "奶油",
            "时空", "大气", "借口", "抹布", "画笔", "山羊", "晚会", "拖鞋", "手心",
            "手工", "明年", "手术", "火苗", "知己", "价格", "树苗", "主意", "摇篮",
            "对比", "胖子", "专家", "年级", "落日", "东风", "屋子", "创意", "报道",
            "下巴", "面子", "迷宫", "雪山", "友好", "烟雾", "西方", "姨妈", "问号",
            "年轮", "居民", "选手", "奶油", "时空", "大气", "借口", "抹布", "画笔",
            "山羊", "晚会", "拖鞋", "手心", "手工", "明年", "手术", "火苗", "知己",
            "价格", "树苗", "主意", "摇篮", "对比", "胖子", "专家", "年级", "落日",
            "东风", "屋子", "创意", "报道", "下巴", "面子", "迷宫", "雪山", "友好",
            "烟雾", "西方", "姨妈", "问号", "年轮", "居民"};


}









