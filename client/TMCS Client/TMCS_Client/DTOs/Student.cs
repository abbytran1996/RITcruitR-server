using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs {
    /// <summary>
    /// The DTO for a student
    /// </summary>
    class Student {
        public string PhoneNumber { get; set; }
        public string School { get; set; }
        public DateTime GraduationDate { get; set; }
        public string Email { get; set; }
        public string LastName { get; set; }
        public string FirstName { get; set; }
        public List<string> PreferredStates { get; set; }
    }
}
