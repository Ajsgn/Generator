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
package cn.ajsgn.common.generator.db;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import cn.ajsgn.common.generator.db.config.TempleteVariable;
import cn.ajsgn.common.generator.util.FileUtil;
import cn.ajsgn.common.generator.util.StrKit;

/**
 * <p>需要自动生成的一张表的配置描述</p>
 * @ClassName: TableConfig
 * @Description: 需要自动生成的一张表的配置描述
 * @author Ajsgn@foxmail.com
 * @date 2017年10月22日 下午7:37:17
 */
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
	
	private TempleteVariable templeteVariable;
	
	/**
	 * <p>构造函数</p>
	 * @param schemaName 当前表所属schema，sql中会在表前面加上sechema前缀，可为空
	 * @param tableName 表名称，不可为空
	 * @param beanName 生成实体Bean的名称，可为空，如果为空，取当前表名首字母大写的驼峰命名规范
	 * @param basePath 生成文件的存储路径，不可为空
	 * @param basePackage 基础包名称描述，不可为空
	 */
	public TableConfig(String schemaName, String tableName, String beanName, String basePath, String basePackage){
		this(schemaName, tableName, beanName, basePath, basePackage, null);
	}
	
	public TableConfig(String schemaName, String tableName, String beanName, String basePath, String basePackage, TempleteVariable templeteVariable){
		setTableName(tableName);
		setBasePath(basePath);
		setBasePackage(basePackage);
		setBeanName(beanName);
		setSchemaName(schemaName);
		
		initBasePathFloderFile();
		initBasePackageFloderFile();
		
		setTempleteVariable(templeteVariable);
		
		init();
	}
	
	/**
	 * <p>返回当前表名称</p>
	 * @Title: getTableName
	 * @Description: 返回当前表名称
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:40:39
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * <p>获取当前表的主键列名称</p>
	 * <p>如果为空，则默认使用ID</p>
	 * @Title: getPrimaryKeys
	 * @Description: 获取当前表的主键
	 * @return String[] 主键数组
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:41:12
	 */
	public String[] getPrimaryKeys() {
		return primaryKeys == null ? new String[]{"ID"} : primaryKeys;
	}
	
	/**
	 * <p>获取生成文件的存储路径</p>
	 * @Title: getBasePath
	 * @Description: 获取生成文件的存储路径
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:42:21
	 */
	public String getBasePath() {
		return basePath;
	}
	
	/**
	 * <p>火拳当前表生成api的基础包路径</p>
	 * @Title: getBasePackage
	 * @Description: 获取当前表生成api的基础包路径
	 * @return String 当前表生成api的包路径描述
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:42:57
	 */
	public String getBasePackage() {
		return basePackage;
	}
	
	/**
	 * <p>获取当前表所属schema</p>
	 * @Title: getSchemaName
	 * @Description: 获取当前表所属schema
	 * @return String 当前表所属schema
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:44:08
	 */
	public String getSchemaName() {
		return schemaName;
	}
	
	/**
	 * <p>修改当前表所属schema名称</p>
	 * <p>schemaName可为空</p>
	 * @Title: setSchemaName
	 * @Description: 修改当前表所属schema名称
	 * @param schemaName 要修改的schema名称
	 * @return TableConfig 当前对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:46:59
	 */
	public TableConfig setSchemaName(String schemaName) {
		this.schemaName = schemaName;
		return this;
	}
	
	/**
	 * <p>修改当期表名称</p>
	 * <p>tableName不可为空</p>
	 * @Title: setTableName
	 * @Description: 修改当前表名称
	 * @param tableName 将要修改为的表名称
	 * @return TableConfig 当前对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:46:16
	 */
	public TableConfig setTableName(String tableName) {
		if(StringUtils.isBlank(tableName)){
			throw new IllegalArgumentException("argument tableName cannot to be null ~");
		}
		this.tableName = tableName;
		return this;
	}
	
	/**
	 * <p>设置当前表的主键列名称</p>
	 * @Title: setPrimaryKeys
	 * @Description: 设置当前表的主键列名称
	 * @param primaryKeys 主键
	 * @return TableConfig 当前对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:49:37
	 */
	public TableConfig setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
		return this;
	}
	
	/**
	 * <p>修改当前表生成文件的存储路径</p>
	 * @Title: setBasePath
	 * @Description: 修改当前表生成文件的存储路径
	 * @param basePath 文件的存储路径
	 * @return TableConfig 当前对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:50:28
	 */
	public TableConfig setBasePath(String basePath) {
		if(StringUtils.isBlank(basePath)){
			throw new IllegalArgumentException("argument basePath cannot to be null ~");
		}
		this.basePath = basePath;
		return this;
	}
	
	/**
	 * <p>修改包路径</p>
	 * @Title: setBasePackage
	 * @Description: 修改包路径
	 * @param basePackage 包路径
	 * @return TableConfig 当前对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:53:24
	 */
	public TableConfig setBasePackage(String basePackage) {
		if(StringUtils.isBlank(basePackage)){
			throw new IllegalArgumentException("argument basePackage cannot to be null ~");
		}
		this.basePackage = basePackage;
		return this;
	}
	
	/**
	 * <p>获取当前表生成bean的名称</p>
	 * <p>如果构造时未设置该字段，则使用当前表面首字母大写的驼峰命名</p>
	 * @Title: getBeanName
	 * @Description: 获取当前表生成的bean的名称
	 * @return String 当前表对应的bean名称
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:59:24
	 */
	public String getBeanName() {
		String beanName = StringUtils.isBlank(this.beanName) ? StrKit.firstCharToUpperCase(StrKit.toCamelCase(tableName.toLowerCase())) : this.beanName;
		return beanName;
	}
	
	/**
	 * <p>修改当前表生成的bean的名称</p>
	 * @Title: setBeanName
	 * @Description: 修改当前表生成的bean的名称
	 * @param beanName 表名称
	 * @return TableConfig 当前对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:01:11
	 */
	public TableConfig setBeanName(String beanName) {
		this.beanName = beanName;
		return this;
	}
	
	/**
	 * <p>初始化文件系统目录</p>
	 * @Title: initBasePathFloderFile
	 * @Description: 初始化文件系统目录
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:02:01
	 */
	private void initBasePathFloderFile() {
		basePathFloderFile = new File(getBasePath());
		FileUtil.mkdirsIfNotExists(basePathFloderFile);
	}
	
	/**
	 * <p>初始化包文件目录</p>
	 * @Title: initBasePackageFloderFile
	 * @Description: 初始化包文件目录
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:02:30
	 */
	private void initBasePackageFloderFile() {
		basePackageFloderFile = new File(basePathFloderFile,getBasePackage().replaceAll("\\.", "/"));
		FileUtil.mkdirsIfNotExists(basePackageFloderFile);
	}
	
	/**
	 * <p>初始化模板文件目录</p>
	 * @Title: init
	 * @Description: 初始化模板文件目录
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:03:05
	 */
	private void init() {
		absFloderFile = new File(basePackageFloderFile.getAbsolutePath(),getTempleteVariable().getAbsPackage());
		FileUtil.mkdirsIfNotExists(absFloderFile);
		
		entityFloderFile = new File(basePackageFloderFile.getAbsolutePath(),getTempleteVariable().getEntityPackage());
		FileUtil.mkdirsIfNotExists(entityFloderFile);
		
		conditionFloderFile = new File(basePackageFloderFile.getAbsolutePath(),getTempleteVariable().getConditionPackage());
		FileUtil.mkdirsIfNotExists(conditionFloderFile);
		
		daoFloderFile = new File(basePackageFloderFile.getAbsolutePath(),getTempleteVariable().getDaoPackage());
		FileUtil.mkdirsIfNotExists(daoFloderFile);
		
		mapperFloderFile = new File(basePackageFloderFile.getAbsolutePath(),getTempleteVariable().getMapperPackage());
		FileUtil.mkdirsIfNotExists(mapperFloderFile);
		
		serviceFloderFile = new File(basePackageFloderFile.getAbsolutePath(),getTempleteVariable().getServicePackage());
		FileUtil.mkdirsIfNotExists(serviceFloderFile);
		
		serviceImplFloderFile = new File(serviceFloderFile,getTempleteVariable().getImplPackage());
		FileUtil.mkdirsIfNotExists(serviceImplFloderFile);
	}
	
	/**
	 * <p>获取存储文件目录文件对象</p>
	 * @Title: getBasePathFloderFile
	 * @Description: 获取存储文件目录文件对象
	 * @return File 文件目录对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:03:49
	 */
	public File getBasePathFloderFile() {
		return basePathFloderFile;
	}
	
	/**
	 * <p>获取包文件目录文件对象</p>
	 * @Title: getBasePackageFloderFile
	 * @Description: 获取包文件目录文件对象
	 * @return File 包文件目录对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:04:45
	 */
	public File getBasePackageFloderFile() {
		return basePackageFloderFile;
	}
	
	/**
	 * <p>获取抽象api目录文件对象</p>
	 * @Title: getAbsFloderFile
	 * @Description: 获取抽象api目录文件对象
	 * @return File 抽象api目录对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:05:32
	 */
	public File getAbsFloderFile() {
		return absFloderFile;
	}
	
	/**
	 * <p>获取Entity目录文件对象</p>
	 * @Title: getEntityFloderFile
	 * @Description: 获取Entity目录文件对象
	 * @return File Entity目录对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:05:58
	 */
	public File getEntityFloderFile() {
		return entityFloderFile;
	}
	
	/**
	 * <p>获取Condition目录文件对象</p>
	 * @Title: getConditionFloderFile
	 * @Description: 获取Condition目录文件对象
	 * @return File Condition目录对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:06:46
	 */
	public File getConditionFloderFile() {
		return conditionFloderFile;
	}
	
	/**
	 * <p>获取Dao目录文件对象</p>
	 * @Title: getDaoFloderFile
	 * @Description: 获取Dao目录文件对象
	 * @return File Dao文件目录对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:07:20
	 */
	public File getDaoFloderFile() {
		return daoFloderFile;
	}
	
	/**
	 * <p>获取Mapper目录文件对象</p>
	 * @Title: getMapperFloderFile
	 * @Description: 获取Mapper目录文件对象
	 * @return File Mapper目录文件对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:07:58
	 */
	public File getMapperFloderFile() {
		return mapperFloderFile;
	}
	
	/**
	 * <p>获取ServiceImpl目录文件对象</p>
	 * @Title: getServiceImplFloderFile
	 * @Description: 获取ServiceImpl目录文件对象
	 * @return File ServiceImpl目录文件对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:09:24
	 */
	public File getServiceImplFloderFile() {
		return serviceImplFloderFile;
	}
	
	/**
	 * <p>获取Service目录文件对象</p>
	 * @Title: getServiceFloderFile
	 * @Description: 获取Service目录文件对象
	 * @return File Service目录文件对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午8:10:10
	 */
	public File getServiceFloderFile() {
		return serviceFloderFile;
	}

	public TempleteVariable getTempleteVariable() {
		if(null == templeteVariable) {
			setTempleteVariable(new TempleteVariable());
		}
		return templeteVariable;
	}

	private void setTempleteVariable(TempleteVariable templeteVariable) {
		this.templeteVariable = templeteVariable;
	}
	
}
