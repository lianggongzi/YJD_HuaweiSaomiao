package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018\8\13 0013.
 */

class PO {
    String firm_PAKID;
    String firm_PN;
    String firm_PO;
    String firm_MFGCODE;
    String firm_DATE;
    String firm_GW;
    String firm_NW;
    String firm_MLot;
    String firm_Qty;

    public PO(String firm_PAKID, String firm_PN, String firm_PO, String firm_MFGCODE, String firm_DATE, String firm_GW, String firm_NW, String firm_MLot, String firm_Qty) {
        this.firm_PAKID = firm_PAKID;
        this.firm_PN = firm_PN;
        this.firm_PO = firm_PO;
        this.firm_MFGCODE = firm_MFGCODE;
        this.firm_DATE = firm_DATE;
        this.firm_GW = firm_GW;
        this.firm_NW = firm_NW;
        this.firm_MLot = firm_MLot;
        this.firm_Qty = firm_Qty;
    }

    public String getFirm_PAKID() {
        return firm_PAKID;
    }

    public void setFirm_PAKID(String firm_PAKID) {
        this.firm_PAKID = firm_PAKID;
    }

    public String getFirm_PN() {
        return firm_PN;
    }

    public void setFirm_PN(String firm_PN) {
        this.firm_PN = firm_PN;
    }

    public String getFirm_PO() {
        return firm_PO;
    }

    public void setFirm_PO(String firm_PO) {
        this.firm_PO = firm_PO;
    }

    public String getFirm_MFGCODE() {
        return firm_MFGCODE;
    }

    public void setFirm_MFGCODE(String firm_MFGCODE) {
        this.firm_MFGCODE = firm_MFGCODE;
    }

    public String getFirm_DATE() {
        return firm_DATE;
    }

    public void setFirm_DATE(String firm_DATE) {
        this.firm_DATE = firm_DATE;
    }

    public String getFirm_GW() {
        return firm_GW;
    }

    public void setFirm_GW(String firm_GW) {
        this.firm_GW = firm_GW;
    }

    public String getFirm_NW() {
        return firm_NW;
    }

    public void setFirm_NW(String firm_NW) {
        this.firm_NW = firm_NW;
    }

    public String getFirm_MLot() {
        return firm_MLot;
    }

    public void setFirm_MLot(String firm_MLot) {
        this.firm_MLot = firm_MLot;
    }

    public String getFirm_Qty() {
        return firm_Qty;
    }

    public void setFirm_Qty(String firm_Qty) {
        this.firm_Qty = firm_Qty;
    }
}
