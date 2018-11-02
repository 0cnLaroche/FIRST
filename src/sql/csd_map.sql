SELECT RUN_Solution.Solution AS "Solution ID", Solution.Name AS "Solution Name", RUN_Solution.RUN AS "RUN ID", RUN.Name AS "RUN Name", RUN_Solution.Weight FROM RUN_Solution 
	INNER JOIN Solution ON RUN_Solution.Solution = Solution.ID 
	INNER JOIN RUN ON RUN.ID = RUN_Solution.RUN 
ORDER BY Solution ASC;