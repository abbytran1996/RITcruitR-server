using System;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.Buttons;

namespace TMCS_Client.CustomUIElements.ViewCells
{
    public class SkillListCell : ViewCell
    {
        public SkillListCell()
        {
			AbsoluteLayout cellLayout = new AbsoluteLayout()
			{
				HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
				BackgroundColor = Color.White,
			};

            Label lblSearchResult = new Label()
			{
				BackgroundColor = Color.White,
				VerticalTextAlignment = TextAlignment.Center,
			};

			RemoveSkillButton btnRemoveSkill = new RemoveSkillButton()
			{
			};

			btnRemoveSkill.SetBinding(RemoveSkillButton.SkillIDProperty, "id");
			btnRemoveSkill.SetBinding(RemoveSkillButton.NameProperty, "name");

			lblSearchResult.SetBinding(Label.TextProperty, "name");

			cellLayout.Children.Add(lblSearchResult,
								   new Rectangle(0.5, 0.0, 0.9, 1.0),
								   AbsoluteLayoutFlags.All);

			cellLayout.Children.Add(btnRemoveSkill, new Rectangle(1.0, 0.5, 40.0,
						   40.0),
								   AbsoluteLayoutFlags.PositionProportional);

			View = cellLayout;
        }
    }
}
