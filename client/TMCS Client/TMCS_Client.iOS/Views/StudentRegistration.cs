using Foundation;
using System;
using UIKit;
using TMCS_Client.DTOs;

namespace TMCS_Client.iOS
{
    internal class StudentRegistrationSource : UITableViewSource{

        //All Cells
        private UIView[] cells = new UIView[12];

        //Title Cell
        UILabel lblTitle = null;

        //First Name Cell
        UILabel lblFirstName = null;
        UITextField txtBoxFirstName = null;

        //Last Name Cell

        public StudentRegistrationSource(){
            //Title Cell
            cells[0] = new UIView(new CoreGraphics.CGRect(0,0,375,60));
            lblTitle = new UILabel(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblTitle.Text = "Student Registration";
            lblTitle.Font = lblTitle.Font.WithSize(28);
            lblTitle.TextAlignment = UITextAlignment.Center;
            cells[0].AddSubview(lblTitle);

            //First Name Cell
            cells[1] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblFirstName = new UILabel(new CoreGraphics.CGRect(16, 0, 91, 21));
            lblFirstName.Text = "First Name";
            lblFirstName.Font = lblFirstName.Font.WithSize(18);
            txtBoxFirstName = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
            txtBoxFirstName.Placeholder = "First Name";
            txtBoxFirstName.BorderStyle = UITextBorderStyle.RoundedRect;
            txtBoxFirstName.Font = txtBoxFirstName.Font.WithSize(16);
            cells[1].AddSubviews(new UIView[] { lblFirstName, txtBoxFirstName });

            //Last Name Cell
            cells[2] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));


            //Rest of cells
            cells[3] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            cells[4] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            cells[5] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            cells[6] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            cells[7] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            cells[8] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            cells[9] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            cells[10] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            cells[11] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
        }

        public override UITableViewCell GetCell(UITableView tableView, NSIndexPath indexPath)
        {

            UITableViewCell newCell = tableView.DequeueReusableCell("FormCell");

            if (newCell == null)
            {
                newCell = new UITableViewCell(UITableViewCellStyle.Default, "FormCell");
            }

            newCell.ContentView.AddSubviews(cells[indexPath.Row].Subviews);
            newCell.SelectionStyle = UITableViewCellSelectionStyle.None;

            return newCell;
        }

        public override nint RowsInSection(UITableView tableview, nint section)
        {
            return cells.Length;
        }
    }

    public partial class StudentRegistration : UITableView
    {
        public StudentRegistration (IntPtr handle) : base (handle){
            this.Source = new StudentRegistrationSource();
        }

        public NewStudent newStudentFromForm(){
            NewStudent newStudent = null;

            return newStudent;
        }
    }
}