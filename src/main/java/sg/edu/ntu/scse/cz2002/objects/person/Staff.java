package sg.edu.ntu.scse.cz2002.objects.person;

import sg.edu.ntu.scse.cz2002.util.ICsvSerializable;

import java.text.ParseException;

/**
 * The Staff Class
 *
 * @author Weibin
 * @version 1.0
 * @since 2019-03-30
 */
public class Staff implements ICsvSerializable
{
	/**
	 * Unique identifier for Staff 
	 */
	private int staffId;
	
	/**
	 * Name of the Staff
	 */
	private String staffName;
	
	/**
	 * Gender of the Staff - M for male, F for female
	 */
	private char gender;
	
	/**
	 * title/position of the Staff
	 */
	private String jobTitle;
	
	/**
     * Create a new Staff with given Id, name, gender and job title.
     * @param id This Staff's unique id.
     * @param name This Staff's name.
     * @param gender This Staff's gender.
     * @param title This Staff's job title.
     */
    public Staff(int id, String name, char gender, String title) 
    { 
    	this.staffId = id;
    	this.staffName = name;
    	this.gender = gender;
    	this.jobTitle = title;
    }
    
    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     * @throws ParseException When the date time provided from the CSV file has an invalid format,
     * which is unlikely to happen unless CSV file was modified outside of program.
     */
    public Staff(String[] csv) {
        this.staffId= Integer.parseInt(csv[0]);
        this.staffName = csv[1];
        this.gender = csv[2].charAt(0);
        this.jobTitle = csv[3];
        
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        String[] s = new String[4];
        s[0] = this.staffId + "";
        s[1] = this.staffName;
        s[2] = this.gender + "";
        s[3] = this.jobTitle;
        
        return s;
    }
    /**
    * Gets the unique staffId of this Staff.
    * @return this Staff's id.
    */
    public int getStaffId() {
        return staffId;
    }

    /**
     * Gets the name of this Staff.
     * @return this Staff's name.
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * Changes the name of this Staff.
     * @param staffName This Staff's ID.
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    /**
     * Gets the gender of this Staff.
     * @return this Staff's gender.
     */
    public char getGender() {
        return gender;
    }

    /**
     * Changes the gender of this Staff.
     * @param gender This Staff's gender.
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Gets the job title of this Staff.
     * @return this Staff's job title.
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Changes the job title of this Staff.
     * @param jobTitle This Staff's job title.
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
