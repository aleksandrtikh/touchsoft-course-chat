package com.aleksandrtikh.tschat.server;

import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserBookerImpl implements UserBooker {

    private ConcurrentLinkedQueue<User> freeCustomers = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<User> freeAgents = new ConcurrentLinkedQueue<>();
    private HashSet<Runnable> freeEventListeners = new HashSet<>();

    @Override
    public boolean isUserFree(User user) {
        return freeAgents.contains(user) || freeCustomers.contains(user);
    }

    @Override
    public void freeAgent(User user) {
        if (user.getRole() == User.Role.AGENT) {
            freeAgents.offer(user);
        } else { throw new IllegalArgumentException("User has a wrong role");
        }
        triggerFreeEvent();
    }

    @Override
    public void freeCustomer(User user) {
        if (user.getRole() == User.Role.CUSTOMER) {
            freeCustomers.offer(user);
        } else { throw new IllegalArgumentException("User has a wrong role");
        }
        triggerFreeEvent();
    }


    private void triggerFreeEvent() {
        for (Runnable listener : freeEventListeners) {
            listener.run();
        }
    }

    @Override
    public boolean bookUser(User user) {
        switch (user.getRole()) {
            case CUSTOMER: return freeCustomers.remove(user);
            case AGENT: return freeAgents.remove(user);
            default: throw new IllegalArgumentException("User has an unknown role");
        }
    }

    @Override
    public User bookNextAgent() {
        return freeAgents.poll();
    }

    @Override
    public User bookNextCustomer() {
        return freeCustomers.poll();
    }

    @Override
    public boolean hasFreePair() {
        return !freeCustomers.isEmpty() && !freeAgents.isEmpty();
    }

    @Override
    public void addFreeEventListener(Runnable listener) {
        freeEventListeners.add(listener);
    }

    @Override
    public void removeFreeEventListener(Runnable listener) {
        freeEventListeners.remove(listener);
    }
}
