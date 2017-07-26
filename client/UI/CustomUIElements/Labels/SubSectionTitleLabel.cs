using System;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Labels
{
    public class SubSectionTitleLabel : Label
    {
        public SubSectionTitleLabel(String title)
        {
            Text = title;
            FontSize = 22.0;
            VerticalTextAlignment = TextAlignment.Center;
            HorizontalTextAlignment = TextAlignment.Center;
        }
    }
}
