package jp.co.cec.vfMstMigrator.model;

/**
 * (x,y)座標をサポート
 */
public class Position2D {
	
	/**
	 * コンストラクタ
	 */
	public Position2D() { }
	
	/**
	 * コンストラクタ
	 */
	public Position2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * x座標
	 */
	public float x;
	
	/**
	 * y座標
	 */
	public float y;
}
