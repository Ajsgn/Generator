package cn.ajsgn.common.generator.vm.func;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import cn.ajsgn.common.generator.db.ColumnInfo;

public class BaseColumnsFunc extends Directive{

	@Override
	public String getName() {
		return "baseColumns";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		Object obj = context.get("columnInfos");
		String lineSeparator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder("\t");
		if(obj instanceof List){
			@SuppressWarnings("rawtypes")
			List list = (List) obj;
			for(int i=0; i<list.size(); i++){
				Object value = list.get(i);
				if(value instanceof ColumnInfo){
					ColumnInfo columnInfo = (ColumnInfo) value;
					sb.append(columnInfo.getColumnName()).append(", ");
				}
				if(0 == (i+1) % 6){
					sb.append(lineSeparator);
					sb.append("\t");
				}
			}
			if(sb.length() > 2){
				sb.delete(sb.lastIndexOf(","), sb.length());
				sb.append(lineSeparator);
			}
		}
		writer.write(sb.toString());
		return true;
	}

}
