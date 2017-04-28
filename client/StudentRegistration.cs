﻿using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.Entries;

using Xamarin.Forms;

namespace TMCS_Client
{
    public class StudentRegistration : ContentPage
    {
        //Whole Page
        private ScrollView pageContent;
        private AbsoluteLayout registrationForm;

        //Title
        private PageTitleLabel lblTitle;

        //First Name
        private FormFieldLabel lblFirstName;
        private FormEntry entFirstName;

        //Last Name
        private FormFieldLabel lblLastName;
        private FormEntry entLastName;

		//Email
		private FormFieldLabel lblEmail;
		private FormEntry entEmail;

		//Password
		private FormFieldLabel lblPassword;
		private FormEntry entPassword;

		//Retype Password
		private FormFieldLabel lblRetypePassword;
		private FormEntry entRetypePassword;

		//School Name
		private FormFieldLabel lblSchoolName;
		private FormEntry entSchoolName;

		//Graduation Date
		private FormFieldLabel lblGraduationDate;
		private FormEntry entGraduationDate;

		//Phone Number
		private FormFieldLabel lblPhoneNumber;
        private FormEntry entPhoneNumber;

        //Prefered Location
        private FormFieldLabel lblPreferredLocationn;
        private FormEntry entPreferredLocation;

        //Prefered Company Size
        private FormFieldLabel lblPreferredCompanySize;
		private FormEntry entPreferredCompanySize;

        //Register Button
        private Button btnRegister;

        //Util
        StudentController studentController = new StudentController();

        public StudentRegistration()
        {
            //Whole page
            pageContent = new ScrollView()
            {
                Orientation = ScrollOrientation.Vertical,
            };

            registrationForm = new AbsoluteLayout()
            {
                HeightRequest = (60.0 * 12.0),
			};


            //Title
            registrationForm.Children.Add(lblTitle =
                                          new PageTitleLabel("Student Registration"),
                                          new Rectangle(0,0,1.0,60.0), 
                                          AbsoluteLayoutFlags.WidthProportional);


            //First Name
            AbsoluteLayout firstNameInput = new AbsoluteLayout()
            {
            };

            firstNameInput.Children.Add(lblFirstName = 
                                        new FormFieldLabel("First Name"), 
                                        new Rectangle(0.5,0,0.9,0.5), 
                                        AbsoluteLayoutFlags.All);

            firstNameInput.Children.Add(entFirstName = 
                                        new FormEntry("First Name", Keyboard.Text), 
                                        new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                        AbsoluteLayoutFlags.All);

            entFirstName.Completed += (object sender, EventArgs e) => entLastName.Focus();

            registrationForm.Children.Add(firstNameInput,
                                         new Rectangle(0, 60.0, 1.0, 60.0),
                                          AbsoluteLayoutFlags.WidthProportional);

			//Last Name
			AbsoluteLayout lastNameInput = new AbsoluteLayout()
			{
			};

			lastNameInput.Children.Add(lblLastName = 
                                       new FormFieldLabel("Last Name"), 
                                       new Rectangle(0.5, 0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

            lastNameInput.Children.Add(entLastName = 
                                       new FormEntry("Last Name", Keyboard.Text), 
                                       new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			entLastName.Completed += (object sender, EventArgs e) => entEmail.Focus();

			registrationForm.Children.Add(lastNameInput,
										 new Rectangle(0, 120.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Email
			AbsoluteLayout emailInput = new AbsoluteLayout()
			{
			};

			emailInput.Children.Add(lblEmail =
									   new FormFieldLabel("Email"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			emailInput.Children.Add(entEmail =
									new FormEntry("Email", Keyboard.Email), 
                                    new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                    AbsoluteLayoutFlags.All);

			entEmail.Completed += (object sender, EventArgs e) => entPassword.Focus();
            entEmail.Unfocused += (object sender, FocusEventArgs e) => emailCheck();

			registrationForm.Children.Add(emailInput,
										 new Rectangle(0, 180.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Password
			AbsoluteLayout passwordInput = new AbsoluteLayout()
			{
			};

			passwordInput.Children.Add(lblPassword =
									   new FormFieldLabel("Password"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

            passwordInput.Children.Add(entPassword = 
                                       new FormEntry("Password", Keyboard.Text, true),
                                       new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			entPassword.Completed += (object sender, EventArgs e) => entRetypePassword.Focus();
            entPassword.Unfocused += (object sender, FocusEventArgs e) => passwordCheck();

			registrationForm.Children.Add(passwordInput,
										 new Rectangle(0, 240.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Retype Password
			AbsoluteLayout retypePasswordInput = new AbsoluteLayout()
			{
			};

			retypePasswordInput.Children.Add(lblRetypePassword =
									   new FormFieldLabel("Retype Password"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			retypePasswordInput.Children.Add(entRetypePassword =
									   new FormEntry("Retype Password", Keyboard.Text, true),
									   new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			entRetypePassword.Completed += (object sender, EventArgs e) => entSchoolName.Focus();
            entRetypePassword.Unfocused += (object sender, FocusEventArgs e) => retypePasswordCheck();

			registrationForm.Children.Add(retypePasswordInput,
										 new Rectangle(0, 300.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//School Name
			AbsoluteLayout schoolNameInput = new AbsoluteLayout()
			{
			};

			schoolNameInput.Children.Add(lblSchoolName =
									   new FormFieldLabel("School Name"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			schoolNameInput.Children.Add(entSchoolName =
									   new FormEntry("School Name", Keyboard.Text),
									   new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			entSchoolName.Completed += (object sender, EventArgs e) => entGraduationDate.Focus();

			registrationForm.Children.Add(schoolNameInput,
										 new Rectangle(0, 360.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Graduation Date
			AbsoluteLayout graduationDateInput = new AbsoluteLayout()
			{
			};

			graduationDateInput.Children.Add(lblGraduationDate =
									   new FormFieldLabel("Graduation Date"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			graduationDateInput.Children.Add(entGraduationDate =
									   new FormEntry("Graduation Date", Keyboard.Numeric),
									   new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			entGraduationDate.Completed += (object sender, EventArgs e) => entPhoneNumber.Focus();
            entGraduationDate.TextChanged += (object sender, TextChangedEventArgs e) => graduationDateUpdate();
            entGraduationDate.Unfocused += (object sender, FocusEventArgs e) => graduationDateCheck();

			registrationForm.Children.Add(graduationDateInput,
									   new Rectangle(0, 420.0, 1.0, 60.0),
									   AbsoluteLayoutFlags.WidthProportional);

			//Phone Number
			AbsoluteLayout phoneNumberInput = new AbsoluteLayout()
			{
			};

            phoneNumberInput.Children.Add(lblPhoneNumber = 
                                       new FormFieldLabel("Phone Number (Optional)"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			phoneNumberInput.Children.Add(entPhoneNumber =
                                       new FormEntry("(xxx) xxx-xxxx", Keyboard.Numeric),
									   new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

            entPhoneNumber.Completed += (object sender, EventArgs e) =>entPreferredLocation.Focus();
            entPhoneNumber.TextChanged += (object sender, TextChangedEventArgs e) => phoneNumberUpdate();
            entPhoneNumber.Unfocused += (object sender, FocusEventArgs e) => phoneNumberCheck();

			registrationForm.Children.Add(phoneNumberInput,
										 new Rectangle(0, 480.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Prefered Location
			AbsoluteLayout preferredLocationInput = new AbsoluteLayout()
            {
            };

			preferredLocationInput.Children.Add(lblPreferredLocationn =
									   new FormFieldLabel("What is your preferred work location?"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			preferredLocationInput.Children.Add(entPreferredLocation =
									   new FormEntry("State, State, ...", Keyboard.Text), 
                                       new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

            entPreferredLocation.Completed += (object sender, EventArgs e) => entPreferredCompanySize.Focus();

			registrationForm.Children.Add(preferredLocationInput,
                                         new Rectangle(0, 540.0, 1.0, 60.0),
                                          AbsoluteLayoutFlags.WidthProportional);

			//Prefered Company Size
			AbsoluteLayout preferredCompanySizeInput = new AbsoluteLayout()
            {
            };

			preferredCompanySizeInput.Children.Add(lblPreferredCompanySize =
									   new FormFieldLabel("What is your preferred company size?"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			preferredCompanySizeInput.Children.Add(entPreferredCompanySize =
									   new FormEntry("Size", Keyboard.Text),
									   new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			registrationForm.Children.Add(preferredCompanySizeInput,
										 new Rectangle(0, 600.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

            //Register Button
            btnRegister = new Button()
            {
                Text = "Register",
                FontSize = 24,
                TextColor = Color.White,
                BackgroundColor = Color.Blue,
                Command = new Command((object obj) => register()),
            };

            registrationForm.Children.Add(btnRegister,
                                         new Rectangle(0.5, 670.0, 0.8, 40),
                                          AbsoluteLayoutFlags.WidthProportional |
                                         AbsoluteLayoutFlags.XProportional);

            pageContent.Content = registrationForm;

            Content = pageContent;

        }

        private void register()
        {
            String invalidDataMessage = "";

            if (!emailCheck())
            {
                invalidDataMessage += "Email is not in proper format.\n";
            }
			if (!passwordCheck())
			{
				invalidDataMessage += "Password does not meet the complexity requirements.\n";
			}
			if (!retypePasswordCheck())
			{
				invalidDataMessage += "Passwords do not match.\n";
			}
			if (!graduationDateCheck())
			{
				invalidDataMessage += "Date is not a valid future date.\n";
			}
			if (!phoneNumberCheck())
			{
				invalidDataMessage += "Phone number is not a valid 10 digit phone number.\n";
			}

            if (invalidDataMessage != "")
            {
                this.DisplayAlert("Invalid Data:", invalidDataMessage, "OK");
            }
            else
            {
                NewStudent newStudent = NewStudent.createAndValidate(
                    entFirstName.Text,
                    entLastName.Text,
                    entEmail.Text,
                    entSchoolName.Text,
                    entGraduationDate.Text.Length < 5 ? "0" + entGraduationDate.Text :
                        entGraduationDate.Text,
                    entPhoneNumber.Text.Replace("(","").Replace(")","")
                        .Replace(" ","").Replace("-", ""),
                    new List<String>(entPreferredLocation.Text.Replace(", ",",").Split(',')),
                    entPreferredCompanySize.Text,
                    entPassword.Text,
                    entRetypePassword.Text
                );

                try
                {
                    studentController.addStudent(newStudent);
                    Navigation.PopToRootAsync();
                }catch(Exception e){
                    Console.WriteLine(e.ToString());
                }
            }
        }

        private void graduationDateUpdate(){
            String stripedGraduationDate =
                entGraduationDate.Text.Replace("/","").Replace(".","");

            String newString;

            switch (stripedGraduationDate.Length){
                case(0):case(1):case(2):
                    newString = stripedGraduationDate;
                    break;
                default:
                    newString = stripedGraduationDate.Substring(0, Math.Min(4,stripedGraduationDate.Length));
                    newString = newString.Insert(newString.Length - 2,"/");
                    break;
            }

            entGraduationDate.Text = newString;
        }

        private void phoneNumberUpdate(){
            String stripedPhoneNumber =
                entPhoneNumber.Text.Replace(" ","").Replace("-","")
                              .Replace("(","").Replace(")", "").Replace(".","");

            String formattedPhoneNumber = "";

            if (stripedPhoneNumber.Length > 0)
            {
                formattedPhoneNumber += "(";
                formattedPhoneNumber += stripedPhoneNumber.Substring(0, Math.Min(3, stripedPhoneNumber.Length));
            }
            if (stripedPhoneNumber.Length > 3)
            {
                formattedPhoneNumber += ") ";
                formattedPhoneNumber += stripedPhoneNumber.Substring(3, Math.Min(3, stripedPhoneNumber.Length - 3));
            }
            if (stripedPhoneNumber.Length > 6)
            {
                formattedPhoneNumber += "-";
                formattedPhoneNumber += stripedPhoneNumber.Substring(6, Math.Min(4, stripedPhoneNumber.Length - 6));
            }

            entPhoneNumber.Text = formattedPhoneNumber;
        }

        private bool emailCheck(){
            bool result;
            if((entEmail.Text != null) && Regex.IsMatch(entEmail.Text, Constants.Emails.STUDENT)){
                entEmail.BackgroundColor = Color.PaleGreen;
                result = true;
            }else{
                entEmail.BackgroundColor = Color.PaleVioletRed;
                result = false;
            }
            return result;
        }

        private bool passwordCheck(){
            bool result;
            if((entPassword.Text != null) && Regex.IsMatch(entPassword.Text, Constants.PASSWORD_REGEX)){
                entPassword.BackgroundColor = Color.PaleGreen;
                result = true;
            }else{
                entPassword.BackgroundColor = Color.PaleVioletRed;
                result = false;
            }
            return result;
        }

        private bool retypePasswordCheck(){
            bool result;
            if((entPassword.Text == null ? "" : entPassword.Text) != 
              (entRetypePassword.Text == null ? "" : entRetypePassword.Text)){
                entRetypePassword.BackgroundColor = Color.PaleVioletRed;
                result = false;
            }else if((entRetypePassword.Text != null) && (entRetypePassword.Text != "")){
                entRetypePassword.BackgroundColor = Color.PaleGreen;
                result = true;
            }else{
                entRetypePassword.BackgroundColor = Color.White;
                result = true;
            }
            return result;
        }

        private bool graduationDateCheck(){
            bool result;
            String adjustedGraduationDate;
            DateTime graduationDate;

            if ((entGraduationDate.Text == null) || (entGraduationDate.Text.Length < 4))
			{
				entGraduationDate.BackgroundColor = Color.PaleVioletRed;
                result = false;
            }
            else
            {
                try
                {
                    adjustedGraduationDate = (entGraduationDate.Text.Length < 5 ?
                                             "0" + entGraduationDate.Text :
                                             entGraduationDate.Text);

                    graduationDate = DateTime.ParseExact(adjustedGraduationDate, "MM/yy", null);

                    if (((graduationDate.Month >= DateTime.Today.Month) &&
                        (graduationDate.Year == DateTime.Today.Year)) ||
                        (graduationDate.Year > DateTime.Today.Year))
                    {
                        entGraduationDate.BackgroundColor = Color.PaleGreen;
                        result = true;
                    }
                    else
                    {
                        entGraduationDate.BackgroundColor = Color.PaleVioletRed;
                        result = false;
                    }
                }
                catch (FormatException e)
                {
                    entGraduationDate.BackgroundColor = Color.PaleVioletRed;
                    result = false;
                }
            }
            return result;
        }

        private bool phoneNumberCheck(){
            bool result;

            if((entPhoneNumber.Text == null) || (entPhoneNumber.Text == ""))
            {
                entPhoneNumber.BackgroundColor = Color.White;
                result = true;
            }else if(Regex.IsMatch(entPhoneNumber.Text.Replace(" ", "").Replace("(", "")
                                   .Replace(")", "").Replace("-", ""), "[0-9]{10}"))
            {
                result = true;
                entPhoneNumber.BackgroundColor = Color.PaleGreen;
            }else
            {
                result = false;
                entPhoneNumber.BackgroundColor = Color.PaleVioletRed;
            }

            return result;
        }
	}
}

