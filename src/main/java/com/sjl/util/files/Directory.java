package com.sjl.util.files;

import java.util.*;

public interface Directory 
extends FileSystemNode {

	public File newFile(String aPath);
	
	public Directory newDirectory(String aPath);
	
	public List<FileSystemNode> listContents();
	
}
