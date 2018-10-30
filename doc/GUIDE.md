# FIRST2

### Introduction
This guide has for objective to help you learn how to use FIRST2. You should be able to get the ropes very easily.

**This guide is composed of 7 sections:**

1. Introduction
1. Projects
1. Run
1. Cost Centers
1. Exports
1. Main controls
1. Administration
1. About

#### What is FIRST ?
FIRST stands for  *Financial Information Reference and Search Tool Version 2*. It's data management system for Financial coding in a context of IT Activity Based Costing (ABC). In the past lists of codes where kept on excel spreadsheets that required tremendous effort to maintain and lacked of consistency and readability. FIRST was developed in an attempt to merge data into a single tool that will be precise, easy to maintain and use. FIRST2 is written in JAVA and uses a MySQL database.

#### Access FIRST
1. Go to [ITCAR SharePoint](http://dialogue/grp/SFI-IFS/SitePages/Home.aspx) site or [CATS Intranet page](http://esdc.prv/en/iitb/corporate/CATS/index.shtml) and click on the link for FIRST.
1. FIRST will launch on your desktop

### Tabs
![Image of tabs](http://dialogue/grp/SFI-IFS/SiteAssets/IT_Costing_Assets/images/tabs.PNG)

Each modules (except Administration) can be accessed via a tab on the left. You can switch between the modules just by clicking on them. You won't loose what you were viewing while going on other tabs.

## Projects
After switching to the Project tab, you should see a blank space and a the Keyword field at the bottom.

#### Finding a project
You can retrieve a project by using any keyword related to it.
1. Type any of the keywords listed below
1. Click **Go**
1. A dialog box will open if there is more then one project matching your search. **Double-Click** on a project or **select one** than click **OK** to open it.

##### Keywords
- Project Name
- Proposal Number (ie: 15-9007-003 )
- Project Manager
- Cost Center (ie: 205460)
- Network code (ie: (64378)
- SAP code (ie: I-0090)

#### Viewing a project
The **top** section will show the **project description** with relevant information such as its proposal number and governance.
The **center** will show all of the **network codes**, separated **by Stage**. If the project is not Stage-Gated, all of the networks are listed in one block. **Activities** can be seen by clicking on the small **+** sign.

## Run
The Run Module is composed of a filters section and a results section. A single click on an element of the result list will open a new window with more details about the code.

#### Finding a Run code
There is many ways you can find a run code. The filter section let you use a variety of of fields for which you can retrieve one or many codes. These filters are dynamics so you don't have to click anything.

#### Filters
- **Status**
  - **Active** : Available to use by employees in CATS and for non-salary expenditures
  - **Closed** : Use is not allowed either for CATS and non-salary. It can have been replaced with a new code.
- **Type**
  - **Maintenance** : These are used to capturing effort put on maintaining applications.
  - **Service** : Activities that are a service provided to internal or external clients.
  - **Business Management** : Other activities related to the management of the Branch.
  - **Anticipatory Projects** : Activities for projects waiting for official approvals.
- **Cost Center** (Dropdown List)
- **Manager** (Dropdown List)
- **#** : Run code or Rec.Order
- **Keyword** : Description or name of the code

The **clear** button will remove all filters.

## Cost Center
The Cost Center Module is composed of Tree view of the cost center hierarchy on the left, detail viewer in the center, and a search section at the bottom.

#### Finding a cost center
1. In the keyword section, type either a **manager's name**, a cost **center number** or **name**.
1. Click **Go**
1. Cost centers matching your search will be selected into the tree view, you can **click** on anyone to **see details** in the center.

## Export
This module allows you to export data from FIRST's database as excel files. These are usually used as reference sheets for reports.

#### How to
1. Select a query
1. Click **open**
1. **Select a location** on your computer where the file will be saved.
1. Click yes if you want to open the file after saving it.

They are 4 queries that you can export :

- **Networks by Approvers** : A list of all network codes (ID, Description, Approver, Status, Stage, Project ID).
- **RUN Codes** : List of all RUN Codes (ID, Description, Type, Cost Center, Status, CSD/Service ID).
- **Cost Center by Level** : A list of cost centers followed by their reporting cost center (parents) on 6 levels.
- **Project List** : A list of all projects including Stage-Gated, Branch Initiatives and Lite. Fields include Project SAP code, Project Name, Model, Proposal #, Project Manager, Status, Closing Date and GCIT Attributes.

### Main controls
![Image of main controls](http://dialogue/grp/SFI-IFS/SiteAssets/IT_Costing_Assets/images/controls.PNG)

#### Copy/Paste
Click on the camera button to copy to your clipboard. Data will be copied as a table that you will be able to edit after you paste it in any kind of Office document. Data will be copied from the current tab with information you have selected. For projects, the project that you have opened will be copied as a whole. For Run, the selection after applying filters will be copied. For cost centers, only the selected cost center will be copied.

#### Export to PDF
This functionality has not been implemented yet, but will allow you to export the current view as a PDF document.

#### Lock/Unlock
This button allows to unlock/lock FIRST for modifications. You won't use that unless you have an administrator* access.

_*Please refer to the Administration Section for more details._

#### Refresh
Refreshing FIRST will download the latest modifications to the database and reload them into the app.

## Administration
The admin module lets you modify information or create any financial coding in FIRST database. Note that this won't apply the changes in SAP. **You will have to make them manually**. You should make sure the information is close as possible as of SAP.

#### Login as administrator
1. Click on the **Lock button** in the main controls section.
1. Enter the **user name** and **password** provided to you by IITB CATS administration team (ITCAR).
1. Click **OK**
1. The lock button should show as unlocked if you logged in successfully.

#### Making modifications*
The detail form of every module should show a **save** button at its bottom.

1. Modify a field in on of the modules detail form.
1. Click the **save** button
1. **Refresh** to see the changes

_*You must be logged in as Administrator to perform this task_

#### Adding a new code*
1. Click on the **module tab** for which you wish to add a new code
1. Click on the **new** button (sheet icon)**
1. **Fill** out all the relevant fields. Don't forget the mandatory ones.
1. Click **save**

_*You must be logged in as Administrator to perform this task._
_**Only appears if your are logged in as Administrator._

### Still need help ?
Contact us at NC-ART-GD@hrsdc-rhdcc.gc.ca

### About
FIRST was produced by IT Costing, Reporting and Analysis team (Developer: Samuel Laroche). FIRST2© is a property of ESDC.

![Image of logo](http://dialogue/grp/SFI-IFS/SiteAssets/IT_Costing_Assets/images/Raccoon.png)

20-08-2018
