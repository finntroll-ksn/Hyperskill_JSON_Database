package client;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

public class Request {

    @Parameter(names = "-t")
    private String type;

    @Parameter(names = "-k")
    private String key;

    @Parameter(names = "-v")
    private String value;

    @Parameter(names = {"--input", "-in"})
    private String input;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getInput() {
        return input;
    }
}