using System;
using RestSharp;
using System.Collections.Generic;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;
namespace TMCS_Client.Controllers
{
    public class SkillController : ServerCommsBase
    {
        private static SkillController skillController = null;

        private SkillController() { }

        public static SkillController getSkillController()
        {
            if(skillController == null)
            {
                skillController = new SkillController();
            }

            return skillController;
        }

        public List<Skill> getAllSkills()
        {
            var request = new RestRequest(Constants.Skill.GET_SKILL_RESOURCE, Method.GET);
            var response = client.Execute<List<Skill>>(request);

            return response.Data;
        }
    }
}
