SELECT Network.ID, Network.Name AS 'Description', w.Approver AS 'Approver', Network.`Status`, Network.Stage, p.ID AS 'Project_ID', p.Name AS 'Project Description' 
FROM Network 
	INNER JOIN WBS w on w.ID = Network.WBS
	INNER JOIN ProjectDefinition p ON w.ProjectDefinition = p.ID;
