﻿using System.Collections.Generic;
using System.Linq;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.CustomUIElements.ViewCells;
using TMCS_Client.DTOs;
using Xamarin.Forms;
using System;

namespace TMCS_Client.UI {
    public class StudentTab : TabbedPage {

        public StudentTab() {
            var matchesPage = new NavigationPage(new StudentHomepage());
            matchesPage.Title = "Matches";

            Children.Add(matchesPage);
            var probPage = new NavigationPage(new StudentProblemList());
            probPage.Title = "Problem Phase";
            Children.Add(probPage);
            
        }

       
        
    }
}
