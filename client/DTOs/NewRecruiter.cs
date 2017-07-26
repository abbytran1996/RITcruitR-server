using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs
{
    public class NewRecruiter : Recruiter
    {
        public string password { get; set; }

        public string passwordConfirm { get; set; }

        // I want this to be private so you have to use the method which validates the recruiter
        private NewRecruiter() { }

        public static NewRecruiter CreateAndValidate(string firstName, string lastName, string email, Company company,
                                                     string phoneNumber, string password, string passwordConfirm)
        {
            if(password != passwordConfirm)
            {
                throw new ArgumentException("Passwords must match");
            }

            var newRecruiter = new NewRecruiter();

            newRecruiter.firstName = firstName;
            newRecruiter.lastName = lastName;
            newRecruiter.email = email;
            newRecruiter.company = company;
            newRecruiter.phoneNumber = phoneNumber;
            newRecruiter.password = password;
            newRecruiter.passwordConfirm = passwordConfirm;
            //newRecruiter.user = user;

            return newRecruiter;
        }
    }
}
