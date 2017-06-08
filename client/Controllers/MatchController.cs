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

			var response = client.Execute<List<Match>>(request);

			return response.Data;
        }
        /*
		public void addStudentResponse(Match match)
		{
			var matchResponse = match.studentProblemResponse;
			var id = match.id;
			var request = new RestRequest(Constants.Matches.ADD_RESPONSE_RESOURCE, Method.POST);
			request.AddUrlSegment("{id}", id.ToString());
			request.AddUrlSegment("{response}", matchResponse);
			request.RequestFormat = DataFormat.Json;
			request.AddBody(match);

			var response = client.Execute(request);
		}*/
    }
}
