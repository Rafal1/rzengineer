package com.elektryczny.rzengineer.android.messages;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String from;
    private String to;
    private Date dateSent;
    private Date dateRecieved;
    private String content;
    private String RSAEncrypted;

    public String getRSAEncrypted() {
        return RSAEncrypted;
    }

    public void setRSAEncrypted(String RSAencrypted) {
        this.RSAEncrypted = RSAencrypted;
    }

    private Long webServiceId;

    public Long getWebServiceId() {
        return webServiceId;
    }

    public void setWebServiceId(Long webServiceId) {
        this.webServiceId = webServiceId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Date getDateRecieved() {
        return dateRecieved;
    }

    public void setDateRecieved(Date dateRecieved) {
        this.dateRecieved = dateRecieved;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
