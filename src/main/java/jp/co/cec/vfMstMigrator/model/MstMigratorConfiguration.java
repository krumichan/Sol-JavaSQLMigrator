package jp.co.cec.vfMstMigrator.model;

import java.util.List;

public class MstMigratorConfiguration {

	public String v1ServerAddress;
	
	public String v1DBName;
	
	public String v1UserName;
	
	public String v1Password;
	
	public String v2ServerAddress;
	
	public String v2DBName;
	
	public String v2UserName;
	
	public String v2Password;
	
	public CharacterSet characterSet;
	
	public String validityDate;
	
	public String ddlPath;
	
	public String resultLogPath;
	
	public String errorLogPath;
	
	public String backupPath;
	
	public String dmlPath;
	
	public List<IdmMaster> idmMaster;
	
	public static class CharacterSet {
		
		public String Client;
		
		public String Connection;
		
		public String Database;
		
		public String Filesystem;
		
		public String Results;
		
		public String Server;
	}
	
	public static class IdmMaster {
		
		public String major;
		
		public List<Medium> medium;
	}
	
	public static class Medium {
		
		public String value;
		
		public List<String> small;
	}
}
