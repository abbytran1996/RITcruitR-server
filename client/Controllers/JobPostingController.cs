using System;
using TMCS_Client.ServerComms;
using TMCS_Client.DTOs;
namespace TMCS_Client.Controllers
{
    public class JobPostingController
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
    }
}
