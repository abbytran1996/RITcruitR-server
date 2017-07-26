using System;
using System.Collections.Generic;
using System.Text;


namespace TMCS_Client.DTOs
{
    public class NewCompany : Company
    {

        private NewCompany() { }

        public static NewCompany createAndValidate(string companyName, string emailSuffix, string companyDescription,
                                                   string size, string location, string presentation, string websiteURL)
        {
            var newCompany = new NewCompany();
            newCompany.companyName = companyName;
            newCompany.emailSuffix = emailSuffix;
            newCompany.companyDescription = companyDescription;
            newCompany.size = size;
            newCompany.location = location;
            newCompany.presentation = presentation;
            newCompany.websiteURL = websiteURL;

            return newCompany;
        }
    }
}
