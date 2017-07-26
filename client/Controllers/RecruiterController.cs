using RestSharp;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;
using TMCS_Client.UI;
using Xamarin.Forms;

namespace TMCS_Client.Controllers
{
    public class RecruiterController : ServerCommsBase
    {
        private static RecruiterController recruiterController = null;

        private RecruiterController() { }

        public static RecruiterController getRecruiterController()
        {
            if(recruiterController == null)
            {
                recruiterController = new RecruiterController();
            }

            return recruiterController;
        }

        public void addRecruiter(NewRecruiter recruiter)
        {
            var request = new RestRequest(Constants.Recruiters.ADD_RECRUITER_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(recruiter);

            var response = client.Execute(request);
            if(response.StatusCode != System.Net.HttpStatusCode.Created)
            {
                if(response.ErrorException != null)
                {
                    throw response.ErrorException;
                }
                throw new RestException(response.StatusCode);
            }
        }

        public Recruiter getRecruiter(string email)
        {
            var request = new RestRequest(Constants.Recruiters.GET_RECRUITER_BY_EMAIL_RESOURCE, Method.GET);
            request.AddUrlSegment("email", email);
            request.RequestFormat = DataFormat.Json;

            var response = client.Execute<Recruiter>(request);

            return response.Data;
        }

        internal void updateRecruiter(Recruiter recruiter)
        {
            string url = Constants.Recruiters.UPDATE_RECRUITER_RESOURCE;
            url = url.Replace("{id}", recruiter.id.ToString());

            var request = new RestRequest(url, Method.PUT);
            request.AddJsonBody(recruiter);

            var response = client.Execute<Recruiter>(request);

            ensureStatusCode(response, HttpStatusCode.OK);
        }
    }
}
