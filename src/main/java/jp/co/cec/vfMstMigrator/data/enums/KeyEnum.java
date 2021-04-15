package jp.co.cec.vfMstMigrator.data.enums;

public enum KeyEnum {
	ExLangToExLang		("[from]mst_exchange_language" 	+ "-" + "[to]mst_exchange_language"					)
	,PlacSetToMap		("[from]mst_place_setting" 		+ "-" + "[to]mst_map"								)
	,HumanToPass		("[from]mst_human"				+ "-" + "[to]mst_password"							)
	,FaciToRes			("[from]mst_facility" 			+ "-" + "[to]mst_resource"							)
	,PlacSetToRes		("[from]mst_place_setting" 		+ "-" + "[to]mst_resource"							)
	,DatToSigAcq		("[from]mst_data_item" 			+ "-" + "[to]mst_signal_acquisition"				)
	,FaciConToSig		("[from]mst_facility_condition" + "-" + "[to]mst_signal"							)
	,DatToSig			("[from]mst_data_item" 			+ "-" + "[to]mst_signal"							)
	,FaciConToValCon	("[from]mst_facility_condition" + "-" + "[to]mst_value_conversion"					)
	,ThreToValCon		("[from]mst_threshold" 			+ "-" + "[to]mst_value_conversion"					)
	,ThreToViePaConf	("[from]mst_threshold" 			+ "-" + "[to]view_part_configuration"				)
	,ToolToSigAndSigAcq	("[from]MstMigrator"			+ "-" + "[to]mst_signal_And_mst_signal_acquisition"	);
	
	private final 	String 	str;
							KeyEnum	(String str) 	{ this.str = str; 	}
	@Override
	public String toString() {
		return this.str;
	}
}
