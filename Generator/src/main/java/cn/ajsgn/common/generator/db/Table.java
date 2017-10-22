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

public class Table {

	private TableConfig tableConfig;
	private List<ColumnInfo> columnInfos;
	private ColumnMapping columnMapping;
	
	private VolecityInstance instance;
	
	public Table(String driverClass, TableConfig tableConfig, List<ColumnInfo> columnInfos){
		this.tableConfig = tableConfig;
		this.columnInfos = columnInfos;
		this.columnMapping = ColumnMappingFactory.instanceOf(driverClass);
		
		if(null == columnMapping){
			throw new RuntimeException("Database not support : " + driverClass);
		}
	}

	public String getTableName() {
		return tableConfig.getTableName();
	}

	public String getSchemaName() {
		return tableConfig.getSchemaName();
	}
	
	public void generatorAbsApi(){
		generatorAbstractServiceImpl();
		generatorBaseDao();
		generatorBaseService();
		generatorDaoCondition();
	}
	
	public void generatorBusinessApi(){
		generatorCondition();
		generatorDao();
		generatorEntity();
		generatorMapper();
		generatorService();
		generatorServiceImpl();
	}
	
	public void generatorAll(){
		generatorAbsApi();
		generatorBusinessApi();
	}
	
	public void generatorDaoCondition(){
		File parentFile = tableConfig.getAbsFloderFile();
		File targetFile = new File(parentFile,"DaoCondition.java");
		flushFile("cn/ajsgn/common/generator/templete/abs/DaoCondition.vm", targetFile,new LinkedHashMap<String,Object>());
	}
	
	public void generatorAbstractServiceImpl(){
		File parentFile = tableConfig.getAbsFloderFile();
		File targetFile = new File(parentFile,"AbstractServiceImpl.java");
		flushFile("cn/ajsgn/common/generator/templete/abs/AbstractServiceImpl.vm", targetFile,new LinkedHashMap<String,Object>());
	}
	
	public void generatorBaseDao(){
		File parentFile = tableConfig.getAbsFloderFile();
		File targetFile = new File(parentFile,"BaseDao.java");
		flushFile("cn/ajsgn/common/generator/templete/abs/BaseDao.vm", targetFile, new LinkedHashMap<String,Object>());
	}
	
	public void generatorBaseService(){
		File parentFile = tableConfig.getAbsFloderFile();
		File targetFile = new File(parentFile,"BaseService.java");
		flushFile("cn/ajsgn/common/generator/templete/abs/BaseService.vm", targetFile, new LinkedHashMap<String,Object>());
	}
	
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
	
	public void generatorDao(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getDaoFloderFile();
		File targetFile = new File(parentFile,beanName+"Dao.java");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		
		flushFile("cn/ajsgn/common/generator/templete/dao/Dao.vm", targetFile, params);
	}
	
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
	
	public void generatorService(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getServiceFloderFile();
		File targetFile = new File(parentFile,beanName+"Service.java");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		
		flushFile("cn/ajsgn/common/generator/templete/service/Service.vm", targetFile, params);
	}
	
	public void generatorServiceImpl(){
		String beanName = tableConfig.getBeanName();
		File parentFile = tableConfig.getServiceImplFloderFile();
		File targetFile = new File(parentFile,beanName+"ServiceImpl.java");
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("beanName", beanName);
		params.put("lowBeanName", StrKit.firstCharToLowerCase(beanName));
		
		flushFile("cn/ajsgn/common/generator/templete/service/impl/ServiceImpl.vm", targetFile, params);
	}
	
	
	public void flushFile(String classpathTempleteResource, File targetFile, Map<String,Object> params){
		try(Writer fw = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8")){
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			map.put("createDate_", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
			map.put("basePackage_", tableConfig.getBasePackage());

			map.putAll(params);
			flushAndCloseStream(classpathTempleteResource, map, fw);
			System.out.println("输出文件：" + targetFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void flushAndCloseStream(String classPathTempleteResource, Map<String,Object> params, Writer targetWriter) throws IOException{
		if(null == instance){
			instance = VolecityInstance.init();
		}
		instance.flush(classPathTempleteResource, params, targetWriter);
	}
	
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
