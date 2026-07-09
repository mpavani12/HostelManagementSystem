package model;

// MEMBER 4 - Core OOP Architecture
public abstract class Person {

    private String id;
    private String name;
    private String phone;
    private String username;
    private String password;

    // MEMBER 4 - Common user object creation
    public Person(String id, String name, String phone,
                  String username, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    // MEMBER 4 - ID access
    public String getId() {
        return id;
    }

    // MEMBER 4 - ID update
    public void setId(String id) {
        this.id = id;
    }

    // MEMBER 4 - Name access
    public String getName() {
        return name;
    }

    // MEMBER 4 - Name update
    public void setName(String name) {
        this.name = name;
    }

    // MEMBER 4 - Phone access
    public String getPhone() {
        return phone;
    }

    // MEMBER 4 - Phone update
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // MEMBER 4 - Username access
    public String getUsername() {
        return username;
    }

    // MEMBER 4 - Username update
    public void setUsername(String username) {
        this.username = username;
    }

    // MEMBER 4 - Password access
    public String getPassword() {
        return password;
    }

    // MEMBER 4 - Password update
    public void setPassword(String password) {
        this.password = password;
    }

    // MEMBER 4 - Common menu contract
    public abstract void showMenu();
}