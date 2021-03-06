package dymmyco.noobs.models;

import java.io.Serializable;
/**
 * Stores the data for a course
 * Created by Jimmy on 18-7-2016.
 */
public class CourseModel implements Serializable {
    public String period;
    public String name;
    public String ects;
    public String grade;
    public String info;

    public CourseModel(String courseName, String ects, String grade, String period, String info) {
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
        this.info = info;
    }

    public CourseModel() {
    }
}
