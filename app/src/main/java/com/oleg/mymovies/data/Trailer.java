package com.oleg.mymovies.data;

import java.net.URL;

public class Trailer {
    private String name;
    private URL url;



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public Trailer(String name, URL url) {
        this.name = name;
        this.url = url;
    }
}
