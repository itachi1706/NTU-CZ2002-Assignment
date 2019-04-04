package sg.edu.ntu.scse.cz2002.objects.menuitem;

import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

public class MenuItem implements ICsvSerializable {

    protected int id;
    protected String name;
    protected String type;
    protected String description;
    protected double price;

    public MenuItem() {}

    public MenuItem(int id, String name, String type, String description, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
    }

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public MenuItem(String[] csv) {
        this.id = Integer.parseInt(csv[0]);
        this.name = csv[1];
        this.type = csv[2];
        this.price = Double.parseDouble(csv[3]);
        this.description = csv[4];
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        String[] s = new String[5];
        s[0] = this.id + "";
        s[1] = this.name;
        s[2] = this.type;
        s[3] = this.price + "";
        s[4] = this.description;
        return s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
