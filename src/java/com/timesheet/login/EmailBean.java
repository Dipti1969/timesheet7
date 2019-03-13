package com.timesheet.login;

import java.io.Serializable;
import java.util.Date;

public class EmailBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String emailSubject = null;
    private String emailMesaage = null;
    private Date emailDate = null;
    private String fromAddress = null;
    private String toAddress = null;
    private boolean isHTMLContents = false;
    private boolean isSent = false;

    public EmailBean() {
        super();
        this.emailSubject = null;
        this.emailMesaage = null;
        this.emailDate = null;
        this.fromAddress = null;
        this.toAddress = null;
        this.isHTMLContents = false;
        this.isSent = false;
    }

    public EmailBean(
            String emailSubject,
            String emailMesaage,
            Date emailDate,
            String fromAddress,
            String toAddress,
            boolean isHTMLContents) {
        super();

        this.emailSubject = emailSubject;
        this.emailMesaage = emailMesaage;
        this.emailDate = emailDate;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.isHTMLContents = isHTMLContents;
    }

    public Date getEmailDate() {
        return emailDate;
    }

    public void setEmailDate(Date emailDate) {
        this.emailDate = emailDate;
    }

    public String getEmailMesaage() {
        return emailMesaage;
    }

    public void setEmailMesaage(String emailMesaage) {
        this.emailMesaage = emailMesaage;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public boolean isHTMLContents() {
        return isHTMLContents;
    }

    public void setHTMLContents(boolean isHTMLContents) {
        this.isHTMLContents = isHTMLContents;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean isSent) {
        this.isSent = isSent;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
}