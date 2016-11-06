package com.suzie.byun.technicaproject;

/**
 * Created by suzie on 11/6/2016.
 */

public class Task {
    private String name;
    private boolean done;
    private long id;

    public Task(String name, boolean done, long id) {
        this.name = name;
        this.done = done;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
