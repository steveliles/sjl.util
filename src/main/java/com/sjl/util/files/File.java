package com.sjl.util.files;

import java.io.*;

public interface File 
extends FileSystemNode {	

	public InputStream getInputStream();
	
	public OutputStream getOuputStream();
	
}
