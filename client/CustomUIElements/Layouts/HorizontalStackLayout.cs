using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Layouts {
    /// <summary>
    /// A stack layout which puts things in a horizontal direction
    /// </summary>
    public class HorizontalStackLayout : StackLayout {
        public HorizontalStackLayout() {
            Orientation = StackOrientation.Horizontal;
        }
    }
}
