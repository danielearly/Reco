package Users;

import java.util.HashMap;
import java.util.List;

public class Member implements User {

    String name;
    String email;
    HashMap<String, String> settings;
    HashMap<String,List<String>> preferences;

    @Override
    public void login()
    {}

    @Override
    public void logout()
    {}


}
