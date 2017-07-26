using System;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Buttons
{
    public class FormSubmitButton : Button
    {
        public FormSubmitButton(String phrase) : base()
        {
            Text = phrase;
            FontSize = 24;
            TextColor = Color.White;
            BackgroundColor = Color.Blue;
        }
    }
}
