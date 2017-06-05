﻿using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs {
    public class Match {
		public static Match NullMatch = new Match()
		{
			id = -1
		};
        public enum ApplicationStatus {
            NEW,
            IN_PROGRESS,
            ACCEPTED,
            REJECTED
        }

        public enum CurrentPhase {
            NONE,
            PROBLEM,
            PRESENTATION,
            INTERVIEW
        }

        public int id { get; set; }
        public JobPosting job { get; set; }
        public Student student { get; set; }
        public float matchStrength { get; set; }

        public String studentProblemResponse { get; set; }
        public Uri studentPresentationLink { get; set; }
        public DateTime timeLastUpdated { get; set; }
        public ApplicationStatus applicationStatus { get; set; }
        public CurrentPhase currentPhase { get; set; }
    }
}
