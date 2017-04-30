using System;
using System.Collections.Generic;
using System.Text;


namespace TMCS_Client.DTOs
{
	public class NewCompany : Company
	{

		private NewCompany() { }

		/// <summary>
		/// Creates a new NewCompany object, validating that all the information is correct
		/// </summary>
		/// <param name="companyName">The name of the new company</param>
		/// <param name="emailSuffix">The email address of the new company</param>
		/// <param name="phoneNumber">The phone number of the new company</param>
		/// <param name="companyDescription">The description of the new company</param>
        /// <param name="size">The size of the company</param>
		/// confirmation field. I included this parameter so that all the validation can happen in this method and 
		/// nothing needs to happen in the OS-specific classes</param>
		/// this password is used for the entire company if a "company admin" wants to edit the companies info
		/// <returns>The created company</returns>

		public static NewCompany createAndValidate(string companyName, string emailSuffix,
                                                   string companyDescription, string location,
                                                  string size)
		{
			var newCompany = new NewCompany();
			newCompany.companyName = companyName;
            newCompany.emailSuffix = emailSuffix;
			newCompany.companyDescription = companyDescription;
            newCompany.location = location;
            newCompany.size = size;

			return newCompany;
		}
	}
}


