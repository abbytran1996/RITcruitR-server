using System;
using Xamarin.Forms.PlatformConfiguration;
using Xamarin.Forms.PlatformConfiguration.iOSSpecific;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Editors
{
    public class FormEditor : Editor
    {
        public FormEditor() : base()
        {
            base.BackgroundColor = Constants.Forms.Colors.EDITOR_BACKGROUND;
            base.FontSize = 16.0;

        }
    }
}
