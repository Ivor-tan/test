package com.example.myTest.Listener;

import java.util.List;

public interface PermissionListener {
    void granted();
    void denied(List<String> deniedList);
}