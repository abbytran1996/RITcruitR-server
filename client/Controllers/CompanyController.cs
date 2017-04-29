using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Controllers
/// <summary>
/// A controller to interact with companies
/// </summary>
{
	public class CompanyController
	{
        private static CompanyController companyController = null;
		private CompanyComms companyComms = new CompanyComms();

        private CompanyController(){
            
        }

        public static CompanyController getCompanyController(){
            if (companyController == null){
                companyController = new CompanyController();
            }

            return companyController;
        }

		/// <summary>
		/// Adds a new company to the server
		/// </summary>
		/// <param name="company">The company to add</param>
		public void addCompany(NewCompany company)
		{
			companyComms.addCompany(company);
		}
	}
}
