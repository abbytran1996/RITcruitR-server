using System;

using Xamarin.Forms;

namespace TMCS_Client.UI
{
    public class StudentPresentationPhase : ContentPage
    {
        public StudentPresentationPhase()
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

