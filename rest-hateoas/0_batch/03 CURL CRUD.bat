@echo on
@set SITE=http://localhost:8080/company
@set CURL=c:\tools\curl-7.58.0\bin\curl.exe
@set CURL=%CURL% -g -i -H "Accept: application/json" -H "Content-Type: application/json"
@set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"

:create
%HR_YELLOW%
@powershell -Command Write-Host "CREATE" -foreground "Green"
%CURL% -d {\"name\":\"N-a-m-e-C-R-E-A-T-E\"} -X POST "%SITE%/departments/"
@echo.
%CURL% -d {\"firstName\":\"F-N-a-m-e-C-R-E-A-T-E\",\"lastName\":\"L-N-a-m-e-C-R-E-A-T-E\"} -X POST "%SITE%/employees/"
@echo.&pause

:get-single
%HR_YELLOW%
@powershell -Command Write-Host "GET single" -foreground "Green"
%CURL% "%SITE%/departments/1"
@echo.
%CURL% "%SITE%/employees/3"
@echo.&pause

:update
%HR_YELLOW%
@powershell -Command Write-Host "UPDATE" -foreground "Green"
%CURL% -d {\"name\":\"N-a-m-e-U-P-D-A-T-E\"} -X PUT "%SITE%/departments/1"
@echo.
%CURL% -d {\"firstName\":\"F-N-a-m-e-U-P-D-A-T-E\",\"lastName\":\"L-N-a-m-e-U-P-D-A-T-E\"} -X PUT "%SITE%/employees/3"
@echo.&pause

:get-all-paged
%HR_YELLOW%
@powershell -Command Write-Host "GET all paged" -foreground "Green"
%CURL% "%SITE%/departments?page=0&size=20&sort=name,asc"
@echo.
%CURL% "%SITE%/employees?page=0&size=20&sort=lastname,asc&sort=firstname,asc"
@echo.&pause

::goto :get-all
:delete
%HR_YELLOW%
@powershell -Command Write-Host "DELETE" -foreground "Green"
%CURL% -X DELETE "%SITE%/departments/1"
@echo.
%CURL% -X DELETE "%SITE%/employees/3"
@echo.&pause

:get-all
%HR_YELLOW%
@powershell -Command Write-Host "GET all" -foreground "Green"
%CURL% "%SITE%/departments/"
@echo.
%CURL% "%SITE%/employees/"
@echo.&pause

:finish
%HR_RED%
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause

::"c:\0_workspace\Study02\0_batch\02 call server with curl.bat" 