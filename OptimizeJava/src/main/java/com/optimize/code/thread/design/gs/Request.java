package com.optimize.code.thread.design.gs;

/**
 * ClassName: Request
 * Description:
 * Date: 2016/6/14 21:37
 *
 * @author SAM SHO
 * @version V1.0
 */
public class Request {

    private String name;

    public Request() {
    }

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }
}
