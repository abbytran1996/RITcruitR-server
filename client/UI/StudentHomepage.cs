using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    public class StudentHomepage : ContentPage {
        private AbsoluteLayout menu;
        private ScrollView pageContent = new ScrollView();

        public StudentHomepage() {
            loadMatches();

            Content = pageContent;
        }

        private View makeJobPostingItem(Match match) {

        }

        /// <summary>
        /// Grabs the Match objects for the current Student, and sets those as this view's items
        /// </summary>
        private void loadMatches() {

        }
    }
}
