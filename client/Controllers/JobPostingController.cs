using System;
using System.Net;
using TMCS_Client.ServerComms;
using TMCS_Client.DTOs;
using System.Collections.Generic;
using RestSharp;

namespace TMCS_Client.Controllers
{
    public class JobPostingController : ServerCommsBase
    {
        private static JobPostingController jobPostingController = null;

        private JobPostingController(){
            
        }

        public static JobPostingController getJobPostingController(){
            if(jobPostingController == null){
                jobPostingController = new JobPostingController();
            }

            return jobPostingController;
        }

        public void createJobPosting(JobPosting newJobPosting){
			var request = new RestRequest(Constants.JobPosting.ADD_JOB_POSTING_RESOURCE, Method.POST);
			request.RequestFormat = DataFormat.Json;
			request.AddBody(newJobPosting);

			var response = client.Execute(request);
        }

        public void deleteJobPosting(JobPosting toDelete){
			var request = new RestRequest(Constants.JobPosting.DELETE_JOB_POSTING_RESOURCE, Method.DELETE);
			request.AddUrlSegment("id", toDelete.id.ToString());

			client.Execute(request);
        }

        public JobPosting getJobPostingById(long id){
			var request = new RestRequest(Constants.JobPosting.GET_JOB_POSTING_RESOURCE, Method.GET);
			request.AddUrlSegment("id", id.ToString());

			var response = client.Execute<JobPosting>(request);

			return response.Data;
        }

        public List<JobPosting> getJobPostingsByRecruiter(Recruiter recruiter){
			var request = new RestRequest(Constants.JobPosting.GET_JOB_POSTING_BY_RECRUITER_RESOURCE, Method.GET);
			request.AddUrlSegment("id", recruiter.id.ToString());

			var response = client.Execute<List<JobPosting>>(request);

			return response.Data;
        }

        public long getProbPhasePosts(JobPosting job)
        {
            var request = new RestRequest(Constants.Matches.GET_PROBLEM_PHASE_MATCHES, Method.GET);
            request.AddUrlSegment("id", job.id.ToString());
            request.RequestFormat = DataFormat.Json;

            var response = client.Execute<long>(request);
            ensureStatusCode(response, HttpStatusCode.OK);
            return response.Data;
        }
    }
}
