package ru.job4j.totaltask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class ValidateParam {

    private final Map<String, String> param;

    public ValidateParam(Map<String, String> param) {
        this.param = param;
    }

    public boolean validate() {
        return (isPresentAll()
                && isPathExists(param.get("-d"))
                && isRegularCorrect(param.get("-r")));
    }

    public Map<String, String> getParam() {
        return param;
    }

    private boolean isPresentAll() {
        boolean result = true;
        if (!param.containsKey("-d") || param.get("-d") == null
                || !param.containsKey("-n") || param.get("-n") == null
                || !param.containsKey("-o") || param.get("-o") == null) {
            result = false;
        }
        if (this.getNameMaskRegSet().size() != 1) {
            result = false;
        }
        return result;
    }

    private List<String> getNameMaskRegSet() {
        List<String> set = new ArrayList<>();
        for (String key : param.keySet()) {
            if (key.equals("-m") || key.equals("-f") || key.equals("-r")) {
                set.add(key);
            }
        }
        return set;
    }

    private boolean isPathExists(String source) {
        File file = new File(source);
        return  (file.exists() && file.isDirectory());
    }

    private boolean isRegularCorrect(String regEx) {
        if (regEx != null) {
            try {
                Pattern.compile(regEx);
            } catch (PatternSyntaxException e) {
                return false;
            }
        }
        return true;
    }
}
