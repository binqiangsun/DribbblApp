package com.dribbb.sun.core.service.account;

/**
 * Created by sunbinqiang on 9/26/16.
 */

public interface AccountListener {
    /**
     * 用户登录,退出登录
     */
    void onAccountChanged(AccountService sender);
}
