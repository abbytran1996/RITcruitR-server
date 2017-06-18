using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Buttons {
    class AcceptButton : Button {
        public AcceptButton() : base() {
            Text = "Accept";
            BackgroundColor = Color.Green;
            TextColor = Color.White;
            FontSize = 18.0;
            FontAttributes = FontAttributes.Bold;
        }
    }
}
