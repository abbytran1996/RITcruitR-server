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
		// I want this to be private so you have to use the method which validates the recruiter
        private NewRecruiter() { }

		/// <summary>
		/// Creates a new NewRecruiter object, validating that all the information is correct
		/// </summary>
		/// <param name="firstName">The first name of the new recruiter</param>
		/// <param name="lastName">The last name of the new recruiter</param>
		/// <param name="email">The email address of the new recruiter</param>
		/// <param name="company">The company the recruiter works for</param>
		/// <param name="phoneNumber">The phone number of the new recruiter</param>
		/// <param name="location">The location the recruiter is working in</param>
		/// <param name="user">The User object to accosiate with the new recruiter</param>
		/// <returns>The created Recruiter</returns>
        public static Recruiter CreateAndValidate(string firstName, string lastName, string email, Company company,
                                                     string phoneNumber, User user)
		{

			// Create recruiter
            var newRecruiter = new Recruiter();

            newRecruiter.firstName = firstName;
            newRecruiter.lastName = lastName;
            newRecruiter.email = email;
            newRecruiter.company = company;
            newRecruiter.phoneNumber = phoneNumber;
            newRecruiter.user = user;

            return newRecruiter;
		}
	}
}
