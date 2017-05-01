﻿using System;
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

        private Entry emailEntry;
        private Entry passwordEntry;

        private Login()
        {
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

            sectionLogin.Children.Add(emailEntry, new Rectangle(0.5,0.2,0.8,0.15), AbsoluteLayoutFlags.All);
            sectionLogin.Children.Add(passwordEntry, new Rectangle(0.5, 0.4, 0.8, 0.15), AbsoluteLayoutFlags.All);

            sectionLogin.Children.Add(new Button() {
                Text = "Login",
                FontSize = 24,
                TextColor = Color.White,
                BackgroundColor = Color.Blue,
                Command = new Command(doLogin)
			},
									  new Rectangle(0.5, 0.6, 0.6, 0.15), AbsoluteLayoutFlags.All);

            //THIS MUST GO IN LAST
			sectionLogin.Children.Add(Constants.Forms.LoginStatusMessage.EMPTY,
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
										new Rectangle(0.5, 0.15, 0.25, 0.2),
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

            Content = pageContent;
        }

        public static Login getLoginPage(){
            if(loginPage == null){
                loginPage = new Login();
            }

            return loginPage;
        }

        public void updateLoginStatusMessage(Entry newLoginStatusMessage){
            sectionLogin.Children.RemoveAt(sectionLogin.Children.Count - 1);
            sectionLogin.Children.Add(newLoginStatusMessage,
                                     new Rectangle(0.5,0,0.8,0.15),
                                     AbsoluteLayoutFlags.All);
        }

        public void doLogin(object obj) {
            var email = emailEntry.Text;
            var password = passwordEntry.Text;

            var serverController = ServerController.getServerController();
            var user = serverController.login(email, password);

            foreach (var role in user.roles) {
                if(role.name == Role.Name.Student.ToString().ToLower()) {
                    // We're a student!
                    var student = StudentController.getStudentController().getStudent(email);
                    (App.Current as App).CurrentStudent = student;
                    Console.WriteLine("Student login detected");
                    Navigation.PushAsync(new StudentHomepage());
                } else if(role.name == Role.Name.Recruiter.ToString().ToLower()) {
                    // We're a recruiter!
                    Console.WriteLine("Recruiter login detected");
                }
            }
        }
    }
}

