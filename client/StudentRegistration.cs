using System;
using System.Collections.Generic;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;

using Xamarin.Forms;

namespace client
{
    public class StudentRegistration : ContentPage
    {
        //Whole Page
        private ScrollView pageContent;
        private AbsoluteLayout registrationForm;

        //Title
        private Label lblTitle;

        //First Name
        private Label lblFirstName;
        private Entry entFirstName;

        //Last Name
        private Label lblLastName;
        private Entry entLastName;

		//Email
		private Label lblEmail;
		private Entry entEmail;

		//Password
		private Label lblPassword;
		private Entry entPassword;

		//Retype Password
		private Label lblRetypePassword;
		private Entry entRetypePassword;

		//School Name
		private Label lblSchoolName;
		private Entry entSchoolName;

		//Graduation Date
		private Label lblGraduationDate;
		private Entry entGraduationDate;

		//Phone Number
		private Label lblPhoneNumber;
        private Entry entPhoneNumber;

        //Prefered Location
        private Label lblPreferedLocation;
        private Entry entPreferedLocation;

        //Prefered Company Size
        private Label lblPreferedCompanySize;
		private Entry entPreferedCompanySize;

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
            registrationForm.Children.Add(lblTitle = new Label(){
                Text = "Student Registration",
                FontSize = 28.0,
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.Center,
            }, new Rectangle(0,0,1.0,60.0), AbsoluteLayoutFlags.WidthProportional);


            //First Name
            AbsoluteLayout firstNameInput = new AbsoluteLayout()
            {
            };

            firstNameInput.Children.Add(lblFirstName = new Label()
            {
                Text = "First Name",
                FontSize = 18.0,
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.Start,
            }, new Rectangle(0.5,0,0.9,0.5), AbsoluteLayoutFlags.All);

            firstNameInput.Children.Add(entFirstName = new Entry(){
                Placeholder = "First Name",
				FontSize = 16.0,
				Keyboard = Keyboard.Create(KeyboardFlags.CapitalizeSentence),
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            entFirstName.Completed += (sender, e) => entLastName.Focus();

            registrationForm.Children.Add(firstNameInput,
                                         new Rectangle(0, 60.0, 1.0, 60.0),
                                          AbsoluteLayoutFlags.WidthProportional);

			//Last Name
			AbsoluteLayout lastNameInput = new AbsoluteLayout()
			{
			};

			lastNameInput.Children.Add(lblLastName = new Label()
			{
				Text = "Last Name",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            lastNameInput.Children.Add(entLastName = new Entry()
            {
                Placeholder = "Last Name",
                FontSize = 16.0,
                Keyboard = Keyboard.Create(KeyboardFlags.CapitalizeSentence),
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			entLastName.Completed += (sender, e) => entEmail.Focus();

			registrationForm.Children.Add(lastNameInput,
										 new Rectangle(0, 120.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Email
			AbsoluteLayout emailInput = new AbsoluteLayout()
			{
			};

			emailInput.Children.Add(lblEmail = new Label()
			{
				Text = "Email",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			emailInput.Children.Add(entEmail = new Entry()
			{
				Placeholder = "Email",
				FontSize = 16.0,
				Keyboard = Keyboard.Email,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			entEmail.Completed += (sender, e) => entPassword.Focus();

			registrationForm.Children.Add(emailInput,
										 new Rectangle(0, 180.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Password
			AbsoluteLayout passwordInput = new AbsoluteLayout()
			{
			};

			passwordInput.Children.Add(lblPassword = new Label()
			{
				Text = "Password",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			passwordInput.Children.Add(entPassword = new Entry()
			{
				Placeholder = "Password",
				FontSize = 16.0,
				Keyboard = Keyboard.Plain,
                IsPassword = true,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			entPassword.Completed += (sender, e) => entRetypePassword.Focus();

			registrationForm.Children.Add(passwordInput,
										 new Rectangle(0, 240.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Retype Password
			AbsoluteLayout retypePasswordInput = new AbsoluteLayout()
			{
			};

			retypePasswordInput.Children.Add(lblRetypePassword = new Label()
			{
				Text = "Retype Password",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			retypePasswordInput.Children.Add(entRetypePassword = new Entry()
			{
				Placeholder = "Retype Password",
				FontSize = 16.0,
				Keyboard = Keyboard.Plain,
				IsPassword = true,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			entRetypePassword.Completed += (sender, e) => entSchoolName.Focus();

			registrationForm.Children.Add(retypePasswordInput,
										 new Rectangle(0, 300.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//School Name
			AbsoluteLayout schoolNameInput = new AbsoluteLayout()
			{
			};

			schoolNameInput.Children.Add(lblSchoolName = new Label()
			{
				Text = "School Name",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			schoolNameInput.Children.Add(entSchoolName = new Entry()
			{
				Placeholder = "School Name",
				FontSize = 16.0,
				Keyboard = Keyboard.Plain,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			entSchoolName.Completed += (sender, e) => entGraduationDate.Focus();

			registrationForm.Children.Add(schoolNameInput,
										 new Rectangle(0, 360.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Graduation Date
			AbsoluteLayout graduationDateInput = new AbsoluteLayout()
			{
			};

			graduationDateInput.Children.Add(lblGraduationDate = new Label()
			{
				Text = "Graduation Date",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			graduationDateInput.Children.Add(entGraduationDate = new Entry()
			{
				Placeholder = "mm/yy",
				FontSize = 16.0,
				Keyboard = Keyboard.Numeric,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			entGraduationDate.Completed += (sender, e) => entPhoneNumber.Focus();

			registrationForm.Children.Add(graduationDateInput,
										 new Rectangle(0, 420.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Phone Number
			AbsoluteLayout phoneNumberInput = new AbsoluteLayout()
			{
			};

			phoneNumberInput.Children.Add(lblPhoneNumber = new Label()
			{
                Text = "Phone Number (Optional)",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			phoneNumberInput.Children.Add(entPhoneNumber = new Entry()
			{
                Placeholder = "(xxx) xxx-xxxx",
				FontSize = 16.0,
				Keyboard = Keyboard.Numeric,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			entPhoneNumber.Completed += (sender, e) => entPreferedLocation.Focus();

			registrationForm.Children.Add(phoneNumberInput,
										 new Rectangle(0, 480.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);

			//Prefered Location
			AbsoluteLayout preferedLocationInput = new AbsoluteLayout()
            {
            };

			preferedLocationInput.Children.Add(lblPreferedLocation = new Label()
			{
				Text = "What is your prefered work location?",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			preferedLocationInput.Children.Add(entPreferedLocation = new Entry()
			{
				Placeholder = "State, State, ...",
				FontSize = 16.0,
				Keyboard = Keyboard.Text,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			entPreferedLocation.Completed += (sender, e) => entPreferedCompanySize.Focus();

			registrationForm.Children.Add(preferedLocationInput,
                                         new Rectangle(0, 540.0, 1.0, 60.0),
                                          AbsoluteLayoutFlags.WidthProportional);

			//Prefered Company Size
			AbsoluteLayout preferedCompanySizeInput = new AbsoluteLayout()
            {
            };

			preferedCompanySizeInput.Children.Add(lblPreferedCompanySize = new Label()
			{
				Text = "What is your prefered company size?",
				FontSize = 18.0,
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Start,
			}, new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

			preferedCompanySizeInput.Children.Add(entPreferedCompanySize = new Entry()
			{
				Placeholder = "Size",
				FontSize = 16.0,
                Keyboard = Keyboard.Text,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

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

