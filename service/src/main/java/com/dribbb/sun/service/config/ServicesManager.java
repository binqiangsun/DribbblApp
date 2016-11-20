package com.dribbb.sun.service.config;

import android.content.Context;
import com.dribbb.sun.service.account.AccountService;
import java.util.HashMap;

/**
 * Created by feifengxu on 15/1/3.
 */
public class ServicesManager {

    public static final String ACCOUNT_SERVICE = "account";

    private HashMap<String,Object> servicesMap = new HashMap<>();

    private static ServicesManager servicesManager;

    private ServicesManager(Context context) {
        servicesMap.put(ACCOUNT_SERVICE, new AccountService(context));
    }

    public static ServicesManager instance(Context context) {
        if (servicesManager == null) {
            servicesManager = new ServicesManager(context);
        }
        return servicesManager;
    }

    public Object getService(String serviceName) {
        return servicesMap.get(serviceName);
    }

}
