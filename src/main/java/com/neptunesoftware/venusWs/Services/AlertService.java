package com.neptunesoftware.venusWs.Services;


import com.google.gson.Gson;
import com.neptunesoftware.venusWs.Beans.VenusRestClient;
import com.neptunesoftware.venusWs.Models.ApiResponse;
import com.neptunesoftware.venusWs.Models.SMS;
import com.neptunesoftware.venusWs.Models.TrxnSmsList;
import com.neptunesoftware.venusWs.Models.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
public class AlertService implements AutoCloseable {


    public TrxnSmsList loadTransactionAlerts(String lastMsgId) {
        try {
            CompletableFuture<TrxnSmsList> future = VenusRestClient.postAsync(lastMsgId, "findTransactionAlerts", TrxnSmsList.class);

            if (!future.join().responseCode.equals("00")) {
                return new TrxnSmsList("96", "No records found", null);
            } else
                return new TrxnSmsList("0", "success", (List<SMS>) future.join().smsList);
        } catch (Exception e) {
            return new TrxnSmsList("96",
                    "An error occurred while processing your request.", null);
        }
    }

    public Update updateAccountStats(String acctNo, int msgCount) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("acctNo", acctNo);
        requestMap.put("msgCount", msgCount);
        try {
            String request = new Gson().toJson(requestMap);
            CompletableFuture<Update> future = VenusRestClient.postAsync(request, "updateAccountStats", Update.class);
            if (!future.join().getErrorCode().equals("00")) {
                return new Update("92", "No updates were performed", null);
            } else
                return new Update("00", "success", future.join().getMsgId());
        } catch (Exception e) {
            return new Update("96",
                    "An error occurred while processing your request ", null);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
