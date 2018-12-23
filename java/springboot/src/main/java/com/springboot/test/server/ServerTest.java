package com.springboot.test.server;

import com.springboot.code.utils.SSHUtils;

public class ServerTest {

    public static void main (String[] args) {
        SSHUtils conn = SSHUtils.getInstance("39.108.64.144", 22, "root", "Ky32130930..");
        String result = conn.execute("cat /etc/redhat-release");
        System.out.println(result);
    }

}
