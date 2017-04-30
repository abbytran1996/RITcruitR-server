using System;
using System.Collections.Generic;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;
namespace TMCS_Client.Controllers
{
    public class SkillController
    {
        private static SkillController skillController = null;
        private SkillComms skillComms = new SkillComms();

        private SkillController(){
        }

        public static SkillController getSkillController(){
            if(skillController == null){
                skillController = new SkillController();
            }

            return skillController;
        }

        public List<Skill> getAllSkills(){
            return skillComms.getAllSkills();
        }
    }
}
