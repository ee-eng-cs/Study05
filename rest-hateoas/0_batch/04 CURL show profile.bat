@echo on
@set CURL=c:\tools\curl-7.58.0\bin\curl.exe
@set CURL=%CURL% -g -i -H "Accept: application/json" -H "Content-Type: application/json"
@set PROFILE=http://localhost:8080/profile

@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@powershell -Command Write-Host "Profile Departments" -foreground "Green"
%CURL% "%PROFILE%/departments"
@echo.&pause

@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@powershell -Command Write-Host "Profile Employees" -foreground "Green"
%CURL% "%PROFILE%/employees"
@echo.&pause

@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause