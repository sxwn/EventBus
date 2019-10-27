package com.xiaowei.eventbus;

public abstract class FuctionHasParamNoResult<P> extends Fuction {
    public FuctionHasParamNoResult(String fuctionName) {
        super(fuctionName);
    }

    public abstract void fuction(P p);
}
