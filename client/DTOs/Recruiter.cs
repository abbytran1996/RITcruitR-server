using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs
{
    public class Recruiter
    {
        public long id { get; internal set; }
        public string firstName { get; set; }
        public string lastName { get; set; }
        public string email { get; set; }
        public Company company { get; set; }
		public string phoneNumber { get; set; }
		public User user { get; set; }
	}
}
