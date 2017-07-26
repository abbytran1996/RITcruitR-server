using System;
using System.Collections.Generic;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Pickers
{
    public class CompanySizePicker : Picker
    {
        public CompanySizePicker()
        {
            foreach(Company.Size size in Enum.GetValues(typeof(Company.Size)))
            {
                Items.Add(size.ToString());
            }
            this.Title = "Preferred Company Size";
        }

        public string getPreferredSize()
        {
            String preferredSize;
            if(this.SelectedItem == null)
            {
                preferredSize = null;
            }
            else
            {
                preferredSize = this.SelectedItem.ToString().ToUpper();
            }
            return preferredSize;
        }
    }
}
