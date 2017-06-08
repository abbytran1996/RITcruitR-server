using System;
using System.Collections.Generic;
using RestSharp;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using TMCS_Client;
using TMCS_Client.UI;

namespace TMCS_Client.ServerComms
{
    public class MatchesComms
    {
        RestClient client = (App.Current as App).Server;

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
		}
    }
}