package cn.ajsgn.common.generator.db;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import cn.ajsgn.common.generator.util.FileUtil;
import cn.ajsgn.common.generator.util.StrKit;

public class TableConfig {
	
	private String tableName;
	private String schemaName;
	private String[] primaryKeys;
	private String basePath;
	private String basePackage;
	private String beanName;
	
	private File basePathFloderFile;
	private File basePackageFloderFile;
	
	private File absFloderFile;
	private File entityFloderFile;
	private File conditionFloderFile;
	private File daoFloderFile;
	private File mapperFloderFile;
	private File serviceFloderFile;
	private File serviceImplFloderFile;
	
	public TableConfig(String schemaName, String tableName, String beanName, String basePath, String basePackage){
		setTableName(tableName);
		setBasePath(basePath);
		setBasePackage(basePackage);
		setBeanName(beanName);
		setSchemaName(schemaName);
		
		initBasePathFloderFile();
		initBasePackageFloderFile();
		
		init();
	}

	public String getTableName() {
		return tableName;
	}

	public String[] getPrimaryKeys() {
		return primaryKeys == null ? new String[]{"ID"} : primaryKeys;
	}

	public String getBasePath() {
		return basePath;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public TableConfig setSchemaName(String schemaName) {
		this.schemaName = schemaName;
		return this;
	}
	
	public TableConfig setTableName(String tableName) {
		if(StringUtils.isBlank(tableName)){
			throw new IllegalArgumentException("argument tableName cannot to be null ~");
		}
		this.tableName = tableName;
		return this;
	}

	public TableConfig setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
		return this;
	}

	public TableConfig setBasePath(String basePath) {
		if(StringUtils.isBlank(basePath)){
			throw new IllegalArgumentException("argument basePath cannot to be null ~");
		}
		this.basePath = basePath;
		return this;
	}

	public TableConfig setBasePackage(String basePackage) {
		if(StringUtils.isBlank(basePackage)){
			throw new IllegalArgumentException("argument basePackage cannot to be null ~");
		}
		this.basePackage = basePackage;
		return this;
	}
	
	public String getBeanName() {
		String beanName = StringUtils.isBlank(this.beanName) ? StrKit.firstCharToUpperCase(StrKit.toCamelCase(tableName.toLowerCase())) : this.beanName;
		return beanName;
	}

	public TableConfig setBeanName(String beanName) {
		this.beanName = beanName;
		return this;
	}
	
	private void initBasePathFloderFile() {
		basePathFloderFile = new File(getBasePath());
		FileUtil.mkdirsIfNotExists(basePathFloderFile);
	}
	
	private void initBasePackageFloderFile() {
		basePackageFloderFile = new File(basePathFloderFile,getBasePackage().replaceAll("\\.", "/"));
		FileUtil.mkdirsIfNotExists(basePackageFloderFile);
	}
	
	private void init() {
		absFloderFile = new File(basePackageFloderFile.getAbsolutePath(),"abs");
		FileUtil.mkdirsIfNotExists(absFloderFile);
		
		entityFloderFile = new File(basePackageFloderFile.getAbsolutePath(),"entity");
		FileUtil.mkdirsIfNotExists(entityFloderFile);
		
		conditionFloderFile = new File(basePackageFloderFile.getAbsolutePath(),"condition");
		FileUtil.mkdirsIfNotExists(conditionFloderFile);
		
		daoFloderFile = new File(basePackageFloderFile.getAbsolutePath(),"dao");
		FileUtil.mkdirsIfNotExists(daoFloderFile);
		
		mapperFloderFile = new File(basePackageFloderFile.getAbsolutePath(),"mapper");
		FileUtil.mkdirsIfNotExists(mapperFloderFile);
		
		serviceFloderFile = new File(basePackageFloderFile.getAbsolutePath(),"service");
		FileUtil.mkdirsIfNotExists(serviceFloderFile);
		
		serviceImplFloderFile = new File(serviceFloderFile,"impl");
		FileUtil.mkdirsIfNotExists(serviceImplFloderFile);
	}

	public File getBasePathFloderFile() {
		return basePathFloderFile;
	}

	public File getBasePackageFloderFile() {
		return basePackageFloderFile;
	}

	public File getAbsFloderFile() {
		return absFloderFile;
	}

	public File getEntityFloderFile() {
		return entityFloderFile;
	}

	public File getConditionFloderFile() {
		return conditionFloderFile;
	}

	public File getDaoFloderFile() {
		return daoFloderFile;
	}

	public File getMapperFloderFile() {
		return mapperFloderFile;
	}

	public File getServiceImplFloderFile() {
		return serviceImplFloderFile;
	}

	public File getServiceFloderFile() {
		return serviceFloderFile;
	}
	
}
