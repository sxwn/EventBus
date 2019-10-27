package com.xiaowei.eventbus;

public abstract class FuctionHasParamHasResult<T,P> extends Fuction {
    public FuctionHasParamHasResult(String fuctionName) {
        super(fuctionName);
    }

    public abstract T fuction(P p);
}
