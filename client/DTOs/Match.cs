using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs {
    public class Match {
        public int id { get; set; }
        public JobPosting job { get; set; }
        public Student student { get; set; }
        public float matchStrength { get; set; }
    }
}
