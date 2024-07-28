package com.springreactive.testwebflux.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DummyModelResponse {
    public ArrayList<DummyModelInfo> posts;
}
