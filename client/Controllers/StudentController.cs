using RestSharp;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Controllers {
    /// <summary>
    /// A controller to interact with students
    /// </summary>
    /// Is this a true MVC controller? I don't know.
    public class StudentController : ServerCommsBase {
        private static StudentController studentController = new StudentController();

        private StudentController() {}

        public static StudentController getStudentController() {
            return studentController;
        }

        /// <summary>
        /// Adds the student to the server
        /// </summary>
        /// <param name="newStudent"></param>
        /// <returns>The status of the response</returns>
        /// <exception cref="RestException">Throws a RestException when the server doesn't return a success</exception>
        public void addStudent(NewStudent newStudent) {
            var request = new RestRequest(Constants.Students.ADD_STUDENT_RESOURCE, Method.POST);

            request.RequestFormat = DataFormat.Json;
            request.AddBody(newStudent);

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.Created);
        }

        /// <summary>
        /// Gets all the Match objects associated with the given student
        /// </summary>
        /// <param name="student">The student to get all the matches of</param>
        /// <returns>All the matches associated with the given student</returns>
        public List<Match> getMatchesForStudent(Student student) {
            var resource = String.Format(Constants.Students.GET_MATCHES_RESORUCE, student.id);
            var request = new RestRequest(resource, Method.GET);

            var response = client.Execute<List<Match>>(request);
            ensureStatusCode(response, HttpStatusCode.OK);

            return response.Data;
        }
    }
}
