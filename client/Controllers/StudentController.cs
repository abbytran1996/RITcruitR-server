using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Controllers {
    /// <summary>
    /// A controller to interact with students
    /// </summary>
    /// Is this a true MVC controller? I don't know.
    public class StudentController {
        private static StudentController studentController = null;
        private StudentComms studentComms = new StudentComms();

        private StudentController(){
        }

        public static StudentController getStudentController(){
            if(studentController == null){
                studentController = new StudentController();
            }

            return studentController;
        }

        /// <summary>
        /// Adds a new student to the server
        /// </summary>
        /// <param name="student">The student to add</param>
        public void addStudent(NewStudent student) {
            studentComms.addStudent(student);
        }

        public List<Match> getMatchesForStudent(Student student) {
            return studentComms.getMatchesForStudent(student);
        }
    }
}
