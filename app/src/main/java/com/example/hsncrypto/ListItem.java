package com.example.hsncrypto;

public class ListItem {

    private String btcName;

    private String btcPrice;

    public String getBtcIcon() {
        return btcIcon;
    }

    public void setBtcIcon(String btcIcon) {
        this.btcIcon = btcIcon;
    }

    private String btcIcon;

    public String getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice(String btcPrice) {
        this.btcPrice = btcPrice;
    }

    public ListItem(String btcName , String btcPrice,String btcIcon) {
        this.btcName = btcName;
        this.btcPrice = btcPrice;
        this.btcIcon = btcIcon;

    }

    public String getBtcName() {
        return btcName;
    }

    public void setBtcName(String btcName) {
        this.btcName = btcName;
    }
}
