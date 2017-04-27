using System;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Labels
{
    public class PageTitleLabel : Label
    {
        public PageTitleLabel(String title)
        {
            Text = title;
            FontSize = 28.0;
            VerticalTextAlignment = TextAlignment.Center;
            HorizontalTextAlignment = TextAlignment.Center;
        }
    }
}
