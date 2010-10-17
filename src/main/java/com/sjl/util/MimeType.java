package com.sjl.util;

import java.util.*;

import com.sjl.util.collections.*;

public class MimeType {

	private static class MimeTypeValueFactory
	implements ValueFactory<String, MimeType>
	{
		@Override
		public MimeType create(String aMimeTypeString) {
			String[] _parts = aMimeTypeString.split("/");
			if (_parts.length == 2) {				
				return new MimeType(_parts[0], _parts[1]);
			} else {
				throw new RuntimeException("unable to parse mime-type from " + aMimeTypeString);
			}
		}			
	}
	
	private static final String[] EMPTY_ARRAY = new String[]{};
	
	private static FuncMap<String,  MimeType> CACHE = Maps.newMap(new MimeTypeValueFactory());
	
	// ----- COMMONLY USED MIMETYPES -----
	public static final MimeType APPLICATION_OCTET_STREAM = init("application", "octet-stream");
	public static final MimeType APPLICATION_PDF = init("application", "pdf", "pdf");
	public static final MimeType APPLICATION_ZIP = init("application", "zip", "zip");	
	
	public static final MimeType IMAGE_JPG = init("image", "jpg", "jpg");
	public static final MimeType IMAGE_JPEG = init("image", "jpeg", "jpeg");
	public static final MimeType IMAGE_BMP = init("image", "bmp", "bmp");
	public static final MimeType IMAGE_TIF = init("image", "tif", "tif");
	public static final MimeType IMAGE_GIF = init("image", "gif", "gif");
	public static final MimeType IMAGE_PNG = init("image", "png", "png");
	
	public static final MimeType TEXT_PLAIN = init("text", "plain", "txt");
	public static final MimeType TEXT_XML = init("text", "xml", "xml");		
	// -----------------------------------
	
	private String major;
	private String minor;
	private String stringRep;
	private String[] fileExts;
	
	public static MimeType parse(String aMime) {
		return CACHE.retrieve(aMime);
	}
	
	public static MimeType get(String aMajor, String aMinor) {
		return CACHE.retrieve(aMajor + "/" + aMinor);
	}
		
	public static MimeType init(String aMajor, String aMinor, String... aFileExts) {
		MimeType _result = get(aMajor, aMinor);		
		_result.addFileExtensions(aFileExts);		
		return _result;
	}	
		
	MimeType(String aMajor, String aMinor) {
		major = aMajor.toLowerCase();
		minor = aMinor.toLowerCase();							
	}
		
	public String getMajorType() {
		return major;
	}
	
	public String getMinorType() {
		return minor;
	}
	
	public void addFileExtensions(String... aFileExts) {
		if (aFileExts.length > 0)
			fileExts = Utils.splice(Utils.firstNonNull(fileExts, EMPTY_ARRAY), aFileExts);
	}
	
	public String getFileExtension() {
		return Utils.isEmpty(fileExts) ? "" : fileExts[0];
	}
	
	public List<String> getFileExtensions() {
		return Arrays.asList(fileExts);
	}
	
	public String toString() {
		if (stringRep == null)
			stringRep = major + "/" + minor;
		return stringRep;
	}

	@Override
	public boolean equals(Object anOther) {
		if (anOther instanceof MimeType)
		{
			return this.toString().equals(anOther.toString());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode() ^ toString().hashCode();
	}	
	
}
