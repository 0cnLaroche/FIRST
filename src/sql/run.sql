SELECT RUN.ID, RUN.Name, RUN.NameFR, RUN.`Type`, RUN.Responsible, RUN.CostCenter_Responsible AS "Cost Center", RUN.EffectiveDate, 
RUN.`Status`, RUN.ClosingDate, RUN.ReplacedBy, RUN_Solution.Solution, RUN_Solution.Weight AS "APM Weight", RUN_Service.Service, RUN.Comments, RUN.ITServicesGroups, RUN.DistributedComputingEnvironment, 
RUN.ADDMByApp, RUN.ADDMByOutcome 
FROM RUN 
	LEFT JOIN RUN_Solution ON RUN.ID = RUN_Solution.RUN
	LEFT JOIN RUN_Service ON RUN.ID = RUN_Service.RUN 
ORDER BY ID ASC;