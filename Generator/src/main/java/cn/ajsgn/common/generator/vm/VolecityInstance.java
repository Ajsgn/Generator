package cn.ajsgn.common.generator.vm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VolecityInstance {
	
	private VelocityEngine ve = new VelocityEngine();

	private VolecityInstance(){
		
	}
	
	public static VolecityInstance init(){
		VolecityInstance instance = new VolecityInstance();
		// 初始化模板引擎
		instance.ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		instance.ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		instance.ve.loadDirective("cn.ajsgn.common.generator.vm.func.BaseColumnsFunc");
		instance.ve.init();
	    return instance;
	}
	
	public void flush(String classPathResource, Map<String,Object> context, Writer targetWriter) throws IOException{
		if(StringUtils.isBlank(classPathResource) || null == context || null == targetWriter){
			return;
		}
	    Template templete = ve.getTemplate(classPathResource);
	    VelocityContext vContext = new VelocityContext(new LinkedHashMap<String,Object>());
	    for(Map.Entry<String, Object> entry : context.entrySet()){
	    	vContext.put(entry.getKey(), entry.getValue());
	    }
	    templete.merge(vContext,targetWriter);
		targetWriter.flush();
	}
	
	public static void main(String[] args) throws IOException {
		VolecityInstance instance = init();
		
		FileWriter writer = new FileWriter(new File("e:/xxxx.java"));
		Map<String,Object> context = new HashMap<String,Object>();
		context.put("createDate", DateFormatUtils.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
		
		instance.flush("cn/ajsgn/common/generator/templete/dao/DaoCondition.vm", context, writer);
		
	}
	
	
}
