package sg.edu.ntu.scse.cz2002.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import sg.edu.ntu.scse.cz2002.objects.menuitem.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenneth on 19/3/2019.
 * for sg.edu.ntu.scse.cz2002.util in assignment-fsp6-grp2
 */
public class MenuItemCSVHelper {

    private String menuItemCsv;

    public MenuItemCSVHelper(String filename) {
        this.menuItemCsv = filename;
    }

    public ArrayList<MenuItem> readFromCsv() throws IOException {
        CSVReader csvReader = new CSVReaderBuilder(FileIOHelper.getFileBufferedReader(this.menuItemCsv)).withSkipLines(1).build(); // Skip Header Line
        List<String[]> csvLines = csvReader.readAll();
        csvReader.close();
        ArrayList<MenuItem> items = new ArrayList<>();
        if (csvLines.size() == 0) return items;
        csvLines.forEach((str) -> items.add(new MenuItem(str)));

        return items;
    }

    public void writeToCsv(ArrayList<MenuItem> items) throws IOException {
        String[] header = {"ID", "Name", "Type", "Price", "Description" };
        CSVWriter csvWriter = new CSVWriter(FileIOHelper.getFileBufferedWriter(this.menuItemCsv));
        csvWriter.writeNext(header, false);
        items.forEach((i) -> csvWriter.writeNext(i.toCsv(), false));
        csvWriter.flush();
    }
}
