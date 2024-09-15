package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        Account result = accounts.putIfAbsent(account.id(), account);
        return result == null;
    }

    public synchronized boolean update(Account account) {
        Account result = accounts.put(account.id(), account);
        return result != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from.amount() >= amount) {
            update(new Account(fromId, from.amount() - amount));
            update(new Account(toId, to.amount() + amount));
            result = true;
        }
        return result;
    }
}

