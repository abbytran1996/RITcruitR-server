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
        private static StudentController studentController = null;

        private StudentController() {}

        public static StudentController getStudentController() {
            if(studentController == null) {
                studentController = new StudentController();
            }
            return studentController;
        }

        /// <summary>
        /// Finds a student based on their email address
        /// </summary>
        /// <param name="email">The email to find the student by</param>
        /// <returns>The found student</returns>
        public Student getStudent(String email) {
            var request = new RestRequest(Constants.Students.GET_STUDENT_BY_EMAIL_RESOURCE, Method.GET);
            request.AddUrlSegment("email", email);
            request.RequestFormat = DataFormat.Json;

            var response = client.Execute<Student>(request);
            ensureStatusCode(response, HttpStatusCode.OK);

            return response.Data;
        }

        /// <summary>
        /// Adds the student to the server
        /// </summary>
        /// <param name="newStudent"></param>
        /// <returns>The status of the response</returns>
        /// <exception cref="RestException">Throws a RestException when the server doesn't return a success</exception>
        public Student addStudent(NewStudent newStudent) {
            var request = new RestRequest(Constants.Students.ADD_STUDENT_RESOURCE, Method.POST);

            request.RequestFormat = DataFormat.Json;
            request.AddBody(newStudent);

            var response = client.Execute<Student>(request);
            ensureStatusCode(response, HttpStatusCode.Created);
            return response.Data;
        }

        /// <summary>
        /// Gets all the Match objects associated with the given student
        /// </summary>
        /// <param name="student">The student to get all the matches of</param>
        /// <returns>All the matches associated with the given student</returns>
        public List<Match> getMatchesForStudent(Student student) {
            var request = new RestRequest(Constants.Students.GET_MATCHES_RESORUCE, Method.GET);
            request.AddUrlSegment("id", student.id.ToString());
            request.RequestFormat = DataFormat.Json;

            var response = client.Execute<List<Match>>(request);
            ensureStatusCode(response, HttpStatusCode.OK);

            return response.Data;
        }

        public Student addSkillsForStudent(Student student, List<Skill> skills)
        {
            var request = new RestRequest(Constants.Students.ADD_SKILLS_RESOURCE, Method.POST);
            request.AddUrlSegment("id", student.id.ToString());
            request.RequestFormat = DataFormat.Json;
            request.AddBody(skills);

            var response = client.Execute<Student>(request);
            ensureStatusCode(response, HttpStatusCode.OK);

            return response.Data;
        }

        public void acceptMatch(Match match, bool acceptthis)
        {
            var request = new RestRequest(Constants.Matches.ACCEPT_JOB_POSTING, Method.POST);
            request.AddUrlSegment("id", match.id.ToString());
            request.RequestFormat = DataFormat.Json;
            request.AddBody(acceptthis);

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.OK);
            return;
        }

        public void addStudentResponse(long id, string matchResponse)
		{
            string url = Constants.Matches.ADD_RESPONSE_RESOURCE;
            url = url.Replace("{id}", id.ToString());
            url = url.Replace("{response}", matchResponse);
			var request = new RestRequest(url, Method.POST);
			request.RequestFormat = DataFormat.Json;
            request.AddBody(matchResponse);

			var response = client.Execute(request);
			ensureStatusCode(response, HttpStatusCode.OK);
			return;
		}
    }
}
