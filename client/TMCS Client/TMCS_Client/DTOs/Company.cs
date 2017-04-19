using System;
using System.Collections.Generic;
using System.Text;

/// <summary>
/// The DTO for a Company
/// </summary>
namespace TMCS_Client.DTOs
{
	
	class Company
	{
		public string companyName { get; set; }
		public string email { get; set; }
		public string phoneNumber { get; set; }
		public string companyDescription { get; set; }
	}
}
