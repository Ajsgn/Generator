package cn.ajsgn.common.generator.db.core;

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

public class Generator {
	
	private String user;
	private String password;
	private String jdbcUrl;
	private String driverClass;
	
	private Connection conn;
	
	private List<TableConfig> tableConfigs = new ArrayList<TableConfig>();
	
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
	
	public Generator addTable(TableConfig tableConfig){
		if(null != tableConfig){
			tableConfigs.add(tableConfig);
		}
		return this;
	}
	
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

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

}
