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
<<<<<<< HEAD
			var request = new RestRequest(Constants.Skill.GET_SKILL_RESOURCE, Method.GET);
=======
            var request = new RestRequest(Constants.Skill.GET_SKILL_RESOURCE, Method.GET);
>>>>>>> d7dff2e5b11714859cf765c18040845f41d019ab

            var response = client.Execute<List<Skill>>(request);

            return response.Data;
        }
    }
}
