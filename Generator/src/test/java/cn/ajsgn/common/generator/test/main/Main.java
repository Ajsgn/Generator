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
package cn.ajsgn.common.generator.test.main;

import java.sql.SQLException;

import cn.ajsgn.common.generator.core.Generator;
import cn.ajsgn.common.generator.db.TableConfig;

/**
 * <p>使用示例demo</p>
 * @ClassName: Main
 * @Description: 使用示例demo
 * @author Ajsgn@foxmail.com
 * @date 2017年10月22日 下午8:44:17
 */
public class Main {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		oracleTest();
		mysqlTest();
	}
	
	static void mysqlTest() throws ClassNotFoundException, SQLException {
		Generator generator = new Generator("jdbc:mysql://localhost:3306/test","root","123456","com.mysql.jdbc.Driver");
		generator.addTable(new TableConfig("test","TABLE1","Table1","d:/123456","cn.ajsgn.generator.test.mysql"));
//		generator.generator().stream().forEach(table -> table.generatorBusinessApi());
		generator.generator().stream().forEach(table -> table.generatorAll());
	}

	static void oracleTest() throws ClassNotFoundException, SQLException {
		Generator generator = new Generator("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger","oracle.jdbc.driver.OracleDriver");
		generator.addTable(new TableConfig("test","TABLE1","Table1","d:/123456","cn.ajsgn.generator.test.oracle"));
		generator.generator().stream().forEach(table -> table.generatorBusinessApi());
	}

}
