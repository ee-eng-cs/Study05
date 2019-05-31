@echo on
@set SITE=http://localhost:8080/company
@set CURL=c:\tools\curl-7.58.0\bin\curl.exe
@set CURL=%CURL% -g -i -H "Accept: application/json" -H "Content-Type: application/json"
@set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"

:get-all-paged
%HR_YELLOW%
@powershell -Command Write-Host "GET all departments" -foreground "Green"
%CURL% "%SITE%/departments?page=0&size=20&sort=name,asc"
@echo.
%HR_YELLOW%
@powershell -Command Write-Host "GET all employees" -foreground "Green"
%CURL% "%SITE%/employees?page=0&size=20&sort=lastname,asc&sort=firstname,asc"
@echo.&pause

:finish
%HR_RED%
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause