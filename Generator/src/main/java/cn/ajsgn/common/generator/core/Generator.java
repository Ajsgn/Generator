/*
 * Copyright (c) 2017, Ajsgn 杨光 (Ajsgn@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ajsgn.common.generator.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import cn.ajsgn.common.generator.db.ColumnInfo;
import cn.ajsgn.common.generator.db.Table;
import cn.ajsgn.common.generator.db.TableConfig;
import cn.ajsgn.common.generator.db.config.ColumnTypeEnum;

/**
 * <p>模板生成核心类</p>
 * @ClassName: Generator
 * @Description: 模板生成核心类
 * @author Ajsgn@foxmail.com
 * @date 2017年10月22日 下午8:29:54
 */
public class Generator {
	
	private String user;
	private String password;
	private String jdbcUrl;
	private String driverClass;
	
	private Connection conn;
	
	private List<TableConfig> tableConfigs = new ArrayList<TableConfig>();
	
	/**
	 * <p>构造函数</p>
	 * @param jdbcUrl jdbc连接
	 * @param username 数据库登录名
	 * @param password 数据库登录密码
	 * @param driverClass 驱动类
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Generator(String jdbcUrl, String username, String password,String driverClass) throws ClassNotFoundException, SQLException{
		this.user = username;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.driverClass = driverClass;
		
		Class.forName(getDriverClass());
		Properties props =new Properties();
		props.put("remarksReporting","true");
		props.put("user",getUser());
		props.put("password",getPassword());
		this.conn = DriverManager.getConnection(getJdbcUrl(), props);
		
	}
	
	/**
	 * <p>增加一个需要生成模板的表名称</p>
	 * @Title: addTable
	 * @Description: 增加一个需要生成模板的表名称
	 * @param tableConfig 表配置描述
	 * @return Generator 当前对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:31:26
	 */
	public Generator addTable(TableConfig tableConfig){
		if(null != tableConfig){
			tableConfigs.add(tableConfig);
		}
		return this;
	}
	
	/**
	 * <p>开始执行模板生成</p>
	 * @Title: generator
	 * @Description: 执行模板生成
	 * @return List<Table> 模板类集合
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:32:44
	 */
	public List<Table> generator() throws SQLException, ClassNotFoundException{
		DatabaseMetaData databaseMetaData = this.conn.getMetaData();
		List<Table> tables = new ArrayList<Table>();
		for(TableConfig tableConfig : tableConfigs){
			List<ColumnInfo> columnInfos = columnInfos(databaseMetaData, tableConfig.getSchemaName(), tableConfig.getTableName());
			Table table = new Table(driverClass,tableConfig,columnInfos);
			tables.add(table);
		}
		return tables;
	}
	
	/**
	 * <p>列信息<p>
	 * @Title: columnInfos
	 * @Description: 列信息
	 * @param databaseMetaData 列信息描述
	 * @param schemaName 表所属schema
	 * @param tableName 表名称
	 * @return List<ColumnInfo> 结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:33:41
	 */
	private List<ColumnInfo> columnInfos(DatabaseMetaData databaseMetaData, String schemaName, String tableName) throws SQLException{
		schemaName = StringUtils.isBlank(schemaName) ? user : schemaName;
		List<ColumnInfo> list = new ArrayList<ColumnInfo>(23);
		ResultSet rs = databaseMetaData.getColumns(null, schemaName.toUpperCase(), tableName, "%");
		while(rs.next()){
			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setBufferLength(rs.getString(ColumnTypeEnum.BUFFER_LENGTH.getValue()));
			columnInfo.setCharOctetLength(rs.getInt(ColumnTypeEnum.CHAR_OCTET_LENGTH.getValue()));
			// FIXME 表中如果有默认值，此处会报  java.sql.SQLException: 流已被关闭
//			columnInfo.setColumnDef(rs.getString(ColumnTypeEnum.COLUMN_DEF.getValue()));
			columnInfo.setColumnName(rs.getString(ColumnTypeEnum.COLUMN_NAME.getValue()));
			columnInfo.setColumnSize(rs.getInt(ColumnTypeEnum.COLUMN_SIZE.getValue()));
			columnInfo.setDataType(rs.getInt(ColumnTypeEnum.DATA_TYPE.getValue()));
			columnInfo.setDecimalDigits(rs.getInt(ColumnTypeEnum.DECIMAL_DIGITS.getValue()));
			columnInfo.setIsAutoincrement(rs.getString(ColumnTypeEnum.IS_AUTOINCREMENT.getValue()));
			columnInfo.setIsNullable(rs.getString(ColumnTypeEnum.IS_NULLABLE.getValue()));
			columnInfo.setNullable(rs.getInt(ColumnTypeEnum.NULLABLE.getValue()));
			columnInfo.setNumPrecRadix(rs.getInt(ColumnTypeEnum.NUM_PREC_RADIX.getValue()));
			columnInfo.setOrdinalPosition(rs.getInt(ColumnTypeEnum.ORDINAL_POSITION.getValue()));
			columnInfo.setRemarks(rs.getString(ColumnTypeEnum.REMARKS.getValue()));
			columnInfo.setScopeCatlog(rs.getString(ColumnTypeEnum.SCOPE_CATLOG.getValue()));
			columnInfo.setScopeSchema(rs.getString(ColumnTypeEnum.SCOPE_SCHEMA.getValue()));
			columnInfo.setScopeTable(rs.getString(ColumnTypeEnum.SCOPE_TABLE.getValue()));
			columnInfo.setSourceDataType(rs.getInt(ColumnTypeEnum.SOURCE_DATA_TYPE.getValue()));
			columnInfo.setSqlDataType(rs.getInt(ColumnTypeEnum.SQL_DATA_TYPE.getValue()));
			columnInfo.setSqlDatetimeSub(rs.getInt(ColumnTypeEnum.SQL_DATETIME_SUB.getValue()));
			columnInfo.setTableCat(rs.getString(ColumnTypeEnum.TABLE_CAT.getValue()));
			columnInfo.setTableName(rs.getString(ColumnTypeEnum.TABLE_NAME.getValue()));
			columnInfo.setTableSchem(rs.getString(ColumnTypeEnum.TABLE_SCHEM.getValue()));
			columnInfo.setTypeName(rs.getString(ColumnTypeEnum.TYPE_NAME.getValue()));
			list.add(columnInfo);
		}
		return list;
	}
	
	/**
	 * <p>获取当前数据库登录名称</p>
	 * @Title: getUser
	 * @Description: 获取数据库的登录名称
	 * @return String 数据库的登录名称
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:35:32
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * <p>获取当前数据库登录密码</p>
	 * @Title: getPassword
	 * @Description: 获取当前数据库登录密码
	 * @return String 当前数据库登录密码
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:36:26
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * <p>获取当前数据库jdbc链接</p>
	 * @Title: getJdbcUrl
	 * @Description: 获取当前数据库的jdbc链接
	 * @return String 当前数据库jdbc链接
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:38:27
	 */
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	
	/**
	 * <p>获取当前数据库的驱动类名称</p>
	 * @Title: getDriverClass
	 * @Description: 获取当前数据库的驱动类名称
	 * @return String 当前数据库驱动类名称
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:39:07
	 */
	public String getDriverClass() {
		return driverClass;
	}

}
