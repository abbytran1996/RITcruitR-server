﻿using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client.DTOs {
    /// <summary>
    /// The DTO sent to the server when a new student is added
    /// </summary>
    public class NewStudent : Student {
        public string password { get; set; }

        public string passwordConfirm { get; set; }

        // I want this to be private so you have to use the method which validates the student
        private NewStudent() { }

        /// <summary>
        /// Creates a new NewStudent object, validating that all the information is correct
        /// </summary>
        /// <param name="firstName">The first name of the new student</param>
        /// <param name="lastName">The last name of the new student</param>
        /// <param name="email">The email address of the new student</param>
        /// <param name="school">The school that the new student attends</param>
        /// <param name="graduationDate">The date that the new student expects to graduate school</param>
        /// <param name="phoneNumber">The phone number of the new student</param>
        /// <param name="preferredStates">The states that the new student would prefer to have a job in</param>
        /// <param name="password">The password that the new student wants to use</param>
        /// <param name="passwordConfirm">The password that the new student wants to use, grabbed from the password 
        /// confirmation field. I included this parameter so that all the validation can happen in this method and 
        /// nothing needs to happen in the OS-specific classes</param>
        /// <returns>The created student</returns>
        public static NewStudent createAndValidate(string firstName, string lastName, string email, string school,
            string graduationDate, string phoneNumber, List<string> preferredStates, string preferredCompanySize,
            string password, string passwordConfirm) {

            // Validation
            if(password != passwordConfirm) {
                throw new ArgumentException("Passwords must match");
            }

            var date = DateTime.ParseExact(graduationDate,"MM/yy",null);

            // Create student
            var newStudent = new NewStudent();

            newStudent.firstName = firstName;
            newStudent.lastName = lastName;
            newStudent.email = email;
            newStudent.school = school;
            newStudent.graduationDate = date;
            newStudent.phoneNumber = phoneNumber;
            newStudent.preferredStates = preferredStates;
            newStudent.preferredCompanySize = preferredCompanySize;
            newStudent.password = password;
            newStudent.passwordConfirm = passwordConfirm;

            return newStudent;
        }
     }
}
