package com.neptunesoftware.venusWs.Controller;


import com.neptunesoftware.venusWs.Models.TrxnSmsList;
import com.neptunesoftware.venusWs.Models.Update;
import com.neptunesoftware.venusWs.Services.AlertService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;

@WebService(name = "XChannel", targetNamespace = "http://www.neptunesoftwareplc.com/")
public class XChannel {

    @WebMethod(operationName = "getTrxnAlerts")
    @WebResult(partName = "getTrxnAlertsResult")
    public TrxnSmsList getTrxnAlerts(@WebParam(name = "lastMsgId") @XmlElement(required = true) String lastMsgId,
                                     @WebParam(name = "vCode") @XmlElement(required = true) String vCode,
                                     @WebParam(name = "vPwd") @XmlElement(required = true) String vPwd) {
        if (vCode == null || !vCode.equals("neptune")) {
            return new TrxnSmsList("99", "Invalid Vendor Code supplied", null);
        } else if (vPwd == null || !vPwd.equalsIgnoreCase("p@ssw0rd")) {
            return new TrxnSmsList("99", "Invalid vendor password supplied", null);
        }
        try (AlertService alertUtil = new AlertService()) {
            return alertUtil.loadTransactionAlerts(lastMsgId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @WebMethod(operationName = "updateMessageCount")
    @WebResult(partName = "updateMessageCountResult")
    public Update updateMessageCount(@WebParam(name = "accountNo") @XmlElement(required = true) String accountNo,
                                     @WebParam(name = "msgCount") @XmlElement(required = true) int msgCount,
                                     @WebParam(name = "vCode") @XmlElement(required = true) String vCode,
                                     @WebParam(name = "vPwd") @XmlElement(required = true) String vPwd) {
        if (vCode == null || !vCode.equals("neptune")) {
            return new Update("99", "Invalid Vendor Code supplied", null);
        } else if (vPwd == null || !vPwd.equalsIgnoreCase("p@ssw0rd")) {
            return new Update("99", "Invalid vendor password supplied", null);
        }
        try (AlertService alertUtil = new AlertService()) {
            return alertUtil.updateAccountStats(accountNo, msgCount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
