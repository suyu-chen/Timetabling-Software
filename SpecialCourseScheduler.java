import java.util.ArrayList;
import java.util.HashMap;

public class SpecialCourseScheduler {

    SpecialCourseScheduler(){}

    public ArrayList<ClassInfo> getSpecialCourseTimetable (HashMap <String,Integer> numberOfClasses){
        ArrayList<ClassInfo> specialCourses = new ArrayList<ClassInfo>();
        ArrayList<Integer> students= null;
        boolean fixed=true;
        String course;
        int timeslot=0;
        int room=0;
        int teacher=0;

        int functionsTime=0;
        int calculasTime=0;
        int scienceTime=0;
        int artTime=0;


        for ( String s: numberOfClasses.keySet()){
            for (int i= 0; i<numberOfClasses.get(s); i++){
                course=s;
                //functions
                if (s.contains("MHF4U1") || s.contains("MHF4UE")){
                functionsTime++;
               // Data.get(mathrooms.(functionsTime/4)); like get the 4 one, so you cycel through each classs 4 times
                timeslot=functionsTime%4;
                //calculas
                }else if (s.contains("MCV4U1") || s.contains("MCV4UE")){
                calculasTime++;
                // Data.get(mathrooms.(functionsTime/4)); like get the 4 one, so you cycel through each classs 4 times
                timeslot= calculasTime%4 +5;
                //Ap Sciences
                }else if (s.contains("SBI4UE") || s.contains("SCH4UE") || s.contains("SPH4UE")){
                scienceTime++;
                // Data.get(mathrooms.(functionsTime/4)); like get the 4 one, so you cycel through each classs 4 times
                timeslot=scienceTime%4;
                //art protfolio semseter 2
                }else if (s.contains ("AEA4O")){
                    artTime++;
                    timeslot=artTime%4;

                    if (artTime>4){
                        room=2005;
                    }else{
                        room = 2004;
                    }
                //co op
                }else if (s.contains ("COP")){
                    room =2001;
                    //semester1 period 1,2 - period 3,4
                    if (s.contains("1")){
                        timeslot=1;
                    }else if (s.contains("2")) {
                        timeslot=3;
                    //semester2
                    }else if (s.contains("4") ){
                        timeslot=5;
                    }else if (s.contains("5")){
                        timeslot=7;
                    }else{
                        //just incase its some weird co-op course
                        timeslot=1;
                    }   
                }
                //dont worry about teachers
                specialCourses.add(new ClassInfo(teacher,  room,  timeslot,  course, fixed, students));
            }
        }
        return specialCourses;
    }
}