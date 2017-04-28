using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs
{
	/// <summary>
	/// The DTO sent to the server when a new recruiter is added
	/// </summary>
	public class NewRecruiter : Recruiter
	{
		public string password { get; set; }

		public string passwordConfirm { get; set; }

		// I want this to be private so you have to use the method which validates the recruiter
        private NewRecruiter() { }

		/// <summary>
		/// Creates a new NewRecruiter object, validating that all the information is correct
		/// </summary>
		/// <param name="firstName">The first name of the new recruiter</param>
		/// <param name="lastName">The last name of the new recruiter</param>
		/// <param name="email">The email address of the new recruiter</param>
		/// <param name="companyName">The company the recruiter works for</param>
		/// <param name="phoneNumber">The phone number of the new recruiter</param>
		/// <param name="location">The location the recruiter is working in</param>
		/// <param name="password">The password that the new recruiter wants to use</param>
		/// <param name="passwordConfirm">The password that the new recruiter wants to use, grabbed from the password 
		/// confirmation field. I included this parameter so that all the validation can happen in this method and 
		/// nothing needs to happen in the OS-specific classes</param>
		/// <returns>The created Recruiter</returns>
        public static NewRecruiter CreateAndValidate(string firstName, string lastName, string email, string companyName,
                                                     string phoneNumber, string location,
			string password, string passwordConfirm)
		{

			// Validation
			if (password != passwordConfirm)
			{
				throw new ArgumentException("Passwords must match");
			}


			// Create recruiter
            var newRecruiter = new NewRecruiter();

            newRecruiter.firstName = firstName;
            newRecruiter.lastName = lastName;
            newRecruiter.email = email;
            newRecruiter.companyName = companyName;
            newRecruiter.phoneNumber = phoneNumber;
            newRecruiter.location = location;
            newRecruiter.password = password;
            newRecruiter.passwordConfirm = passwordConfirm;

            return newRecruiter;
		}
	}
}
