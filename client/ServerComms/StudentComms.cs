using RestSharp;
using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.DTOs;

namespace TMCS_Client.ServerComms {
    /// <summary>
    /// Allows someone to interact with the server's Student API
    /// </summary>
    /// This class is not thread safe
    class StudentComms {
        RestClient client = new RestClient(Constants.SERVER_URL);

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
            if(response.StatusCode != System.Net.HttpStatusCode.Created) {
                if(response.ErrorException != null) {
                    throw response.ErrorException;
                }
                throw new RestException(response.StatusCode);
            }
        }

        public List<Match> getMatchesForStudent(Student student) {
            var resource = String.Format(Constants.Students.GET_MATCHES_RESORUCE, student.id);
            var request = new RestRequest();

            return null;
        }
    }
}
