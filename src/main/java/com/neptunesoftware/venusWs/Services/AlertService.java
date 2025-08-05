package com.neptunesoftware.venusWs.Services;


import com.google.gson.Gson;
import com.neptunesoftware.venusWs.Beans.VenusRestClient;
import com.neptunesoftware.venusWs.Models.TrxnSmsList;
import com.neptunesoftware.venusWs.Models.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
public class AlertService  implements AutoCloseable {



    public TrxnSmsList loadTransactionAlerts(String lastMsgId) {
       try {
           CompletableFuture<TrxnSmsList> future = VenusRestClient.postAsync(lastMsgId, "findTransactionAlerts", TrxnSmsList.class);
           return future.join();
       }
       catch (Exception e) {
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

            return future.join();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new Update("96",
                    "An error occurred while processing your request ", null);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
