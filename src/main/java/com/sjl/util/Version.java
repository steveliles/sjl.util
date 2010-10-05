package com.sjl.util;

import java.util.*;

import com.sjl.util.collections.*;
import com.sjl.util.strings.*;

public class Version {
		
	public static final String MM_PATTERN = "([0-9]*)\\.([0-9]*)";
	public static final String MMM_PATTERN = "([0-9]*)\\.([0-9]*)\\.([0-9]*)";
	public static final String MMMB_PATTERN = "([0-9]*)\\.([0-9]*)\\.([0-9]*)\\.([0-9]*)";
	public static final String FULL_VERSION_PATTERN = "([0-9]*)\\.([0-9]*)\\.([0-9]*)\\.([0-9]*) \\((.*)\\)";
	
	public static final List<String> VERSION_COMPONENTS = Arrays.asList(new String[]{
		"majorVersion", "minorVersion", "maintenanceVersion", "buildNumber", "codeName"
	});	
	
	private String majorVersion;
	private String minorVersion;
	private String maintenanceVersion;
	private String buildNumber;
	private String codeName = "no code-name";
	private String string;

	public Version(String aVersionString) {				
		this(aVersionString, MM_PATTERN);
	}
		
	public Version(String aVersionString, String aVersionPattern) {
		if (!aVersionString.matches(aVersionPattern))
			throw new RuntimeException(String.format("Version string '%s' does not match pattern '%s'", aVersionString, aVersionPattern));
		
		FuncMap<String, ?> _map = Strings.captureAsMap(aVersionString, aVersionPattern, VERSION_COMPONENTS);		

		majorVersion = getValue(_map, 0);
		minorVersion = getValue(_map, 1);
		maintenanceVersion = getValue(_map, 2);
		buildNumber = getValue(_map, 3);
		codeName = getValue(_map, 4);
	}
	
	public Version(String... aComponents) {
		majorVersion = aComponents[0];
		minorVersion = (aComponents.length > 1) ? aComponents[1] : null;
		maintenanceVersion = (aComponents.length > 2) ? aComponents[2] : null;
		buildNumber = (aComponents.length > 3) ? aComponents[3] : null;
		codeName = (aComponents.length > 4) ? aComponents[4] : null;
	}
	
	public String getMajorVersion() {
		return majorVersion;
	}
	
	public String getMinorVersion() {
		return minorVersion;
	}
	
	public String getMaintenanceVersion() {
		return maintenanceVersion;
	}
	
	public String getBuildNumber() {
		return buildNumber;
	}
	
	public String getCodeName() {
		return codeName;
	}
	
	public String toString() {
		if (string == null) {
			string = Strings.joinWithDelimiter(".", majorVersion, minorVersion, maintenanceVersion, buildNumber);
			if (codeName != null)
				string = String.format("%s (%s)", string, codeName);
		}
		return string;
	}

	private String getValue(FuncMap<String, ?> aMap, int aKeyIndex) {
		return (String)aMap.get(VERSION_COMPONENTS.get(aKeyIndex));		
	}
	
}
