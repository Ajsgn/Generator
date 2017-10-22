package cn.ajsgn.common.generator.db.column;

/**
 * <p>数据库列映射关系</p>
 * @ClassName: ColumnMapping
 * @Description: 数据库列映射关系
 * @author Ajsgn@foxmail.com
 * @date 2017年10月16日 下午4:42:31
 */
public interface ColumnMapping {
	
	/**
	 * <p>通过数据库列类型名称获取其对应的javaType</p>
	 * <p>不区分大小写</p>
	 * @Title: getColumnClassName
	 * @Description: 通过数据库列类型名称获取其对应的javaType
	 * @param columnTypeName 数据库类型名称
	 * @return String
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月16日 下午4:42:47
	 */
	public String getColumnClassName(String columnTypeName);
	
	/**
	 * <p>补充一组映射关系</p>
	 * <p>不区分大小写</p>
	 * @Title: setColumnClassName
	 * @Description: 补充一组映射关系
	 * @param columnTypeName 数据库类型名称
	 * @param javaType 对应的java数据类型的字符串表示
	 * @return void
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月16日 下午6:27:55
	 */
	public void setColumnClassName(String columnTypeName,String javaType);
	
	/**
	 * <p>通过数据库列类型名称获取其对应的jdbcType</p>
	 * <p>不区分大小写</p>
	 * @Title: getDbColumnType
	 * @Description: 通过数据库列类型名称获取其对应的jdbcType
	 * @param columnTypeName 数据库类型名称
	 * @return String
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月16日 下午4:43:58
	 */
	public String getDbColumnType(String columnTypeName);
	
	/**
	 * <p>补充一组映射关系</p>
	 * <p>不区分大小写</p>
	 * @Title: setDbColumnType
	 * @Description: 补充一组映射关系
	 * @param columnTypeName 数据库类型名称
	 * @param jdbcType 对应的jdbc数据类型的字符串表示
	 * @return void
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月16日 下午6:30:06
	 */
	public void setDbColumnType(String columnTypeName,String jdbcType);
	
}
