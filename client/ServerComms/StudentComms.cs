using RestSharp;
using RestSharp.Deserializers;
using RestSharp.Serializers;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using TMCS_Client.DTOs;

namespace TMCS_Client.ServerComms {
    /// <summary>
    /// Allows someone to interact with the server's Student API
    /// </summary>
    /// This class is not thread safe
    public class StudentComms {
        private RestClient client = new RestClient(Constants.SERVER_URL);

        private JsonDeserializer deserialiser = new JsonDeserializer();

        /// <summary>
        /// Adds the student to the server
        /// </summary>
        /// <param name="student"></param>
        /// <returns>The status of the response</returns>
        /// <exception cref="RestException">Throws a RestException when the server doesn't return a success</exception>
        public void addStudent(NewStudent student) {
            var request = new RestRequest(Constants.Students.ADD_STUDENT_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(student);

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

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.OK);
            
            return deserialiser.Deserialize<List<Match>>(response);
        }

        private void ensureStatusCode(IRestResponse response, HttpStatusCode code) {
            if(response.StatusCode != code) {
                if(response.ErrorException != null) {
                    throw response.ErrorException;
                }
                throw new RestException(response.StatusCode);
            }
        }
    }
}
