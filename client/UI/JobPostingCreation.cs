using System;
using System.Collections.Generic;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.Entries;
using TMCS_Client.CustomUIElements.Editors;
using TMCS_Client.CustomUIElements.SearchBars;
using TMCS_Client.CustomUIElements.ListViews;

using Xamarin.Forms;

namespace TMCS_Client.UI
{
    public class JobPostingCreation : ContentPage
    {
        //Whole Page
        private ScrollView pageContent;
        private AbsoluteLayout creationForm;

        //Position Name
        //FormFieldLabel lblPositionName;
        //FormEntry entPositionName;

        //Description
        //FormFieldLabel lblDescription;
        //FormEditor editorDescription;

        //All Skills: Get the list of skills from skills controller call
        List<Skill> allSkills = new List<Skill>{ new Skill() { name = "Communication" },
            new Skill(){name = "Team Work"},
            new Skill(){name = "C/C++"},
            new Skill(){name = "Object Oriented Principles"},
            new Skill(){name = "Technical Writing"},
            new Skill(){name = "Problem Solving"},};
		
        //Required Skills
		FormSearchBar<Skill> skillsSearchBar;
        FormSearchResultsListView<Skill> skillsSearchResults;
        FormListView requiredSkills;

        //Recommended Skills

        //Recruiter
        private Recruiter associatedRecruiter = null;

        //Location

        //Phase Timeout

        //Problem Statement

        //Webpage URL

        public JobPostingCreation(Recruiter associatedRecruiter)
        {
            this.associatedRecruiter = associatedRecruiter;

            pageContent = new ScrollView()
            {

            };

			creationForm = new AbsoluteLayout()
			{
				HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT * 8,
			};

			skillsSearchResults = new FormSearchResultsListView<Skill>();

            skillsSearchBar = new FormSearchBar<Skill>("Search for required skills",
                                                      allSkills, skillsSearchResults)
            {
            };

            requiredSkills = new FormListView();

            creationForm.Children.Add(skillsSearchBar,
                                     new Rectangle(0,0,1.0,Constants.Forms.Sizes.ROW_HEIGHT),
                                     AbsoluteLayoutFlags.WidthProportional);

            creationForm.Children.Add(skillsSearchResults,
                                     new Rectangle(0.5,Constants.Forms.Sizes.ROW_HEIGHT * 1,
                                                  0.9,Constants.Forms.Sizes.ROW_HEIGHT * 3),
                                      AbsoluteLayoutFlags.XProportional | 
                                      AbsoluteLayoutFlags.WidthProportional);

            pageContent.Content = creationForm;
            Content = pageContent;
        }

    }
}

