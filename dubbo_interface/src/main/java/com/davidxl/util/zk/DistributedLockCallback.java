package com.davidxl.util.zk;


public interface DistributedLockCallback {
    /**
     * 回调接口
     * @return 业务逻辑的结果
     */
    Object execute();
}
