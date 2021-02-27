package util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class Resource {
    public static String LoadResource(String path){
        path = Resource.class
                .getClassLoader().getResource(path).getFile();
        if(path.contains("file:/")){
            path = path.replaceAll("/","//");
        }else{
            path = path.substring(1).replaceAll("/", "//");
        }
        return path;
    }

}
