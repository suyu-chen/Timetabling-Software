
public class Student {
    
    private String name;
    private char gender;
    private int id;
    private int grade;
    private String courseChoices []= new String [11]; // last 3 are alternatives
    private String alternateChoices []= new String [3]; 
    private String[] timetable =new String[9]; // final timetable
    

    public Student(String name, char gender, int studentNumber,int grade, String courseChoices[],String alternateChoices[]){
        this.name = name;
        this.gender = gender;
        this.id = studentNumber;
        this.courseChoices = courseChoices;
        this.alternateChoices=alternateChoices;
    }

    public boolean setTimetable(String course, int timeslot){
        if(timetable[timeslot] == null){
            timetable[timeslot] = course;
            return true;
        }else{
            return false;
        }
    }

    public String getName() {
        return this.name;
    }

    public char getGender() {
        return this.gender;
    }

    public int getStudentNumber() {
        return this.id;
    }

    public int getGrade() {
        return this.grade;
    }

    public String[] getCourseChoices() {
        return this.courseChoices;
    }

    public boolean hasCourse(String course){
        for(int i = 0; i < courseChoices.length;i++){
            if(courseChoices[i].equals(course)){
                courseChoices[i] = courseChoices[i] + "COMPLETE";
                return true;
            }
        }
        return false;
    }

    public int correctCourses(){
        int counter = 0;
        for(int i = 0; i < courseChoices.length;i++){
            if(courseChoices[i].endsWith("COMPLETE") || courseeChoices[i].equals("")){
                counter++;
            }
        }
        return counter;
    }


    public String[] getAlternateChoices() {
        return this.alternateChoices;
    }

    public String[] getTimetable() {
        return this.timetable;
    }

    public void setTimetable(String timetable[]) {
        this.timetable = timetable;
    }
// -----------------------------------------------------------------------------------------
    
    
    
}
