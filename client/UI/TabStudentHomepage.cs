using System.Collections.Generic;
using System.Linq;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.CustomUIElements.ViewCells;
using TMCS_Client.DTOs;
using Xamarin.Forms;
using System;

namespace TMCS_Client.UI
{
    public class StudentTab : TabbedPage
    {

        public StudentTab()
        {
#if __IOS__
            var matchesPage = new StudentHomepage();

#endif
#if __ANDROID__
            var matchesPage = new NavigationPage(new StudentHomepage());
#endif
            matchesPage.Title = "Matches";

            Children.Add(matchesPage);
#if __IOS__
            var probPage = new StudentProblemList();

#endif
#if __ANDROID__
            var probPage = new NavigationPage(new StudentProblemList());
#endif
            probPage.Title = "Problem Phase";
            Children.Add(probPage);
            
        }

       
        
    }
}
