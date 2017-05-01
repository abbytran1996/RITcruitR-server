using System;
using System.Collections.Generic;
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

            var response = client.Execute(request);
        }

        public void deleteJobPosting(JobPosting toDelete){
            string url = 
            string.Format(Constants.JobPosting.DELETE_JOB_POSTING_RESOURCE, toDelete.id);
            
            var request = new RestRequest(url, Method.DELETE);

            client.Execute(request);
        }

        public JobPosting getJobPostingById(long id){
			string url =
			string.Format(Constants.JobPosting.GET_JOB_POSTING_RESOURCE, id);
            var request = new RestRequest(url, Method.GET);

            var response = client.Execute<JobPosting>(request);

            return response.Data;
        }

        public List<JobPosting> getJobPostingsByRecruiter(long recruiterID){
            string url =
                string.Format(Constants.JobPosting.GET_JOB_POSTING_BY_RECRUITER_RESOURCE, recruiterID);

            var request = new RestRequest(url, Method.GET);

            var response = client.Execute<List<JobPosting>>(request);

            return response.Data;
        }
    }
}
