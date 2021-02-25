package util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class DBHelper {

	private static String driver;
	private static String url;
	private static String name;
	private static String pwd;

	/**
	 * 静态块，初始化
	 */
	static {
		try {
			// 读取配置文件 ==> 通过类加载器读取类路径里面的文件
			String path = "conn.properties";
			InputStream in = DBHelper.class.getClassLoader().getResourceAsStream(path);
			// 创建集合对象
			Properties prop = new Properties();
			prop.load(in);
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			name = prop.getProperty("name");
			pwd = prop.getProperty("pwd");
			Class.forName(driver);
		} catch (Exception e) {
			// 异常转型 + 抛出运行期异常
			// 块不能直接抛出编译器异常
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取连接对象
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, name, pwd);
	}

	/**
	 * 查询歌手数据
	 * @param sql sql语句
	 * @param params 可变参数数组，sql语句中的参数
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> selectList(String sql, ResultSetMapper<T> callback, Object... params)
			throws SQLException {

		System.out.println("SQL：" + sql);
		System.out.println("参数：" + Arrays.toString(params));
		Connection conn = getConnection();
		try {
			// 创建语句对象
			PreparedStatement ps = conn.prepareStatement(sql);
			// 设置查询参数
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			// 执行语句
			ResultSet rs = ps.executeQuery();
			// 定义返回结果
			List<T> ret = new ArrayList<>();
			// while(rs.next());
			for (; rs.next();) {
				T t = callback.map(rs);
				ret.add(t);
			}
			return ret;
		} finally {
			conn.close();
		}
	}

	/**
	 *	回调接口，用于将结果集中某一行数据（所有字段值），映射到某个对象中，该接口是泛型接口
	 * @param <T>
	 */
	public static interface ResultSetMapper<T> {
		T map(ResultSet rs) throws SQLException;
	}

	/**
	 * 	将结果集映射到Map中的映射器实现类
	 */
	public static ResultSetMapper<Map<String, Object>> RsMapMapper = new ResultSetMapper<Map<String, Object>>() {
		@Override
		public Map<String, Object> map(ResultSet rs) throws SQLException {
			// 定义返回结果
			Map<String, Object> ret = new LinkedHashMap<String, Object>();
			// 获取结果集元数据对象
			ResultSetMetaData md = rs.getMetaData();
			// 遍历该结果集所有的列
			for (int i = 0; i < md.getColumnCount(); i++) {
				// 获取当前列的列名
				String columnName = md.getColumnName(i + 1);
				// 获取当前列的列值
				Object columnValue = rs.getObject(columnName);
				// 添加到map集合中
				ret.put(columnName, columnValue);
			}
			// 返回映射后的 map 集合
			return ret;
		}
	};

	public static class RsBeanMapper<T> implements ResultSetMapper<T> {
		// 要转换的实体类的类型
		private Class<T> beanClass;

		public RsBeanMapper(Class<T> beanClass) {
			this.beanClass = beanClass;
		}

		@Override
		public T map(ResultSet rs) throws SQLException {
			try {
				T bean = beanClass.newInstance();
				Field[] fields = beanClass.getDeclaredFields();
				// 遍历该结果集所有的列
				for (int i = 0; i < fields.length; i++) {
					// 获取当前列的列名
					String columnName = toDBName(fields[i].getName());
					// 获取当前列的列值
					Object columnValue = null;
					try {
						columnValue = getValue(rs, columnName, fields[i].getType());
					} catch (SQLException e) {
						System.out.printf("结果集中无法获取“属性名：%s => 列名：%s”的值，原因：%s\n", fields[i].getName(), columnName, e.getMessage());
					}
					// 强制访问私有属性
					fields[i].setAccessible(true);
					fields[i].set(bean, columnValue);
				}
				// 返回映射后的 map 集合
				return bean;
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("创建实体对象错误！", e);
			}
		}

	}

	/**
	 * 	更新方法，执行增删改（除查询以外的所有语句）
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int update(String sql, Object... params) throws SQLException {
		System.out.println("SQL：" + sql);
		System.out.println("参数：" + Arrays.toString(params));
		Connection conn = getConnection();
		try {
			// 创建语句对象
			PreparedStatement ps = conn.prepareStatement(sql);
			// 设置查询参数
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			return ps.executeUpdate();
		} finally {
			conn.close();
		}
	}

	/**
	 * 	将查询结果，装载到List中存实体类（T：泛型类）的集合
	 */
	public static <T> List<T> selectListBean(String sql, Class<T> beanClass, Object... params) throws SQLException {
		return selectList(sql, new RsBeanMapper<T>(beanClass), params);
	}

	/**
	 *	查询单行结果集（Map） 
	 */
	public static <T> T selectOneBean(String sql, Class<T> beanClass, Object... params) throws SQLException {
		sql = "select * from (" + sql + ") limit 0, 1";
		List<T> ret = selectListBean(sql, beanClass, params);
		if (ret.size() > 1) {
			throw new SQLException("查询结果行数大于1！");
		} else if (ret.isEmpty()) {
			return null;
		} else {
			return ret.get(0);
		}
	}

	/**
	 * 	将查询结果，装载到List中存Map的集合
	 */
	public static List<Map<String, Object>> selectListMap(String sql, Object... params) throws SQLException {
		return selectList(sql, RsMapMapper, params);
	}

	/**
	 *	查询单行结果集（Map） 
	 */
	public static Map<String, Object> selectOneMap(String sql, Object... params) throws SQLException {
		sql = "select * from " + sql + " limit 0, 1";
		List<Map<String, Object>> ret = selectList(sql, RsMapMapper, params);
		if (ret.size() > 1) {
			throw new SQLException("查询结果行数大于1！");
		} else if (ret.isEmpty()) {
			return null;
		} else {
			return ret.get(0);
		}
	}

	/**
	 *	查询单值结果集（Map） 
	 */
	public static Object selectValue(String sql, Object... params) throws SQLException {
		Map<String, Object> ret = selectOneMap(sql, params);
		// 将 值 集合转换成数组，返回第一个值（查询结果中的第一列的值）
		return ret.values().toArray()[0];
	}

	/**
	 * 	将java命名规则（驼峰命名法）转换数据库命名规则，例如：userName => user_name
	 * @param name
	 * @return
	 */
	private static String toDBName(String name) {
		return name.replaceAll("([A-Z])", "_$1").toLowerCase();
	}

	/**
	 * 	根据不同的实体类属性类型，从结果集中获取值
	 */
	private static Object getValue(ResultSet rs, String columnName, Class<?> type) throws SQLException {
		if (type.equals(String.class)) {
			return rs.getString(columnName);
		} else if (type.equals(Integer.class) || type.equals(int.class)) {
			return rs.getInt(columnName);
		} else if (type.equals(Long.class) || type.equals(long.class)) {
			return rs.getLong(columnName);
		} else if (type.equals(Short.class) || type.equals(short.class)) {
			return rs.getShort(columnName);
		} else if (type.equals(Byte.class) || type.equals(byte.class)) {
			return rs.getByte(columnName);
		} else if (type.equals(Float.class) || type.equals(float.class)) {
			return rs.getFloat(columnName);
		} else if (type.equals(Double.class) || type.equals(double.class)) {
			return rs.getDouble(columnName);
		} else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
			return rs.getBoolean(columnName);
		} else if (type.equals(Date.class)) {
			return rs.getDate(columnName);
		} else if (type.equals(Timestamp.class)) {
			return rs.getTimestamp(columnName);
		}
		return rs.getObject(columnName);
	}
}