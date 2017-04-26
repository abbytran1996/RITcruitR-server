using System;

using Xamarin.Forms;

namespace client
{
    public class StudentRegistration : ContentPage
    {
        //Whole Page
        ScrollView pageContent;
        AbsoluteLayout registrationForm;

        //Title
        Label lblTitle;

        //First Name
        Label lblFirstName;
        Entry entFirstName;

        //Last 
        Label lblLastName;
        Entry entLastName;

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

            registrationForm.Children.Add(firstNameInput,
                                         new Rectangle(0, 60.0, 1.0, 60.0),
                                          AbsoluteLayoutFlags.WidthProportional);

			//LastName
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

			registrationForm.Children.Add(lastNameInput,
										 new Rectangle(0, 120.0, 1.0, 60.0),
										  AbsoluteLayoutFlags.WidthProportional);
            
            pageContent.Content = registrationForm;

            Content = pageContent;

        }
    }
}

