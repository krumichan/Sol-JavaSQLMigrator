package jp.co.cec.vfMstMigrator.command;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import jp.co.cec.vfMstMigrator.command.executor.GetV1Command;
import jp.co.cec.vfMstMigrator.command.executor.GetV1ViewPartConfCommand;
import jp.co.cec.vfMstMigrator.command.executor.InsertV2Command;
import jp.co.cec.vfMstMigrator.command.manufacturing.MstExchangeLanguageManufacturing;
import jp.co.cec.vfMstMigrator.command.manufacturing.MstMapManufacturing;
import jp.co.cec.vfMstMigrator.command.manufacturing.MstPasswordManufacturing;
import jp.co.cec.vfMstMigrator.command.manufacturing.MstResourceManufacturing;
import jp.co.cec.vfMstMigrator.command.manufacturing.MstSignalAcquisitionManufacturing;
import jp.co.cec.vfMstMigrator.command.manufacturing.MstSignalManufacturing;
import jp.co.cec.vfMstMigrator.command.manufacturing.MstValueConversionManufacturing;
import jp.co.cec.vfMstMigrator.command.manufacturing.ViewPartConfigurationManufacturing;
import jp.co.cec.vfMstMigrator.data.enums.KeyEnum;
import jp.co.cec.vfMstMigrator.template.Command;

/**
 * コマンドファクトリー
 */
public class CommandFactory {
	
	/**
	 * シングルトンパターン
	 */
	private static CommandFactory instance = null;
	public 	static CommandFactory get() {
		if (instance == null) {
			instance = new CommandFactory();
		}
		
		return instance;
	}
	
	/**
	 * コンストラクタ
	 */
	private CommandFactory() {
		commands = new HashMap<String, Command[]>();
		Command command = new GetV1Command();
		
		commands.put(KeyEnum.PlacSetToRes	.toString()	, new Command[] { command						, new MstResourceManufacturing				()});
		commands.put(KeyEnum.FaciToRes		.toString()	, new Command[] { command						, new MstResourceManufacturing				()});
		commands.put(KeyEnum.PlacSetToMap	.toString()	, new Command[] { command						, new MstMapManufacturing					()});
		commands.put(KeyEnum.DatToSig		.toString()	, new Command[] { command						, new MstSignalManufacturing				()});
		commands.put(KeyEnum.DatToSigAcq	.toString()	, new Command[] { command						, new MstSignalAcquisitionManufacturing		()});
		commands.put(KeyEnum.FaciConToValCon.toString()	, new Command[] { command						, new MstValueConversionManufacturing		()});
		commands.put(KeyEnum.FaciConToSig	.toString()	, new Command[] { command						, new MstSignalManufacturing				()});
		commands.put(KeyEnum.ThreToValCon	.toString()	, new Command[] { command						, new MstValueConversionManufacturing		()});
		commands.put(KeyEnum.ThreToViePaConf.toString()	, new Command[] { new GetV1ViewPartConfCommand(), new ViewPartConfigurationManufacturing	()});
		commands.put(KeyEnum.ExLangToExLang	.toString()	, new Command[] { command						, new MstExchangeLanguageManufacturing		()});
		commands.put(KeyEnum.HumanToPass   	.toString()	, new Command[] { command						, new MstPasswordManufacturing				()});
	}
	
	/**
	 * コマンドリスト
	 */
	private final HashMap<String, Command[]> commands;
	
	/**
	 * Insert Command
	 */
	private static Command insert;
	
	/**
	 * insert commandをリターン
	 * @return
	 */
	public Command insertCommand() {
		return ((insert == null) ? insert = new InsertV2Command() : insert);
	}
	
	/**
	 * コマンド取得メソッド
	 */
	public Command[] getCommand(String key) {
		if (commands.containsKey(key)) {
			return commands.get(key);
		}
		return null;
	}

	/**
	 * 全てのキーをリターン
	 */
	public Set<String> getKeys() {
		return commands.keySet();
	}

	/**
	 *　From Table名をリターン 
	 */
	public Set<String> getV2TableNames() {
		Function<String, String> f = key -> key.substring(key.indexOf("[to]") + 4).trim();
		return getKeys()
				.stream()
				.filter(key -> !key.contains("configuration"))
				.map(f)
				.collect(Collectors.toSet());
	}
}