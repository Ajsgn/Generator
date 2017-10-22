package cn.ajsgn.common.generator.util;

import java.io.File;

public class FileUtil {
	
	public static void mkdirsIfNotExists(File... files){
		for(File file:files){
			if(false == file.exists()){
				file.mkdirs();
			}else{
				if(false == file.isDirectory()){
					throw new IllegalArgumentException(String.format("the same file has exist ~", file.getAbsolutePath()));
				}
			}
		}
	}
	
}
