using System;
using System.Collections.Generic;
using System.Text;


namespace TMCS_Client.DTOs
{
	class NewCompany : Company
	{
		public string password { get; set; }
		public string passwordConfirm { get; set; }

		private NewCompany() { }

		/// <summary>
		/// Creates a new NewCompany object, validating that all the information is correct
		/// </summary>
		/// <param name="companyName">The name of the new company</param>
		/// <param name="email">The email address of the new company</param>
		/// <param name="phoneNumber">The phone number of the new company</param>
		/// <param name="companyDescription">The description of the new company</param>
		/// <param name="password">The password that the new company wants to use</param>
		/// <param name="passwordConfirm">The password that the new company wants to use, grabbed from the password 
		/// confirmation field. I included this parameter so that all the validation can happen in this method and 
		/// nothing needs to happen in the OS-specific classes</param>
		/// this password is used for the entire company if a "company admin" wants to edit the companies info
		/// <returns>The created company</returns>

		public static NewCompany createAndValidate(string companyName, string email, string phoneNumber,
							   string companyDescription, string password, string passwordConfirm)
		{
			if (password != passwordConfirm)
			{
				throw new ArgumentException("Passwords must match");
			}
			var newCompany = new NewCompany();
			newCompany.companyName = companyName;
			newCompany.email = email;
			newCompany.companyDescription = companyDescription;
			newCompany.password = password;
			newCompany.passwordConfirm = passwordConfirm;

			return newCompany;
		}
	}
}


