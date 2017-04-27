using System;
using System.Collections.Generic;
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
        private FormFieldLabel lblPreferedLocation;
        private FormEntry entPreferedLocation;

        //Prefered Company Size
        private FormFieldLabel lblPreferedCompanySize;
		private FormEntry entPreferedCompanySize;

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

            entFirstName.Completed += (sender, e) => entLastName.Focus();

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

			entLastName.Completed += (sender, e) => entEmail.Focus();

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

			entEmail.Completed += (sender, e) => entPassword.Focus();

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

			entPassword.Completed += (sender, e) => entRetypePassword.Focus();

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

			entRetypePassword.Completed += (sender, e) => entSchoolName.Focus();

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

			entSchoolName.Completed += (sender, e) => entGraduationDate.Focus();

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
									   new FormEntry("Graduation Date", Keyboard.Text),
									   new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			entGraduationDate.Completed += (sender, e) => entPhoneNumber.Focus();

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
                                       new FormEntry("(xxx) xxx-xxxx", Keyboard.Text),
									   new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			entPhoneNumber.Completed += (sender, e) => entPreferedLocation.Focus();

			registrationForm.Children.Add(phoneNumberInput,
										 new Rectangle(0, 480.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Prefered Location
			AbsoluteLayout preferedLocationInput = new AbsoluteLayout()
            {
            };

			preferedLocationInput.Children.Add(lblPreferedLocation =
									   new FormFieldLabel("What is your prefered work location?"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			preferedLocationInput.Children.Add(entPreferedLocation =
									   new FormEntry("State, State, ...", Keyboard.Text, true), 
                                       new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			entPreferedLocation.Completed += (sender, e) => entPreferedCompanySize.Focus();

			registrationForm.Children.Add(preferedLocationInput,
                                         new Rectangle(0, 540.0, 1.0, 60.0),
                                          AbsoluteLayoutFlags.WidthProportional);

			//Prefered Company Size
			AbsoluteLayout preferedCompanySizeInput = new AbsoluteLayout()
            {
            };

			preferedCompanySizeInput.Children.Add(lblPreferedCompanySize =
									   new FormFieldLabel("What is your preferec company size?"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			preferedCompanySizeInput.Children.Add(entPreferedCompanySize =
									   new FormEntry("Size", Keyboard.Text, true),
									   new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                       AbsoluteLayoutFlags.All);

			registrationForm.Children.Add(preferedCompanySizeInput,
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

        private void register(){
            /*NewStudent newStudent = NewStudent.createAndValidate(
                entFirstName.Text,
                entLastName.Text,
                entEmail.Text,
                entSchoolName.Text,
                entGraduationDate.Text,
                entPhoneNumber.Text,
                new List<String>(entPreferedLocation.Text.Replace(" ","").Split(',')),
                entPreferedCompanySize.Text,
                entPassword.Text,
                entRetypePassword.Text
            );

            try
            {
                //studentController.addStudent(newStudent);
                Navigation.PopToRootAsync();
            }catch(Exception e){
                Console.WriteLine(e.ToString());
            }*/

            Navigation.PopToRootAsync();
        }
	}
}

