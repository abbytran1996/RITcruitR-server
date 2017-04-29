using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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

        //Title
        PageTitleLabel lblTitle;

        //Position Name
        //FormFieldLabel lblPositionName;
        //FormEntry entPositionName;

        //Description
        //FormFieldLabel lblDescription;
        //FormEditor editorDescription;

        //All Skills: Get the list of skills from skills controller call
        List<Skill> allSkills = new List<Skill>{ new Skill() {id=0, name = "Communication" },
            new Skill(){id=1,name = "Team Work"},
            new Skill(){id=2,name = "C/C++"},
            new Skill(){id=3,name = "Object Oriented Principles"},
            new Skill(){id=4,name = "Technical Writing"},
            new Skill(){id=5,name = "Problem Solving"},};

        //Required Skills
        AbsoluteLayout requiredSkillsSection;
        SubSectionTitleLabel lblRequiredSkills;
		FormSearchBar<Skill> skillsSearchBar;
        FormSearchResultsListView<Skill> skillsSearchResults;
        FormFieldLabel lblChosenRequiredSkills;
        FormListView<Skill> requiredSkills;

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

            //Whole Page
            pageContent = new ScrollView()
            {

            };

			creationForm = new AbsoluteLayout()
			{
				HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT * 15,
			};

            //Title
            lblTitle = new PageTitleLabel("Job Posting Creation") {
            };
            creationForm.Children.Add(lblTitle,
                                     new Rectangle(0.0,0.0,1.0,Constants.Forms.Sizes.ROW_HEIGHT),
                                     AbsoluteLayoutFlags.WidthProportional |
                                     AbsoluteLayoutFlags.XProportional);

            //Required Skills
            requiredSkillsSection = new AbsoluteLayout()
            {
                HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT * 5,
            };

            lblRequiredSkills = new SubSectionTitleLabel("Required Skills Seletion");
            
            requiredSkills = new FormListView<Skill>(
                new ObservableCollection<Skill>(new Skill[]{Skill.NullSkill,}));

			skillsSearchResults = new FormSearchResultsListView<Skill>(requiredSkills);

            skillsSearchBar = new FormSearchBar<Skill>("Search for required skills",
                                                      allSkills, skillsSearchResults);

            lblChosenRequiredSkills = new FormFieldLabel("Chosen Required Skills");

            requiredSkillsSection.Children.Add(lblRequiredSkills,
                                              new Rectangle(0.0, 0.0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT * 1),
                                               AbsoluteLayoutFlags.XProportional | 
                                               AbsoluteLayoutFlags.WidthProportional);

            requiredSkillsSection.Children.Add(skillsSearchBar,
                                     new Rectangle(0,Constants.Forms.Sizes.ROW_HEIGHT,
                                                   1.0,Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
									 AbsoluteLayoutFlags.XProportional |
									AbsoluteLayoutFlags.WidthProportional);

			requiredSkillsSection.Children.Add(lblChosenRequiredSkills,
									 new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
												  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

            requiredSkillsSection.Children.Add(requiredSkills,
                                              new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 2,
                                                           0.9,Constants.Forms.Sizes.ROW_HEIGHT * 3),
                                              AbsoluteLayoutFlags.XProportional | 
                                               AbsoluteLayoutFlags.WidthProportional);
            
			requiredSkillsSection.Children.Add(skillsSearchResults,
									 new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
												  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 3),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);


            creationForm.Children.Add(requiredSkillsSection,
                                     new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 1,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 5),
                                     AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

            //Next Section

            pageContent.Content = creationForm;
            Content = pageContent;
        }

    }
}

