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
package cn.ajsgn.common.generator.db.config;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>模板变量</p>
 * @ClassName: TempleteVariable
 * @Description: TODO
 * @author Ajsgn@foxmail.com
 * @date 2017年11月10日 下午10:41:18
 */
public class TempleteVariable {
	
	/**
	 * 查询条件后缀
	 */
	private String conditionSuffix;
	/**
	 * dao后缀
	 */
	private String daoSuffix;
	/**
	 * service后缀
	 */
	private String serviceSuffix;
	/**
	 * service实现后缀
	 */
	private String implSuffix;
	/**
	 * mapper后缀
	 */
	private String mapperSuffix;
	
	/**
	 * abs的package路径
	 */
	private String absPackage;
	/**
	 * 查询条件的package路径
	 */
	private String conditionPackage;
	/**
	 * dao的package路径
	 */
	private String daoPackage;
	/**
	 * entity的package路径
	 */
	private String entityPackage;
	/**
	 * mapper的package路径
	 */
	private String mapperPackage;
	/**
	 * service的package路径
	 */
	private String servicePackage;
	/**
	 * service.impl的package路径
	 */
	private String implPackage;
	
	public String getConditionSuffix() {
		return StringUtils.isBlank(conditionSuffix) ? "Condition" : conditionSuffix;
	}
	
	public TempleteVariable setConditionSuffix(String conditionSuffix) {
		this.conditionSuffix = conditionSuffix;
		return this;
	}

	public String getDaoSuffix() {
		return StringUtils.isBlank(daoSuffix) ? "Dao" : daoSuffix;
	}

	public TempleteVariable setDaoSuffix(String daoSuffix) {
		this.daoSuffix = daoSuffix;
		return this;
	}

	public String getServiceSuffix() {
		return StringUtils.isBlank(serviceSuffix) ? "Service" : serviceSuffix;
	}

	public TempleteVariable setServiceSuffix(String serviceSuffix) {
		this.serviceSuffix = serviceSuffix;
		return this;
	}
	
	public String getImplSuffix() {
		return StringUtils.isBlank(implSuffix) ? "Impl" : implSuffix;
	}
	
	public TempleteVariable setImplSuffix(String implSuffix) {
		this.implSuffix = implSuffix;
		return this;
	}

	public String getMapperSuffix() {
		return StringUtils.isBlank(mapperSuffix) ? "Mapper" : mapperSuffix;
	}

	public TempleteVariable setMapperSuffix(String mapperSuffix) {
		this.mapperSuffix = mapperSuffix;
		return this;
	}

	public String getAbsPackage() {
		return StringUtils.isBlank(absPackage) ? "abs" : absPackage;
	}
	
	public TempleteVariable setAbsPackage(String absPackage) {
		this.absPackage = absPackage;
		return this;
	}

	public String getConditionPackage() {
		return StringUtils.isBlank(conditionPackage) ? "condition" : conditionPackage;
	}

	public TempleteVariable setConditionPackage(String conditionPackage) {
		this.conditionPackage = conditionPackage;
		return this;
	}

	public String getDaoPackage() {
		return StringUtils.isBlank(daoPackage) ? "dao" : daoPackage;
	}

	public TempleteVariable setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
		return this;
	}

	public String getEntityPackage() {
		return StringUtils.isBlank(entityPackage) ? "entity" : entityPackage;
	}

	public TempleteVariable setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
		return this;
	}

	public String getMapperPackage() {
		return StringUtils.isBlank(mapperPackage) ? "mapper" : mapperPackage;
	}

	public TempleteVariable setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
		return this;
	}

	public String getServicePackage() {
		return StringUtils.isBlank(servicePackage) ? "service" : servicePackage;
	}

	public TempleteVariable setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
		return this;
	}

	public String getImplPackage() {
		return StringUtils.isBlank(implPackage) ? "impl" : implPackage;
	}

	public TempleteVariable setImplPackage(String implPackage) {
		this.implPackage = implPackage;
		return this;
	}

}
