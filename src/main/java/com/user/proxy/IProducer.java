package com.user.proxy;

/**
 * 对生产厂家要求的接口
 */
public interface IProducer {

    /**
     * 销售电脑
     * @param money
     */
    void saleProduct(float money);

    /**
     * 售后服务
     * @param money
     */
    void afterService(float money);
}
