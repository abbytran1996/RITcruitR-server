using System;
using System.Collections.Generic;
namespace TMCS_Client.DTOs
{
    public class JobPosting
    {
        public long id { get; set; }
        public string positionTitle { get; set; }
        public string description { get; set; }
        public List<Skill> requiredSkills { get; set; }
        public List<Skill> recommendedSkills { get; set; }
        //Recruiter
        public string location { get; set; }
        public long phaseTimeout { get; set; }
        public string problemStatement { get; set; }
        public string url { get; set; }
    }
}
