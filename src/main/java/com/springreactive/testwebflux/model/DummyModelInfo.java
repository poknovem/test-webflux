package com.springreactive.testwebflux.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DummyModelInfo {
    public int id;
    public String title;
    public String body;
    public ArrayList<String> tags;
    public DummyModelReactions reactions;
    public int views;
    public int userId;
}
