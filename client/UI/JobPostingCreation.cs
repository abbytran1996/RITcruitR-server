﻿using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.Entries;
using TMCS_Client.CustomUIElements.Editors;
using TMCS_Client.CustomUIElements.SearchBars;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.CustomUIElements.ViewCells;
using TMCS_Client.CustomUIElements.Buttons;

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
        FormFieldLabel lblPositionName;
        FormEntry entPositionName;

        //Description
        FormFieldLabel lblDescription;
        FormEditor editorDescription;

        //All Skills: Get the list of skills from skills controller call
        List<Skill> allSkills = new List<Skill>{ new Skill() {id=6, name = "Communication" },
            new Skill(){id=1,name = "Team Work"},
            new Skill(){id=2,name = "C/C++"},
            new Skill(){id=3,name = "Object Oriented Principles"},
            new Skill(){id=4,name = "Technical Writing"},
            new Skill(){id=5,name = "Problem Solving"},};

        //Required Skills
        AbsoluteLayout requiredSkillsSection;
        SubSectionTitleLabel lblRequiredSkills;
        FormSearchBar<Skill, SkillSearchResultCell, SkillListCell> requiredSkillsSearchBar;
        FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell> requiredSkillsSearchResults;
        FormFieldLabel lblChosenRequiredSkills;
        FormListView<Skill, SkillListCell> requiredSkills;

		//Recommended Skills
		AbsoluteLayout recommendedSkillsSection;
		SubSectionTitleLabel lblRecommendedSkills;
		FormSearchBar<Skill, SkillSearchResultCell, SkillListCell> recommendedSkillsSearchBar;
		FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell> recommendedSkillsSearchResults;
		FormFieldLabel lblChosenRecommendedSkills;
		FormListView<Skill, SkillListCell> recommendedSkills;

        //Recruiter
        private Recruiter associatedRecruiter = null;

		//Location
		FormFieldLabel lblLocation;
		FormEntry entLocation;

        //Phase Timeout
        FormFieldLabel lblPhaseTimeout;
        FormEntry entPhaseTimeout;

        //Problem Statement
        FormFieldLabel lblProblemStatement;
        FormEditor editorProblemStatement;

		//Webpage URL
		FormFieldLabel lblWebpageURL;
		FormEntry entWebpageURL;

        //Create
        FormSubmitButton btnCreate;

        public JobPostingCreation(Recruiter associatedRecruiter)
        {
            this.Title = "Job Posting Creation";

            this.associatedRecruiter = associatedRecruiter;

            //get scills

            //Whole Page
            pageContent = new ScrollView()
            {

            };

			creationForm = new AbsoluteLayout()
			{
				HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT * 22,
			};

            //Title
            lblTitle = new PageTitleLabel("Job Posting Creation") {
            };
            creationForm.Children.Add(lblTitle,
                                     new Rectangle(0.0,0.0,1.0,Constants.Forms.Sizes.ROW_HEIGHT),
                                     AbsoluteLayoutFlags.WidthProportional |
                                     AbsoluteLayoutFlags.XProportional);

            //Position Name
            AbsoluteLayout positionNameSection = new AbsoluteLayout();

            positionNameSection.Children.Add(lblPositionName = new FormFieldLabel("Position Name"),
                                            new Rectangle(0.5, 0.0, 0.9, 0.5),
                                             AbsoluteLayoutFlags.All);
            positionNameSection.Children.Add(entPositionName =
                                             new FormEntry("Position Name", Keyboard.Plain),
                                            new Rectangle(0.5, 1.0, 0.9, 0.5),
                                            AbsoluteLayoutFlags.All);
            entPositionName.Completed += (object sender, EventArgs e) => editorDescription.Focus();
            creationForm.Children.Add(positionNameSection,
                                     new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 1,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT),
                                      AbsoluteLayoutFlags.XProportional | 
                                      AbsoluteLayoutFlags.WidthProportional);

			//Description
			AbsoluteLayout descriptionSection = new AbsoluteLayout();

			descriptionSection.Children.Add(lblDescription = new FormFieldLabel("Position Description"),
											new Rectangle(0.5, 0.0, 0.9, 1.0/6.0),
											 AbsoluteLayoutFlags.All);
			descriptionSection.Children.Add(editorDescription =
											 new FormEditor(),
											new Rectangle(0.5, 1.0, 0.9, 5.0/6.0),
											AbsoluteLayoutFlags.All);
            editorDescription.Completed += (object sender, EventArgs e) => requiredSkillsSearchBar.Focus();
			creationForm.Children.Add(descriptionSection,
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 2,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 3),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

            //Required Skills
            requiredSkillsSection = new AbsoluteLayout();

            lblRequiredSkills = new SubSectionTitleLabel("Required Skills Selection");
            
            requiredSkills = new FormListView<Skill, SkillListCell>(Skill.NullSkill);

			requiredSkillsSearchResults = new FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell>(requiredSkills);

            requiredSkillsSearchBar = new FormSearchBar<Skill, SkillSearchResultCell, SkillListCell>("Search for required skills",
                                                      allSkills, requiredSkillsSearchResults);

            lblChosenRequiredSkills = new FormFieldLabel("Chosen Required Skills");

            requiredSkillsSection.Children.Add(lblRequiredSkills,
                                              new Rectangle(0.0, 0.0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT * 1),
                                               AbsoluteLayoutFlags.XProportional | 
                                               AbsoluteLayoutFlags.WidthProportional);

            requiredSkillsSection.Children.Add(requiredSkillsSearchBar,
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
            
			requiredSkillsSection.Children.Add(requiredSkillsSearchResults,
									 new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
												  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 3),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);


            creationForm.Children.Add(requiredSkillsSection,
                                     new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 5,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 5),
                                     AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

			//Recommended Skills
			recommendedSkillsSection = new AbsoluteLayout();

			lblRecommendedSkills = new SubSectionTitleLabel("Recommended Skills Selection");

			recommendedSkills = new FormListView<Skill, SkillListCell>(Skill.NullSkill);

			recommendedSkillsSearchResults = new FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell>(recommendedSkills);

			recommendedSkillsSearchBar = new FormSearchBar<Skill, SkillSearchResultCell, SkillListCell>("Search for recommended skills",
													  allSkills, recommendedSkillsSearchResults);

			lblChosenRecommendedSkills = new FormFieldLabel("Chosen Recommended Skills");

			recommendedSkillsSection.Children.Add(lblRecommendedSkills,
											  new Rectangle(0.0, 0.0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT * 1),
											   AbsoluteLayoutFlags.XProportional |
											   AbsoluteLayoutFlags.WidthProportional);

			recommendedSkillsSection.Children.Add(recommendedSkillsSearchBar,
									 new Rectangle(0, Constants.Forms.Sizes.ROW_HEIGHT,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
									 AbsoluteLayoutFlags.XProportional |
									AbsoluteLayoutFlags.WidthProportional);

			recommendedSkillsSection.Children.Add(lblChosenRecommendedSkills,
									 new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
												  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

			recommendedSkillsSection.Children.Add(recommendedSkills,
											  new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 2,
														   0.9, Constants.Forms.Sizes.ROW_HEIGHT * 3),
											  AbsoluteLayoutFlags.XProportional |
											   AbsoluteLayoutFlags.WidthProportional);

			recommendedSkillsSection.Children.Add(recommendedSkillsSearchResults,
									 new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
												  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 3),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);


			creationForm.Children.Add(recommendedSkillsSection,
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 10,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 5),
									 AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

			//Location
			AbsoluteLayout locationSection = new AbsoluteLayout();

			locationSection.Children.Add(lblLocation = new FormFieldLabel("Position Location"),
											new Rectangle(0.5, 0.0, 0.9, 0.5),
											 AbsoluteLayoutFlags.All);
			locationSection.Children.Add(entLocation =
											 new FormEntry("Position Location", Keyboard.Plain),
											new Rectangle(0.5, 1.0, 0.9, 0.5),
											AbsoluteLayoutFlags.All);
			entLocation.Completed += (object sender, EventArgs e) => entPhaseTimeout.Focus();
			creationForm.Children.Add(locationSection,
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 15,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

			//Location
			AbsoluteLayout phaseTimeoutSection = new AbsoluteLayout();

			phaseTimeoutSection.Children.Add(lblPhaseTimeout = new FormFieldLabel("Phase Timeout"),
											new Rectangle(0.5, 0.0, 0.9, 0.5),
											 AbsoluteLayoutFlags.All);
			phaseTimeoutSection.Children.Add(entPhaseTimeout =
											 new FormEntry("Days", Keyboard.Numeric),
											new Rectangle(0.5, 1.0, 0.9, 0.5),
											AbsoluteLayoutFlags.All);
			entLocation.Completed += (object sender, EventArgs e) => editorProblemStatement.Focus();
			creationForm.Children.Add(phaseTimeoutSection,
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 16,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);
			//Problem Statement
			AbsoluteLayout problemStatementSection = new AbsoluteLayout();

			problemStatementSection.Children.Add(lblProblemStatement = new FormFieldLabel("Problem Statement"),
											new Rectangle(0.5, 0.0, 0.9, 1.0 / 6.0),
											 AbsoluteLayoutFlags.All);
			problemStatementSection.Children.Add(editorProblemStatement =
											 new FormEditor(),
											new Rectangle(0.5, 1.0, 0.9, 5.0 / 6.0),
											AbsoluteLayoutFlags.All);
			editorProblemStatement.Completed += (object sender, EventArgs e) => entWebpageURL.Focus();
			creationForm.Children.Add(problemStatementSection,
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 17,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 3),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

			//Webpage URL
			AbsoluteLayout webpageURLSection = new AbsoluteLayout();

			webpageURLSection.Children.Add(lblWebpageURL = new FormFieldLabel("Webpage URL"),
											new Rectangle(0.5, 0.0, 0.9, 0.5),
											 AbsoluteLayoutFlags.All);
			webpageURLSection.Children.Add(entWebpageURL =
											 new FormEntry("Webpage URL", Keyboard.Url),
											new Rectangle(0.5, 1.0, 0.9, 0.5),
											AbsoluteLayoutFlags.All);
			entWebpageURL.Completed += (object sender, EventArgs e) => entWebpageURL.Unfocus();
			creationForm.Children.Add(webpageURLSection,
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 20,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

            //Create
            btnCreate = new FormSubmitButton("Create Job Posting");
            btnCreate.Command = new Command((object obj) => createJobPosting());

			creationForm.Children.Add(btnCreate,
										  new Rectangle(0.5, ((21.0 * Constants.Forms.Sizes.ROW_HEIGHT) + 10.0), 0.8, Constants.Forms.Sizes.ROW_HEIGHT - 20.0),
										  AbsoluteLayoutFlags.WidthProportional |
										 AbsoluteLayoutFlags.XProportional);

            pageContent.Content = creationForm;
            Content = pageContent;
        }

        private void createJobPosting(){
            JobPosting newJobPosting = new JobPosting();
            newJobPosting.location = "California";
            newJobPosting.phaseTimeout = 2;
            newJobPosting.description = "Well it is a job atleast...";
            newJobPosting.positionTitle = "Software Engineer?";
            newJobPosting.minMatchedRequiredSkills = 4;
            newJobPosting.recommendedSkillsWeight = 0.5;
            newJobPosting.url = "google.com";
            newJobPosting.requiredSkills = new List<Skill>(new Skill[]{allSkills[2],allSkills[1]});
            newJobPosting.recommendedSkills = new List<Skill>(new Skill[] { allSkills[3] });
            newJobPosting.recruiter = new Recruiter(){
                id=1,
            };
            newJobPosting.problemStatement = "Find x.";

            JobPostingController.getJobPostingController().createJobPosting(newJobPosting);
        }
    }
}

