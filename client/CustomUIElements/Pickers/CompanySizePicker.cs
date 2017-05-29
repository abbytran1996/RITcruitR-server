using System;
using System.Collections.Generic;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.Pickers
{
    public class CompanySizePicker : Picker
    {
        public CompanySizePicker()
        {
			this.Items.Add("Large");
			this.Items.Add("Medium");
			this.Items.Add("Small");
			this.Title = "Preferred Company Size";
        }

        public string getPreferredSize(){
            String preferredSize;
            if (this.SelectedItem == null){
                preferredSize = null;
            }else{
                preferredSize = this.SelectedItem.ToString().ToUpper();
            }
            return preferredSize;
        }
    }
}
