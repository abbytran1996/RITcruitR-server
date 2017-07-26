using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.CustomUIElements.ViewCells;

namespace TMCS_Client.CustomUIElements.Buttons
{
    public class AddSkillButton : Button
    {
        public static readonly BindableProperty SkillIDProperty =
            BindableProperty.Create("skillID", typeof(long), typeof(AddSkillButton), 0L);

        public static readonly BindableProperty NameProperty =
            BindableProperty.Create("name", typeof(String), typeof(AddSkillButton), "No skills selected");

        public long skillID
        {
            get { return (long)GetValue(SkillIDProperty); }
            set { SetValue(SkillIDProperty, value); }
        }

        public String name
        {
            get { return (String)GetValue(NameProperty); }
            set { SetValue(NameProperty, value); }
        }

        public AddSkillButton() : base()
        {
            BorderColor = Color.White;
            BorderWidth = 0.0;
            BackgroundColor = Color.White;
            BorderRadius = 0;
            Clicked += addButtonClicked;
#if __IOS__
            Image = "TMCS_Client.iOS.Resources.add.png";
#elif __ANDROID__
            Image = "add.png";
#endif
        }

        private void addButtonClicked(object sender, EventArgs e)
        {
            FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell> ancestor =
                ((FormSearchResultsListView<Skill, SkillSearchResultCell, SkillListCell>)
                 ((SkillSearchResultCell)((AbsoluteLayout)((AddSkillButton)sender).Parent)
                  .Parent).Parent);

            Skill extractedSkill = new Skill()
            {
                id = ((AddSkillButton)sender).skillID,
                name = ((AddSkillButton)sender).name,
            };

            ancestor.selectedItemsView.addItem(extractedSkill);
            ancestor.searchResults.Remove(extractedSkill);
            ancestor.hide();
        }
    }
}
