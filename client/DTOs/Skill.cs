using System;
namespace TMCS_Client.DTOs
{
    public class Skill
    {
        public static Skill NullSkill = new Skill() { name = "No skills selected." };

        public string name { get; set; }

        public override string ToString()
        {
            return name;
        }

    }
}
