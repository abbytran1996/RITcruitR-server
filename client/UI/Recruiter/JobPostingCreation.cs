﻿﻿using System;
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
        FormFieldLabel lblPositionTitle;
        FormEntry entPositionTitle;

        //Description
        FormFieldLabel lblDescription;
        FormEditor editorDescription;

        //All Skills: Get the list of skills from skills controller call
        List<Skill> allSkills;

        //Important Skills
        AbsoluteLayout importantSkillsSection;
        SubSectionTitleLabel lblImportantSkills;
        FormSearchBar<Skill, SkillSearchResultCell, SkillListCell> importantSkillsSearchBar;
        FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell> importantSkillsSearchResults;
        FormFieldLabel lblChosenImportantSkills;
        FormListView<Skill, SkillListCell> importantSkills;

		//Nice-to-have Skills
        AbsoluteLayout nicetohaveSkillsSection;
        SubSectionTitleLabel lblNicetohaveSkills;
        FormSearchBar<Skill, SkillSearchResultCell, SkillListCell> nicetohaveSkillsSearchBar;
        FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell> nicetohaveSkillsSearchResults;
        FormFieldLabel lblChosenNicetohaveSkills;
        FormListView<Skill, SkillListCell> nicetohaveSkills;
        FormFieldLabel lblNicetohaveSkillsWeight;
        AbsoluteLayout nicetohaveSkillsImportanceLabels;
        Label lblNicetohaveSkillsImportanceLow;
        Label lblNicetohaveSkillsImportanceMed;
        Label lblNicetohaveSkillsImportanceHigh;
        Slider slidNicetohaveSkillsWeight;

        //Match Threshold
        FormFieldLabel lblMatchThreshold;
        Label lblMatchThresholdLenient;
		Label lblMatchThresholdModerate;
		Label lblMatchThresholdStrict;
		AbsoluteLayout matchThresholdLabels;
        Slider slidMatchThreshold;

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

        //Recruiter
        private Recruiter associatedRecruiter = null;

        public JobPostingCreation(Recruiter associatedRecruiter)
        {
            this.Title = "Job Posting Creation";

            this.associatedRecruiter = associatedRecruiter;

            allSkills = SkillController.getSkillController().getAllSkills();

            //Whole Page
            pageContent = new ScrollView()
            {

            };

            creationForm = new AbsoluteLayout()
            {
                HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT * 25,
            };

            //Title
            lblTitle = new PageTitleLabel("Job Posting Creation")
            {
            };
            creationForm.Children.Add(lblTitle,
                                     new Rectangle(0.0, 0.0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
                                     AbsoluteLayoutFlags.WidthProportional |
                                     AbsoluteLayoutFlags.XProportional);

            //Position Name
            AbsoluteLayout positionNameSection = new AbsoluteLayout();

            positionNameSection.Children.Add(lblPositionTitle = new FormFieldLabel("Position Name"),
                                            new Rectangle(0.5, 0.0, 0.9, 0.5),
                                             AbsoluteLayoutFlags.All);
            positionNameSection.Children.Add(entPositionTitle =
                                             new FormEntry("Position Name", Keyboard.Plain),
                                            new Rectangle(0.5, 1.0, 0.9, 0.5),
                                            AbsoluteLayoutFlags.All);
            entPositionTitle.Completed += (object sender, EventArgs e) => editorDescription.Focus();
            creationForm.Children.Add(positionNameSection,
                                     new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 1,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT),
                                      AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

            //Description
            AbsoluteLayout descriptionSection = new AbsoluteLayout();

            descriptionSection.Children.Add(lblDescription = new FormFieldLabel("Position Description"),
                                            new Rectangle(0.5, 0.0, 0.9, 1.0 / 6.0),
                                             AbsoluteLayoutFlags.All);
            descriptionSection.Children.Add(editorDescription =
                                             new FormEditor(),
                                            new Rectangle(0.5, 1.0, 0.9, 5.0 / 6.0),
                                            AbsoluteLayoutFlags.All);
            editorDescription.Completed += (object sender, EventArgs e) => importantSkillsSearchBar.Focus();
            creationForm.Children.Add(descriptionSection,
                                     new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 2,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 3),
                                      AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

            //Important Skills
            importantSkillsSection = new AbsoluteLayout();

            lblImportantSkills = new SubSectionTitleLabel("Important Skills Selection");

            importantSkills = new FormListView<Skill, SkillListCell>(Skill.NullSkill);

            importantSkillsSearchResults = new FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell>(importantSkills);

            importantSkillsSearchBar = new FormSearchBar<Skill, SkillSearchResultCell, SkillListCell>("Search for required skills",
                                                      allSkills, importantSkillsSearchResults);

            lblChosenImportantSkills = new FormFieldLabel("Chosen Important Skills");

            importantSkillsSection.Children.Add(lblImportantSkills,
                                              new Rectangle(0.0, 0.0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT * 1),
                                               AbsoluteLayoutFlags.XProportional |
                                               AbsoluteLayoutFlags.WidthProportional);

            importantSkillsSection.Children.Add(importantSkillsSearchBar,
                                     new Rectangle(0, Constants.Forms.Sizes.ROW_HEIGHT,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
                                     AbsoluteLayoutFlags.XProportional |
                                    AbsoluteLayoutFlags.WidthProportional);

            importantSkillsSection.Children.Add(lblChosenImportantSkills,
                                     new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
                                                  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
                                      AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

            importantSkillsSection.Children.Add(importantSkills,
                                              new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 2,
                                                           0.9, Constants.Forms.Sizes.ROW_HEIGHT * 3),
                                              AbsoluteLayoutFlags.XProportional |
                                               AbsoluteLayoutFlags.WidthProportional);

            importantSkillsSection.Children.Add(importantSkillsSearchResults,
                                     new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
                                                  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 3),
                                      AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

            creationForm.Children.Add(importantSkillsSection,
                                     new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 5,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 5),
                                     AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

            //Nice-to-have Skills
            nicetohaveSkillsSection = new AbsoluteLayout();

            lblNicetohaveSkills = new SubSectionTitleLabel("Nice-to-have Skills Selection");

            nicetohaveSkills = new FormListView<Skill, SkillListCell>(Skill.NullSkill);

            nicetohaveSkillsSearchResults = new FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell>(nicetohaveSkills);

            nicetohaveSkillsSearchBar = new FormSearchBar<Skill, SkillSearchResultCell, SkillListCell>("Search for recommended skills",
                                                      allSkills, nicetohaveSkillsSearchResults);

            lblChosenNicetohaveSkills = new FormFieldLabel("Chosen Nice-to-have Skills");

            nicetohaveSkillsSection.Children.Add(lblNicetohaveSkills,
                                              new Rectangle(0.0, 0.0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT * 1),
                                               AbsoluteLayoutFlags.XProportional |
                                               AbsoluteLayoutFlags.WidthProportional);

            nicetohaveSkillsSection.Children.Add(nicetohaveSkillsSearchBar,
                                     new Rectangle(0, Constants.Forms.Sizes.ROW_HEIGHT,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
                                     AbsoluteLayoutFlags.XProportional |
                                    AbsoluteLayoutFlags.WidthProportional);

            nicetohaveSkillsSection.Children.Add(lblChosenNicetohaveSkills,
                                     new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
                                                  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
                                      AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

            nicetohaveSkillsSection.Children.Add(nicetohaveSkills,
                                              new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 2,
                                                           0.9, Constants.Forms.Sizes.ROW_HEIGHT * 3),
                                              AbsoluteLayoutFlags.XProportional |
                                               AbsoluteLayoutFlags.WidthProportional);

            nicetohaveSkillsSection.Children.Add(nicetohaveSkillsSearchResults,
                                     new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
                                                  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 3),
                                      AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

            nicetohaveSkillsSection.Children.Add(lblNicetohaveSkillsWeight =
                                                  new FormFieldLabel("Nice-to-have Skills Importance"),
                                              new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 5,
                                                  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
                                               AbsoluteLayoutFlags.XProportional |
                                               AbsoluteLayoutFlags.WidthProportional);

            nicetohaveSkillsImportanceLabels = new AbsoluteLayout();

            nicetohaveSkillsImportanceLabels.Children.Add(lblNicetohaveSkillsImportanceLow =
                                                           new Label() { Text = "Low", 
                                                            HorizontalTextAlignment = TextAlignment.Start},
                                                          new Rectangle(0.0,0.0,1.0,1.0),
                                                          AbsoluteLayoutFlags.All);

			nicetohaveSkillsImportanceLabels.Children.Add(lblNicetohaveSkillsImportanceMed =
														   new Label()
														   {
															   Text = "Medium",
															   HorizontalTextAlignment = TextAlignment.Center
														   },
														  new Rectangle(0.0, 0.0, 1.0, 1.0),
														  AbsoluteLayoutFlags.All);

			nicetohaveSkillsImportanceLabels.Children.Add(lblNicetohaveSkillsImportanceHigh=
														   new Label()
														   {
															   Text = "High",
															   HorizontalTextAlignment = TextAlignment.End
														   },
														  new Rectangle(0.0, 0.0, 1.0, 1.0),
														  AbsoluteLayoutFlags.All);

            nicetohaveSkillsSection.Children.Add(nicetohaveSkillsImportanceLabels,
                                                 new Rectangle(0.5,Constants.Forms.Sizes.ROW_HEIGHT * 5.5,
                                                              0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
												 AbsoluteLayoutFlags.XProportional |
											     AbsoluteLayoutFlags.WidthProportional);
            
			nicetohaveSkillsSection.Children.Add(slidNicetohaveSkillsWeight =
                                                  new Slider( Constants.Forms.RECOMMENDED_SKILLS_WEIGHT.MIN, 
                                                             Constants.Forms.RECOMMENDED_SKILLS_WEIGHT.MAX,
                                                            (Constants.Forms.RECOMMENDED_SKILLS_WEIGHT.MAX + 
                                                              Constants.Forms.RECOMMENDED_SKILLS_WEIGHT.MIN) / 2),
											  new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 6,
												  0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
											   AbsoluteLayoutFlags.XProportional |
											   AbsoluteLayoutFlags.WidthProportional);
			
            creationForm.Children.Add(nicetohaveSkillsSection,
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 10,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 6.5),
									 AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

            //Match Threshold
            AbsoluteLayout matchThresholdSection = new AbsoluteLayout();

            matchThresholdSection.Children.Add(lblMatchThreshold =
                                               new FormFieldLabel("Match Threshold"),
											  new Rectangle(0.5, 0.0, 0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
											 AbsoluteLayoutFlags.XProportional |
											   AbsoluteLayoutFlags.WidthProportional);

            matchThresholdLabels = new AbsoluteLayout();

            matchThresholdLabels.Children.Add(lblMatchThresholdLenient =
                                              new Label() { Text = "Lenient", HorizontalTextAlignment = TextAlignment.Start },
                                             new Rectangle(0.0,0.0,1.0,1.0),
                                             AbsoluteLayoutFlags.All);

			matchThresholdLabels.Children.Add(lblMatchThresholdModerate =
											  new Label() { Text = "Moderate", HorizontalTextAlignment = TextAlignment.Center },
											 new Rectangle(0.0, 0.0, 1.0, 1.0),
											 AbsoluteLayoutFlags.All);

            matchThresholdLabels.Children.Add(lblMatchThresholdStrict =
                                              new Label() { Text = "Strict", HorizontalTextAlignment = TextAlignment.End },
											 new Rectangle(0.0, 0.0, 1.0, 1.0),
											 AbsoluteLayoutFlags.All);

            matchThresholdSection.Children.Add(matchThresholdLabels,
											  new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 0.5,
												   0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
									 AbsoluteLayoutFlags.XProportional |
                                               AbsoluteLayoutFlags.WidthProportional);

            matchThresholdSection.Children.Add(slidMatchThreshold = new Slider(0.0, 1.0, 0.8),
                                              new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT,
                                                   0.9, Constants.Forms.Sizes.ROW_HEIGHT * 0.5),
                                     AbsoluteLayoutFlags.XProportional |
                                               AbsoluteLayoutFlags.WidthProportional);
            
            creationForm.Children.Add(matchThresholdSection,
									 new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 16.5,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 1.5),
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
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 18,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

			//Phase Timeout
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
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 19,
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
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 20,
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
									 new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 23,
												   1.0, Constants.Forms.Sizes.ROW_HEIGHT),
									  AbsoluteLayoutFlags.XProportional |
									  AbsoluteLayoutFlags.WidthProportional);

            //Create
            btnCreate = new FormSubmitButton("Create Job Posting");
            btnCreate.Command = new Command((object obj) => createJobPosting());

			creationForm.Children.Add(btnCreate,
										  new Rectangle(0.5, ((24 * Constants.Forms.Sizes.ROW_HEIGHT) + 10.0), 0.8, Constants.Forms.Sizes.ROW_HEIGHT - 20.0),
										  AbsoluteLayoutFlags.WidthProportional |
										 AbsoluteLayoutFlags.XProportional);

            pageContent.Content = creationForm;
            Content = pageContent;
        }

        private void createJobPosting(){

            String invalidDataMessage = validateForm();
            if (invalidDataMessage != "")
            {
                DisplayAlert("Invalid Data:", invalidDataMessage, "OK");
            }
            else
            {

                JobPosting newJobPosting = new JobPosting();
                newJobPosting.location = entLocation.Text;
                newJobPosting.phaseTimeout = int.Parse(entPhaseTimeout.Text);
                newJobPosting.description = editorDescription.Text;
                newJobPosting.positionTitle = entPositionTitle.Text;
                newJobPosting.recommendedSkillsWeight = slidNicetohaveSkillsWeight.Value;
                newJobPosting.matchThreshold = slidMatchThreshold.Value;
                newJobPosting.url = entWebpageURL.Text;
                newJobPosting.importantSkills = new List<Skill>(importantSkills.items);
                newJobPosting.importantSkills.Remove(Skill.NullSkill);
                newJobPosting.nicetohaveSkills = new List<Skill>(nicetohaveSkills.items);
                newJobPosting.nicetohaveSkills.Remove(Skill.NullSkill);
                newJobPosting.recruiter = associatedRecruiter;
                newJobPosting.problemStatement = editorProblemStatement.Text;

                JobPostingController.getJobPostingController().createJobPosting(newJobPosting);
                Navigation.PopAsync(true);
            }
        }

        private String validateForm(){
            String invalidDataMessage = "";
            int temp;

            if(string.IsNullOrEmpty(entLocation.Text)){
                invalidDataMessage += "Location is required\n";
			}

			if (string.IsNullOrEmpty(entPhaseTimeout.Text))
			{
				invalidDataMessage += "Phase Timeout is required\n";
            }else if(!int.TryParse(entPhaseTimeout.Text, out temp)){
                invalidDataMessage += "Phase Timeout must be an integer\n";
            }else if(temp < 0){
                invalidDataMessage += "Phase Timeout must be non-negative\n";
            }

            if (string.IsNullOrEmpty(editorDescription.Text))
                {
                    invalidDataMessage += "A position description is required\n";
                }

			if (string.IsNullOrEmpty(entPositionTitle.Text))
			{
				invalidDataMessage += "A position title is required\n";
			}

            if(importantSkills.items.Count < 1){
                invalidDataMessage += "Atleast 1 required skill is required\n";
            }

            if (string.IsNullOrEmpty(editorProblemStatement.Text)){
                invalidDataMessage += "A problem statement must be provided\n";
            }
            return invalidDataMessage;
        }
    }
}

