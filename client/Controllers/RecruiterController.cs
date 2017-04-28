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
	class RecruiterController
	{
        private RecruiterComms recruiterComms = new RecruiterComms();

		/// <summary>
		/// Adds a new recruiter to the server
		/// </summary>
		/// <param name="student">The recruiter to add</param>
        public void addRecruiter(NewRecruiter recruiter)
		{
            recruiterComms.addRecruiter(recruiter);
		}
	}
}
