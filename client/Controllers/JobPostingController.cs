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
        private JobPostingComms jobPostingComms = new JobPostingComms();
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
            jobPostingComms.createJobPositing(newJobPosting);
        }

        public void deleteJobPosting(JobPosting toDelete){
            jobPostingComms.deleteJobPosting(toDelete);
        }

        public JobPosting getJobPostingById(long id){
            return jobPostingComms.getJobPostingById(id);
        }

        public List<JobPosting> getJobPostingsByRecruiter(Recruiter recruiter){
            return jobPostingComms.getJobPostingsByRecruiter(recruiter.id);
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
