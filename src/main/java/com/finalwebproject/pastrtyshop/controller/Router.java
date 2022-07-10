package com.finalwebproject.pastrtyshop.controller;

public class Router {

    public enum RouteType{
        FORWARD,
        REDIRECT
    }

    private String pagePath;
    private RouteType route = RouteType.FORWARD;

    public String getPagePath(){
        return pagePath;
    }

    public void setPagePath(String pagePath){
        this.pagePath = pagePath;
    }

    public RouteType getRouteType(){
        return route;
    }

    public void setRouteType(RouteType route){
        if (route == null){
            this.route = RouteType.FORWARD;
        }
        else {
            this.route = route;
        }
    }
}
