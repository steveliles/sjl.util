package com.sjl.util.files;

public interface FileSystem {
	
	public Directory newDirectory(String aPath);
	
	public File newFile(String aPath);
	
}
