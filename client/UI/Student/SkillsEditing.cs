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
    public class SkillsEditing : ContentPage
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

        //Required Skills
        AbsoluteLayout requiredSkillsSection;
        SubSectionTitleLabel lblRequiredSkills;
        FormSearchBar<Skill, SkillSearchResultCell, SkillListCell> requiredSkillsSearchBar;
        FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell> requiredSkillsSearchResults;
        FormFieldLabel lblChosenRequiredSkills;
        FormListView<Skill, SkillListCell> requiredSkills;
        FormFieldLabel lblMinMatchedRequiredSkills;
        FormEntry entMinMatchedRequiredSkills;


        //Recruiter
        private DTOs.Student associatedStudent = null;

        //Create
        FormSubmitButton btnSave;

        public SkillsEditing(DTOs.Student associatedStudent)
        {
            this.Title = "Edit your skills";

            this.associatedStudent = associatedStudent;

            allSkills = SkillController.getSkillController().getAllSkills();

            //Whole Page
            pageContent = new ScrollView()
            {

            };

			creationForm = new AbsoluteLayout()
			{
				HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT * 7,
			};

            
            //Required Skills
            requiredSkillsSection = new AbsoluteLayout();

            lblRequiredSkills = new SubSectionTitleLabel("Skills Selection");
            
            requiredSkills = new FormListView<Skill, SkillListCell>(Skill.NullSkill);

			requiredSkillsSearchResults = new FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell>(requiredSkills);

            requiredSkillsSearchBar = new FormSearchBar<Skill, SkillSearchResultCell, SkillListCell>("Search for required skills",
                                                      allSkills, requiredSkillsSearchResults);

            lblChosenRequiredSkills = new FormFieldLabel("Your Skills");

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
                                     new Rectangle(0.0, 0.0,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT * 6),
                                     AbsoluteLayoutFlags.XProportional |
                                      AbsoluteLayoutFlags.WidthProportional);

			
            //Create
            btnSave = new FormSubmitButton("Save skills");
            btnSave.Command = new Command((object obj) => saveSkills());

			creationForm.Children.Add(btnSave,
										  new Rectangle(0.5, ((Constants.Forms.Sizes.ROW_HEIGHT * 6) + 10.0), 0.8, Constants.Forms.Sizes.ROW_HEIGHT - 20.0),
										  AbsoluteLayoutFlags.WidthProportional |
										 AbsoluteLayoutFlags.XProportional);

            pageContent.Content = creationForm;
            Content = pageContent;
        }

        private void saveSkills(){
            List<Skill> skillslist = new List<Skill>(requiredSkills.items);
            StudentController.getStudentController().addSkillsForStudent(associatedStudent, skillslist);
            Navigation.PopAsync(true);
        }

        
    }
}

