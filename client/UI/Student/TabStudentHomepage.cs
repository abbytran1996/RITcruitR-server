using System.Collections.Generic;
using System.Linq;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.CustomUIElements.ViewCells;
using TMCS_Client.UI.Student;
using TMCS_Client.DTOs;
using Xamarin.Forms;
using System;

namespace TMCS_Client.UI
{
    public class StudentTab : TabbedPage
    {

        public StudentTab()
        {
            var matchesPage = new StudentHomepage();
            var probPage = new StudentProblemList();
            var presentationPage = new StudentPresentationList();
            var interviewPage = new StudentInterviewList();



            var editProfileButton = new ToolbarItem()
            {
                Text = "Edit profile",
                Command = new Command(goToEditProfile),
                Order = ToolbarItemOrder.Secondary
            };

			var manageSkillsButton = new ToolbarItem()
			{
				Text = "Manage Skills",
                Command = new Command(goToManageSkills),
                Order = ToolbarItemOrder.Secondary
			};

            ToolbarItems.Add(editProfileButton);
            ToolbarItems.Add(manageSkillsButton);
            matchesPage.Title = "Matches";
			Children.Add(matchesPage);
            probPage.Title = "Problem Phase";
            Children.Add(probPage);
            presentationPage.Title = "Presentation Phase";
            Children.Add(presentationPage);
            interviewPage.Title = "Interview Phase";
            Children.Add(interviewPage);
        }

        private void goToEditProfile()
        {
            Navigation.PushAsync(new StudentEditProfile(((App)App.Current).CurrentStudent));
        }

        private void goToManageSkills()
        {
            Navigation.PushAsync(new SkillsEditing(((App)App.Current).CurrentStudent));
        }
    }
}
