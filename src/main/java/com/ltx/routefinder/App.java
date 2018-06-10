package com.ltx.routefinder;

import com.ltx.routefinder.acl.FileHelper;
import com.ltx.routefinder.core.RouteManager;
import com.ltx.routefinder.core.RouteManagerFactory;
import java.util.List;
import java.util.Objects;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

/**
 * CLI route-finder application.
 */
public class App {
    public static void main(String[] args) {

        Option city1Opt = Option.builder("c1")
                .hasArg()
                .required(true)
                .desc("The name of a source city")
                .longOpt("city1")
                .build();

        Option city2Opt = Option.builder("c2")
                .hasArg()
                .required(true)
                .desc("The name of a destination city")
                .longOpt("city2")
                .build();

        Option filePathOpt = Option.builder("f")
                .hasArg()
                .required(true)
                .desc("Path to file with city connection pair")
                .longOpt("file")
                .build();

        Options options = new Options();
        options.addOption(city1Opt);
        options.addOption(city2Opt);
        options.addOption(filePathOpt);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("route-finder", options);

            System.exit(1);
        }

        String source = cmd.getOptionValue("city1");
        String destination = cmd.getOptionValue("city2");
        String filePath = cmd.getOptionValue("file");

        verify(source, destination, filePath);

        run(source, destination, filePath);
    }

    private static void verify(String source, String destination, String filePath) {
        if (StringUtils.isBlank(source))
            throw new IllegalArgumentException("'city1' argument value is blank");
        if (StringUtils.isBlank(destination))
            throw new IllegalArgumentException("'city2' argument value is blank");
        if (StringUtils.isBlank(filePath))
            throw new IllegalArgumentException("'file' argument value is blank");
    }

    private static void run(String source, String destination, String filePath) {

        // read file, construct RouteManager and populate with cities
        RouteManager routeManager = RouteManagerFactory.createGraphRouteManager();
        FileHelper.readFileLineByLine(filePath, pair -> routeManager.addConnection(pair.get(0), pair.get(1)));

        // find route
        List<String> route = routeManager.getRoute(source, destination);
        boolean connected = routeManager.connected(source, destination);

        // publish
        System.out.println("ROUTE: " + route);
        System.out.println(String.format("Is %s connected to %s? - %s", source, destination, String.valueOf(connected)));
    }
}