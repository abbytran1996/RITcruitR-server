using System;
using System.Collections.Generic;
namespace TMCS_Client.DTOs
{
    public class JobPosting
    {
        public static JobPosting NullJobPosting = new JobPosting() { 
            id = -1
        };

        public long id { get; set; }
        public int status { get; set; }
        public string positionTitle { get; set; }
        public string description { get; set; }
        public List<Skill> importantSkills { get; set; }
		public List<Skill> nicetohaveSkills { get; set; }
		public double matchThreshold { get; set; }
        public double recommendedSkillsWeight { get; set; }
        public Recruiter recruiter { get; set; }
        public string location { get; set; }
        public long phaseTimeout { get; set; }
        public string problemStatement { get; set; }
        public string url { get; set; }
    }
}
