using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Buttons
{
    class DeclineButton : Button
    {
        public DeclineButton() : base()
        {
            Text = "Decline";
            BackgroundColor = Color.Red;
            TextColor = Color.White;
            FontSize = 18.0;
            FontAttributes = FontAttributes.Bold;
        }
    }
}
