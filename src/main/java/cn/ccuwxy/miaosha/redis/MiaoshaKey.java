package cn.ccuwxy.miaosha.redis;

public class MiaoshaKey extends BasePrefix {
    private MiaoshaKey( String prefix) {
        super(prefix);
    }
    private MiaoshaKey( int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60,"mp");
    public static MiaoshaKey verity_code = new MiaoshaKey(300,"vc");



}
