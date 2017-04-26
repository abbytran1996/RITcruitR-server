using System;

using Xamarin.Forms;

namespace client
{
    public class RegistrationMain : ContentPage
    {
        public RegistrationMain()
        {
            Content = new StackLayout
            {
                Children = {
                    new Label { Text = "Hello ContentPage" }
                }
            };
        }
    }
}

