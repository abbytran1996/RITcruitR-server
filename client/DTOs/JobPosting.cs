﻿using System;
using System.Collections.Generic;
namespace TMCS_Client.DTOs
{
    public class JobPosting
    {
        public static JobPosting NullJobPosting = new JobPosting() { 
            id = -1,
            positionTitle = "No job postings created",
            location = "San Jose, CA"
        };

        public long id { get; set; }
        public int status { get; set; }
        public string positionTitle { get; set; }
        public string description { get; set; }
        public List<Skill> requiredSkills { get; set; }
        public int minMatchedRequiredSkills { get; set; }
        public List<Skill> recommendedSkills { get; set; }
        public double recommendedSkillsWeight { get; set; }
        public Recruiter recruiter { get; set; }
        public string location { get; set; }
        public long phaseTimeout { get; set; }
        public string problemStatement { get; set; }
        public string url { get; set; }
    }
}
