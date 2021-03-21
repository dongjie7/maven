package com.mycompany.app.mvnPlugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Mojo( name = "sayhi")
public class GreetingMojo extends AbstractMojo {
    @Parameter( property = "sayhi.greeting", defaultValue = "Hello World!" )
    private String greeting;
    @Parameter
    private boolean myBoolean;
    @Parameter
    private Integer myInteger;
    @Parameter
    private Double myDouble;
    @Parameter
    private Date myDate;
    @Parameter
    private File myFile;
    @Parameter
    private URL myURL;
    @Parameter
    private Color myColor;
    @Parameter
    private String[] myArray;
    @Parameter
    private List myList;
    @Parameter
    private Map myMap;
    @Parameter
    private Properties myProperties;

    public void execute() throws MojoExecutionException {
        getLog().info( "Hello, world." + this.greeting);
    }
}
