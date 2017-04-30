using RestSharp;
using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.UI;

namespace TMCS_Client.ServerComms {
    /// <summary>
    /// Allows someone to interact with the server's Recruiter API
    /// </summary>
    /// This class is not thread safe
    class RecruiterComms {
        RestClient client = (App.Current as App).Server;

        /// <summary>
        /// Adds the recruiter to the server
        /// </summary>
        /// <param name="recruiter"></param>
        /// <returns>The status of the response</returns>
        /// <exception cref="RestException">Throws a RestException when the server doesn't return a success</exception>
        public void addRecruiter(NewRecruiter recruiter) {
            var request = new RestRequest(Constants.Recruiters.ADD_RECRUITER_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(recruiter);

            var response = client.Execute(request);
            if(response.StatusCode != System.Net.HttpStatusCode.Created) {
                if(response.ErrorException != null) {
                    throw response.ErrorException;
                }
                throw new RestException(response.StatusCode);
            }
        }

        internal Recruiter getRecruiter(string email) {
            var request = new RestRequest(Constants.Recruiters.GET_RECRUITER_BY_EMAIL_RESOURCE, Method.GET);

            var response = client.Execute<Recruiter>(request);
            if(response.StatusCode != System.Net.HttpStatusCode.OK) {
                if(response.ErrorException != null) {
                    throw response.ErrorException;
                }
                throw new RestException(response.StatusCode);
            }

            return response.Data;
        }
    }
}
