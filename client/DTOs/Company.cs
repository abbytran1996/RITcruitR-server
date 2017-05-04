using System;
using System.Collections.Generic;
using System.Text;


namespace TMCS_Client.DTOs
/// <summary>
/// The DTO for a Company
/// </summary>
{
	
	public class Company
	{
        public long id { get; set; }
        public User user { get; set; }
        public string companyName { get; set; }
        public string emailSuffix { get; set; }
		public string location { get; set; }
        public string size { get; set; }
        public string presentation { get; set; }
		public string companyDescription { get; set; }
        public Boolean approvalStatus { get; set; }
	}
}
