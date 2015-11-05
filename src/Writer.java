import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by boggs on 9/19/15.
 */
public class Writer {
    public File file = new File("output");
    public PrintWriter writer;

    public Writer() {
        resetFiles();
        initializeWriters();
        writeHeaders();
    }

    public void resetFiles() {
        try {
            file.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeWriters() {
        try {
            writer = new PrintWriter(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(cal.getTime());
    }

    public void writeHeaders() {
        writeLine("GENERATED REPORT:");
        writeLine("By: Aldanese, Soriano - INTRNLP S19");
        writeLine(getDate());
        writeLine("");
    }

    public void writeLine(String line) {
        try {
            writer.println(line);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWriters() {
        writer.flush();
        writer.close();
    }

    public File getFile() {
        return file;
    }

    public PrintWriter getWriter() {
        return writer;
    }
}
