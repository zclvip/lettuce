package com.zcl.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by fenglg1 on 2015/5/19.
 */
public class OrderInfo implements Serializable {

    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 用户ID
     */
    private String memberId;
    /**
     * 会员账号
     */
    private String memberCode;
    /**
     * 联想ID
     */
    private String lenovoId;
    /**
     * 商户ID (1：联想商城  2：Epp   3：神奇)
     */
    private int merchantId;
    /**
     * 平台ID（PC、WAP、APP）
     */
    private int platId;
    /**
     * 推广来源（1：百度  2：联盟  3：EDM）
     */
    private int sourceId;
    /**
     * 订单信息（Json）
     */
    private String orderInfo;
    /**
     * 购物车信息（Json）
     */
    private String cartInfo;
    /**
     * 状态，是否已读取
     */
    private int status;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 读取时间
     */
    private Timestamp readTime;
    /**
     * 读取次数
     */
    private int readTimes;

    private int shopId;

    private int terminal;

    private String currencyCode;//币种编码

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getLenovoId() {
        return lenovoId;
    }

    public void setLenovoId(String lenovoId) {
        this.lenovoId = lenovoId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getPlatId() {
        return platId;
    }

    public void setPlatId(int platId) {
        this.platId = platId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(String cartInfo) {
        this.cartInfo = cartInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }

    public int getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(int readTimes) {
        this.readTimes = readTimes;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }
}
