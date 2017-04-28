using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs
{
	/// <summary>
	/// The DTO for a recruiter
	/// </summary>
	public class Recruiter
	{
		public string phoneNumber { get; set; }
		public string email { get; set; }
		public string lastName { get; set; }
		public string firstName { get; set; }
		public string location { get; set; }
        public string companyName { get; set; }
		public string companySize { get; set; }
	}
}
