using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs {
    class Match {
        public JobPosting jobPosting { get; set; };
        public Student student { get; set; };
        public float matchStrength { get; set; };
    }
}
