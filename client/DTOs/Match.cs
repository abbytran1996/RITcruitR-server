using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs
{
    public class Match
    {
        public static Match EmptyMatch = new Match()
        {
            id = -1
        };
        public enum ApplicationStatus
        {
            NEW,
            IN_PROGRESS,
            ACCEPTED,
            REJECTED,
            TIMED_OUT
        }

        public enum CurrentPhase
        {
            NONE,
            PROBLEM_WAITING_FOR_STUDENT,
            PROBLEM_WAITING_FOR_RECRUITER,
            PRESENTATION_WAITING_FOR_STUDENT,
            PRESENTATION_WAITING_FOR_RECRUITER,
            INTERVIEW
        }

        public int id { get; set; }
        public JobPosting job { get; set; }
        public Student student { get; set; }
        public float matchStrength { get; set; }
        public String tag { get; set; }
        public String studentProblemResponse { get; set; }
        public Uri studentPresentationLink { get; set; }
        public DateTime timeLastUpdated { get; set; }
        public ApplicationStatus applicationStatus { get; set; }
        public CurrentPhase currentPhase { get; set; }
    }
}
