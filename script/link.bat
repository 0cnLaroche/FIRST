REM @echo off

set SCRIPT="%TEMP%\%RANDOM%-%RANDOM%-%RANDOM%-%RANDOM%.vbs"

echo Set oWS = WScript.CreateObject("WScript.Shell") >> %SCRIPT%
REM echo sLinkFile = "\\\%USERNAME%\Downloads\test.lnk" >> %SCRIPT%
echo sLinkFile = "C:\Users\%USERNAME%\Downloads\FIRST2.lnk" >> %SCRIPT%
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> %SCRIPT%
echo oLink.TargetPath = "\\hrdc-drhc.net\nc_common-commun$\IITB-DGIIT\SPAM-SPAG\RM-GR\MACTI-ITCAM\Restricted\Time Reporting Tools Maintenance\SAP-CATS Administration\Database\FIRST_2.jar" >> %SCRIPT%
echo oLink.IconLocation = "\\hrdc-drhc.net\nc_common-commun$\IITB-DGIIT\SPAM-SPAG\RM-GR\MACTI-ITCAM\Restricted\Time Reporting Tools Maintenance\SAP-CATS Administration\Database\raccoon.ico" >> %SCRIPT%
echo oLink.Save >> %SCRIPT%

cscript /nologo %SCRIPT%
del %SCRIPT%

pause
