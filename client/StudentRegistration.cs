using System;

using Xamarin.Forms;

namespace client
{
    public class StudentRegistration : ContentPage
    {
        public StudentRegistration()
        {
            ScrollView pageContent = new ScrollView()
            {
                Orientation = ScrollOrientation.Vertical,

            };

            AbsoluteLayout registrationForm = new AbsoluteLayout()
            {
                HeightRequest = (60.0 * 12.0),
			};

            registrationForm.Children.Add( new Label(){
                Text = "Student Registration",
                FontSize = 28.0,
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.Center,
            }, new Rectangle(0,0,1.0,60.0), AbsoluteLayoutFlags.WidthProportional);

            AbsoluteLayout firstNameInput = new AbsoluteLayout()
            {
                //BackgroundColor = Color.Orange,
            };

            firstNameInput.Children.Add(new Label()
            {
                Text = "First Name",
                FontSize = 18.0,
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.Start,
            }, new Rectangle(0.5,0,0.9,0.5), AbsoluteLayoutFlags.All);

            firstNameInput.Children.Add(new Entry(){
                Placeholder = "First Name",
                FontSize = 16.0,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            registrationForm.Children.Add(firstNameInput,
                                         new Rectangle(0, 60.0, 1.0, 60.0),
                                          AbsoluteLayoutFlags.WidthProportional);

            pageContent.Content = registrationForm;

            Content = pageContent;

        }
    }
}

