package cn.ajsgn.common.generator.db.column;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Oracle映射关系
 * @ClassName: OracleColumnMapping
 * @Description: Oracle映射关系
 * @author g.yang@i-vpoints.com
 * @date 2017年10月16日 下午6:32:21
 */
class OracleColumnMapping implements ColumnMapping{
	
	private Map<String,String> javaTypeMapping = new HashMap<String,String>();
	private Map<String,String> jdbcTypeMapping = new HashMap<String,String>();
	
	public OracleColumnMapping(){
		javaTypeMappingInit();
		jdbcTypeMappingInit();
	}
	
	@Override
	public String getColumnClassName(String columnTypeName) {
		return javaTypeMapping.get(StringUtils.defaultIfBlank(columnTypeName, "").toUpperCase());
	}
	
	@Override
	public void setColumnClassName(String columnTypeName, String javaType) {
		javaTypeMapping.put(StringUtils.defaultIfBlank(columnTypeName, "").toUpperCase(), StringUtils.defaultIfBlank(javaType, ""));
	}

	@Override
	public String getDbColumnType(String columnTypeName) {
		return jdbcTypeMapping.get(StringUtils.defaultIfBlank(columnTypeName, "").toUpperCase());
	}

	@Override
	public void setDbColumnType(String columnTypeName, String jdbcType) {
		jdbcTypeMapping.put(StringUtils.defaultIfBlank(columnTypeName, "").toUpperCase(), StringUtils.defaultIfBlank(jdbcType, ""));
	}
	
	private void javaTypeMappingInit(){
		javaTypeMapping.put("BFILE", "oracle.jdbc.OracleBfile");
		javaTypeMapping.put("BINARY_DOUBLE", "java.lang.Double");
		javaTypeMapping.put("BINARY_FLOAT", "java.lang.Float");
		javaTypeMapping.put("BLOB", "oracle.jdbc.OracleBlob");
		javaTypeMapping.put("CHAR", "java.lang.String");
		javaTypeMapping.put("VARCHAR2", "java.lang.String");
		javaTypeMapping.put("CLOB", "oracle.jdbc.OracleClob");
		javaTypeMapping.put("DATE", "java.sql.Timestamp");
		javaTypeMapping.put("NUMBER", "java.math.BigDecimal");
		javaTypeMapping.put("INTERVALDS", "oracle.sql.INTERVALDS");
		javaTypeMapping.put("INTERVALYM", "oracle.sql.INTERVALYM");
		javaTypeMapping.put("INTERVAL DAY(2) TO SECOND(6)", "java.lang.Object");
		javaTypeMapping.put("INTERVAL YEAR(2) TO MONTH", "java.lang.Object");
		javaTypeMapping.put("FLOAT", "java.lang.Float");
		javaTypeMapping.put("LONG", "java.lang.String");
		javaTypeMapping.put("NCHAR", "java.lang.String");
		javaTypeMapping.put("NVARCHAR2", "java.lang.String");
		javaTypeMapping.put("NCLOB", "oracle.jdbc.OracleNClob");
		javaTypeMapping.put("NVARCHAR2", "java.lang.String");
		javaTypeMapping.put("RAW", "java.lang.Byte[]");
		javaTypeMapping.put("ROWID", "oracle.sql.ROWID");
		javaTypeMapping.put("UROWID", "oracle.sql.ROWID");
		javaTypeMapping.put("TIMESTAMP", "java.sql.Timestamp");
		javaTypeMapping.put("TIMESTAMP(6)", "oracle.sql.TIMESTAMP");
	}
	
	private void jdbcTypeMappingInit(){
		jdbcTypeMapping.put("BFILE", "OTHER");
		jdbcTypeMapping.put("BINARY_DOUBLE", "OTHER");
		jdbcTypeMapping.put("BINARY_FLOAT", "OTHER");
		jdbcTypeMapping.put("BLOB", "BLOB");
		jdbcTypeMapping.put("CHAR", "CHAR");
		jdbcTypeMapping.put("VARCHAR2", "VARCHAR");
		jdbcTypeMapping.put("CLOB", "CLOB");
		jdbcTypeMapping.put("DATE", "DATE");
		jdbcTypeMapping.put("FLOAT", "FLOAT");
		jdbcTypeMapping.put("NUMBER", "DECIMAL");
		jdbcTypeMapping.put("INTERVALDS", "OTHER");
		jdbcTypeMapping.put("INTERVALYM", "OTHER");
		jdbcTypeMapping.put("INTERVAL DAY(2) TO SECOND(6)", "OTHER");
		jdbcTypeMapping.put("INTERVAL YEAR(2) TO MONTH", "OTHER");
		jdbcTypeMapping.put("LONG", "LONG VARCHAR");
		jdbcTypeMapping.put("NCHAR", "NCHAR");
		jdbcTypeMapping.put("NVARCHAR2", "OTHER");
		jdbcTypeMapping.put("NCLOB", "NCLOB");
		jdbcTypeMapping.put("RAW", "OTHER");
		jdbcTypeMapping.put("ROWID", "OTHER");
		jdbcTypeMapping.put("UROWID", "OTHER");
		jdbcTypeMapping.put("TIMESTAMP", "TIMESTAMP");
		jdbcTypeMapping.put("TIMESTAMP(6)", "TIMESTAMP");
	}

	@Override
	public String toString() {
		return "OracleColumnMapping [javaTypeMapping=" + javaTypeMapping + ", jdbcTypeMapping=" + jdbcTypeMapping + "]";
	}
	
}
