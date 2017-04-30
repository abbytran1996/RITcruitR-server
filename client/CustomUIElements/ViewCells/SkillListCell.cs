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

			Label searchResultLabel = new Label()
			{
				BackgroundColor = Color.White,
				VerticalTextAlignment = TextAlignment.Center,
			};

			RemoveSkillButton addSearchResult = new RemoveSkillButton()
			{
			};

			addSearchResult.SetBinding(RemoveSkillButton.SkillIDProperty, "id");
			addSearchResult.SetBinding(RemoveSkillButton.NameProperty, "name");

			searchResultLabel.SetBinding(Label.TextProperty, "name");

			cellLayout.Children.Add(searchResultLabel,
								   new Rectangle(0.5, 0.0, 0.9, 1.0),
								   AbsoluteLayoutFlags.All);

			cellLayout.Children.Add(addSearchResult, new Rectangle(1.0, 0.5, Constants.Forms.Sizes.ROW_HEIGHT * (2.0 / 3.0),
						   Constants.Forms.Sizes.ROW_HEIGHT * (2.0 / 3.0)),
								   AbsoluteLayoutFlags.PositionProportional);

			View = cellLayout;
        }
    }
}
