using System;
namespace TMCS_Client.DTOs
{
    public class Skill
    {
        public static Skill NullSkill = new Skill() { name = "No skills selected." };

        public long id { get; internal set; }
        public string name { get; set; }

        public override string ToString()
        {
            return name;
        }


        public override bool Equals(object obj)
        {
            Boolean equal = false;

            if(obj.GetType() == typeof(Skill)){
                equal = (((Skill)obj).id == this.id) &&
                    (((Skill)obj).name == this.name);
            }

            return equal;
        }
    }
}
