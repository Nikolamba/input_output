package ru.job4j.totaltask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class StartSearch {

    private final static String LS = System.lineSeparator();
    private final ValidateParam validateParam;
    private final FileFinder finder;

    public StartSearch(ValidateParam validateParam, FileFinder finder) {
        this.validateParam = validateParam;
        this.finder = finder;
    }

    public void start() {
        if (validateParam.getParam().containsKey("-help")) {
            giveHint();
            return;
        }
        if (validateParam.validate()) {
            String logFileName = validateParam.getParam().get("-o");
            writeResult(finder.findFiles(), logFileName);
            System.out.println("The result is written to file: " + logFileName);
        } else {
            System.out.println("startup keys missing");
            giveHint();
        }
    }

    public void giveHint() {
        System.out.println(String.format("Startup keys:%s"
                + "-d: search folder%s"
                + "-n: search line%s"
                + "-m: mask search, -f: fullname search, -r: regular expression search%s"
                + "-o: file for results", LS, LS, LS, LS));
    }

    public void writeResult(List<File> files, String logName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logName))) {
            bw.write("------------------------------" + LS);
            for (File file : files) {
                bw.write(file.toString() + LS);
            }
            bw.write("------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> loadArgs(String[] args) {
        Map<String, String> argsMap = new HashMap<>();
        String str = null;
        for (String arg : args) {
            if (str != null) {
                argsMap.put(str, arg);
                str = null;
            }
            switch (arg) {
                case "-d": str = "-d"; break;
                case "-n": str = "-n"; break;
                case "-m": argsMap.put(arg, null); break;
                case "-f": argsMap.put(arg, null); break;
                case "-r": argsMap.put(arg, null); break;
                case "-o": str = "-o"; break;
                case "-help": argsMap.put(arg, null); break;
                default: break;
            }
        }
        return argsMap;
    }

    public static void main(String[] args) {
        Map<String, String> param = StartSearch.loadArgs(args);
        for (String arg : args) {
            System.out.println(arg);
        }
        ValidateParam validateParam = new ValidateParam(param);
        FileFinder fileFinder = new FileFinder(param);
        new StartSearch(validateParam, fileFinder).start();
    }
}
