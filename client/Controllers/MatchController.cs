using RestSharp;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Controllers
{
    public class MatchController : ServerCommsBase
    {
		private static MatchController matchController = null;

		private MatchController() { }

		public static MatchController getMatchController()
		{
			if (matchController == null)
			{
				matchController = new MatchController();
			}
			return matchController;
		}

        public List<Match> getMatchesWithProblemResponsePending(long jobPostingID){
            var request = new RestRequest(Constants.Matches.GET_MATCHES_WITH_PROBLEM_RESPONSE_PENDING, Method.GET);
			request.AddUrlSegment("jobPostingID", jobPostingID.ToString());
			request.RequestFormat = DataFormat.Json;

			var response = client.Execute<List<Match>>(request);

			return response.Data;
        }

        public void updateMatch(Match match){
            var request = new RestRequest(Constants.Matches.UPDATE_MATCH, Method.PATCH);
            request.RequestFormat = DataFormat.Json;
            request.AddUrlSegment("id",match.id.ToString());
            request.AddBody(match);

            var response = client.Execute<Boolean>(request);
            Console.WriteLine(response.Data);
		}

        public void acceptMatch(Match match, bool acceptthis)
        {
            var request = new RestRequest(Constants.Matches.ACCEPT_JOB_POSTING, Method.POST);
            request.AddUrlSegment("id", match.id.ToString());
            request.RequestFormat = DataFormat.Json;
            request.AddBody(acceptthis);

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.OK);
            return;
        }

        public void addStudentResponse(long id, string matchResponse)
        {
            string url = Constants.Matches.ADD_RESPONSE_RESOURCE;
            url = url.Replace("{id}", id.ToString());
            url = url.Replace("{response}", matchResponse);
            var request = new RestRequest(url, Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(matchResponse);

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.OK);
            return;
        }

        public void addStudentLink(long id, string responseLink)
        {
            string url = Constants.Matches.ADD_LINK_RESOURCE;
            url = url.Replace("{id}", id.ToString());
            url = url.Replace("{link}", responseLink);
            var request = new RestRequest(url, Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(responseLink);

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.OK);
            return;
        }

		/// <summary>
		/// Gets all the Match objects associated with the given student
		/// </summary>
		/// <param name="student">The student to get all the matches of</param>
		/// <returns>All the matches associated with the given student</returns>
		public List<Match> getMatchesForStudent(Student student)
		{
			var request = new RestRequest(Constants.Matches.GET_MATCHES_RESORUCE, Method.GET);
			request.AddUrlSegment("id", student.id.ToString());
			request.RequestFormat = DataFormat.Json;

			var response = client.Execute<List<Match>>(request);
			ensureStatusCode(response, HttpStatusCode.OK);

			return response.Data;
		}

        internal List<Match> getMatchesInPresentationPhase(JobPosting job) {
            var request = new RestRequest(Constants.Matches.GET_PRESENTATION_PHASE_MATCHES, Method.GET);
            request.AddUrlSegment("id", job.id.ToString());
            request.RequestFormat = DataFormat.Json;

            var response = client.Execute<List<Match>>(request);
            ensureStatusCode(response, HttpStatusCode.OK);
            return response.Data;
        }

        public long getInterviewPhaseMatchesCount(JobPosting job){
            var request = new RestRequest(Constants.Matches.GET_INTERVIEW_PHASE_MATCHES_COUNT, Method.GET);
			request.AddUrlSegment("jobPostingID", job.id.ToString());
			request.RequestFormat = DataFormat.Json;

            var response = client.Execute<long>(request);
            return response.Data;
        }

		public List<Match> getInterviewPhaseMatches(JobPosting job)
		{
			var request = new RestRequest(Constants.Matches.GET_INTERVIEW_PHASE_MATCHES, Method.GET);
			request.AddUrlSegment("jobPostingID", job.id.ToString());
			request.RequestFormat = DataFormat.Json;

			var response = client.Execute<List<Match>>(request);
			return response.Data;
		}
    }
}
