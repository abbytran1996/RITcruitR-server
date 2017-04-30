using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Controllers
{
	/// <summary>
	/// A controller to interact with students
	/// </summary>
	/// Is this a true MVC controller? I don't know.
	public class RecruiterController
	{
        private static RecruiterController recruiterController = null;
        private RecruiterComms recruiterComms = new RecruiterComms();

        private RecruiterController(){
        }

        public static RecruiterController getRecruiterController(){
            if(recruiterController == null){
                recruiterController = new RecruiterController();
            }

            return recruiterController;
        }

		/// <summary>
		/// Adds a new recruiter to the server
		/// </summary>
		/// <param name="student">The recruiter to add</param>
        public void addRecruiter(NewRecruiter recruiter)
		{
            recruiterComms.addRecruiter(recruiter);
		}

        /// <summary>
        /// Gets a recruiter by email address
        /// </summary>
        /// <param name="email">The email of the recruiter to get</param>
        /// <returns>The found recruiter</returns>
        public Recruiter getRecruiter(string email) {
            return recruiterComms.getRecruiter(email);
        }
	}
}
