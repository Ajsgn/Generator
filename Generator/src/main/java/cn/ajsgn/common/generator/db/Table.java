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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import cn.ajsgn.common.generator.db.column.ColumnMapping;
import cn.ajsgn.common.generator.db.column.ColumnMappingFactory;
import cn.ajsgn.common.generator.util.StrKit;
import cn.ajsgn.common.generator.vm.VolecityInstance;

/**
 * <p>一张表对象</p>
 * <p>如果需要生成文件，只需要调用相应的api即可</p>
 * @ClassName: Table
 * @Description: 如果需要生成文件，只需要调用相应的api即可</
 * @author Ajsgn@foxmail.com
 * @date 2017年10月22日 下午4:10:14
 */
public class Table {
	
	private TableConfig tableConfig;
	private List<ColumnInfo> columnInfos;
	private ColumnMapping columnMapping;
	
	private VolecityInstance instance;
	
	/**
	 * <p>构造函数</p>
	 * @param driverClass 驱动类
	 * @param tableConfig 表配置描述
	 * @param columnInfos 数据库表列信息描述
	 */
	public Table(String driverClass, TableConfig tableConfig, List<ColumnInfo> columnInfos){
		this.tableConfig = tableConfig;
		this.columnInfos = columnInfos;
		this.columnMapping = ColumnMappingFactory.instanceOf(driverClass);
		
		if(null == columnMapping){
			throw new RuntimeException("Database not support : " + driverClass);
		}
	}
	
	/**
	 * <p>获取当前对应表名称</p>
	 * @Title: getTableName
	 * @Description: 获取当前对应表名称
	 * @return String 表名称
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午4:12:42
	 */
	public String getTableName() {
		return tableConfig.getTableName();
	}
	
	/**
	 * <p>获取当前表的schema名称</p>
	 * @Title: getSchemaName
	 * @Description: 获取当前表的schema名称
	 * @return String 当前表对赢的shcema
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午4:13:05
	 */
	public String getSchemaName() {
		return tableConfig.getSchemaName();
	}
	
	/**
	 * <p>生成公共api模板</p>
	 * <li>DaoCondition</li>
	 * <li>BaseDao</li>
	 * <li>BaseService</li>
	 * <li>AbstractServiceImpl</li>
	 * @Title: generatorAbsApi
	 * @Description: 生成公共api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:10:03
	 */
	public void generatorAbsApi(){
		generatorDaoCondition();
		generatorBaseDao();
		generatorBaseService();
		generatorAbstractServiceImpl();
	}
	
	/**
	 * <p>生成与业务相关的api模板</p>
	 * <li>Entity</li>
	 * <li>Condition</li>
	 * <li>Dao</li>
	 * <li>Mapper</li>
	 * <li>Service</li>
	 * <li>ServiceImpl</li>
	 * @Title: generatorBusinessApi
	 * @Description: 生成与业务相关的api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:12:54
	 */
	public void generatorBusinessApi(){
		generatorEntity();
		generatorCondition();
		generatorDao();
		generatorMapper();
		generatorService();
		generatorServiceImpl();
	}
	
	/**
	 * <p>生成所有的api模板</p>
	 * <p>相当于调用了 generatorAbsApi() + generatorBusinessApi()</p>
	 * @Title: generatorAll
	 * @Description: 生成所有的api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:16:42
	 */
	public void generatorAll(){
		generatorAbsApi();
		generatorBusinessApi();
	}
	
	/**
	 * <p>生成 DaoCondition api模板</p>
	 * @Title: generatorDaoCondition
	 * @Description: 生成DaoCondition api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:20:57
	 */
	public void generatorDaoCondition(){
		File parentFile = tableConfig.getAbsFloderFile();
		File targetFile = new File(parentFile,"DaoCondition.java");
		flushFile("cn/ajsgn/common/generator/templete/abs/DaoCondition.vm", targetFile,new LinkedHashMap<String,Object>());
	}
	
	/**
	 * <p>生成AbstractServiceImpl api模板</p>
	 * @Title: generatorAbstractServiceImpl
	 * @Description: 生成AbstractServiceImpl
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:22:27
	 */
	public void generatorAbstractServiceImpl(){
		File parentFile = tableConfig.getAbsFloderFile();
		File targetFile = new File(parentFile,"AbstractServiceImpl.java");
		flushFile("cn/ajsgn/common/generator/templete/abs/AbstractServiceImpl.vm", targetFile,new LinkedHashMap<String,Object>());
	}
	
	/**
	 * <p>生成BaseDao api模板</p>
	 * @Title: generatorBaseDao
	 * @Description: 生成BaseDao api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:23:10
	 */
	public void generatorBaseDao(){
		File parentFile = tableConfig.getAbsFloderFile();
		File targetFile = new File(parentFile,"BaseDao.java");
		flushFile("cn/ajsgn/common/generator/templete/abs/BaseDao.vm", targetFile, new LinkedHashMap<String,Object>());
	}
	
	/**
	 * <p>生成BaseService api模板</p>
	 * @Title: generatorBaseService
	 * @Description: 生成BaseService api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:24:04
	 */
	public void generatorBaseService(){
		File parentFile = tableConfig.getAbsFloderFile();
		File targetFile = new File(parentFile,"BaseService.java");
		flushFile("cn/ajsgn/common/generator/templete/abs/BaseService.vm", targetFile, new LinkedHashMap<String,Object>());
	}
	
	/**
	 * <p>生成Entity api模板</p>
	 * @Title: generatorEntity
	 * @Description: 生成Entity api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:24:33
	 */
	public void generatorEntity(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getEntityFloderFile();
		File targetFile = new File(parentFile,beanName+".java");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		params.put("columnInfos", columnInfos);
		params.put("columnMapping", columnMapping);
		
		flushFile("cn/ajsgn/common/generator/templete/entity/Entity.vm", targetFile, params);
	}
	
	/**
	 * <p>生成Condition api模板</p>
	 * @Title: generatorCondition
	 * @Description: 生成Condition api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:29:03
	 */
	public void generatorCondition(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getConditionFloderFile();
		File targetFile = new File(parentFile,beanName+"Condition.java");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		params.put("columnInfos", columnInfos);
		params.put("columnMapping", columnMapping);
		
		flushFile("cn/ajsgn/common/generator/templete/condition/Condition.vm", targetFile, params);
	}
	
	/**
	 * <p>生成Dao api模板</p>
	 * @Title: generatorDao
	 * @Description: 生成Dao api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:29:47
	 */
	public void generatorDao(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getDaoFloderFile();
		File targetFile = new File(parentFile,beanName+"Dao.java");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		
		flushFile("cn/ajsgn/common/generator/templete/dao/Dao.vm", targetFile, params);
	}
	
	/**
	 * <p>生成Mapper api模板</p>
	 * @Title: generatorMapper
	 * @Description: 生成Mapper api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:30:16
	 */
	public void generatorMapper(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getMapperFloderFile();
		File targetFile = new File(parentFile,beanName+"Mapper.xml");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		params.put("schemaName", getSchemaName());
		params.put("tableName", getTableName());
		params.put("columnInfos", columnInfos);
		params.put("columnMapping", columnMapping);
		params.put("primaryKeys", primaryKeys());
		
		flushFile("cn/ajsgn/common/generator/templete/mapper/Mapper.vm", targetFile, params);
	}
	
	/**
	 * <p>生成Service api模板</p>
	 * @Title: generatorService
	 * @Description: 生成Service api模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:30:53
	 */
	public void generatorService(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getServiceFloderFile();
		File targetFile = new File(parentFile,beanName+"Service.java");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		
		flushFile("cn/ajsgn/common/generator/templete/service/Service.vm", targetFile, params);
	}
	
	/**
	 * <p>生成ServiceImpl api模板</p>
	 * @Title: generatorServiceImpl
	 * @Description: 生成ServiceImpl模板
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:31:23
	 */
	public void generatorServiceImpl(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getServiceImplFloderFile();
		File targetFile = new File(parentFile,beanName+"ServiceImpl.java");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		params.put("lowBeanName", StrKit.firstCharToLowerCase(beanName));
		
		flushFile("cn/ajsgn/common/generator/templete/service/impl/ServiceImpl.vm", targetFile, params);
	}
	
	/**
	 * <p>模板文件输出文件输出</p>
	 * @Title: flushFile
	 * @Description: 模板文件输出
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:32:06
	 */
	public void flushFile(String classpathTempleteResource, File targetFile, Map<String,Object> params){
		try(Writer fw = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8")){
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			map.put("createDate_", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
			map.put("basePackage_", tableConfig.getBasePackage());

			map.putAll(params);
			flush(classpathTempleteResource, map, fw);
			System.out.println("输出文件：" + targetFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>模板文件输出</p>
	 * @Title: flush
	 * @Description: 模板文件输出
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:35:27
	 */
	private void flush(String classPathTempleteResource, Map<String,Object> params, Writer targetWriter) throws IOException{
		if(null == instance){
			instance = VolecityInstance.init();
		}
		instance.flush(classPathTempleteResource, params, targetWriter);
	}
	
	/**
	 * <p>主键描述拼接，用于Mapper</p>
	 * @Title: primaryKeys
	 * @Description: 主键描述拼接，用于Mapper
	 * @author Ajsgn@foxmail.com
	 * @date 2017年10月22日 下午7:35:52
	 */
	private List<ColumnInfo> primaryKeys(){
		List<ColumnInfo> primaryKeys = new ArrayList<ColumnInfo>();
		String[] pks = tableConfig.getPrimaryKeys();
		for(String pk:pks){
			for(ColumnInfo columnInfo : columnInfos){
				if(StringUtils.equalsIgnoreCase(pk, columnInfo.getColumnName())){
					primaryKeys.add(columnInfo);
				}
			}
		}
		return primaryKeys;
	}
	
}
