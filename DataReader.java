import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList; 
/**
 * [DataReader.java]
 * reads the data from all the files
 * @author Alex, Samson
 * @version 1.0
 */
public class DataReader {
    private int courseCounter = 0;

    /**
     * Gets the total number of courses chosen by students
     * @return the total number of courses chosen by students
     */
    public int getNumCourses(){
        return courseCounter;
    }
    
    /**
     * gets special courses from file
     * @author Samson
     * @return hashmap of course code and which semster 
     */
    public HashMap<String, Integer> getSpecialCourses() throws Exception {
        Scanner specialCourseData = new Scanner(new File("SpecialCourse.csv"));
        HashMap<String, Integer> specialCourses = new HashMap<String, Integer>();
        specialCourseData.nextLine();
        while (specialCourseData.hasNext()) {
            String[] currentLine = (specialCourseData.nextLine().split(","));
            specialCourses.put(currentLine[0], Integer.valueOf(currentLine[1]));
        }
        return specialCourses;
    }

   /**
     * gets room number and the room type from file
     * @author Alex
     * @return Hashmap of room number, and room class
     */
    public HashMap<String, Room> getRooms() throws Exception {
        Scanner roomData = new Scanner(new File("RoomData.csv"));
        HashMap<String, Room> rooms = new HashMap<String, Room>();
        roomData.nextLine();
        while (roomData.hasNext()) {
            String[] currentLine = (roomData.nextLine().split(","));
            rooms.put(currentLine[0], new Room(currentLine[0], currentLine[1]));
        }
        return rooms;
    }

   /**
     * get room type and all courses from file
     * @author Alex
     * @return Hashmap of roomtype, and arraylist of courses codes
     */
    public HashMap<String, String[]> getRoomTypeCourses() throws Exception {
        Scanner roomData = new Scanner(new File("RoomType.csv"));
        HashMap<String, String[]> roomTypes = new HashMap<String, String[]>();
        roomData.nextLine();
        while (roomData.hasNext()) {
            String[] currentLine = (roomData.nextLine().split(","));
            String room = currentLine[0];
            String[] courses = new String[currentLine.length - 1];
            for (int i = 1; i < currentLine.length; i++) {
                courses[i - 1] = currentLine[i];
            }
            roomTypes.put(room, courses);
        }
        return roomTypes;
    }
    /**
     * creates a map relating room type to room numbers 
     * @author Alex
     * @return Hashmap of roomtype, and arraylist of room codes
     */
    public HashMap<String, ArrayList<String>> getRoomTypes() {
        HashMap<String, ArrayList<String>> coursesToRooms = new HashMap<String, ArrayList<String>>();
        for (String type : Data.roomTypeCourses.keySet()) {
            ArrayList<String> rooms = new ArrayList<String>();
            for (Room r : Data.roomMap.values()) {
                if (r.getRoomType().equals(type)) {
                    rooms.add(r.getRoomNum());
                }
            }
            coursesToRooms.put(type, rooms);
        }
        return coursesToRooms;
    }
    /**
     * get course data from file
     * @author Alex
     * @return Hashmap of course Code, and course class
     */
    public HashMap<String, Course> getCourses() throws Exception {
        Scanner courseData = new Scanner(new File("CourseData.csv"));
        HashMap<String, Course> courses = new HashMap<String, Course>();
        courseData.nextLine();
        while (courseData.hasNext()) {
            String[] currentLine = removeCommas(courseData.nextLine()).split("`");
            String courseCode = currentLine[0];
            String courseTitle = currentLine[1];
            int grade = Integer.parseInt(currentLine[2]);
            String type = currentLine[3];
            int maxSize = Integer.parseInt(currentLine[8]);
            courses.put(courseCode, new Course(courseCode, courseTitle, grade, type, maxSize));

        }
        return courses;
    }
    /**
     * get student data from file
     * @author Samson
     * @return Hashmap of student Number, and student class
     */
    public HashMap<Integer, Student> getStudents() throws Exception {
        Scanner studentData = new Scanner(new File("StudentDataObfuscated.csv"));
        HashMap<Integer, Student> students = new HashMap<Integer, Student>();
        studentData.nextLine();
        while(studentData.hasNext()){
            String [] currentLine = removeCommas(studentData.nextLine()).split("`"); 
            String name = currentLine[0];
            char gender = currentLine[1].charAt(0);
            int studentNumber = Integer.valueOf(currentLine[2]);
            int grade = Integer.valueOf(currentLine[6]);

            ArrayList<String> courseChoices = courseInputs(currentLine);
            ArrayList<String> alternateChoices = alternateInputs(currentLine);
            students.put(studentNumber,new Student(name, gender, studentNumber, grade, courseChoices, alternateChoices));
        }
        studentData.close();
        return students;
    }

    /**
     * get the course inputs of each students
     * @author Samson
     * @param currentLine input from file
     * @return arraylist of all the courses
     */
    public ArrayList<String> courseInputs(String[] currentLine){
        ArrayList<String> courseChoices= new ArrayList<String>();      
        int courseIndex; 
        for (int i =0; i <11; i++){
            courseIndex = (3*i)+7;
            if(!currentLine[courseIndex].equals("")) {
                if (currentLine[courseIndex].equals("CHV2OL")){
                    courseChoices.add("CIVCAR ESL");
                    courseCounter++;
                }else if(currentLine[courseIndex].contains("CHV2O")){
                    courseChoices.add("CIVCAR");
                    courseCounter++;
                }else if (!(currentLine[courseIndex].contains("GLC2O") || currentLine[courseIndex].contains("ZREMOT") || currentLine[courseIndex].contains("AMR"))){
                    courseChoices.add(currentLine[courseIndex]);
                    courseCounter++;
                }
            } 
        }
        // TODO basically having >8 courses crashes the student assignment method, so this is a temp fix
        while(courseChoices.size()>8){
            courseChoices.remove(courseChoices.size()-1);
        }
        return courseChoices;
    }

    /**
     * get the alternate course inputs of each students
     * @author Samson
     * @param currentLine input from file
     * @return arraylist of all the alternate courses
     */
    public ArrayList<String> alternateInputs(String[] currentLine) {
        ArrayList<String> alternateChoices = new ArrayList<String>();
        int courseIndex; 
        for (int i = 0; i < 3; i++) {
            courseIndex=(3*i)+41;
            if(courseIndex < currentLine.length) {
                  alternateChoices.add(currentLine[courseIndex]);
            } 
        }
        return alternateChoices;
    }

    /**
     * removes all the commas from the string and replace them with a different character 
     * @author Alex
     * @param  the line to remove commas from
     * @return line with all commas replaced
     */
    public String removeCommas(String line) {
        boolean remove = false;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '"' && remove) {
                remove = false;
            } else if (line.charAt(i) == '"' && !remove) {
                remove = true;
            }
            if (line.charAt(i) == ',' && !remove) {
                line = line.substring(0, i) + "`" + line.substring(i + 1);
            }
        }
        return line;
    }
}