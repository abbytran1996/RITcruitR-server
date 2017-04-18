﻿using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs {
    /// <summary>
    /// The DTO for a student
    /// </summary>
    class Student {
        public string phoneNumber { get; set; }
        public string school { get; set; }
        public DateTime graduationDate { get; set; }
        public string email { get; set; }
        public string lastName { get; set; }
        public string firstName { get; set; }
        public List<string> preferredStates { get; set; }
        public string preferredCompanySize { get; set; }
    }
}
