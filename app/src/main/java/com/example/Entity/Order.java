package com.example.Entity;

public class Order{
    private String type;
    private String orderState;
    private int avatarId;
    private String count;
    private String countTime;

    public Order(String type, String orderState, int avatarId, String countTime) {
        this.type = type;
        this.orderState = orderState;
        this.avatarId = avatarId;
        this.countTime = countTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderState() {
        return "调度状态："+orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCountTime() {
        return "倒计时："+countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }
}
