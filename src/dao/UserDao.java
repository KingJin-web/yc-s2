package dao;

import bean.SqMember;
import bean.SqShare;
import  util.DBHelper;
import  util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void register(SqMember sqMember) throws SQLException {

        String sql = "insert into sq_member value (null,?,?,?,null,?,null,null,null,null,now(),null) ";
        DBHelper.update(sql, sqMember.getName(), Utils.generateName(),
                sqMember.getPwd(), sqMember.getEmail());
    }

    public int login(SqMember sqMember) throws SQLException {
        String sql = "select count(*) from sq_member where name=? and pwd =?";

        return DBHelper.selectList(sql, new DBHelper.ResultSetMapper<Integer>() {
            @Override
            public Integer map(ResultSet rs) throws SQLException {
                return rs.getInt(1);
            }
        }, sqMember.getName(), sqMember.getPwd()).get(0);
    }

    /**
     * 查询用户名注册人数
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public int selectNumByName(String name) throws SQLException {
        String sql = "select count(*) from sq_member where name=? ";
        return DBHelper.selectList(sql, new DBHelper.ResultSetMapper<Integer>() {
            @Override
            public Integer map(ResultSet rs) throws SQLException {
                return rs.getInt(1);
            }
        }, name).get(0);
    }

    /**
     * id: 0,
     * name: "",
     * phone: "",
     * email: "",
     * qq: "",
     * head: "",
     * rank: "",
     * glod: "",
     * create_time: ""
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public SqMember selectByName(String name) throws SQLException {
        String sql = "select * from sq_member where name = ?";
        return DBHelper.selectListBean(sql, SqMember.class, name).get(0);
    }

    /**
     * UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
     *
     * @param sqMember
     * @throws SQLException
     */
    public void updateById(SqMember sqMember) throws SQLException {
        String sql = "update sq_member set nickname = ? , phone=? , email =? , qq=? where id = ?";
        DBHelper.update(sql, sqMember.getNickname(), sqMember.getPhone(), sqMember.getEmail(), sqMember.getQq(), sqMember.getId());
    }

    public void insertShare(SqShare sqShare) throws SQLException {

        String sql = "INSERT INTO `sq_share` VALUES (null, ?, ?, ?, ?, " +
                "?, ?, ?, 999, ?, " +
                "?, now(),null);";
        DBHelper.update(sql, sqShare.getName(), sqShare.getSingers(), sqShare.getType(), sqShare.getTags(),
                sqShare.getSrcType(), sqShare.getFormat(), sqShare.getIntro(), sqShare.getDownUrl(),
                sqShare.getMember()
        );

    }

    /**
     * 通过名字查询分享的内容
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public List<SqShare> selectShareByUserName(String name) throws SQLException {
        String sql = "select * from sq_share where member =?";
        return DBHelper.selectListBean(sql, SqShare.class, name);
    }

    /**
     * 通过名字查询分享的内容
     * 分页
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public List<SqShare> selectShareByUserName(String name, int page, int pageNums) throws SQLException {
        int begin = (page - 1) * pageNums;
        String sql = "select * from sq_share where member =? order by create_time DESC limit ?,?";
        return DBHelper.selectListBean(sql, SqShare.class, name, begin, pageNums);
    }

//    public static void main(String[] args) throws SQLException {
//        UserDao userDao = new UserDao();
//        List<SqShare> list = userDao.selectShareByUserName("hjy123", 1, 5);
//
//        for (SqShare sqShare : list) {
//            System.out.println(sqShare);
//        }
//        List<SqShare> list2 = userDao.selectShareByUserName("hjy123", 2, 5);
//
//        for (SqShare sqShare : list2) {
//            System.out.println(sqShare);
//        }
//        System.out.println();
//    }

    /**
     * 通过姓名查询总页数
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public int selectSharePages(String name) throws SQLException {
        String sql = "select count(*) from sq_share where member =? ";
        return DBHelper.selectList(sql, new DBHelper.ResultSetMapper<Integer>() {
            @Override
            public Integer map(ResultSet rs) throws SQLException {
                return rs.getInt(1);
            }
        }, name).get(0);
    }

    /**
     * 通过 sq_share 表id删除数据
     * DELETE FROM table_name [WHERE Clause]
     *
     * @param id id
     * @throws SQLException
     */
    public void deleteShearById(String id) throws SQLException {
        String sql = "DELETE FROM sq_share where id = ?";
        DBHelper.update(sql, id);
    }

    public boolean isVip(String name) throws SQLException {
        String sql = "SELECT timestampdiff(SECOND,  now(), sa.vip_time) from sq_member sa where name = ?;";
        int a = DBHelper.selectList(sql, new DBHelper.ResultSetMapper<Integer>() {
            @Override
            public Integer map(ResultSet rs) throws SQLException {
                return rs.getInt(1);
            }
        }, name).get(0);
        return a > 0;
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
        System.out.println(userDao.isVip("hjy123"));
        System.out.println(userDao.isVip("sq"));
    }

    /**
     * 修改 头像
     *
     * @param head
     * @param name
     * @throws SQLException
     */
    public void updateUserHead(String head, String name) throws SQLException {
        String sql = "update sq_member set head = ? where name = ?";
        DBHelper.update(sql, head, name);
    }

    /**
     * vip 自定义 头像
     */
    public void updateUserHeadVip(String head, String name) throws SQLException {
        String sql = "update sq_member set head = ? where name = ?";
        DBHelper.update(sql, head, name);
    }

    /**
     * 冲一年会员
     *
     * @param name
     * @throws SQLException
     */
    public void vipYears(String name) throws SQLException {
        String sql = "UPDATE sq_member \n" +
                "SET vip_time =\n" +
                "IF\n" +
                "	(\n" +
                "		vip_time >  now(),\n" +
                "		DATE_ADD( vip_time, INTERVAL 1 YEAR ),\n" +
                "	DATE_ADD( now(), INTERVAL 1 YEAR )) \n" +
                "WHERE\n" +
                "	`name` = ?";
        DBHelper.update(sql, name);
    }

    /**
     * 充三个月会员
     *
     * @param name
     */
    public void vipThreeMonths(String name) throws SQLException {
        String sql = "\tUPDATE sq_member \n" +
                "SET vip_time =\n" +
                "IF\n" +
                "\t(\n" +
                "\t\tvip_time >  now(),\n" +
                "\t\tDATE_ADD( vip_time, INTERVAL 3 MONTH ),\n" +
                "\tDATE_ADD( now(), INTERVAL 3 MONTH )) \n" +
                "WHERE\n" +
                "\t`name` = ?";
        DBHelper.update(sql, name);
    }

    /**
     * 充一个月会员
     *
     * @param name
     */
    public void vipOneMonths(String name) throws SQLException {
        String sql = "\tUPDATE sq_member \n" +
                "SET vip_time =\n" +
                "IF\n" +
                "\t(\n" +
                "\t\tvip_time >  now(),\n" +
                "\t\tDATE_ADD( vip_time, INTERVAL 1 MONTH ),\n" +
                "\tDATE_ADD( now(), INTERVAL 1 MONTH )) \n" +
                "WHERE\n" +
                "\t`name` = ?";
        DBHelper.update(sql, name);
    }
}
