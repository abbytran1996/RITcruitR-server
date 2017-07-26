using System;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Labels
{
    public class FormFieldLabel : Label
    {
        public FormFieldLabel(String fieldName)
        {
            Text = fieldName;
            FontSize = 18;
            VerticalTextAlignment = TextAlignment.Center;
            HorizontalTextAlignment = TextAlignment.Start;
        }
    }
}
