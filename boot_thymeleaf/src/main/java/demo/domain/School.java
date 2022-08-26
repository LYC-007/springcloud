package demo.domain;

public class School {

    private String schname;
    private String schaddress;

    public String getSchname() {
        return schname;
    }

    public void setSchname(String schname) {
        this.schname = schname;
    }

    public String getSchaddress() {
        return schaddress;
    }

    public void setSchaddress(String schaddress) {
        this.schaddress = schaddress;
    }

    @Override
    public String toString() {
        return "School{" +
                "schname='" + schname + '\'' +
                ", schaddress='" + schaddress + '\'' +
                '}';
    }
}
