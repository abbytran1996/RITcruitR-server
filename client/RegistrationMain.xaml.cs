using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace client
{
    public partial class RegistrationMain : ContentPage
    {
        public RegistrationMain()
        {
            
			Label lblTitle = new Label()
			{
				Text = "Welcome To RecruitR!",
				FontSize = 46.0,
                VerticalTextAlignment = TextAlignment.Center,
				HorizontalTextAlignment = TextAlignment.Center,
				BackgroundColor = Color.Teal,

			};


			AbsoluteLayout roleChoiceSection = new AbsoluteLayout()
			{
                //BackgroundColor = Color.SandyBrown,
			};

            roleChoiceSection.Children.Add(new Label()
            {
                Text = "Choose A Role You Want To Register As",
                FontSize = 25,
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.Center,
            },

			new Rectangle(0.5, 0.3 , 1.0, 0.2),AbsoluteLayoutFlags.All);
            

            roleChoiceSection.Children.Add(new Button()
            {
                Text = "Student",
                FontSize = 20,
                HorizontalOptions = new LayoutOptions(LayoutAlignment.Fill, true),
            },
            new Rectangle(0.5, 0.6, 1.0, 0.4),AbsoluteLayoutFlags.All);
           
            roleChoiceSection.Children.Add(new Button()
            {
                Text = "Recruiter",
                FontSize = 20,
                HorizontalOptions = new LayoutOptions(LayoutAlignment.Fill, true),
            },
                                      new Rectangle(0.5, 0.9 , 1.0, 0.4), AbsoluteLayoutFlags.All);



			AbsoluteLayout pageContent = new AbsoluteLayout()
			{
			};

			pageContent.Children.Add(lblTitle, new Rectangle(0.5, 0.2, 0.7, 0.3),
                                     AbsoluteLayoutFlags.All);
            pageContent.Children.Add(roleChoiceSection, new Rectangle(0.5, 1.0, 1.0, 0.7),
                                     AbsoluteLayoutFlags.All);

            Content = pageContent;
        }
    }
}
