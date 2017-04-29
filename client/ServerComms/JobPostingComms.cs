using System;
using RestSharp;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using TMCS_Client;
using TMCS_Client.UI;

namespace TMCS_Client.ServerComms
{
    public class JobPostingComms
    {
        RestClient client = (App.Current as App).Server;

        public void createJobPositing(JobPosting newJobPosting){
            var request = new RestRequest(Constants.JobPosting.ADD_JOB_POSTING_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(newJobPosting);
        }

        public void deleteJobPosting(JobPosting toDelete){
            string url = Constants.JobPosting.DELETE_JOB_POSTING_RESOURCE;
            url.Replace("{id}", toDelete.id.ToString());
            var request = new RestRequest(url, Method.DELETE);

            client.Execute(request);
        }

        public JobPosting getJobPostingById(long id){
            string url = Constants.JobPosting.GET_JOB_POSTING_RESOURCE;
            url.Replace("{id}", id.ToString());
            var request = new RestRequest(url, Method.GET);

            var response = client.Execute<JobPosting>(request);

            return response.Data;
        }
    }
}
