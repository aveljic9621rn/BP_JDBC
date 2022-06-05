package controller.check;

import java.io.FileNotFoundException;

public interface Rule {

    public String check(String query) throws FileNotFoundException;

}
