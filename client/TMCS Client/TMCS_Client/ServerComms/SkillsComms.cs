using System;
using RestSharp;
using System.Collections.Generic;
using TMCS_Client.DTOs;
namespace TMCS_Client.ServerComms
{
    public class SkillComms
    {
        RestClient client = new RestClient(Constants.SERVER_URL);

        public List<Skill> getAllSkills(){
			var request = new RestRequest(Constants.Skill.GET_SKILL_RESOURCE, Method.GET);

            var response = client.Execute<List<Skill>>(request);

            return response.Data;
        }
    }
}
