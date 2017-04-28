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
	public class RecruiterRegistration : ContentPage
	{
		//Whole Page
		private ScrollView pageContent;
		private AbsoluteLayout registrationForm;

		//Title
		private PageTitleLabel lblTitle;

		//First Name
		private FormFieldLabel lblFirstName;
		private FormEntry txtFirstName;

		//Last Name
		private FormFieldLabel lblLastName;
		private FormEntry txtLastName;

		//Email
		private FormFieldLabel lblCompanyEmail;
		private FormEntry txtCompanyEmail;

		//Password
		private FormFieldLabel lblPassword;
		private FormEntry txtPassword;

		//Retype Password
		private FormFieldLabel lblRetypePassword;
		private FormEntry txtRetypePassword;

		//Company Name
		private FormFieldLabel lblCompanyName;
		private FormEntry txtCompanyName;

		//Phone Number
		private FormFieldLabel lblPhoneNumber;
		private FormEntry txtPhoneNumber;

		//Location
		private FormFieldLabel lblLocation;
		private FormEntry txtLocation;

        //New Company* Description
        private FormFieldLabel lblCompanyDescription;
        private Editor txtCompanyDescription;

        //Company Size
        private FormFieldLabel lblCompanySize;
        private FormEntry txtCompanySize;

		//Register Button
		private Button btnRegister;

		//Util
        RecruiterController recruiterController = new RecruiterController();

		public RecruiterRegistration()
		{
			//Whole page
			pageContent = new ScrollView()
			{
				Orientation = ScrollOrientation.Vertical,
			};

			registrationForm = new AbsoluteLayout()
			{
				HeightRequest = (60.0 * 10.0),
			};


			//Title
			registrationForm.Children.Add(lblTitle =
										  new PageTitleLabel("Recruiter Registration"),
										  new Rectangle(0, 0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);


			//First Name
			AbsoluteLayout firstNameInput = new AbsoluteLayout()
			{
			};

			firstNameInput.Children.Add(lblFirstName =
										new FormFieldLabel("First Name"),
										new Rectangle(0.5, 0, 0.9, 0.5),
										AbsoluteLayoutFlags.All);

			firstNameInput.Children.Add(txtFirstName =
										new FormEntry("First Name", Keyboard.Text),
										new Rectangle(0.5, 1.0, 0.9, 0.5),
										AbsoluteLayoutFlags.All);

			txtFirstName.Completed += (object sender, EventArgs e) => txtLastName.Focus();

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

			lastNameInput.Children.Add(txtLastName =
									   new FormEntry("Last Name", Keyboard.Text),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

            txtLastName.Completed += (object sender, EventArgs e) => txtCompanyEmail.Focus();

			registrationForm.Children.Add(lastNameInput,
										 new Rectangle(0, 120.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Email
			AbsoluteLayout emailInput = new AbsoluteLayout()
			{
			};

			emailInput.Children.Add(lblCompanyEmail =
									   new FormFieldLabel("Company Email"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			emailInput.Children.Add(txtCompanyEmail =
									new FormEntry("Company Email", Keyboard.Email),
									new Rectangle(0.5, 1.0, 0.9, 0.5),
									AbsoluteLayoutFlags.All);

			txtCompanyEmail.Completed += (object sender, EventArgs e) => txtPassword.Focus();
			txtCompanyEmail.Unfocused += (object sender, FocusEventArgs e) => emailCheck();

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

			passwordInput.Children.Add(txtPassword =
									   new FormEntry("Password", Keyboard.Text, true),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			txtPassword.Completed += (object sender, EventArgs e) => txtRetypePassword.Focus();
			txtPassword.Unfocused += (object sender, FocusEventArgs e) => passwordCheck();

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

			retypePasswordInput.Children.Add(txtRetypePassword =
									   new FormEntry("Retype Password", Keyboard.Text, true),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			txtRetypePassword.Completed += (object sender, EventArgs e) => txtCompanyName.Focus();
			txtRetypePassword.Unfocused += (object sender, FocusEventArgs e) => retypePasswordCheck();

			registrationForm.Children.Add(retypePasswordInput,
										 new Rectangle(0, 300.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Company Name
			AbsoluteLayout companyNameInput = new AbsoluteLayout()
			{
			};

			companyNameInput.Children.Add(lblCompanyName =
									   new FormFieldLabel("Company Name"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			companyNameInput.Children.Add(txtCompanyName =
									   new FormEntry("Company Name", Keyboard.Text),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			txtCompanyName.Completed += (object sender, EventArgs e) => txtPhoneNumber.Focus();

			registrationForm.Children.Add(companyNameInput,
										 new Rectangle(0, 360.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);




			//Phone Number
			AbsoluteLayout phoneNumberInput = new AbsoluteLayout()
			{
			};

			phoneNumberInput.Children.Add(lblPhoneNumber =
									   new FormFieldLabel("Phone Number (Optional)"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			phoneNumberInput.Children.Add(txtPhoneNumber =
									   new FormEntry("(xxx) xxx-xxxx", Keyboard.Numeric),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			txtPhoneNumber.Completed += (object sender, EventArgs e) => txtLocation.Focus();
			txtPhoneNumber.TextChanged += (object sender, TextChangedEventArgs e) => phoneNumberUpdate();

			registrationForm.Children.Add(phoneNumberInput,
										 new Rectangle(0, 420.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Prefered Location
			AbsoluteLayout LocationInput = new AbsoluteLayout()
			{
			};

			LocationInput.Children.Add(lblLocation=
									   new FormFieldLabel("Office Location"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			LocationInput.Children.Add(txtLocation =
									   new FormEntry("State, State, ...", Keyboard.Text, true),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			txtLocation.Completed += (object sender, EventArgs e) => txtCompanySize.Focus();

			registrationForm.Children.Add(LocationInput,
										 new Rectangle(0, 480.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);
            //Company Description

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
										 new Rectangle(0.5, 570.0, 0.8, 30),
										  AbsoluteLayoutFlags.WidthProportional |
										 AbsoluteLayoutFlags.XProportional);

			pageContent.Content = registrationForm;

			Content = pageContent;

		}

		private void register()
		{
			/*NewRecruiter newRecruiter = NewRecruiter.createAndValidate(
                txtFirstName.Text,
                txtLastName.Text,
                txtEmail.Text,
                txtSchoolName.Text,
                txtGraduationDate.Text,
                txtPhoneNumber.Text,
                new List<String>(txtPreferedLocation.Text.Replace(" ","").Split(',')),
                txtPreferedCompanySize.Text,
                txtPassword.Text,
                txtRetypePassword.Text
            );

            try
            {
                //recruiterController.addRecruiter(newRecruiter);
                Navigation.PopToRootAsync();
            }catch(Exception e){
                Console.WriteLine(e.ToString());
            }*/

			Navigation.PopToRootAsync();
		}

		private void phoneNumberUpdate()
		{

		}

		private bool emailCheck()
		{
			bool result;
			if (Regex.IsMatch(txtCompanyEmail.Text, Constants.Emails.COMPANY))
			{
				txtCompanyEmail.BackgroundColor = Color.PaleGreen;
				result = true;
			}
			else
			{
				txtCompanyEmail.BackgroundColor = Color.PaleVioletRed;
				result = false;
			}
			return result;
		}

		private bool passwordCheck()
		{
			bool result;
			if (Regex.IsMatch(txtPassword.Text, Constants.PASSWORD_REGEX))
			{
				txtPassword.BackgroundColor = Color.PaleGreen;
				result = true;
			}
			else
			{
				txtPassword.BackgroundColor = Color.PaleVioletRed;
				result = false;
			}
			return result;
		}

		private bool retypePasswordCheck()
		{
			bool result;
			if (txtPassword.Text != txtRetypePassword.Text)
			{
				txtRetypePassword.BackgroundColor = Color.PaleVioletRed;
				result = false;
			}
			else
			{
				txtRetypePassword.BackgroundColor = Color.PaleGreen;
				result = true;
			}
			return result;
		}
	}
}
