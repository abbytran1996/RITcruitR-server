using System;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.Buttons;

namespace TMCS_Client.CustomUIElements.ViewCells
{
	public class SkillSearchResultCell : ViewCell
	{
		public SkillSearchResultCell() : base()
		{
			AbsoluteLayout cellLayout = new AbsoluteLayout()
			{
				HeightRequest = 40.0,
				BackgroundColor = Color.White,
			};

            Label lblSearchResult = new Label()
			{
				BackgroundColor = Color.White,
				VerticalTextAlignment = TextAlignment.Center,
			};

            AddSkillButton btnAddSearchResult = new AddSkillButton()
			{
			};

			btnAddSearchResult.SetBinding(AddSkillButton.SkillIDProperty, "id");
			btnAddSearchResult.SetBinding(AddSkillButton.NameProperty, "name");

			lblSearchResult.SetBinding(Label.TextProperty, "name");

			cellLayout.Children.Add(lblSearchResult,
								   new Rectangle(0.5, 0.0, 0.9, 1.0),
								   AbsoluteLayoutFlags.All);

			cellLayout.Children.Add(btnAddSearchResult, new Rectangle(1.0, 0.5, 40.0,
						   40.0),
								   AbsoluteLayoutFlags.PositionProportional);

			View = cellLayout;
		}
	}
}
