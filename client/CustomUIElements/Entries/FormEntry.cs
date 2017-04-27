using System;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Entries
{
    public class FormEntry : Entry
    {
        public FormEntry(String placeholder, Keyboard keyboard, Boolean isPassword = false)
        {
            Placeholder = placeholder;
            Keyboard = keyboard;
			IsPassword = isPassword;
            FontSize = 16.0;
        }
    }
}
