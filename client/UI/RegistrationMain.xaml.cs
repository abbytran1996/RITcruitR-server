﻿using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace TMCS_Client.UI
{
    public partial class RegistrationMain : ContentPage
    {
        private static RegistrationMain registrationMainPage = null;

        private RegistrationMain()
        {
            this.Title = "Registration";

			Label lblTitle = new Label()
			{
				Text = "Welcome To RecruitR!",
                TextColor = Color.Blue,
                FontAttributes = FontAttributes.Bold,
				FontSize = 46.0,
                VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Center,

			};


			AbsoluteLayout roleChoiceSection = new AbsoluteLayout()
			{
                //BackgroundColor = Color.SandyBrown,
			};

            roleChoiceSection.Children.Add(new Label()
            {
                Text = "Choose A Role You Want To Register As:",
                FontSize = 25,
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.Center,
            },

			new Rectangle(0.5, 0.2 , 1.0, 0.2),AbsoluteLayoutFlags.All);


            roleChoiceSection.Children.Add(new Button()
            {
                Text = "Student",
                TextColor = Color.Black,
                FontSize = 25,
                BorderColor = Color.SteelBlue,
				BorderWidth = 2,
                BorderRadius = 25,
                HorizontalOptions = new LayoutOptions(LayoutAlignment.Fill, true),
                Command = new Command((object obj) => this.Navigation.PushAsync(new StudentRegistration())),
            },
            new Rectangle(0.5, 0.6, 0.4, 0.15),AbsoluteLayoutFlags.All);
           
            roleChoiceSection.Children.Add(new Button()
            {
                Text = "Recruiter",
                TextColor = Color.Black,
				FontSize = 25,
				BorderColor = Color.DarkSlateBlue,
				BorderWidth = 2,
                BorderRadius = 25,
                HorizontalOptions = new LayoutOptions(LayoutAlignment.Fill, true),
                Command = new Command((object obj) => this.Navigation.PushAsync(new RecruiterRegistration())),
            },
                                      new Rectangle(0.5, 0.85 , 0.4, 0.15), AbsoluteLayoutFlags.All);



			AbsoluteLayout pageContent = new AbsoluteLayout()
			{
			};

			pageContent.Children.Add(lblTitle, new Rectangle(0.5, 0.1, 0.7, 0.3),
                                     AbsoluteLayoutFlags.All);
            pageContent.Children.Add(roleChoiceSection, new Rectangle(0.5, 1.0, 1.0, 0.7),
                                     AbsoluteLayoutFlags.All);

            Content = pageContent;
        }

        public static RegistrationMain getRegistrationMainPage(){
            if(registrationMainPage == null){
                registrationMainPage = new RegistrationMain();
            }

            return registrationMainPage;
        }
    }
}
