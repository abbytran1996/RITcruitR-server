using RestSharp;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;
using TMCS_Client.UI;
using Xamarin.Forms;

namespace TMCS_Client.Controllers
{
	/// <summary>
	/// A controller to interact with students
	/// </summary>
	/// Is this a true MVC controller? I don't know.
    public class RecruiterController : ServerCommsBase
	{
		RestClient client = (Application.Current as App).Server;

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
		/// <param name="recruiter">The recruiter to add</param>
        public void addRecruiter(Recruiter recruiter)
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

        internal void updateRecruiter(Recruiter recruiter)
		{
			Console.WriteLine("Updating Recruiter");

            string url = Constants.Recruiters.UPDATE_RECRUITER_RESOURCE;
			url = url.Replace("{id}", recruiter.id.ToString());

			var request = new RestRequest(url, Method.PUT);
            request.AddJsonBody(recruiter);

            var response = client.Execute<Recruiter>(request);

			ensureStatusCode(response, HttpStatusCode.OK);
		}
	}
}
