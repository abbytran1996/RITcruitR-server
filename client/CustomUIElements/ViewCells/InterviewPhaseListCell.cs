using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;

namespace TMCS_Client.CustomUIElements.ViewCells
{
    public class InterviewPhaseListCell : ViewCell
    {
        FormFieldLabel lblStudentName;
        Label lblInterview;
        Label lblTag;

        public InterviewPhaseListCell()
        {
            AbsoluteLayout cellLayout = new AbsoluteLayout();

            cellLayout.Children.Add(lblStudentName = new FormFieldLabel(""),
                                   new Rectangle(0.5,0.0,0.9,0.5),
                                    AbsoluteLayoutFlags.All);

            cellLayout.Children.Add(lblInterview = new Label(){
                TextColor = Color.Gray,
            }, new Rectangle(0.5,1.0,0.9,0.5), AbsoluteLayoutFlags.All);

			cellLayout.Children.Add(lblTag= new Label()
			{
				TextColor = Color.Gray,
                HorizontalTextAlignment = TextAlignment.End,
			}, new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            this.View = cellLayout;
        }

		protected override void OnBindingContextChanged()
		{
			base.OnBindingContextChanged();
			if ((BindingContext != null) && (((Match)BindingContext) == Match.EmptyMatch))
			{
				this.View = new AbsoluteLayout()
				{
					HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
				};
				((AbsoluteLayout)this.View).Children.Add(new Label()
				{
					Text = "No Interview Phase Students",
					VerticalTextAlignment = TextAlignment.Center,
					HorizontalTextAlignment = TextAlignment.Center,
					FontSize = 22.0,
				}, new Rectangle(0.0, 0.0, 1.0, 1.0),
														 AbsoluteLayoutFlags.All);
			}
			else if (BindingContext != null)
			{
                //TODO Truncate/convert these values
                lblStudentName.Text = ((Match)BindingContext).student.firstName + " " +
                                 ((Match)BindingContext).student.lastName[0] + ".";
                lblInterview.Text = "Interview: ";// + ((Match)BindingContext).interview...
                lblTag.Text = "Tag: " + ((Match)BindingContext).tag;
			}
		}
    }
}
