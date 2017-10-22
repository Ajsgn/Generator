package cn.ajsgn.common.generator.db.column;

import org.apache.commons.lang3.StringUtils;

public class ColumnMappingFactory {
	
	public static ColumnMapping instanceOf(String driverName) {
		ColumnMapping cm = null;
		if(StringUtils.containsIgnoreCase(driverName, "ORACLE")){
			cm = new OracleColumnMapping();
		}else if(StringUtils.containsIgnoreCase(driverName, "MYSQL")){
			cm = new MysqlColumnMapping();
		}
		return cm;
	}
	
}
