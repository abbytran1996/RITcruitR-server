using System;
using System.Collections.Generic;
using System.Text;


namespace TMCS_Client.DTOs
/// <summary>
/// The DTO for a Company
/// </summary>
{
	
	class Company
	{
		public string companyName { get; set; }
		public string email { get; set; }
		public string location { get; set; }
		public string size { get; set; }
		public FileStyleUriParser file { get; set; }
		public string companyDescription { get; set; }
	}
}
