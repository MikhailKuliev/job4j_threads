package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public synchronized boolean add(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("aккаунта не существует");
        }
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("аккаунта не существует");
        }
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше 0");

        }
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from == null || to == null) {
            throw new IllegalStateException("аккаунт не найден !");
        }
        if (from.amount() < amount) {
            throw new IllegalStateException("не достаточно средств");
        }
        accounts.put(fromId, new Account(fromId, from.amount() - amount));
        accounts.put(toId, new Account(toId, to.amount() + amount));

        return true;
    }
}

