using System;

using Xamarin.Forms;

namespace TMCS_Client.UI
{
    public class Login : ContentPage
    {
        public Login()
        {
            Label lblTitle = new Label
            {
                Text = "RecruitR",
                FontSize = 36.0,
                VerticalTextAlignment = TextAlignment.End,
                HorizontalTextAlignment = TextAlignment.Center,
            };

            AbsoluteLayout sectionLogin = new AbsoluteLayout()
            {
            };

            sectionLogin.Children.Add(new Entry()
            {
                Placeholder = "Email",
                Keyboard = Keyboard.Email,
            },
                                      new Rectangle(0.5,0.2,0.8,0.15), AbsoluteLayoutFlags.All);
            sectionLogin.Children.Add(new Entry()
            {
                Placeholder = "Password",
                IsPassword = true,
			},
									  new Rectangle(0.5, 0.4, 0.8, 0.15), AbsoluteLayoutFlags.All);
            sectionLogin.Children.Add(new Button()
            {
                Text = "Login",
                FontSize = 24,
                TextColor = Color.White,
                BackgroundColor = Color.Blue,
			},
									  new Rectangle(0.5, 0.6, 0.6, 0.15), AbsoluteLayoutFlags.All);

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
                Command = new Command((object obj) => this.Navigation.PushAsync(new RegistrationMain())),
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
    }
}

