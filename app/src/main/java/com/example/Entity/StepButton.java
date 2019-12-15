package com.example.Entity;

public class StepButton {
    private String num;
    private boolean check;//是否选中
    private boolean enable;//是否可用

    public StepButton() {
    }

    public StepButton(String num, boolean check, boolean enable) {
        this.num = num;
        this.check = check;
        this.enable = enable;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}