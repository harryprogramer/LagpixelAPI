package util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class Resource {
    public File LoadResource(String path){
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
