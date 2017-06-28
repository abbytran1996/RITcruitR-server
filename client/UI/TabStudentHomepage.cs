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
            var probPage = new StudentProblemList();
            var presentationPage = new StudentPresentationList();

#endif
#if __ANDROID__
            var matchesPage = new NavigationPage(new StudentHomepage());
            var probPage = new NavigationPage(new StudentProblemList());
            var presentationPage = new NavigationPage(new StudentPresentationList());

#endif
			matchesPage.Title = "Matches";
			Children.Add(matchesPage);
            probPage.Title = "Problem Phase";
            Children.Add(probPage);
            presentationPage.Title = "Presentation Phase";
            Children.Add(presentationPage);


        }

       
        
    }
}
