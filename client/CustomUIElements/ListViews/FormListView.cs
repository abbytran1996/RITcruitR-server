using System;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.ListViews
{
    public class FormListView : ListView
    {
        public FormListView() : base(ListViewCachingStrategy.RetainElement)
        {
            
        }
    }
}
