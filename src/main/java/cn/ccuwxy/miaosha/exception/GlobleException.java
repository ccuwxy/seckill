package cn.ccuwxy.miaosha.exception;

import cn.ccuwxy.miaosha.result.CodeMsg;

public class GlobleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobleException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
