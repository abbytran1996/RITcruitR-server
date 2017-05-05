﻿﻿using System;
using TMCS_Client;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;

using Xamarin.Forms;

namespace TMCS_Client.UI
{
    public class Login : ContentPage
    {
        private static Login loginPage = null;

        private AbsoluteLayout sectionLogin = null;

        private Entry statusMessage;
        private Entry emailEntry;
        private Entry passwordEntry;

        private ActivityIndicator loginBusyIndicator;

#if __IOS__
        //const double ROW_HEIGHT = 0.4;
#endif
#if __ANDROID__
		//const double ROW_HEIGHT = 0.20;
#endif

		private Login()
        {
            this.Title = "Login";

            Label lblTitle = new Label
            {
                Text = "RecruitR",
                FontSize = 36.0,
                VerticalTextAlignment = TextAlignment.End,
                HorizontalTextAlignment = TextAlignment.Center,
            };

            sectionLogin = new AbsoluteLayout()
            {
            };

            emailEntry = new Entry() {
                Placeholder = "Email",
                Keyboard = Keyboard.Email,
            };

            passwordEntry = new Entry() {
                Placeholder = "Password",
                IsPassword = true,
            };

            sectionLogin.Children.Add(emailEntry, new Rectangle(0.5,0.2,0.8,0.20), AbsoluteLayoutFlags.All);
            sectionLogin.Children.Add(passwordEntry, new Rectangle(0.5, 0.5, 0.8, 0.2), AbsoluteLayoutFlags.All);

            sectionLogin.Children.Add(new Button() {
                Text = "Login",
                FontSize = 24,
                TextColor = Color.White,
                BackgroundColor = Color.Blue,
                Command = new Command(doLogin)
			},
									  new Rectangle(0.5, 0.78, 0.6, 0.2), AbsoluteLayoutFlags.All);

			sectionLogin.Children.Add(statusMessage = Constants.Forms.LoginStatusMessage.EMPTY,
									 new Rectangle(0.5, 0, 0.8, 0.15),
									 AbsoluteLayoutFlags.All);

            AbsoluteLayout sectionRegister = new AbsoluteLayout()
            {
            };

			sectionRegister.Children.Add(new Label()
			{
				Text = "Don't have an account?",
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Center,
			},
                                        new Rectangle(0,0,1.0,0.1),
                                        AbsoluteLayoutFlags.All);
            sectionRegister.Children.Add(new Button()
            {
                Text = "Register",
                TextColor = Color.Blue,
                BorderColor = Color.Blue,
                BorderWidth = 2,
                Command = new Command((object obj) => this.Navigation.PushAsync(RegistrationMain.getRegistrationMainPage())),
			},
										new Rectangle(0.5, 0.2, 0.25, 0.25),
										AbsoluteLayoutFlags.All);

            AbsoluteLayout pageContent = new AbsoluteLayout()
            {
			};

            pageContent.Children.Add(lblTitle, new Rectangle(0, 0, 1.0, 0.2),
                                      AbsoluteLayoutFlags.All);
            pageContent.Children.Add(sectionLogin, new Rectangle(0,0.4,1.0,0.5),
                                      AbsoluteLayoutFlags.All);
            pageContent.Children.Add(sectionRegister, new Rectangle(0, 1.0, 1.0, 0.3),
                                      AbsoluteLayoutFlags.All);
            pageContent.Children.Add(loginBusyIndicator = new ActivityIndicator(),
                                    new Rectangle(0, 0, 1.0, 1.0), AbsoluteLayoutFlags.All);

            loginBusyIndicator.IsEnabled = true;
            loginBusyIndicator.IsRunning = true;
            loginBusyIndicator.IsVisible = false;
            loginBusyIndicator.Color = Color.Black;
            loginBusyIndicator.Scale = 2.0;

            Content = pageContent;
        }

        public static Login getLoginPage(){
            if(loginPage == null){
                loginPage = new Login();
            }

            return loginPage;
        }

        public void updateLoginStatusMessage(Entry newLoginStatusMessage){
            sectionLogin.Children.Remove(statusMessage);
            statusMessage = newLoginStatusMessage;
			sectionLogin.Children.Add(statusMessage, new Rectangle(0.5, 0, 0.8, 0.15),
									 AbsoluteLayoutFlags.All);
        }

        public void doLogin(object obj) {
            loginBusyIndicator.IsVisible = true;

            var email = emailEntry.Text;
            var password = passwordEntry.Text;

            var serverController = ServerController.getServerController();
            var user = serverController.login(email, password);

            if (user == null)
            {
                updateLoginStatusMessage(Constants.Forms.LoginStatusMessage.SERVER_CONNECTION_FAILURE);
            }
            else if(user.id == -1){
                updateLoginStatusMessage(Constants.Forms.LoginStatusMessage.USERNAME_OR_PASSWORD_INVALID);
            }
            else
            {

                foreach (var role in user.roles)
                {
                    if (role.name == Role.Name.Student.ToString().ToLower())
                    {
                        // We're a student!
                        var student = StudentController.getStudentController().getStudent(email);
                        (App.Current as App).CurrentStudent = student;
                        Console.WriteLine("Student login detected");
                        Navigation.InsertPageBefore(new StudentHomepage(), Login.getLoginPage());
                        break;
                    }
                    else if (role.name == Role.Name.Recruiter.ToString().ToLower())
                    {
                        // We're a recruiter!
                        var recruiter = RecruiterController.getRecruiterController().getRecruiter(email);
                        //
                        Console.WriteLine("Recruiter login detected");
                        Navigation.InsertPageBefore(new RecruiterHomepage(recruiter), Login.getLoginPage());
                        break;
                    }
                }
                Navigation.PopToRootAsync(false);
            } 
            loginBusyIndicator.IsVisible = false;
        }
    }
}

