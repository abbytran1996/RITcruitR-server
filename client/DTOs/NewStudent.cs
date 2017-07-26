using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs
{
    public class NewStudent : Student
    {
        public string password { get; set; }

        public string passwordConfirm { get; set; }

        public Resume resume { get; set; }

        private NewStudent() { }

        public static NewStudent createAndValidate(string firstName, string lastName, string email, string school,
            string graduationDate, string phoneNumber, List<string> preferredStates, string preferredCompanySize,
            string password, string passwordConfirm, Resume resume)
        {

            if(password != passwordConfirm)
            {
                throw new ArgumentException("Passwords must match");
            }

            var newStudent = new NewStudent();
            newStudent.firstName = firstName;
            newStudent.lastName = lastName;
            newStudent.email = email;
            newStudent.school = school;
            newStudent.graduationDate = DateTime.ParseExact(graduationDate, "MM/yy", null);
            newStudent.phoneNumber = phoneNumber;
            newStudent.preferredStates = preferredStates;
            newStudent.preferredCompanySize = preferredCompanySize;
            newStudent.password = password;
            newStudent.passwordConfirm = passwordConfirm;
            newStudent.resume = resume;

            return newStudent;
        }
    }
}
