package teumin.server.account;

import teumin.server.account.Account;

import java.util.ArrayList;

/**
 * 서버에서 현재 접속 중인 계정을 저장하는 용도
 */
public class Accounts {
    private Accounts() {}

    private static ArrayList<Account> accountList;

    public static ArrayList<Account> getAccountList() {
        return accountList;
    }

    public static void initialize() {
        accountList = new ArrayList<Account>();
    }

    public static Account getNewAccount() {
        Account account = new Account();

        synchronized (accountList) {
            accountList.add(account);
        }

        return account;
    }

    public static void remove(Account account) {
        synchronized (accountList) {
            accountList.remove(account);
        }
    }
}
