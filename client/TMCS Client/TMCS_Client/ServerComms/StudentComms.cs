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
        RestClient client = new RestClient(Constants.ServerUrl);

        /// <summary>
        /// Adds the student to the server
        /// </summary>
        /// <param name="student"></param>
        /// <returns>The status of the response</returns>
        /// <exception cref="RestException">Throws a RestException when the server doesn't return a success</exception>
        public void addStudent(NewStudent student) {
            var request = new RestRequest("/students", Method.PUT);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(student);

            var response = client.Execute(request);
            if(response.StatusCode != System.Net.HttpStatusCode.Created) {
                throw new RestException(response.StatusCode);
            }
        }
    }
}
