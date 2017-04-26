using System;

using Xamarin.Forms;

namespace client
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
                //BackgroundColor = Color.Orange,
            };

            AbsoluteLayout setionLogin = new AbsoluteLayout()
            {
                //BackgroundColor = Color.Blue,
            };

            setionLogin.Children.Add(new Entry()
            {
                Placeholder = "Email",
            },
                                      new Rectangle(0.5,0.2,0.8,0.15), AbsoluteLayoutFlags.All);
            setionLogin.Children.Add(new Entry()
            {
                Placeholder = "Password",
                IsPassword = true,
			},
									  new Rectangle(0.5, 0.4, 0.8, 0.15), AbsoluteLayoutFlags.All);
            setionLogin.Children.Add(new Button()
            {
                Text = "Login",
                FontSize = 24,
			},
									  new Rectangle(0.5, 0.6, 0.6, 0.2), AbsoluteLayoutFlags.All);

            AbsoluteLayout sectionRegister = new AbsoluteLayout()
            {
                //BackgroundColor = Color.Green,
            };

			sectionRegister.Children.Add(new Label()
			{
				Text = "Don't have an account?",
				HorizontalOptions = new LayoutOptions(LayoutAlignment.Fill, true),
				VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Center,
			},
                                        new Rectangle(0,0,1.0,0.1),
                                        AbsoluteLayoutFlags.All);
			sectionRegister.Children.Add(new Button()
			{
				Text = "Register",
				HorizontalOptions = new LayoutOptions(LayoutAlignment.Fill, true),
			},
										new Rectangle(0.5, 0.15, 0.25, 0.3),
										AbsoluteLayoutFlags.All);

            AbsoluteLayout pageContent = new AbsoluteLayout()
            {
			};

            pageContent.Children.Add(lblTitle, new Rectangle(0, 0, 1.0, 0.2),
                                      AbsoluteLayoutFlags.All);
            pageContent.Children.Add(setionLogin, new Rectangle(0,0.4,1.0,0.5),
                                      AbsoluteLayoutFlags.All);
            pageContent.Children.Add(sectionRegister, new Rectangle(0, 1.0, 1.0, 0.3),
                                      AbsoluteLayoutFlags.All);


            Content = pageContent;
        }
    }
}

