using System;
using System.Collections.Generic;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;
namespace TMCS_Client.Controllers
{
    public class SkillController
    {
        private SkillComms skillComms = new SkillComms();

        public List<Skill> getAllSkills(){
            return skillComms.getAllSkills();
        }
    }
}
