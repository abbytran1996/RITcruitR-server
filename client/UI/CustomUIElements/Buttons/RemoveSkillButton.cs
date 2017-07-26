using System;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.ViewCells;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.ListViews;

namespace TMCS_Client.CustomUIElements.Buttons
{
    public class RemoveSkillButton : Button
    {
        public static readonly BindableProperty SkillIDProperty =
            BindableProperty.Create("skillID", typeof(long), typeof(AddSkillButton), -1L);

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

        public RemoveSkillButton() : base()
        {
            BorderColor = Color.White;
            BorderWidth = 0.0;
            BackgroundColor = Color.White;
            BorderRadius = 0;
            Clicked += removeButtonClicked;
#if __IOS__
            Image = "TMCS_Client.iOS.Resources.remove.png";
#elif __ANDROID__
            Image = "remove.png";
#endif
        }

        private void removeButtonClicked(object sender, EventArgs e)
        {

            FormListView<Skill, SkillListCell> ancestor =
                ((FormListView<Skill, SkillListCell>)
                 ((SkillListCell)((AbsoluteLayout)((RemoveSkillButton)sender).Parent).Parent).Parent);

            Skill extractedSkill = new Skill()
            {
                id = ((RemoveSkillButton)sender).skillID,
                name = ((RemoveSkillButton)sender).name,
            };

            ancestor.removeItem(extractedSkill);
        }

        protected override void OnBindingContextChanged()
        {
            base.OnBindingContextChanged();

            if(BindingContext != null && skillID != -1)
            {
                this.IsEnabled = true;
                this.IsVisible = true;
            }
            else
            {
                this.IsEnabled = false;
                this.IsVisible = false;
            }
        }
    }
}
