package com.xiaowei.eventbus;

public abstract class FuctionNoParamHasResult<T> extends Fuction {
    public FuctionNoParamHasResult(String fuctionName) {
        super(fuctionName);
    }

    public abstract T fuction();
}
